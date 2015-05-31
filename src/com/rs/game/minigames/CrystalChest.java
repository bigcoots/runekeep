package com.rs.game.minigames;

import com.rs.game.player.Player;
import com.rs.game.Animation;
import com.rs.utils.Utils;

/**
 * Represents the chest on which the key is used.
 * @author Corey 2012 <Fixed some stuff by bart>
 */

public class CrystalChest {
	
	private static final Object[] CHEST_REWARDS = { };
	public static final int[] KEY_HALVES = { 985, 987 };
	public static final int KEY = 989;
	public static final int Animation = 881;
	
	/**
	 * Represents the key being made.
	 * Using tooth halves.
	 */
	public static void makeKey(Player p){
		if (p.getInventory().containsItem(toothHalf(), 1) 
				&& p.getInventory().containsItem(loopHalf(), 1)){
			p.getInventory().deleteItem(toothHalf(), 1);
			p.getInventory().deleteItem(loopHalf(), 1);
			p.getInventory().addItem(KEY, 1);
			p.getPackets().sendGameMessage("You succesfully make a crytal key.");
		}
	}
	
	/**
	 * If the player can open the chest.
	 */

	
	/**
	 * When the player searches the chest.
	 */
	public static void searchChest(final Player p){
		if(p.getInventory().containsItem(KEY, 1)){
			p.getPackets().sendGameMessage("You unlock the chest with your key.");
			p.getInventory().deleteItem(KEY, 1);
			p.setNextAnimation(new Animation(Animation));
			p.getInventory().addItem(995, Utils.random(8230));
			p.getInventory().addItem((Integer)
			CHEST_REWARDS[Utils.random(getLength() - 1)], 1);
			p.getPackets().sendGameMessage("You find some treasure in the chest.");
		}
	}
	
	public static int getLength() {
		return CHEST_REWARDS.length;
	}
	
	/**
	 * Represents the toothHalf of the key.
	 */
	public static int toothHalf(){
		return KEY_HALVES[0];
	}
	
	/**
	 * Represent the loop half of the key.
	 */
	public static int loopHalf(){
		return KEY_HALVES[1];
	}
	
}