package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Skills;

public class MakeTools extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to make?",
					"tools set(costs 2m).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 55) {
				player.getPackets().sendGameMessage(
						"You need a construction level of at least 55 to make this.");
			} else if (player.getInventory().containsItem(995, 2000000)) {
			player.tools = 1;
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);
			player.setNextAnimation(new Animation(898));
			player.getInventory().deleteItem(995, 2000000);
			player.getPackets().sendGameMessage("You make a tools set.");
			} else {
				player.out("You need 2m coins to build this.");
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
