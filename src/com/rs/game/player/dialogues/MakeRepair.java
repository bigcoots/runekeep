package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeRepair extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"repair bench.", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
				player.repair = 1;
				player.out("You build repair bench.");
		} else if (componentId == OPTION_2) {
			end();
		}
			end();
	}

	@Override
	public void finish() {

	}

}
