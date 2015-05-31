package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeHeraldry extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Pluming stand(costs 2m).", "Shield easel(costs 3m).", "Banner easel(costs 4m).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Pluming stand.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.heraldry = 1;
				player.out("You build Pluming stand.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);				
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Shield easel.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 3000000)) {
						player.heraldry = 2;
						player.out("You build Shield easel.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 500);						
						player.getInventory().deleteItem(995, 3000000);
					} else {
						player.out("You need 3M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Banner easel.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 4000000)) {
				player.heraldry = 3;
				player.out("You build Banner easel.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 633);				
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
