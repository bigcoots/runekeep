package com.rs.game.player.dialogues;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.WorldTile;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.utils.ShopsHandler;

public class Train extends Dialogue {
private int npcId;
 @Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello! I can take you to several training places around Validus,",
						" would you like to?" }, IS_NPC, npcId, 9827);
	}

 @Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { player.getDisplayName(), "Sure thing!" },
					IS_PLAYER, player.getIndex(), 9827);
			stage = 1;
		} else if (stage == 1) {
		sendOptionsDialogue("Select an option.",
				"Rock Crabs", "Yaks", "Ape Atoll", "Bandits", "More training...");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2707, 3715, 0));
		end();
			} else if (componentId == OPTION_2) {			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2322, 3804, 0));
		end();
			} else if (componentId == OPTION_3) {
			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2793, 2786, 0));
		end();
			} else if (componentId == OPTION_4) {			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3167, 2983, 0));
		end();	
			} else if (componentId == OPTION_5) {
				stage = 3;
						sendOptionsDialogue("Select an option.", "Cockroach",
					"Giant spider", "Armoured zombies", "Vultures", "Jadinkos");
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3159, 4279, 3));
		end();
			} else if (componentId == OPTION_2) {			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2133, 5276, 0));
		end();
			} else if (componentId == OPTION_3) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3240, 9992, 0));
		end();
			} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3675, 3021, 0));
		end();
			} else if (componentId == OPTION_5) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3012, 9273, 0));
		end();		
			}
		}
	}

 @Override
	public void finish() {

	}
}
