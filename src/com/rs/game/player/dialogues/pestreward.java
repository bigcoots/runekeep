package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class pestreward extends Dialogue {

        private int npcId;

        @Override
        public void start() {
            sendEntityDialogue(SEND_2_TEXT_CHAT,
                    new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
                                    "Hi there, Would you like to buy elite void?"}, IS_NPC, npcId, 9827);
        }
 
 
        @Override
        public void run(int interfaceId, int componentId) {
            if (stage == -1) {
                sendOptionsDialogue("Please select an item.", "Gray elite void (30)", "Black elite void (30)", "White elite void (30)", "Never mind.");
                stage = 1;
        } else if (stage == 1) {
                if (componentId == OPTION_1) {
                	if (player.pest >= 30){
                        player.getInventory().addItem(19787, 1);
                        player.getInventory().addItem(19788, 1);
                        player.pest -=30;
			player.getPackets().sendGameMessage("You have killed Pest queen " +player.pest + "/30 times.");				
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't not have enough pest queen kills to buy this, you currently have: " +player.pest);
                        end();
                } else if (componentId == OPTION_2) {
                	if (player.pest >= 30){
                        player.getInventory().addItem(19785, 1);
                        player.getInventory().addItem(19786, 1);
                        player.pest -=30;
							player.getPackets().sendGameMessage("You have killed Pest queen " +player.pest + "/30 times.");	
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't not have enough pest queen kills to buy this, you currently have: " +player.pest);
                        end();
                } else if (componentId == OPTION_3) {
                	if (player.pest >= 30){
                        player.getInventory().addItem(19789, 1);
                        player.getInventory().addItem(19790, 1);
                        player.pest -=30;
							player.getPackets().sendGameMessage("You have killed Pest queen " +player.pest + "/30 times.");	
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't not have enough pest queen kills to buy this, you currently have: " +player.pest);
                        end();
                } else if (componentId == OPTION_4) {

                        end();

                }
        }
        }


            

        @Override
        public void finish() {

        }
}
