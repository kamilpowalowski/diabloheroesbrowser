package com.hydrasoftworks.diablo;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.Artisan;
import com.hydrasoftworks.diablo.model.CareerProfile;

public class ArtisansFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.artisans_fragment, container,
				false);
		List<Artisan> artisans = CareerProfile.getActiveElement().getArtisans();
		List<Artisan> hardcoreArtisans = CareerProfile.getActiveElement()
				.getHardcoreArtisans();
		for (Artisan artisan : artisans) {
			if (artisan.getSlug().equals(Artisan.SLUG_BLACKSMITH))
				((TextView) view.findViewById(R.id.blacksmith_level))
						.setText(getString(
								R.string.artisan_level, artisan.getLevel()));
			if (artisan.getSlug().equals(Artisan.SLUG_JEWELER))
				((TextView) view.findViewById(R.id.jeweler_level))
						.setText(getString(
								R.string.artisan_level, artisan.getLevel()));
		}
		for (Artisan artisan : hardcoreArtisans) {
			if (artisan.getSlug().equals(Artisan.SLUG_BLACKSMITH))
				((TextView) view.findViewById(R.id.blacksmith_hardcore_level))
						.setText(getString(
								R.string.artisan_level_hardcore,
								artisan.getLevel()));
			if (artisan.getSlug().equals(Artisan.SLUG_JEWELER))
				((TextView) view.findViewById(R.id.jeweler_hardcore_level))
						.setText(getString(
								R.string.artisan_level_hardcore,
								artisan.getLevel()));
		}
		return view;
	}

}
