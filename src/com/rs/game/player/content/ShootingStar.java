package com.rs.game.player.content;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class ShootingStar {
	
	/**
	 * Halp make it cleaner pls
	 */
	
	public void mineShootingStar(Player player) {
		if (crashedStar == 1) {//Fally Park
		World.star++;
		player.recievedGift = false;

		 if (World.star >= 80) {
			player.starSprite = true;
			player.stopAll();
			World.spawnObject(new WorldObject(-1, 10, 0 , 3028, 3365, 0), true);
			World.spawnNPC(8091, new WorldTile(3028, 3365, 0), -1, true, true);
			World.removeStarSprite(player);
			World.spawnStar();
			}
		} else if (crashedStar == 2) {//Rimmington Mine
			World.star++;
			player.recievedGift = false;

			 if (World.star >= 80) {
				player.starSprite = true;
				player.stopAll();
				World.spawnObject(new WorldObject(-1, 10, 0 , 2974, 3238, 0), true);
				World.spawnNPC(8091, new WorldTile(2974, 3238, 0), -1, true, true);
				World.removeStarSprite(player);
				World.spawnStar();
				}
		} else { // Varrock Square
			World.star++;
			player.recievedGift = false;

			  if (World.star >= 80) {
				player.starSprite = true;
				player.stopAll();
				World.spawnObject(new WorldObject(-1, 10, 0 , 3230, 3369, 0), true);
				World.spawnNPC(8091, new WorldTile(3230, 3369, 0), -1, true, true);
				World.removeStarSprite(player);
				World.spawnStar();
			}
		}
	}
	
	public static int crashedStar;
	
	/**
	 * Gets random world tiles and sets an int for a check
	 */
	
	public static void spawnRandomStar() {
		WorldTile tile = new WorldTile(3028, 3365, 0);
		WorldTile tile2 = new WorldTile(2974, 3238, 0);
		WorldTile tile3 = new WorldTile(3245, 3509, 0);
		WorldTile[] tiles = {tile, tile2, tile3};
		World.spawnObject(new WorldObject(38660, 10, 0 , tiles[Utils.random(0, 3)]), true);
		if (tiles[Utils.random(0, 3)] == tile) {
			//Falador Spawn
			crashedStar = 1;
			World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Falador!", false);
			World.spawnObject(new WorldObject(38660, 10, 0 , 3028, 3365, 0), true);
		} else if (tiles[Utils.random(0, 3)] == tile2) {
			//Rimmington Mine Spawn
			crashedStar = 2;
			World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Rimmington Mines!", false);
			World.spawnObject(new WorldObject(38660, 10, 0 , 2974, 3238, 0), true);
		} else if (tiles[Utils.random(0, 3)] == tile3) {
			//Varrock Spawn
			crashedStar = 3;
			World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Varrock lodestone area!", false);
			World.spawnObject(new WorldObject(38660, 10, 0 , 3230, 3369, 0), true);
		}
	}

}