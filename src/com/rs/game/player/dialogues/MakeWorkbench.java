package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class MakeWorkbench extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"workbench1.", "workbench2.", "workbench3.", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
				player.workbench = 1;
				player.out("You build workbench1.");
		} else if (componentId == OPTION_2) {
						player.workbench = 2;
						player.out("You build workbench2.");

		} else if (componentId == OPTION_3) {
				player.workbench = 3;
				player.out("You build workbench3.");
		} else if (componentId == OPTION_4) {
			end();
		}
			end();
	}

	@Override
	public void finish() {

	}

}
