package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeBed extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Large oak bed (Costs 1M).", "Large teak bed (costs 2M).", "Gilded 4-poster (Costs 5M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Large oak bed .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 1000000)) {
				player.bed = 1;
				player.out("You build Large oak bed .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 180);				
				player.getInventory().deleteItem(995, 1000000);
			} else {
				player.out("You need 1M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Large teak bed .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
						player.bed = 2;
						player.out("You build Large teak bed .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);						
						player.getInventory().deleteItem(995, 2000000);
					} else {
						player.out("You need 2M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Gilded 4-poster .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 5000000)) {
				player.bed = 3;
				player.out("You build Gilded 4-poster .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 833);				
				player.getInventory().deleteItem(995, 5000000);
			} else {
				player.out("You need 5M coins in your inventory to build this.");
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
