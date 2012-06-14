package com.hydrasoftworks.diablo;

import java.util.Collections;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.CareerProfile.Progression;

public class CareerFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.career_fragment, container, false);
		CareerProfile profile = CareerProfile.getActiveProfile();

		Collections.sort(profile.getProgression(), Collections.reverseOrder());
		Progression progression = profile.getProgression().get(0);
		int progressionValue = progression.getAct();
		for (int i = 0; i < Progression.LEVELS.length; i++) {
			if (progression.getDifficulty().equals(Progression.LEVELS[i])) {
				progressionValue += i * Progression.LEVELS.length;
			}
		}
		SeekBar progressBar = (SeekBar) view
				.findViewById(R.id.progression_seek_normal);
		progressBar.setProgress(progressionValue);
		progressBar.setEnabled(false);

		((TextView) view.findViewById(R.id.kills)).setText(Html
				.fromHtml(getString(R.string.kills, profile.getKills()
						.getMonsters())));
		((TextView) view.findViewById(R.id.elite_kills)).setText(Html
				.fromHtml(getString(R.string.elite_kills, profile.getKills()
						.getElites())));
		((TextView) view.findViewById(R.id.hardcore_kills)).setText(Html
				.fromHtml(getString(R.string.hardcore_kills, profile.getKills()
						.getHardcoreMonsters())));
		return view;
	}

}
