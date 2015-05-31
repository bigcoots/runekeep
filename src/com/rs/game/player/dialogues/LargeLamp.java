package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class LargeLamp extends Dialogue {
	
	int XP = 6500000;
	int LAMP = 23715;
	
	@Override
	public void start() {
		sendOptionsDialogue("What skill would you like to gain experience in?", "Attack", "Strength", "Defence", "Ranged", "More...");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
			case -1:
				if(componentId == OPTION_1) {
					player.getSkills().addXp(Skills.ATTACK, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_2) {
					player.getSkills().addXp(Skills.STRENGTH, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_3) {
					player.getSkills().addXp(Skills.DEFENCE, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_4) {
					player.getSkills().addXp(Skills.RANGE, XP);
					destroyLamp();
			end();
				} else {
					sendOptionsDialogue("What skill would you like to gain experience in?", "Prayer", "Magic", "Constitution", "Summoning", "More...");
					stage = 1;
				}
			break;
			case 1:
				if(componentId == OPTION_1) {
					player.getSkills().addXp(Skills.PRAYER, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_2) {
					player.getSkills().addXp(Skills.MAGIC, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_3) {
					player.getSkills().addXp(Skills.HITPOINTS, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_4) {
					player.getSkills().addXp(Skills.SUMMONING, XP);
					destroyLamp();
			end();
				} else {
					sendOptionsDialogue("What skill would you like to gain experience in?", "Agility", "Herblore", "Thieving", "Crafting", "More...");
					stage = 2;
				}
			break;
			case 2:
				if(componentId == OPTION_1) {
					player.getSkills().addXp(Skills.AGILITY, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_2) {
					player.getSkills().addXp(Skills.HERBLORE, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_3) {
					player.getSkills().addXp(Skills.THIEVING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_4) {
					player.getSkills().addXp(Skills.CRAFTING, XP);
					destroyLamp();
			end();
				} else {
					sendOptionsDialogue("What skill would you like to gain experience in?", "Fletching", "Runecrafting", "Construction", "Slayer", "More...");
					stage = 3;
				}
			break;
			case 3:
				if(componentId == OPTION_1) {
					player.getSkills().addXp(Skills.FLETCHING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_2) {
					player.getSkills().addXp(Skills.RUNECRAFTING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_3) {
					player.getSkills().addXp(Skills.CONSTRUCTION, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_4) {
					player.getSkills().addXp(Skills.SLAYER, XP);
					destroyLamp();
			end();
				} else {
					sendOptionsDialogue("What skill would you like to gain experience in?", "Hunter", "Mining", "Smithing", "Fishing", "More...");
					stage = 4;
				}
			break;
			case 4:
				if(componentId == OPTION_1) {
					player.getSkills().addXp(Skills.HUNTER, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_2) {
					player.getSkills().addXp(Skills.MINING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_3) {
					player.getSkills().addXp(Skills.SMITHING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_4) {
					player.getSkills().addXp(Skills.FISHING, XP);
					destroyLamp();
			end();
				} else {
					sendOptionsDialogue("What skill would you like to gain experience in?", "Cooking", "Firemaking", "Woodcutting", "Farming", "Dungeoneering");
					stage = 5;
				}
			break;
			case 5:
				if(componentId == OPTION_1) {
					player.getSkills().addXp(Skills.COOKING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_2) {
					player.getSkills().addXp(Skills.FIREMAKING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_3) {
					player.getSkills().addXp(Skills.WOODCUTTING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_4) {
					player.getSkills().addXp(Skills.FARMING, XP);
					destroyLamp();
			end();
				} else if(componentId == OPTION_5) {
					player.getSkills().addXp(Skills.DUNGEONEERING, XP);
					destroyLamp();
			end();
				} else {
					sendOptionsDialogue("What skill would you like to gain experience in?", "Attack", "Strength", "Defence", "Ranged", "More...");
					stage = -1;
				}
		}
	}

	public void destroyLamp() {
		player.getInventory().deleteItem(LAMP, 1);		
	}

	@Override
	public void finish() {
		
	}
}