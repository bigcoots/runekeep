package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeIcon extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Icon of Saradomin (Costs 2M).", "Icon of Zamorak(costs 2M).", "Icon of Guthix(Costs 2M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Icon of Saradomin.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.icon = 1;
				player.getSkills().addXp(Skills.CONSTRUCTION, 333);			
				player.out("You build Icon of Saradomin.");
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Icon of Zamorak.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
						player.icon = 2;
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);						
						player.out("You build Icon of Zamorak.");
						player.getInventory().deleteItem(995, 200000);
					} else {
						player.out("You need 2M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Icon of Guthix.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.icon = 3;
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);				
				player.out("You build Icon of Guthix.");
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
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
