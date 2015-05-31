package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeDecoration extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"dragon heads(Costs 5M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build dragon heads.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 5000000)) {
				player.decoration = 1;
				player.out("You build dragon heads.");
				player.getInventory().deleteItem(995, 5000000);
			} else {
				player.out("You need 5M coins in your inventory to build this.");
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
