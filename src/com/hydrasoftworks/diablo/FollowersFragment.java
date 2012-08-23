package com.hydrasoftworks.diablo;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Follower;
import com.hydrasoftworks.diablo.model.Hero;
import com.hydrasoftworks.diablo.model.Item;
import com.hydrasoftworks.diablo.model.Skill;
import com.hydrasoftworks.diablo.views.FollowerInfoView;
import com.hydrasoftworks.diablo.views.PassiveSkillView;
import com.hydrasoftworks.diablo.views.SkillView;

public class FollowersFragment extends SherlockFragment {
	private static final String TAG = FollowersFragment.class.getSimpleName();
	private Hero hero;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.followers_fragment, container,
				false);

		hero = CareerProfile.getActiveProfile().getActiveHero();
		FollowerInfoView fiv = (FollowerInfoView) view
				.findViewById(R.id.templar_view);
		Follower follower = hero.getFollower(Follower.TEMPLAR);
		new EquipmentLoad(follower, "neck", fiv, R.id.neck).execute();
		new EquipmentLoad(follower, "offHand", fiv, R.id.offHand).execute();
		new EquipmentLoad(follower, "mainHand", fiv, R.id.mainHand).execute();
		new EquipmentLoad(follower, "special", fiv, R.id.special).execute();
		new EquipmentLoad(follower, "rightFinger", fiv, R.id.rightFinger)
				.execute();
		new EquipmentLoad(follower, "leftFinger", fiv, R.id.leftFinger)
				.execute();
		if (follower != null) {
			creatSkillInfo(view, follower, R.id.templar_skills);
		}
		fiv = (FollowerInfoView) view.findViewById(R.id.scoundrel_view);
		follower = hero.getFollower(Follower.SCOUNDREL);
		new EquipmentLoad(follower, "neck", fiv, R.id.neck).execute();
		new EquipmentLoad(follower, "offHand", fiv, R.id.offHand).execute();
		new EquipmentLoad(follower, "mainHand", fiv, R.id.mainHand).execute();
		new EquipmentLoad(follower, "special", fiv, R.id.special).execute();
		new EquipmentLoad(follower, "rightFinger", fiv, R.id.rightFinger)
				.execute();
		new EquipmentLoad(follower, "leftFinger", fiv, R.id.leftFinger)
				.execute();
		if (follower != null) {
			creatSkillInfo(view, follower, R.id.scoundrel_skills);
		}
		fiv = (FollowerInfoView) view.findViewById(R.id.enchantress_view);
		follower = hero.getFollower(Follower.ENCHANTRESS);
		new EquipmentLoad(follower, "neck", fiv, R.id.neck).execute();
		new EquipmentLoad(follower, "offHand", fiv, R.id.offHand).execute();
		new EquipmentLoad(follower, "mainHand", fiv, R.id.mainHand).execute();
		new EquipmentLoad(follower, "special", fiv, R.id.special).execute();
		new EquipmentLoad(follower, "rightFinger", fiv, R.id.rightFinger)
				.execute();
		new EquipmentLoad(follower, "leftFinger", fiv, R.id.leftFinger)
				.execute();

		if (follower != null) {
			creatSkillInfo(view, follower, R.id.enchantress_skills);
		}

		return view;
	}

	private void creatSkillInfo(View view, Follower follower, int layoutId) {
		LinearLayout layout = (LinearLayout) view.findViewById(layoutId);
		LinearLayout firstLine = new LinearLayout(getActivity());
		firstLine.setOrientation(LinearLayout.HORIZONTAL);
		layout.addView(firstLine);
		LinearLayout secondLine = new LinearLayout(getActivity());
		firstLine.setOrientation(LinearLayout.HORIZONTAL);
		layout.addView(secondLine);

		for (final Skill skill : follower.getSkills()) {
			if (skill.getSkillInfo() == null) {
				continue;
			}
			TextView tv = new TextView(getActivity());
			tv.setText(skill.getSkillInfo().getName());
			tv.setPadding(10, 5, 10, 5);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.weight = 1.0f;
			params.setMargins(5, 0, 0, 0);
			tv.setLayoutParams(params);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							TooltipWebViewActivity.class);
					intent.putExtra(TooltipWebViewActivity.URL,
							skill.createTooltipLink());
					startActivity(intent);

				}
			});
			if (firstLine.getChildCount() < 2)
				firstLine.addView(tv);
			else
				secondLine.addView(tv);
			try {
				new SkillImageLoad(tv).execute(skill.getSkillInfo()
						.createImageLink());
			} catch (MalformedURLException e) {
				Log.d(TAG, "Can't found skill image");
			}

		}
	}

	private class EquipmentLoad extends AsyncTask<Void, Void, Drawable> {
		private Follower follower;
		private String itemName;
		private int imageViewId;
		private Item item;
		private FollowerInfoView fiv;

		public EquipmentLoad(Follower follower, String itemName,
				FollowerInfoView fiv, int imageViewId) {
			this.follower = follower;
			this.itemName = itemName;
			this.imageViewId = imageViewId;
			this.fiv = fiv;
		}

		@Override
		protected Drawable doInBackground(Void... params) {
			try {
				item = follower.getItem(itemName);

				InputStream is = item.createImageLink().openStream();
				Drawable d = Drawable.createFromStream(is, "src");

				return d;
			} catch (Exception ex) {
				Log.d(TAG,
						"Error when downloading image file " + ex.getMessage());
				return null;

			}
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				fiv.setItemImage(result, imageViewId);
				fiv.setItemBackgroundResource(
						EquipmentFragment.getBackground(item.getDisplayColor()),
						imageViewId);

				fiv.setItemOnClickListener(imageViewId, new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								TooltipWebViewActivity.class);
						intent.putExtra(TooltipWebViewActivity.URL,
								item.createTooltipLink());
						startActivity(intent);

					}
				});
			}
		}

	}

	private class SkillImageLoad extends AsyncTask<URL, Void, Drawable> {
		private TextView textView;

		public SkillImageLoad(TextView tv) {
			textView = tv;
		}

		@Override
		protected Drawable doInBackground(URL... params) {
			try {
				InputStream is = params[0].openStream();
				Drawable d = Drawable.createFromStream(is, "src");

				return d;
			} catch (Exception ex1) {
				Log.d(TAG, "Can't found skill image");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				textView.setCompoundDrawablesWithIntrinsicBounds(result, null,
						null, null);
			}
		}

	}

}
