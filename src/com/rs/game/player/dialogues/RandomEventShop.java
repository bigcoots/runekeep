package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class RandomEventShop extends Dialogue {

        private int npcId;

        @Override
        public void start() {
            sendEntityDialogue(SEND_2_TEXT_CHAT,
                    new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
                                    "Here you can buy items with your random event tokens."}, IS_NPC, npcId, 9827);
        }
 
 
        @Override
        public void run(int interfaceId, int componentId) {
            if (stage == -1) {
                sendOptionsDialogue("Please select an item.", "Golden Hammer (5)", "Witchdoctor Mask (10)", "Witchdoctor Robes (10)", "Witchdoctor Legs (10)", "Next Page");
                stage = 1;
        } else if (stage == 1) {
                if (componentId == OPTION_1) {
                	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                        player.getInventory().addItem(20084, 1);                  
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                        end();
                } else if (componentId == OPTION_2) {
                	if (player.getInventory().containsItem(7478, 10)) {
            player.getInventory().deleteItem(7478, 10);
                        player.getInventory().addItem(20046, 1);                       
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                        end();
                } else if (componentId == OPTION_3) {
                	if (player.getInventory().containsItem(7478, 10)) {
            player.getInventory().deleteItem(7478, 10);
                        player.getInventory().addItem(20044, 1);                       
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                        end();
                } else if (componentId == OPTION_4) {
                	if (player.getInventory().containsItem(7478, 10)) {
            player.getInventory().deleteItem(7478, 10);
                        player.getInventory().addItem(20045, 1);                       
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                        end();
                } else if (componentId == OPTION_5) {
                        stage = 2;
                        	sendOptionsDialogue("Please select an item.", "Lederhosen Top(5)", "Lederhosen Shorts (5)", "Lederhosen Hat (5)", "Prince Tunic (5)", "Next Page");
                }
                    } else if (stage == 2) {
                            if (componentId == OPTION_1) {
                            	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                    player.getInventory().addItem(6180, 5);                                   
                                            end();
                                    } else
                                    	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                    end();
                            } else if (componentId == OPTION_2) {
                            	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                    player.getInventory().addItem(6181, 1);                                    
                                            end();
                                    } else
                                    	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                    end();
                            } else if (componentId == OPTION_3) {
                            	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                    player.getInventory().addItem(6182, 1);                                    
                                            end();
                                    } else
                                    	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                    end();
                            } else if (componentId == OPTION_4) {
                            	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                    player.getInventory().addItem(6184, 1);                                   
                                            end();
                                    } else
                                    	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                    end();
                            } else if (componentId == OPTION_5) {
                                    stage = 3;
                                    	sendOptionsDialogue("Please select an item.", "Prince Leggings (5)", "Princess Blouse (5)", "Princess Skirt (5)", "Frog Mask (5)", "Next Page");
                            }
                                } else if (stage == 3) {
                                        if (componentId == OPTION_1) {
                                        	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                                player.getInventory().addItem(6185, 1);
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                end();
                                        } else if (componentId == OPTION_2) {
                                        	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                                player.getInventory().addItem(6186, 1);
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                end();
                                        } else if (componentId == OPTION_3) {
                                        	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                                player.getInventory().addItem(6187, 1);
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                end();
                                        } else if (componentId == OPTION_4) {
                                        	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                                player.getInventory().addItem(6188, 1);                                                
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                end();
                                        } else if (componentId == OPTION_5) {
                                            stage = 4;
                                        	sendOptionsDialogue("Please select an item.", "Salty Claws Hat (4)", "Guthix Halo (5)", "Saradomin Halo (5)", "Zamorak Halo (5)", "Page 1");
                                }
                                } else if (stage == 4) {
                                    if (componentId == OPTION_1) {
                                    	if (player.getInventory().containsItem(7478, 4)) {
            player.getInventory().deleteItem(7478, 4);
                                            player.getInventory().addItem(20077, 1);                                           
                                                    end();
                                            } else
                                            	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                            end();
                                    } else if (componentId == OPTION_2) {
                                    	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                            player.getInventory().addItem(18744, 1);                                            
                                                    end();
                                            } else
                                            	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                            end();
                                    } else if (componentId == OPTION_3) {
                                    	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                            player.getInventory().addItem(18745, 1);                                           
                                                    end();
                                            } else
                                            	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                            end();
                                    } else if (componentId == OPTION_4) {
                                    	if (player.getInventory().containsItem(7478, 5)) {
            player.getInventory().deleteItem(7478, 5);
                                            player.getInventory().addItem(18746, 1);                                         
                                                    end();
                                            } else
                                            	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                            end();
                                    } else if (componentId == OPTION_5) {
                                    	stage = 1;
                                    }
                                    } else if (stage == 5) {
                                        if (componentId == OPTION_1) {
                                        	if (player.getInventory().containsItem(7478, 1)) {
            player.getInventory().deleteItem(7478, 1);
                                                player.getInventory().addItem(9470, 1);
                                                
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                end();
                                        } else if (componentId == OPTION_2) {
                                        	if (player.getInventory().containsItem(14, 1)) {
                                                player.getInventory().addItem(7671, 1);
												player.getInventory().addItem(7673, 1);
                                                player.donatorPoints -=2;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                end();
                                        } else if (componentId == OPTION_3) {
                                        	if (player.getInventory().containsItem(7478, 1)) {
            player.getInventory().deleteItem(7478, 1);
                                                player.getInventory().addItem(8871, 1);
                                                
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                end();
                                        } else if (componentId == OPTION_4) {
                                        	if (player.getInventory().containsItem(14, 1)) {
                                                player.getInventory().addItem(24437, 1);
                                                player.donatorPoints -=2;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                end();
                                        } else if (componentId == OPTION_5) {
                                        	stage = 6;
                                        	sendOptionsDialogue("Please select an item.", "dice bag (10)", "scythe (1)", "golden scythe (2)", "Korasi's sword (2)", "Close Page");
                                        }
                                        } else if (stage == 6) {
                                            if (componentId == OPTION_1) {
                                            	if (player.donatorPoints >= 10) {
                                                    player.getInventory().addItem(15098, 1);
                                                    player.donatorPoints -=10;
                                                            end();
                                                    } else
                                                    	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                    end();
                                            } else if (componentId == OPTION_2) {
                                            	if (player.getInventory().containsItem(7478, 1)) {
            player.getInventory().deleteItem(7478, 1);
                                                    player.getInventory().addItem(10735, 1);
                                                    
                                                            end();
                                                    } else
                                                    	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                    end();
                                            } else if (componentId == OPTION_3) {
                                            	if (player.getInventory().containsItem(14, 1)) {
                                                    player.getInventory().addItem(22321, 1);
                                                    player.donatorPoints -=2;
                                                            end();
                                                    } else
                                                    	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                    end();
                                            } else if (componentId == OPTION_4) {
                                            	if (player.getInventory().containsItem(14, 1)) {
                                                    player.getInventory().addItem(19784, 1);
                                                    player.donatorPoints -=2;
                                                            end();
                                                    } else
                                                    	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this!");
                                                    end();
                                            } else if (componentId == OPTION_5) {
                                            	end();
                }
        }
        }


                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	
                	/*stage = 2;
                	sendOptionsDialogue("Please select an item.", "White Partyhat (5)", "Purple Partyhat (5)", "Santa Hat (5)", "Dragon Claws (3)", "Next Page");
                } else if (stage == 2) {
                        if (componentId == OPTION_1) {
                        	if (player.donatorPoints >= 5)) {
                                player.getInventory().addItem(1048, 1);
                                player.donatorPoints -=5;
                                        end();
                                } else
                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this");
                                end();
                        } else if (componentId == OPTION_2) {
                        	if (player.donatorPoints >= 5)) {
                                player.getInventory().addItem(1046, 1);
                                player.donatorPoints -=5;
                                        end();
                                } else
                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this");
                                end();
                        } else if (componentId == OPTION_3) {
                        	if (player.donatorPoints >= 5)) {
                                player.getInventory().addItem(1050, 1);
                                player.donatorPoints -=5;
                                        end();
                                } else
                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this");
                                end();
                        } else if (componentId == OPTION_4) {
                        	if (player.getInventory().containsItem(7478, 1)) {
            player.getInventory().deleteItem(7478, 1);
                                player.getInventory().addItem(14484, 1);
                                
                                        end();
                                } else
                                	player.getPackets().sendGameMessage("You don't have enough Random Event tokens to buy this");
                                end();
                        } else if (componentId == OPTION_5) {
                        	stage = 3;
                        } else if (stage == 3) {
                        	sendNPCDialogue(npcId, 9827, "Here is the next page of items");
                        	stage = 4;
                        	//sendOptionsDialogue("Please select an item.", "Torva Helm (1)", "Torva Platebody (2)", "Torva Platelegs (3)", "Zaryte Bow (4)", "Next Page");
                        }
                }
        }
        }*/
        						

                				
                        
                			
                			
                        
                        


        @Override
        public void finish() {

        }
}