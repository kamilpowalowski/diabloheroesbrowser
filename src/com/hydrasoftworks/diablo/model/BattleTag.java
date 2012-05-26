package com.hydrasoftworks.diablo.model;

public class BattleTag implements Comparable<BattleTag> {
	public static final String TABLE_NAME = "battletags";
	public static final String BATTLETAG = "battletag";
	public static final String VALUE = "value";
	public static final String BATTLETAG_ID = "_id";

	private long id;
	private String battleTag;
	private int value;

	/**
	 * @return the battleTag
	 */
	public String getBattleTag() {
		return battleTag;
	}

	/**
	 * @param battleTag
	 *            the battleTag to set
	 */
	public void setBattleTag(String battleTag) {
		this.battleTag = battleTag;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getBattleTag();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int compareTo(BattleTag another) {
		return new Integer(getValue())
				.compareTo(new Integer(another.getValue()));
	}
}
