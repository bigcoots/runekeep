package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeDeco extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"decoration (Costs 1M).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 60) {
				player.out("You need at least 60 Construction to build decoration.");
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 60 && player.getInventory().containsItem(995, 1000000)) {
				player.deco = 1;
				player.out("You build decoration.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 180);				
				player.getInventory().deleteItem(995, 1000000);
			} else {
				player.out("You need 1M coins in your inventory to build this.");
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
