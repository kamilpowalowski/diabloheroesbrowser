package com.hydrasoftworks.diablo;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Hero;
import com.hydrasoftworks.diablo.model.Skill;
import com.hydrasoftworks.diablo.views.PassiveSkillView;
import com.hydrasoftworks.diablo.views.SkillView;

public class SkillsFragment extends SherlockFragment {
	private static final String TAG = SkillsFragment.class.getSimpleName();

	private int[] skillsViewsIds = { R.id.skill0, R.id.skill1, R.id.skill2,
			R.id.skill3, R.id.skill4, R.id.skill5 };
	private int[] passvieSkillsViewsIds = { R.id.passive_skill0,
			R.id.passive_skill1, R.id.passive_skill2 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		HashMap<String, Integer> runes = new HashMap<String, Integer>();
		runes.put("a", R.drawable.rune_a);
		runes.put("b", R.drawable.rune_b);
		runes.put("c", R.drawable.rune_c);
		runes.put("d", R.drawable.rune_d);
		runes.put("e", R.drawable.rune_e);

		View view = inflater
				.inflate(R.layout.skills_fragment, container, false);
		List<Skill> activeSkills = CareerProfile.getActiveProfile()
				.getActiveHero().getSkills(Skill.ACTIVE_SKILL);

		for (int i = 0; i < skillsViewsIds.length; i++) {
			SkillView skillView = (SkillView) view
					.findViewById(skillsViewsIds[i]);
			final Skill skill = activeSkills.get(i);
			if (skill != null) {
				skillView.setSkillName(skill.getSkillInfo().getName());
				if (skill.getRune() != null) {
					skillView.setRuneImage(getResources().getDrawable(
							runes.get(skill.getRune().getType())));
					skillView.setRuneName(skill.getRune().getName());

				}

				skillView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								TooltipWebViewActivity.class);
						intent.putExtra(TooltipWebViewActivity.URL,
								skill.createTooltipLink());
						startActivity(intent);

					}
				});

				try {
					new SkillImageLoad(skillView).execute(skill.getSkillInfo()
							.createImageLink());
				} catch (MalformedURLException e) {
					Log.d(TAG, e.getMessage());
				}
			}
		}

		List<Skill> passiveSkills = CareerProfile.getActiveProfile()
				.getActiveHero().getSkills(Skill.PASSIVE_SKILL);
		for (int i = 0; i < passvieSkillsViewsIds.length; i++) {
			PassiveSkillView skillView = (PassiveSkillView) view
					.findViewById(passvieSkillsViewsIds[i]);
			final Skill skill = passiveSkills.get(i);
			if (skill != null) {
				skillView.setSkillName(skill.getName());

				skillView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								TooltipWebViewActivity.class);
						intent.putExtra(TooltipWebViewActivity.URL,
								skill.createTooltipLink());
						startActivity(intent);

					}
				});

				try {
					new SkillImageLoad(skillView).execute(skill
							.createImageLink());
				} catch (MalformedURLException e) {
					Log.d(TAG, e.getMessage());
				}
			}
		}

		return view;
	}

	private class SkillImageLoad extends AsyncTask<URL, Void, Drawable> {
		private SkillView skillView;
		private PassiveSkillView passiveSkillView;

		public SkillImageLoad(SkillView skillView) {
			this.skillView = skillView;
		}

		public SkillImageLoad(PassiveSkillView skillView) {
			passiveSkillView = skillView;
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
				if (skillView != null)
					skillView.setSkillImage(result);
				if (passiveSkillView != null)
					passiveSkillView.setSkillImage(result);
			}
		}

	}
}
