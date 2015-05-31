package com.rs.game.player.content;

import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.player.Player;

import java.util.Random;
import java.util.TimerTask;
/**
 * @Author: Apache Ah64, Thanks Apache :)
 */
public class TriviaBot {
	
	private static String questions [][] = {
		{"How many altars are there at ::altars?", "3"},
		{"What is the maximum total level you can achieve?", "2496"},
		{"In what year was RuneScape established?", "2001"},
		{"How many portals are located at Clan Wars?", "3"},
		{"What is maximum combat level in Validus?", "138"},
		{"Is a tomato a fruit or a vegetable?", "Fruit"},
		{"How many legs does a spider have?", "8"},
		{"How many barrows brothers are on each hill?", "5"},
		{"How many Squeal of Fortune item options are there", "13"},
		{"How many Bankers are there at ::home", "3"},
		{"What is the first ancient spell?", "smoke rush"},
		{"What is the most powerful curse?", "turmoil"},
		{"How much of a percentage does a dragon dagger special require?", "25%"},
		{"What color does the Donator sign have?", "Green"},
		{"What's the hardest skill to get maxed in?", "dungeoneering"},
		{"What is the best free to play armour?", "Rune"},
		{"Where do you get Zeals at?", "Soul wars"},
		{"How many bank booths are located at ::home?", "3"},
		{"How many cannon balls do u need to refill a cannon?", "30"},	
		{"Who is the owner?", "max"},
			{"Who is the co-owner?", "plasticboy22"},		
		{"How much experience is required to achieve 120 Dungeoneering", "104m"},
		{"What do you receive when a fire disappears?", "Ashes"},		
		{"What is the name of the new firecape?", "TokHaar-Kal"}
	};
	
	public static int questionid = -1;
	public static int round = 0;
	public static boolean victory = false;

	public TriviaBot() {
		//TODO
	}
	
	public static void Run() {
		int rand = RandomQuestion();
		questionid = rand;
		victory = false;
		for(Player participant : World.getPlayers()) {
			if(participant == null)
				continue;
				participant.getPackets().sendGameMessage("<col=56A5EC>[Trivia]"+questions[rand][0]+"</col>");
		}
	}
	
	public static void sendRoundWinner(String winner, Player player) {
		for(Player participant : World.getPlayers()) {
			if(participant == null)
				continue;
				victory = true;
				player.TriviaPoints+= 1;
				participant.getPackets().sendGameMessage("<col=56A5EC>[Trivia] "+winner+" gave the correct answer and is now on "+player.TriviaPoints+" Trivia Points.</col>");
		}
	}
	
	public static void verifyAnswer(final Player player, String answer) {
		if(victory) {
			player.getPackets().sendGameMessage("That round has already been won, wait for the next round.");
		} else if(questions[questionid][1].equalsIgnoreCase(answer)) {
			round++;
			sendRoundWinner(player.getDisplayName(), player);
		} else {
			player.getPackets().sendGameMessage("That answer wasn't correct, please try it again.");
		}
	}
	
	public static int RandomQuestion() {
		int random = 0;
		Random rand = new Random();
		random = rand.nextInt(questions.length);
		return random;
	}
	
	public static boolean TriviaArea(final Player participant) {
		if(participant.getX() >= 2630 && participant.getX() <= 2660 && participant.getY() >= 9377 && participant.getY() <= 9400) {
			return true;
		}
		return false;
	}
}
