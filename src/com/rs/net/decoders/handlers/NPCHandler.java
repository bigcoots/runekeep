package com.rs.net.decoders.handlers;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.others.FireSpirit;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.pet.Pet;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Fishing;
import com.rs.game.player.actions.Rest;
import com.rs.game.player.actions.Fishing.FishingSpots;
import com.rs.game.player.actions.mining.LivingMineralMining;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.game.player.actions.runecrafting.SiphonActionCreatures;
import com.rs.game.player.actions.thieving.PickPocketAction;
import com.rs.game.player.actions.thieving.PickPocketableNPC;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.dialogues.FremennikShipmaster;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.NPCSpawns;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public class NPCHandler {

	public static void handleExamine(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		if(forceRun)
			player.setRun(forceRun);
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		if (player.getRights() > 1) {
			player.getPackets().sendGameMessage(
					"NPC - [id=" + npc.getId() + ", loc=[" + npc.getX() + ", " + npc.getY() + ", " + npc.getPlane() + "]].");
		}
		player.getPackets().sendNPCMessage(0, npc, "It's a " + npc.getDefinitions().name + ".");
		if(player.isSpawnsMode()) {
			try {
				if(NPCSpawns.removeSpawn(npc)) {
					player.getPackets().sendGameMessage("Removed spawn!");
					return;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			player.getPackets().sendGameMessage("Failed removing spawn!");
		}
		if (Settings.DEBUG)
			Logger.log("NPCHandler", "examined npc: " + npcIndex+", "+npc.getId());
	}
	
	public static void handleOption1(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("Banker", npc.getId());
			return;
		}
		if(SiphonActionCreatures.siphon(player, npc)) 
			return;
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				if (!player.getControlerManager().processNPCClick1(npc))
					return;
				FishingSpots spot = FishingSpots.forId(npc.getId() | 1 << 24);
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return; // its a spot, they wont face us
				}else if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					player.getActionManager().setAction(new LivingMineralMining((LivingRock) npc));
					return;
				}
				npc.faceEntity(player);
				if (npc.getId() == 3709)
					player.getDialogueManager().startDialogue("MrEx",
							npc.getId());
				if (npc.getId() == 231)
					player.getDialogueManager().startDialogue("Max", npc.getId());
				if (npc.getId() == 6390)
					player.getDialogueManager().startDialogue("Clan", npc.getId());
				if (npc.getId() == 1)
					player.getDialogueManager().startDialogue("BankTest",
							npc.getId());
				else if (npc.getId() == 5532)
					player.getDialogueManager().startDialogue("SorceressGardenNPCs", npc);
				else if (npc.getId() == 5563)
					player.getDialogueManager().startDialogue("SorceressGardenNPCs", npc);
				else if (npc.getId() == 208)
					player.getDialogueManager().startDialogue("Lawgof");
				else if (npc.getId() == 2477)
					player.getDialogueManager().startDialogue("QuizMaster", npc.getId(),9827);	
				else if (npc.getId() == 11506)
					player.getDialogueManager().startDialogue("pestreward", npc.getId(),9827);	
				else if (npc.getId() == 258)
					player.getDialogueManager().startDialogue("bossreward", npc.getId(),9827);						
				else if (npc.getId() == 2340)
					ShopsHandler.openShop(player, 41);
				else if (npc.getId() == 1948)
					ShopsHandler.openShop(player, 54);
				else if (npc.getId() == 44)
					player.getBank().openBank();
				else if (npc.getId() == 1834)
					ShopsHandler.openShop(player, 12);
				else if (npc.getId() == 1463)
					ShopsHandler.openShop(player, 45);	
					else if (npc.getId() == 3000)
					ShopsHandler.openShop(player, 49);				
				else if (npc.getId() == 7559)
					player.getDialogueManager().startDialogue("FarmingTeleport");
				else if (npc.getId() == 209)
					player.getDialogueManager().startDialogue("Nulodion");
				else if (npc.getId() == 13884)
					player.getDialogueManager().startDialogue("Lodefala");	
					else if (npc.getId() == 13885)
					player.getDialogueManager().startDialogue("LodeSarim");	
					else if (npc.getId() == 13886)
					player.getDialogueManager().startDialogue("Lodedraynor");
				else if (npc.getId() == 13887)
					player.getDialogueManager().startDialogue("LodeVarrock");	
				else if (npc.getId() == 218)
					player.getDialogueManager().startDialogue("Repair", npc.getId(),519);						
				else if (npc.getId() == 13888)
					player.getDialogueManager().startDialogue("Lodeedge");
				else if (npc.getId() == 15418)
					player.getDialogueManager().startDialogue("Runespannpc");
					else if (npc.getId() == 15402)
					player.getDialogueManager().startDialogue("FloatingEssence", npc.getId());				
				else if (npc.getId() == 13889)
					player.getDialogueManager().startDialogue("Lodetav");	
				else if (npc.getId() == 13890)
					player.getDialogueManager().startDialogue("Lodeburth");	
				else if (npc.getId() == 13891)
					player.getDialogueManager().startDialogue("Lodecath");	
				else if (npc.getId() == 13892)
					player.getDialogueManager().startDialogue("Lodeseers");	
				else if (npc.getId() == 13893)
					player.getDialogueManager().startDialogue("LodeArdougne");	
				else if (npc.getId() == 13894)
					player.getDialogueManager().startDialogue("LodeYanille");	
				else if (npc.getId() == 13895)
					player.getDialogueManager().startDialogue("LodeAlkarid");						
				else if (npc.getId() == 5559) 
					player.sendDeath(npc);
				else if (npc.getId() == 15451 && npc instanceof FireSpirit) {
					FireSpirit spirit = (FireSpirit) npc;
					spirit.giveReward(player);
				}
				else if (npc.getId() == 949)
					player.getDialogueManager().startDialogue("QuestGuide",
							npc.getId(), null);
				else if (npc.getId() >= 1 && npc.getId() <= 6 || npc.getId() >= 7875 && npc.getId() <= 7884)
					player.getDialogueManager().startDialogue("Man", npc.getId());
				else if (npc.getId() == 198)
					player.getDialogueManager().startDialogue("Guildmaster", npc.getId());
				else if (npc.getId() == 9711)
					  player.getDialogueManager().startDialogue("Dungreward", npc.getId());	
				else if (npc.getId() == 6788)
					  player.getDialogueManager().startDialogue("kill", npc.getId());
				else if (npc.getId() == 2831)
					ShopsHandler.openShop(player, 50);					  
				else if (npc.getId() == 9462)
					Strykewyrm.handleStomping(player, npc);
				else if (npc.getId() == 9707)
					player.getDialogueManager().startDialogue(
							"FremennikShipmaster", npc.getId(), true);
				else if (npc.getId() == 9708)
					player.getDialogueManager().startDialogue(
							"FremennikShipmaster", npc.getId(), false);
				else if (npc.getId() == 6988)
					player.getDialogueManager().startDialogue("SummoningShop", false);							
				else if (npc.getId() == 11270)
					ShopsHandler.openShop(player, 19);
				else if (npc.getId() == 2323)
								player.getDialogueManager().startDialogue("Farmshop",
							npc.getId());
				else if (npc.getId() == 13955)
					ShopsHandler.openShop(player, 27);
				else if (npc.getId() == 1772)
					player.getDialogueManager().startDialogue("RandomEventShop");					
				else if (npc.getId() == 6537)
					player.getDialogueManager().startDialogue("SetSkills",
							npc.getId());
                else if (npc.getId() == 2998)
                	player.getDialogueManager().startDialogue("StatLog", npc.getId());
                else if (npc.getId() == 3373)
                	player.getDialogueManager().startDialogue("Train", npc.getId());
				else if (npc.getId() == 599)
					player.getDialogueManager().startDialogue("MakeOverMage", npc.getId(), 0);
				else if (npc.getId() == 598)
					player.getDialogueManager().startDialogue("Hairdresser", npc.getId());
				else if (npc.getId() == 548)
					player.getDialogueManager().startDialogue("Thessalia", npc.getId());
				else if (npc.getId() == 8091)
					player.getDialogueManager().startDialogue("StarSprite");
				else if (npc.getId() == 4247)
					if (player.spokeToAgent == false) {
					player.getDialogueManager().startDialogue("EstateAgent");
					} else {
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "I have already purchased a house.");
					}
				else if (npc.getId() == 8459)
				if(player.isExtremeDonator()) {
			ShopsHandler.openShop(player, 51);	
					} else {
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "I need to be extreme donator to use this shop.");
					}
					else if (npc.getId() == 552)
				if(player.isDonator()) {
			ShopsHandler.openShop(player, 22);	
					} else {
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "I need to be donator to use this shop.");
					}				
				else if (npc.getId() == 11307)
					if (player.spokeToDemon == false) {
					player.getDialogueManager().startDialogue("DemonButler");
					} else {
						player.getDialogueManager().startDialogue("SimplePlayerMessage", "I have already hired the Demon Butler.");
					}
				else if (npc.getId() == 4243)
					player.getDialogueManager().startDialogue("Butler", npc.getId());
				else if (npc.getId() == 7557)
					player.getDialogueManager().startDialogue("FarmingTeleport");
				else if (npc.getId() == 100)
					player.getDialogueManager().startDialogue("FreakyForester", npc.getId());					
				else if (npc.getId() == 3021)
					player.getDialogueManager().startDialogue("FarmingTeleport");
				else if (npc.getId() == 2324)
					ShopsHandler.openShop(player, 41);
				else if (npc.getId() == 654)
					player.getDialogueManager().startDialogue("Shamus", npc.getId());
				else if (npc.getId() == 650)
					player.getDialogueManager().startDialogue("Warrior", npc.getId());
				else if (npc.getId() == 2729)
					player.getDialogueManager().startDialogue("MonkOfEntrana", npc.getId());
				else if(npc.getId() == 659)
					player.getDialogueManager().startDialogue("PartyPete");
				else if(npc.getId() == 410)
					player.getDialogueManager().startDialogue("Charms");
				else if(npc.getId() == 8540)
					player.getDialogueManager().startDialogue("Xmas");
				else if(npc.getId() == 9400)
					player.getDialogueManager().startDialogue("XmasS");
				else if(npc.getId() == 2253)
					player.getDialogueManager().startDialogue("PrestigeOne");
				else if(npc.getId() == 1552)
					player.getDialogueManager().startDialogue("XmasD");
				else if(npc.getId() == 285)
					player.getDialogueManager().startDialogue("XmasV");
				else if(npc.getId() == 1597)
					player.getDialogueManager().startDialogue("Vannaka");
				else if(npc.getId() == 8273)
					player.getDialogueManager().startDialogue("Turael");
				else if (npc.getId() == 579)
					player.getDialogueManager().startDialogue("DrogoDwarf", npc.getId());
				else if (npc.getId() == 582) //dwarves general store
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 31);
				else if (npc.getId() == 528 || npc.getId() == 529) //edge
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 1);
				else if (npc.getId() == 522 || npc.getId() == 523) //varrock
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 8);
				else if (npc.getId() == 520 || npc.getId() == 521) //lumbridge
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 4);
				else if (npc.getId() == 594)
					player.getDialogueManager().startDialogue("Nurmof", npc.getId());
				else if (npc.getId() == 665)
					player.getDialogueManager().startDialogue("BootDwarf", npc.getId());
				else if (npc.getId() == 382 || npc.getId() == 3294 || npc.getId() == 4316)
					player.getDialogueManager().startDialogue("MiningGuildDwarf", npc.getId(), false);
				else if (npc.getId() == 3295)
					player.getDialogueManager().startDialogue("MiningGuildDwarf", npc.getId(), true);
				else if (npc.getId() == 537)
					player.getDialogueManager().startDialogue("Scavvo", npc.getId());
				else if (npc.getId() == 536)
					player.getDialogueManager().startDialogue("Valaine", npc.getId());
				else if (npc.getId() == 4563) //Crossbow Shop
					player.getDialogueManager().startDialogue("Hura", npc.getId());
				else if (npc.getId() == 2617)
					player.getDialogueManager().startDialogue("TzHaarMejJal", npc.getId());
				else if (npc.getId() == 2618)
					player.getDialogueManager().startDialogue("TzHaarMejKah", npc.getId());
				else if(npc.getId() == 15149)
					player.getDialogueManager().startDialogue("MasterOfFear", 0);
				else if (npc instanceof Pet) {
					Pet pet = (Pet) npc;
					if (pet != player.getPet()) {
						player.getPackets().sendGameMessage("This isn't your pet.");
						return;
					}
					player.setNextAnimation(new Animation(827));
					pet.pickup();
				}
				else {
					player.getPackets().sendGameMessage(
							"Nothing interesting happens.");
					if (Settings.DEBUG)
						System.out.println("cliked 1 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
				}
			}
		}, npc.getSize()));
	}

	public static void handleOption2(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getBank().openBank();
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				FishingSpots spot = FishingSpots.forId(npc.getId() | (2 << 24));
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return;
				}
				PickPocketableNPC pocket = PickPocketableNPC.get(npc.getId());
				if (pocket != null) {
					player.getActionManager().setAction(
							new PickPocketAction(npc, pocket));
					return;
				}
				if (npc instanceof Familiar) {
					if (npc.getDefinitions().hasOption("store")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						player.getFamiliar().store();
					} else if (npc.getDefinitions().hasOption("cure")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						if (!player.getPoison().isPoisoned()) {
							player.getPackets().sendGameMessage(
									"Your arent poisoned or diseased.");
							return;
						} else {
							player.getFamiliar().drainSpecial(2);
							player.addPoisonImmune(120);
						}
					}
					return;
				}
				npc.faceEntity(player);
				if (!player.getControlerManager().processNPCClick2(npc))
					return;
				if (npc.getId() == 9707)
					FremennikShipmaster.sail(player, true);
				if (npc.getId() == 3463 || npc.getId() == 8699 || npc.getId() == 5442) {
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
				else if (npc.getId() == 9708)
					FremennikShipmaster.sail(player, false);
				else if (npc.getId() == 13455 || npc.getId() == 2617 || npc.getId() == 2618 
						|| npc.getId() == 15194)
					player.getBank().openBank();
				else if (npc.getId() == 4243)
				player.getDialogueManager().startDialogue("Butler", npc.getId());
				else if (npc.getId() == 528 || npc.getId() == 529)
					ShopsHandler.openShop(player, 1);
				else if (npc.getId() == 519)
					ShopsHandler.openShop(player, 2);
				else if (npc.getId() == 520 || npc.getId() == 521)
					ShopsHandler.openShop(player, 4);
				else if (npc.getId() == 209) {
					if (player.completedDwarfCannonQuest == true && player.isDonator()) { 
					ShopsHandler.openShop(player, 43);
					}
					if (player.completedDwarfCannonQuest == true && player.isExtremeDonator()) { 
						ShopsHandler.openShop(player, 44);
					}
				}

				
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 6);
				else if (npc.getId() == 522 || npc.getId() == 523)
					ShopsHandler.openShop(player, 8);
				else if (npc.getId() == 546)
					ShopsHandler.openShop(player, 10);
				else if (npc.getId() == 11475)
					ShopsHandler.openShop(player, 9);
				else if (npc.getId() == 551)
					ShopsHandler.openShop(player, 13);
				else if (npc.getId() == 550)
					ShopsHandler.openShop(player, 14);
				else if (npc.getId() == 549)
					ShopsHandler.openShop(player, 15);	
				else if (npc.getId() == 548)
					ShopsHandler.openShop(player, 18); //thesalia
				else if (npc.getId() == 2233 || npc.getId() == 3671)
					ShopsHandler.openShop(player, 20);
				else if (npc.getId() == 579)  //Drogo's mining Emporium
					ShopsHandler.openShop(player, 30);
				else if (npc.getId() == 582) //dwarves general store
					ShopsHandler.openShop(player, 31);
				else if (npc.getId() == 594)  //Nurmof's Pickaxe Shop
					ShopsHandler.openShop(player, 32);
				else if (npc.getId() == 537) //Scavvo's Rune Store
					ShopsHandler.openShop(player, 12);
				else if (npc.getId() == 536) //Valaine's Shop of Champions
					ShopsHandler.openShop(player, 17);
				else if (npc.getId() == 4563) //Crossbow Shop
					ShopsHandler.openShop(player, 33);
				else if (npc.getId() == 11679) //Crossbow Shop
					ShopsHandler.openShop(player, 28);
				else if (npc.getId() == 1917) //Charms
					ShopsHandler.openShop(player, 34);
				else if (npc.getId() == 555) //summon
					ShopsHandler.openShop(player, 19);
				else if (npc.getId() == 556) //trivia
					ShopsHandler.openShop(player, 40);
				else if(npc.getId() == 15149)
					player.getDialogueManager().startDialogue("MasterOfFear", 3);
				else if (npc.getId() == 599)
					PlayerLook.openMageMakeOver(player);
				else if (npc.getId() == 598)
					PlayerLook.openHairdresserSalon(player);
				else if (npc instanceof Pet) {
					if (npc != player.getPet()) {
						player.getPackets().sendGameMessage("This isn't your pet!");
						return;
					}
					Pet pet = player.getPet();
					player.getPackets().sendMessage(99, "Pet [id=" + pet.getId() 
							+ ", hunger=" + pet.getDetails().getHunger()
							+ ", growth=" + pet.getDetails().getGrowth()
							+ ", stage=" + pet.getDetails().getStage() + "].", player);
				}
				else {
					player.getPackets().sendGameMessage(
							"Nothing interesting happens.");
					if (Settings.DEBUG)
						System.out.println("cliked 2 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
				}
			}
		}, npc.getSize()));
	}

	public static void handleOption3(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				if (!player.getControlerManager().processNPCClick3(npc))
					return;
				player.faceEntity(npc);
				if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					MiningBase.propect(player, "You examine the remains...", "The remains contain traces of living minerals.");
					return;
					
				}
				npc.faceEntity(player);
				if ((npc.getId() == 8462 || npc.getId() == 8464
						|| npc.getId() == 1597 || npc.getId() == 1598
						|| npc.getId() == 7780 || npc.getId() == 8467 || npc
						.getId() == 9084))
					ShopsHandler.openShop(player, 1);
				else if (npc.getId() == 7557)
					player.getDialogueManager().startDialogue("FarmingTeleports");
				else if (npc.getId() == 3021)
					player.getDialogueManager().startDialogue("FarmingTeleport");
				else if (npc.getId() == 548)
					PlayerLook.openThessaliasMakeOver(player);
				else if (npc.getId() == 5532) {
					npc.setNextForceTalk(new ForceTalk("Senventior Disthinte Molesko!"));
					player.getControlerManager().startControler("SorceressGarden");
					
				} else
					player.getPackets().sendGameMessage(
							"Nothing interesting happens.");
			}
		}, npc.getSize()));
		if (Settings.DEBUG)
			System.out.println("cliked 3 at npc id : "
					+ npc.getId() + ", " + npc.getX() + ", "
					+ npc.getY() + ", " + npc.getPlane());
	}
}
