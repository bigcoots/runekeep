package com.rs.game.npc.combat.impl;

import java.util.List;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
/**
 * 
 * @author Jae
 * 
 * Handles custom boss, Sunfreet.
 *
 */

public class strangearrow extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 213 };
	}



	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(1) == 0) {
			switch (Utils.getRandom(5)) {
			case 0:
							npc.setNextAnimation(new Animation(426));
													
									World.sendProjectile(npc, target, 1066, 41, 16, 31, 35,
							16, 0);
							target.applyHit(new Hit(npc, Utils.random(450) / 2,
									HitLook.RANGE_DAMAGE));
					
				break;
		case 1:
		
							npc.setNextAnimation(new Animation(1074));
													
					World.sendProjectile(npc, target, 1066, 41, 16, 31, 35,
							16, 0);
					World.sendProjectile(npc, target, 1066, 41, 16, 25, 35,
							21, 0);
							target.applyHit(new Hit(npc, Utils.random(450) / 2,
									HitLook.RANGE_DAMAGE));
							target.applyHit(new Hit(npc, Utils.random(450) / 2,
									HitLook.RANGE_DAMAGE));						
			break;


}	
		}
		
				
		if (Utils.getRandom(10) == 0) {
			npc.setCapDamage(500);
										npc.setNextAnimation(new Animation(16959));
							npc.setNextGraphics(new Graphics (3192));
			for (Entity t : npc.getPossibleTargets()) {
				if (!target.withinDistance(npc, 18))
					continue;
				int damage = getRandomMaxHit(npc, defs.getMaxHit(),
						NPCCombatDefinitions.RANGE, target) + Utils.random(100);
				if (damage > 0) {
						WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
						for (Entity t : npc.getPossibleTargets()) {

					

							t.applyHit(new Hit(npc, Utils.random(650) / 2,
									HitLook.REGULAR_DAMAGE));
							t.applyHit(new Hit(npc, Utils.random(350) / 2,
									HitLook.REGULAR_DAMAGE));
							npc.heal(Utils.random(200));
				
					delayHit(npc, 1, t, getRegularHit(npc, Utils.random(100)));
					delayHit(npc, 1, t, getRegularHit(npc, Utils.random(100)));
											t.setNextGraphics(new Graphics (751));
							t.setNextGraphics(new Graphics (3191));
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
