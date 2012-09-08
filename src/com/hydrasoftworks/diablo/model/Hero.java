package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.hydrasoftworks.diablo.model.Act.Quest;
import com.hydrasoftworks.diablo.model.CareerProfile.Progress;

public class Hero implements Comparable<Hero> {
	private static final String HERO_PROFILE_URL = "hero/";
	public static final String HERO_ID = "hero_id";
	public static final String CLASS_BARBARIAN = "barbarian";
	public static final String CLASS_DEMON_HUNTER = "demon-hunter";
	public static final String CLASS_MONK = "monk";
	public static final String CLASS_WITCH_DOCTOR = "witch-doctor";
	public static final String CLASS_WIZARD = "wizard";

	private String name;
	private int id = 0;
	private int heroId = 0;
	private int level;
	private int paragonLevel;
	private int gender;
	@SerializedName("class")
	private String heroClass;
	private boolean hardcore;
	@SerializedName("last-updated")
	private int lastUpdated;
	private HashMap<String, Item> items;
	private Stats stats;
	private Kills kills;
	private Death death;
	private HashMap<String, List<Skill>> skills;
	private HashMap<String, Follower> followers;
	private HashMap<String, HashMap<String, Act>> progress;

	public URL createUrl(BattleTag tag) throws MalformedURLException {
		return new URL(CareerProfile.createUrl(tag).toExternalForm()
				+ HERO_PROFILE_URL + getId());
	}

	public static class Stats {
		private double damageIncrease;
		private double damageReduction;
		private double attackSpeed;
		private double critChance;
		private int life;
		private int strength;
		private int dexterity;
		private int intelligence;
		private int vitality;
		private int armor;
		private int physicalResist;
		private int coldResist;
		private int fireResist;
		private int lightningResist;
		private int poisonResist;
		private int arcaneResist;
		private double damage;
		private double critDamage;
		private double blockChance;
		private double thorns;
		private double lifeSteal;
		private double lifePerKill;
		private double goldFind;
		private double magicFind;
		private int blockAmountMin;
		private int blockAmountMax;
		private double lifeOnHit;
		private int primaryResource;
		private int secondaryResource;

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

		/**
		 * @return the critDamage
		 */
		public double getCritDamage() {
			return critDamage;
		}

		/**
		 * @return the blockChance
		 */
		public double getBlockChance() {
			return blockChance;
		}

		/**
		 * @return the thorns
		 */
		public double getThorns() {
			return thorns;
		}

		/**
		 * @return the lifeSteal
		 */
		public double getLifeSteal() {
			return lifeSteal;
		}

		/**
		 * @return the lifePerKill
		 */
		public double getLifePerKill() {
			return lifePerKill;
		}

		/**
		 * @return the goldFind
		 */
		public double getGoldFind() {
			return goldFind;
		}

		/**
		 * @return the magicFind
		 */
		public double getMagicFind() {
			return magicFind;
		}

		/**
		 * @return the blockAmountMin
		 */
		public int getBlockAmountMin() {
			return blockAmountMin;
		}

		/**
		 * @return the blockAmountMax
		 */
		public int getBlockAmountMax() {
			return blockAmountMax;
		}

		/**
		 * @return the lifeOnHit
		 */
		public double getLifeOnHit() {
			return lifeOnHit;
		}

		/**
		 * @return the primaryResource
		 */
		public int getPrimaryResource() {
			return primaryResource;
		}

		/**
		 * @return the secondaryResource
		 */
		public int getSecondaryResource() {
			return secondaryResource;
		}

		/**
		 * @return the attackSpeed
		 */
		public double getAttackSpeed() {
			return attackSpeed;
		}

		/**
		 * @return the physicalResist
		 */
		public int getPhysicalResist() {
			return physicalResist;
		}
	}

	public static class Kills {
		private int monsters;
		private int elites;

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
	}

	public static class Death {
		private String location;
		private String killer;
		private int time;

		/**
		 * @return the location
		 */
		public String getLocation() {
			return location;
		}

		/**
		 * @return the killer
		 */
		public String getKiller() {
			return killer;
		}
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
			return Integer.valueOf(getLevel()).compareTo(
					Integer.valueOf(another.getLevel()));
		return Integer.valueOf(getLastUpdated()).compareTo(
				Integer.valueOf(another.getLastUpdated()));
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
	public List<Skill> getSkills(String typeOfSkills) {
		return skills.get(typeOfSkills);
	}

	/**
	 * @return the death
	 */
	public Death getDeath() {
		return death;
	}

	public boolean isFallen() {
		return death != null;
	}

	public Follower getFollower(String name) {
		return followers.get(name);
	}

	public int getProgressValue() {
		int value = Progress.LEVELS.length * Act.ACTS.length;
		for (int i = Progress.LEVELS.length - 1; i >= 0; i--) {
			HashMap<String, Act> acts = progress.get(Progress.LEVELS[i]);
			for (int j = Act.ACTS.length - 1; j >= 0; j--) {
				if (!acts.get(Act.ACTS[j]).isCompleted())
					value--;
				else
					return value;
			}
		}
		return value;
	}

	public Quest getLastFinishedQuest() {
		for (int i = Progress.LEVELS.length - 1; i >= 0; i--) {
			HashMap<String, Act> acts = progress.get(Progress.LEVELS[i]);
			for (int j = Act.ACTS.length - 1; j >= 0; j--) {
				Act act = acts.get(Act.ACTS[j]);
				List<Quest> quests = act.getCompletedQuests();
				if (quests.size() != 0)
					return quests.get(quests.size() - 1);
			}
		}
		return null;
	}

	/**
	 * @return the kills
	 */
	public Kills getKills() {
		return kills;
	}

	/**
	 * @return the lastUpdated
	 */
	public int getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @return the paragonLevel
	 */
	public int getParagonLevel() {
		return paragonLevel;
	}
}
