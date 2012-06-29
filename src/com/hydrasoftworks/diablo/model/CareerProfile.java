package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CareerProfile {
	private static final String CAREER_PROFILE_URL = "http://eu.battle.net/api/d3/account/";
	private static HashMap<String, CareerProfile> downloadedProfiles = new HashMap<String, CareerProfile>();
	private static CareerProfile activeProfile;

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
	private HashMap<Integer, Hero> downloadedHeroes = new HashMap<Integer, Hero>();
	private Hero activeHero;

	public static URL createUrl(BattleTag tag) throws MalformedURLException {
		return new URL(CAREER_PROFILE_URL
				+ tag.getBattleTag().replace("#", "-"));
	}

	public static CareerProfile getDownloadedProfile(String tag) {
		CareerProfile.activeProfile = CareerProfile.downloadedProfiles.get(tag);
		return CareerProfile.activeProfile;
	}

	public static CareerProfile getActiveProfile() {
		return CareerProfile.activeProfile;
	}

	public static boolean hasDownloadedProfile(String tag) {
		return CareerProfile.downloadedProfiles.containsKey(tag);
	}

	public void addToDownloadedProfiles(BattleTag battleTag) {
		this.battleTag = battleTag;
		CareerProfile.downloadedProfiles.put(battleTag.getBattleTag(), this);
	}

	public static class Kills {
		private int monsters;
		private int elites;
		@SerializedName("hardcoreMonsters")
		private int hardcoreMonsters;

		/**
		 * @return the monsters
		 */
		public int getMonsters() {
			return monsters;
		}

		/**
		 * @return the elites
		 */
		public int getElites() {
			return elites;
		}

		/**
		 * @return the hardcoreMonsters
		 */
		public int getHardcoreMonsters() {
			return hardcoreMonsters;
		}
	}

	public static class TimePlayed { // TODO: Showing plaing time
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

			return Integer.valueOf(thisValue).compareTo(
					Integer.valueOf(anotherValue));
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

	/**
	 * @return the kills
	 */
	public Kills getKills() {
		return kills;
	}

	/**
	 * @return the fallenHeroes
	 */
	public List<Hero> getFallenHeroes() {
		return fallenHeroes;
	}

	public boolean hasDownloadedHero(int id) {
		return downloadedHeroes.containsKey(id);
	}

	public Hero getDownloadedHero(int id) {
		activeHero = downloadedHeroes.get(id);
		return activeHero;
	}

	public void addToDownloadedHeroes(Hero fullHero) {
		downloadedHeroes.put(fullHero.getId(), fullHero);

	}

	public Hero getActiveHero() {
		return activeHero;
	}

	public int getProgressValue() {
		Collections.sort(getProgression(), Collections.reverseOrder());
		Progression progression = getProgression().get(0);
		int progressionValue = progression.getAct();
		for (int i = 0; i < Progression.LEVELS.length; i++) {
			if (progression.getDifficulty().equals(Progression.LEVELS[i])) {
				progressionValue += i * Progression.LEVELS.length;
			}
		}
		return progressionValue;
	}

}
