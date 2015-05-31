package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Skills;

public class MakeStair extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to make?",
					"teak staircase.", "marble staircase.", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 3) {
				player.getPackets().sendGameMessage(
						"You need a construction level of at least 3 to make this.");
			} else if (player.getInventory().containsItem(8780, 10)) {
			player.stair = 1;
			player.getSkills().addXp(Skills.CONSTRUCTION, 200);
			player.setNextAnimation(new Animation(898));
			player.getInventory().deleteItem(8780, 10);
			player.getPackets().sendGameMessage("You make a teak staircase.");
			} else {
				player.out("You need 10 teak planks to build this.");
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 33) {
				player.getPackets().sendGameMessage(
						"You need a construction level of at least 33 to make this.");
			} else if (player.getInventory().containsItem(8786, 4)) {
			player.stair = 2;
			player.getSkills().addXp(Skills.CONSTRUCTION, 8000);
			player.setNextAnimation(new Animation(898));
			player.getInventory().deleteItem(8786, 4);
			player.getPackets().sendGameMessage("You make a marble staircase.");
			} else {
				player.out("You need 4 marble blocks to build this.");
			}
		} else if (componentId == OPTION_3) {
			end();
		}
		end();
	}

	@Override
	public void finish() {

	}

}
