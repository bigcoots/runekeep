package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;

public class PestQueenCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 6358 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(4) == 0) {
			switch (Utils.getRandom(0)) {
			case 0:
				npc.setNextForceTalk(new ForceTalk("BEGONE!"));
				break;
			}
		}
		if (Utils.getRandom(1) == 0) {
			switch (Utils.getRandom(5)) {
			case 0:
							npc.setNextAnimation(new Animation(14801));
													

							target.applyHit(new Hit(npc, Utils.random(450) / 2,
									HitLook.MELEE_DAMAGE));
					
				break;


			case 1:
							npc.setNextAnimation(new Animation(14792));
										World.sendProjectile(npc, target, 2991, 34, 16, 30, 35, 16, 0);					

							target.applyHit(new Hit(npc, Utils.random(650) / 2,
									HitLook.MAGIC_DAMAGE));
							npc.heal(Utils.random(200));
					
					
				break;
}	
		}
		if (Utils.getRandom(10) == 0) {
			npc.setCapDamage(500);
				for (Entity t : npc.getPossibleTargets()) {
					npc.setNextAnimation(new Animation(14804));
					

	
		
				if (!target.withinDistance(npc, 18))
					continue;
				int damage = getRandomMaxHit(npc, defs.getMaxHit(),
						NPCCombatDefinitions.MAGE, target) + Utils.random(100);
				if (damage > 0) {
						WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
						for (Entity t : npc.getPossibleTargets()) {
npc.setNextAnimation(new Animation(14802));
				
							t.applyHit(new Hit(npc, Utils.random(650) / 2,
									HitLook.REGULAR_DAMAGE));
							npc.heal(Utils.random(200));
						t.setNextGraphics(new Graphics (3146));
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