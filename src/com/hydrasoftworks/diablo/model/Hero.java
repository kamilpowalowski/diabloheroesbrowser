package com.hydrasoftworks.diablo.model;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Hero {
	private static final String HERO_PROFILE_URL = "http://eu.battle.net/api/d3/hero/";
	public static final String CLASS_BARBARIAN = "barbarian";
	public static final String CLASS_DEMON_HUNTER = "demon-hunter";
	public static final String CLASS_MONK = "monk";
	public static final String CLASS_WITCH_DOCTOR = "witch-doctor";
	public static final String CLASS_WIZARD = "wizard";

	private String name;
	private int id;
	@SerializedName("heroId")
	private int heroId;
	private int level;
	private int gender;
	@SerializedName("class")
	private String heroClass;
	private boolean hardcore;
	private int lastUpdated;
	private HashMap<String, Item> items;
	private Stats stats;
	private Kills kills;
	private Death death;

	public static class Stats {
		private double damageIncrease;
		private double damageReduction;
		private double critChance;
		private int life;
		private int strength;
		private int dexterity;
		private int intelligence;
		private int vitality;
		private int armor;
		private int coldResist;
		private int fireResist;
		private int lightningResist;
		private int poisonResist;
		private int arcaneResist;
		private double damage;
	}

	public static class Kills {
		private int monsters;
		private int elites;
	}

	public static class Death {
		private String location;
		private String killer;
		private int time;
	}

}
