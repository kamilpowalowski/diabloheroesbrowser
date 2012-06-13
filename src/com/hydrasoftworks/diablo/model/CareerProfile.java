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
	private static CareerProfile activeElement;

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
		CareerProfile.activeElement = CareerProfile.elements.get(tag);
		return CareerProfile.activeElement;
	}

	public static CareerProfile getActiveElement() {
		return CareerProfile.activeElement;
	}

	public static boolean hasElement(String tag) {
		return CareerProfile.elements.containsKey(tag);
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

	public static class Progression implements Comparable<Progression> {
		public static final String[] LEVELS = { Progression.NORMAL,
				Progression.NIGHTMARE, Progression.HELL, Progression.INFERNO };
		public static final String NORMAL = "normal";
		public static final String NIGHTMARE = "nightmare";
		public static final String HELL = "hell";
		public static final String INFERNO = "inferno";
		private int act;
		private String difficulty;

		/**
		 * @return the act
		 */
		public int getAct() {
			return act;
		}

		/**
		 * @return the difficulty
		 */
		public String getDifficulty() {
			return difficulty;
		}

		@Override
		public int compareTo(Progression another) {

			int thisValue = act;
			int anotherValue = another.act;
			for (int i = 0; i < Progression.LEVELS.length; i++) {
				if (difficulty.equals(Progression.LEVELS[i]))
					thisValue += 10 * i;
				if (another.difficulty.equals(Progression.LEVELS[i]))
					anotherValue += 10 * i;
			}
			return new Integer(thisValue).compareTo(new Integer(anotherValue));
		}
	}

	public BattleTag getBattleTag() {
		return battleTag;
	}

	/**
	 * @return the progression
	 */
	public List<Progression> getProgression() {
		return progression;
	}
	
	/**
	 * @return the heroes
	 */
	public List<Hero> getHeroes() {
		return heroes;
	}

	/**
	 * @return the artisans
	 */
	public List<Artisan> getArtisans() {
		return artisans;
	}

	/**
	 * @return the hardcoreArtisans
	 */
	public List<Artisan> getHardcoreArtisans() {
		return hardcoreArtisans;
	}

}
