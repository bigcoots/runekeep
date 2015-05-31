package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class DonatorShop extends Dialogue {

        private int npcId;

        @Override
        public void start() {
            sendEntityDialogue(SEND_2_TEXT_CHAT,
                    new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
                                    "Hello, Would you like to see the Donator shop? To check how many Donator points you have use the command ::checkpoints"}, IS_NPC, npcId, 9827);
        }
 
 
        @Override
        public void run(int interfaceId, int componentId) {
            if (stage == -1) {
                sendOptionsDialogue("Please select an item.", "Red Partyhat (5)", "Blue Partyhat (5)", "Yellow Partyhat (5)", "Green Partyhat (5)", "Next Page");
                stage = 1;
        } else if (stage == 1) {
                if (componentId == OPTION_1) {
                	if (player.donatorPoints >= 5) {
                        player.getInventory().addItem(1038, 1);
                        player.donatorPoints -=5;
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                        end();
                } else if (componentId == OPTION_2) {
                	if (player.donatorPoints >= 5) {
                        player.getInventory().addItem(1042, 1);
                        player.donatorPoints -=5;
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                        end();
                } else if (componentId == OPTION_3) {
                	if (player.donatorPoints >= 5) {
                        player.getInventory().addItem(1040, 1);
                        player.donatorPoints -=5;
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                        end();
                } else if (componentId == OPTION_4) {
                	if (player.donatorPoints >= 5) {
                        player.getInventory().addItem(1040, 1);
                        player.donatorPoints -=5;
                                end();
                        } else
                        	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                        end();
                } else if (componentId == OPTION_5) {
                        stage = 2;
                        	sendOptionsDialogue("Please select an item.", "White Partyhat (5)", "Purple Partyhat (5)", "duellist's cap (1)", "christmas cracker (5)", "Next Page");
                }
                    } else if (stage == 2) {
                            if (componentId == OPTION_1) {
                            	if (player.donatorPoints >= 5) {
                                    player.getInventory().addItem(1048, 1);
                                    player.donatorPoints -=5;
                                            end();
                                    } else
                                    	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                    end();
                            } else if (componentId == OPTION_2) {
                            	if (player.donatorPoints >= 5) {
                                    player.getInventory().addItem(1046, 1);
                                    player.donatorPoints -=5;
                                            end();
                                    } else
                                    	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                    end();
                            } else if (componentId == OPTION_3) {
                            	if (player.donatorPoints >= 1) {
                                    player.getInventory().addItem(20800, 1);
                                    player.donatorPoints -=1;
                                            end();
                                    } else
                                    	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                    end();
                            } else if (componentId == OPTION_4) {
                            	if (player.donatorPoints >= 5) {
                                    player.getInventory().addItem(962, 1);
                                    player.donatorPoints -=5;
											player.getPackets().sendGameMessage("Use the cracker on another player! Don't forget there is a chance you get nothing!");
                                            end();
                                    } else
                                    	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                    end();
                            } else if (componentId == OPTION_5) {
                                    stage = 3;
                                    	sendOptionsDialogue("Please select an item.", "Primal set (5)", "Cape set (4)", "Santa set (4)", "Grain sack (1)", "Next Page");
                            }
                                } else if (stage == 3) {
                                        if (componentId == OPTION_1) {
                                        	if (player.donatorPoints >= 5) {
                                                /*player.getInventory().addItem(16293, 1);
												 player.getInventory().addItem(16359, 1);
												  player.getInventory().addItem(16403, 1);
												 player.getInventory().addItem(16425, 1); 
												  player.getInventory().addItem(16667, 1); 
												  player.getInventory().addItem(16689, 1);
												  player.getInventory().addItem(16711, 1);
												   player.getInventory().addItem(16909, 1); 
												   player.getInventory().addItem(16955, 1); 
												   player.getInventory().addItem(17259, 1); 
                                                player.donatorPoints -=5;*/
player.getPackets().sendGameMessage("You can't buy this item yet.");
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                end();
                                        } else if (componentId == OPTION_2) {
                                        	if (player.donatorPoints >= 4) {
                                                player.getInventory().addItem(21435, 1);
												player.getInventory().addItem(24299, 1);
												player.getInventory().addItem(12645, 1);
												player.getInventory().addItem(24317, 1);
												player.getInventory().addItem(21406, 1);
												player.getInventory().addItem(21407, 1);
												player.getInventory().addItem(21408, 1);
                                                player.donatorPoints -=4;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                end();
                                        } else if (componentId == OPTION_3) {
                                        	if (player.donatorPoints >= 4) {
                                                player.getInventory().addItem(1050, 1);
												player.getInventory().addItem(14600, 1);
												player.getInventory().addItem(14601, 1);
												player.getInventory().addItem(14602, 1);
												player.getInventory().addItem(14603, 1);
												player.getInventory().addItem(14604, 1);
												player.getInventory().addItem(14605, 1);
                                                player.donatorPoints -=4;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                end();
                                        } else if (componentId == OPTION_4) {
                                        	if (player.donatorPoints >= 1) {
                                                player.getInventory().addItem(5607, 1);
                                                player.donatorPoints -=1;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                end();
                                        } else if (componentId == OPTION_5) {
                                            stage = 4;
                                        	sendOptionsDialogue("Please select an item.", "Santa Hat(3)", "Red H'ween Mask(3)", "Green H'ween Mask(3)", "Blue H'ween Mask(3)", "Next Page");
                                }
                                } else if (stage == 4) {
                                    if (componentId == OPTION_1) {
                                    	if (player.donatorPoints >= 3) {
                                            player.getInventory().addItem(1050, 1);
                                            player.donatorPoints -=3;
                                                    end();
                                            } else
                                            	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                            end();
                                    } else if (componentId == OPTION_2) {
                                    	if (player.donatorPoints >= 3) {
                                            player.getInventory().addItem(1057, 1);
                                            player.donatorPoints -=3;
                                                    end();
                                            } else
                                            	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                            end();
                                    } else if (componentId == OPTION_3) {
                                    	if (player.donatorPoints >= 3) {
                                            player.getInventory().addItem(1053, 1);
                                            player.donatorPoints -=3;
                                                    end();
                                            } else
                                            	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                            end();
                                    } else if (componentId == OPTION_4) {
                                    	if (player.donatorPoints >= 3) {
                                            player.getInventory().addItem(1055, 1);
                                            player.donatorPoints -=3;
                                                    end();
                                            } else
                                            	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                            end();
                                    } else if (componentId == OPTION_5) {
                                    	stage = 5;
                                    	sendOptionsDialogue("Please select an item.", "Gnome scarf (1)", "Boxing gloves (2)", "Crate with Zanik (1)", "Flaming Skull (2)", "Next Page");
                                    }
                                    } else if (stage == 5) {
                                        if (componentId == OPTION_1) {
                                        	if (player.donatorPoints >= 1) {
                                                player.getInventory().addItem(9470, 1);
                                                player.donatorPoints -=1;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                end();
                                        } else if (componentId == OPTION_2) {
                                        	if (player.donatorPoints >= 2) {
                                                player.getInventory().addItem(7671, 1);
												player.getInventory().addItem(7673, 1);
                                                player.donatorPoints -=2;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                end();
                                        } else if (componentId == OPTION_3) {
                                        	if (player.donatorPoints >= 1) {
                                                player.getInventory().addItem(8871, 1);
                                                player.donatorPoints -=1;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                end();
                                        } else if (componentId == OPTION_4) {
                                        	if (player.donatorPoints >= 2) {
                                                player.getInventory().addItem(24437, 1);
                                                player.donatorPoints -=2;
                                                        end();
                                                } else
                                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                end();
                                        } else if (componentId == OPTION_5) {
                                        	stage = 6;
                                        	sendOptionsDialogue("Please select an item.", "Dice bag (10)", "Scythe (1)", "Golden scythe (2)", "Korasi's sword (2)", "Close Page");
                                        }
                                        } else if (stage == 6) {
                                            if (componentId == OPTION_1) {
                                            	if (player.donatorPoints >= 10) {
                                                    player.getInventory().addItem(15098, 1);
                                                    player.donatorPoints -=10;
                                                            end();
                                                    } else
                                                    	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                    end();
                                            } else if (componentId == OPTION_2) {
                                            	if (player.donatorPoints >= 1) {
                                                    player.getInventory().addItem(10735, 1);
                                                    player.donatorPoints -=1;
                                                            end();
                                                    } else
                                                    	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                    end();
                                            } else if (componentId == OPTION_3) {
                                            	if (player.donatorPoints >= 2) {
                                                    player.getInventory().addItem(22321, 1);
                                                    player.donatorPoints -=2;
                                                            end();
                                                    } else
                                                    	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                                    end();
                                            } else if (componentId == OPTION_4) {
                                            	if (player.donatorPoints >= 2) {
                                                    player.getInventory().addItem(19784, 1);
                                                    player.donatorPoints -=2;
                                                            end();
                                                    } else
                                                    	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
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
                        	if (player.donatorPoints >= 5) {
                                player.getInventory().addItem(1048, 1);
                                player.donatorPoints -=5;
                                        end();
                                } else
                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                end();
                        } else if (componentId == OPTION_2) {
                        	if (player.donatorPoints >= 5) {
                                player.getInventory().addItem(1046, 1);
                                player.donatorPoints -=5;
                                        end();
                                } else
                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                end();
                        } else if (componentId == OPTION_3) {
                        	if (player.donatorPoints >= 5) {
                                player.getInventory().addItem(1050, 1);
                                player.donatorPoints -=5;
                                        end();
                                } else
                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
                                end();
                        } else if (componentId == OPTION_4) {
                        	if (player.donatorPoints >= 3) {
                                player.getInventory().addItem(14484, 1);
                                player.donatorPoints -=3;
                                        end();
                                } else
                                	player.getPackets().sendGameMessage("You don't have enough Donator points to buy this, you currently have: " +player.donatorPoints);
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