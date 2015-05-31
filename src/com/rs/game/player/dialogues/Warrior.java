package com.rs.game.player.dialogues;

import com.rs.game.player.QuestManager;
import com.rs.game.player.QuestManager.Quests;
import com.rs.game.player.Skills;
import com.rs.game.player.content.PlayerLook;
import com.rs.utils.ShopsHandler;


public class Warrior extends Dialogue {

	int npcId;
	public int starter = 0;
	

	@Override
	public void start() {
		if (player.getSkills().getLevel(Skills.CRAFTING) < 31 && player.getSkills().getLevel(Skills.WOODCUTTING) < 36) {
			player.getDialogueManager().startDialogue("SimpleMessage", "To start this quest, you need a Crafting level of 32 and a Woodcutting level of 37.");
		} else {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hi there, what can I do for you?");
			}
		}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "I want to find Zanaris.", "Nothing.");
			break;
		case 0:
			if(componentId == OPTION_2) {
				stage = 1;
				sendPlayerDialogue(9827, "Nothing.");
			}else {
				stage = 2;
				sendPlayerDialogue(9827, "I want to find Zanaris");
			}
			break;
		case 1:
			stage = -2;
			sendNPCDialogue(npcId, 9827, "Okay.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Hmm Zanaris, I've heard of that place, I don't know much about it though.");
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Come on man, you have to know something.", "Okay, guess I'll leave it for now.");
			break;
		case 4:
			if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Okay, guess I'll leave it for now.");
				end();
			}else {
				stage = 5;
				sendPlayerDialogue(9827, "Come on man, you have to know something.");
			}
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "Well.. I do know a man that has traveled to Zanaris, his names Shamus.");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "I heard he lives near here, in a tree of some sort, take a look around the trees and you might see him.");
			player.spokeToWarrior = true;
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "I hope he will be able to help you.");
			break;
		case 8:
			stage = 9;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Thanks for your help!", "Screw that! It's a lure!", "I'll Leave it for now.");
			break;
		case 9:
			if(componentId == OPTION_3) {
				stage = 1;
				sendPlayerDialogue(9827, "I'll Leave it for now.");
			}else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Screw that! It's a lure!");
				end();
			}else {
				stage = 10;
				sendPlayerDialogue(9827, "Thanks for your help!");
				player.spokeToWarrior = true;
				player.getQuestManager().setQuestStageAndRefresh(Quests.LOST_CITY, 1);
			}
			break;
		case 10:
			if(player.getEquipment().wearingArmour()) {
				stage = -2;
				sendNPCDialogue(npcId, 9827, "Good luck my friend.");
			}else{
				stage = 11;
				sendNPCDialogue(npcId, 9827, "Good luck my friend.");
			}
			break;
		case 11:
			stage = 12;
			sendPlayerDialogue(9827, "Thank you, brave warrior.");
			break;
		case 12:
			//PlayerLook.openThessaliasMakeOver(player);
			end();
			break;
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}