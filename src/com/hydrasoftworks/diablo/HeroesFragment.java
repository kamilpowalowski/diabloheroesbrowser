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

public class HeroesFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.heroes_fragment, container, false);
		List<Hero> heroes = CareerProfile.getActiveProfile().getHeroes();
		Collections.sort(heroes, Collections.reverseOrder());
		ListView listview = (ListView) view.findViewById(R.id.heroes_listview);
		listview.setAdapter(new HeroesAdapter(getActivity(),
				R.layout.heroes_row, heroes.toArray(new Hero[heroes.size()])));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Hero hero = (Hero)parent.getAdapter().getItem(position);
				Intent intent = getActivity().getIntent().setClass(getActivity(), HeroFragmentActivity.class);
				intent.putExtra(Hero.HERO_ID, hero.getId());
				startActivity(intent);

			}
		});
		return view;
	}

}
