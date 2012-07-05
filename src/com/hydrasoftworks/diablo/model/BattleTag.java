package com.hydrasoftworks.diablo.model;

public class BattleTag implements Comparable<BattleTag> {
	public static final String TABLE_NAME = "battletags";
	public static final String BATTLETAG = "battletag";
	public static final String VALUE = "value";
	public static final String SERVER = "server";
	public static final String BATTLETAG_ID = "_id";

	private long id;
	private String battleTagText;
	private int value;
	private String server;

	/**
	 * @return the battleTagText
	 */
	public String getBattleTagText() {
		return battleTagText;
	}

	/**
	 * @param battleTagText
	 *            the battleTagText to set
	 */
	public void setBattleTagText(String battleTag) {
		this.battleTagText = battleTag;
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
		return getBattleTagText();
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
		return Integer.valueOf(getValue()).compareTo(
				Integer.valueOf(another.getValue()));
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server
	 *            the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}
}
