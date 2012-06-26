package com.hydrasoftworks.diablo.model;

import java.util.HashMap;
import java.util.List;

public class Follower {
	public static final String TEMPLAR = "templar";
	public static final String SCOUNDREL = "scoundrel";
	public static final String ENCHANTRESS = "enchantress";
	
	private String slug;
    private int level;
    private HashMap<String, Item> items;
    private List<Skill> skills;
    
	public Item getItem(String partOfBody) {
		return items.get(partOfBody);
	}

	/**
	 * @return the skills
	 */
	public List<Skill> getSkills() {
		return skills;
	}
}
