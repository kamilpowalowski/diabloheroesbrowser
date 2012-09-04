package com.hydrasoftworks.diablo.model;

import java.util.List;

public class Act {
	public static final String[] ACTS = { "act1", "act2", "act3", "act4" };

	private boolean completed;
	private List<Quest> completedQuests;

	public static class Quest {
		private String slug;
		private String name;
		
		public String getName() {
			return name;
		}
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @return the quests
	 */
	public List<Quest> getCompletedQuests() {
		return completedQuests;
	}

}
