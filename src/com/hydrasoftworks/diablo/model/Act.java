package com.hydrasoftworks.diablo.model;

import java.util.List;

public class Act {
	public static final String[] ACTS = { "act1", "act2", "act3", "act4" };

	private boolean completed;
	private List<Quest> quests;

	public static class Quest {
		private boolean completed;
		private QuestInfo quest;

		public static class QuestInfo {
			private String slug;
			private String name;
		}

		/**
		 * @return the completed
		 */
		public boolean isCompleted() {
			return completed;
		}
		
		public String getName() {
			return quest.name;
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
	public List<Quest> getQuests() {
		return quests;
	}
}
