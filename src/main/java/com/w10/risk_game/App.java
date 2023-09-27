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
	 *             passed in arguments for the application
	 */
	public static void main(String[] args) {
		GameEngine l_GameEngine = new GameEngine();
		System.out.println(Constants.STARTUP_PHASE_ENTRY_STRING);
		boolean l_exit = false;
		while (true) {
			System.out.print(Constants.USER_INPUT_REQUEST);
			Scanner l_scanner = new Scanner(System.in);
			String l_choice = l_scanner.nextLine();
			String[] l_splitStr = l_choice.trim().split(Constants.REGEX_SPLIT_ON_SPACE);

			switch (l_splitStr[0]) {
				case Constants.USER_INPUT_COMMAND_LOADMAP:
					try {
						System.out.println(Constants.CLI_LOAD_MAP + l_splitStr[1]);
						l_GameEngine.loadMap(l_splitStr[1]);

					} catch (Exception e) {
						System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
					}
					break;
				case Constants.USER_INPUT_COMMAND_SHOWMAP:
					System.out.println(Constants.CLI_SHOW_MAP);
					l_GameEngine.showMap();

					break;
				case Constants.USER_INPUT_COMMAND_GAMEPLAYER:
					try {
						if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_GAMEPLAYER_OPTION_ADD)) {
							l_GameEngine.createPlayer(l_splitStr[2].toString());
							System.out.println(Constants.CLI_GAME_PLAYER_ADD + l_splitStr[2].toString());
						} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_GAMEPLAYER_OPTION_REMOVE)) {
							l_GameEngine.removePlayer(l_splitStr[2].toString());
							System.out.println(Constants.CLI_GAME_PLAYER_REMOVE + l_splitStr[2].toString());

						} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_GAMEPLAYER_OPTION_SHOW_ALL)) {
							l_GameEngine.showAllPlayers();
						}
					} catch (Exception e) {
						System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
					}
					break;
				case Constants.USER_INPUT_COMMAND_ASSIGN_COUNTRIES:
					System.out.println(Constants.CLI_ASSIGN_COUNTRIES);
					l_GameEngine.assignCountries();
					break;
				case Constants.USER_INPUT_COMMAND_QUIT:
					l_scanner.close();
					l_exit = true;
					break;
				default:
					System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
			}
			if (l_exit)
				break;
			System.out.println();
		}
	}
}
