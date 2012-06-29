package com.hydrasoftworks.diablo;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.Act.Quest;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Hero;

public class HeroProgressionFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.hero_progression_fragment,
				container, false);
		Hero hero = CareerProfile.getActiveProfile().getActiveHero();
		((TextView) view.findViewById(R.id.elite_kills)).setText(Html
				.fromHtml(getString(R.string.elite_kills, hero.getKills()
						.getElites())));
		if (!hero.isFallen()) {
			SeekBar progressBar = (SeekBar) view
					.findViewById(R.id.progression_seek_normal);
			progressBar.setProgress(hero.getProgressValue());
			progressBar.setEnabled(false);

			Quest quest = hero.getLastFinishedQuest();
			if (quest == null) {
				view.findViewById(R.id.quest_layout).setVisibility(View.GONE);
			} else {
				((TextView) view.findViewById(R.id.last_quest)).setText(quest
						.getName());
			}

		} else {
			view.findViewById(R.id.progression_seek_normal).setVisibility(
					View.GONE);
			view.findViewById(R.id.quest_layout).setVisibility(View.GONE);
			view.findViewById(R.id.progression_labels).setVisibility(View.GONE);

			TextView kills = (TextView) view.findViewById(R.id.kills);
			kills.setVisibility(View.VISIBLE);
			kills.setText(Html.fromHtml(getString(R.string.kills, hero
					.getKills().getMonsters())));

			view.findViewById(R.id.fallen_layout).setVisibility(View.VISIBLE);
			TextView fallenInfo = (TextView) view
					.findViewById(R.id.fallen_info);
			fallenInfo
					.setText(String.format(getString(R.string.fallen_info),
							hero.getDeath().getKiller(), hero.getDeath()
									.getLocation()));
		}
		return view;
	}
}
