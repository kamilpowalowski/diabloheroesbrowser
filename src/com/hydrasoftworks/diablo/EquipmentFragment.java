package com.hydrasoftworks.diablo;

import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Hero;
import com.hydrasoftworks.diablo.model.Item;

public class EquipmentFragment extends SherlockFragment {
	private static final String TAG = EquipmentFragment.class.getSimpleName();
	private Hero hero;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.equipment_fragment, container, false);
		hero = CareerProfile.getActiveProfile().getActiveHero();

		new EquipmentLoad("shoulders", R.id.shoulders).execute();
		new EquipmentLoad("head", R.id.head).execute();
		new EquipmentLoad("torso", R.id.torso).execute();
		new EquipmentLoad("neck", R.id.neck).execute();
		new EquipmentLoad("hands", R.id.hands).execute();
		new EquipmentLoad("bracers", R.id.bracers).execute();
		new EquipmentLoad("waist", R.id.waist).execute();
		new EquipmentLoad("legs", R.id.legs).execute();
		new EquipmentLoad("feet", R.id.feet).execute();
		new EquipmentLoad("leftFinger", R.id.leftFinger).execute();
		new EquipmentLoad("rightFinger", R.id.rightFinger).execute();
		new EquipmentLoad("mainHand", R.id.mainHand).execute();
		new EquipmentLoad("offHand", R.id.offHand).execute();

		ImageView equipmentImage = (ImageView) view
				.findViewById(R.id.equipment_image);

		if (hero.getHeroClass().equals(Hero.CLASS_BARBARIAN)) {
			if (hero.getGender() == 0) {
				equipmentImage
						.setImageResource(R.drawable.equipment_barbarian_male);
			} else {
				equipmentImage
						.setImageResource(R.drawable.equipment_barbarian_female);
			}
		} else if (hero.getHeroClass().equals(Hero.CLASS_DEMON_HUNTER)) {
			if (hero.getGender() == 0) {
				equipmentImage.setImageResource(R.drawable.equipment_dh_male);
			} else {
				equipmentImage.setImageResource(R.drawable.equipment_dh_female);
			}
		} else if (hero.getHeroClass().equals(Hero.CLASS_MONK)) {
			if (hero.getGender() == 0) {
				equipmentImage.setImageResource(R.drawable.equipment_monk_male);
			} else {
				equipmentImage
						.setImageResource(R.drawable.equipment_monk_female);
			}
		} else if (hero.getHeroClass().equals(Hero.CLASS_WITCH_DOCTOR)) {
			if (hero.getGender() == 0) {
				equipmentImage.setImageResource(R.drawable.equipment_wd_male);
			} else {
				equipmentImage.setImageResource(R.drawable.equipment_wd_female);
			}
		} else if (hero.getHeroClass().equals(Hero.CLASS_WIZARD)) {
			if (hero.getGender() == 0) {
				equipmentImage
						.setImageResource(R.drawable.equipment_wizard_male);
			} else {
				equipmentImage
						.setImageResource(R.drawable.equipment_wizard_female);
			}
		}
		return view;
	}

	public static int getBackground(String color) {
		if (color.equals("white")) {
			return R.drawable.item_white_shape;
		} else if (color.equals("yellow")) {
			return R.drawable.item_yellow_shape;
		} else if (color.equals("orange")) {
			return R.drawable.item_orange_shape;
		} else if (color.equals("blue")) {
			return R.drawable.item_blue_shape;
		} else if (color.equals("green")) {
			return R.drawable.item_green_shape;
		} else if (color.equals("grey")) {
			return R.drawable.item_grey_shape;
		}
		return 0;
	}

	private class EquipmentLoad extends AsyncTask<Void, Void, Drawable> {
		private String itemName;
		private int imageViewId;
		private Item item;

		public EquipmentLoad(String itemName, int imageViewId) {
			this.itemName = itemName;
			this.imageViewId = imageViewId;
		}

		@Override
		protected Drawable doInBackground(Void... params) {
			try {
				item = hero.getItem(itemName);

				InputStream is = item.createImageLink().openStream();
				Drawable d = Drawable.createFromStream(is, "src");

				return d;
			} catch (Exception ex) {
				Log.d(TAG,
						"Error when downloading image file "
								+ ex.getLocalizedMessage());

				return null;
			}
		}

		@Override
		protected void onPostExecute(Drawable result) {
			ImageView imageView = (ImageView) view.findViewById(imageViewId);
			if (result != null) {
				imageView.setImageDrawable(result);
				imageView.setBackgroundResource(getBackground(item
						.getDisplayColor()));
				imageView.setOnClickListener(new OnClickListener() {

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

}
