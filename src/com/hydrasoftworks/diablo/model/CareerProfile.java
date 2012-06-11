package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CareerProfile {
	private static final String CAREER_PROFILE_URL = "http://eu.battle.net/api/d3/account/";
	private static HashMap<String, CareerProfile> elements = new HashMap<String, CareerProfile>();

	private BattleTag battleTag;
	private int lastHeroPlayed;
	private int lastUpdated;
	private List<Artisan> artisans;
	private List<Artisan> hardcoreArtisans;
	private List<Hero> heroes;
	private List<Hero> fallenHeroes;
	private Kills kills;
	private TimePlayed timePlayed;
	private List<Progression> progression;

	public static URL createUrl(BattleTag tag) throws MalformedURLException {
		return new URL(CAREER_PROFILE_URL
				+ tag.getBattleTag().replace("#", "-"));
	}

	public static CareerProfile getElement(String tag) {
		return CareerProfile.elements.get(tag);
	}
	

	public void addToElements(BattleTag battleTag) {
		this.battleTag = battleTag;
		CareerProfile.elements.put(battleTag.getBattleTag(), this);
	}
	
	public static class Kills {
		private int monsters;
		private int elites;
		@SerializedName("hardcoreMonsters")
		private int hardcoreMonsters;
	}

	public static class TimePlayed {
		private double barbarian;
		private double demonHunter;
		private double monk;
		private double witchDoctor;
		private double wizard;
	}

	public static class Progression {
		private int act;
		private String difficulty;
	}

	public BattleTag getBattleTag() {
		return battleTag;
	}

}
