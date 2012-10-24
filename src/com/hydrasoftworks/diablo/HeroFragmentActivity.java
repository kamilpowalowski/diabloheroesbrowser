package com.hydrasoftworks.diablo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Hero;

public class HeroFragmentActivity extends SherlockFragmentActivity {
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Hero hero = null;
		try {
			hero = CareerProfile.getActiveProfile().getDownloadedHero(
				getIntent().getIntExtra(Hero.HERO_ID, 0));
		} 
		catch(NullPointerException ex) {
			startActivity(new Intent(this, DiabloHeroesBrowserActivity.class));
			finish();
		}
		setContentView(R.layout.career_profile_fragment_activity);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

		TabsAdapter mTabsAdapter = new TabsAdapter(this, viewPager);
		ActionBar bar = getSupportActionBar();

		bar.setTitle(Html.fromHtml(hero.getName()
				+ " ("
				+ hero.getLevel()
				+ (hero.getParagonLevel() > 0 ? " <small><font color='#A99FFF'>("
						+ hero.getParagonLevel() + ")</font></small>" : "") + " "
				+ WordUtils.capitalize(hero.getHeroClass().replace("-", " "))
				+ ")"));
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setHomeButtonEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);

		mTabsAdapter.addTab(
				bar.newTab().setText(R.string.hero_progression_tab),
				HeroProgressionFragment.class, null);

		mTabsAdapter.addTab(bar.newTab().setText(R.string.hero_general_tab),
				HeroStatisticFragment.class, null);

		if (!hero.isFallen()) {
			mTabsAdapter.addTab(bar.newTab().setText(R.string.skills_tab),
					SkillsFragment.class, null);
		}
		mTabsAdapter.addTab(bar.newTab().setText(R.string.equipment_tab),
				EquipmentFragment.class, null);

		if (!hero.isFallen()) {
			mTabsAdapter.addTab(bar.newTab().setText(R.string.followers_tab),
					FollowersFragment.class, null);
		}
		viewPager.setAdapter(mTabsAdapter);
		viewPager.setOffscreenPageLimit(5);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = getIntent().setClass(this,
					CareerProfileFragmentActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class TabsAdapter extends FragmentStatePagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> _class, Bundle _args) {
				clss = _class;
				args = _args;
			}
		}

		public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mActionBar = activity.getSupportActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			tab.setTag(info);
			tab.setTabListener(this);
			mTabs.add(info);
			mActionBar.addTab(tab);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
			selectInSpinnerIfPresent(position, true);
		}

		public void onPageScrollStateChanged(int state) {
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();
			for (int i = 0; i < mTabs.size(); i++) {
				if (mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);
				}
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
		
		/**
		 * Hack that takes advantage of interface parity between ActionBarSherlock and the native interface to reach inside
		 * the classes to manually select the appropriate tab spinner position if the overflow tab spinner is showing.
		 * 
		 * Related issues: https://github.com/JakeWharton/ActionBarSherlock/issues/240 and
		 * https://android-review.googlesource.com/#/c/32492/
		 * 
		 * @author toulouse@crunchyroll.com
		 */
		private void selectInSpinnerIfPresent(int position, boolean animate) {
		    try {
		        View actionBarView = findViewById(R.id.abs__action_bar);
		        if (actionBarView == null) {
		            int id = getResources().getIdentifier("action_bar", "id", "android");
		            actionBarView = findViewById(id);
		        }

		        Class<?> actionBarViewClass = actionBarView.getClass();
		        Field mTabScrollViewField = actionBarViewClass.getDeclaredField("mTabScrollView");
		        mTabScrollViewField.setAccessible(true);

		        Object mTabScrollView = mTabScrollViewField.get(actionBarView);
		        if (mTabScrollView == null) {
		            return;
		        }

		        Field mTabSpinnerField = mTabScrollView.getClass().getDeclaredField("mTabSpinner");
		        mTabSpinnerField.setAccessible(true);

		        Object mTabSpinner = mTabSpinnerField.get(mTabScrollView);
		        if (mTabSpinner == null) {
		            return;
		        }

		        Method setSelectionMethod = mTabSpinner.getClass().getSuperclass().getDeclaredMethod("setSelection", Integer.TYPE, Boolean.TYPE);
		        setSelectionMethod.invoke(mTabSpinner, position, animate);

		        Method requestLayoutMethod = mTabSpinner.getClass().getSuperclass().getDeclaredMethod("requestLayout");
		        requestLayoutMethod.invoke(mTabSpinner);
		    } catch (IllegalArgumentException e) {
		        e.printStackTrace();
		    } catch (IllegalAccessException e) {
		        e.printStackTrace();
		    } catch (NoSuchFieldException e) {
		        e.printStackTrace();
		    } catch (NoSuchMethodException e) {
		        e.printStackTrace();
			} catch (InvocationTargetException e) {
		        e.printStackTrace();
		    }
		}
	}


}
