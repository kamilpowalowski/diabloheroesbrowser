package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CareerProfile {
	private static final String CAREER_PROFILE_URL = "http://eu.battle.net/api/d3/account/";

	private int lastHeroPlayed;
	private int lastUpdated;
	private List<Artisan> artisans;
	private List<Artisan> hardcoreArtisans;
	private Kills kills;
	private TimePlayed timePlayed;
	
	public static URL createUrl(BattleTag tag) throws MalformedURLException {
		return new URL(CAREER_PROFILE_URL
				+ tag.getBattleTag().replace("#", "-"));
	}

	public static class Kills {
		private int monsters;
		private int elites;
		private int hardcoreMonsters; // FIXME: hardcoreMonsters has diferent
										// codding
	}

	public static class TimePlayed {
		private double barbarian;
		private double demonHunter;
		private double monk;
		private double witchDoctor;
		private double wizard;
	}

}
