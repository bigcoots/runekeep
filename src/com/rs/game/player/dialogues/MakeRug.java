package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Skills;

public class MakeRug extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to make?",
					"magic rug(costs 10m).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 95) {
				player.getPackets().sendGameMessage(
						"You need a construction level of at least 95 to make this.");
			} else if (player.getInventory().containsItem(995, 10000000)) {
			player.rug = 1;
			player.getSkills().addXp(Skills.CONSTRUCTION, 1333);
			player.setNextAnimation(new Animation(898));
			player.getInventory().deleteItem(995, 10000000);
			player.getPackets().sendGameMessage("You make a magic rug.");
			} else {
				player.out("You need 10m coins to build this.");
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
