package com.rs.net.decoders.handlers;

import java.util.List;
import com.rs.game.minigames.CrystalChest;
import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cores.WorldThread;
import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.minigames.Flower;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player.AlchType;
import com.rs.game.player.dialogues.magic;
import com.rs.game.player.ClueScrolls;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.Hit.HitLook;
import com.rs.game.Hit;
import com.rs.game.Graphics;
import com.rs.game.ForceTalk;
import com.rs.game.player.actions.BoxAction;
import com.rs.game.player.actions.BoxAction.HunterEquipment;
import com.rs.game.player.actions.Firemaking;
import com.rs.game.player.actions.Fletching;
import com.rs.game.player.actions.Fletching.Fletch;
import com.rs.game.player.actions.GemCutting;
import com.rs.game.player.actions.GemCutting.Gem;
import com.rs.game.player.actions.HerbCleaning;
import com.rs.game.player.actions.Herblore;
import com.rs.game.player.actions.LeatherCrafting;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.player.content.AncientEffigies;
import com.rs.game.player.content.ArmourSets;
import com.rs.game.player.content.ArmourSets.Sets;
import com.rs.game.player.content.Burying.Bone;
import com.rs.game.player.content.Dicing;
import com.rs.game.player.content.DonationBox;
import com.rs.game.player.content.SquealOfFortune;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.DwarfCannon;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.controlers.Barrows;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class InventoryOptionsHandler {
	
	public static boolean canOpen(Player player){
		if(player.getInventory().containsItem(995, 2000000000)){
		return true;
		} else {
		player.getPackets().sendGameMessage("<col=FFFF00>You Do Not Have 2B In Your Inventory.</col>");
		return false;
		}
		}

	public static void handleItemOption2(final Player player, final int slotId, final int itemId, Item item) {
		if (Firemaking.isFiremaking(player, itemId))
			return;
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.emptyPouch(player, pouch);
			player.stopAll(false);
		} else if (itemId >= 15086 && itemId <= 15100) {
			Dicing.handleRoll(player, itemId, true); 
			return;
		} else {
			if (player.isEquipDisabled())
				return;
			long passedTime = Utils.currentTimeMillis()
					- WorldThread.LAST_CYCLE_CTM;
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					List<Integer> slots = player.getSwitchItemCache();
					int[] slot = new int[slots.size()];
					for (int i = 0; i < slot.length; i++)
						slot[i] = slots.get(i);
					player.getSwitchItemCache().clear();
					ButtonHandler.sendWear(player, slot);
					player.stopAll(false, true, false);
				}
			}, passedTime >= 600 ? 0 : passedTime > 330 ? 1 : 0);
			if (player.getSwitchItemCache().contains(slotId))
				return;
			player.getSwitchItemCache().add(slotId);
		}
	}
	
	public static void DFS(Player player, final int slotId, final int itemId) {
		if (itemId == 11283) {
			player.out("You're shield has " +player.DFS+ " charges.");
		} else {
		}
	}
	
	public static void dig(final Player player) {
		player.resetWalkSteps();
		player.setNextAnimation(new Animation(830));
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.unlock();
				if (Barrows.digIntoGrave(player))
					return;
				if(player.getX() == 3005 && player.getY() == 3376
						|| player.getX() == 2999 && player.getY() == 3375
						|| player.getX() == 2996 && player.getY() == 3377
						|| player.getX() == 2989 && player.getY() == 3378
						|| player.getX() == 2987 && player.getY() == 3387
						|| player.getX() == 2984 && player.getY() == 3387) {
					//mole
					player.setNextWorldTile(new WorldTile(1752, 5137, 0));
					player.getPackets().sendGameMessage("You seem to have dropped down into a network of mole tunnels.");
					return;
				}
				if (ClueScrolls.digSpot(player)){
					return;
				}
				player.getPackets().sendGameMessage("You find nothing.");
			}
			
		});
	}

	public static void handleItemOption1(Player player, final int slotId,
			int itemId, Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (Foods.eat(player, item, slotId))
			return;
		if (itemId >= 15086 && itemId <= 15100) {
			Dicing.handleRoll(player, itemId, false); 
			return;
		}
                if (itemId == 6199) {
			DonationBox.Reward(player);
            player.getInventory().deleteItem(6199, 1);
		}
               if (itemId == 23715) {
			player.getDialogueManager().startDialogue("LargeLamp");
		}
                if (itemId == 15262) {
            player.getInventory().deleteItem(15262, 1);
            player.getInventory().addItem(12183, 5000);
		}
		if (itemId == 5073) {
			player.seedNest();
		}
		if (itemId == 5074) {
			player.ringNest();
		}
		if (itemId == 6) {//Cannon
			if (player.isDonator()) {
			player.getDwarfCannon().checkLocation();
		} else {
			player.out("You must be a Donator to set up a Cannon.");
				}
			}
		if (itemId == 20498) {//Royal Cannon
			if (player.isExtremeDonator()) {
			player.getDwarfCannon().checkRoyalLocation();
		} else {
			player.out("You must be an Extreme Donator to set up a Royal Cannon.");
			}
		}  
              if (itemId == 20264 ||  itemId == 20266 || itemId == 20268) { //infernal ashes
				player.setNextAnimation(new Animation(445));
				player.setNextGraphics(new Graphics(40));
				player.getSkills().addXp(Skills.PRAYER, 40);
				player.getInventory().deleteItem(itemId, 1);
				player.getPackets().sendGameMessage("You scatter the ashes in the wind.");
                        }
      if (itemId == 23749) {
            player.getSkills().addXp(Skills.DUNGEONEERING, 300);
            player.getInventory().deleteItem(23749, 1);
            player.getPackets().sendGameMessage("You have earned yourself some Dungeoneering Experience.");	
}	
if (itemId == CrystalChest.toothHalf()
        && itemId == CrystalChest.loopHalf() 
	|| itemId == CrystalChest.loopHalf()
	&& itemId == CrystalChest.toothHalf()){
	CrystalChest.makeKey(player);
	return;
}
      if (itemId == 23786) {
            player.getSkills().addXp(Skills.MINING, 50);
            player.getInventory().deleteItem(23786, 1);
            player.getPackets().sendGameMessage("You have earned yourself some mining Experience.");	
}	
      if (itemId == 19644) {
            player.getSkills().addXp(Skills.ATTACK, 100);
            player.getInventory().deleteItem(19644, 1);
            player.getPackets().sendGameMessage("You have earned yourself some attack Experience.");	
}
      if (itemId == 19643) {
            player.getSkills().addXp(Skills.DEFENCE, 100);
            player.getInventory().deleteItem(19643, 1);
            player.getPackets().sendGameMessage("You have earned yourself some defence Experience.");	
}	
      if (itemId == 19791) {
            player.getSkills().addXp(Skills.STRENGTH, 100);
            player.getInventory().deleteItem(19791, 1);
            player.getPackets().sendGameMessage("You have earned yourself some strength Experience.");	
}	
      if (itemId == 23787) {
            player.getSkills().addXp(Skills.MINING, 100);
            player.getInventory().deleteItem(23787, 1);
            player.getPackets().sendGameMessage("You have earned yourself some mining Experience.");	
}	
      if (itemId == 23788) {
            player.getSkills().addXp(Skills.MINING, 150);
            player.getInventory().deleteItem(23788, 1);
            player.getPackets().sendGameMessage("You have earned yourself some mining Experience.");	
}
      if (itemId == 23789) {
            player.getSkills().addXp(Skills.MINING, 200);
            player.getInventory().deleteItem(23789, 1);
            player.getPackets().sendGameMessage("You have earned yourself some mining Experience.");	
}				
		if (itemId == 5072) {
			player.getInventory().deleteItem(5072, 1);
			player.getInventory().addItem(5077, 1);
		}
		if (itemId == 6542) {
			player.getInventory().deleteItem(6542, 1);
			player.getInventory().addItem(24155, 1);
		}		
		if (itemId == 5071) {
			player.getInventory().deleteItem(5071, 1);
			player.getInventory().addItem(5078, 1);
		}
		if (itemId == 5070) {
			player.getInventory().deleteItem(5070, 1);
			player.getInventory().addItem(5076, 1);
		}
		if (itemId == 621 && canOpen(player)) {
			player.getInventory().addItem(995, 1000000000);
			player.getInventory().deleteItem(621, 1);
			player.getPackets().sendGameMessage("You Have Swapped A Cheque for 1B cash.");
			return;
		}
		
		if (itemId == 621 && canOpen(player)) {
			player.getInventory().addItem(995, 1000000000);
			player.getInventory().deleteItem(621, 1);
			player.getPackets().sendGameMessage("You Have Swapped A Cheque for 1B cash.");
			return;
		}
		//if (itemId == 299 && player.isExtremeDonator()) {
		//	Flower.PlantFlower(player);
		//	return;
		//}
		if (Pots.pot(player, item, slotId))
			return;
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.fillPouch(player, pouch);
			return;
		}
		for (int i: ClueScrolls.ScrollIds){
			if (itemId == i){
				if (ClueScrolls.Scrolls.getMap(itemId) != null){
					ClueScrolls.showMap(player, ClueScrolls.Scrolls.getMap(itemId));
					return;
				}
				if (ClueScrolls.Scrolls.getObjMap(itemId) != null){
					ClueScrolls.showObjectMap(player, ClueScrolls.Scrolls.getObjMap(itemId));
					return;
				}
				if (ClueScrolls.Scrolls.getRiddles(itemId) != null){
					ClueScrolls.showRiddle(player, ClueScrolls.Scrolls.getRiddles(itemId));
					return;
				}
			}
			
		}
				if (itemId == 2717){
					ClueScrolls.giveReward(player);
				}
		if (itemId == 771) {// Dramen branch
		player.getInventory().deleteItem(771, 1);
		player.getInventory().addItem(772, 1);
		player.getInventory().refresh();
                return;
                }
		if (itemId == 23724) {// huge strength lamp
			player.getSkills().addXp(2, 80);
			player.getInventory().deleteItem(23724, 1);
			return;
		}
		if (itemId == 23750) {// medium dung lamp
			player.getSkills().addXp(24, 55);
			player.getInventory().deleteItem(23750, 1);
			return;
		}
		if (itemId == 23757) {// small agility lamp
			player.getSkills().addXp(16, 30);
			player.getInventory().deleteItem(23757, 1);
			return;
		}
		if (itemId == 11157) {// small dung lamp
			player.getSkills().addXp(Skills.DUNGEONEERING, 30000);
			player.getInventory().deleteItem(11157, 1);
			return;
		}
		if (itemId == 7509) {//rock cake
			if (player.getControlerManager().getControler() instanceof DuelArena || player.getControlerManager().getControler() instanceof DuelControler) {
				player.out("You cannot use this here.");
				return;
			} else {
			player.applyHit(new Hit(player, 100, HitLook.REGULAR_DAMAGE));
			player.setNextForceTalk(new ForceTalk("Ow! I nearly broke a tooth!"));
			}
			return;
		}
		if (itemId == 24154) {
			player.spins += 1;
			player.refreshSqueal();
			player.getPackets().sendGameMessage("You have received spin on the Squeal of Fortune!");
			player.getInventory().deleteItem(24154, 1);
                        player.refreshSqueal();
		}
		if (itemId == 24155) {
			player.spins += 2;
			player.refreshSqueal();
			player.getPackets().sendGameMessage("You have received 2 spins on the Squeal of Fortune!");
			player.getInventory().deleteItem(24155, 1);
                        player.refreshSqueal();
		}
		 if (itemId == 2694){
    		player.getInventory().deleteItem(2694, 1);
			player.getInventory().addItem(2677, 1);	
			}
		 if (itemId == 2695){
    		player.getInventory().deleteItem(2695, 1);
			player.getInventory().addItem(2678, 1);	
			}
		 if (itemId == 2696){
    		player.getInventory().deleteItem(2696, 1);
			player.getInventory().addItem(2679, 1);
			}
		 if (itemId == 2697){
    		player.getInventory().deleteItem(2697, 1);
			player.getInventory().addItem(2680, 1);	
			}
		 if (itemId == 2698){
    		player.getInventory().deleteItem(2698, 1);
			player.getInventory().addItem(2681, 1);
			}
		if (itemId == 22370) {
			Summoning.openDreadnipInterface(player);
		}
		if (itemId == 15707) {
		player.getInterfaceManager().sendInterface(938);
        	}
		if (itemId == 952) {// spade
			dig(player);
			return;
		}
		if (HerbCleaning.clean(player, item, slotId))
			return;
		Bone bone = Bone.forId(itemId);
		if (bone != null) {
			Bone.bury(player, slotId);
			return;
		}
		if (Magic.useTabTeleport(player, itemId))
			return;
		if (itemId == AncientEffigies.SATED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.GORGED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.NOURISHED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.STARVED_ANCIENT_EFFIGY)
			player.getDialogueManager().startDialogue("AncientEffigiesD",
					itemId);
		else if (itemId == 4155)
			player.getDialogueManager().startDialogue("EnchantedGemDialouge");
		else if (itemId >= 23653 && itemId <= 23658)
			FightKiln.useCrystal(player, itemId);
				
		else if (itemId == 1856) {// Information Book
			player.getInterfaceManager().sendInterface(275);
			player.getPackets()
					.sendIComponentText(275, 2, Settings.SERVER_NAME);
			player.getPackets().sendIComponentText(275, 16,
					"Welcome to " + Settings.SERVER_NAME + ".");
			player.getPackets().sendIComponentText(275, 17,
					"If want some an item use command ::item id.");
			player.getPackets().sendIComponentText(275, 18,
					"If you don't have an item list you can find ids");
			player.getPackets().sendIComponentText(275, 19,
					"at http://itemdb.biz");
			player.getPackets().sendIComponentText(275, 20,
					"You can change your prayers and spells at home.");
			player.getPackets().sendIComponentText(275, 21,
					"If you need any help, do ::ticket. (Don't abuse it)");
			player.getPackets().sendIComponentText(275, 22,
					"at start of your message on public chat.");
			player.getPackets().sendIComponentText(275, 22,
					"By the way you can compare your ::score with your mates.");
			player.getPackets().sendIComponentText(275, 23,
					"Oh and ye, don't forget to ::vote and respect rules.");
			player.getPackets().sendIComponentText(275, 24, "");
			player.getPackets().sendIComponentText(275, 25,
					"Forums: " + Settings.WEBSITE_LINK);
			player.getPackets().sendIComponentText(275, 26, "");
			player.getPackets().sendIComponentText(275, 27,
					"Enjoy your time on " + Settings.SERVER_NAME + ".");
			player.getPackets().sendIComponentText(275, 28,
					"<img=1> Staff Team");
			player.getPackets().sendIComponentText(275, 29, "");
			player.getPackets().sendIComponentText(275, 30, "");
			player.getPackets().sendIComponentText(275, 14,
					"<u>Visit Website</u>");
			for (int i = 31; i < 300; i++)
				player.getPackets().sendIComponentText(275, i, "");
		} else if (itemId == HunterEquipment.BOX.getId()) // almost done
			player.getActionManager().setAction(new BoxAction(HunterEquipment.BOX));
		else if (itemId == HunterEquipment.BRID_SNARE.getId())
			player.getActionManager().setAction(
					new BoxAction(HunterEquipment.BRID_SNARE));
		else if (item.getDefinitions().getName().startsWith("Burnt")) 
			player.getDialogueManager().startDialogue("SimplePlayerMessage", "Ugh, this is inedible.");
		
		if (Settings.DEBUG)
			Logger.log("ItemHandler", "Item Select:" + itemId + ", Slot Id:"
					+ slotId);
	}

	/*
	 * returns the other
	 */
	public static Item contains(int id1, Item item1, Item item2) {
		if (item1.getId() == id1)
			return item2;
		if (item2.getId() == id1)
			return item1;
		return null;
	}

	public static boolean contains(int id1, int id2, Item... items) {
		boolean containsId1 = false;
		boolean containsId2 = false;
		for (Item item : items) {
			if (item.getId() == id1)
				containsId1 = true;
			else if (item.getId() == id2)
				containsId2 = true;
		}
		return containsId1 && containsId2;
	}
	
	public static void handleMagicOnItem(final Player player, InputStream stream) {
		int inventoryInter = stream.readInt() >> 16;
		int itemId = stream.readShort128();
		@SuppressWarnings("unused")
		int junk = stream.readShort();
		@SuppressWarnings("unused")
		int itemSlot = stream.readShortLE();
		int interfaceSet = stream.readIntV1();
		int spellId = interfaceSet & 0xFFF;
		int magicInter = interfaceSet >> 16;
		if (inventoryInter == 149 && magicInter == 192) {
			switch (spellId) {
			case 59:// High Alch
				if (player.getSkills().getLevel(Skills.MAGIC) < 55) {
					player.getPackets()
					.sendGameMessage(
							"You do not have the required level to cast this spell.");
					return;
				}
				if (itemId == 995) {
					player.getPackets().sendGameMessage(
							"You can't alch this!");
					return;
				}
				if (player.getEquipment().getWeaponId() == 1401
						|| player.getEquipment().getWeaponId() == 3054
						|| player.getEquipment().getWeaponId() == 19323) {
					if (!player.getInventory().containsItem(561, 1)) {
						player.getPackets()
						.sendGameMessage(
								"You do not have the required runes to cast this spell.");
						return;
					}
					player.setNextAnimation(new Animation(9633));
					player.setNextGraphics(new Graphics(112));
					player.getInventory().deleteItem(561, 1);
					player.getInventory().deleteItem(itemId, 1);
					player.getInventory().addItem(995, new Item(itemId, 1).getDefinitions().getValue() >> 6);
				} else {
					if (!player.getInventory().containsItem(561, 1)
							|| !player.getInventory().containsItem(554, 5)) {
						player.getPackets()
						.sendGameMessage(
								"You do not have the required runes to cast this spell.");
						return;
					}
					player.setNextAnimation(new Animation(713));
					player.setNextGraphics(new Graphics(113));
					player.getInventory().deleteItem(561, 1);
					player.getInventory().deleteItem(554, 5);
					player.getInventory().deleteItem(itemId, 1);
					player.getInventory()
					.addItem(
							995,
							new Item(itemId, 1).getDefinitions()
							.getValue() >> 6);
				}
				break;
			default:
				System.out.println("Spell:" + spellId + ", Item:" + itemId);
			}
		}
	}

	public static void handleItemOnItem(final Player player, InputStream stream) {
		int itemUsedWithId = stream.readShort();
		int toSlot = stream.readShortLE128();
		int interfaceId = stream.readInt() >> 16;
		int interfaceId2 = stream.readInt() >> 16;
		int fromSlot = stream.readShort();
		int itemUsedId = stream.readShortLE128();
		int itemId = stream.readShort128();

		
		/*if (interfaceId == 192 && interfaceId == 679) {
			if (comp1 == 59) {
				player.setAlchType(AlchType.HIGH);
			} else if (comp1 == 38) {
				player.setAlchType(AlchType.LOW);
			}
			if (player.getAlchType() != null) {
				Item itemToAlch = player.getInventory().getItem(toSlot);
				if (itemToAlch == null)
					return;
				ItemDefinitions def = ItemDefinitions.forId(itemToAlch.getId());
				if (def == null)
					return;
				switch(player.getAlchType()) {
				case HIGH:
					if (itemToAlch.getId() == 1045) {
					player.out("High");
					}
					break;
				case LOW:
					player.out("Low");
					break;
				}
			}
		}*/
		
		if ((interfaceId2 == 747 || interfaceId2 == 662)
				&& interfaceId == Inventory.INVENTORY_INTERFACE) {
			if (player.getFamiliar() != null) {
				player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.ITEM) {
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(toSlot);
				}
			}
			return;
		}

        if (itemUsedId == 1755 && itemUsedWithId == 11967) {
    		player.getInventory().deleteItem(11967, 1);
			player.getInventory().addItem(6, 1);
			player.getInventory().addItem(8, 1);
			player.getInventory().addItem(10, 1);
			player.getInventory().addItem(12, 1);
    		return;
        }
        if (itemUsedId == 11286 && itemUsedWithId == 1540 || itemUsedId == 1540 && itemUsedWithId == 11286) {
    		player.getInventory().deleteItem(1540, 1);
    		player.getInventory().deleteItem(11286, 1);
    		player.getInventory().addItem(11283, 1);
    		return;
        }
        if (itemUsedId == 1755 && itemUsedWithId == 1615 ) {
    		player.getInventory().deleteItem(1615, 1);
    		player.getInventory().addItem(9244, 15);
    		return;
        }
        if (itemUsedId == 1755 && itemUsedWithId == 989 ) {
    		player.getInventory().deleteItem(989, 1);
			 CrystalChest.searchChest(player);
    		return;
        }		
        if (itemUsedId == 1755 && itemUsedWithId == 1615 ) {
    		player.getInventory().deleteItem(1615, 1);
    		player.getInventory().addItem(9244, 15);
    		return;
        }		
        if (itemUsedId == 1755 && itemUsedWithId == 7147 ) {
    		player.getInventory().deleteItem(7147, 1);
    		player.getInventory().addItem(2, 100);
    		return;
        }
        if (itemUsedId == 1755 && itemUsedWithId == 1601 ) {
    		player.getInventory().deleteItem(1601, 1);
    		player.getInventory().addItem(9243, 15);
    		return;
        }
        if (itemUsedId == 1755 && itemUsedWithId == 6573 ) {
    		player.getInventory().deleteItem(6573, 1);
    		player.getInventory().addItem(9245, 15);
    		return;
        }		
                if (itemUsedId == 2347 && itemUsedWithId == 11967) {
		World.spawnObject(new WorldObject(6, 10, 3, 2540, 5774, 0), true);
                player.setNextAnimation(new Animation(11900));
                player.setNextGraphics(new Graphics(2069));
                return;
                }

                if (itemUsedId == 11710 && itemUsedWithId == 11712 || (itemUsedId == 11712 && itemUsedWithId == 11710)) {
		player.getInventory().deleteItem(11710, 1);
		player.getInventory().deleteItem(11712, 1);
		player.getInventory().addItem(11686, 1);
		player.getInventory().refresh();
                return;
                }
            if (itemUsedId == 15098 || itemUsedWithId == 15098) {
		player.getDialogueManager().startDialogue("Dicing");
                   		player.getPackets().sendGameMessage("Rolling...");
                player.setNextGraphics(new Graphics(2075));
                player.setNextAnimation(new Animation(11900));
                int random = Utils.getRandom(100);
                int numberRolled = Utils.getRandom(100);
                player.setNextForceTalk(new ForceTalk("You rolled <col=FF0000>" + numberRolled + "</col> " + "on the percentile dice"));
                player.getPackets().sendGameMessage("rolled <col=FF0000>" + numberRolled + "</col> " + "on the percentile dice"); 
			 
                }
		if (itemUsedId == 22448 || itemUsedWithId == 554 || itemUsedWithId == 22498) {
                    if (player.getInventory().containsItem(22448, 3000)
                                    && player.getInventory().containsItem(554, 25000)
                                    && player.getInventory().containsItem(22498, 1)) {
                            player.getInventory().deleteItem(22448, 3000);
                            player.getInventory().deleteItem(554, 25000);
                            player.getInventory().deleteItem(22498, 1);
			    player.setNextAnimation(new Animation(15434));
                            player.getInventory().addItem(22494, 1);
                            player.getPackets().sendGameMessage("You attached the shards and made a Polypore Staff.");
                    }
                }				
		if (itemUsedId == 22456 || itemUsedWithId == 22451) {
                    if (player.getInventory().containsItem(22456, 1)
                                    && player.getInventory().containsItem(22451, 3500)) {
                            player.getInventory().deleteItem(22451, 3500);
                            player.getInventory().deleteItem(22456, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22490, 1);
                            player.getPackets().sendGameMessage("You attached the Poncho and Flakes to make a Ganodermic Poncho.");
                    }
                }
		if (itemUsedId == 22451 || itemUsedWithId == 22454) {
                    if (player.getInventory().containsItem(22451, 1500)
                                    && player.getInventory().containsItem(22454, 1)) {
                            player.getInventory().deleteItem(22451, 1500);
                            player.getInventory().deleteItem(22454, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22486, 1);
                            player.getPackets().sendGameMessage("You attached the Leggings and Flakes to make a Ganodermic Leggings.");
                    }
                }
		if (itemUsedId == 22451 || itemUsedWithId == 22452) {
                    if (player.getInventory().containsItem(22451, 500)
                                    && player.getInventory().containsItem(22452, 1)) {
                            player.getInventory().deleteItem(22451, 500);
                            player.getInventory().deleteItem(22452, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22482, 1);
                            player.getPackets().sendGameMessage("You attached the Visor and Flakes to make a Ganodermic Visor.");
                    }
                }
		if (itemUsedId == 22450 || itemUsedWithId == 22456) {
                    if (player.getInventory().containsItem(22450, 3500)
                                    && player.getInventory().containsItem(22456, 1)) {
                            player.getInventory().deleteItem(22450, 3500);
                            player.getInventory().deleteItem(22456, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22478, 1);
                            player.getPackets().sendGameMessage("You attached the Poncho and Flakes to make a Grifolic Poncho.");
                    }
                }
		if (itemUsedId == 22450 || itemUsedWithId == 22454) {
                    if (player.getInventory().containsItem(22450, 1500)
                                    && player.getInventory().containsItem(22454, 1)) {
                            player.getInventory().deleteItem(22450, 1500);
                            player.getInventory().deleteItem(22454, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22474, 1);
                            player.getPackets().sendGameMessage("You attached the Leggings and Flakes to make a Grifolic Leggings.");
                    }
                }
		if (itemUsedId == 22450 || itemUsedWithId == 22452) {
                    if (player.getInventory().containsItem(22450, 500)
                                    && player.getInventory().containsItem(22452, 1)) {
                            player.getInventory().deleteItem(22450, 500);
                            player.getInventory().deleteItem(22452, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22470, 1);
                            player.getPackets().sendGameMessage("You attached the Visor and Flakes to make a Grifolic Visor.");
                    }
                }
           	if (itemUsedId == 4151 || itemUsedWithId == 9917) {
                    if (player.getInventory().containsItem(9917, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(9917, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(15441, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
	    	if (itemUsedId == 4151 || itemUsedWithId == 9915) {
                    if (player.getInventory().containsItem(9915, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(9915, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(15442, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
	    	if (itemUsedId == 4151 || itemUsedWithId == 9913) {
                    if (player.getInventory().containsItem(9913, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(9913, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(15443, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
	    	if (itemUsedId == 4151 || itemUsedWithId == 9916) {
                    if (player.getInventory().containsItem(9916, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(9916, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(15444, 1);
			    player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
		if (itemUsedId == 22449 || itemUsedWithId == 22456) {
                    if (player.getInventory().containsItem(22449, 3500)
                                    && player.getInventory().containsItem(22456, 1)) {
                            player.getInventory().deleteItem(22449, 3500);
                            player.getInventory().deleteItem(22456, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22466, 1);
                            player.getPackets().sendGameMessage("You attached the Poncho and Flakes to make a Fungal Poncho.");
                    }
                }
		if (itemUsedId == 22449 || itemUsedWithId == 22454) {
                    if (player.getInventory().containsItem(22449, 1500)
                                    && player.getInventory().containsItem(22454, 1)) {
                            player.getInventory().deleteItem(22449, 1500);
                            player.getInventory().deleteItem(22454, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22462, 1);
                            player.getPackets().sendGameMessage("You attached the Leggings and Flakes to make a Fungal Leggings.");
                    }
                }
		if (itemUsedId == 21369 || itemUsedWithId == 4151) {
                    if (player.getInventory().containsItem(21369, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(21369, 1);
                            player.getInventory().deleteItem(4151, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(21371, 1);
                            player.getPackets().sendGameMessage("You attached the Vine with the Whip to make An Abbyssal Vine Whip.");
                    }
                }
		if (itemUsedId == 22449 || itemUsedWithId == 22452) {
                    if (player.getInventory().containsItem(22449, 500)
                                    && player.getInventory().containsItem(22452, 1)) {
                            player.getInventory().deleteItem(22449, 500);
                            player.getInventory().deleteItem(22452, 1);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(22458, 1);
                            player.getPackets().sendGameMessage("You attached the Visor and Flakes to make a Fungal Visor.");
                    }
                }				
                if (itemUsedId == 2368 || itemUsedWithId == 2366) {
                    if (player.getInventory().containsItem(2368, 1)
                                    && player.getInventory().containsItem(2366, 1)) {
                            player.getInventory().deleteItem(2368, 1);
                            player.getInventory().deleteItem(2366, 1);
                            player.getInventory().addItem(1187, 1);
                            player.getPackets().sendGameMessage("You attach the shield halves to each other.");
                    }
                }
                if (itemUsedId == 2366 || itemUsedWithId == 2368) {
                    if (player.getInventory().containsItem(2368, 1)
                                    && player.getInventory().containsItem(2366, 1)) {
                            player.getInventory().deleteItem(2368, 1);
                            player.getInventory().deleteItem(2366, 1);
                            player.getInventory().addItem(1187, 1);
                            player.getPackets().sendGameMessage("You attach the shield halves to each other.");
                    }
                }
                if (itemUsedId == 19333 || itemUsedWithId == 6585) {
                    if (player.getInventory().containsItem(6585, 1)
                                    && player.getInventory().containsItem(19333, 1)) {
                            player.getInventory().deleteItem(6585, 1);
                            player.getInventory().deleteItem(19333, 1);
                            player.getInventory().addItem(19335, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
                if (itemUsedId == 19348 || itemUsedWithId == 4585) {
                    if (player.getInventory().containsItem(4585, 1)
                                    && player.getInventory().containsItem(19348, 1)) {
                            player.getInventory().deleteItem(4585, 1);
                            player.getInventory().deleteItem(19348, 1);
                            player.getInventory().addItem(19339, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
	    	if (itemUsedId == 269 || itemUsedWithId == 15308) {
                    if (player.getInventory().containsItem(269, 1)
                                    && player.getInventory().containsItem(15308, 1)
                                    && player.getInventory().containsItem(15312, 1)
                                    && player.getInventory().containsItem(15316, 1)		
	                            && player.getInventory().containsItem(15320, 1)								
                                    && player.getInventory().containsItem(15324, 1)) {
                            player.getInventory().deleteItem(15308, 1);
                            player.getInventory().deleteItem(15312, 1);
                            player.getInventory().deleteItem(15316, 1);
                            player.getInventory().deleteItem(15320, 1);
                            player.getInventory().deleteItem(15324, 1);
                            player.getInventory().deleteItem(269, 1);							
                            player.getInventory().addItem(15332, 1);
							player.setNextAnimation(new Animation(363));
                            player.getPackets().sendGameMessage("you mixed the potions to an overload.");
                    }
                }
                if (itemUsedId == 19348 || itemUsedWithId == 4087) {
                    if (player.getInventory().containsItem(4087, 1)
                                    && player.getInventory().containsItem(19348, 1)) {
                            player.getInventory().deleteItem(4087, 1);
                            player.getInventory().deleteItem(19348, 1);
                            player.getInventory().addItem(19338, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
                if (itemUsedId == 19350 || itemUsedWithId == 14479) {
                    if (player.getInventory().containsItem(14479, 1)
                                    && player.getInventory().containsItem(19350, 1)) {
                            player.getInventory().deleteItem(14479, 1);
                            player.getInventory().deleteItem(19350, 1);
                            player.getInventory().addItem(19337, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }	
                if (itemUsedId == 19346 || itemUsedWithId == 11335) {
                    if (player.getInventory().containsItem(11335, 1)
                                    && player.getInventory().containsItem(19346, 1)) {
                            player.getInventory().deleteItem(11335, 1);
                            player.getInventory().deleteItem(19346, 1);
                            player.getInventory().addItem(19336, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
                if (itemUsedId == 19352 || itemUsedWithId == 1187) {
                    if (player.getInventory().containsItem(1187, 1)
                                    && player.getInventory().containsItem(19352, 1)) {
                            player.getInventory().deleteItem(1187, 1);
                            player.getInventory().deleteItem(19352, 1);
                            player.getInventory().addItem(19340, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }	
                if (itemUsedId == 19356 || itemUsedWithId == 4585) {
                    if (player.getInventory().containsItem(4585, 1)
                                    && player.getInventory().containsItem(19356, 1)) {
                            player.getInventory().deleteItem(4585, 1);
                            player.getInventory().deleteItem(19356, 1);
                            player.getInventory().addItem(19344, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }	
                if (itemUsedId == 19356 || itemUsedWithId == 4087) {
                    if (player.getInventory().containsItem(4087, 1)
                                    && player.getInventory().containsItem(19356, 1)) {
                            player.getInventory().deleteItem(4087, 1);
                            player.getInventory().deleteItem(19356, 1);
                            player.getInventory().addItem(19343, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
                if (itemUsedId == 19360 || itemUsedWithId == 1187) {
                    if (player.getInventory().containsItem(1187, 1)
                                    && player.getInventory().containsItem(19360, 1)) {
                            player.getInventory().deleteItem(1187, 1);
                            player.getInventory().deleteItem(19360, 1);
                            player.getInventory().addItem(19345, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
                if (itemUsedId == 19354 || itemUsedWithId == 11335) {
                    if (player.getInventory().containsItem(11335, 1)
                                    && player.getInventory().containsItem(19354, 1)) {
                            player.getInventory().deleteItem(11335, 1);
                            player.getInventory().deleteItem(19354, 1);
                            player.getInventory().addItem(19341, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
                if (itemUsedId == 19358 || itemUsedWithId == 14479) {
                    if (player.getInventory().containsItem(14479, 1)
                                    && player.getInventory().containsItem(19358, 1)) {
                            player.getInventory().deleteItem(14479, 1);
                            player.getInventory().deleteItem(19358, 1);
                            player.getInventory().addItem(19342, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
	    if (itemUsedId == 11235 || itemUsedWithId == 9915) {
                    if (player.getInventory().containsItem(9915, 1)
                                    && player.getInventory().containsItem(11235, 1)) {
                            player.getInventory().deleteItem(9915, 1);
                            player.getInventory().deleteItem(11235, 1);
                            player.getInventory().addItem(15702, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }						
	    if (itemUsedId == 11235 || itemUsedWithId == 9913) {
                    if (player.getInventory().containsItem(9913, 1)
                                    && player.getInventory().containsItem(11235, 1)) {
                            player.getInventory().deleteItem(9913, 1);
                            player.getInventory().deleteItem(11235, 1);
                            player.getInventory().addItem(15703, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }								
	    if (itemUsedId == 11235 || itemUsedWithId == 9916) {
                    if (player.getInventory().containsItem(9916, 1)
                                    && player.getInventory().containsItem(11235, 1)) {
                            player.getInventory().deleteItem(9916, 1);
                            player.getInventory().deleteItem(11235, 1);
                            player.getInventory().addItem(15704, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }								
	    if (itemUsedId == 15486 || itemUsedWithId == 9914) {
                    if (player.getInventory().containsItem(9914, 1)
                                    && player.getInventory().containsItem(15486, 1)) {
                            player.getInventory().deleteItem(9914, 1);
                            player.getInventory().deleteItem(15486, 1);
                            player.getInventory().addItem(22207, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }								
	    if (itemUsedId == 15486 || itemUsedWithId == 9917) {
                    if (player.getInventory().containsItem(9917, 1)
                                    && player.getInventory().containsItem(15486, 1)) {
                            player.getInventory().deleteItem(9917, 1);
                            player.getInventory().deleteItem(15486, 1);
                            player.getInventory().addItem(22209, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }								
	    if (itemUsedId == 15486 || itemUsedWithId == 9915) {
                    if (player.getInventory().containsItem(9915, 1)
                                    && player.getInventory().containsItem(15486, 1)) {
                            player.getInventory().deleteItem(9915, 1);
                            player.getInventory().deleteItem(15486, 1);
                            player.getInventory().addItem(22211, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
	    if (itemUsedId == 15486 || itemUsedWithId == 9916) {
                    if (player.getInventory().containsItem(9916, 1)
                                    && player.getInventory().containsItem(15486, 1)) {
                            player.getInventory().deleteItem(9916, 1);
                            player.getInventory().deleteItem(15486, 1);
                            player.getInventory().addItem(22213, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
	    if (itemUsedId == 9470 || itemUsedWithId == 9914) {
                    if (player.getInventory().containsItem(9914, 1)
                                    && player.getInventory().containsItem(9470, 1)) {
                            player.getInventory().deleteItem(9914, 1);
                            player.getInventory().deleteItem(9470, 1);
                            player.getInventory().addItem(22215, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
		if (itemUsedId == 9470 || itemUsedWithId == 9917) {
                    if (player.getInventory().containsItem(9917, 1)
                                    && player.getInventory().containsItem(9470, 1)) {
                            player.getInventory().deleteItem(9917, 1);
                            player.getInventory().deleteItem(9470, 1);
                            player.getInventory().addItem(22216, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
	    if (itemUsedId == 9470 || itemUsedWithId == 9915) {
                    if (player.getInventory().containsItem(9915, 1)
                                    && player.getInventory().containsItem(9470, 1)) {
                            player.getInventory().deleteItem(9915, 1);
                            player.getInventory().deleteItem(9470, 1);
                            player.getInventory().addItem(22217, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }								
	    if (itemUsedId == 9470 || itemUsedWithId == 9916) {
                    if (player.getInventory().containsItem(9916, 1)
                                    && player.getInventory().containsItem(9470, 1)) {
                            player.getInventory().deleteItem(9916, 1);
                            player.getInventory().deleteItem(9470, 1);
                            player.getInventory().addItem(22218, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }									
	    if (itemUsedId == 22215 || itemUsedWithId == 9913) {
                    if (player.getInventory().containsItem(9913, 1)
                                    && player.getInventory().containsItem(22215, 1)) {
                            player.getInventory().deleteItem(9913, 1);
                            player.getInventory().deleteItem(22215, 1);
                            player.getInventory().addItem(9470, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
	    if (itemUsedId == 22216 || itemUsedWithId == 9913) {
                    if (player.getInventory().containsItem(9913, 1)
                                    && player.getInventory().containsItem(22216, 1)) {
                            player.getInventory().deleteItem(9913, 1);
                            player.getInventory().deleteItem(22216, 1);
                            player.getInventory().addItem(9470, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }	
	    if (itemUsedId == 22217 || itemUsedWithId == 9913) {
                    if (player.getInventory().containsItem(9913, 1)
                                    && player.getInventory().containsItem(22217, 1)) {
                            player.getInventory().deleteItem(9913, 1);
                            player.getInventory().deleteItem(22217, 1);
                            player.getInventory().addItem(9470, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
	    if (itemUsedId == 22218 || itemUsedWithId == 9913) {
                    if (player.getInventory().containsItem(9913, 1)
                                    && player.getInventory().containsItem(22218, 1)) {
                            player.getInventory().deleteItem(9913, 1);
                            player.getInventory().deleteItem(22218, 1);
                            player.getInventory().addItem(9470, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
	    if (itemUsedId == 1755 || itemUsedWithId == 6573) {
                    if (player.getInventory().containsItem(6573, 1)
                                  && player.getInventory().containsItem(9194, 15)) {
                            player.getInventory().deleteItem(6573, 1);
    			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(9194, 15);
                            player.getPackets().sendGameMessage("you made 15 onyx bolt tips.");
                    }
                }
		if (itemUsedId == 22448 || itemUsedWithId == 554 || itemUsedWithId == 22498) {
                    if (player.getInventory().containsItem(22448, 3000)
                                    && player.getInventory().containsItem(554, 1)
                                    && player.getInventory().containsItem(22498, 1)) {
                            player.getInventory().deleteItem(22448, 3000);
                            player.getInventory().deleteItem(554, 25000);
                            player.getInventory().deleteItem(22498, 1);
			    player.setNextAnimation(new Animation(15434));
                            player.getInventory().addItem(22494, 1);
                            player.getPackets().sendGameMessage("You attached the shards and made a Polypore Staff.");
                    }
                }
            if (itemUsedId == 9381 || itemUsedWithId == 9194) {
                    if (player.getInventory().containsItem(9381, 15)
                                    && player.getInventory().containsItem(9194, 15)) {
                            player.getInventory().deleteItem(9381, 15);
                            player.getInventory().deleteItem(9194, 15);
			    player.setNextAnimation(new Animation(1249));
                            player.getInventory().addItem(9342, 15);
                            player.getPackets().sendGameMessage("you added the bolt tips to runite bolts unf.");
                    }
                }
            if (itemUsedId == 4151 || itemUsedWithId == 9917) {
                    if (player.getInventory().containsItem(9917, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(9917, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(15441, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
	    if (itemUsedId == 4151 || itemUsedWithId == 9915) {
                    if (player.getInventory().containsItem(9915, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(9915, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(15442, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
	    if (itemUsedId == 4151 || itemUsedWithId == 9913) {
                    if (player.getInventory().containsItem(9913, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(9913, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(15443, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
	    if (itemUsedId == 4151 || itemUsedWithId == 9916) {
                    if (player.getInventory().containsItem(9916, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(9916, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(15444, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }								
	    if (itemUsedId == 21371 || itemUsedWithId == 9917) {
                    if (player.getInventory().containsItem(9917, 1)
                                    && player.getInventory().containsItem(21371, 1)) {
                            player.getInventory().deleteItem(9917, 1);
                            player.getInventory().deleteItem(21371, 1);
                            player.getInventory().addItem(21372, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
	    if (itemUsedId == 21371 || itemUsedWithId == 9915) {
                    if (player.getInventory().containsItem(9915, 1)
                                    && player.getInventory().containsItem(21371, 1)) {
                            player.getInventory().deleteItem(9915, 1);
                            player.getInventory().deleteItem(21371, 1);
                            player.getInventory().addItem(21373, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
	    if (itemUsedId == 21371 || itemUsedWithId == 9916) {
                    if (player.getInventory().containsItem(9916, 1)
                                    && player.getInventory().containsItem(21371, 1)) {
                            player.getInventory().deleteItem(9916, 1);
                            player.getInventory().deleteItem(21371, 1);
                            player.getInventory().addItem(21375, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }
	    if (itemUsedId == 11235 || itemUsedWithId == 9917) {
                    if (player.getInventory().containsItem(9917, 1)
                                    && player.getInventory().containsItem(11235, 1)) {
                            player.getInventory().deleteItem(9917, 1);
                            player.getInventory().deleteItem(11235, 1);
                            player.getInventory().addItem(15701, 1);
							player.setNextAnimation(new Animation(1745));
                            player.getPackets().sendGameMessage("you added a new collor to ure item.");
                    }
                }				
                if (itemUsedId == 985 || itemUsedWithId == 987) {
                    if (player.getInventory().containsItem(985, 1)
                                    && player.getInventory().containsItem(987, 1)) {
                            player.getInventory().deleteItem(985, 1);
                            player.getInventory().deleteItem(987, 1);
                            player.getInventory().addItem(989, 1);
                            player.getPackets().sendGameMessage("You join the two halves of the key together.");
                    }
                }				
                if (itemUsedId == 24352 || itemUsedWithId == 4585) {
                    if (player.getInventory().containsItem(4585, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(4585, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24364, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
                if (itemUsedId == 24352 || itemUsedWithId == 4087) {
                    if (player.getInventory().containsItem(4087, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(4087, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24363, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }	
                if (itemUsedId == 24352 || itemUsedWithId == 14479) {
                    if (player.getInventory().containsItem(14479, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(14479, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24360, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }	
                if (itemUsedId == 24352 || itemUsedWithId == 11335) {
                    if (player.getInventory().containsItem(11335, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(11335, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24359, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
	                if (itemUsedId == 24352 || itemUsedWithId == 11732) {
                    if (player.getInventory().containsItem(11732, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(11732, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24362, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
	                if (itemUsedId == 24352 || itemUsedWithId == 7461) {
                    if (player.getInventory().containsItem(7461, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(7461, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24361, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
	                if (itemUsedId == 24352 || itemUsedWithId == 6916) {
                    if (player.getInventory().containsItem(6916, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(6916, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24355, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
	                if (itemUsedId == 24352 || itemUsedWithId == 6918) {
                    if (player.getInventory().containsItem(6918, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(6918, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24354, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
	                if (itemUsedId == 24352 || itemUsedWithId == 6920) {
                    if (player.getInventory().containsItem(6920, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(6920, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24358, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
		                if (itemUsedId == 24352 || itemUsedWithId == 6922) {
                    if (player.getInventory().containsItem(6922, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(6922, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24357, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }
		                if (itemUsedId == 24352 || itemUsedWithId == 6924) {
                    if (player.getInventory().containsItem(6924, 1)
                                    && player.getInventory().containsItem(24352, 1)) {
                            player.getInventory().deleteItem(6924, 1);
                            player.getInventory().deleteItem(24352, 1);
                            player.getInventory().addItem(24356, 1);
                            player.getPackets().sendGameMessage("You attach the kit.");
                    }
                }				
                if (itemUsedId == 11714 && itemUsedWithId == 11686 || (itemUsedId == 11686 && itemUsedWithId == 11714)) {
		player.getInventory().deleteItem(11714, 1);
		player.getInventory().deleteItem(11686, 1);
		player.getInventory().addItem(11690, 1);
		player.getInventory().refresh();
                return;
                }
                if (itemUsedId == 11702 && itemUsedWithId == 11690 || (itemUsedId == 11690 && itemUsedWithId == 11702)) {
		player.getInventory().deleteItem(11702, 1);
		player.getInventory().deleteItem(11690, 1);
		player.getInventory().addItem(11694, 1);
		player.getInventory().refresh();
                return;
                }
                if (itemUsedId == 11704 && itemUsedWithId == 11690 || (itemUsedId == 11690 && itemUsedWithId == 11704)) {
		player.getInventory().deleteItem(11704, 1);
		player.getInventory().deleteItem(11690, 1);
		player.getInventory().addItem(11696, 1);
		player.getInventory().refresh();
                return;
                }
                if (itemUsedId == 11706 && itemUsedWithId == 11690 || (itemUsedId == 11690 && itemUsedWithId == 11706)) {
		player.getInventory().deleteItem(11706, 1);
		player.getInventory().deleteItem(11690, 1);
		player.getInventory().addItem(11698, 1);
		player.getInventory().refresh();
                return;
                }
                if (itemUsedId == 11708 && itemUsedWithId == 11690 || (itemUsedId == 11690 && itemUsedWithId == 11708)) {
		player.getInventory().deleteItem(11708, 1);
		player.getInventory().deleteItem(11690, 1);
		player.getInventory().addItem(11700, 1);
		player.getInventory().refresh();
                return;
                }
		if (interfaceId == Inventory.INVENTORY_INTERFACE
				&& interfaceId == interfaceId2
				&& !player.getInterfaceManager().containsInventoryInter()) {
			if (toSlot >= 28 || fromSlot >= 28)
				return;
			Item usedWith = player.getInventory().getItem(toSlot);
			Item itemUsed = player.getInventory().getItem(fromSlot);
			if (itemUsed == null || usedWith == null
					|| itemUsedId != itemUsedId
					|| itemUsedWithId != itemUsedWithId)
				return;
			player.stopAll();
			if (!player.getControlerManager().canUseItemOnItem(itemUsed,
					usedWith))
				return;
			Fletch fletch = Fletching.isFletching(usedWith, itemUsed);
			if (fletch != null) {
				player.getDialogueManager().startDialogue("FletchingD", fletch);
				return;
			}
			int herblore = Herblore.isHerbloreSkill(itemUsed, usedWith);
			if (herblore > -1) {
				player.getDialogueManager().startDialogue("HerbloreD",
						herblore, itemUsed, usedWith);
				return;
			}
			if (itemUsedId == LeatherCrafting.NEEDLE.getId()
					|| itemUsedWithId == LeatherCrafting.NEEDLE.getId()) {
				if (LeatherCrafting
						.handleItemOnItem(player, itemUsed, usedWith)) {
					return;
				}
			}
			Sets set = ArmourSets.getArmourSet(itemUsedId, itemUsedWithId);
			if (set != null) {
				ArmourSets.exchangeSets(player, set);
				return;
			}
			if (Firemaking.isFiremaking(player, itemUsed, usedWith))
				return;
			else if (contains(1755, Gem.OPAL.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.OPAL);
			else if (contains(1755, Gem.JADE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.JADE);
			else if (contains(1755, Gem.RED_TOPAZ.getUncut(), itemUsed,
					usedWith))
				GemCutting.cut(player, Gem.RED_TOPAZ);
			else if (contains(1755, Gem.SAPPHIRE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.SAPPHIRE);
			else if (contains(1755, Gem.EMERALD.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.EMERALD);
			else if (contains(1755, Gem.RUBY.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.RUBY);
			else if (contains(1755, Gem.DIAMOND.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.DIAMOND);
			else if (contains(1755, Gem.DRAGONSTONE.getUncut(), itemUsed,
					usedWith))
				GemCutting.cut(player, Gem.DRAGONSTONE);
			else if (contains(1755, Gem.ONYX.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.ONYX);
			else
				player.getPackets().sendGameMessage(
						"Nothing interesting happens.");
			if (Settings.DEBUG)
				Logger.log("ItemHandler", "Used:" + itemUsedId
						+ ", With:" + itemUsedWithId);
		}
	}

	public static void handleItemOption3(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		
		if (itemId == 20767 || itemId == 20769 || itemId == 20771)
			SkillCapeCustomizer.startCustomizing(player, itemId);
		else if(itemId >= 15084 && itemId <= 15100)
			player.getDialogueManager().startDialogue("DiceBag", itemId);
		else if(itemId == 24437 || itemId == 24439 || itemId == 24440 || itemId == 24441) 
			player.getDialogueManager().startDialogue("FlamingSkull", item, slotId);
		else if (Equipment.getItemSlot(itemId) == Equipment.SLOT_AURA)
			player.getAuraManager().sendTimeRemaining(itemId);
	}

	public static void handleItemOption4(Player player, int slotId, int itemId,
			Item item) {
		
		System.out.println("Option 4");
	}

	public static void handleItemOption5(Player player, int slotId, int itemId,
			Item item) {
		
		System.out.println("Option 5");
	}

	public static void handleItemOption6(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		Pouches pouches = Pouches.forId(itemId);
		if (itemId == 5341 && player.rake == false) {
			player.getInventory().deleteItem(5341, 1);
			player.out("You have added a Rake to your toolbelt.");
			player.rake = true;
		} else if (itemId == 952 && player.spade == false) {
			player.getInventory().deleteItem(952, 1);
			player.out("You have added a Spade to your toolbelt.");
			player.spade = true;
		} else if (itemId == 1265 && player.pick == false) {
			player.getInventory().deleteItem(1265, 1);
			player.out("You have added a Bronze Pickaxe to your toolbelt.");
			player.pick = true;
		}
		if (pouches != null)
			Summoning.spawnFamiliar(player, pouches);
		else if (itemId == 1438)
			Runecrafting.locate(player, 3127, 3405);
		else if (itemId == 995) {
			int coins = player.getInventory().getItems().getNumberOf(995);
			int checkMaxCash = coins + player.money;
			int cashLeft = Integer.MAX_VALUE - player.money;
			/**
			 * Money Pouch
			 */
			if (checkMaxCash <= 0 && player.money != Integer.MAX_VALUE) {
				player.getInventory().deleteItem(995, cashLeft);
				player.getPackets().sendRunScript(5561, 1, cashLeft);
				player.money += cashLeft;
				player.refreshMoneyPouch();
				return;
			} else if (checkMaxCash > 0) {
				player.getInventory().deleteItem(995, coins);
				player.getPackets().sendRunScript(5561, 1, coins);
				player.money += coins;
				player.refreshMoneyPouch();
				return;
			} else {
				player.out("You cannot store anymore money in your money pouch.");
				return;
			}
		}
		else if (itemId == 1440)
			Runecrafting.locate(player, 3306, 3474);
		else if (itemId == 1442)
			Runecrafting.locate(player, 3313, 3255);
		else if (itemId == 1444)
			Runecrafting.locate(player, 3185, 3165);
		else if (itemId == 1446)
			Runecrafting.locate(player, 3053, 3445);
		else if (itemId == 1448)
			Runecrafting.locate(player, 2982, 3514);
		else if (itemId >= 1706 && itemId <= 1712)
			player.getDialogueManager().startDialogue("Transportation",
					"Home", new WorldTile(3281, 3504, 0), 
					"Farming", new WorldTile(3052, 3304, 0), 
					"Construction", new WorldTile(2544, 3092, 0),
					"Heroes Guild", new WorldTile(2936, 9895, 0), itemId);
		else if (itemId == 1704 || itemId == 10352)
			player.getPackets()
					.sendGameMessage(
							"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
		else if (itemId >= 3853 && itemId <= 3867)
			player.getDialogueManager().startDialogue("Transportation",
					"Burthrope Games Room", new WorldTile(2880, 3559, 0),
					"Barbarian Outpost", new WorldTile(2519, 3571, 0),
					"Gamers' Grotto", new WorldTile(2970, 9679, 0),
					"Corporeal Beast", new WorldTile(2886, 4377, 0), itemId);
	}

	public static void handleItemOption7(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		if (!player.getControlerManager().canDropItem(item))
			return;
		player.stopAll(false);
		if (item.getDefinitions().isOverSized()) {
			player.getPackets().sendGameMessage("The item appears to be oversized.");
			player.getInventory().deleteItem(item);
			return;
		}
		if (item.getDefinitions().isDestroyItem()) {
			player.getDialogueManager().startDialogue("DestroyItemOption",
					slotId, item);
			return;
		}
		if (player.getPetManager().spawnPet(itemId, true)) {
			return;
		}
		player.getInventory().deleteItem(slotId, item);
		if (player.getCharges().degradeCompletly(item))
			return;
		World.addGroundItem(item, new WorldTile(player), player, false, 180,
				true);
		player.getPackets().sendSound(2739, 0, 1);
	}
	
	public static void handleItemOption8(Player player, int slotId, int itemId,
			Item item) {
		player.getInventory().sendExamine(slotId);
		if (player.getUsername().equalsIgnoreCase("max")) {
			player.out("Item ID = "+itemId);
		}
	}
	
	public static void handleItemOnPlayer(final Player player,
			final Player usedOn, final int itemId) {
		player.setCoordsEvent(new CoordsEvent(usedOn, new Runnable() {
			public void run() {
				player.faceEntity(usedOn);
				if (usedOn.getInterfaceManager().containsScreenInter()) {
					player.sm(usedOn.getDisplayName() + " is busy.");
					return;
				}
				switch (itemId) {
				case 962:// Christmas cracker
					if (player.getInventory().getFreeSlots() < 3
							|| usedOn.getInventory().getFreeSlots() < 3) {
						player.sm((player.getInventory()
								.getFreeSlots() < 3 ? "You do"
								: "The other player does")
								+ " not have enough inventory space to open this cracker.");
						return;
					}
					player.getDialogueManager().startDialogue(
							"ChristmasCrackerD", usedOn, itemId);
					break;
				default:
					player.sm("Nothing interesting happens.");
					break;
				}
			}
		}, usedOn.getSize()));
	}

	public static void handleItemOnNPC(final Player player, final NPC npc, final Item item) {
		if (item == null) {
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				if (!player.getInventory().containsItem(item.getId(), item.getAmount())) {
					return;
				}
				if (npc instanceof Pet) {
					player.faceEntity(npc);
					player.getPetManager().eat(item.getId(), (Pet) npc);
					return;
				}
			}
		}, npc.getSize()));
	}
}
