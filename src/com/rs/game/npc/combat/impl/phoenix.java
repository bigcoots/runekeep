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

public class phoenix extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 8549 };
	}



	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(1) == 0) {
			switch (Utils.getRandom(5)) {
			case 0:
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				npc.setCapDamage(800);
				target.setNextGraphics(new Graphics (453));
							World.sendProjectile(npc, target, 1155, 60, 32, 50, 50, 0, 0);
							delayHit(npc, 1, target, getMagicHit(npc, Utils.random(100)));
				break;
		case 1:
		
						npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				npc.setCapDamage(800);
						target.setNextGraphics(new Graphics (197));			
				
							target.applyHit(new Hit(npc, Utils.random(350) / 2,
									HitLook.MAGIC_DAMAGE));
						
							delayHit(npc, 1, target, getRangeHit(npc, Utils.random(100)));
						
						
			break;


}	
		}
		
				
		if (Utils.getRandom(10) == 0) {
			npc.setCapDamage(500);
	npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			for (Entity t : npc.getPossibleTargets()) {
				if (!target.withinDistance(npc, 18))
					continue;
				int damage = getRandomMaxHit(npc, defs.getMaxHit(),
						NPCCombatDefinitions.MELEE, target) + Utils.random(100);
				if (damage > 0) {
						WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
						for (Entity t : npc.getPossibleTargets()) {

							t.setNextGraphics(new Graphics (1393));
							t.applyHit(new Hit(npc, Utils.random(650) / 2,
									HitLook.REGULAR_DAMAGE));
							t.applyHit(new Hit(npc, Utils.random(350) / 2,
									HitLook.REGULAR_DAMAGE));
							npc.heal(Utils.random(200));
				
					delayHit(npc, 1, t, getRegularHit(npc, Utils.random(100)));
					delayHit(npc, 1, t, getRegularHit(npc, Utils.random(100)));
					}
						}
						}, 7);

		} else {
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				npc.setCapDamage(800);
				target.setNextGraphics(new Graphics (453));
							World.sendProjectile(npc, target, 1155, 60, 32, 50, 50, 0, 0);
							delayHit(npc, 1, target, getMagicHit(npc, Utils.random(100)));
		}
		return defs.getAttackDelay();
			}
		}
		int shit = Utils.random(4);
		return shit;
	}
}
