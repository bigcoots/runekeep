package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;

public class FloatingEssence extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getInventory().containsItem(7937, 250)) {
			player.getInventory().deleteItem(7937, 250);
			player.getInventory().refresh();
		sendEntityDialogue(
				SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"You touch the essence and gave out 250 essence,",
				"for 30 upgraded essence."}, IS_NPC, npcId,
				15402);
		stage = -1;
		}
		else {
		player.sm("You need 250 Pure Essence (noted).");	
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			player.getInventory().refresh();
			player.getInventory().addItem(24227, 30);
			stage = 1;
		}
		if (stage == 1) {
			player.closeInterfaces();
			player.getInterfaceManager().closeChatBoxInterface();
	}
	}
	@Override
	public void finish() {

	}

}
