package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;
import java.util.ArrayList;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.minigames.ZarosGodwars;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class kill extends Dialogue {
	

	public void Transformation(int toNPCId) {
		this.toNPCId = toNPCId;
	}
	
	private int toNPCId;
	@Override
	public void start() {
			sendOptionsDialogue("What would you like to do?",
					"im going to kill u.", "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			toNPCId = 6774;

		} else if (componentId == OPTION_2) {
			end();
		}
			end();
	}
	
	public int getToNPCId() {
		return toNPCId;
	}
	@Override
	public void finish() {
		

	}

}
