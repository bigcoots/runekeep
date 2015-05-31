package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.RegionBuilder;
import com.rs.game.WorldTile;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.utils.ShopsHandler;

public class Farmshop extends Dialogue {


	@Override
	public void start() {
		sendOptionsDialogue("Select an option.",
				"Farm shop 1.", "Farm shop 2", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 41);
				end();
		} else if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 42);
				end();

		} else if (componentId == OPTION_3) {
		end();
		}
			end();
		}

	

	@Override
	public void finish() {

	
}
}
