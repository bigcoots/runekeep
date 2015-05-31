package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeBell extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Rope bell-pull (Costs 1M).", "Bell-pull(costs 2M).", "Posh bell-pull(Costs 3M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Rope bell-pull.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 1000000)) {
				player.bell = 1;
				player.out("You build Rope bell-pull.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 180);				
				player.getInventory().deleteItem(995, 1000000);
			} else {
				player.out("You need 1M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Bell-pull.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
						player.bell = 2;
						player.out("You build Bell-pull.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);						
						player.getInventory().deleteItem(995, 200000);
					} else {
						player.out("You need 2M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Posh bell-pull.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 3000000)) {
				player.bell = 3;
				player.out("You build Posh bell-pull.");
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
