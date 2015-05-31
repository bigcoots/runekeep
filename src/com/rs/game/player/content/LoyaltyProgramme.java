package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.player.Player;
import com.rs.game.player.content.LoyaltyItem;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.item.FloorItem;


public class LoyaltyProgramme implements Serializable {

	/**
	 * The serial UID
	 */
	private static final long serialVersionUID = -111881367666488484L;

	/**
	 * The loyalty shop interface
	 */
	public static final int INTERFACE_ID = 1143;

	/**
	 * The tab switch config
	 */
	public static final int TAB_CONFIG = 2226;

	/**
	 * An array of available loyalty shop categories
	 */
	public static final String[] CATEGORIES = { "auras", "emotes", "outfits",
			"titles", "special-offers", "limmited-edition", "recolor",
			"effects", "favorites", "home" };

	/**
	 * The current tab
	 */
	public static int currentTab;

	/**
	 * The player using the programme
	 */
	private Player player;

	/**
	 * Opens the loyalty shop interface
	 */
	public static void openShop(Player player) {
		player.getInterfaceManager().sendScreenInterface(96, INTERFACE_ID);
		player.getPackets().sendConfig(TAB_CONFIG, -1);
		currentTab = -1;
		player.getPackets().sendIComponentText(INTERFACE_ID, 127,
				"" + player.getLoyaltyPoints());
	}

	/**
	 * Opens a tab on the loyalty interface
	 * 
	 * @param tab
	 *            The tab to open
	 */
	public void openTab(String tab) {
		switch (tab.toLowerCase()) {
		case "home":
			player.getPackets().sendConfig(TAB_CONFIG, -1);
			currentTab = -1;
		case "auras":
			player.getPackets().sendConfig(TAB_CONFIG, 1);
			currentTab = 1;
			break;
		case "emotes":
			player.getPackets().sendConfig(TAB_CONFIG, 2);
			currentTab = 2;
			break;
		case "outfits":
			player.getPackets().sendConfig(TAB_CONFIG, 3);
			currentTab = 3;
			break;
		case "titles":
			player.getPackets().sendConfig(TAB_CONFIG, 4);
			currentTab = 4;
			break;
		case "recolor":
			player.getPackets().sendConfig(TAB_CONFIG, 5);
			currentTab = 5;
			break;
		case "special-offers":
			player.getPackets().sendConfig(TAB_CONFIG, 6);
			currentTab = 6;
			break;
		case "limmited-edition":
			player.getPackets().sendConfig(TAB_CONFIG, 7);
			currentTab = 7;
			break;
		case "favorites":
			player.getPackets().sendConfig(TAB_CONFIG, 8);
			currentTab = 8;
			break;
		case "effects":
			player.getPackets().sendConfig(TAB_CONFIG, 9);
			currentTab = 9;
			break;
		default:
			player.getPackets().sendGameMessage("This tab is currently un-available");
		}
	}

	public static void openPurchaseResultsInterface(Player player, String category, int slotId) {
		try {
			player.getPackets().sendHideIComponent(INTERFACE_ID, 16, false);
			player.getPackets().sendHideIComponent(INTERFACE_ID, 58, false);
			player.getPackets().sendIComponentText(INTERFACE_ID, 161, "Your purchase was successful!");
			switch (category) {
			case "emotes":
				player.getPackets().sendIComponentText(INTERFACE_ID, 162,
						"Xuan teaches you the emote: (emoteNameHere)");
				break;
			case "outfits":
				player.getPackets().sendIComponentText(INTERFACE_ID, 162,
						"Xuan hands you the costume you purchased.");
				break;
			case "auras":
				player.getPackets().sendIComponentText(
						INTERFACE_ID,
						162,
						"You recieved the aura: ");
				break;
			case "titles":
				player.getPackets().sendIComponentText(INTERFACE_ID, 162,
						"You have unlocked the title: (titleNameHere)");
				break;
			}
			player.getPackets().sendIComponentText(INTERFACE_ID, 162,
					"\n \n Click 'Ok' to return to the shop.");
		} catch (Exception e) {
			//openErrorOccuredInterface();
		}
	}

	/**
	 * Handles any button clicks
	 * 
	 * @param componentId
	 *            The clicked component
	 * @param slotId
	 *            The clicked slot
	 * @param slotId2
	 *            The clicked slot (2)
	 * @param packetId
	 *            The packet ID
	 */
	public void handleLoyaltyButtons(int componentId, int slotId, int slotId2,
			int packetId) {
		switch (componentId) {
		case 3:
			openTab("favorites");
			break;
		case 103:
	 		player.closeInterfaces();
			break;
		case 1:
			openTab("home");
			break;
		case 7:
			openTab("auras");
			break;
		case 8:
			openTab("effects");
			break;
		case 9:
			openTab("emotes");
			break;
		case 10:
			openTab("outfits");
			break;
		case 11:
			openTab("titles");
			break;
		case 12:
			openTab("recolor");
			break;
		case 13:
			openTab("special-offers");
			break;
		case 169:// Buy
			//buyItem(slotId, "auras");
			openPurchaseResultsInterface(player, "special-offers", slotId);// will use attributes.
			break;
		}
	}

	/**
	 * Favorites an item
	 * 
	 * @param value
	 *            The item to favorite
	 */
	public void favorite(int value) {
		player.getPackets().sendConfig(2391, value);
	}

	/**
	 * Claims an item
	 * 
	 * @param value
	 *            The item to claim
	 */
	public void claim(int value) {
		player.getPackets().sendConfig(2229, value);
		player.getPackets().sendConfig(TAB_CONFIG, currentTab);// refresh tab
	}

	/**
	 * Sets the player
	 * 
	 * @param player
	 *            The player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
}