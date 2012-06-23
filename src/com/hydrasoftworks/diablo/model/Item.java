package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.annotations.SerializedName;

public class Item {
	private static final String IMAGE_LINK = "http://eu.media.blizzard.com/d3/icons/items/large/";
	private String name;
	private String icon;
	@SerializedName("displayColor")
	private String displayColor;
	@SerializedName("tooltipParams")
	private String tooltipParams;

	public URL createImageLink(String heroClass, int gender)
			throws MalformedURLException {
		return new URL(Item.IMAGE_LINK + icon + "_"
				+ heroClass.replace("-", "") + "_"
				+ (gender == 0 ? "male" : "female") + ".png");
	}

	/**
	 * @return the displayColor
	 */
	public String getDisplayColor() {
		return displayColor;
	}

}
