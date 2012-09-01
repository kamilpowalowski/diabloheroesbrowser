package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CareerProfile {
	private static final String HOST = "http://{region}.battle.net/";
	private static final String CAREER_PROFILE_URL = HOST + "api/d3/profile/";
	private static HashMap<String, CareerProfile> downloadedProfiles = new HashMap<String, CareerProfile>();
	private static CareerProfile activeProfile;

	private BattleTag playerBattleTag;
	private String battleTag;
	private int lastHeroPlayed;
	@SerializedName("last-updated")
	private int lastUpdated; //TODO: dosn't work
	private List<Artisan> artisans;
	private List<Artisan> hardcoreArtisans;
	private List<Hero> heroes;
	private List<Hero> fallenHeroes;
	private Kills kills;
	private TimePlayed timePlayed;
	private HashMap<String, HashMap<String, Act>> progression;
	private HashMap<String, HashMap<String, Act>> hardcoreProgression;
	private HashMap<Integer, Hero> downloadedHeroes = new HashMap<Integer, Hero>();
	private Hero activeHero;

	public static URL createUrl(BattleTag tag) throws MalformedURLException {
		return new URL(CAREER_PROFILE_URL.replace("{region}", tag.getServer())
				+ tag.getBattleTagText().replace("#", "-") + "/");
	}

	public static CareerProfile getDownloadedProfile(BattleTag battleTag) {
		CareerProfile.activeProfile = CareerProfile.downloadedProfiles
				.get(battleTag.getBattleTagText() + battleTag.getServer());
		return CareerProfile.activeProfile;
	}

	public static CareerProfile getActiveProfile() {
		return CareerProfile.activeProfile;
	}

	public static boolean hasDownloadedProfile(BattleTag battleTag) {
		return CareerProfile.downloadedProfiles.containsKey(battleTag
				.getBattleTagText() + battleTag.getServer());
	}

	public String getHost() {
		return HOST.replace("{region}", getBattleTag().getServer());
	}

	public void addToDownloadedProfiles(BattleTag battleTag) {
		this.playerBattleTag = battleTag;
		CareerProfile.downloadedProfiles.put(battleTag.getBattleTagText()
				+ battleTag.getServer(), this);
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

	public static class Progress {
		public static final String[] LEVELS = { Progress.NORMAL,
				Progress.NIGHTMARE, Progress.HELL, Progress.INFERNO };
		public static final String NORMAL = "normal";
		public static final String NIGHTMARE = "nightmare";
		public static final String HELL = "hell";
		public static final String INFERNO = "inferno";
	}

	public BattleTag getBattleTag() {
		return playerBattleTag;
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
		int value = 0;
		for (int i = 0; i < Progress.LEVELS.length; i++) {
			HashMap<String, Act> acts = progression.get(Progress.LEVELS[i]);
			for (int j = 0; j < Act.ACTS.length; j++) {
				Act act = acts.get(Act.ACTS[j]);
				if (act.isCompleted())
					value++;
				else
					return value;
			}
		}
		return value;
	}
	
	public int getHardcoreProgressValue() {
		int value = 0;
		for (int i = 0; i < Progress.LEVELS.length; i++) {
			HashMap<String, Act> acts = hardcoreProgression.get(Progress.LEVELS[i]);
			for (int j = 0; j < Act.ACTS.length; j++) {
				Act act = acts.get(Act.ACTS[j]);
				if (act.isCompleted())
					value++;
				else
					return value;
			}
		}
		return value;
	}

	public static CareerProfile getDownloadedProfile(String tag, String region) {
		CareerProfile.activeProfile = CareerProfile.downloadedProfiles
				.get(tag + region);
		return CareerProfile.activeProfile;
	}

	public String getBattleTagText() {
		return battleTag;
	}


}
