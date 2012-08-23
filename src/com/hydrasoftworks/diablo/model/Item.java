package com.hydrasoftworks.diablo.model;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.annotations.SerializedName;

public class Item {
	private static final String ITEM_URL = "d3/en/tooltip/";
	private static final String IMAGE_LINK = "http://eu.media.blizzard.com/d3/icons/items/large/";
	private String name;
	private String icon;
	@SerializedName("displayColor")
	private String displayColor;
	@SerializedName("tooltipParams")
	private String tooltipParams;

	public URL createImageLink()
			throws MalformedURLException {
		return new URL(Item.IMAGE_LINK + icon + ".png");
	}

	public String createTooltipLink() {
		return CareerProfile.getActiveProfile().getHost() + ITEM_URL
				+ tooltipParams.replace("item-data/", "item/");
	}

	/**
	 * @return the displayColor
	 */
	public String getDisplayColor() {
		return displayColor;
	}

}
