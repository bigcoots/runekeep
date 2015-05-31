package com.rs.game.player.controlers;

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

public class HouseCon extends Controler {
	
	/**
	 * This controller loads the build mode.
	 */
	
	private House house;
	private int[] boundChuncks;
	
	@Override
	public void start() {
		house = new House();
		boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8); 
		house.constructHouse(boundChuncks, true, player);
		player.setNextWorldTile(new WorldTile(boundChuncks[0] * 8 + 35, boundChuncks[1] * 8 + 34, 0));
		World.spawnObject(new WorldObject(13405, 10, 0, boundChuncks[0] * 8 + 35, boundChuncks[1] * 8 + 35, 0), true);
		World.spawnObject(new WorldObject(15270, 10, 0, boundChuncks[0] * 8 + 35, boundChuncks[1] * 8 + 26, 0), true);	
		World.spawnObject(new WorldObject(15269, 10, 0, boundChuncks[0] * 8 + 35, boundChuncks[1] * 8 + 24, 0), true);	
			World.spawnObject(new WorldObject(15276, 10, 1, boundChuncks[0] * 8 + 32, boundChuncks[1] * 8 + 27, 0), true);
			World.spawnObject(new WorldObject(15380, 10, 0, boundChuncks[0] * 8 + 44, boundChuncks[1] * 8 + 43, 0), true);	
			World.spawnObject(new WorldObject(15298, 10, 1, boundChuncks[0] * 8 + 51, boundChuncks[1] * 8 + 42, 0), true);
			World.spawnObject(new WorldObject(15262, 10, 1, boundChuncks[0] * 8 + 55, boundChuncks[1] * 8 + 38, 0), true);
			World.spawnObject(new WorldObject(15439, 10, 1, boundChuncks[0] * 8 + 44, boundChuncks[1] * 8 + 27, 0), true);	
			World.spawnObject(new WorldObject(15323, 10, 0, boundChuncks[0] * 8 + 48, boundChuncks[1] * 8 + 31, 0), true);			
		if (player.hasBoughtDemon) {
		World.spawnNPC(4243, new WorldTile(boundChuncks[0] * 8 + 43, boundChuncks[1] * 8 + 35, 0), -1, true, true);
		}
		player.isOwner = true;
		player.hasLocked = false;
		player.inBuildMode = true;
		player.closeInterfaces();
		//player.checkObjects(boundChuncks); //Used to position objects in build mode.
	}

	
	boolean remove = true;
	/**
	 * return process normally
	 */
	@Override
	public boolean processObjectClick5(WorldObject object) {
		house.previewRoom(player, boundChuncks, new RoomReference(Room.GARDEN, 4, 5, 0, 0), remove = !remove);
		return true;
	}

}