package com.rs.game.worldlist;

import java.util.HashMap;

import com.rs.Settings;

public class WorldList {

	public static final HashMap<Integer, WorldEntry> WORLDS = new HashMap<Integer, WorldEntry>();
	
	public static void init() {
		WORLDS.put(1, new WorldEntry(Settings.SERVER_NAME, "127.0.0.1", 77, "Main World", false));
		WORLDS.put(2, new WorldEntry(Settings.SERVER_NAME, "127.0.0.1", 191, "Max' World", false));
	}
	
	public static WorldEntry getWorld(int worldId) {
		return WORLDS.get(worldId);
	}
}
