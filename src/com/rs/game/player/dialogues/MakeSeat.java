package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeSeat extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Carved teak bench (Costs 2M).", "Mahogany bench (costs 4M).", "Gilded bench (Costs 6M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Carved teak bench.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.seat = 1;
				player.out("You build Carved teak bench.");
				player.getSkills().addXp(Skills.CONSTRUCTION, 333);			
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Mahogany bench .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 4000000)) {
						player.seat = 2;
						player.out("You build Mahogany bench .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 833);						
						player.getInventory().deleteItem(995, 4000000);
					} else {
						player.out("You need 4M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Gilded bench .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 6000000)) {
				player.seat = 3;
				player.out("You build Gilded bench .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 1000);				
				player.getInventory().deleteItem(995, 6000000);
			} else {
				player.out("You need 6M coins in your inventory to build this.");
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
