package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeBench extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"teak bench (Costs 2M).", "mahogany bench (costs 5M).", "gilded bench (Costs 10M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build teak bench.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.bench = 1;
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);				
				player.out("You build teak bench.");
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 70) {
				player.out("You need at least 70 Construction to build mahogany bench.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 70 && player.getInventory().containsItem(995, 5000000)) {
						player.bench = 2;
						player.out("You build mahogany bench.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 833);						
						player.getInventory().deleteItem(995, 5000000);
					} else {
						player.out("You need 5M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 75) {
				player.out("You need at least 75 Construction to build gilded bench.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 75 && player.getInventory().containsItem(995, 10000000)) {
				player.bench = 3;
				player.out("You build gilded bench.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 1333);				
				player.getInventory().deleteItem(995, 10000000);
			} else {
				player.out("You need 10M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_4) {
			end();
		}
			end();
	}

	@Override
	public void finish() {

	}

}
