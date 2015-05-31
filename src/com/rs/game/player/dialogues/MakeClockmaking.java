package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeClockmaking extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Clockmaker's bench1(costs 2m).", "Clockmaker's bench2(costs 3m).", "Clockmaker's bench3(costs 4m).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Clockmaker's bench1.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.clockmaking = 1;
				player.out("You build Clockmaker's bench1.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);				
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Clockmaker's bench2 .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 3000000)) {
						player.clockmaking = 2;
						player.out("You build Clockmaker's bench2 .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 500);						
						player.getInventory().deleteItem(995, 3000000);
					} else {
						player.out("You need 3M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Clockmaker's bench2 .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 4000000)) {
				player.clockmaking = 3;
				player.out("You build Clockmaker's bench2 .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 666);				
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
