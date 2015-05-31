package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class LavaStrykewyrmCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 3334 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.getRandom(10);
		if (attackStyle <= 7) { // melee
			int size = npc.getSize();
			int distanceX = target.getX() - npc.getX();
			int distanceY = target.getY() - npc.getY();
			if (distanceX > size || distanceX < -1 || distanceY > size
					|| distanceY < -1) {
				// nothing
			} else {
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				delayHit(
						npc,
						0,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MAGE, target)));
				return defs.getAttackDelay();
			}
		}
		if (attackStyle <= 9) { // mage
			npc.setNextAnimation(new Animation(12794));
			final Hit hit = getMagicHit(
					npc,
					getRandomMaxHit(npc, defs.getMaxHit(),
							NPCCombatDefinitions.MAGE, target));
			delayHit(npc, 1, target, hit);
			World.sendProjectile(npc, target, defs.getAttackProjectile(), 41,
					16, 41, 30, 16, 0);
			if (npc.getId() == 3334) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						if (Utils.getRandom(10) == 0
								&& target.getFreezeDelay() < System
										.currentTimeMillis()) {
							target.addFreezeDelay(3000);
							target.setNextGraphics(new Graphics(1463));
							if (target instanceof Player) {
								Player targetPlayer = (Player) target;
								targetPlayer.stopAll();
							}
						} else if (hit.getDamage() != 0)
							target.setNextGraphics(new Graphics(2191));
					}
				}, 1);
			}
		} else if (attackStyle == 11) {
			npc.setNextAnimation(new Animation(12794));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(2191), center);
			npc.setNextForceTalk(new ForceTalk("How are the burns?"));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;

				@Override
				public void run() {
					for (Player player : World.getPlayers()) { // lets just loop
																// all players
																// for massive
																// moves
						if (player == null || player.isDead()
								|| player.hasFinished())
							continue;
						if (player.withinDistance(center, 1)) {
							delayHit(npc, 0, player,
									new Hit(npc, Utils.random(250),
											HitLook.REGULAR_DAMAGE));
						}
					}
					if (count++ == 10) {
						stop();
						return;
					}
				}
			}, 0, 0);
		}
		return defs.getAttackDelay();
	}
}
