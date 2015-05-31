package com.rs.utils;
 
import com.rs.game.StarterMap;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.content.FriendChatsManager;
 
public class Starter {
 
    public static final int MAX_STARTER_COUNT = 2;
     
    private static int amount = 3000000;
    
    private static boolean hasRegistered;
 
    public static void appendStarter(Player player) {
        String ip = player.getSession().getIP();
        int count = StarterMap.getSingleton().getCount(ip);
        player.getStarter = true;
        if (count >= MAX_STARTER_COUNT) {
            return;
        }
         
        player.getInventory().addItem(995, amount);
		player.getInventory().addItem(1333, 1);
		player.getInventory().addItem(4587, 1);
		player.getInventory().addItem(1323, 1);
		player.getInventory().addItem(1153, 1);
		player.getInventory().addItem(1115, 1);
		player.getInventory().addItem(1067, 1);
		player.getInventory().addItem(841, 1);
		player.getInventory().addItem(882, 10000);
		player.getInventory().addItem(884, 10000);
		player.getInventory().addItem(558, 1000);
		player.getInventory().addItem(556, 1000);
		player.switchItemsLook();
		hasRegistered = true;
		if (hasRegistered == true) {
		World.sendWorldMessage("<col=01DFD7>Welcome<col> " + player.getDisplayName() + "<col=01DFD7> to Validus!</col>", true);
		}
 
        player.getHintIconsManager().removeUnsavedHintIcon();
        player.getMusicsManager().reset();
        player.getCombatDefinitions().setAutoRelatie(false);
        player.getCombatDefinitions().refreshAutoRelatie();
        StarterMap.getSingleton().addIP(ip);
    }
}