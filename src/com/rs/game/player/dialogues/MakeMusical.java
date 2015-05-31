package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeMusical extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Windchimes (Costs 2M).", "Bells(costs 3M).", "Organ (Costs 4M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Windchimes.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.musical = 1;
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);				
				player.out("You build Windchimes.");
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Bells.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 3000000)) {
						player.musical = 2;
						player.out("You build Bells.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 500);						
						player.getInventory().deleteItem(995, 300000);
					} else {
						player.out("You need 3M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Organ .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 4000000)) {
				player.musical = 3;
				player.out("You build Organ .");
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
