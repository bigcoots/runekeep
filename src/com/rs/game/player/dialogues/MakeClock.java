package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeClock extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Oak clock (Costs 1M).", "Teak clock(costs 2M).", "Gilded clock(Costs 3M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Oak clock .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 1000000)) {
				player.clock = 1;
				player.out("You build Oak clock.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 180);				
				player.getInventory().deleteItem(995, 1000000);
			} else {
				player.out("You need 1M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Teak clock.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
						player.clock = 2;
						player.out("You build Teak clock.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);						
						player.getInventory().deleteItem(995, 2000000);
					} else {
						player.out("You need 2M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Gilded clock.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 3000000)) {
				player.clock = 3;
				player.out("You build Gilded clock.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 500);				
				player.getInventory().deleteItem(995, 3000000);
			} else {
				player.out("You need 3M coins in your inventory to build this.");
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
