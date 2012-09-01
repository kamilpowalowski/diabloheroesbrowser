package com.hydrasoftworks.diablo;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Hero;
import com.hydrasoftworks.diablo.model.Hero.Stats;

public class HeroStatisticFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Hero hero = CareerProfile.getActiveProfile().getActiveHero();
		Stats stats = hero.getStats();

		View view = inflater.inflate(R.layout.hero_statistic_fragment,
				container, false);
		((TextView) view.findViewById(R.id.attributes_description))
				.setText(Html
						.fromHtml(getString(R.string.attributes_description)));

		String[] attr = { "" + stats.getStrength(), "" + stats.getDexterity(),
				"" + stats.getIntelligence(), "" + stats.getVitality(),
				"" + stats.getArmor(), "" + stats.getLife(),
				"" + stats.getDamage(), "",
				"" + roundTwoDecimals(stats.getDamageIncrease() * 100) + "%",
				"" + roundTwoDecimals(stats.getDamageReduction() * 100) + "%",
				"" + roundTwoDecimals(stats.getCritChance() * 100) + "%" };
		((TextView) view.findViewById(R.id.attributes_values)).setText(Html
				.fromHtml(StringUtils.join(attr, "<br>")));

		((TextView) view.findViewById(R.id.defensive_description)).setText(Html
				.fromHtml(getString(R.string.defensive_description)));
		String[] defens = { "" + stats.getColdResist(),
				"" + stats.getFireResist(), "" + stats.getLightningResist(),
				"" + stats.getPoisonResist(), "" + stats.getArcaneResist() };
		((TextView) view.findViewById(R.id.defensive_values)).setText(Html
				.fromHtml(StringUtils.join(defens, "<br>")));
		return view;
	}

	private String roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return twoDForm.format(d);
	}
}
