package com.rs.game.minigames;

 import java.util.List;
 import java.util.TimerTask;
 import java.util.concurrent.TimeUnit;

 import com.rs.Settings;
 import com.rs.cores.CoresManager;
 import com.rs.game.Animation;
 import com.rs.game.RegionBuilder;
 import com.rs.game.World;
 import com.rs.game.WorldObject;
 import com.rs.game.WorldTile;
 import com.rs.game.npc.NPC;
 import com.rs.game.npc.zombies.ZombieCaves;
 import com.rs.game.npc.zombies.ZombiesNPC;
 import com.rs.game.player.Player;
 import com.rs.game.player.content.FadingScreen;
 import com.rs.game.player.controlers.Controler;
 import com.rs.game.tasks.WorldTask;
 import com.rs.game.tasks.WorldTasksManager;
 import com.rs.utils.Logger;

 /**
 * 
 * @author Austin
 */

 public class dung extends Controler{
 /**
 * Data
 */

 private int[] regionChucks;
 private dungStages stage;
 private boolean logoutAtEnd;
 private boolean login;
 public boolean spawned;
 private int[] boundChuncks;





 /**
 * Holds the Zombies
 */



 private final int[][] MONSTERS = {
 {152}
 ,{213}
 ,{226}
 ,{70}
 ,{8549}
 };

 /*
 * 14281//135
 * 14339//85
 */



 /**
 * 
 * @author Adam
 *
 */
 private static enum dungStages {
 LOADING,
 RUNNING,
 DESTROYING
 }



 /**
 * Starts game
 */
 @Override
 public void start() {
 startGame(false);

 }





 /**
 * Starts the game & loads the map.
 * @param login
 */

 public void fade (final Player player) {
 final long time = FadingScreen.fade(player);
 CoresManager.slowExecutor.execute(new Runnable() {
 @Override
 public void run() {
 try {
 FadingScreen.unfade(player, time, new Runnable() {
 @Override
 public void run() {



 }
 });
 } catch (Throwable e) {
 Logger.handle(e);
 }
 }

 });
 }



 public void startGame(final boolean login) {

 fade(player);
 this.login = login;
 stage = dungStages.LOADING;
 player.lock(); //locks player

 CoresManager.slowExecutor.execute(new Runnable() {
 @Override
 public void run() {
 //regionChucks = RegionBuilder.findEmptyChunkBound(9, 9);
  //RegionBuilder.copyAllPlanesMap(456, 439, regionChucks[0],// mhmk ima eat icecream have fun
 //regionChucks[1], 9);
 regionChucks = RegionBuilder.findEmptyChunkBound(8, 8); 
 RegionBuilder.copyAllPlanesMap(122, 140, regionChucks[0], regionChucks[0], 8);//is this rightno urs is abovethes

 player.setNextWorldTile(getWorldTile(1, 2) );
 player.getDialogueManager().startDialogue("SimpleNPCMessage", 11226, "Welcome to dungeoneering,kill the boss to gain ure loot!");
 WorldTasksManager.schedule(new WorldTask() {
 @Override
 public void run() {
 player.unlock(); 
 stage = dungStages.RUNNING;



 }


	
 }, 1);
 if(!login) {
 CoresManager.fastExecutor.schedule(new TimerTask() {

 @Override
 public void run() {
 if(stage != dungStages.RUNNING)
 return;
 try {
 startWave();
 player.setNextWorldTile(new WorldTile(65, 67, 0));
 } catch (Throwable t) {
 Logger.handle(t);
 }
 }
 }, 6000);
 }
 }
 });
 }




 /**
 * 
 * @return
 */



 public WorldTile getSpawnTile() {
  return getWorldTile(5, 5);
 }





 /**
 * 
 * @param player
 */




 public static void enterRfd(Player player) {
 player.getControlerManager().startControler("dung", 1); 
 }





 /**
 * Handles the buttons.
 */




 @Override
 public boolean processButtonClick(int interfaceId, int componentId, int slotId, int packetId) {
 if(stage != dungStages.RUNNING)
 return false;
 if(interfaceId == 182 && (componentId == 6 || componentId == 13)) {
 if(!logoutAtEnd) {
 logoutAtEnd = true;
 player.getPackets().sendGameMessage("<col=00ff00>You will be logged out automatically at the end of this wave.");
 player.getPackets().sendGameMessage("<col=00ff00>If you log out sooner, you will have to repeat this wave.");
 }else
 player.forceLogout();
 return false;
 }
 return true;
 }







 /**
 * return process normaly
 */




 @Override
 public boolean processObjectClick1(WorldObject object) {
 if(object.getId() == 56149) {
 if(stage != dungStages.RUNNING)

 return false;
 exitCave(1);
	//player.getInterfaceManager().sendPrayerBook(); 
	if(object.getId() == 2156) {
 if(stage != dungStages.RUNNING)
 return false;
 player.setNextWorldTile(getWorldTile(65,67));
  }
 	if(object.getId() == 48496) {
 if(stage != dungStages.RUNNING)
 return false;
 player.setNextWorldTile(getWorldTile(65,67));
 }
	if(object.getId() == 55762) {
		
		
player.setNextWorldTile(getWorldTile(35,35));

}
 return false;

 }
 return true;
 }

	
 @Override
 public void moved() {
 if(stage != dungStages.RUNNING || !login)
 return;
 login = false;
 setWaveEvent();
 }

 public void win() {
 if(stage != dungStages.RUNNING)
 return;
 exitCave(4);
 //player.getInterfaceManager().sendPrayerBook();
 }


 public void startWave() {
 int currentWave = getCurrentWave();
 if(currentWave > MONSTERS.length) {
 win();
 //player.getInterfaceManager().sendPrayerBook();
 return;
 }
 if(stage != dungStages.RUNNING)
 return;
 for(int id : MONSTERS[currentWave-1]) {
 new ZombiesNPC(id, getSpawnTile());
 NPC Monster = findNPC(id);
 player.getHintIconsManager().addHintIcon(Monster, 0, -1, false);
 player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 11 : 0, 316);
 player.getPackets().sendConfig(639, currentWave);
 }
 spawned = true;

 if (getCurrentWave() == 2) {

				player.getBank().addItem(2996, 5000, true);
				player.getBank().addItem(23749, 2, true);
			
 player.out("<col=00ff00> You've recieved some reward in ure bank");
 } else if (getCurrentWave() == 3) {

				player.getBank().addItem(2996, 5000, true);
				player.getBank().addItem(23749, 3, true);			
 player.out("<col=00ff00> You've recieved some reward in ure bank");
 } else if (getCurrentWave() == 4) {

				player.getBank().addItem(2996, 5000, true);
				player.getBank().addItem(23749, 4, true);
 player.out("<col=00ff00> You've recieved some reward in ure bank");
 } else if (getCurrentWave() == 5) {

				player.getBank().addItem(2996, 5000, true);
				player.getBank().addItem(23749, 5, true);
 player.out("<col=00ff00> You've recieved some reward in ure bank");
 //player.getInterfaceManager().sendPrayerBook();
 }
 }

 public static NPC findNPC(int id) {
 for (NPC npc : World.getNPCs()) {
 if (npc == null || npc.getId() != id)
 continue;
 return npc;
 }
 return null;
 }



 /**
 * 
 */
 public void setWaveEvent() {

 if (getCurrentWave() == 5) 
 player.getDialogueManager().startDialogue("SimpleNPCMessage", 8549, "u really think u can kill me?");
 CoresManager.fastExecutor.schedule(new TimerTask() {


 @Override
 public void run() {
 try {
 if(stage != dungStages.RUNNING)
 return;
 startWave();
 } catch (Throwable e) {
 Logger.handle(e);
 }
 }
 }, 600);
 }




 /**
 * Processing.
 */


 @Override
 public void process() {
 if(spawned) {
 List<Integer> npcs = World.getRegion(player.getRegionId()).getNPCsIndexes();
 if(npcs == null || npcs.isEmpty()) {
 spawned = false;
 nextWave();
 }
 }
 }


 /**
 * Sets the next wave.
 */

 public void nextWave() {
 setCurrentWave(getCurrentWave()+1);
 if(logoutAtEnd) {
 player.forceLogout();
 return;
 }
 setWaveEvent();
 }


 /**
 * Death method.
 */
 @Override
 public boolean sendDeath() {
 player.lock(7);
 player.stopAll();
 //player.getInterfaceManager().sendPrayerBook();
 WorldTasksManager.schedule(new WorldTask() {
 int loop;

 @Override
 public void run() {
 if (loop == 0) {
 player.setNextAnimation(new Animation(836));
 } else if (loop == 1) {
 player.getPackets().sendGameMessage("Oh, dear you have died.");
 //player.getInterfaceManager().sendPrayerBook();
 } else if (loop == 3) {
 player.reset();
 exitCave(1);
	//player.getInterfaceManager().sendPrayerBook();
 player.setNextAnimation(new Animation(-1));
 } else if (loop == 4) {
 player.getPackets().sendMusicEffect(90);
 stop();
 }
 loop++;
 }
 }, 0, 1);
 return false;
 }



 /**
 * 
 */

 @Override
 public void magicTeleported(int type) {
 exitCave(2);
	//player.getInterfaceManager().sendPrayerBook();
 }

 /*
 * logout or not. if didnt logout means lost, 0 logout, 1, normal, 2 tele
 */
 public void exitCave(int type) {
 stage = dungStages.DESTROYING;
 //player.getInterfaceManager().sendPrayerBook();

 WorldTile outside = new WorldTile(Settings.START_PLAYER_LOCATION); 
 if(type == 0 || type == 2)
 player.setLocation(outside);
 else {
 player.setForceMultiArea(false);
 if(type == 1 || type == 4) {
 player.setNextWorldTile(outside);
 if(type == 4) {
 fade(player);
 player.reset();
 //player.getInterfaceManager().sendPrayerBook();

				player.getBank().addItem(2996, 5000, true);
				player.getBank().addItem(23749, 1, true);				
 for (Player players : World.getPlayers()) {
 if (players == null)
 continue;

	//player.getInterfaceManager().sendPrayerBook();
 }


 }
 }

 removeControler();
 }

 CoresManager.slowExecutor.schedule(new Runnable() {
 @Override
 public void run() {
 RegionBuilder.destroyMap(regionChucks[0], regionChucks[1], 8, 8);
 //player.getInterfaceManager().sendPrayerBook();
 }
 }, 1200, TimeUnit.MILLISECONDS);

 }





 /*
 * gets worldtile inside the map
 */
 public WorldTile getWorldTile(int mapX, int mapY) {
 return new WorldTile(regionChucks[0]*8 + mapX, regionChucks[1]*8 + mapY, 0);
 }




 /*
 * return false so wont remove script
 */
 @Override
 public boolean logout() {
 /*
 * only can happen if dungeon is loading and system update happens
 */
 if(stage != dungStages.RUNNING)
 return false;
 exitCave(0);
 //player.getInterfaceManager().sendPrayerBook();
 return false;

 }



 /**
 * 
 * @return
 */

 public int getCurrentWave() {
 if (getArguments() == null || getArguments().length == 0) 
 return 0;
 return (Integer) getArguments()[0];
 }





 /**
 * 
 * @param wave
 */

 public void setCurrentWave(int wave) {
 if(getArguments() == null || getArguments().length == 0)
 this.setArguments(new Object[1]);
 getArguments()[0] = wave;
 }

 @Override
 public void forceClose() {
 /*
 * shouldnt happen
 */
 if(stage != dungStages.RUNNING)
 return;
 exitCave(2);
 //player.getInterfaceManager().sendPrayerBook();
 }



 public void spawnZombieMembers() {
 if(stage != dungStages.RUNNING)
 return;
 for(int i = 0; i < 4; i++)
 new ZombieCaves(2746, getSpawnTile());
 }


 }
