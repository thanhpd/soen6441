package com.w10.risk_game;

import java.util.Scanner;

import com.w10.risk_game.models.GameEngine;

/**
 * Initialize the Application
 */
public class App {
	/**
	 * @param args passed in arguments for the application
	 */
	public static void main(String[] args) {
		GameEngine l_GameEngine = new GameEngine();
		while (true) {
			Scanner l_scanner = new Scanner(System.in);
			String l_choice = l_scanner.nextLine();
			if (l_choice.toLowerCase().contains("loadmap")) {
				String[] l_splitStr = l_choice.trim().split("\\s+");
				l_GameEngine.loadMap(l_splitStr[1]);
			} else if (l_choice.toLowerCase().contains("showmap")) {
				l_GameEngine.showMap();
			} else if (l_choice.toLowerCase().contains("gameplayer")) {
				String[] l_splitStr = l_choice.trim().split("\\s+");
				if (l_splitStr[1].equals("-add")) {
					l_GameEngine.createPlayer(l_splitStr[2].toString());
				} else if (l_splitStr[1].equals("-remove")) {
					l_GameEngine.removePlayer(l_splitStr[2].toString());
				}
			} else if (l_choice.toLowerCase().contains("quit")) {
				l_scanner.close();
				break;
			}
		}
	}
}

// haha test failure
