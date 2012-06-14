package com.hydrasoftworks.diablo;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.adapters.HeroesAdapter;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Hero;

public class FallenHeroesFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fallen_heroes_fragment,
				container, false);
		List<Hero> fallenHeroes = CareerProfile.getActiveElement()
				.getFallenHeroes();
		Collections.sort(fallenHeroes, Collections.reverseOrder());
		((ListView) view.findViewById(R.id.fallen_heroes_listview))
				.setAdapter(new HeroesAdapter(getActivity(),
						R.layout.heroes_row, fallenHeroes
								.toArray(new Hero[fallenHeroes.size()])));
		return view;
	}

}
