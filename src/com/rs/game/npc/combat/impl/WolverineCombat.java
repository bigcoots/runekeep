package com.rs.game.npc.combat.impl;

import java.util.List;
import java.util.Random;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.utils.Utils;
import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
/**
 * 
 * @author Jae
 * 
 * Handles custom boss, Sunfreet.
 *
 */

public class WolverineCombat extends CombatScript {

	private boolean forceFollowClose;
	private int capDamage;




	@Override
	public Object[] getKeys() {
		setForceFollowClose(true);
		setCapDamage(1000);
		return new Object[] { 70 };
		

	}

	public void setCapDamage(int capDamage) {
		this.capDamage = capDamage;
	}

	public void setForceFollowClose(boolean forceFollowClose) {
		this.forceFollowClose = forceFollowClose;
	}



	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(1) == 0) {
			switch (Utils.getRandom(5)) {
			case 0:
				setForceFollowClose(true);
		npc.setNextAnimation(new Animation(393));
				npc.setCapDamage(800);
							target.applyHit(new Hit(npc, Utils.random(650) / 2,
									HitLook.MELEE_DAMAGE));
				break;
		case 1:
		
							npc.setNextAnimation(new Animation(14393));
				npc.setCapDamage(800);
							target.applyHit(new Hit(npc, Utils.random(650) / 2,
									HitLook.MELEE_DAMAGE));
			target.setNextGraphics(new Graphics(80, 5, 60));
	
			
			break;		
}
}				
		if (Utils.getRandom(10) == 0) {
			npc.setCapDamage(500);

			for (Entity t : npc.getPossibleTargets()) {
				if (!target.withinDistance(npc, 1))
					continue;
				int damage = getRandomMaxHit(npc, defs.getMaxHit(),
						NPCCombatDefinitions.MELEE, target) + Utils.random(100);
				if (damage > 0) {
						WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
						for (Entity t : npc.getPossibleTargets()) {

								npc.setNextAnimation(new Animation(10961));

							t.applyHit(new Hit(npc, Utils.random(650) / 2,
									HitLook.REGULAR_DAMAGE));
							t.applyHit(new Hit(npc, Utils.random(350) / 2,
									HitLook.MAGIC_DAMAGE));
							t.applyHit(new Hit(npc, Utils.random(650) / 2,
									HitLook.RANGE_DAMAGE));
							t.applyHit(new Hit(npc, Utils.random(350) / 2,
									HitLook.MELEE_DAMAGE));						
				

					}
						}
						}, 7);

		} else {
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			npc.setCapDamage(800);
			delayHit(
					npc,
					0,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, defs.getMaxHit(),
									NPCCombatDefinitions.MELEE, target)));
		}
		return defs.getAttackDelay();
			}
		}
		int shit = Utils.random(4);
		return shit;
	}
}
