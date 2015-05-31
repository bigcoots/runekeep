package com.rs.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.Launcher;
import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.GodWarsBosses;
import com.rs.game.minigames.ZarosGodwars;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.RequestController;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.npc.NPC;
import com.rs.game.npc.corp.CorporealBeast;
import com.rs.game.npc.dragons.KingBlackDragon;
import com.rs.game.npc.godwars.GodWarMinion;
import com.rs.game.npc.godwars.armadyl.GodwarsArmadylFaction;
import com.rs.game.npc.godwars.armadyl.KreeArra;
import com.rs.game.npc.godwars.bandos.GeneralGraardor;
import com.rs.game.npc.godwars.bandos.GodwarsBandosFaction;
import com.rs.game.npc.godwars.saradomin.CommanderZilyana;
import com.rs.game.npc.godwars.saradomin.GodwarsSaradominFaction;
import com.rs.game.npc.godwars.zammorak.GodwarsZammorakFaction;
import com.rs.game.npc.godwars.zammorak.KrilTstsaroth;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.npc.godwars.zaros.NexMinion;
import com.rs.game.npc.kalph.KalphiteQueen;
import com.rs.game.npc.nomad.FlameVortex;
import com.rs.game.npc.nomad.Nomad;
import com.rs.game.npc.others.Bork;
import com.rs.game.npc.others.Glacor;
import com.rs.game.npc.others.ItemHunterNPC;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.others.Lucien;
import com.rs.game.npc.others.MasterOfFear;
import com.rs.game.npc.others.MercenaryMage;
import com.rs.game.npc.others.PestMonsters;
import com.rs.game.npc.others.Revenant;
import com.rs.game.npc.others.SeaTrollQueen;
import com.rs.game.npc.others.TormentedDemon;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.npc.sorgar.Elemental;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.BoxAction.HunterNPC;
import com.rs.game.player.content.DwarfCannon;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.LivingRockCavern;
import com.rs.game.player.content.ShootingStar;
import com.rs.game.player.content.TriviaBot;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.AntiFlood;
import com.rs.utils.IPBanL;
import com.rs.utils.Logger;
import com.rs.utils.PkRank;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import java.util.ArrayList;
import com.rs.game.npc.others.Pker;

public final class World {

	public static int exiting_delay;
	public static long exiting_start;

	private static final EntityList<Player> players = new EntityList<Player>(
			Settings.PLAYERS_LIMIT);

	private static final EntityList<NPC> npcs = new EntityList<NPC>(
			Settings.NPCS_LIMIT);
	private static final Map<Integer, Region> regions = Collections
			.synchronizedMap(new HashMap<Integer, Region>());
	
	public static List<WorldTile> restrictedTiles = new ArrayList<WorldTile>();
	
	public static void deleteObject(WorldTile tile){
		restrictedTiles.add(tile);
	}

	// private static final Object lock = new Object();
	

	public static final void init() {
		// addLogicPacketsTask();
		addTriviaBotTask();
		//restockShops();
		//crashedStar();
		spawnStar();
		addRestoreRunEnergyTask();
		addDrainPrayerTask();
		addRestoreHitPointsTask();
		addRestoreSkillsTask();
		addRestoreSpecialAttackTask();
		addRestoreShopItemsTask();
		addSummoningEffectTask();
		addOwnedObjectsTask();
		LivingRockCavern.init();
	}

        private static final EntityList<Player> lobbyPlayers = new EntityList<Player>(Settings.PLAYERS_LIMIT);

	/*
	 * private static void addLogicPacketsTask() {
	 * CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
	 * 
	 * @Override public void run() { for(Player player : World.getPlayers()) {
	 * if(!player.hasStarted() || player.hasFinished()) continue;
	 * player.processLogicPackets(); } }
	 * 
	 * }, 300, 300); }
	 */

	private static void addOwnedObjectsTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					OwnedObjectManager.processAll();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
	}

