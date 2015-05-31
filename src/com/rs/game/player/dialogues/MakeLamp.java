package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeLamp extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"incense burners (Costs 2M).", "Mahogany burners(costs 10M).", "marble burners (Costs 20M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build incense burners.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.lamp = 1;
				player.getSkills().addXp(Skills.CONSTRUCTION, 333);			
				player.out("You build incense burners.");
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 70) {
				player.out("You need at least 70 Construction to build Mahogany burners.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 70 && player.getInventory().containsItem(995, 10000000)) {
						player.lamp = 2;
			player.getSkills().addXp(Skills.CONSTRUCTION, 1333);						
						player.out("You build Mahogany burners.");
						player.getInventory().deleteItem(995, 10000000);
					} else {
						player.out("You need 10M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 75) {
				player.out("You need at least 75 Construction to build marble burners.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 75 && player.getInventory().containsItem(995, 20000000)) {
				player.lamp = 3;
				player.out("You build marble burners.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 2666);				
				player.getInventory().deleteItem(995, 20000000);
			} else {
				player.out("You need 20M coins in your inventory to build this.");
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
