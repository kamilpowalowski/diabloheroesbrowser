package com.hydrasoftworks.diablo;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.hydrasoftworks.diablo.model.BattleTag;
import com.hydrasoftworks.diablo.model.CareerProfile;

public class CareerProfileFragmentActivity extends SherlockFragmentActivity {
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		CareerProfile profile = CareerProfile.getElement(getIntent()
				.getExtras().getString(BattleTag.BATTLETAG));
		setContentView(R.layout.career_profile_fragment_activity);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

		TabsAdapter mTabsAdapter = new TabsAdapter(this, viewPager);
		ActionBar bar = getSupportActionBar();

		mTabsAdapter.addTab(bar.newTab().setText(R.string.career_tab),
				CareerFragment.class, null);

		mTabsAdapter.addTab(bar.newTab().setText(R.string.artisans_tab),
				ArtisansFragment.class, null);

		mTabsAdapter.addTab(bar.newTab().setText(R.string.heroes_tab),
				HeroesFragment.class, null);

		mTabsAdapter.addTab(bar.newTab().setText(R.string.fallen_heroes_tab),
				FallenHeroesFragment.class, null);

		viewPager.setAdapter(mTabsAdapter);
		viewPager.setOffscreenPageLimit(3);

		bar.setTitle(profile.getBattleTag().getBattleTag());
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	}

	public static class TabsAdapter extends FragmentStatePagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
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
	}
}
