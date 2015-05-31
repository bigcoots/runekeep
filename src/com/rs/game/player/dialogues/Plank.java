package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class Plank extends Dialogue {

	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"Armchairs .", "Tables.", "Armour cases.", "Toyboxes.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 0) {
				player.out("You need at least 1 Construction to build Armchairs .");
				end();
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 1 && player.getInventory().containsItem(960, 3) && player.getInventory().containsItem(4819, 3)) {
				player.out("You build Armchairs .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 166);				
				player.getInventory().deleteItem(960, 3);
				player.getInventory().deleteItem(4819, 3);			
			} else {
				player.out("You need 3planks and 3nails in your inventory to build this.");
				end();
			}
		} else if (componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 50) {
				player.out("You need at least 50 Construction to build Tables .");
				end();
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 50 && player.getInventory().containsItem(8778, 4)) {
						player.out("You build Tables .");
			player.getSkills().addXp(Skills.CONSTRUCTION, 333);						
						player.getInventory().deleteItem(8778, 4);
					} else {
						player.out("You need 4oak planks in your inventory to build this.");
						end();
					}
		} else if (componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 75) {
				player.out("You need at least 75 Construction to build Armour cases.");
				end();
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 75 && player.getInventory().containsItem(8780, 3)) {
				player.out("You build Armour cases.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 500);				
				player.getInventory().deleteItem(8780, 3);
			} else {
				player.out("You need 3teak planks in your inventory to build this.");
				end();
			}
		} else if (componentId == OPTION_4) {
			if (player.getSkills().getLevel(Skills.CONSTRUCTION) < 90) {
				player.out("You need at least 90 Construction to build Toyboxes.");
				end();
			} else if (player.getSkills().getLevel(Skills.CONSTRUCTION) >= 90 && player.getInventory().containsItem(8782, 2)) {
				player.out("You build Toyboxes.");
			player.getSkills().addXp(Skills.CONSTRUCTION, 633);				
				player.getInventory().deleteItem(8782, 2);
			} else {
				player.out("You need 2mahogany plank in your inventory to build this.");
			end();	
		}
			end();
	}
	}
	@Override
	public void finish() {

	}

}
