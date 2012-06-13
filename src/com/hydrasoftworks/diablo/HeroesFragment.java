package com.hydrasoftworks.diablo;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Hero;

public class HeroesFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.heroes_fragment, container, false);
		List<Hero> heroes = CareerProfile.getActiveElement().getHeroes();
		Collections.sort(heroes, Collections.reverseOrder());
		((ListView) view.findViewById(R.id.heroes_listview))
				.setAdapter(new HeroesAdapter(getActivity(),
						R.layout.heroes_row, heroes.toArray(new Hero[heroes
								.size()])));
		return view;
	}

	class HeroesAdapter extends ArrayAdapter<Hero> {

		public HeroesAdapter(Context context, int textViewResourceId,
				Hero[] objects) {
			super(context, textViewResourceId, objects);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row;
			if (convertView == null) {
				row = ((LayoutInflater) getContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE)).inflate(
						R.layout.heroes_row, parent, false);
			} else {
				row = convertView;
			}

			Hero hero = getItem(position);
			ImageView heroImage = (ImageView) row.findViewById(R.id.hero_image);
			if (hero.getHeroClass().equals(Hero.CLASS_BARBARIAN)) {
				if (hero.getGender() == 0) {
					heroImage.setImageResource(R.drawable.barbarian_male);
				} else {
					heroImage.setImageResource(R.drawable.barbarian_female);
				}
			} else if (hero.getHeroClass().equals(Hero.CLASS_DEMON_HUNTER)) {
				if (hero.getGender() == 0) {
					heroImage.setImageResource(R.drawable.demonhunter_male);
				} else {
					heroImage.setImageResource(R.drawable.demonhunter_female);
				}
			} else if (hero.getHeroClass().equals(Hero.CLASS_MONK)) {
				if (hero.getGender() == 0) {
					heroImage.setImageResource(R.drawable.monk_male);
				} else {
					heroImage.setImageResource(R.drawable.monk_female);
				}
			} else if (hero.getHeroClass().equals(Hero.CLASS_WITCH_DOCTOR)) {
				if (hero.getGender() == 0) {
					heroImage.setImageResource(R.drawable.witchdoctor_male);
				} else {
					heroImage.setImageResource(R.drawable.witchdoctor_female);
				}
			} else if (hero.getHeroClass().equals(Hero.CLASS_WIZARD)) {
				if (hero.getGender() == 0) {
					heroImage.setImageResource(R.drawable.wizard_male);
				} else {
					heroImage.setImageResource(R.drawable.wizard_female);
				}
			}
			TextView heroName = (TextView) row.findViewById(R.id.hero_name);
			heroName.setText(hero.getName());
			TextView heroClass = (TextView) row.findViewById(R.id.hero_class);
			heroClass.setText(hero.getLevel()
					+ " "
					+ WordUtils.capitalize(hero.getHeroClass()
							.replace("-", " ")));
			if(hero.isHardcore()) {
				heroClass.setTextColor(Color.RED);
				heroImage.setBackgroundResource(R.drawable.imageview_red_shape);
			}
			return row;
		}

	}

}
