package com.w10.risk_game;

import java.util.Scanner;

import com.w10.risk_game.models.GameEngine;
import com.w10.risk_game.utils.Constants;

/**
 * Initialize the Application
 */
public class App {
	/**
	 * @param args
	 *            passed in arguments for the application
	 */
	public static void main(String[] args) {
		GameEngine l_GameEngine = new GameEngine();
		System.out.println(Constants.STARTUP_PHASE_ENTRY_STRING);
		while (true) {
			System.out.print(Constants.USER_INPUT_REQUEST);
			Scanner l_scanner = new Scanner(System.in);
			String l_choice = l_scanner.nextLine();
			if (l_choice.toLowerCase().contains(Constants.USER_INPUT_COMMAND_LOADMAP)) {
				String[] l_splitStr = l_choice.trim().split(Constants.REGEX_SPLIT_ON_SPACE);
				l_GameEngine.loadMap(l_splitStr[1]);
			} else if (l_choice.toLowerCase().contains(Constants.USER_INPUT_COMMAND_SHOWMAP)) {
				l_GameEngine.showMap();
			} else if (l_choice.toLowerCase().contains(Constants.USER_INPUT_COMMAND_GAMEPLAYER)) {
				String[] l_splitStr = l_choice.trim().split(Constants.REGEX_SPLIT_ON_SPACE);
				if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_GAMEPLAYER_OPTION_ADD)) {
					l_GameEngine.createPlayer(l_splitStr[2].toString());
				} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_GAMEPLAYER_OPTION_REMOVE)) {
					l_GameEngine.removePlayer(l_splitStr[2].toString());
				} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_GAMEPLAYER_OPTION_SHOW_ALL)) {
					l_GameEngine.showAllPlayers();
				}
			} else if (l_choice.toLowerCase().contains(Constants.USER_INPUT_COMMAND_ASSIGN_COUNTRIES)) {
				l_GameEngine.assignCountries();
			} else if (l_choice.toLowerCase().contains(Constants.USER_INPUT_COMMAND_QUIT)) {
				l_scanner.close();
				break;
			} else {
				System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
			}
			System.out.println();
		}
	}
}

// haha test failure
