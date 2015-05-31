package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class bossreward extends Dialogue {

        private int npcId;

        @Override
        public void start() {
            sendEntityDialogue(SEND_2_TEXT_CHAT,
                    new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
                                    "Hello, Would you like to buy balmung"}, IS_NPC, npcId, 9827);
        }
 
 
        @Override
        public void run(int interfaceId, int componentId) {
            if (stage == -1) {
                sendOptionsDialogue("Please select an item.","Balmung", "nevermind");
                stage = 1;
        } else if (stage == 1) {
                if (componentId == OPTION_1) {
                	if (player.pest >= 100
                			&& (player.nex >= 10) 
                			&& (player.bandos >= 15) 
                			&& (player.saradomin >= 15)
                			&& (player.zamorak >= 15) 
                			&& (player.armadyl >= 15)
                			&& (player.qbd >= 5 )
                			&& (player.corp >= 10)
                			&& (player.kbd >= 15 )
                			&& (player.sunfreet >= 15 )
                			&& (player.td >= 15 )
                			&& (player.gano >= 15 )
                			&& (player.glacor >= 15 )
                			&& (player.revs >= 15 )
                			&& (player.dags >= 15 )
                			&& (player.sea >= 15 )
                			&& (player.kq >= 15)){
                        player.getInventory().addItem(15403, 1);
                	 player.bandos -=15;
                	 player.kbd -=15;
                	 player.sunfreet -=15;
                	 player.td -=15;
                	 player.gano -=15;
                	 player.glacor -=15;
                	 player.revs -=15;
                	 player.dags -=15;
                	 player.sea -=15;
                	 player.kq -=15;
                	 player.saradomin -=15;
                	 player.zamorak -=15;
                	 player.armadyl -=15;
                	 player.qbd -=5;
                	 player.corp -=10;
                	 player.nex -=10;
                   
                        player.pest -=100;
                        player.getInterfaceManager().sendInterface(275);
                        player.getPackets().sendIComponentText(275, 1, "Bosses");
			player.getPackets().sendIComponentText(275, 10, " You have killed General Graardor  " +player.bandos + " times.");
			player.getPackets().sendIComponentText(275, 11, " You have killed K'ril Tsutsaroth  " +player.zamorak + " times.");
			player.getPackets().sendIComponentText(275, 12, " You have killed Kree'arra   " +player.armadyl + " times.");
			player.getPackets().sendIComponentText(275, 13, " You have killed Commander Zilyana   " +player.saradomin + " times.");
			player.getPackets().sendIComponentText(275, 14, " You have killed Corporeal beast " +player.corp + " times.");
			player.getPackets().sendIComponentText(275, 15, " You have killed Kbd " +player.kbd + " times.");
			player.getPackets().sendIComponentText(275, 16, " You have killed Sunfreet " +player.sunfreet + " times.");	
			player.getPackets().sendIComponentText(275, 17, " You have killed Pest queen " +player.pest + " times.");	
			player.getPackets().sendIComponentText(275, 18, " You have killed Nex " +player.nex + " times.");
			player.getPackets().sendIComponentText(275, 19, " You have killed Tormented demon " +player.td + " times.");
			player.getPackets().sendIComponentText(275, 20, " You have killed Ganodermic beast " +player.gano + " times.");	
			player.getPackets().sendIComponentText(275, 21, " You have killed Glacor " +player.glacor + " times.");	
			player.getPackets().sendIComponentText(275, 22, " You have killed Revenant creatures " +player.revs + " times.");
			player.getPackets().sendIComponentText(275, 23, " You have killed Dagannoth kings " +player.dags + " times.");
			player.getPackets().sendIComponentText(275, 24, " You have killed Sea troll queen" +player.sea + " times.");
			player.getPackets().sendIComponentText(275, 25, " You have killed Kalphite queen " +player.kq + " times.");	
			player.getPackets().sendIComponentText(275, 26, " You have killed Queen black dragon " +player.qbd + " times.");
			
                                end();
                        } else
                        	player.getPackets().sendGameMessage(" Get 15 pest queen kills to buy this, you currently have: " +player.pest);
            	player.getPackets().sendGameMessage(" Get 15 General Graardor kills to buy this, you currently have: " +player.bandos);
            	player.getPackets().sendGameMessage(" Get 15 K'ril Tsutsaroth kills to buy this, you currently have: " +player.zamorak);
            	player.getPackets().sendGameMessage(" Get 15 Kree'arra kills to buy this, you currently have: " +player.armadyl);
            	player.getPackets().sendGameMessage(" Get 15 Commander Zilyana kills to buy this, you currently have: " +player.saradomin);
            	player.getPackets().sendGameMessage(" Get 10 Corporeal beast kills to buy this, you currently have: " +player.corp);
            	player.getPackets().sendGameMessage(" Get 15 Kbd kills to buy this, you currently have: " +player.kbd);
            	player.getPackets().sendGameMessage(" Get 15 Sunfreet kills to buy this, you currently have: " +player.sunfreet);
            	player.getPackets().sendGameMessage(" Get 10 Nex kills to buy this, you currently have: " +player.nex);
            	player.getPackets().sendGameMessage(" Get 15 Tormented demon kills to buy this, you currently have: " +player.td);
            	player.getPackets().sendGameMessage(" Get 15 Ganodermic beast kills to buy this, you currently have: " +player.gano);
            	player.getPackets().sendGameMessage(" Get 15 Glacor kills to buy this, you currently have: " +player.glacor);
            	player.getPackets().sendGameMessage(" Get 15 Revenant creatures kills to buy this, you currently have: " +player.revs);
            	player.getPackets().sendGameMessage(" Get 15 Dagannoth kings kills to buy this, you currently have: " +player.dags);
            	player.getPackets().sendGameMessage(" Get 15 Sea troll queen kills to buy this, you currently have: " +player.sea);
            	player.getPackets().sendGameMessage(" Get 5 Kalphite queen kills to buy this, you currently have: " +player.kq);
            	player.getPackets().sendGameMessage(" Get 15 Queen black dragon kills to buy this, you currently have: " +player.qbd);
                        end();


                } else if (componentId == OPTION_2) {

                        end();

                }
        }
        } 
        @Override
        public void finish() {

        }
}		