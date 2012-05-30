package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hydrasoftworks.diablo.model.exceptions.HeroParsingException;

public class Hero {
	private static final String HERO_PROFILE_URL = "http://eu.battle.net/api/d3/hero/";
	public static final String CLASS_BARBARIAN = "barbarian";
	public static final String CLASS_DEMON_HUNTER = "demon-hunter";
	public static final String CLASS_MONK = "monk";
	public static final String CLASS_WITCH_DOCTOR = "witch-doctor";
	public static final String CLASS_WIZARD = "wizard";
	
	private int id;
	private String name;
	private int level;
	private String heroClass;
	private boolean isHardcore;
	private int gender;
	private int createTime;
	private int updateTime;
	private Vector<Hireling> hirelings;
	private Vector<Skill> activeSkills;
	private Vector<Skill> passiveSkills;
	private int elitesKilled;
	private Attributes attributes;
	
	public Hero(){
		hirelings = new Vector<Hireling>();
		activeSkills = new Vector<Skill>();
		passiveSkills = new Vector<Skill>();
	}

	public URL createUrl() throws MalformedURLException {
		return new URL(HERO_PROFILE_URL + getId());
	}
	
	public void parseHero(String json) throws HeroParsingException {
		try {
			JSONObject jsonObject = new JSONObject(json);
			gender = jsonObject.getInt("gender");
			createTime = jsonObject.getInt("create_time");
			updateTime = jsonObject.getInt("update_time");
			JSONArray jsonArray = jsonObject.getJSONArray("hireling");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonHireling = jsonArray.getJSONObject(i);
				Hireling hireling = new Hireling();
				hireling.setClass(jsonHireling.getInt("hireling_class"));
				hireling.setLevel(jsonHireling.getInt("level"));
				hireling.setIsActive(jsonHireling.optBoolean("active"));
				hirelings.add(hireling);
			}
			
			jsonArray = jsonObject.getJSONObject("skills").getJSONArray("active");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonSkill = jsonArray.getJSONObject(i);
				Skill skill = new Skill();
				skill.setSlug(jsonSkill.getString("slug"));
				skill.setName(jsonSkill.getString("name"));
				skill.setIcon(jsonSkill.getString("icon"));
				JSONObject jsonRune = jsonSkill.optJSONObject("rune");
				if(jsonRune != null) {
					skill.setRuneType(jsonRune.getInt("type"));
					skill.setRuneName(jsonRune.getString("name"));
				}
				activeSkills.add(skill);
			}
			
			jsonArray = jsonObject.getJSONObject("skills").getJSONArray("passive");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonSkill = jsonArray.getJSONObject(i);
				Skill skill = new Skill();
				skill.setSlug(jsonSkill.getString("slug"));
				skill.setName(jsonSkill.getString("name"));
				skill.setIcon(jsonSkill.getString("icon"));
				JSONObject jsonRune = jsonSkill.optJSONObject("rune");
				if(jsonRune != null) {
					skill.setRuneType(jsonRune.getInt("type"));
					skill.setRuneName(jsonRune.getString("name"));
				}
				passiveSkills.add(skill);
			}
			
			elitesKilled = jsonObject.getInt("elites_killed");
			
			jsonObject = jsonObject.getJSONObject("attributes");
			attributes = new Attributes();
			attributes.setLife(jsonObject.getInt("life"));
			attributes.setDps(jsonObject.getDouble("dps"));
			attributes.setArmor(jsonObject.getInt("armor"));
			attributes.setStrength(jsonObject.getInt("strength"));
			attributes.setDexterity(jsonObject.getInt("dexterity"));
			attributes.setIntelligence(jsonObject.getInt("intelligence"));
			attributes.setVitality(jsonObject.getInt("vitality"));
			attributes.setResistArcane(jsonObject.getInt("resist_arcane"));
			attributes.setResistFire(jsonObject.getInt("resist_fire"));
			attributes.setResistLightning(jsonObject.getInt("resist_lightning"));
			attributes.setResistPoison(jsonObject.getInt("resist_poison"));
			attributes.setResistCold(jsonObject.getInt("resist_cold"));
			attributes.setCritChance(jsonObject.getDouble("crit_chance"));
			attributes.setDamageReduction(jsonObject.getDouble("damage_reduction"));
			attributes.setMagicFind(jsonObject.getInt("magic-find"));
			attributes.setMagicFind(jsonObject.getInt("gold-find"));
			
		} catch (JSONException e) {
			throw new HeroParsingException(e.getMessage());
		}
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public void setLevel(int level) {
		this.level = level;
		
	}

	public void setClass(String heroClass) {
		this.heroClass = heroClass;
		
	}

	public void setIsHardcore(boolean isHardcore) {
		this.isHardcore = isHardcore;
		
	}

}
