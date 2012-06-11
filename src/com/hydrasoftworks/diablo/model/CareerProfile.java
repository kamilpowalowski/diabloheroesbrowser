package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CareerProfile {
	private static final String CAREER_PROFILE_URL = "http://eu.battle.net/api/d3/account/";

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

}
