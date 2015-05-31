package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeTable extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Teak table (Costs 2M).", "Mahogany table (costs 3M).", "Opulent table (Costs 4M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Teak table.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.table = 1;
				player.out("You build Teak table.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);				
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Mahogany table .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 3000000)) {
						player.table = 2;
						player.out("You build Mahogany table .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 500);						
						player.getInventory().deleteItem(995, 3000000);
					} else {
						player.out("You need 3M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Opulent table .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 4000000)) {
				player.table = 3;
				player.out("You build Opulent table .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 833);				
				player.getInventory().deleteItem(995, 4000000);
			} else {
				player.out("You need 4M coins in your inventory to build this.");
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
