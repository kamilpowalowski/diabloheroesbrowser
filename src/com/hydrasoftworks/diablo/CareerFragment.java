package com.hydrasoftworks.diablo;

import java.util.Collections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.CareerProfile.Progression;

public class CareerFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.career_fragment, container, false);
		CareerProfile profile = CareerProfile.getActiveElement();

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
		return view;
	}

}
