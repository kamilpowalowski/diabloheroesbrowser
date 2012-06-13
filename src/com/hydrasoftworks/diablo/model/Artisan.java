package com.hydrasoftworks.diablo.model;

public class Artisan {
	public static final String SLUG_BLACKSMITH = "blacksmith";
	public static final String SLUG_JEWELER = "jeweler";
	private String slug;
	private int level;
	private int stepCurrent;
	private int stepMax;
	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
}
