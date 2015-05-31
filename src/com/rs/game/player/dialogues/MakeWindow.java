package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeWindow extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Stained-glass window (Costs 2M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build Stained-glass window.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 2000000)) {
				player.window = 1;
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);				
				player.out("You build Stained-glass window.");
				player.getInventory().deleteItem(995, 2000000);
			} else {
				player.out("You need 2M coins in your inventory to build this.");
			}
		} else if (componentId == OPTION_2) {
			end();
		}
			end();
	}

	@Override
	public void finish() {

	}

}