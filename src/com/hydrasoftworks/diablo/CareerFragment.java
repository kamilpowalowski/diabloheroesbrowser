package com.hydrasoftworks.diablo;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.CareerProfile;

public class CareerFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.career_fragment, container, false);
		CareerProfile profile = CareerProfile.getActiveProfile();


		SeekBar progressBar = (SeekBar) view
				.findViewById(R.id.progression_seek_normal);
		progressBar.setProgress(profile.getProgressValue());
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
