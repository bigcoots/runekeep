package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Skills;

public class MakeThrone extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to make?",
					"Oak Throne(costs 20m).", "Crystal Throne(costs 40m).", "Demonic Throne(costs 60m).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 80) {
				player.out("You need at least 80 Construction to build Oak Throne.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 80 && player.getInventory().containsItem(995, 20000000)) {
				player.throne = 1;
				player.getSkills().addXp(Skills.CONSTRUCTION, 2666);			
				player.out("You build Oak Throne.");
				player.getInventory().deleteItem(995, 20000000);
			} else {
				player.out("You need 20M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 95) {
				player.out("You need at least 95 Construction to build Crystal Throne.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 95 && player.getInventory().containsItem(995, 40000000)) {
						player.throne = 2;
			player.getSkills().addXp(Skills.CONSTRUCTION, 5333);						
						player.out("You build Crystal Throne.");
						player.getInventory().deleteItem(995, 4000000);
					} else {
						player.out("You need 40M coins in your inventory to build this.");
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 99) {
				player.out("You need at least 99 Construction to build Demonic Throne.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 99 && player.getInventory().containsItem(995, 60000000)) {
				player.throne = 3;
			player.getSkills().addXp(Skills.CONSTRUCTION, 8000);				
				player.out("You build Demonic Throne.");
				player.getInventory().deleteItem(995, 60000000);
			} else {
				player.out("You need 60M coins in your inventory to build this.");
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
