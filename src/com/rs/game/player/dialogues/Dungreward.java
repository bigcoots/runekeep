package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;

public class Dungreward extends Dialogue {

	private int npcId;

	
	
 @Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello, I can sell u dung weps,",
						" would you like to?" }, IS_NPC, npcId, 9827);
	}

 @Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(), "um, ofc ... i need better weps !" },
					IS_PLAYER, player.getIndex(), 9827);
			stage = 1;
		} else if (stage == 1) {
			sendOptionsDialogue("What would you like to buy?", "chaotic rapier (200k).",
					"chaotic longsword (200k).", "chaotic maul (200k).", "chaotic staff (200k).", "More Options...");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
			if (player.getInventory().containsItem(2996, 200000)) {
				player.getInventory().deleteItem(2996, 200000);
				player.getInventory().addItem(18349, 1);
				end();
			} else {
			player.getPackets().sendGameMessage("You need 200k pvp ticks to buy this.");
			end();
			}
			} else if (componentId == OPTION_2) {			
			if (player.getInventory().containsItem(2996, 200000)) {
				player.getInventory().deleteItem(2996, 200000);
				player.getInventory().addItem(18351, 1);
				end();
			} else {
			player.getPackets().sendGameMessage("You need 200k pvp ticks to buy this.");
			end();
			}
			} else if (componentId == OPTION_3) {
			
			if (player.getInventory().containsItem(2996, 200000)) {
				player.getInventory().deleteItem(2996, 200000);
				player.getInventory().addItem(18353, 1);
				end();
			} else {
			player.getPackets().sendGameMessage("You need 200k pvp ticks to buy this.");
			end();
			}
			} else if (componentId == OPTION_4) {			
			if (player.getInventory().containsItem(2996, 200000)) {
				player.getInventory().deleteItem(2996, 200000);
				player.getInventory().addItem(18355, 1);
				end();
			} else {
			player.getPackets().sendGameMessage("You need 200k pvp ticks to buy this.");
			end();
			}
			} else if (componentId == OPTION_5) {
				stage = 3;
				sendOptionsDialogue("What would you like to switch?",
						"chaotic crossbow (200k).", "off-hand set (300k).", "More Options...");
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {			
			if (player.getInventory().containsItem(2996, 200000)) {
				player.getInventory().deleteItem(2996, 200000);
				player.getInventory().addItem(18357, 1);
				end();
			} else {
			player.getPackets().sendGameMessage("You need 200k pvp ticks to buy this.");
			end();
			}
			} else if (componentId == OPTION_2) {			
			if (player.getInventory().containsItem(2996, 300000)) {
				player.getInventory().deleteItem(2996, 300000);
				player.getInventory().addItem(25991, 1);
				player.getInventory().addItem(25993, 1);
					player.getInventory().addItem(25995, 1);
					end();
			} else {
			player.getPackets().sendGameMessage("You need 300k pvp ticks to buy this.");
			end();
			}
			} else if (componentId == OPTION_3) {
				stage = 2;
			sendOptionsDialogue("What would you like to buy?", "chaotic rapier (200k).",
					"chaotic longsword (200k).", "chaotic maul (200k).", "chaotic staff (200k).", "More Options...");
			}
		}
	}

 @Override
	public void finish() {

	}
}