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
				"" + stats.getArmor(), "" + stats.getDamage(), "" };
		((TextView) view.findViewById(R.id.attributes_values)).setText(Html
				.fromHtml(StringUtils.join(attr, "<br>")));

		((TextView) view.findViewById(R.id.offense_description)).setText(Html
				.fromHtml(getString(R.string.offense_description)));
		String[] offense = {
				"" + roundTwoDecimals(stats.getDamageIncrease() * 100) + "%",
				"" + roundTwoDecimals(stats.getAttackSpeed()),
				"" + roundTwoDecimals(stats.getCritChance() * 100) + "%",
				"" + roundTwoDecimals(stats.getCritDamage() * 100) + "%" };
		((TextView) view.findViewById(R.id.offense_values)).setText(Html
				.fromHtml(StringUtils.join(offense, "<br>")));

		((TextView) view.findViewById(R.id.defense_description)).setText(Html
				.fromHtml(getString(R.string.defense_description)));
		String[] defens = { "" + stats.getBlockAmountMin(),
				"" + stats.getBlockAmountMax(),
				"" + roundTwoDecimals(stats.getBlockChance() * 100) + "%",
				"" + roundTwoDecimals(stats.getDamageReduction() * 100) + "%",
				"" + stats.getPhysicalResist(), "" + stats.getColdResist(),
				"" + stats.getFireResist(), "" + stats.getLightningResist(),
				"" + stats.getPoisonResist(), "" + stats.getArcaneResist(),
				"" + stats.getThorns() };
		((TextView) view.findViewById(R.id.defense_values)).setText(Html
				.fromHtml(StringUtils.join(defens, "<br>")));

		((TextView) view.findViewById(R.id.life_description)).setText(Html
				.fromHtml(getString(R.string.life_description)));
		String[] life = { "" + stats.getLife(),
				"" + roundTwoDecimals(stats.getLifeSteal() * 100) + "%",
				"" + stats.getLifePerKill(), "" + stats.getLifeOnHit() };
		((TextView) view.findViewById(R.id.life_values)).setText(Html
				.fromHtml(StringUtils.join(life, "<br>")));

		((TextView) view.findViewById(R.id.resource_description)).setText(Html
				.fromHtml(getString(R.string.resource_description)));
		String[] resource = { "" + stats.getPrimaryResource(),
				"" + stats.getSecondaryResource() };
		((TextView) view.findViewById(R.id.resource_values)).setText(Html
				.fromHtml(StringUtils.join(resource, "<br>")));

		((TextView) view.findViewById(R.id.adventure_description)).setText(Html
				.fromHtml(getString(R.string.adventure_description)));
		String[] adventure = {
				"" + roundTwoDecimals(stats.getGoldFind() * 100) + "%",
				"" + roundTwoDecimals(stats.getMagicFind() * 100) + "%" };
		((TextView) view.findViewById(R.id.adventure_values)).setText(Html
				.fromHtml(StringUtils.join(adventure, "<br>")));
		return view;
	}

	private String roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("0.00");
		return twoDForm.format(d);
	}
}
