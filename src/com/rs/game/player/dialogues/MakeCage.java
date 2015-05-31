package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Skills;

public class MakeCage extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to make?",
					"cage(costs 20m).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 95) {
				player.getPackets().sendGameMessage(
						"You need a construction level of at least 95 to make this.");
			} else if (player.getInventory().containsItem(995, 20000000)) {
			player.cage = 1;
			player.getSkills().addXp(Skills.CONSTRUCTION, 2666);			
			player.setNextAnimation(new Animation(898));
			player.getInventory().deleteItem(995, 20000000);
			player.getPackets().sendGameMessage("You make a cage.");
			} else {
				player.out("You need 20m coins to build this.");
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
