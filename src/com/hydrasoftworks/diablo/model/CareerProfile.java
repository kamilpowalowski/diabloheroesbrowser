package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hydrasoftworks.diablo.model.exceptions.CareerProfileParsingException;

public class CareerProfile {
	private static final String CAREER_PROFILE_URL = "http://eu.battle.net/api/d3/account/";
	private String account;
	private ArrayList<Hero> heroes;
	private ArrayList<Artisan> artisans;
	private HashMap<String, Integer> kills;
	private HashMap<Integer, Integer> progression;
	private HashMap<String, Double> timePlayed;
	
	private CareerProfile() {
		heroes = new ArrayList<Hero>();
		artisans = new ArrayList<Artisan>();
		kills = new HashMap<String, Integer>();
		progression = new HashMap<Integer, Integer>();
		timePlayed = new HashMap<String, Double>();
	}
	
	public static CareerProfile parseCareerProfile(String json) throws CareerProfileParsingException {
		CareerProfile profile = new CareerProfile();
		try {
			JSONObject jsonObject = new JSONObject(json);
			profile.account = jsonObject.getString("account");
			JSONArray jsonArray = jsonObject.getJSONArray("heroes");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonHero = jsonArray.getJSONObject(i);
				Hero hero = new Hero();
				hero.setId(jsonHero.getInt("id"));
				hero.setName(jsonHero.getString("name"));
				hero.setLevel(jsonHero.getInt("level"));
				hero.setClass(jsonHero.getString("class"));
				hero.setIsHardcore(jsonHero.getBoolean("hardcore"));
				profile.heroes.add(hero);
			}
			
			jsonArray = jsonObject.getJSONArray("artisan");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonArtisan = jsonArray.getJSONObject(i);
				Artisan artisan = new Artisan();
				artisan.setSlug(jsonArtisan.getString("slug"));
				artisan.setLevel(jsonArtisan.getInt("level"));
				artisan.setStepCurrent(jsonArtisan.getJSONObject("step").getInt("current"));
				artisan.setStepMax(jsonArtisan.getJSONObject("step").getInt("max"));
				profile.artisans.add(artisan);
			}
			jsonArray = jsonObject.getJSONArray("progression");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonProg = jsonArray.getJSONObject(i);
				profile.progression.put(jsonProg.getInt("act"), jsonProg.getInt("difficulty"));
			}
			
			JSONObject jsonKills = jsonObject.getJSONObject("kills");
			profile.kills.put("monsters", jsonKills.getInt("monsters"));
			profile.kills.put("elites", jsonKills.getInt("elites"));
			profile.kills.put("hardcore_monsters", jsonKills.getInt("hardcore_monsters"));

			JSONObject jsonTime = jsonObject.getJSONObject("time-played");
			profile.timePlayed.put(Hero.CLASS_BARBARIAN, jsonTime.getDouble(Hero.CLASS_BARBARIAN));
			profile.timePlayed.put(Hero.CLASS_DEMON_HUNTER, jsonTime.getDouble(Hero.CLASS_DEMON_HUNTER));
			profile.timePlayed.put(Hero.CLASS_MONK, jsonTime.getDouble(Hero.CLASS_MONK));
			profile.timePlayed.put(Hero.CLASS_WITCH_DOCTOR, jsonTime.getDouble(Hero.CLASS_WITCH_DOCTOR));
			profile.timePlayed.put(Hero.CLASS_WIZARD, jsonTime.getDouble(Hero.CLASS_WIZARD));
		} catch (JSONException e) {
			throw new CareerProfileParsingException(e.getMessage());
		}
		return profile;
	}
	
	public static URL createUrl(BattleTag tag) throws MalformedURLException {
		return new URL(CAREER_PROFILE_URL + tag.getBattleTag().replace("#", "/"));
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	
}
