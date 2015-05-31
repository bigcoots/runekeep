package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeGuard extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"hellhound guard (costs 40M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 99) {
				player.out("You need at least 99 Construction to build hellhound .");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 99 && player.getInventory().containsItem(995, 40000000)) {
				player.guard = 1;
				player.getSkills().addXp(Skills.CONSTRUCTION, 5333);			
						player.out("You build hellhound.");
						player.getInventory().deleteItem(995, 40000000);
					} else {
						player.out("You need 40M coins in your inventory to build this.");
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
