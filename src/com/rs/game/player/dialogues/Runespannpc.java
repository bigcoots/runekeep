package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.RegionBuilder;
import com.rs.game.WorldTile;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.utils.ShopsHandler;

public class Runespannpc extends Dialogue {


	@Override
	public void start() {
		sendOptionsDialogue("Select an option.",
				"floor 1.", "floor 2", "floor 3.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
	Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3995, 6109, 1));
				end();
		} else if (componentId == OPTION_2) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4135, 6088, 1));	
				end();

		} else if (componentId == OPTION_3) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4332, 6045, 1));

		end();
		}
			end();
		}

	

	@Override
	public void finish() {

	
}
}
