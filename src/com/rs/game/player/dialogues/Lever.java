package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;
import com.rs.game.Animation;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.construction.Furniture.FurnitureObjects;
import com.rs.game.player.content.construction.House.Room;
import com.rs.game.player.content.construction.House.RoomReference;
import com.rs.game.player.content.construction.PlayerHouseSaving;
import com.rs.game.player.content.construction.loadHouse;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class Lever extends Dialogue {
	public int[] boundChuncks;
	private House house;	
	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"buy trap(costs 20m).", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {

			 if (player.getInventory().containsItem(995, 20000000)) {
			  	player.hasBoughttrap = true;
				player.out("You build trap.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);				
				player.getInventory().deleteItem(995, 20000000);
			} else {
				player.out("You need 20M coins in your inventory to build this.");
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
