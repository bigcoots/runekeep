package com.rs.game.player.content;


import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.content.ItemConstants;
import com.rs.utils.ItemExamines;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.Player;


/**
 * @author Jens
 */


public class DonationBox {
    
    private static int randomItemList[] = { 
        4585, 13967, 13864, 13867, 6739, 2613, 1249, 1263, 1305, 1377, 1434,
        24154, 24155, 22494, 22482, 22486, 8878, 1149, 1187, 1215, 1231,
        13861, 4151, 13858, 13876, 13873, 2597, 2607, 2609, 2611, 4712, 4714,
        13870, 11694, 13902, 13896, 13890, 6568, 2591, 2593, 2595,
        13884, 13905, 13899, 13893, 13887, 11710, 11712, 11714, 4716, 4718, 4720, 4722, 4708, 4710,
        12681, 12680, 10828, 989, 19333, 6585, 1631, 4732, 4734, 4736, 4738, 4724, 4726, 4728, 4730,
        14479, 13970, 22490, 21777, 21787, 21790, 11732, 15262, 
        4087
    };
    
    /**
     * Selects a random number from above
     */
    public static int giveRandomItem() {
        return randomItemList[(int) (Math.random()*randomItemList.length)];
    }
    
    
    /**
     * Add more than one number to make it more common, or to make 
     * other numbers less common.
     */
    private static int RandomAmounts[] = { 
        1,1,1,1,1,1,1,1,1,
        1,1,1,1,1,1,1,
        1,1,1,1,
        1,1,
        1
    };
    
    /**
     * Selects an amount randomly from the above
     */
    public static int getRandomAmount() {
        return RandomAmounts[(int) (Math.random()*RandomAmounts.length)];
    }
    
    /**
     * Separte the message for rare items/ or the item you excatly want xD ex 1038 is a phat when someone get it, another message will popup
     */
    public static void Reward(Player p) {
        int item = giveRandomItem();
        ItemDefinitions defs = ItemDefinitions.getItemDefinitions(item);
        String name = defs == null ? "" : defs.getName().toLowerCase();
        int amount = getRandomAmount();
        
        if (item == 4087) {
        World.sendWorldMessage("<img=5> [<col=0000FF>Mystery box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won Joury's PASSWORD.", false);
            p.getInventory().addItem(4585, 1);
        } else {
            p.getInventory().addItem(item, amount);
            World.sendWorldMessage("<img=5> [<col=0000FF>Mystery Box</col>] <col=FF0000>"+p.getDisplayName()+" has just opened his mystery box and won a " + name + ".", false);
        }
    }
}