package com.rs.game.player;

import com.rs.game.Animation;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class CathFarming {

	private static final int RAKE = 5341, WEEDS = 6055;
	
	public static void useRakeCA(final Player player, final int configId) {
		if (player.getInventory().containsItem(RAKE, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.lock();
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 1);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
				} else if (loop == 3) {
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 2);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
				} else if (loop == 6) {	
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 3);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
					player.out("You successfully clear all the weeds.");
					player.mustRakeCA = true;
					player.unlock();
					player.farmingStatusI = 1;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need a rake to get rid of the weeds.");
			}
	}
	
	public static void useRakeCB(final Player player, final int configId) {
		if (player.getInventory().containsItem(RAKE, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.lock();
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 1);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
				} else if (loop == 3) {
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 2);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
				} else if (loop == 6) {	
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 3);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
					player.out("You successfully clear all the weeds.");
					player.mustRakeCB = true;
					player.unlock();
					player.farmingStatusJ = 1;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need a rake to get rid of the weeds.");
			}
	}
	public static void useRakeCH(final Player player, final int configId) {
		if (player.getInventory().containsItem(RAKE, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.lock();
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 1);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
				} else if (loop == 3) {
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 2);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
				} else if (loop == 6) {	
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 3);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
					player.out("You successfully clear all the weeds.");
					player.mustRakeCH = true;
					player.unlock();
					player.farmingStatusL = 1;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need a rake to get rid of the weeds.");
			}
	}
	public static void useRakeCF(final Player player, final int configId) {
		if (player.getInventory().containsItem(RAKE, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.lock();
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 1);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
				} else if (loop == 3) {
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 2);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
				} else if (loop == 6) {	
					player.setNextAnimation(new Animation(2273));
					player.getPackets().sendConfigByFile(configId, 3);
					player.getInventory().addItem(WEEDS, 1);
					player.getSkills().addXp(Skills.FARMING, 50);
					player.out("You successfully clear all the weeds.");
					player.mustRakeCF = true;
					player.unlock();
					player.farmingStatusK = 1;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need a rake to get rid of the weeds.");
			}
	}
	
	
	public static void plantPotatoCA(final Player player, final int configId) {
		if (player.getInventory().containsItem(5318, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusI = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 6);
					player.getInventory().deleteItem(5318, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 7);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 10);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment A.</col>");
					player.canHarvestCA = true;
					player.farmingStatusI = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some potato seeds.");
			}
	}
	
	public static void plantPotatoCB(final Player player, final int configId) {
		if (player.getInventory().containsItem(5318, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusJ = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 6);
					player.getInventory().deleteItem(5318, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 7);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 10);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment B.</col>");
					player.canHarvestCB = true;
					player.farmingStatusJ = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some potato seeds.");
			}
	}

	public static void plantTomatoCA(final Player player, final int configId) {
		if (player.getInventory().containsItem(5322, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusI = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 27);
					player.getInventory().deleteItem(5322, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 29);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 31);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment A.</col>");
					player.canHarvestCA = true;
					player.farmingStatusI = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some tomato seeds.");
			}
	}
	
	public static void plantTomatoCB(final Player player, final int configId) {
		if (player.getInventory().containsItem(5322, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusJ = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 27);
					player.getInventory().deleteItem(5322, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 29);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 31);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment B.</col>");
					player.canHarvestCB = true;
					player.farmingStatusJ = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some tomato seeds.");
			}
	}

	public static void plantOnionCA(final Player player, final int configId) {
		if (player.getInventory().containsItem(5319, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusI = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 13);
					player.getInventory().deleteItem(5319, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 15);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 17);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment A.</col>");
					player.canHarvestCA = true;
					player.farmingStatusI = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Onion seeds.");
			}
	}
	
	public static void plantOnionCB(final Player player, final int configId) {
		if (player.getInventory().containsItem(5319, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusJ = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 13);
					player.getInventory().deleteItem(5319, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 15);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 17);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment B.</col>");
					player.canHarvestCB = true;
					player.farmingStatusJ = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Onion seeds.");
			}
	}

	public static void plantCabbageCA(final Player player, final int configId) {
		if (player.getInventory().containsItem(5324, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusI = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 20);
					player.getInventory().deleteItem(5324, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 21);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 24);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment A.</col>");
					player.canHarvestCA = true;
					player.farmingStatusI = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Cabbage seeds.");
			}
	}
	
	public static void plantCabbageCB(final Player player, final int configId) {
		if (player.getInventory().containsItem(5324, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusJ = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 20);
					player.getInventory().deleteItem(5324, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 21);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 24);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment B.</col>");
					player.canHarvestCB = true;
					player.farmingStatusJ = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Cabbage seeds.");
			}
	}

	public static void plantStrawberryCA(final Player player, final int configId) {
		if (player.getInventory().containsItem(5323, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusI = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 43);
					player.getInventory().deleteItem(5504, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 46);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 49);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment A.</col>");
					player.canHarvestCA = true;
					player.farmingStatusI = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Strawberry seeds.");
			}
	}
	
	public static void plantStrawberryCB(final Player player, final int configId) {
		if (player.getInventory().containsItem(5323, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusJ = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 43);
					player.getInventory().deleteItem(5504, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 46);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 49);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment B.</col>");
					player.canHarvestCB = true;
					player.farmingStatusJ = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Strawberry seeds.");
			}
	}
	
	public static void plantMelonCA(final Player player, final int configId) {
		if (player.getInventory().containsItem(5321, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusI = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 52);
					player.getInventory().deleteItem(5321, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 56);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 60);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment A.</col>");
					player.canHarvestCA = true;
					player.farmingStatusI = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some melon seeds.");
			}
	}
	
	public static void plantMelonCB(final Player player, final int configId) {
		if (player.getInventory().containsItem(5321, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusJ = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2292));
					player.getPackets().sendConfigByFile(configId, 52);
					player.getInventory().deleteItem(5321, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 56);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 60);
					player.out("<col=FF0000>[Notice] Your crops have fully grown at Catherby, allotment B.</col>");
					player.canHarvestCB = true;
					player.farmingStatusJ = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some melon seeds.");
			}
	}
	
	public static void plantSweetCA(final Player player, final int configId) {
		if (player.getInventory().containsItem(5320, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusI = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 34);
					player.getInventory().deleteItem(5320, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 37);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 40);
					player.out("<col=FF0000>[Notice] Your Sweetcorn has fully grown at Catherby, Allotment A.</col>");
					player.canHarvestCA = true;
					player.farmingStatusI = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need a Sweetcorn Seed.");
			}
	}
	
	public static void plantSweetCB(final Player player, final int configId) {
		if (player.getInventory().containsItem(5320, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusJ = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 34);
					player.getInventory().deleteItem(5320, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 37);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 40);
					player.out("<col=FF0000>[Notice] Your Sweetcorn has fully grown at Catherby, Allotment B.</col>");
					player.canHarvestCB = true;
					player.farmingStatusJ = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need a Sweetcorn Seed.");
			}
	}
	
	public static void plantGuamC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5291, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5291, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some guam seeds.");
			}
	}
	
	
	public static void plantSnapC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5300, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5300, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some snapdragon seeds.");
			}
	}
	
	public static void plantMarrenC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5292, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5292, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Marrentil seeds.");
			}
	}
	
		public static void plantTarromC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5293, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5293, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Tarromin seeds.");
			}
	}
	
		public static void plantHarraC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5294, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5294, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Harralander seeds.");
			}
	}
	
		public static void plantRannarC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5295, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5295, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Rannar seeds.");
			}
	}
	
		public static void plantToadC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5296, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5296, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Toadflax seeds.");
			}
	}
	
		public static void plantIritC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5297, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5297, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Irit seeds.");
			}
	}
	
		public static void plantWergC(final Player player, final int configId) {
		if (player.getInventory().containsItem(14870, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(14870, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Wergali seeds.");
			}
	}
	
		public static void plantAvantC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5298, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5298, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Avantoe seeds.");
			}
	}
	
		public static void plantKwuarmC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5299, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5299, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Kwuarm seeds.");
			}
	}
	
		public static void plantCadanC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5301, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5301, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Cadantine seeds.");
			}
	}
	
		public static void plantLantC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5302, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5302, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Lantadyme seeds.");
			}
	}
	
		public static void plantDwarfC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5303, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5303, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Dwarf seeds.");
			}
	}
	
		public static void plantFellC(final Player player, final int configId) {
		if (player.getInventory().containsItem(21621, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(21621, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Fellstalk seeds.");
			}
	}
	
		public static void plantGoatC(final Player player, final int configId) {
		if (player.getInventory().containsItem(3261, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(3261, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Goat seeds.");
			}
	}
	
	public static void plantTorstolC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5304, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusL = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 4);
					player.getInventory().deleteItem(5304, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 6);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 8);
					player.out("<col=FF0000>[Notice] Your herbs are fully grown at Catherby.</col>");
					player.canHarvestHerbCA = true;
					player.farmingStatusL = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Torstol seeds.");
			}
	}
	
	public static void plantGoldC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5096, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusK = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 8);
					player.getInventory().deleteItem(5096, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 10);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 12);
					player.out("<col=FF0000>[Notice] Your Marigolds are fully grown at Catherby.</col>");
					player.canHarvestFlowerCA = true;
					player.farmingStatusK = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Marigold seeds.");
			}
	}
	
	public static void plantLilyC(final Player player, final int configId) {
		if (player.getInventory().containsItem(14589, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusK = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 37);
					player.getInventory().deleteItem(14589, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 39);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 41);
					player.out("<col=FF0000>[Notice] Your White Lily's have fully grown at Catherby.</col>");
					player.canHarvestFlowerCA = true;
					player.farmingStatusK = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some White Lily seeds.");
			}
	}
	
	public static void plantWoadC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5099, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusK = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 23);
					player.getInventory().deleteItem(5098, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 25);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 27);
					player.out("<col=FF0000>[Notice] Your Woad Seeds are fully grown at Catherby.</col>");
					player.canHarvestFlowerCA = true;
					player.farmingStatusK = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Woad seeds.");
			}
	}
	

	public static void plantNastC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5098, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusK = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 18);
					player.getInventory().deleteItem(5098, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 20);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 22);
					player.out("<col=FF0000>[Notice] Your Nasturtium are fully grown at Catherby.</col>");
					player.canHarvestFlowerCA = true;
					player.farmingStatusK = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Nasturium seeds.");
			}
	}
	
	public static void plantRoseC(final Player player, final int configId) {
		if (player.getInventory().containsItem(5097, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
					player.farmingStatusK = 2;
					player.farmStatus();
					player.setNextAnimation(new Animation(2291));
					player.getPackets().sendConfigByFile(configId, 13);
					player.getInventory().deleteItem(5097, 1);
				} else if (loop == 150) {
					player.getPackets().sendConfigByFile(configId, 15);
				} else if (loop == 300) {	
					player.getPackets().sendConfigByFile(configId, 17);
					player.out("<col=FF0000>[Notice] Your Rosemary have fully grown at Catherby.</col>");
					player.canHarvestFlowerCA = true;
					player.farmingStatusK = 3;
				}
					loop++;
					}
				}, 0, 1);
			} else {
				player.out("You'll need some Rosemary seeds.");
			}
	}
}
