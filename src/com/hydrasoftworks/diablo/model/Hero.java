package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

public class Hero implements Comparable<Hero> {
	private static final String HERO_PROFILE_URL = "/hero/";
	public static final String HERO_ID = "hero_id";
	public static final String CLASS_BARBARIAN = "barbarian";
	public static final String CLASS_DEMON_HUNTER = "demon-hunter";
	public static final String CLASS_MONK = "monk";
	public static final String CLASS_WITCH_DOCTOR = "witch-doctor";
	public static final String CLASS_WIZARD = "wizard";

	private String name;
	private int id = 0;
	@SerializedName("heroId")
	private int heroId = 0;
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
	private HashMap<String, List<Skill>> skills;

	public URL createUrl(BattleTag tag) throws MalformedURLException {
		return new URL(CareerProfile.createUrl(tag).toExternalForm() + HERO_PROFILE_URL + getId());
	}
	
	public static class Stats {
		@SerializedName("damageIncrease")
		private double damageIncrease;
		@SerializedName("damageReduction")
		private double damageReduction;
		@SerializedName("critChance")
		private double critChance;
		private int life;
		private int strength;
		private int dexterity;
		private int intelligence;
		private int vitality;
		private int armor;
		@SerializedName("coldResist")
		private int coldResist;
		@SerializedName("fireResist")
		private int fireResist;
		@SerializedName("lightningResist")
		private int lightningResist;
		@SerializedName("poisonResist")
		private int poisonResist;
		@SerializedName("arcaneResist")
		private int arcaneResist;
		private double damage;
		/**
		 * @return the damageIncrease
		 */
		public double getDamageIncrease() {
			return damageIncrease;
		}
		/**
		 * @return the damageReduction
		 */
		public double getDamageReduction() {
			return damageReduction;
		}
		/**
		 * @return the critChance
		 */
		public double getCritChance() {
			return critChance;
		}
		/**
		 * @return the life
		 */
		public int getLife() {
			return life;
		}
		/**
		 * @return the strength
		 */
		public int getStrength() {
			return strength;
		}
		/**
		 * @return the dexterity
		 */
		public int getDexterity() {
			return dexterity;
		}
		/**
		 * @return the intelligence
		 */
		public int getIntelligence() {
			return intelligence;
		}
		/**
		 * @return the vitality
		 */
		public int getVitality() {
			return vitality;
		}
		/**
		 * @return the armor
		 */
		public int getArmor() {
			return armor;
		}
		/**
		 * @return the coldResist
		 */
		public int getColdResist() {
			return coldResist;
		}
		/**
		 * @return the fireResist
		 */
		public int getFireResist() {
			return fireResist;
		}
		/**
		 * @return the lightningResist
		 */
		public int getLightningResist() {
			return lightningResist;
		}
		/**
		 * @return the poisonResist
		 */
		public int getPoisonResist() {
			return poisonResist;
		}
		/**
		 * @return the arcaneResist
		 */
		public int getArcaneResist() {
			return arcaneResist;
		}
		/**
		 * @return the damage
		 */
		public double getDamage() {
			return damage;
		}
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

	/**
	 * @return the heroClass
	 */
	public String getHeroClass() {
		return heroClass;
	}

	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	@Override
	public int compareTo(Hero another) {
		if (getLevel() != another.getLevel())
			return new Integer(getLevel()).compareTo(new Integer(another
					.getLevel()));
		return new Integer(lastUpdated).compareTo(new Integer(lastUpdated));
	}

	/**
	 * @return the hardcore
	 */
	public boolean isHardcore() {
		return hardcore;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id + heroId;
	}
	
	public Item getItem(String partOfBody) {
		return items.get(partOfBody);
	}

	/**
	 * @return the stats
	 */
	public Stats getStats() {
		return stats;
	}

	/**
	 * @return the skills
	 */
	public HashMap<String, List<Skill>> getSkills() {
		return skills;
	}

}