public static final Player getLobbyPlayerByDisplayName(String username) {
		String formatedUsername = Utils.formatPlayerNameForDisplay(username);
		for (Player player : getLobbyPlayers()) {
			if (player == null) {
				continue;
			}
			if (player.getUsername().equalsIgnoreCase(formatedUsername)
					|| player.getDisplayName().equalsIgnoreCase(formatedUsername)) {
				return player;
			}
		}
		return null;
	}

	public static final EntityList<Player> getLobbyPlayers() {
		return lobbyPlayers;
	}
		
	public static final void addPlayer(Player player) {
		players.add(player);
		if (World.containsLobbyPlayer(player.getUsername())) {
			World.removeLobbyPlayer(player);
			AntiFlood.remove(player.getSession().getIP());
		}
		AntiFlood.add(player.getSession().getIP());
	}

	public static final void addLobbyPlayer(Player player) {
		lobbyPlayers.add(player);
		AntiFlood.add(player.getSession().getIP());
	}

	public static final boolean containsLobbyPlayer(String username) {
		for (Player p2 : lobbyPlayers) {
			if (p2 == null) {
				continue;
			}
			if (p2.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	public static void removeLobbyPlayer(Player player) {
		for (Player p : lobbyPlayers) {
			if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
				if (player.getCurrentFriendChat() != null) {
					player.getCurrentFriendChat().leaveChat(player, true);
				}
				lobbyPlayers.remove(p);
			}
		}
		AntiFlood.remove(player.getSession().getIP());
	}

	public static void removePlayer(Player player) {
		for (Player p : players) {
			if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
				players.remove(p);
			}
		}
		AntiFlood.remove(player.getSession().getIP());
	}
	
	private static void addRestoreShopItemsTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					ShopsHandler.restoreShops();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30, TimeUnit.SECONDS);
	}

	public static final void addListUpdateTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead()
								|| !player.isRunning())
							continue;
						player.getPackets().sendIComponentText(751, 16, "Players Online: " + getPlayers().size());
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 10);
	}

	private static final void addSummoningEffectTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.getFamiliar() == null || player.isDead()
								|| !player.hasFinished())
							continue;
						if (player.getFamiliar().getOriginalId() == 6814) {
							player.heal(20);
							player.setNextGraphics(new Graphics(1507));
						}
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 15, TimeUnit.SECONDS);
	}

	private static final void addRestoreSpecialAttackTask() {

		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead()
								|| !player.isRunning())
							continue;
						player.getCombatDefinitions().restoreSpecialAttack();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30000);
	}

	private static boolean checkAgility;

	private static final void addTriviaBotTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					TriviaBot.Run();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 600000);
	}
	
	public static void restockShops() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					ShopsHandler.loadUnpackedShops();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 3600000);
	}
	
	public static int star = 0;
	
	public static void crashedStar() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					star = 0;
					World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Falador!", false);
					World.spawnObject(new WorldObject(38660, 10, 0 , 3028, 3365, 0), true);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1200000);
	}
	public static void spawnStar() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 1200) {
					star = 0;
					ShootingStar.spawnRandomStar();
					}
					loop++;
					}
				}, 0, 1);
	}
	
	public static void removeStarSprite(final Player player) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 50) {
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8091)
							continue;
						n.sendDeath(n);
					}
				}
					loop++;
					}
				}, 0, 1);
	}
	
	public static void randomEventTeleport(final Player player) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			Random rand = new Random();
			@Override
			public void run() {
				if (loop == 5) {
					for (Player p : World.getPlayers()) {
						if (p == null)
							continue;
				 		Magic.sendNormalTeleportSpell(p, 0, 0, new WorldTile(1972, 5046, 0));
						player.setNextForceTalk(new ForceTalk("Oh no!"));
						player.randomRune();
						player.tagged5Posts = 0;
						player.out("Tag the "+player.randomRune+" rune pinball post.");
					}
				}
					loop++;
					}
				}, 0, 1);
	}

	private static final void addRestoreRunEnergyTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null
								|| player.isDead()
								|| !player.isRunning()
								|| (checkAgility && player.getSkills()
										.getLevel(Skills.AGILITY) < 70))
							continue;
						player.restoreRunEnergy();
					}
					checkAgility = !checkAgility;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1000);
	}

	private static final void addDrainPrayerTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead()
								|| !player.isRunning())
							continue;
						player.getPrayer().processPrayerDrain();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 600);
	}

	private static final void addRestoreHitPointsTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead()
								|| !player.isRunning())
							continue;
						player.restoreHitPoints();
					}
					for (NPC npc : npcs) {
						if (npc == null || npc.isDead() || npc.hasFinished())
							continue;
						npc.restoreHitPoints();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 6000);
	}

	private static final void addRestoreSkillsTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || !player.isRunning())
							continue;
						int ammountTimes = player.getPrayer().usingPrayer(0, 8) ? 2
								: 1;
						if (player.isResting())
							ammountTimes += 1;
						boolean berserker = player.getPrayer()
								.usingPrayer(1, 5);
						for (int skill = 0; skill < 25; skill++) {
							if (skill == Skills.SUMMONING)
								continue;
							for (int time = 0; time < ammountTimes; time++) {
								int currentLevel = player.getSkills().getLevel(
										skill);
								int normalLevel = player.getSkills()
										.getLevelForXp(skill);
								if (currentLevel > normalLevel) {
									if (skill == Skills.ATTACK
											|| skill == Skills.STRENGTH
											|| skill == Skills.DEFENCE
											|| skill == Skills.RANGE
											|| skill == Skills.MAGIC) {
										if (berserker
												&& Utils.getRandom(100) <= 15)
											continue;
									}
									player.getSkills().set(skill,
											currentLevel - 1);
								} else if (currentLevel < normalLevel)
									player.getSkills().set(skill,
											currentLevel + 1);
								else
									break;
							}
						}
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30000);

	}

	public static final Map<Integer, Region> getRegions() {
		// synchronized (lock) {
		return regions;
		// }
	}

	public static final Region getRegion(int id) {
		return getRegion(id, false);
	}

	public static final Region getRegion(int id, boolean load) {
		// synchronized (lock) {
		Region region = regions.get(id);
		if (region == null) {
			region = new Region(id);
			regions.put(id, region);
		}
		if(load)
			region.checkLoadMap();
		return region;
		// }
	}

	public static final void addNPC(NPC npc) {
		npcs.add(npc);
	}

	public static final void removeNPC(NPC npc) {
		npcs.remove(npc);
	}

	public static final NPC spawnNPC(int id, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea,
			boolean spawned) {
		NPC n = null;
		HunterNPC hunterNPCs = HunterNPC.forId(id);
		if (hunterNPCs != null) {
			if (id == hunterNPCs.getNpcId())
				n = new ItemHunterNPC(id, tile, mapAreaNameHash,
						canBeAttackFromOutOfArea, spawned);
		} else if (id == 6142 || id == 6144 || id == 6145 || id == 6143)
			n = new PestMonsters(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id >= 5533 && id <= 5558)
			n = new Elemental(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 7134)
			n = new Bork(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 9441)
			n = new FlameVortex(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id >= 8832 && id <= 8834)
			n = new LivingRock(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 13465 && id <= 13481)
			n = new Revenant(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 1158 || id == 1160)
			n = new KalphiteQueen(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id >= 8528 && id <= 8532)
			n = new Nomad(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6215 || id == 6211 || id == 3406 || id == 6216|| id == 6214 || id == 6215|| id == 6212 || id == 6219 || id == 6221 || id == 6218)
			n = new GodwarsZammorakFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6254  && id == 6259)
			n = new GodwarsSaradominFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6246 || id == 6236 || id == 6232 || id == 6240 || id == 6241 || id == 6242 || id == 6235 || id == 6234 || id == 6243 || id == 6236 || id == 6244 || id == 6237 || id == 6246 || id == 6238 || id == 6239 || id == 6230)
			n = new GodwarsArmadylFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6281 || id == 6282 || id == 6275 || id == 6279|| id == 9184 || id == 6268 || id == 6270 || id == 6274 || id == 6277 || id == 6276 || id == 6278)
			n = new GodwarsBandosFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6261 || id == 6263 || id == 6265)
			n = GodWarsBosses.graardorMinions[(id - 6261) / 2] = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6260)
			n = new GeneralGraardor(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6222)
			n = new KreeArra(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6223 || id == 6225 || id == 6227)
			n = GodWarsBosses.armadylMinions[(id - 6223) / 2] = new GodWarMinion(
					id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 6203)
			n = new KrilTstsaroth(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 6204 || id == 6206 || id == 6208)
			n = GodWarsBosses.zamorakMinions[(id - 6204) / 2] = new GodWarMinion(
					id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 50 || id == 2642)
			n = new KingBlackDragon(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id >= 9462 && id <= 9467)
			n = new Strykewyrm(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea);
		else if (id == 6248 || id == 6250 || id == 6252)
			n = GodWarsBosses.commanderMinions[(id - 6248) / 2] = new GodWarMinion(
					id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 6247)
			n = new CommanderZilyana(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 8133)
			n = new CorporealBeast(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 15174)
			n = new Pker(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 3847)
			n = new SeaTrollQueen(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13447)
			n = ZarosGodwars.nex = new Nex(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13451)
			n = ZarosGodwars.fumus = new NexMinion(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13452)
			n = ZarosGodwars.umbra = new NexMinion(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13453)
			n = ZarosGodwars.cruor = new NexMinion(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13454)
			n = ZarosGodwars.glacies = new NexMinion(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 14256)
			n = new Lucien(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 8335)
			n = new MercenaryMage(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 8349 || id == 8450 || id == 8451)
			n = new TormentedDemon(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 14301)
		    n = new Glacor(id, tile, mapAreaNameHash,
		            canBeAttackFromOutOfArea, spawned);
		else if (id == 15149)
			n = new MasterOfFear(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else 
			n = new NPC(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		return n;
	}

	public static final NPC spawnNPC(int id, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		return spawnNPC(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, false);
	}

	/*
	 * check if the entity region changed because moved or teled then we update
	 * it
	 */
	public static final void updateEntityRegion(Entity entity) {
		if (entity.hasFinished()) {
			if (entity instanceof Player)
				getRegion(entity.getLastRegionId()).removePlayerIndex(entity.getIndex());
			else 
				getRegion(entity.getLastRegionId()).removeNPCIndex(entity.getIndex());
			return;
		}
		int regionId = entity.getRegionId();
		if (entity.getLastRegionId() != regionId) { // map region entity at
			// changed
			if (entity instanceof Player) {
				if (entity.getLastRegionId() > 0)
					getRegion(entity.getLastRegionId()).removePlayerIndex(
							entity.getIndex());
				Region region = getRegion(regionId);
				region.addPlayerIndex(entity.getIndex());
				Player player = (Player) entity;
				int musicId = region.getMusicId();
				if (musicId != -1)
					player.getMusicsManager().checkMusic(musicId);
				player.getControlerManager().moved();
				if(player.hasStarted())
					checkControlersAtMove(player);
			} else {
				if (entity.getLastRegionId() > 0)
					getRegion(entity.getLastRegionId()).removeNPCIndex(
							entity.getIndex());
				getRegion(regionId).addNPCIndex(entity.getIndex());
			}
			entity.checkMultiArea();
			entity.setLastRegionId(regionId);
		} else {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				player.getControlerManager().moved();
				if(player.hasStarted())
					checkControlersAtMove(player);
			}
			entity.checkMultiArea();
		}
	}
	
	public static boolean isAtHome(WorldTile tile) {
		return (tile.getX() >= 3655 && tile.getX() <= 3747 && tile.getY() >= 2940 && tile.getY() <= 3070);
	}
	public static boolean isAtthieve(WorldTile tile) {
		return (tile.getX() >= 2652 && tile.getX() <= 2668 && tile.getY() >= 3295 && tile.getY() <= 3318);
	}	
	public static boolean isAtwc(WorldTile tile) {
		return (tile.getX() >= 2684 && tile.getX() <= 2740 && tile.getY() >= 3391 && tile.getY() <= 3482);
	}	
	public static boolean isAtzana(WorldTile tile) {
		return (tile.getX() >= 2374 && tile.getX() <= 2493 && tile.getY() >= 4372 && tile.getY() <= 4512);
	}		
	public static boolean isAtlost(WorldTile tile) {
		return (tile.getX() >= 3127 && tile.getX() <= 3157 && tile.getY() >= 3203 && tile.getY() <= 3221)
	|| (tile.getX() >= 3032 && tile.getX() <= 3054 && tile.getY() >= 3230 && tile.getY() <= 3248)	
	|| (tile.getX() >= 2807 && tile.getX() <= 2867 && tile.getY() >= 3330 && tile.getY() <= 3389)
	|| (tile.getX() >= 2818 && tile.getX() <= 2844 && tile.getY() >= 9741 && tile.getY() <= 9800)
	|| (tile.getX() >= 3196 && tile.getX() <= 3207 && tile.getY() >= 3165 && tile.getY() <= 3177)	
		;
	}		
	public static boolean isAtdung(WorldTile tile) {
		return (tile.getX() >= 3408 && tile.getX() <= 3483 && tile.getY() >= 3687 && tile.getY() <= 3766)
		|| (tile.getX() >= 2306 && tile.getX() <= 2369 && tile.getY() >= 5487 && tile.getY() <= 5504)
		|| (tile.getX() >= 974 && tile.getX() <= 997 && tile.getY() >= 1101 && tile.getY() <= 1138)		
		;
	}	
	public static boolean isAtrc(WorldTile tile) {
		return (tile.getX() >= 3027 && tile.getX() <= 3053 && tile.getY() >= 4821 && tile.getY() <= 4845)
		|| (tile.getX() >= 2393 && tile.getX() <= 2409 && tile.getY() >= 4837 && tile.getY() <= 4846)
		 || (tile.getX() >= 2114 && tile.getX() <= 2170 && tile.getY() >= 4809 && tile.getY() <= 4863)
		 || (tile.getX() >= 2575 && tile.getX() <= 2596 && tile.getY() >= 4829 && tile.getY() <= 4844)
		  ||(tile.getX() >= 2452 && tile.getX() <= 2473 && tile.getY() >= 4886 && tile.getY() <= 4905)
		  ||(tile.getX() >= 2641 && tile.getX() <= 2677 && tile.getY() >= 4823 && tile.getY() <= 4846)	
		  ||(tile.getX() >= 2507 && tile.getX() <= 2535 && tile.getY() >= 4827 && tile.getY() <= 4856)
		  ||(tile.getX() >= 2761 && tile.getX() <= 2804 && tile.getY() >= 4824 && tile.getY() <= 4851)
		  ||(tile.getX() >= 2840 && tile.getX() <= 2848 && tile.getY() >= 4828 && tile.getY() <= 4840)	
		  ||(tile.getX() >= 2186 && tile.getX() <= 2236 && tile.getY() >= 4821 && tile.getY() <= 4857)
		  ||(tile.getX() >= 2446 && tile.getX() <= 2479 && tile.getY() >= 4817 && tile.getY() <= 4851)	
		  ||(tile.getX() >= 2249 && tile.getX() <= 2289 && tile.getY() >= 4828 && tile.getY() <= 4860)			  
;		  
	}		
	public static boolean isAtagility(WorldTile tile) {
		return (tile.getX() >= 2467 && tile.getX() <= 2491 && tile.getY() >= 3411 && tile.getY() <= 3440 && tile.getPlane() == 0)
		|| (tile.getX() >= 2467 && tile.getX() <= 2491 && tile.getY() >= 3411 && tile.getY() <= 3440 && tile.getPlane() == 1)
		|| (tile.getX() >= 2467 && tile.getX() <= 2491 && tile.getY() >= 3411 && tile.getY() <= 3440 && tile.getPlane() == 2)
		||  (tile.getX() >= 2467 && tile.getX() <= 2491 && tile.getY() >= 3411 && tile.getY() <= 3440 && tile.getPlane() == 3)
		||  (tile.getX() >= 2527 && tile.getX() <= 2553 && tile.getY() >= 3539 && tile.getY() <= 3561 && tile.getPlane() == 0)
		||  (tile.getX() >= 2527 && tile.getX() <= 2553 && tile.getY() >= 3539 && tile.getY() <= 3561 && tile.getPlane() == 1)
		||  (tile.getX() >= 2527 && tile.getX() <= 2553 && tile.getY() >= 3539 && tile.getY() <= 3561 && tile.getPlane() == 2)
		||  (tile.getX() >= 2527 && tile.getX() <= 2553 && tile.getY() >= 3539 && tile.getY() <= 3561 && tile.getPlane() == 3);			
	}
	public static boolean isAtmith(WorldTile tile) {
		return (tile.getX() >= 1749 && tile.getX() <= 1786 && tile.getY() >= 5322 && tile.getY() <= 5362 && tile.getPlane() == 1)
;			
	}	
	public static boolean isAthunter(WorldTile tile) {
		return (tile.getX() >= 2433 && tile.getX() <= 2612 && tile.getY() >= 2824 && tile.getY() <= 2951);
	}	
	public static boolean isAtslayer(WorldTile tile) {
		return (tile.getX() >= 3407 && tile.getX() <= 3453 && tile.getY() >= 3528 && tile.getY() <= 3579);
	}	
	public static boolean isAtconstruct(WorldTile tile) {
		return (tile.getX() >= 2533 && tile.getX() <= 2548 && tile.getY() >= 3081 && tile.getY() <= 3110);
	}	
	public static boolean isAtsmithing(WorldTile tile) {
		return (tile.getX() >= 2704 && tile.getX() <= 2715 && tile.getY() >= 3486 && tile.getY() <= 3500);
	}	
	public static boolean isAtmining(WorldTile tile) {
		return (tile.getX() >= 3293 && tile.getX() <= 3306 && tile.getY() >= 3284 && tile.getY() <= 3316);
	}	
	public static boolean isAtsumm(WorldTile tile) {
		return (tile.getX() >= 2924 && tile.getX() <= 2935 && tile.getY() >= 3444 && tile.getY() <= 3453);
	}		
	public static boolean isAtdonar(WorldTile tile) {
		return (tile.getX() >= 2576 && tile.getX() <= 2595 && tile.getY() >= 3903 && tile.getY() <= 3922);
	}	
	public static boolean isAtfletch(WorldTile tile) {
		return (tile.getX() >= 2717 && tile.getX() <= 2732 && tile.getY() >= 3484 && tile.getY() <= 3500);
	}
	public static boolean isAttzhaar(WorldTile tile) {
		return (tile.getX() >= 4511 && tile.getX() <= 4812 && tile.getY() >= 5041 && tile.getY() <= 5195);
	}	
	public static boolean isAtcook(WorldTile tile) {
		return (tile.getX() >= 2814 && tile.getX() <= 2819 && tile.getY() >= 3438 && tile.getY() <= 3445);
	}		
	public static boolean isAtYaks(WorldTile tile) {
		return (tile.getX() >= 2305 && tile.getX() <= 2348 && tile.getY() >= 3781 && tile.getY() <= 3823);
	}
	public static boolean isAtfish(WorldTile tile) {
		return (tile.getX() >= 3082 && tile.getX() <= 3101 && tile.getY() >= 3218 && tile.getY() <= 3247)
		|| (tile.getX() >= 2823 && tile.getX() <= 2851 && tile.getY() >= 3428 && tile.getY() <= 3451);		
	}	
	public static boolean isAtFallyFarm(WorldTile tile) {
		return (tile.getX() >= 3042 && tile.getX() <= 3063 && tile.getY() >= 3300 && tile.getY() <= 3317)
		||  (tile.getX() >= 2995 && tile.getX() <= 3010 && tile.getY() >= 3371 && tile.getY() <= 3380)
		||  (tile.getX() >= 2650 && tile.getX() <= 2680 && tile.getY() >= 3357 && tile.getY() <= 3384)	
		||  (tile.getX() >= 2799 && tile.getX() <= 2815 && tile.getY() >= 3453 && tile.getY() <= 3474);			
	}
	public static boolean isAtClan(WorldTile tile) {
		return (tile.getX() >= 2981 && tile.getX() <= 3005 && tile.getY() >= 9666 && tile.getY() <= 9693);
	}
	public static boolean isAtnex(WorldTile tile) {
		return (tile.getX() >= 2900 && tile.getX() <= 2943 && tile.getY() >= 5186 && tile.getY() <= 5225);
	}	
	public static boolean isAtcharm(WorldTile tile) {
		return (tile.getX() >= 3134 && tile.getX() <= 3172 && tile.getY() >= 5446 && tile.getY() <= 5449);
	}
	public static boolean isAtcastle(WorldTile tile) {
		return (tile.getX() >= 2363 && tile.getX() <= 2448 && tile.getY() >= 3069 && tile.getY() <= 3164);
	}
	public static boolean isAtduel(WorldTile tile) {
		return (tile.getX() >= 3315 && tile.getX() <= 3400 && tile.getY() >= 3200 && tile.getY() <= 3290);
	}	
	public static boolean isAthero(WorldTile tile) {
		return (tile.getX() >= 2884 && tile.getX() <= 2948 && tile.getY() >= 9888 && tile.getY() <= 9917);
	}	
	public static boolean isAtess(WorldTile tile) {
		return (tile.getX() >= 2883 && tile.getX() <= 2941 && tile.getY() >= 4804 && tile.getY() <= 4861);
	}
	public static boolean isAtedge(WorldTile tile) {
		return (tile.getX() >= 3074 && tile.getX() <= 3121 && tile.getY() >= 3463 && tile.getY() <= 3521);
	}
	public static boolean isAtape(WorldTile tile) {
		return (tile.getX() >= 2687 && tile.getX() <= 2817 && tile.getY() >= 2689 && tile.getY() <= 2815);
	}
	public static boolean isAtcamp(WorldTile tile) {
		return (tile.getX() >= 3150 && tile.getX() <= 3193 && tile.getY() >= 2960 && tile.getY() <= 3009);
	}
	public static boolean isAtroach(WorldTile tile) {
		return (tile.getX() >= 3128 && tile.getX() <= 3178 && tile.getY() >= 4253 && tile.getY() <= 4289);
	}	
	public static boolean isAtspider(WorldTile tile) {
		return (tile.getX() >= 2114 && tile.getX() <= 2135 && tile.getY() >= 5264 && tile.getY() <= 5280);
	}
	public static boolean isAtzombie(WorldTile tile) {
		return (tile.getX() >= 3216 && tile.getX() <= 3244 && tile.getY() >= 9975 && tile.getY() <= 10019);
	}
	public static boolean isAtardy(WorldTile tile) {
		return (tile.getX() >= 2437 && tile.getX() <= 2745 && tile.getY() >= 3232 && tile.getY() <= 3384)
			||  (tile.getX() >= 2601 && tile.getX() <= 2648 && tile.getY() >= 3340 && tile.getY() <= 3384)
		;
	}
	public static boolean isAtseers(WorldTile tile) {
		return (tile.getX() >= 2668 && tile.getX() <= 2787 && tile.getY() >= 3453 && tile.getY() <= 3516);
	}		
	public static boolean isAtjadinko(WorldTile tile) {
		return (tile.getX() >= 3005 && tile.getX() <= 3063 && tile.getY() >= 9216 && tile.getY() <= 9291);
	}
	public static boolean isAtyanille(WorldTile tile) {
		return (tile.getX() >= 2500 && tile.getX() <= 2641 && tile.getY() >= 3066 && tile.getY() <= 3141)
	||  (tile.getX() >= 2497 && tile.getX() <= 2533 && tile.getY() >= 3070 && tile.getY() <= 3131)		
		;
	}	
	public static boolean isAtdraynor(WorldTile tile) {
		return (tile.getX() >= 3068 && tile.getX() <= 3126 && tile.getY() >= 3239 && tile.getY() <= 3389)
			||  (tile.getX() >= 3069 && tile.getX() <= 3150 && tile.getY() >= 3142 && tile.getY() <= 3385)	
		;
	}
	public static boolean isAtlum(WorldTile tile) {
		return (tile.getX() >= 3145 && tile.getX() <= 3270 && tile.getY() >= 3201 && tile.getY() <= 3328);
	}
	public static boolean isAtbrim(WorldTile tile) {
		return (tile.getX() >= 2701 && tile.getX() <= 2913 && tile.getY() >= 3134 && tile.getY() <= 3232)
			||  (tile.getX() >= 2862 && tile.getX() <= 2964 && tile.getY() >= 3136 && tile.getY() <= 3184)	
		;
	}
	public static boolean isAtkaramja(WorldTile tile) {
		return (tile.getX() >= 2711 && tile.getX() <= 2975 && tile.getY() >= 2876 && tile.getY() <= 3133);
	}	
	public static boolean isAtcath(WorldTile tile) {
		return (tile.getX() >= 2788 && tile.getX() <= 2875 && tile.getY() >= 3428 && tile.getY() <= 3526);
	}
	public static boolean isAtrel(WorldTile tile) {
		return (tile.getX() >= 2600 && tile.getX() <= 2726 && tile.getY() >= 3589 && tile.getY() <= 3734);
	}	
	public static boolean isAtburth(WorldTile tile) {
		return (tile.getX() >= 2837 && tile.getX() <= 2938 && tile.getY() >= 3525 && tile.getY() <= 3578);
	}
	public static boolean isAttav(WorldTile tile) {
		return (tile.getX() >= 2872 && tile.getX() <= 2938 && tile.getY() >= 3407 && tile.getY() <= 3519);
	}	
	public static boolean isAtfala(WorldTile tile) {
		return (tile.getX() >= 2936 && tile.getX() <= 3070 && tile.getY() >= 3311 && tile.getY() <= 3415);
	}
	public static boolean isAtbarb(WorldTile tile) {
		return (tile.getX() >= 3071 && tile.getX() <= 3100 && tile.getY() >= 3388 && tile.getY() <= 3462);
	}	
	public static boolean isAtport(WorldTile tile) {
		return (tile.getX() >= 2990 && tile.getX() <= 3068 && tile.getY() >= 3131 && tile.getY() <= 3282);
	}	
	public static boolean isAtvarr(WorldTile tile) {
		return (tile.getX() >= 3150 && tile.getX() <= 3326 && tile.getY() >= 3322 && tile.getY() <= 3520);
	}
	public static boolean isAtkharid(WorldTile tile) {
		return (tile.getX() >= 3262 && tile.getX() <= 3314 && tile.getY() >= 3127 && tile.getY() <= 3280);
	}
	public static boolean isAtwildy(WorldTile tile) {
		return (tile.getX() >= 2949 && tile.getX() <= 3400 && tile.getY() >= 3525 && tile.getY() <= 3976);
	}	
	public static boolean isAtlunar(WorldTile tile) {
		return (tile.getX() >= 2058 && tile.getX() <= 2177 && tile.getY() >= 3833 && tile.getY() <= 3964);
	}
	public static boolean isAtdesert(WorldTile tile) {
		return (tile.getX() >= 3140 && tile.getX() <= 3497 && tile.getY() >= 2682 && tile.getY() <= 3174);
	}	
	public static boolean isAtswamp(WorldTile tile) {
		 return (tile.getX() >= 3420 && tile.getX() <= 3719 && tile.getY() >= 3193 && tile.getY() <= 3618);
	}
	public static boolean isAtrimming(WorldTile tile) {
		return (tile.getX() >= 2919 && tile.getX() <= 2985 && tile.getY() >= 3188 && tile.getY() <= 3240);
	}
	public static boolean isAtpisc(WorldTile tile) {
		return (tile.getX() >= 2254 && tile.getX() <= 2387 && tile.getY() >= 3464 && tile.getY() <= 3709);
	}
	public static boolean isAtelf(WorldTile tile) {
		return (tile.getX() >= 2120 && tile.getX() <= 2350 && tile.getY() >= 3048 && tile.getY() <= 3463);
	}	
	public static boolean isAtgnome(WorldTile tile) {
		return (tile.getX() >= 2363 && tile.getX() <= 2496 && tile.getY() >= 3391 && tile.getY() <= 3562);
	}
	public static boolean isAtgodwars(WorldTile tile) {
		return (tile.getX() >= 2816 && tile.getX() <= 2939 && tile.getY() >= 5249 && tile.getY() <= 5368);
	}
	public static boolean isAtbandos(WorldTile tile) {
		return (tile.getX() >= 2863 && tile.getX() <= 2878 && tile.getY() >= 5352 && tile.getY() <= 5373);
	}
	public static boolean isAtpest(WorldTile tile) {
		return (tile.getX() >= 4493 && tile.getX() <= 4531 && tile.getY() >= 5508 && tile.getY() <= 5625);
	}		
	public static boolean isAtzam(WorldTile tile) {
		return (tile.getX() >= 2916 && tile.getX() <= 2939 && tile.getY() >= 5315 && tile.getY() <= 5334);
	}	
	public static boolean isAtarma(WorldTile tile) {
		return (tile.getX() >= 2818 && tile.getX() <= 2841 && tile.getY() >= 5294 && tile.getY() <= 5312);
	}
	public static boolean isAtsara(WorldTile tile) {
		return (tile.getX() >= 2914 && tile.getX() <= 2933 && tile.getY() >= 5241 && tile.getY() <= 5257);
	}	
	public static boolean isAttd(WorldTile tile) {
		return (tile.getX() >= 2198 && tile.getX() <= 2622 && tile.getY() >= 5713 && tile.getY() <= 5803);
	}	
	public static boolean isAtrevs(WorldTile tile) {
		return (tile.getX() >= 3015 && tile.getX() <= 3125 && tile.getY() >= 10058 && tile.getY() <= 10175);
	}
	public static boolean isAtgano(WorldTile tile) {
		return (tile.getX() >= 4614 && tile.getX() <= 4665 && tile.getY() >= 5382 && tile.getY() <= 5500);
	}
	public static boolean isAtglacor(WorldTile tile) {
		return (tile.getX() >= 4163 && tile.getX() <= 4222 && tile.getY() >= 5697 && tile.getY() <= 5726);
	}	
	public static boolean isAtbarrows(WorldTile tile) {
		return (tile.getX() >= 3545 && tile.getX() <= 3584 && tile.getY() >= 3271 && tile.getY() <= 3311);
	}
	public static boolean isAtcorp(WorldTile tile) {
		return (tile.getX() >= 3001 && tile.getX() <= 2880 && tile.getY() >= 4360 && tile.getY() <= 4411 && tile.getPlane() == 2);
	}	
	public static boolean isAtsunfreet(WorldTile tile) {
		return (tile.getX() >= 3662 && tile.getX() <= 3696 && tile.getY() >= 6024 && tile.getY() <= 6071 && tile.getPlane() == 2);
	}		
	public static boolean isAtdaggs(WorldTile tile) {
		return (tile.getX() >= 2893 && tile.getX() <= 2931 && tile.getY() >= 4433 && tile.getY() <= 4467);
	}
		public static boolean isAtsea(WorldTile tile) {
		return (tile.getX() >= 2981 && tile.getX() <= 3005 && tile.getY() >= 3103 && tile.getY() <= 3131);
	}
		public static boolean isAtkq(WorldTile tile) {
		return (tile.getX() >= 3464 && tile.getX() <= 3501 && tile.getY() >= 9477 && tile.getY() <= 9521);
	}	
		public static boolean isAtkbd(WorldTile tile) {
		return (tile.getX() >= 2250 && tile.getX() <= 2292 && tile.getY() >= 4678 && tile.getY() <= 4716);
	}		
	public static boolean isAtdwarf(WorldTile tile) {
		return (tile.getX() >= 2547 && tile.getX() <= 2598 && tile.getY() >= 3442 && tile.getY() <= 3509)
	||  (tile.getX() >= 2995 && tile.getX() <= 3029 && tile.getY() >= 3445 && tile.getY() <= 3461)			
		;
	}
	public static boolean isAtrunespan(WorldTile tile) {
		return (tile.getX() >= 3986 && tile.getX() <= 4006 && tile.getY() >= 6092 && tile.getY() <= 6121)
	||  (tile.getX() >= 4123 && tile.getX() <= 4148 && tile.getY() >= 6066 && tile.getY() <= 6095)
	||  (tile.getX() >= 4312 && tile.getX() <= 4353 && tile.getY() >= 6039 && tile.getY() <= 6074)		
		;
	}		

	private static void checkControlersAtMove(Player player) {
		if (!(player.getControlerManager().getControler() instanceof RequestController) && RequestController.inWarRequest(player))
			player.getControlerManager().startControler("clan_wars_request");
		else if (DuelControler.isAtDuelArena(player)) 
			player.getControlerManager().startControler("DuelControler");
		else if (FfaZone.inArea(player)) 
			player.getControlerManager().startControler("clan_wars_ffa");
		else if (player.hasLocation == true) {
			if (isAtHome(player)) {
				player.location = "Home";
			} else if (isAtnex(player)) {
				player.location = "Nex";
			} else if (isAtsunfreet(player)) {
				player.location = "Sunfreet";
			} else if (isAtpest(player)) {
				player.location = "Pest Queen";				
			} else if (isAtkbd(player)) {
				player.location = "King black dragon";				
			} else if (isAtkq(player)) {
				player.location = "Kalphite queen";				
			} else if (isAtsea(player)) {
				player.location = "Sea troll queen";				
			} else if (isAtdaggs(player)) {
				player.location = "Dagannoth kings";				
			} else if (isAtcorp(player)) {
				player.location = "Corporeal beast";				
			} else if (isAtmith(player)) {
				player.location = "Mithril dragons";				
			} else if (isAtbarrows(player)) {
				player.location = "Barrows";				
			} else if (isAtglacor(player)) {
				player.location = "Glacors";				
			} else if (isAtgano(player)) {
				player.location = "Ganodermic beast";				
			} else if (isAtrevs(player)) {
				player.location = "Forinthry dungeon";				
			} else if (isAttd(player)) {
				player.location = "Tormented demons";				
			} else if (isAtzam(player)) {
				player.location = "Zamorak";
			} else if (isAtsara(player)) {
				player.location = "Saradomin";				
			} else if (isAtarma(player)) {
				player.location = "Armadyl";
			} else if (isAtbandos(player)) {
				player.location = "Bandos";					
			} else if (isAtgodwars(player)) {
				player.location = "Godwars";							
			} else if (DwarfCannon.isAtRockCrabs(player)) {
				player.location = "Rock Crabs";
			} else if (isAtdonar(player)) {
				player.location = "Donatorzone";
			} else if (isAtelf(player)) {
				player.location = "Tirannwn";				
			} else if (isAtpisc(player)) {
				player.location = "Piscatoris";				
			} else if (isAtbrim(player)) {
				player.location = "Brimhaven";
			} else if (isAtgnome(player)) {
				player.location = "Tree gnome stronghold";				
			} else if (isAtrel(player)) {
				player.location = "Rellekka";				
			} else if (isAtkaramja(player)) {
				player.location = "Karamja";					
			} else if (isAtYaks(player)) {
				player.location = "Yaks";
			} else if (isAtlunar(player)) {
				player.location = "Lunar isle";				
			} else if  (isAtClan(player)) {
				player.location = "Clan Wars Lobby";
			} else if  (isAtmining(player)) {
				player.location = "Mining place";
			} else if  (isAtbarb(player)) {
				player.location = "Barbarian village";	
			} else if  (isAtdesert(player)) {
				player.location = "Desert";	
			} else if  (isAtswamp(player)) {
				player.location = "Swamp";
			} else if  (isAtrimming(player)) {
				player.location = "Rimmington";					
			} else if  (isAtsmithing(player)) {
				player.location = "Smithing place";	
			} else if  (isAtconstruct(player)) {
				player.location = "Construct place";
			} else if  (isAtslayer(player)) {
				player.location = "Slayer place";
			} else if  (isAthunter(player)) {
				player.location = "Hunter place";	
			} else if  (isAtagility(player)) {
				player.location = "Agility place";	
			} else if  (isAtkharid(player)) {
				player.location = "Al-kharid";	
			} else if  (isAtwildy(player)) {
				player.location = "Wildy";					
			} else if  (isAtwc(player)) {
				player.location = "Woodcutting place";	
			} else if  (isAtthieve(player)) {
				player.location = "Thieving place";	
			} else if  (isAtFallyFarm(player)) {
				player.location = "Farming place";	
			} else if  (isAtlum(player)) {
				player.location = "Lumbridge";	
			} else if  (isAtvarr(player)) {
				player.location = "Varrock";				
			} else if  (isAtfish(player)) {
				player.location = "Fishing place";	
			} else if  (isAtrc(player)) {
				player.location = "Runecrafting place";	
			} else if  (isAtdung(player)) {
				player.location = "Dungeoneering place";	
			} else if  (isAtsumm(player)) {
				player.location = "Summoning place";
			} else if  (isAtcook(player)) {
				player.location = "Cooking place";	
			} else if  (isAttav(player)) {
				player.location = "Taverly";	
			} else if  (isAtburth(player)) {
				player.location = "Burthrope";
			} else if  (isAtport(player)) {
				player.location = "Port sarim";	
			} else if  (isAtdraynor(player)) {
				player.location = "Draynor village";					
			} else if  (isAtfala(player)) {
				player.location = "Falador";				
			} else if  (isAtzana(player)) {
				player.location = "Zanaris";
			} else if  (isAttzhaar(player)) {
				player.location = "Tzhaar city";
			} else if  (isAtroach(player)) {
				player.location = "Cockroach place";	
			} else if  (isAtspider(player)) {
				player.location = "Giant spiders";					
			} else if  (isAtcharm(player)) {
				player.location = "Charm place";
			} else if  (isAtcastle(player)) {
				player.location = "Castle wars";
			} else if  (isAtduel(player)) {
				player.location = "Duel arena";	
			} else if  (isAtyanille(player)) {
				player.location = "Yanille";
			} else if  (isAtseers(player)) {
				player.location = "Seers village";
			} else if  (isAtcath(player)) {
				player.location = "Catherby";					
			} else if  (isAtardy(player)) {
				player.location = "Ardougne";				
			} else if  (isAthero(player)) {
				player.location = "Heroes guild";
			} else if  (isAtrunespan(player)) {
				player.location = "Runespan";
			} else if  (isAtape(player)) {
				player.location = "Ape-atoll";	
			} else if  (isAtzombie(player)) {
				player.location = "Armoured zombies";
			} else if  (isAtjadinko(player)) {
				player.location = "Jadinko's lair";				
			} else if  (isAtcamp(player)) {
				player.location = "Bandit camp";				
			} else if  (isAtedge(player)) {
				player.location = "Edgeville";				
			} else if  (isAtess(player)) {
				player.location = "Rune essence place";				
			} else if  (isAtlost(player)) {
				player.location = "Lost city quest place";					
			} else if  (isAtfletch(player)) {
				player.location = "Fletch/Firemaking place";
			} else if  (isAtdwarf(player)) {
				player.location = "Dwarf cannon quest";				
			} else {		
				player.location = "Unknown place";
			}
			player.getInterfaceManager().sendOverlay(1073, false);
			player.getPackets().sendIComponentText(1073, 10, "Your current location:");
			player.getPackets().sendIComponentText(1073, 11, ""+player.location);
		}
	}

	/*
	 * checks clip
	 */
	public static boolean canMoveNPC(int plane, int x, int y, int size) {
		for (int tileX = x; tileX < x + size; tileX++)
			for (int tileY = y; tileY < y + size; tileY++)
				if (getMask(plane, tileX, tileY) != 0)
					return false;
		return true;
	}

	/*
	 * checks clip
	 */
	public static boolean isNotCliped(int plane, int x, int y, int size) {
		for (int tileX = x; tileX < x + size; tileX++)
			for (int tileY = y; tileY < y + size; tileY++)
				if ((getMask(plane, tileX, tileY) & 2097152) != 0)
					return false;
		return true;
	}

	public static int getMask(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return -1;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		return region.getMask(tile.getPlane(), baseLocalX, baseLocalY);
	}

	public static void setMask(int plane, int x, int y, int mask) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		region.setMask(tile.getPlane(), baseLocalX, baseLocalY, mask);
	}

	public static int getRotation(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return 0;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		return region.getRotation(tile.getPlane(), baseLocalX, baseLocalY);
	}

	private static int getClipedOnlyMask(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return -1;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		return region
				.getMaskClipedOnly(tile.getPlane(), baseLocalX, baseLocalY);
	}

	public static final boolean checkProjectileStep(int plane, int x, int y,
			int dir, int size) {
		int xOffset = Utils.DIRECTION_DELTA_X[dir];
		int yOffset = Utils.DIRECTION_DELTA_Y[dir];
		/*
		 * int rotation = getRotation(plane,x+xOffset,y+yOffset); if(rotation !=
		 * 0) { dir += rotation; if(dir >= Utils.DIRECTION_DELTA_X.length) dir =
		 * dir - (Utils.DIRECTION_DELTA_X.length-1); xOffset =
		 * Utils.DIRECTION_DELTA_X[dir]; yOffset = Utils.DIRECTION_DELTA_Y[dir];
		 * }
		 */
		if (size == 1) {
			int mask = getClipedOnlyMask(plane, x
					+ Utils.DIRECTION_DELTA_X[dir], y
					+ Utils.DIRECTION_DELTA_Y[dir]);
			if (xOffset == -1 && yOffset == 0)
				return (mask & 0x42240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (mask & 0x60240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (mask & 0x40a40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (mask & 0x48240000) == 0;
			if (xOffset == -1 && yOffset == -1) {
				return (mask & 0x43a40000) == 0
						&& (getClipedOnlyMask(plane, x - 1, y) & 0x42240000) == 0
						&& (getClipedOnlyMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == 1 && yOffset == -1) {
				return (mask & 0x60e40000) == 0
						&& (getClipedOnlyMask(plane, x + 1, y) & 0x60240000) == 0
						&& (getClipedOnlyMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == -1 && yOffset == 1) {
				return (mask & 0x4e240000) == 0
						&& (getClipedOnlyMask(plane, x - 1, y) & 0x42240000) == 0
						&& (getClipedOnlyMask(plane, x, y + 1) & 0x48240000) == 0;
			}
			if (xOffset == 1 && yOffset == 1) {
				return (mask & 0x78240000) == 0
						&& (getClipedOnlyMask(plane, x + 1, y) & 0x60240000) == 0
						&& (getClipedOnlyMask(plane, x, y + 1) & 0x48240000) == 0;
			}
		} else if (size == 2) {
			if (xOffset == -1 && yOffset == 0)
				return (getClipedOnlyMask(plane, x - 1, y) & 0x43a40000) == 0
				&& (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4e240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (getClipedOnlyMask(plane, x + 2, y) & 0x60e40000) == 0
				&& (getClipedOnlyMask(plane, x + 2, y + 1) & 0x78240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (getClipedOnlyMask(plane, x, y - 1) & 0x43a40000) == 0
				&& (getClipedOnlyMask(plane, x + 1, y - 1) & 0x60e40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (getClipedOnlyMask(plane, x, y + 2) & 0x4e240000) == 0
				&& (getClipedOnlyMask(plane, x + 1, y + 2) & 0x78240000) == 0;
			if (xOffset == -1 && yOffset == -1)
				return (getClipedOnlyMask(plane, x - 1, y) & 0x4fa40000) == 0
				&& (getClipedOnlyMask(plane, x - 1, y - 1) & 0x43a40000) == 0
				&& (getClipedOnlyMask(plane, x, y - 1) & 0x63e40000) == 0;
			if (xOffset == 1 && yOffset == -1)
				return (getClipedOnlyMask(plane, x + 1, y - 1) & 0x63e40000) == 0
				&& (getClipedOnlyMask(plane, x + 2, y - 1) & 0x60e40000) == 0
				&& (getClipedOnlyMask(plane, x + 2, y) & 0x78e40000) == 0;
			if (xOffset == -1 && yOffset == 1)
				return (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4fa40000) == 0
				&& (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4e240000) == 0
				&& (getClipedOnlyMask(plane, x, y + 2) & 0x7e240000) == 0;
			if (xOffset == 1 && yOffset == 1)
				return (getClipedOnlyMask(plane, x + 1, y + 2) & 0x7e240000) == 0
				&& (getClipedOnlyMask(plane, x + 2, y + 2) & 0x78240000) == 0
				&& (getClipedOnlyMask(plane, x + 1, y + 1) & 0x78e40000) == 0;
		} else {
			if (xOffset == -1 && yOffset == 0) {
				if ((getClipedOnlyMask(plane, x - 1, y) & 0x43a40000) != 0
						|| (getClipedOnlyMask(plane, x - 1, -1 + (y + size)) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 0) {
				if ((getClipedOnlyMask(plane, x + size, y) & 0x60e40000) != 0
						|| (getClipedOnlyMask(plane, x + size, y - (-size + 1)) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x, y - 1) & 0x43a40000) != 0
						|| (getClipedOnlyMask(plane, x + size - 1, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x, y + size) & 0x4e240000) != 0
						|| (getClipedOnlyMask(plane, x + (size - 1), y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x - 1, y - 1) & 0x43a40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + (-1 + sizeOffset)) & 0x4fa40000) != 0
					|| (getClipedOnlyMask(plane, sizeOffset - 1 + x,
							y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x + size, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + size, sizeOffset
							+ (-1 + y)) & 0x78e40000) != 0
							|| (getClipedOnlyMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x - 1, y + size) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0
					|| (getClipedOnlyMask(plane, -1 + (x + sizeOffset),
							y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x + size, y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0
					|| (getClipedOnlyMask(plane, x + size, y
							+ sizeOffset) & 0x78e40000) != 0)
						return false;
			}
		}
		return true;
	}

	public static final boolean checkWalkStep(int plane, int x, int y, int dir,
			int size) {
		int xOffset = Utils.DIRECTION_DELTA_X[dir];
		int yOffset = Utils.DIRECTION_DELTA_Y[dir];
		int rotation = getRotation(plane, x + xOffset, y + yOffset);
		if (rotation != 0) {
			for (int rotate = 0; rotate < (4 - rotation); rotate++) {
				int fakeChunckX = xOffset;
				int fakeChunckY = yOffset;
				xOffset = fakeChunckY;
				yOffset = 0 - fakeChunckX;
			}
		}

		if (size == 1) {
			int mask = getMask(plane, x + Utils.DIRECTION_DELTA_X[dir], y
					+ Utils.DIRECTION_DELTA_Y[dir]);
			if (xOffset == -1 && yOffset == 0)
				return (mask & 0x42240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (mask & 0x60240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (mask & 0x40a40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (mask & 0x48240000) == 0;
			if (xOffset == -1 && yOffset == -1) {
				return (mask & 0x43a40000) == 0
						&& (getMask(plane, x - 1, y) & 0x42240000) == 0
						&& (getMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == 1 && yOffset == -1) {
				return (mask & 0x60e40000) == 0
						&& (getMask(plane, x + 1, y) & 0x60240000) == 0
						&& (getMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == -1 && yOffset == 1) {
				return (mask & 0x4e240000) == 0
						&& (getMask(plane, x - 1, y) & 0x42240000) == 0
						&& (getMask(plane, x, y + 1) & 0x48240000) == 0;
			}
			if (xOffset == 1 && yOffset == 1) {
				return (mask & 0x78240000) == 0
						&& (getMask(plane, x + 1, y) & 0x60240000) == 0
						&& (getMask(plane, x, y + 1) & 0x48240000) == 0;
			}
		} else if (size == 2) {
			if (xOffset == -1 && yOffset == 0)
				return (getMask(plane, x - 1, y) & 0x43a40000) == 0
				&& (getMask(plane, x - 1, y + 1) & 0x4e240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (getMask(plane, x + 2, y) & 0x60e40000) == 0
				&& (getMask(plane, x + 2, y + 1) & 0x78240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (getMask(plane, x, y - 1) & 0x43a40000) == 0
				&& (getMask(plane, x + 1, y - 1) & 0x60e40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (getMask(plane, x, y + 2) & 0x4e240000) == 0
				&& (getMask(plane, x + 1, y + 2) & 0x78240000) == 0;
			if (xOffset == -1 && yOffset == -1)
				return (getMask(plane, x - 1, y) & 0x4fa40000) == 0
				&& (getMask(plane, x - 1, y - 1) & 0x43a40000) == 0
				&& (getMask(plane, x, y - 1) & 0x63e40000) == 0;
			if (xOffset == 1 && yOffset == -1)
				return (getMask(plane, x + 1, y - 1) & 0x63e40000) == 0
				&& (getMask(plane, x + 2, y - 1) & 0x60e40000) == 0
				&& (getMask(plane, x + 2, y) & 0x78e40000) == 0;
			if (xOffset == -1 && yOffset == 1)
				return (getMask(plane, x - 1, y + 1) & 0x4fa40000) == 0
				&& (getMask(plane, x - 1, y + 1) & 0x4e240000) == 0
				&& (getMask(plane, x, y + 2) & 0x7e240000) == 0;
			if (xOffset == 1 && yOffset == 1)
				return (getMask(plane, x + 1, y + 2) & 0x7e240000) == 0
				&& (getMask(plane, x + 2, y + 2) & 0x78240000) == 0
				&& (getMask(plane, x + 1, y + 1) & 0x78e40000) == 0;
		} else {
			if (xOffset == -1 && yOffset == 0) {
				if ((getMask(plane, x - 1, y) & 0x43a40000) != 0
						|| (getMask(plane, x - 1, -1 + (y + size)) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 0) {
				if ((getMask(plane, x + size, y) & 0x60e40000) != 0
						|| (getMask(plane, x + size, y - (-size + 1)) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == -1) {
				if ((getMask(plane, x, y - 1) & 0x43a40000) != 0
						|| (getMask(plane, x + size - 1, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == 1) {
				if ((getMask(plane, x, y + size) & 0x4e240000) != 0
						|| (getMask(plane, x + (size - 1), y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == -1) {
				if ((getMask(plane, x - 1, y - 1) & 0x43a40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x - 1, y + (-1 + sizeOffset)) & 0x4fa40000) != 0
					|| (getMask(plane, sizeOffset - 1 + x, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == -1) {
				if ((getMask(plane, x + size, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x + size, sizeOffset + (-1 + y)) & 0x78e40000) != 0
					|| (getMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == 1) {
				if ((getMask(plane, x - 1, y + size) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0
					|| (getMask(plane, -1 + (x + sizeOffset), y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 1) {
				if ((getMask(plane, x + size, y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0
					|| (getMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			}
		}
		return true;
	}

	public static final boolean containsPlayer(String username) {
		for (Player p2 : players) {
			if (p2 == null)
				continue;
			if (p2.getUsername().equals(username))
				return true;
		}
		return false;
	}

	public static Player getPlayer(String username) {
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.getUsername().equals(username))
				return player;
		}
		return null;
	}

	public static final Player getPlayerByDisplayName(String username) {
		String formatedUsername = Utils.formatPlayerNameForDisplay(username);
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.getUsername().equalsIgnoreCase(formatedUsername)
					|| player.getDisplayName().equalsIgnoreCase(formatedUsername))
				return player;
		}
		return null;
	}

	public static final EntityList<Player> getPlayers() {
		return players;
	}

	public static final EntityList<NPC> getNPCs() {
		return npcs;
	}

	private World() {

	}

	public static final void safeShutdown(final boolean restart, int delay) {
		if (exiting_start != 0)
			return;
		exiting_start = Utils.currentTimeMillis();
		exiting_delay = delay;
		for (Player player : World.getPlayers()) {
			if (player == null || !player.hasStarted() || player.hasFinished())
				continue;
			player.getPackets().sendSystemUpdate(delay);
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					for (Player player : World.getPlayers()) {
						if (player == null || !player.hasStarted())
							continue;
						player.realFinish();
					}
					IPBanL.save();
					PkRank.save();
					if (restart)
						Launcher.restart();
					else
						Launcher.shutdown();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, delay, TimeUnit.SECONDS);
	}

	/*
	 * by default doesnt changeClipData
	 */
	public static final void spawnTemporaryObject(final WorldObject object,
			long time) {
		spawnTemporaryObject(object, time, false);
	}

	public static final void spawnTemporaryObject(final WorldObject object,
			long time, final boolean clip) {
		final int regionId = object.getRegionId();
		WorldObject realMapObject = getRegion(regionId).getRealObject(object);
		// remakes object, has to be done because on static region coords arent
		// same of real
		final WorldObject realObject = realMapObject == null ? null
				: new WorldObject(realMapObject.getId(),
						realMapObject.getType(), realMapObject.getRotation(),
						object.getX(), object.getY(), object.getPlane());
		spawnObject(object, clip);
		final int baseLocalX = object.getX() - ((regionId >> 8) * 64);
		final int baseLocalY = object.getY() - ((regionId & 0xff) * 64);
		if (realObject != null && clip)
			getRegion(regionId).removeMapObject(realObject, baseLocalX,
					baseLocalY);
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					getRegion(regionId).removeObject(object);
					if (clip) {
						getRegion(regionId).removeMapObject(object, baseLocalX,
								baseLocalY);
						if (realObject != null) {
							int baseLocalX = object.getX()
									- ((regionId >> 8) * 64);
							int baseLocalY = object.getY()
									- ((regionId & 0xff) * 64);
							getRegion(regionId).addMapObject(realObject,
									baseLocalX, baseLocalY);
						}
					}
					for (Player p2 : players) {
						if (p2 == null || !p2.hasStarted() || p2.hasFinished()
								|| !p2.getMapRegionsIds().contains(regionId))
							continue;
						if (realObject != null)
							p2.getPackets().sendSpawnedObject(realObject);
						else
							p2.getPackets().sendDestroyObject(object);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		}, time, TimeUnit.MILLISECONDS);
	}

	public static final boolean isSpawnedObject(WorldObject object) {
		final int regionId = object.getRegionId();
		WorldObject spawnedObject = getRegion(regionId)
				.getSpawnedObject(object);
		if (spawnedObject != null && object.getId() == spawnedObject.getId())
			return true;
		return false;
	}

	public static final boolean removeTemporaryObject(final WorldObject object,
			long time, final boolean clip) {
		final int regionId = object.getRegionId();
		// remakes object, has to be done because on static region coords arent
		// same of real
		final WorldObject realObject = object == null ? null : new WorldObject(
				object.getId(), object.getType(), object.getRotation(),
				object.getX(), object.getY(), object.getPlane());
		if (realObject == null)
			return false;
		removeObject(object, clip);
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					getRegion(regionId).removeRemovedObject(object);
					if (clip) {
						int baseLocalX = object.getX() - ((regionId >> 8) * 64);
						int baseLocalY = object.getY()
								- ((regionId & 0xff) * 64);
						getRegion(regionId).addMapObject(realObject,
								baseLocalX, baseLocalY);
					}
					for (Player p2 : players) {
						if (p2 == null || !p2.hasStarted() || p2.hasFinished()
								|| !p2.getMapRegionsIds().contains(regionId))
							continue;
						p2.getPackets().sendSpawnedObject(realObject);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		}, time, TimeUnit.MILLISECONDS);

		return true;
	}

	public static final void removeObject(WorldObject object, boolean clip) {
		int regionId = object.getRegionId();
		getRegion(regionId).addRemovedObject(object);
		if (clip) {
			int baseLocalX = object.getX() - ((regionId >> 8) * 64);
			int baseLocalY = object.getY() - ((regionId & 0xff) * 64);
			getRegion(regionId).removeMapObject(object, baseLocalX, baseLocalY);
		}
		synchronized (players) {
			for (Player p2 : players) {
				if (p2 == null || !p2.hasStarted() || p2.hasFinished()
						|| !p2.getMapRegionsIds().contains(regionId))
					continue;
				p2.getPackets().sendDestroyObject(object);
			}
		}
	}

	public static final WorldObject getObject(WorldTile tile) {
		int regionId = tile.getRegionId();
		int baseLocalX = tile.getX() - ((regionId >> 8) * 64);
		int baseLocalY = tile.getY() - ((regionId & 0xff) * 64);
		return getRegion(regionId).getObject(tile.getPlane(), baseLocalX,
				baseLocalY);
	}

	public static final WorldObject getObject(WorldTile tile, int type) {
		int regionId = tile.getRegionId();
		int baseLocalX = tile.getX() - ((regionId >> 8) * 64);
		int baseLocalY = tile.getY() - ((regionId & 0xff) * 64);
		return getRegion(regionId).getObject(tile.getPlane(), baseLocalX,
				baseLocalY, type);
	}

	public static final void spawnObject(WorldObject object, boolean clip) {
		int regionId = object.getRegionId();
		getRegion(regionId).addObject(object);
		if (clip) {
			int baseLocalX = object.getX() - ((regionId >> 8) * 64);
			int baseLocalY = object.getY() - ((regionId & 0xff) * 64);
			getRegion(regionId).addMapObject(object, baseLocalX, baseLocalY);
		}
		synchronized (players) {
			for (Player p2 : players) {
				if (p2 == null || !p2.hasStarted() || p2.hasFinished())
					continue;
				p2.getPackets().sendSpawnedObject(object);
			}
		}
	}

	public static final void addGroundItem(final Item item, final WorldTile tile) {
		final FloorItem floorItem = new FloorItem(item, tile, null, false,
				false);
		final Region region = getRegion(tile.getRegionId());
		region.forceGetFloorItems().add(floorItem);
		int regionId = tile.getRegionId();
		for (Player player : players) {
			if (player == null || !player.hasStarted() || player.hasFinished()
					|| player.getPlane() != tile.getPlane()
					|| !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendGroundItem(floorItem);
		}
	}

	public static final void addGroundItem(final Item item,
			final WorldTile tile, final Player owner/* null for default */,
			final boolean underGrave, long hiddenTime/* default 3minutes */,
			boolean invisible) {
		addGroundItem(item, tile, owner, underGrave, hiddenTime, invisible, false, 180);
	}

	public static final void addGroundItem(final Item item,
			final WorldTile tile, final Player owner/* null for default */,
			final boolean underGrave, long hiddenTime/* default 3minutes */,
			boolean invisible, boolean intoGold) {
		addGroundItem(item, tile, owner, underGrave, hiddenTime, invisible, intoGold, 180);
	}

	public static final void addGroundItem(final Item item,
			final WorldTile tile, final Player owner/* null for default */,
			final boolean underGrave, long hiddenTime/* default 3minutes */,
			boolean invisible, boolean intoGold, final int publicTime) {
		if(intoGold) {
			if(!ItemConstants.isTradeable(item)) {
				int price = item.getDefinitions().getValue();
				if(price <= 0) 
					return;
				item.setId(995);
				item.setAmount(price);
			}
		}
		final FloorItem floorItem = new FloorItem(item, tile, owner,
				owner == null ? false : underGrave, invisible);
		final Region region = getRegion(tile.getRegionId());
		region.forceGetFloorItems().add(floorItem);
		if (invisible && hiddenTime != -1) {
			if (owner != null)
				owner.getPackets().sendGroundItem(floorItem);
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						if (!region.forceGetFloorItems().contains(floorItem))
							return;
						int regionId = tile.getRegionId();
						if (underGrave || !ItemConstants.isTradeable(floorItem) || item.getName().contains("Dr nabanik")) {
							region.forceGetFloorItems().remove(floorItem);
							if(owner != null) {
								if (owner.getMapRegionsIds().contains(regionId) && owner.getPlane() == tile.getPlane())
									owner.getPackets().sendRemoveGroundItem(floorItem);
							}
							return;
						}

						floorItem.setInvisible(false);
						for (Player player : players) {
							if (player == null
									|| player == owner
									|| !player.hasStarted()
									|| player.hasFinished()
									|| player.getPlane() != tile.getPlane()
									|| !player.getMapRegionsIds().contains(
											regionId))
								continue;
							player.getPackets().sendGroundItem(floorItem);
						}
						removeGroundItem(floorItem, publicTime);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, hiddenTime, TimeUnit.SECONDS);
			return;
		}
		int regionId = tile.getRegionId();
		for (Player player : players) {
			if (player == null || !player.hasStarted() || player.hasFinished()
					|| player.getPlane() != tile.getPlane()
					|| !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendGroundItem(floorItem);
		}
		removeGroundItem(floorItem, publicTime);
	}

	public static final void updateGroundItem(Item item, final WorldTile tile,
			final Player owner) {
		final FloorItem floorItem = World.getRegion(tile.getRegionId())
				.getGroundItem(item.getId(), tile, owner);
		if (floorItem == null) {
			addGroundItem(item, tile, owner, false, 360, true);
			return;
		}
		floorItem.setAmount(floorItem.getAmount() + item.getAmount());
		owner.getPackets().sendRemoveGroundItem(floorItem);
		owner.getPackets().sendGroundItem(floorItem);

	}

	private static final void removeGroundItem(final FloorItem floorItem, long publicTime) {
		if (publicTime < 0) {
			return;
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					int regionId = floorItem.getTile().getRegionId();
					Region region = getRegion(regionId);
					if (!region.forceGetFloorItems().contains(floorItem))
						return;
					region.forceGetFloorItems().remove(floorItem);
					for (Player player : World.getPlayers()) {
						if (player == null
								|| !player.hasStarted()
								|| player.hasFinished()
								|| player.getPlane() != floorItem.getTile()
								.getPlane()
								|| !player.getMapRegionsIds()
								.contains(regionId))
							continue;
						player.getPackets().sendRemoveGroundItem(floorItem);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, publicTime, TimeUnit.SECONDS);
	}

	public static final boolean removeGroundItem(Player player,
			FloorItem floorItem) {
		return removeGroundItem(player, floorItem, true);
	}

	public static final boolean removeGroundItem(Player player,
			FloorItem floorItem, boolean add) {
		int regionId = floorItem.getTile().getRegionId();
		Region region = getRegion(regionId);
		int coinsInInventory = player.getInventory().getNumerOf(floorItem.getId()) + floorItem.getAmount();//TODO Fixed max cash reset.
		if (coinsInInventory < 0) {
			player.out("Sorry you do not have enough space in your inventory.");
			return false;
		}
		if (!region.forceGetFloorItems().contains(floorItem))
			return false;
		if (player.getInventory().getFreeSlots() == 0)
			return false;
		region.forceGetFloorItems().remove(floorItem);
		if (add) {
			/*
			 * Adds money to pouch if pouch is not full, else adds to inventory.
			 */
			if (floorItem.getId() == 995 && player.money + floorItem.getAmount() > 0) {
				player.getPackets().sendRunScript(5561, 1, floorItem.getAmount());
				player.money += floorItem.getAmount();
				player.refreshMoneyPouch();
				player.out(player.getFormattedNumber(floorItem.getAmount())+" coins have been added to your money pouch.");
			} else {
				player.getInventory().addItem(floorItem.getId(), floorItem.getAmount());
			}
		}
		if (floorItem.isInvisible() || floorItem.isGrave()) {
			player.getPackets().sendRemoveGroundItem(floorItem);
			return true;
		} else {
			for (Player p2 : World.getPlayers()) {
				if (p2 == null || !p2.hasStarted() || p2.hasFinished()
						|| p2.getPlane() != floorItem.getTile().getPlane()
						|| !p2.getMapRegionsIds().contains(regionId))
					continue;
				p2.getPackets().sendRemoveGroundItem(floorItem);
			}
			return true;
		}
	}

	public static final void sendObjectAnimation(WorldObject object, Animation animation) {
		sendObjectAnimation(null, object, animation);
	}

	public static final void sendObjectAnimation(Entity creator, WorldObject object, Animation animation) {
		if (creator == null) {
			for (Player player : World.getPlayers()) {
				if (player == null || !player.hasStarted()
						|| player.hasFinished() || !player.withinDistance(object))
					continue;
				player.getPackets().sendObjectAnimation(object, animation);
			}
		} else {
			for (int regionId : creator.getMapRegionsIds()) {
				List<Integer> playersIndexes = getRegion(regionId)
						.getPlayerIndexes();
				if (playersIndexes == null)
					continue;
				for (Integer playerIndex : playersIndexes) {
					Player player = players.get(playerIndex);
					if (player == null || !player.hasStarted()
							|| player.hasFinished()
							|| !player.withinDistance(object))
						continue;
					player.getPackets().sendObjectAnimation(object, animation);
				}
			}
		}
	}


	public static final void sendGraphics(Entity creator, Graphics graphics,
			WorldTile tile) {
		if (creator == null) {
			for (Player player : World.getPlayers()) {
				if (player == null || !player.hasStarted()
						|| player.hasFinished() || !player.withinDistance(tile))
					continue;
				player.getPackets().sendGraphics(graphics, tile);
			}
		} else {
			for (int regionId : creator.getMapRegionsIds()) {
				List<Integer> playersIndexes = getRegion(regionId)
						.getPlayerIndexes();
				if (playersIndexes == null)
					continue;
				for (Integer playerIndex : playersIndexes) {
					Player player = players.get(playerIndex);
					if (player == null || !player.hasStarted()
							|| player.hasFinished()
							|| !player.withinDistance(tile))
						continue;
					player.getPackets().sendGraphics(graphics, tile);
				}
			}
		}
	}

	public static final void sendProjectile(Entity shooter,
			WorldTile startTile, WorldTile receiver, int gfxId,
			int startHeight, int endHeight, int speed, int delay, int curve,
			int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null
						|| !player.hasStarted()
						|| player.hasFinished()
						|| (!player.withinDistance(shooter) && !player
								.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, startTile, receiver,
						gfxId, startHeight, endHeight, speed, delay, curve,
						startDistanceOffset, 1);
			}
		}
	}

	public static final void sendProjectile(WorldTile shooter, Entity receiver,
			int gfxId, int startHeight, int endHeight, int speed, int delay,
			int curve, int startDistanceOffset) {
		for (int regionId : receiver.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null
						|| !player.hasStarted()
						|| player.hasFinished()
						|| (!player.withinDistance(shooter) && !player
								.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, shooter, receiver,
						gfxId, startHeight, endHeight, speed, delay, curve,
						startDistanceOffset, 1);
			}
		}
	}

	public static final void sendProjectile(Entity shooter, WorldTile receiver,
			int gfxId, int startHeight, int endHeight, int speed, int delay,
			int curve, int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null
						|| !player.hasStarted()
						|| player.hasFinished()
						|| (!player.withinDistance(shooter) && !player
								.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, shooter, receiver,
						gfxId, startHeight, endHeight, speed, delay, curve,
						startDistanceOffset, shooter.getSize());
			}
		}
	}

	public static final void sendProjectile(Entity shooter, Entity receiver, int gfxId, int startHeight, int endHeight, int speed,
			int delay, int curve, int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null
						|| !player.hasStarted()
						|| player.hasFinished()
						|| (!player.withinDistance(shooter) && !player
								.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				player.getPackets().sendProjectile(
						receiver,
						new WorldTile(shooter.getCoordFaceX(size), shooter
								.getCoordFaceY(size), shooter.getPlane()),
								receiver, gfxId, startHeight, endHeight, speed, delay,
								curve, startDistanceOffset, size);
			}
		}
	}
public static final boolean isCannonArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return  (destX >= 3649 && destX <= 3710 && destY >= 2936 && destY <= 3007&& tile.getPlane() == 0) //home
				|| (destX >= 2864 && destX <= 2879 && destY >= 5350 && destY <= 5371 && tile.getPlane() == 2)
			|| (destX >= 2373 && destX <= 2441 && destY >= 3069 && destY <= 3133 && tile.getPlane() == 0)
	|| (destX >= 3324 && destX <= 3394 && destY >= 3201 && destY <= 3280 && tile.getPlane() == 0)
	|| (destX >= 3052 && destX <= 3019 && destY >= 9281 && destY <= 9220 && tile.getPlane() == 0)
	|| (destX >= 3507 && destX <= 3468 && destY >= 9520 && destY <= 9480 && tile.getPlane() == 0)		
;				
	}
	public static final boolean isMultiArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return  (destX >= 3462 && destX <= 3511 && destY >= 9481 && destY <= 9521 && tile.getPlane() == 0) //kalphite queen lair
				|| (destX >= 4540 && destX <= 4799 && destY >= 5052 && destY <= 5183 && tile.getPlane() == 0) // thzaar city
				|| (destX >= 1721 && destX <= 1791 && destY >= 5123 && destY <= 5249) // mole
				|| (destX >= 4493 && destX <= 4531 && destY >= 5508 && destY <= 5625) // pest queen
				|| (destX >= 3664 && destX <= 3695 && destY >= 6035 && destY <= 6063 && tile.getPlane() == 2) // sunfreet				
				|| (destX >= 3029 && destX <= 3374 && destY >= 3759 && destY <= 3903)//wild
				|| (destX >= 2250 && destX <= 2280 && destY >= 4670 && destY <= 4720)
				|| (destX >= 2301 && destX <= 2368 && destY >= 5484 && destY <= 5506)//dung
				|| (destX >= 2693 && destX <= 2710 && destY >= 9557 && destY <= 9569)//Blink				
				|| (destX >= 3198 && destX <= 3380 && destY >= 3904 && destY <= 3970)
				|| (destX >= 3191 && destX <= 3326 && destY >= 3510 && destY <= 3759)
				|| (destX >= 2987 && destX <= 3006 && destY >= 3912 && destY <= 3937)
				|| (destX >= 2245 && destX <= 2295 && destY >= 4675 && destY <= 4720)
				|| (destX >= 2450 && destX <= 3520 && destY >= 9450 && destY <= 9550)
				|| (destX >= 3006 && destX <= 3071 && destY >= 3602 && destY <= 3710)
		|| (tile.getX() >= 2893 && tile.getX() <= 2931 && tile.getY() >= 4433 && tile.getY() <= 4467)//daggs				
				|| (destX >= 3134 && destX <= 3192 && destY >= 3519 && destY <= 3646)
				|| (destX >= 2815 && destX <= 2966 && destY >= 5240 && destY <= 5375)//wild
				|| (destX >= 2840 && destX <= 2950 && destY >= 5190 && destY <= 5230) // godwars
				|| (destX >= 3547 && destX <= 3555 && destY >= 9690 && destY <= 9699) // zaros
				//rock crabs
				|| (destX >= 2689 && destX <= 2728 && destY >= 3709 && destY <= 3731)
				//charms place
				|| (destX >= 3136 && destX <= 3155 && destY >= 5441 && destY <= 5463)
				|| (destX >= 2979 && destX <= 3009 && destY >= 3106 && destY <= 3131)
				// godwars
				|| KingBlackDragon.atKBD(tile) // King Black Dragon lair
				|| TormentedDemon.atTD(tile) // Tormented demon's area
				|| Bork.atBork(tile) // Bork's area
				|| (destX >= 2970 && destX <= 3000 && destY >= 4365 && destY <= 4400)// corp
				|| (destX >= 3195 && destX <= 3327 && destY >= 3520
				&& destY <= 3970 || (destX >= 2376 && 5127 >= destY
				&& destX <= 2422 && 5168 <= destY))
				|| (destX >= 2374 && destY >= 5129 && destX <= 2424 && destY <= 5168) // pits
				|| (destX >= 2622 && destY >= 5696 && destX <= 2573 && destY <= 5752) // torms
				|| (destX >= 2368 && destY >= 3072 && destX <= 2431 && destY <= 3135) // castlewars
				// out
				|| (destX >= 2365 && destY >= 9470 && destX <= 2436 && destY <= 9532) // castlewars
				|| (destX >= 2948 && destY >= 5537 && destX <= 3071 && destY <= 5631) // Risk
				// ffa.
				|| (destX >= 2756 && destY >= 5537 && destX <= 2879 && destY <= 5631)	//Safe ffa

				|| (tile.getX() >= 3011 && tile.getX() <= 3132 && tile.getY() >= 10052 && tile.getY() <= 10175
				&& (tile.getY() >= 10066 || tile.getX() >= 3094)) //fortihrny dungeon
				;
		// in

		// multi
	}

	public static final boolean isPvpArea(WorldTile tile) {
		return Wilderness.isAtWild(tile);
	}

	public static void destroySpawnedObject(WorldObject object, boolean clip) {
		int regionId = object.getRegionId();
		int baseLocalX = object.getX() - ((regionId >> 8) * 64);
		int baseLocalY = object.getY() - ((regionId & 0xff) * 64);
		WorldObject realMapObject = getRegion(regionId).getRealObject(object);

		World.getRegion(regionId).removeObject(object);
		if (clip)
			World.getRegion(regionId).removeMapObject(object, baseLocalX,
					baseLocalY);
		for (Player p2 : World.getPlayers()) {
			if (p2 == null || !p2.hasStarted() || p2.hasFinished()
					|| !p2.getMapRegionsIds().contains(regionId))
				continue;
			if (realMapObject != null)
				p2.getPackets().sendSpawnedObject(realMapObject);
			else
				p2.getPackets().sendDestroyObject(object);
		}
	}

	public static void destroySpawnedObject(WorldObject object) {
		int regionId = object.getRegionId();
		int baseLocalX = object.getX() - ((regionId >> 8) * 64);
		int baseLocalY = object.getY() - ((regionId & 0xff) * 64);
		World.getRegion(regionId).removeObject(object);
		World.getRegion(regionId).removeMapObject(object, baseLocalX,baseLocalY);
		for (Player p2 : World.getPlayers()) {
			if (p2 == null || !p2.hasStarted() || p2.hasFinished()
					|| !p2.getMapRegionsIds().contains(regionId))
				continue;
			p2.getPackets().sendDestroyObject(object);
		}
	}

	public static final void spawnTempGroundObject(final WorldObject object,
			final int replaceId, long time) {
		final int regionId = object.getRegionId();
		WorldObject realMapObject = getRegion(regionId).getRealObject(object);
		final WorldObject realObject = realMapObject == null ? null
				: new WorldObject(realMapObject.getId(),
						realMapObject.getType(), realMapObject.getRotation(),
						object.getX(), object.getY(), object.getPlane());
		spawnObject(object, false);
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					getRegion(regionId).removeObject(object);
					addGroundItem(new Item(replaceId), object, null, false,
							180, false);
					for (Player p2 : players) {
						if (p2 == null || !p2.hasStarted() || p2.hasFinished()
								|| p2.getPlane() != object.getPlane()
								|| !p2.getMapRegionsIds().contains(regionId))
							continue;
						if (realObject != null)
							p2.getPackets().sendSpawnedObject(realObject);
						else
							p2.getPackets().sendDestroyObject(object);
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, time, TimeUnit.MILLISECONDS);
	}

	public static void sendWorldMessage(String message, boolean forStaff) {
		for (Player p : World.getPlayers()) {
			if (p == null || !p.isRunning() || p.isYellOff() || (forStaff && p.getRights() == 0))
				continue;
			p.getPackets().sendGameMessage(message);
		}
	}

	public static final void sendProjectile(WorldObject object, WorldTile startTile,
			WorldTile endTile, int gfxId, int startHeight, int endHeight, int speed, int delay,
			int curve, int startOffset) {
		for(Player pl : players) {
			if(pl == null || !pl.withinDistance(object, 20))
				continue;
			pl.getPackets().sendProjectile(null, startTile, endTile, gfxId,
					startHeight, endHeight, speed, delay, curve, startOffset, 1);
		}
	}

}