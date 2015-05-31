package com.rs.net.decoders.handlers;
import com.rs.game.player.content.FairyRing;
import com.rs.game.player.content.LoyaltyProgramme;
import com.rs.game.player.content.LoyaltyItem;
import java.util.HashMap;
import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Magic;
import com.rs.Settings;
import com.rs.cache.loaders.ConfigDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.CastleWars;
import com.rs.game.minigames.Crucible;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.EmotesManager;
import com.rs.utils.Hiscores;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.FightPitsViewingOrb;
import com.rs.game.player.actions.HomeTeleport;
import com.rs.game.player.actions.Rest;
import com.rs.game.player.actions.Smithing.ForgingInterface;
import com.rs.game.player.actions.summoning.SummonTrain;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.content.SummoningScroll;
import com.rs.game.player.content.AdventurersLog;
import com.rs.game.player.content.Combat;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.content.SquealOfFortune;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.construction.Room;
import com.rs.game.player.content.construction.RoomReference;
import com.rs.game.player.controlers.HouseControler;
import com.rs.game.player.dialogues.LevelUp;
import com.rs.game.player.dialogues.Transportation;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class ButtonHandler {

private static int[] boundChuncks;
private static Entity target;
private House house;

public static boolean canExchange(Player player){
	
if(player.getInventory().containsItem(8951, 1)){
player.getInventory().deleteItem(8951, 1);
player.getSkills().addXp(Skills.SLAYER, 
player.getSkills().getLevel(Skills.SLAYER) * 430 + Math.pow(player.getSkills().getLevel(0x16), 2));
player.increaseSlayer();
return true;

} else {

player.getPackets().sendGameMessage("<col=FFFF00>You dont not have any pieces of eight on you.</col>");
return false;

}

}
	public static void handleButtons(final Player player, InputStream stream,
			int packetId) {
		
		int interfaceHash = stream.readIntV2();
		int interfaceId = interfaceHash >> 16;
		if (Utils.getInterfaceDefinitionsSize() <= interfaceId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		if (!World.containsLobbyPlayer(player.getUsername())) {
		if (player.isDead() || !player.getInterfaceManager().containsInterface(interfaceId))
			return;
                }
		final int componentId = interfaceHash - (interfaceId << 16);
		if (componentId != 65535
				&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		final int slotId2 = stream.readUnsignedShort128();
		final int slotId = stream.readUnsignedShortLE128();
		if (!player.getControlerManager().processButtonClick(interfaceId,
				componentId, slotId, packetId))
			return;
		// squeal
		if (interfaceId == 1253) {
	 		if (componentId == 93) {
	 		SquealOfFortune.spin(player);
		} else if (componentId == 7) {
			player.getPackets().sendOpenURL("http://validus.net/forums/");
		} else if (componentId == 239) {
			SquealOfFortune.closeSOF(player);
	 	} else if (componentId == 192) {
	 		SquealOfFortune.claimItem(player);
			 player.closeInterfaces();
	 	} else if (componentId == 273){
	 		SquealOfFortune.openSOF(player);
		} else if (componentId == 258){
			SquealOfFortune.claimItem(player);
			SquealOfFortune.closeSOF(player);
		} else if (componentId == 106)
			SquealOfFortune.closeSOF(player);
			}
		
		if (interfaceId == 1139) {
			if (componentId == 18) {
				SquealOfFortune.openSOF(player);
			} else if (componentId == 23) {
                  LoyaltyProgramme.openShop(player);
			}
		}
		if (interfaceId == 1094) {
			if (slotId == 1) {
                   String value = stream.readString();
				ClansManager.createClan(player, value);
			}
		}
		if (interfaceId == 1089) {
		    if (componentId == 30)
			player.getTemporaryAttributtes().put("clanflagselection", slotId);
		    else if (componentId == 26) {
			Integer flag = (Integer) player.getTemporaryAttributtes().remove("clanflagselection");
			player.stopAll();
			if (flag != null)
			    ClansManager.setClanFlagInterface(player, flag);
		    }
		}
		if (interfaceId == 1096) {
		    if (componentId == 41)
		    	ClansManager.viewClammateDetails(player, slotId);
		    } else if (componentId == 94) {
		    	ClansManager.switchGuestsInChatCanEnterInterface(player);
		    } else if (componentId == 95) {
		    	ClansManager.switchGuestsInChatCanTalkInterface(player);
		    } else if (componentId == 96) {
		    	ClansManager.switchRecruitingInterface(player);
		    } else if (componentId == 97) {
		    	ClansManager.switchClanTimeInterface(player);
		    } else if (componentId == 124) {
		    	ClansManager.openClanMottifInterface(player);
		    } else if (componentId == 131) {
		    	ClansManager.openClanMottoInterface(player);
		    } else if (componentId == 240) {
		    	ClansManager.setTimeZoneInterface(player, -720 + slotId * 10);
		    } else if (componentId == 262) {
			player.getTemporaryAttributtes().put("editclanmatejob", slotId);
		    } else if (componentId == 276) {
			player.getTemporaryAttributtes().put("editclanmaterank", slotId);
		    } else if (componentId == 309) {
			ClansManager.kickClanmate(player);
		    } else if (componentId == 318) {
			ClansManager.saveClanmateDetails(player);
		    } else if (componentId == 290) {
			ClansManager.setWorldIdInterface(player, slotId);
		    } else if (componentId == 297) {
			ClansManager.openForumThreadInterface(player);
		    } else if (componentId == 346) {
			ClansManager.openNationalFlagInterface(player);
		    } else if (componentId == 113) {
			ClansManager.showClanSettingsClanMates(player);
		    } else if (componentId == 120) {
			ClansManager.showClanSettingsSettings(player);
		    } else if (componentId == 386) {
			ClansManager.showClanSettingsPermissions(player);
		    } else if (componentId >= 395 && componentId <= 475) {
			int selectedRank = (componentId - 395) / 8;
			if (selectedRank == 10) {
			    selectedRank = 125;
			} else if (selectedRank > 5) {
			    selectedRank = 100 + selectedRank - 6;
			ClansManager.selectPermissionRank(player, selectedRank);
		    } else if (componentId == 489) {
			ClansManager.selectPermissionTab(player, 1);
		    } else if (componentId == 498) {
			ClansManager.selectPermissionTab(player, 2);
		    } else if (componentId == 506) {
			ClansManager.selectPermissionTab(player, 3);
		    } else if (componentId == 514) {
			ClansManager.selectPermissionTab(player, 4);
		    } else if (componentId == 522) {
			ClansManager.selectPermissionTab(player, 5);
                      }
                  }
		if (interfaceId == 1105) {
		    if (componentId == 63 || componentId == 66) {
			ClansManager.setClanMottifTextureInterface(player, componentId == 66, slotId);
		   }  else if (componentId == 35) {
			ClansManager.openSetMottifColor(player, 0);
		    } else if (componentId == 80) {
			ClansManager.openSetMottifColor(player, 1);
		    } else if (componentId == 92) {
			ClansManager.openSetMottifColor(player, 2);
		    } else if (componentId == 104) {
			ClansManager.openSetMottifColor(player, 3);
		    } else if (componentId == 120) {
			player.stopAll();
                   }
		} if (interfaceId == 1110) {
		    if (componentId == 82) {
			ClansManager.joinClanChatChannel(player);
		    } else if (componentId == 75) {
			ClansManager.openClanDetails(player);
		    } else if (componentId == 78) {
			ClansManager.openClanSettings(player);
		    } else if (componentId == 91) {
			ClansManager.joinGuestClanChat(player);
		    } else if (componentId == 95) {
			ClansManager.banPlayer(player);
		    } else if (componentId == 99) {
			ClansManager.unbanPlayer(player);
		    } else if (componentId == 11) {
			ClansManager.unbanPlayer(player, slotId);
		    } else if (componentId == 109) {
			ClansManager.leaveClan(player);
		}
          }
		if (interfaceId == 1252) {
	 		if (componentId == 3) {
	 		SquealOfFortune.openSOF(player);
	 		} else if (componentId == 5) {
	 		player.getPackets().closeInterface(1252);
			player.getPackets().sendGameMessage("The icon will appear the next time you log in");
				}
			}

                 if (interfaceId == 1143) { //loyalty program
			if (componentId == 103) {
	 		player.closeInterfaces();
		} else if (componentId == 3) {
			player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, 8);
			LoyaltyProgramme.currentTab = 8;
		} else if (componentId == 1) {
			player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, -1);
			LoyaltyProgramme.currentTab = -1;
	 	} else if (componentId == 7) {
			player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, 1);
			LoyaltyProgramme.currentTab = 1;
	 	} else if (componentId == 8) {
			player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, 9);
			LoyaltyProgramme.currentTab = 9;
		} else if (componentId == 9) {
			player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, 2);
			LoyaltyProgramme.currentTab = 2;
		} else if (componentId == 10) {
			player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, 3);
			LoyaltyProgramme.currentTab = 3;
		} else if (componentId == 11) {
			player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, 4);
			LoyaltyProgramme.currentTab = 4;
		} else if (componentId == 12) {
			player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, 5);
			LoyaltyProgramme.currentTab = 5;
		} else if (componentId == 13) {
                        player.getPackets().sendConfig(LoyaltyProgramme.TAB_CONFIG, 6);
        player.getPackets().sendHideIComponent(interfaceId, componentId, false);
			LoyaltyProgramme.currentTab = 6;
		} else if (componentId == 1 && slotId == 27) {
				player.setLoyaltyPoints(player.getLoyaltyPoints() - 3500);
			player.getPackets().sendGameMessage("You successfully purchased aura: Tracker.");
            player.getInventory().addItem(22927, 1);
		} else if (componentId == 59) {
	 		player.closeInterfaces();
		} else if (componentId == 163) {
	 		player.closeInterfaces();
                  LoyaltyProgramme.openShop(player);
		} else if (componentId == 169) {
	 		LoyaltyProgramme.openPurchaseResultsInterface(player, "auras", slotId);
		        }
                }

		if (interfaceId == 751) {
	 		if (componentId == 14) {
				player.getInterfaceManager().sendInterface(275);
				int number = 0;
					for (int i = 0; i < 100; i++) {
				player.getPackets().sendIComponentText(275, i, "");
				}
				for(Player p5 : World.getPlayers()) {
				if(p5 == null)
				continue;
				number++;
				String titles = "";
				if (!(p5.isDonator()) || !(p5.isSuperDonator()) || !(p5.isExtremeDonator()) && p5.getRights() == 0) {
				titles = "[Player] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}
				if (p5.isDonator()) {
				titles = "[<img=8>Donator] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}
				if (p5.isSuperDonator()) {
				titles = "[<img=8>Super Donator] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}
				if (p5.isExtremeDonator()) {
				titles = "[<img=11>Extreme Donator] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}
				if (p5.isSupporter()) {
				titles = "<col=00ff48>[Support] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}
				if (p5.getRights() == 1) {
				titles = "<col=bcb8b8>[<img=0>Moderator] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}
				if (p5.getRights() == 2) {
				titles = "<col=ff1d1d>[<img=1>Administrator] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}
				if (p5.getDisplayName().equalsIgnoreCase("max")) {
				titles = "[<img=1> <col=0000FF>Owner</col>] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}
				if (p5.getDisplayName().equalsIgnoreCase("plasticboy22")) {
				titles = "[<img=1> <col=0000FF>Co-Owner</col>] [Level: "+ p5.getSkills().getCombatLevel() +"] ";
				}				
				player.getPackets().sendIComponentText(275, (13+number), titles + ""+ p5.getDisplayName());
				}
				player.getPackets().sendIComponentText(275, 1, "Validus Players");
				player.getPackets().sendIComponentText(275, 10, " ");
				player.getPackets().sendIComponentText(275, 11, "Players Online: "+number);
				player.getPackets().sendIComponentText(275, 12, "Who's online?");
				player.getPackets().sendGameMessage(
						"There are currently " + World.getPlayers().size()
								+ " players playing " + Settings.SERVER_NAME
								+ ".");
				}
			}
		 if (interfaceId == 578) { // Fairy Ring Warning
	        if (componentId == 15) {
	        FairyRing.warningInterface(player);
	        }
	        else if (componentId == 16) {
	        	player.closeInterfaces();
	        }
		} else if (interfaceId == 734) { //Fairy rings                                
	        if (componentId == 23) { //1st Plus
                if (player.firstColumn == 2) {
                    player.firstColumn = 4;
            } else if (player.firstColumn == 3) {
                player.firstColumn = 3;
        }    else if (player.firstColumn >= 4) {
            player.firstColumn = 1;
    }
        else{
            player.firstColumn++;
        }
	        } else if (componentId == 24) { //1st Subtract
	                if (player.firstColumn <= 1) {
	                        player.firstColumn = 4;
	                } else {
	                        player.firstColumn--;
	                }
	        } else if (componentId == 25) { //2nd Plus
                if (player.secondColumn == 2) {
                    player.secondColumn = 4;
            } else if (player.secondColumn == 3) {
                player.secondColumn = 3;
        }    else if (player.secondColumn >= 4) {
            player.secondColumn = 1;
    }
            else{
                        player.secondColumn++;
                }
	        } else if (componentId == 26) { //2nd Subtract
	                if (player.secondColumn <= 1) {
	                        player.secondColumn = 4;
	                } else {
	                        player.secondColumn--;
	                }
	        } else if (componentId == 27) { //3rd Plus
                if (player.thirdColumn == 2) {
                        player.thirdColumn = 4;
                } else if (player.thirdColumn == 3) {
                    player.thirdColumn = 3;
            }    else if (player.thirdColumn >= 4) {
                player.thirdColumn = 1;
        }
                else{
	                        player.thirdColumn++;
	                }
	        } else if (componentId == 28) { //3rd Subtract
	                if (player.thirdColumn <= 1) {
	                        player.thirdColumn = 4;
	                } else {
	                        player.thirdColumn--;
	                }
	        } else if (componentId == 21) { //Confirm
	                FairyRing.ringTele(player, player.firstColumn, player.secondColumn, player.thirdColumn);
	        }	
		 }  		
		if (interfaceId == 397) {
			switch (componentId) {
			case 45:
				if(!player.getInventory().containsItem(960, 4)) {
					player.getPackets().sendGameMessage("You need 4 planks to make a armchair.");
				} else {
					player.getPackets().closeInterface(397);
					player.setNextAnimation(new Animation(898));
					player.getInventory().deleteItem(960, 4);
					player.getSkills().addXp(Skills.CONSTRUCTION, 10000);
					player.getPackets().sendGameMessage("You make a armchair");
					break;
			}
			case 46:
				if(!player.getInventory().containsItem(8778, 4)) {
					player.getPackets().sendGameMessage("You need 4 oak planks to make a bookcase");
				} else {
					player.getPackets().closeInterface(397);
					player.getInventory().deleteItem(8778, 4);
					player.getSkills().addXp(Skills.CONSTRUCTION, 20000);
					player.setNextAnimation(new Animation(898));
					player.getPackets().sendGameMessage("You make a bookcase");
					break;
				}
			case 47:
				if(!player.getInventory().containsItem(8778, 5)) {
					player.getPackets().sendGameMessage("You need 5 oak planks to make a beer barrel");
				} else {
					player.getPackets().closeInterface(397);
					player.getInventory().deleteItem(8778, 5);
					player.getSkills().addXp(Skills.CONSTRUCTION, 21000);
					player.setNextAnimation(new Animation(898));
					player.getPackets().sendGameMessage("You make a beer barrel");
					player.increaseCon();
					break;
				}
			case 48:
				if(!player.getInventory().containsItem(8778, 6)) {
					player.getPackets().sendGameMessage("You need 6 oak planks to make a Kitchen Table");
				} else {
					player.getPackets().closeInterface(397);
					player.getInventory().deleteItem(8778, 6);
					player.getSkills().addXp(Skills.CONSTRUCTION, 25000);
					player.setNextAnimation(new Animation(898));
					player.getPackets().sendGameMessage("You make a Kitchen Table");
					player.increaseCon();
					break;
				}
			case 49:
				if(!player.getInventory().containsItem(8778, 6)) {
					player.getPackets().sendGameMessage("You need 6 oak planks to make a Dining Table");
				} else {
					player.getPackets().closeInterface(397);
					player.getInventory().deleteItem(8778, 6);
					player.getSkills().addXp(Skills.CONSTRUCTION, 26000);
					player.setNextAnimation(new Animation(898));
					player.getPackets().sendGameMessage("You make a Dining Table");
					player.increaseCon();
					break;
				}

				
			}
		}
		
		if (interfaceId == 276) {
	 		if (componentId == 32) {
			if (canExchange(player));
			} else {
				player.getPackets().sendGameMessage("You can only claim slayer xp.");
				}
			}
		
		if (interfaceId == 190 && componentId == 15) {
	 		if (slotId == 68) {
	 			if (player.spokeToWarrior == false && player.spokeToShamus == false) {
			player.getInterfaceManager().sendInterface(275);
			player.getPackets().sendIComponentText(275, 1, "Lost-City Quest");
			player.getPackets().sendIComponentText(275, 10, "");
			player.getPackets().sendIComponentText(275, 11, "Speak to the Warrior West of Draynor");
			player.getPackets().sendIComponentText(275, 12, "<u>Requirements</u>");
			player.getPackets().sendIComponentText(275, 13, "<col=ffff00>31 Crafting, 36 Woodcutting</col>");
			player.getPackets().sendIComponentText(275, 14, "-=-Tips-=-");
			player.getPackets().sendIComponentText(275, 15, "Use the 'Quests & Minigames' teleport at home to start the quest.");
			player.getPackets().sendIComponentText(275, 16, "The lodestone works, remember to take full use of it.");
			player.getPackets().sendIComponentText(275, 17, "You will need the skills required to complete the quest");
			player.getPackets().sendIComponentText(275, 18, "The Monk Of Entrana removes everything in your inventory.");
			player.getPackets().sendIComponentText(275, 19, "");
			player.getPackets().sendIComponentText(275, 20, "");
	 			} else if (player.spokeToWarrior == true && player.spokeToShamus == false) {
	 				player.getInterfaceManager().sendInterface(275);
	 				player.getPackets().sendIComponentText(275, 1, "Lost-City Quest");
	 				player.getPackets().sendIComponentText(275, 10, "");
	 				player.getPackets().sendIComponentText(275, 11, "Shamus appears to be in one of the trees around this location.");
	 				player.getPackets().sendIComponentText(275, 12, "The Warrior told me the tree displays 'Chop Tree'");
	 				player.getPackets().sendIComponentText(275, 13, "");
	 				player.getPackets().sendIComponentText(275, 14, "-=-Tips-=-");
	 				player.getPackets().sendIComponentText(275, 15, "Use the 'Quests & Minigames' teleport at home to start the quest.");
	 				player.getPackets().sendIComponentText(275, 16, "The lodestone works, remember to take full use of it.");
	 				player.getPackets().sendIComponentText(275, 17, "You will need the skills required to complete the quest");
	 				player.getPackets().sendIComponentText(275, 18, "The Monk Of Entrana removes everything in your inventory.");
	 				player.getPackets().sendIComponentText(275, 19, "");
	 				player.getPackets().sendIComponentText(275, 20, "");
	 			} else if (player.spokeToWarrior == true && player.spokeToShamus == true) {
	 				player.getInterfaceManager().sendInterface(275);
	 				player.getPackets().sendIComponentText(275, 1, "Lost-City Quest");
	 				player.getPackets().sendIComponentText(275, 10, "");
	 				player.getPackets().sendIComponentText(275, 11, "I should go find the Monk of Entrana, Who is located at Port Sarim.");
	 				player.getPackets().sendIComponentText(275, 12, "");
	 				player.getPackets().sendIComponentText(275, 13, "");
	 				player.getPackets().sendIComponentText(275, 14, "-=-Tips-=-");
	 				player.getPackets().sendIComponentText(275, 15, "Use the 'Quests & Minigames' teleport at home to start the quest.");
	 				player.getPackets().sendIComponentText(275, 16, "The lodestone works, remember to take full use of it.");
	 				player.getPackets().sendIComponentText(275, 17, "You will need the skills required to complete the quest");
	 				player.getPackets().sendIComponentText(275, 18, "The Monk Of Entrana removes everything in your inventory.");
	 				player.getPackets().sendIComponentText(275, 19, "");
	 				player.getPackets().sendIComponentText(275, 20, "");
	 			} else if (player.spokeToWarrior == true && player.spokeToShamus == true && player.spokeToMonk == true) {
	 				player.getInterfaceManager().sendInterface(275);
	 				player.getPackets().sendIComponentText(275, 1, "Lost-City Quest");
	 				player.getPackets().sendIComponentText(275, 10, "The other side of Entrana is a ladder which leads to a cave");
	 				player.getPackets().sendIComponentText(275, 11, "I should go down the ladder and search for the dramen tree.");
	 				player.getPackets().sendIComponentText(275, 12, "In order to chop the dramen tree I must have a axe.");
	 				player.getPackets().sendIComponentText(275, 13, "The zombies must drop a axe of some sort.");
	 				player.getPackets().sendIComponentText(275, 14, "-=-Tips-=-");
	 				player.getPackets().sendIComponentText(275, 15, "Use the 'Quests & Minigames' teleport at home to start the quest.");
	 				player.getPackets().sendIComponentText(275, 16, "The lodestone works, remember to take full use of it.");
	 				player.getPackets().sendIComponentText(275, 17, "You will need the skills required to complete the quest");
	 				player.getPackets().sendIComponentText(275, 18, "The Monk Of Entrana removes everything in your inventory.");
	 				player.getPackets().sendIComponentText(275, 19, "");
	 				player.getPackets().sendIComponentText(275, 20, "");
	 			} else if (player.spokeToWarrior == true && player.spokeToShamus == true && player.spokeToMonk == true && player.lostCity == 1) {
	 				player.getInterfaceManager().sendInterface(275);
	 				player.getPackets().sendIComponentText(275, 1, "Lost-City Quest");
	 				player.getPackets().sendIComponentText(275, 10, "");
	 				player.getPackets().sendIComponentText(275, 11, "");
	 				player.getPackets().sendIComponentText(275, 12, "Congratulations Quest Complete!");
	 				player.getPackets().sendIComponentText(275, 13, "");
	 				player.getPackets().sendIComponentText(275, 14, "");
	 				player.getPackets().sendIComponentText(275, 15, "");
	 				player.getPackets().sendIComponentText(275, 16, "");
	 				player.getPackets().sendIComponentText(275, 17, "");
	 				player.getPackets().sendIComponentText(275, 18, "");
	 				player.getPackets().sendIComponentText(275, 19, "");
	 				player.getPackets().sendIComponentText(275, 20, "");
	 			}
			}
	 		if (slotId == 34) {
				player.getInterfaceManager().sendInterface(275);
				player.getPackets().sendIComponentText(275, 1, "Dwarf Cannon Quest");
				player.getPackets().sendIComponentText(275, 10, "");
				player.getPackets().sendIComponentText(275, 11, "Speak to the Captain Lawgof at the Kingdom of Kandarin");
				player.getPackets().sendIComponentText(275, 12, "I have fixed "+player.fixedRailings+"/6 of the railings.");
				player.getPackets().sendIComponentText(275, 13, "");
				player.getPackets().sendIComponentText(275, 14, "");
				player.getPackets().sendIComponentText(275, 15, "");
				player.getPackets().sendIComponentText(275, 16, "");
				player.getPackets().sendIComponentText(275, 17, "");
				if (player.fixedRailings >= 6) {
				player.getPackets().sendIComponentText(275, 12, "<str>I have fixed "+player.fixedRailings+"/6 of the railings.");
				}
				if (player.completedRailingTask == true) {
				player.getPackets().sendIComponentText(275, 14, "I should find 'Nulodion' who is located at the Dwarven Mine.");
				}
				if (player.completedDwarfCannonQuest == true) {
				player.getPackets().sendIComponentText(275, 11, "<str>Speak to the Captain Lawgof at the Kingdom of Kandarin");
				player.getPackets().sendIComponentText(275, 14, "<str>I should find 'Nulodion' who is located at the Dwarven Mine.");
				player.getPackets().sendIComponentText(275, 15, "");
				player.getPackets().sendIComponentText(275, 16, "<u>Quest Complete.</u>");
				player.getPackets().sendIComponentText(275, 17, "Use a steel bar on any furnace to make cannonballs.");
				player.getPackets().sendIComponentText(275, 18, "You can now purchase a cannon from Nulodion.");
				}
				player.getPackets().sendIComponentText(275, 18, "");
				player.getPackets().sendIComponentText(275, 19, "");
				player.getPackets().sendIComponentText(275, 20, "");
	 		}
		}
		
		if (interfaceId == 548 || interfaceId == 746) {
			if ((interfaceId == 548 && componentId == 148)
					|| (interfaceId == 746 && componentId == 199)) {
				if (player.getInterfaceManager().containsScreenInter()
						|| player.getInterfaceManager()
						.containsInventoryInter()) {
					// TODO cant open sound
					player.getPackets()
					.sendGameMessage(
							"Please finish what you're doing before opening the world map.");
					return;
				}
				// world map open
				player.getPackets().sendWindowsPane(755, 0);
				int posHash = player.getX() << 14 | player.getY();
				player.getPackets().sendGlobalConfig(622, posHash); // map open
				// center
				// pos
				player.getPackets().sendGlobalConfig(674, posHash); // player
				// position
			} else if ((interfaceId == 548 && componentId == 17)
					|| (interfaceId == 746 && componentId == 54)) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getSkills().switchXPDisplay();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getSkills().switchXPPopup();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getSkills().setupXPCounter();
				else if ((interfaceId == 746 && componentId == 207) || (interfaceId == 548 && componentId == 159)) {
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
						if (player.getInterfaceManager().containsScreenInter()) {
							player.getPackets()
							.sendGameMessage(
									"Please finish what you're doing before opening the price checker.");
							return;
						}
						player.stopAll();
						player.getPriceCheckManager().openPriceCheck();
					}
				}
				/**
				 * Start of Money Pouch
				 */
			} else if ((interfaceId == 746 && componentId == 207) || (interfaceId == 548 && componentId == 159) || (interfaceId == 548 && componentId == 194)) {
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
						if (!player.getInterfaceManager().containsScreenInter()) {
							player.getPackets().sendRunScript(5557, 1);
							player.refreshMoneyPouch();
						} else
							player.getPackets().sendGameMessage("Please finish what you are doing.");
					} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET){
						player.getTemporaryAttributtes().put("remove_X_Amount_money", 995);
						player.getTemporaryAttributtes().put("remove_money", Boolean.TRUE);
						player.getPackets().sendRunScript(108, new Object[] { "                          Your money pouch contains " +player.getFormattedNumber(player.money) + " coins." + "                           How many would you like to withdraw?"});
					} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET){
						player.getPackets().sendGameMessage("Your money pouch currently contains " + player.getFormattedNumber(player.money) + " coins.");
					} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
						if (player.getInterfaceManager().containsScreenInter()) {
							player.getPackets()
							.sendGameMessage(
									"Please finish what you're doing before opening the price checker.");
							return;
						}
						player.stopAll();
						player.getPriceCheckManager().openPriceCheck();
					}


					
				/*}else if ((interfaceId == 746 && componentId == 207) || (interfaceId == 548 && componentId == 159)
					|| (interfaceId == 548 && componentId == 194)) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (!player.getInterfaceManager().containsScreenInter()) {
						player.getPackets().sendRunScript(5557, 1);
						player.refreshMoneyPouch();
					} else
						player.getPackets().sendGameMessage("Please finish first with what your doing.");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET){
					player.getTemporaryAttributtes().put("remove_X_money", 995);
					player.getTemporaryAttributtes().put("remove_money", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "                          Your money pouch contains " + player.money + " coins." + "                           How many would you like to withdraw?"});
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET){
					player.getPackets().sendGameMessage("Your money pouch currently contains " + player.money + " coins.");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					if (player.getInterfaceManager().containsScreenInter()) {
						player.getPackets()
						.sendGameMessage(
								"Please finish what you're doing before opening the price checker.");
						return;
					}
					player.stopAll();
					player.getPriceCheckManager().openPriceCheck();
				}*/
			}
		} else if (interfaceId == 34) {// notes interface
			/*	switch(componentId) {
			case 35:
			case 37:
			case 39:
			case 41:
				Note current = (Note) player.getTemporaryAttributtes().get("curNote");
				current.setColour(componentId - (34 + (componentId == 35 ? 0 : componentId + 1))); 
				player.getNotes().refresh();
				player.getPackets().sendHideIComponent(34, 16, true);
				break;
			case 3:
				player.getPackets().sendRunScript(109, new Object[] { "Please enter the note text." });
				player.getTemporaryAttributtes().put("entering_note", Boolean.TRUE);
				break;
			case 9:
				switch(packetId) {
				case WorldPacketsDecoder.ACTION_BUTTON1_PACKET:
					Note note = player.getNotes().getNotes().get(slotId);
					if (player.getTemporaryAttributtes().get("curNote") == note) {
						player.getTemporaryAttributtes().remove("curNote");
						player.getPackets().sendConfig(1439, -1);
						return;
					} else {
						player.getTemporaryAttributtes().put("curNote", note);
						player.getPackets().sendConfig(1439, slotId);
					}
					break;
				case WorldPacketsDecoder.ACTION_BUTTON2_PACKET:
					player.getTemporaryAttributtes().put("curNote", player.getNotes().getNotes().get(slotId));
					player.getPackets().sendRunScript(109, new Object[] { "Please edit the note text." });
					player.getTemporaryAttributtes().put("editing_note", Boolean.TRUE);
					player.getPackets().sendConfig(1439, slotId);
					break;
				case WorldPacketsDecoder.ACTION_BUTTON3_PACKET:
					player.getTemporaryAttributtes().put("curNote", player.getNotes().getNotes().get(slotId));
					player.getPackets().sendHideIComponent(34, 16, false);
					player.getPackets().sendConfig(1439, slotId);
					break;
				}
				break;
			case 8:
				Note note = (Note) player.getTemporaryAttributtes().get("curNote");
				player.getNotes().remove(note);
				break;
			}*/
		} else if (interfaceId == 182) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6 || componentId == 13)
				if (!player.hasFinished())
					player.logout(componentId == 6);
					Hiscores.saveHighScore(player);
		} else if (interfaceId == 1165) {
			//if (componentId == 22)
			//Summoning.closeDreadnipInterface(player);
		} else if (interfaceId == 880) {
			if (componentId >= 7 && componentId <= 19)
				Familiar.setLeftclickOption(player, (componentId - 7) / 2);
			else if (componentId == 21)
				Familiar.confirmLeftOption(player);
			else if (componentId == 25)
				Familiar.setLeftclickOption(player, 7);
		} else if (interfaceId == 662) {
			if (player.getFamiliar() == null) {
				if (player.getPet() == null) {
					return;
				}
				if (componentId == 49) 
					player.getPet().call();
				else if (componentId == 51) 
					player.getDialogueManager().startDialogue("DismissD");
				return;
			}
			if (componentId == 49)
				player.getFamiliar().call();
			else if (componentId == 51)
				player.getDialogueManager().startDialogue("DismissD");
			else if (componentId == 67)
				player.getFamiliar().takeBob();
			else if (componentId == 69)
				player.getFamiliar().renewFamiliar();
			else if (componentId == 74) {
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
					player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().hasSpecialOn())
					player.getFamiliar().submitSpecial(player);
			}
		} else if (interfaceId == 747) {
			if (componentId == 8) {
				Familiar.selectLeftOption(player);
			} else if (player.getPet() != null) {
				if (componentId == 11 || componentId == 20) {
					player.getPet().call();
				} else if (componentId == 12 || componentId == 21) {
					player.getDialogueManager().startDialogue("DismissD");
				} else if (componentId == 10 || componentId == 19) {
					player.getPet().sendFollowerDetails();
				}
			} else if (player.getFamiliar() != null) {
				if (componentId == 11 || componentId == 20)
					player.getFamiliar().call();
				else if (componentId == 12 || componentId == 21)
					player.getDialogueManager().startDialogue("DismissD");
				else if (componentId == 13 || componentId == 22)
					player.getFamiliar().takeBob();
				else if (componentId == 14 || componentId == 23)
					player.getFamiliar().renewFamiliar();
				else if (componentId == 19 || componentId == 10)
					player.getFamiliar().sendFollowerDetails();
				else if (componentId == 18) {
					if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
						player.getFamiliar().setSpecial(true);
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(player);
				}
			}
		}else if (interfaceId == 309) 
			PlayerLook.handleHairdresserSalonButtons(player, componentId, slotId);
		else if (interfaceId == 729) 
			PlayerLook.handleThessaliasMakeOverButtons(player, componentId, slotId);
		else if (interfaceId == 187) {
			if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getMusicsManager().playAnotherMusic(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getMusicsManager().sendHint(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getMusicsManager().addToPlayList(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getMusicsManager().removeFromPlayList(slotId / 2);
			} else if (componentId == 4)
				player.getMusicsManager().addPlayingMusicToPlayList();
			else if (componentId == 10)
				player.getMusicsManager().switchPlayListOn();
			else if (componentId == 11)
				player.getMusicsManager().clearPlayList();
			else if (componentId == 13)
				player.getMusicsManager().switchShuffleOn();
		} else if (interfaceId == 275) {
			if (componentId == 14) {
				player.getPackets().sendOpenURL(Settings.WEBSITE_LINK);
			}
		} else if ((interfaceId == 590 && componentId == 8) || interfaceId == 464) {
			player.getEmotesManager().useBookEmote(interfaceId == 464 ? componentId : EmotesManager.getId(slotId, packetId));
		} else if (interfaceId == 192) {
			if (componentId == 2)
				player.getCombatDefinitions().switchDefensiveCasting();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 11)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId == 13)
				player.getCombatDefinitions().switchShowSkillSpells();
			else if (componentId >= 15 & componentId <= 17)
				player.getCombatDefinitions().setSortSpellBook(componentId - 15);
			else
				Magic.processNormalSpell(player, componentId, packetId);
		} else if (interfaceId == 334) {
			if(componentId == 22)
				player.closeInterfaces();
			else if (componentId == 21)
				player.getTrade().accept(false);
		} else if (interfaceId == 335) {
			if(componentId == 18)
				player.getTrade().accept(true);
			else if (componentId == 53) {
				player.getTemporaryAttributtes().put("add_To_Trade", 995);
				player.getTemporaryAttributtes().put("add_coins_to_trade", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] { "                          Your money pouch contains " +player.getFormattedNumber(player.money) + " coins." + "                           How much would you like to offer?"});
			}
		if (interfaceId == 938) {
			if (componentId == 60)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(120, 4295, 0));
			}
			else if(componentId == 20) 
				player.closeInterfaces();
			else if(componentId == 32) {
				if(packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().removeItem(slotId, 1);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getTrade().removeItem(slotId, 5);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getTrade().removeItem(slotId, 10);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getTrade().removeItem(slotId, Integer.MAX_VALUE);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("trade_item_X_Slot", slotId);
					player.getTemporaryAttributtes().put("trade_isRemove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				}else if(packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getTrade().sendValue(slotId, false);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getTrade().sendExamine(slotId, false);
			}else if(componentId == 35) {
				if(packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().sendValue(slotId, true);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getTrade().sendExamine(slotId, true);
			}
		} else if (interfaceId == 336) {
			if(componentId == 0) {
				if(packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().addItem(slotId, 1);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getTrade().addItem(slotId, 5);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getTrade().addItem(slotId, 10);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getTrade().addItem(slotId, Integer.MAX_VALUE);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("trade_item_X_Slot", slotId);
					player.getTemporaryAttributtes().remove("trade_isRemove");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				}else if(packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getTrade().sendValue(slotId);
				else if(packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 300) {
			ForgingInterface.handleIComponents(player, componentId);
		} else if (interfaceId == 206) {
			if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().removeItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("pc_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			}
                 else if (interfaceId == 666) {
				if (componentId == 16) {
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						SummoningScroll.createScroll(player, slotId2, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						SummoningScroll.createScroll(player, slotId2, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						SummoningScroll.createScroll(player, slotId2, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						SummoningScroll.createScroll(player, slotId2,
								Integer.MAX_VALUE);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						SummoningScroll.createScroll(player, slotId2, 28);// x
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
						player.getPackets().sendGameMessage(
								"You currently need "
										+ ItemDefinitions.getItemDefinitions(
												slotId2)
												.getCreateItemRequirements());
					}
				}
				if (componentId == 18) {
				player.getInterfaceManager().sendInterface(672);
				}
				}
		} else if (interfaceId == 672){
			if (componentId == 16) {
				switch(slotId) {
				case 2:
					SummonTrain.CreatePouch(player, 1, 7,12158,2859,-1,12047,4800);
					return;
				case 7:
					SummonTrain.CreatePouch(player, 4, 8,12158,2138,-1,12043,9300);
					break;
				case 12:
					SummonTrain.CreatePouch(player, 10, 8,12158,6291,-1,12059,12600);
					break;
				case 177:
					SummonTrain.CreatePouch(player, 52, 12,12158,9978,-1,12007,12600);
					break;
				case 17:
					SummonTrain.CreatePouch(player, 13, 9,12158,3363,-1,12019,12600);
					break;
				case 22:
					SummonTrain.CreatePouch(player, 16, 7,12158,440,-1,12009,12600);
					break;
				case 27:
					SummonTrain.CreatePouch(player, 17, 1,12158,6319,-1,12778,12000);
					break;
				case 32:
					SummonTrain.CreatePouch(player, 18, 45,12159,1783,-1,12049,2500);
					break;
				case 37:
					SummonTrain.CreatePouch(player, 19, 57,12160,3095,-1,12055,12000);
					break;
				case 42:
					SummonTrain.CreatePouch(player, 22, 64,12160,12168,-1,12808,15000);
					break;
				case 47:
					SummonTrain.CreatePouch(player, 23, 75,12163,2134,-1,12067,16000);
					break;
				case 52:
					SummonTrain.CreatePouch(player, 25, 51,12163,3138,-1,12063,10000);
					break;
				case 57:
					SummonTrain.CreatePouch(player, 28, 47,12159,6032,-1,12091,10000);
					break;
				case 62:
					SummonTrain.CreatePouch(player, 29, 84,12163,9976,-1,12800,10000);
					break;
				case 67:
					SummonTrain.CreatePouch(player, 31, 81,12160,3325,-1,12053,10000);
					break;
				case 72:
					SummonTrain.CreatePouch(player, 32, 84,12160,12156,-1,12065,10000);
					break;
				case 77:
					SummonTrain.CreatePouch(player, 33, 72,12159,1519, -1,12021,12000);
					break;
				case 82:
					SummonTrain.CreatePouch(player, 34, 74,12159,12164,-1,12818,12000);
					break;
				case 87:
					SummonTrain.CreatePouch(player, 34, 74, 12163,12166,-1, 12780,12000);
					break;
				case 92:
					SummonTrain.CreatePouch(player, 34, 74, 12163, 12167,-1,12798,20000);
					break;
				case 97:
					SummonTrain.CreatePouch(player, 34, 74, 12163,12165,-1,12814,22000);
					break;
				case 107:
					SummonTrain.CreatePouch(player, 40, 11, 12158, 6010,-1,12087,22000);
					break;
				case 117:
					SummonTrain.CreatePouch(player, 42, 104, 12160, 12134, -1, 12051, 22000);
					break;
				case 122:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12109, -1, 12095, 22000);
					break;
				case 127:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12111, -1, 12097, 22000);
					break;
				case 132:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12113, -1, 12099, 22000);
					break;
				case 137:
					SummonTrain.CreatePouch(player, 43, 88, 12159, 12115, -1, 12101, 22000);
					break;
				case 347:
					SummonTrain.CreatePouch(player, 85, 150, 12160, 10149, 1, 12776, 22000);
					break;
				case 387:
					SummonTrain.CreatePouch(player, 99, 178, 12160, 1119, -1, 12790, 22000);
					break;
				case 382:
					SummonTrain.CreatePouch(player, 96, 211, 12160, 10818, -1, 12093, 23000);
					break;
				case 242:
					SummonTrain.CreatePouch(player, 66, 152, 12163, 2359, -1, 12079, 22000);
					break;
				case 252:
					SummonTrain.CreatePouch(player, 67, 1, 12158, 7939, -1, 12031, 23000);
					break;
				default:
					player.getPackets().sendGameMessage("Report on forum this pouch is not working.");
					//logger.debug("summonButton: "+buttonId2+".");
					break;
				}
			} 
				if (componentId == 19) {
					SummoningScroll.sendInterface(player);
				}
		} else if (interfaceId == 207) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().addItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("pc_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 665) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
					.addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bob_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 1083) {
			 switch (componentId) {
			 case 387:
					player.getInterfaceManager().sendInterface(1083);
					player.getPackets().sendIComponentText(1083, 136, "Crude Chair");
					player.getPackets().sendIComponentText(1083, 137, "Lvl 1");
					player.getPackets().sendIComponentText(1083, 132, "Wooden Chair");
					player.getPackets().sendIComponentText(1083, 133, "Lvl 8");
					player.getPackets().sendIComponentText(1083, 128, "Oak Chair");
					player.getPackets().sendIComponentText(1083, 129, "Lvl 19");
					player.getPackets().sendIComponentText(1083, 124, "Teak Armchair");
					player.getPackets().sendIComponentText(1083, 125, "Lvl 35");
					player.getPackets().sendIComponentText(1083, 85, "Click on the option you would like to make.");
					player.getPackets().sendIComponentText(1083, 87, "Then click the button 'Learn' to create the object you selected.");
					player.getPackets().sendIComponentText(1083, 89, "");
					player.getPackets().sendIComponentText(1083, 120, "");
					player.getPackets().sendIComponentText(1083, 116, "");
					player.getPackets().sendIComponentText(1083, 112, "");
					player.getPackets().sendIComponentText(1083, 108, "");
					player.getPackets().sendIComponentText(1083, 434, "");
					player.getPackets().sendIComponentText(1083, 445, "");
					player.getPackets().sendIComponentText(1083, 456, "");
			 case 74:
			 case 69:
			 case 64:
			 }
		} else if (interfaceId == 402) {
			switch (componentId) {
            case 93:
    			//player.getRoomConstruction().buildRoom(Room.PARLOUR, componentId);
            }
		} else if (interfaceId == 1206) {
			if (componentId == 5) {
				player.closeInterfaces();
			}
		} else if (interfaceId == 506) {
            switch (componentId) {
            case 2://player rankings
            	player.getDialogueManager().startDialogue("Panel");
            		break;
            }
			switch (componentId) {
			case 4:
				player.setNextWorldTile(new WorldTile(player.getX(), player.getY(), player.getPlane() + 1));
			break;			
			case 6:
				CastleWars.setCape(player, new Item(6570, 1));
				CastleWars.setBoots(player, new Item(1837, 1));
				CastleWars.setWeapon(player, new Item(4151, 1));
			break;
			case 8:
				player.setNextWorldTile(new WorldTile(player.getX(), player.getY(), player.getPlane() - 1));
			break;
			case 10://Torva
				CastleWars.setPlate(player, new Item(20139, 1));
				CastleWars.setLegs(player, new Item(20143, 1));
				CastleWars.setHood(player, new Item(20135, 1));
			break;
			case 14://vesta
				CastleWars.setPlate(player, new Item(13887, 1));
				CastleWars.setLegs(player, new Item(13893, 1));
				CastleWars.setHood(player, new Item(10828, 1));
			break;
			case 12://Dharok
				CastleWars.setBoots(player, new Item(21787, 1));
				CastleWars.setPlate(player, new Item(4720, 1));
				CastleWars.setLegs(player, new Item(4722, 1));
				CastleWars.setHood(player, new Item(4716, 1));
				CastleWars.setWeapon(player, new Item(4718, 1));
			break;
		}
		} else if (interfaceId == 671) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
					.removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("bob_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
 		} else if (interfaceId == 506) {
			switch (componentId) {
			case 2://item command
			player.getTemporaryAttributtes().put("remove_X_money", 995);
			player.getTemporaryAttributtes().put("remove_money", Boolean.TRUE);
			player.getPackets().sendRunScript(108, new Object[] { "Enter Item Id"});
			break;
			}
			switch (componentId) {
			case 10:
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2708, 3709, 0));
			break;			
			}
			switch (componentId) {
			case 14:
			player.setSpins(10);
			player.getPackets().sendGameMessage("You have recived some spins!");
			break;			
			}
			switch (componentId) {
			case 6:
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3087, 3503, 0));
			break;			
			}
			switch (componentId) {
			case 8:
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2868,	5355, 2));
			break;			
			}
			switch (componentId) {
			case 12:
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2838, 5301, 2));
			break;			
			}
			switch (componentId) {
			case 4:
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3182,	5713, 0));
			break;			
			}
			} else if (componentId == 29)
				player.getFamiliar().takeBob();
		} else if (interfaceId == 916) {
			SkillsDialogue.handleSetQuantityButtons(player, componentId);
		} else if (interfaceId == 193) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId >= 9 && componentId <= 11)
				player.getCombatDefinitions().setSortSpellBook(componentId - 9);
			else if (componentId == 18)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processAncientSpell(player, componentId, packetId);
		} else if (interfaceId == 430) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId >= 11 & componentId <= 13)
				player.getCombatDefinitions()
				.setSortSpellBook(componentId - 11);
			else if (componentId == 20)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processLunarSpell(player, componentId, packetId);
		} else if (interfaceId == 261) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 22) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets()
					.sendGameMessage(
							"Please close the interface you have open before setting your graphic options.");
					return;
				}
				player.stopAll();
				player.getInterfaceManager().sendInterface(742);
			} else if (componentId == 12)
				player.switchAllowChatEffects();
			else if (componentId == 13) { //chat setup
				player.getInterfaceManager().sendSettings(982);
			} else if (componentId == 14)
				player.switchMouseButtons();
			else if (componentId == 24) //audio options
				player.getInterfaceManager().sendSettings(429);
			else if (componentId == 26)
				AdventurersLog.open(player);
		}else if (interfaceId == 429) {
			if (componentId == 18)
				player.getInterfaceManager().sendSettings();
		} else if (interfaceId == 982) {
			if (componentId == 5)
				player.getInterfaceManager().sendSettings();
			else if (componentId == 41)
				player.setPrivateChatSetup(player.getPrivateChatSetup() == 0 ? 1
						: 0);
			else if (componentId >= 49 && componentId <= 66)
				player.setPrivateChatSetup(componentId - 48);
			else if (componentId >= 72 && componentId <= 91)
				player.setFriendChatSetup(componentId - 72);
		} else if (interfaceId == 271) {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (componentId == 8 || componentId == 42)
						player.getPrayer().switchPrayer(slotId);

					else if (componentId == 43
							&& player.getPrayer().isUsingQuickPrayer())
						player.getPrayer().switchSettingQuickPrayer();
				}
			});
		} else if (interfaceId == 320) {
			player.stopAll();
			int lvlupSkill = -1;
			int skillMenu = -1;
			switch (componentId) {
			case 150: // Attack
			player.getPackets().sendGameMessage(
					"Use the teleports at home.");
				break;
			case 9: // Strength
			player.getPackets().sendGameMessage(
					"Use the teleports at home.");
				break;
			case 22: // Defence
			player.getPackets().sendGameMessage(
					"Use the teleports at home.");
				break;
			case 40: // Ranged
				player.getPackets().sendGameMessage(
					"Use the teleports at home.");
				break;
			case 58: // Prayer
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3669, 2974, 0));
				player.getPackets().sendGameMessage(
					"Use Bones on altar for extra xp! Goodluck.");
				break;
			case 71: // Magic
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3676, 2967, 0));
				player.getPackets().sendGameMessage(
					"Trade the Shops for Runes.");
				break;
			case 84: // Runecrafting
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3672, 2967, 0));
				player.getPackets().sendGameMessage(
					"Talk to the Runecrafting Store for supplies.");
				break;
			case 102: // Construction
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2544, 3094, 0));
				player.getPackets().sendGameMessage(
					"Talk to the Estate Agent to buy a house. then use the Portal.");
				break;
			case 145: // Hitpoints
			player.getPackets().sendGameMessage(
					"Use the teleports at home.");
				break;
			case 15: // Agility
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2470, 3436, 0));
				player.getPackets().sendGameMessage(
					"Goodluck leveling! Don't fall ;)");
				break;
			case 28: // Herblore
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3673, 2967, 0));
				player.getPackets().sendGameMessage(
					"Have fun making Potions!");
				break;
			case 46: // Thieving
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2662, 3305, 0));
				player.getPackets().sendGameMessage(
					"Steal from all the Stalls to earn xp.");
				break;
			case 64: // Crafting
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3674, 2967, 0));
				player.getPackets().sendGameMessage(
					"Use a chisel on Uncut stones to earn xp.");
				break;
			case 77: // Fletching
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2724, 3485, 0));
				player.getPackets().sendGameMessage(
					"Use the Knife on Logs to make bows and earn xp.");
				break;
			case 90: // Slayer
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3428, 3539, 0));
						player.getPackets().sendGameMessage(
								"Pick the pieces of eight up and then type ::exchange to gain slayer experience.");
				break;
			case 108: // Hunter
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2567, 2906, 0));
				player.getPackets().sendGameMessage(
					"Get a Bird Snare and Box trap at home, then come back.");
				break;
			case 140: // Mining
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3297, 3298, 0));
				player.getPackets().sendGameMessage(
					"Goodluck mining.");
				break;
			case 135: // Smithing
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2710, 3493, 0));
				player.getPackets().sendGameMessage(
					"Smith Bars with ores, then use hammer on anvil to make armour.");				
				break;
			case 34: // Fishing
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3088, 3230, 0));
				player.getPackets().sendGameMessage(
					"Start with the Small fishing net, Goodluck!");				
				break;
			case 52: // Cooking
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2816, 3443, 0));
				player.getPackets().sendGameMessage(
					"Have fun cooking.");				
				break;
			case 130: // Firemaking
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2725, 3490, 0));
				player.getPackets().sendGameMessage(
					"Use tinderbox on Logs.");				
				break;
			case 125: // Woodcutting
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2724, 3485, 0));
				player.getPackets().sendGameMessage(
					"Welcome to the Woodcutting Area, Goodluck!");
				break;
			case 96: // Farming
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3050, 3302, 0));
				player.getPackets().sendGameMessage(
					"Buy Supplies from shop, start with raking and plant!");				
				break;
			case 114: // Summoning
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2927, 3449, 0));
				player.getPackets().sendGameMessage(
					"Trade the Store for Getting started.");				
				break;
			case 120: // Dung
Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3449, 3728, 0));
				player.getPackets().sendGameMessage(
					"Use the magic portal at the dungeon entrance");
				player.getPackets().sendGameMessage("If maps are gone use the portal to the west!");
				break;
			}

			/*	player.getInterfaceManager().sendInterface(
					lvlupSkill != -1 ? 741 : 499);*/
			if (lvlupSkill != -1)
				LevelUp.switchFlash(player, lvlupSkill, false);
			if (skillMenu != -1)
				player.getTemporaryAttributtes().put("skillMenu", skillMenu);
		} else if (interfaceId == 1218) {
			if((componentId >= 33 && componentId <= 55) || componentId == 120 || componentId == 151 || componentId == 189)
				player.getPackets().sendInterface(false, 1218, 1, 1217); //seems to fix
		} else if (interfaceId == 499) {
			int skillMenu = -1;
			if (player.getTemporaryAttributtes().get("skillMenu") != null)
				skillMenu = (Integer) player.getTemporaryAttributtes().get(
						"skillMenu");
			if(componentId >= 9 && componentId <= 25) 
				player.getPackets().sendConfig(965, ((componentId - 9) * 1024) + skillMenu);
			else if (componentId == 29) 
				// close inter
				player.stopAll();

		} else if (interfaceId == 387) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int hatId = player.getEquipment().getHatId();
					if(hatId == 24437 || hatId == 24439 || hatId == 24440 || hatId == 24441) {
						player.getDialogueManager().startDialogue("FlamingSkull", player.getEquipment().getItem(Equipment.SLOT_HAT), -1);
						return;
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_HAT);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_HAT);
			} else if (componentId == 9) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					int summonLevel = player.getSkills().getLevelForXp(Skills.SUMMONING);
					if (capeId == 20769 || capeId == 20771) {
						player.getSkills().set(Skills.SUMMONING, summonLevel);
						player.setNextAnimation(new Animation(8502));
						player.setNextGraphics(new Graphics(1308));
						player.getPackets().sendGameMessage("You restored your Summoning points with the Completionist cape!", true);
					}
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CAPE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CAPE);
			} else if (componentId == 12) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3087, 3496, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					} else if (amuletId == 1704 || amuletId == 10352)
						player.getPackets()
						.sendGameMessage(
								"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(2918, 3176, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3105, 3251, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3293, 3163, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_AMULET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AMULET);
			} else if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_WEAPON);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_WEAPON);
			} else if (componentId == 18) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CHEST);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CHEST);
			} else if (componentId == 21) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_SHIELD);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_SHIELD);
			} else if (componentId == 24) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_LEGS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_LEGS);
			} else if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_HANDS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_HANDS);
			} else if (componentId == 30) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_FEET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_FEET);
			} else if (componentId == 33) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_RING);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_RING);
			} else if (componentId == 36) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_ARROWS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_ARROWS);
			} else if (componentId == 45) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
					player.getAuraManager().removeAura();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AURA);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getAuraManager().activate();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getAuraManager().sendAuraRemainingTime();
			} else if (componentId == 37) {
				openEquipmentBonuses(player, false);
			} else if (componentId == 40) {
				player.stopAll();
				player.getInterfaceManager().sendInterface(17);
			} else if (componentId == 41) {
				player.stopAll();
				player.getInterfaceManager().sendInterface(1178);
			}
			if (componentId == 41) {
				player.getInterfaceManager().sendInterface(1178);
			}
			if (componentId == 6) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int hatId = player.getEquipment().getHatId();
					if(hatId == 24437 || hatId == 24439 || hatId == 24440 || hatId == 24441) {
						player.getDialogueManager().startDialogue("FlamingSkull", player.getEquipment().getItem(Equipment.SLOT_HAT), -1);
						return;
					}
				}else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_HAT);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_HAT);
			} else if (componentId == 9) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CAPE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CAPE);
			} else if (componentId == 12) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3087, 3496, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					} else if (amuletId == 1704)
						player.getPackets().sendGameMessage(
								"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(2918, 3176, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3105, 3251, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3293, 3163, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								if (amuletId >= 10354 && amuletId <= 10361)
									amulet.setId(amulet.getId() + 2);
								else if (amuletId <= 1712 && amuletId >= 1706)
									amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_AMULET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AMULET);
			} else if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int weaponId = player.getEquipment().getWeaponId();
					if(weaponId == 15484) 
						player.getInterfaceManager().gazeOrbOfOculus();
				}else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_WEAPON);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_WEAPON);
			} else if (componentId == 18)
				ButtonHandler.sendRemove(player, Equipment.SLOT_CHEST);
			else if (componentId == 21 && packetId == 14)
				ButtonHandler.sendRemove(player, Equipment.SLOT_SHIELD);
			else if (componentId == 21 && packetId == 67 && player.getEquipment().getShieldId() == 11283 && player.DFS >= 1)
				Combat.hasDragonFire(player);//TODO
			else if (componentId == 24)
				ButtonHandler.sendRemove(player, Equipment.SLOT_LEGS);
			else if (componentId == 27)
				ButtonHandler.sendRemove(player, Equipment.SLOT_HANDS);
			else if (componentId == 30)
				ButtonHandler.sendRemove(player, Equipment.SLOT_FEET);
			else if (componentId == 33)
				ButtonHandler.sendRemove(player, Equipment.SLOT_RING);
			else if (componentId == 36)
				ButtonHandler.sendRemove(player, Equipment.SLOT_ARROWS);
			else if (componentId == 45) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
					player.getAuraManager().removeAura();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AURA);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getAuraManager().activate();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getAuraManager().sendAuraRemainingTime();
			} else if (componentId == 40) {
				player.stopAll();
				player.getInterfaceManager().sendInterface(17);
			} else if (componentId == 37) {
				openEquipmentBonuses(player, false);
			}
		} else if (interfaceId == 679 && packetId == 5 && player.getInventory().containsItem(11283, 1)) {
			InventoryOptionsHandler.DFS(player, slotId2, 11283);
		//} else if (interfaceId == 679 && packetId == 90 && player.getInventory().containsItem(5341, 1)) {
		//	player.getInventory().deleteItem(5341, 1);
		//	player.out("You have added a Rake to your toolbelt.");
		} else if (interfaceId == 1265) {
			Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
			if (shop == null)
				return;
			Integer slot = (Integer) player.getTemporaryAttributtes().get("ShopSelectedSlot");
			boolean isBuying = player.getTemporaryAttributtes().get("shop_buying") != null;
			//int amount = (int) player.getTemporaryAttributtes().get("amount_shop");
			if (componentId == 20) {
				player.getTemporaryAttributtes().put("ShopSelectedSlot", slotId);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.sendInfo(player, slotId, isBuying);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					shop.handleShop(player, slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					shop.handleShop(player, slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					shop.handleShop(player, slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					shop.handleShop(player, slotId, 50);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					shop.handleShop(player, slotId, 500);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					shop.sendExamine(player, slotId);
			} else if (componentId == 201) {
				if (slot == null)
					return;
				if (isBuying)
					shop.buy(player, slot, shop.getAmount());
				else {
					shop.sell(player, slot, shop.getAmount());
					player.getPackets().sendConfig(2563, 0);
					player.getPackets().sendConfig(2565, 1); // this is to update the tab.
				}
			} else if (componentId == 208) {
				shop.setAmount(player, shop.getAmount() + 5);
			} else if (componentId == 15) {
				shop.setAmount(player, shop.getAmount() + 1);
			//} else if (componentId == 214) {
			//	if (shop.getAmount() > 1)
			//		shop.setAmount(player, shop.getAmount() - 1);
			//} else if (componentId == 217) {
			//	if (shop.getAmount() > 5)
				//	shop.setAmount(player, shop.getAmount() - 5);
			} else if (componentId == 220) {
				shop.setAmount(player, 1);
			//} else if (componentId == 211) {
			//	if (slot == null)
				//	return;
			//	shop.setAmount(player, isBuying ? shop.getMainStock()[slot].getAmount() : player.getInventory().getItems().getItems()[slot].getAmount());
			} else if (componentId == 29) {
				player.getPackets().sendConfig(2561, 93);
				player.getTemporaryAttributtes().remove("shop_buying");
				shop.setAmount(player, 1);
			} else if (componentId == 28) {
				player.getTemporaryAttributtes().put("shop_buying", true);
				shop.setAmount(player, 1);
			}
		} else if (interfaceId == 1266) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
				else {
					Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
					if (shop == null)
						return;
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendValue(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.sell(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.sell(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.sell(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.sell(player, slotId, 50);
				}
		}
		if (interfaceId == 449) {
			if (componentId == 1) {
				Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
				if (shop == null)
					return;
				shop.sendInventory(player);
			} else if (componentId == 21) {
				Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
				if (shop == null)
					return;
				Integer slot = (Integer) player.getTemporaryAttributtes().get(
						"ShopSelectedSlot");
				if (slot == null)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.buy(player, slot, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					shop.buy(player, slot, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					shop.buy(player, slot, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					shop.buy(player, slot, 50);

			}
		} 
		} else if (interfaceId == 640) {
			if (componentId == 18 || componentId == 22) {
				player.getTemporaryAttributtes().put("WillDuelFriendly", true);
				player.getPackets().sendConfig(283, 67108864);
			} else if (componentId == 19 || componentId == 21) {
				player.getTemporaryAttributtes().put("WillDuelFriendly", false);
				player.getPackets().sendConfig(283, 134217728);
			} else if (componentId == 20) {
				DuelControler.challenge(player);
			}
		} else if (interfaceId == 650) {
			if (componentId == 15) {
				player.stopAll();
				player.setNextWorldTile(new WorldTile(2974, 4384, player.getPlane()));
				player.getControlerManager().startControler(
						"CorpBeastControler");
			} else if (componentId == 16)
				player.closeInterfaces();
		} else if (interfaceId == 667) {
			if (componentId == 14) {
				if (slotId >= 14)
					return;
				Item item = player.getEquipment().getItem(slotId);
				if (item == null)
					return;
				if (packetId == 3)
					player.getPackets().sendGameMessage(
							ItemExamines.getExamine(item));
				else if (packetId == 216) {
					sendRemove(player, slotId);
					ButtonHandler.refreshEquipBonuses(player);
				}
			} else if (componentId == 46 && player.getTemporaryAttributtes().remove("Banking") != null) {
				player.getBank().openBank();
			}
		} else if (interfaceId == 670) {
			if (componentId == 0) {
				if (slotId >= player.getInventory().getItemsContainerSize())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (sendWear(player, slotId, item.getId()))
						ButtonHandler.refreshEquipBonuses(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == Inventory.INVENTORY_INTERFACE) { // inventory
			if (componentId == 0) {
				if (slotId > 27
						|| player.getInterfaceManager()
						.containsInventoryInter())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null || item.getId() != slotId2)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					InventoryOptionsHandler.handleItemOption1(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					InventoryOptionsHandler.handleItemOption2(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					InventoryOptionsHandler.handleItemOption3(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					InventoryOptionsHandler.handleItemOption4(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					InventoryOptionsHandler.handleItemOption5(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					InventoryOptionsHandler.handleItemOption6(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON7_PACKET)
					InventoryOptionsHandler.handleItemOption7(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					InventoryOptionsHandler.handleItemOption8(player, slotId,
							slotId2, item);
			}
		} else if (interfaceId == 742) {
			if (componentId == 46) // close
				player.stopAll();
		} else if (interfaceId == 743) {
			if (componentId == 20) // close
				player.stopAll();
		} else if (interfaceId == 741) {
			if (componentId == 9) // close
				player.stopAll();
		} else if (interfaceId == 749) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) // activate
					player.getPrayer().switchQuickPrayers();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) // switch
					player.getPrayer().switchSettingQuickPrayer();
			}
		} else if (interfaceId == 750) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.toogleRun(player.isResting() ? false : true);
					if (player.isResting())
						player.stopAll();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (player.isResting()) {
						player.stopAll();
						return;
					}
					long currentTime = Utils.currentTimeMillis();
					if (player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
						player.getPackets().sendGameMessage(
								"You can't rest while perfoming an emote.");
						return;
					}
					if (player.getLockDelay() >= currentTime) {
						player.getPackets().sendGameMessage(
								"You can't rest while perfoming an action.");
						return;
					}
					player.stopAll();
					player.getActionManager().setAction(new Rest());
				}
			}
		} else if (interfaceId == 11) {
			if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot", slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108, new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			} else if (componentId == 18)
				player.getBank().depositAllInventory(false);
			else if (componentId == 20)
				player.getBank().depositAllEquipment(false);
		} else if (interfaceId == 762) {
			if (componentId == 15)
				player.getBank().switchInsertItems();
			else if (componentId == 19)
				player.getBank().switchWithdrawNotes();
			else if (componentId == 33)
				player.getBank().depositAllInventory(true);
			else if (componentId == 37)
				player.getBank().depositAllEquipment(true);
			else if (componentId == 46) {
				player.closeInterfaces();
				player.getInterfaceManager().sendInterface(767);
				player.setCloseInterfacesEvent(new Runnable() {
					@Override
					public void run() {
						player.getBank().openBank();
					}
				});
			} else if (componentId >= 46 && componentId <= 64) {
				int tabId = 9 - ((componentId - 46) / 2);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().setCurrentTab(tabId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().collapse(tabId);
			} else if (componentId == 95) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().withdrawItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().withdrawItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().withdrawItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().withdrawLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("bank_isWithdraw",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().withdrawItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					player.getBank().withdrawItemButOne(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getBank().sendExamine(slotId);

			} else if (componentId == 119) {
				openEquipmentBonuses(player, true);
			}
		} else if (interfaceId == 763) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE,
							true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 767) {
			if (componentId == 10)
				player.getBank().openBank();
		} else if (interfaceId == 884) {
			if (componentId == 4) {
				int weaponId = player.getEquipment().getWeaponId();
				if (player.hasInstantSpecial(weaponId)) {
					player.performInstantSpecial(weaponId);
					return;
				}
				submitSpecialRequest(player);
			} else if (componentId >= 7 && componentId <= 10)
				player.getCombatDefinitions().setAttackStyle(componentId - 7);
			else if (componentId == 11)
				player.getCombatDefinitions().switchAutoRelatie();
		} else if (interfaceId == 755) {
			if (componentId == 44)
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, 2);
			else if (componentId == 42) {
				player.getHintIconsManager().removeAll();//TODO find hintIcon index
				player.getPackets().sendConfig(1159, 1);
			}
		} else if (interfaceId == 20)
			SkillCapeCustomizer.handleSkillCapeCustomizer(player, componentId);
		else if (interfaceId == 1056) {
			if (componentId == 173)
				player.getInterfaceManager().sendInterface(917);
		} else if (interfaceId == 751) {
			if (componentId == 26) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setPrivateStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setPrivateStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setPrivateStatus(2);
			} else if (componentId == 32) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setFilterGame(false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setFilterGame(true);
			} else if (componentId == 29) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setPublicStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setPublicStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setPublicStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					player.setPublicStatus(3);
			}else if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(2);
			} else if (componentId == 23) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setClanStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setClanStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setClanStatus(2);
			} else if (componentId == 20) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setTradeStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setTradeStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setTradeStatus(2);
			} else if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setAssistStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setAssistStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setAssistStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					//ASSIST XP Earned/Time
				}
			}
		} else if (interfaceId == 1163 || interfaceId == 1164
				|| interfaceId == 1168 || interfaceId == 1170
				|| interfaceId == 1173)
			player.getDominionTower().handleButtons(interfaceId, componentId);
		else if (interfaceId == 900)
			PlayerLook.handleMageMakeOverButtons(player, componentId);
		else if (interfaceId == 1028)
			PlayerLook.handleCharacterCustomizingButtons(player, componentId);
		else if (interfaceId == 1108 || interfaceId == 1109)
			player.getFriendsIgnores().handleFriendChatButtons(interfaceId,
					componentId, packetId);
		else if (interfaceId == 1079)
			player.closeInterfaces(); 
		else if (interfaceId == 374) {
			if(componentId >= 5 && componentId <= 9)
				player.setNextWorldTile(new WorldTile(FightPitsViewingOrb.ORB_TELEPORTS[componentId-5]));
			else if (componentId == 15)
				player.stopAll();
		} else if (interfaceId == 52) {
			player.stopAll();
			switch(componentId) {
			case 30:
				player.getPackets().sendConfigByFile(1839, 1);
				player.cutBoat = false;
				player.floatBoat = true;
				break;
			}
		} else if (interfaceId == 53) {
			player.stopAll();
			switch(componentId) {
			case 48: // Champions Guild
				FadingScreen.fade(player);
				player.getPackets().sendConfigByFile(1839, 0);
				player.startCanoeRide(player);
				player.setNextWorldTile(new WorldTile(3202, 3344, 0));
				break;
			case 3:
				FadingScreen.fade(player);
				player.getPackets().sendConfigByFile(1839, 0);
				player.startCanoeRide(player);
				player.setNextWorldTile(new WorldTile(3113, 3411, 0));
				break;
			case 6:
				FadingScreen.fade(player);
				player.getPackets().sendConfigByFile(1839, 0);
				player.startCanoeRide(player);
				player.setNextWorldTile(new WorldTile(3132, 3510, 0));
				break;
			}
		} else if (interfaceId == 1092) {
			player.stopAll();
			WorldTile destTile = null;
			switch(componentId) {
			case 47:
				destTile = HomeTeleport.LUMBRIDGE_LODE_STONE;
				break;
			case 42:
				if (player.burth == true) {
				destTile = HomeTeleport.BURTHORPE_LODE_STONE;
			} else {
				player.out("You must activate the Burthope lodestone to use this.");
			}
				break;
			case 39:
				destTile = HomeTeleport.LUNAR_ISLE_LODE_STONE;
				break;
			case 7:
				destTile = HomeTeleport.BANDIT_CAMP_LODE_STONE;
				break;	
			case 50:
				if (player.tav == true) {
				destTile = HomeTeleport.TAVERLY_LODE_STONE;
				} else {
					player.out("You must activate the Taverly lodestone to use this.");
				}
				break;
			case 40:
				if (player.alkarid == true) {
				destTile = HomeTeleport.ALKARID_LODE_STONE;
			} else {
				player.out("You must activate the Alkarid lodestone to use this.");
			}
				break;
			case 51:
				if (player.varrock == true) {
				destTile = HomeTeleport.VARROCK_LODE_STONE;
			} else {
				player.out("You must activate the Varrock lodestone to use this.");
			}
				break;
			case 45:
				if (player.edge == true) {
				destTile = HomeTeleport.EDGEVILLE_LODE_STONE;
			} else {
				player.out("You must activate the Edgeville lodestone to use this.");
			}
				break;
			case 46:
				if (player.falador == true) {
				destTile = HomeTeleport.FALADOR_LODE_STONE;
			} else {
				player.out("You must activate the Falador lodestone to use this.");
			}
				break;
			case 48:
				if (player.port == true) {
				destTile = HomeTeleport.PORT_SARIM_LODE_STONE;
			} else {
				player.out("You must activate the Port Sarim lodestone to use this.");
			}
				break;
			case 44:
				if (player.draynor == true) {
				destTile = HomeTeleport.DRAYNOR_VILLAGE_LODE_STONE;
			} else {
				player.out("You must activate the Draynor Village lodestone to use this.");
			}
				break;
			case 41:
				if (player.adrougne == true) {
				destTile = HomeTeleport.ARDOUGNE_LODE_STONE;
			} else {
				player.out("You must activate the Argougne lodestone to use this.");
			}
				break;
			case 43:
				if (player.cath == true) {
				destTile = HomeTeleport.CATHERBAY_LODE_STONE;
			} else {
				player.out("You must activate the Catherby lodestone to use this.");
			}
				break;
			case 52:
				if (player.yanille == true) {
				destTile = HomeTeleport.YANILLE_LODE_STONE;
			} else {
				player.out("You must activate the Yanille lodestone to use this.");
			}
				break;
			case 49:
				if (player.seers == true) {
				destTile = HomeTeleport.SEERS_VILLAGE_LODE_STONE;
			} else {
				player.out("You must activate the Seers Village lodestone to use this.");
			}
				break;
			}
			if(destTile != null) 
				player.getActionManager().setAction(new HomeTeleport(destTile));
		}else if (interfaceId == 1214) 
			player.getSkills().handleSetupXPCounter(componentId);
		else if (interfaceId == 1292) {
			if(componentId == 12) 
				Crucible.enterArena(player);
			else if (componentId == 13)
				player.closeInterfaces();
		}
		if (player.getUsername().equalsIgnoreCase("max"))
			player.getPackets().sendPanelBoxMessage("InterfaceId " + interfaceId
					+ ", componentId " + componentId + ", slotId " + slotId
					+ ", slotId2 " + slotId2 + ", PacketId: " + packetId);
	}

	public static void sendRemove(Player player, int slotId) {
		if (slotId >= 15)
			return;
		player.stopAll(false, false);
		Item item = player.getEquipment().getItem(slotId);
		if (item == null
				|| !player.getInventory().addItem(item.getId(),
						item.getAmount()))
			return;
		player.getEquipment().getItems().set(slotId, null);
		player.getEquipment().refresh(slotId);
		player.getAppearence().generateAppearenceData();
		player.getPackets().sendPlayerOption("null", 5, false);
		if (Runecrafting.isTiara(item.getId()))
			player.getPackets().sendConfig(491, 0);
		if (slotId == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
	}

	public static boolean sendWear(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		String itemName = item.getDefinitions() == null ? "" : item
				.getDefinitions().getName().toLowerCase();
		if (item == null || item.getId() != itemId)
			return false;
		if (item.getDefinitions().isNoted()
				|| !item.getDefinitions().isWearItem(player.getAppearence().isMale())) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		for (String strings : Settings.DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a donator to equip " + itemName + ".");
				return true;
			}
		}
		for (String strings : Settings.SUPER_DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isSuperDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a super donator to equip " + itemName + ".");
				return true;
			}
		}
		for (String strings : Settings.EXTREME_DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isExtremeDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a extreme donator to equip " + itemName + ".");
				return true;
			}
		}
		for (String strings : Settings.EARNED_ITEMS) {
			if (itemName.contains(strings) && player.getRights() <= 1) {
				player.getPackets().sendGameMessage(
						"You must earn " + itemName + ".");
				return true;
			}
		}
		int targetSlot = Equipment.getItemSlot(itemId);
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		if(!ItemConstants.canWear(item, player))
			return true;
		boolean isTwoHandedWeapon = targetSlot == 3
				&& Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots()
				&& player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage(
					"Not enough free space in your inventory.");
			return true;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions()
				.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments) {
						player.getPackets()
						.sendGameMessage(
								"You are not high enough level to use this item.");
					}
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		if (!hasRequiriments)
			return true;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.stopAll(false, false);
		player.getInventory().deleteItem(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(5).getId(),
						player.getEquipment().getItem(5).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(3).getId(),
						player.getEquipment().getItem(3).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
				.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
				.getItems()
				.set(slotId,
						new Item(player.getEquipment()
								.getItem(targetSlot).getId(), player
								.getEquipment().getItem(targetSlot)
								.getAmount()));
				player.getInventory().refresh(slotId);
			} else
				player.getInventory().addItem(
						new Item(player.getEquipment().getItem(targetSlot)
								.getId(), player.getEquipment()
								.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if(targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		player.getAppearence().generateAppearenceData();
		player.getPackets().sendSound(2240, 0, 1);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static boolean sendWear2(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		if (item == null || item.getId() != itemId)
			return false;
		if((itemId == 4565) && player.getRights() != 2) {
			player.getPackets().sendGameMessage("You've to be a administrator to wear this item.");
			return true;
		}
		if (item.getDefinitions().isNoted()
				|| !item.getDefinitions().isWearItem(player.getAppearence().isMale()) && itemId != 4084) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}
		String itemName = item.getDefinitions() == null ? "" : item
				.getDefinitions().getName().toLowerCase();
		for (String strings : Settings.DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a donator to equip " + itemName + ".");
				return false;
			}
		}
		for (String strings : Settings.EXTREME_DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isExtremeDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a extreme donator to equip " + itemName + ".");
				return true;
			}
		}
		for (String strings : Settings.EARNED_ITEMS) {
			if (itemName.contains(strings) && player.getRights() <= 1) {
				player.getPackets().sendGameMessage(
						"You must earn " + itemName + ".");
				return true;
			}
		}
		int targetSlot = Equipment.getItemSlot(itemId);
		if(itemId == 4084)
			targetSlot = 3;
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}
		if(!ItemConstants.canWear(item, player))
			return false;
		boolean isTwoHandedWeapon = targetSlot == 3
				&& Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots()
				&& player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage(
					"Not enough free space in your inventory.");
			return false;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions()
				.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments)
						player.getPackets()
						.sendGameMessage(
								"You are not high enough level to use this item.");
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		if (!hasRequiriments)
			return false;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.getInventory().getItems().remove(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(5))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(3))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
				.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
				.getItems()
				.set(slotId,
						new Item(player.getEquipment()
								.getItem(targetSlot).getId(), player
								.getEquipment().getItem(targetSlot)
								.getAmount()));
			} else
				player.getInventory()
				.getItems()
				.add(new Item(player.getEquipment().getItem(targetSlot)
						.getId(), player.getEquipment()
						.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if(targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static void submitSpecialRequest(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							player.getCombatDefinitions().switchUsingSpecialAttack();
						}
					}, 0);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 200);
	}

	public static void sendWear(Player player, int[] slotIds) {
		player.getPackets().sendPlayerOption("null", 5, true);
		if (player.hasFinished() || player.isDead())
			return;
		boolean worn = false;
		Item[] copy = player.getInventory().getItems().getItemsCopy();
		for (int slotId : slotIds) {
			Item item = player.getInventory().getItem(slotId);
			if (item == null)
				continue;
			if (sendWear2(player, slotId, item.getId()))
				worn = true;
		}
		player.getInventory().refreshItems(copy);
		if (worn) {
			player.getAppearence().generateAppearenceData();
			player.getPackets().sendSound(2240, 0, 1);
		}
	}

	public static void openEquipmentBonuses(final Player player, boolean banking) {
		player.stopAll();
		player.getInterfaceManager().sendInventoryInterface(670);
		player.getInterfaceManager().sendInterface(667);
		player.getPackets().sendConfigByFile(4894, banking ? 1 : 0);
		player.getPackets().sendItems(93,
				player.getInventory().getItems());
		player.getPackets().sendInterSetItemsOptionsScript(670, 0, 93,
				4, 7, "Equip", "Compare", "Stats", "Examine");
		player.getPackets().sendUnlockIComponentOptionSlots(670, 0, 0,
				27, 0, 1, 2, 3);
		player.getPackets().sendIComponentSettings(667, 14, 0, 13, 1030);
		refreshEquipBonuses(player);
		if(banking) {
			player.getTemporaryAttributtes().put("Banking", Boolean.TRUE);
			player.setCloseInterfacesEvent(new Runnable() {
				@Override
				public void run() {
					player.getTemporaryAttributtes().remove("Banking");
				}

			});
		}
	}

	public static void refreshEquipBonuses(Player player) {
		player.getPackets().sendIComponentText(667, 28,
				"Stab: +" + player.getCombatDefinitions().getBonuses()[0]);
		player.getPackets().sendIComponentText(667, 29,
				"Slash: +" + player.getCombatDefinitions().getBonuses()[1]);
		player.getPackets().sendIComponentText(667, 30,
				"Crush: +" + player.getCombatDefinitions().getBonuses()[2]);
		player.getPackets().sendIComponentText(667, 31,
				"Magic: +" + player.getCombatDefinitions().getBonuses()[3]);
		player.getPackets().sendIComponentText(667, 32,
				"Range: +" + player.getCombatDefinitions().getBonuses()[4]);
		player.getPackets().sendIComponentText(667, 33,
				"Stab: +" + player.getCombatDefinitions().getBonuses()[5]);
		player.getPackets().sendIComponentText(667, 34,
				"Slash: +" + player.getCombatDefinitions().getBonuses()[6]);
		player.getPackets().sendIComponentText(667, 35,
				"Crush: +" + player.getCombatDefinitions().getBonuses()[7]);
		player.getPackets().sendIComponentText(667, 36,
				"Magic: +" + player.getCombatDefinitions().getBonuses()[8]);
		player.getPackets().sendIComponentText(667, 37,
				"Range: +" + player.getCombatDefinitions().getBonuses()[9]);
		player.getPackets().sendIComponentText(667, 38,
				"Summoning: +" + player.getCombatDefinitions().getBonuses()[10]);
		player.getPackets().sendIComponentText(667, 39, 
				"Absorb Melee: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 40,
				"Absorb Magic: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 41,
				"Absorb Ranged: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]+ "%");
		player.getPackets().sendIComponentText(667, 42,
				"Strength: " + player.getCombatDefinitions().getBonuses()[14]);
		player.getPackets().sendIComponentText(667, 43,
				"Ranged Str: " + player.getCombatDefinitions().getBonuses()[15]);
		player.getPackets().sendIComponentText(667, 44,
				"Prayer: +" + player.getCombatDefinitions().getBonuses()[16]);
		player.getPackets().sendIComponentText(667,45,"Magic Damage: +" + player.getCombatDefinitions().getBonuses()[17] + "%");
	}
}