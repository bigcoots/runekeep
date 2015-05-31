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

public class Lodetav extends Dialogue {
	public int[] boundChuncks;
	private House house;	
	@Override
	public void start() {
							sendOptionsDialogue("What would you like to do?",
					"activate Taverly lodestone.", "Never mind.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {

					player.tav = true;
					player.out("You have activated the Taverly lodestone.");
			
	
		
		} else if (componentId == OPTION_2) {
			end();
		}
			end();
	}

	@Override
	public void finish() {

	}

}
