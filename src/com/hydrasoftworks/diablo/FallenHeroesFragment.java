package com.hydrasoftworks.diablo;

import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		List<Hero> fallenHeroes = CareerProfile.getActiveProfile()
				.getFallenHeroes();
		Collections.sort(fallenHeroes, Collections.reverseOrder());
		ListView listview = (ListView) view
				.findViewById(R.id.fallen_heroes_listview);
		listview.setAdapter(new HeroesAdapter(getActivity(),
				R.layout.heroes_row, fallenHeroes.toArray(new Hero[fallenHeroes
						.size()])));

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Hero hero = (Hero) parent.getAdapter().getItem(position);
				CareerProfile.getActiveProfile().addToDownloadedHeroes(hero);
				Intent intent = getActivity().getIntent().setClass(
						getActivity(), HeroFragmentActivity.class);
				intent.putExtra(Hero.HERO_ID, hero.getId());
				intent.putExtra(CareerProfileFragmentActivity.ACTIVE_TAB,
						getSherlockActivity().getSupportActionBar()
								.getSelectedNavigationIndex());
				startActivity(intent);

			}
		});
		return view;
	}

}
