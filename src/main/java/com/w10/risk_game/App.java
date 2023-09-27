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
	 *            - passed in arguments for the application. The code is the main
	 *            method of the application. It initializes a GameEngine object and
	 *            starts a loop that prompts the user for input and performs
	 *            different actions based on the input.
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
				// Map editor phase
				case Constants.USER_INPUT_COMMAND_LOADMAP :
					try {
						System.out.println(Constants.CLI_LOAD_MAP + l_splitStr[1]);
						l_GameEngine.loadMap(l_splitStr[1]);

					} catch (Exception e) {
						System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
					}
					break;
				case Constants.USER_INPUT_COMMAND_SAVEMAP :
					l_GameEngine.getGameMap().saveMap(l_splitStr[1]);
					break;
				case Constants.USER_INPUT_COMMAND_SHOWMAP :
					System.out.println(Constants.CLI_SHOW_MAP);
					l_GameEngine.showMap();
					break;
				case Constants.USER_INPUT_COMMAND_EDITMAP :
					l_GameEngine.editMap();
					break;
				case Constants.USER_INPUT_COMMAND_EDIT_CONTINENT :
					if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
						l_GameEngine.addContinent(Integer.parseInt(l_splitStr[2]), l_splitStr[3].toString());
					} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
						l_GameEngine.removeContinent(Integer.parseInt(l_splitStr[2]));
					}
					break;
				case Constants.USER_INPUT_COMMAND_EDIT_COUNTRY :
					if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
						l_GameEngine.addCountry(Integer.parseInt(l_splitStr[2]), l_splitStr[3].toString(),
								Integer.parseInt(l_splitStr[4]));
					} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
						l_GameEngine.removeCountry(Integer.parseInt(l_splitStr[2]));
					}
					break;
				case Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR :
					if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
						l_GameEngine.addNeighbor(Integer.parseInt(l_splitStr[2]), Integer.parseInt(l_splitStr[4]));
					} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
						l_GameEngine.removeNeighbor(Integer.parseInt(l_splitStr[2]), Integer.parseInt(l_splitStr[4]));
					}
					break;

				// Gameplay: Start up phase
				case Constants.USER_INPUT_COMMAND_GAMEPLAYER :
					try {
						if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							System.out.println(Constants.CLI_GAME_PLAYER_ADD + l_splitStr[2].toString());

							l_GameEngine.createPlayer(l_splitStr[2].toString());
						} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							System.out.println(Constants.CLI_GAME_PLAYER_REMOVE + l_splitStr[2].toString());
							l_GameEngine.removePlayer(l_splitStr[2].toString());
						} else if (l_splitStr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_SHOW_ALL)) {
							l_GameEngine.showAllPlayers();
						}
					} catch (Exception e) {
						System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
					}
					break;
				case Constants.USER_INPUT_COMMAND_ASSIGN_COUNTRIES :
					System.out.println(Constants.CLI_ASSIGN_COUNTRIES);
					l_GameEngine.assignCountries();
					break;

				// Others
				case Constants.USER_INPUT_COMMAND_QUIT :
					l_scanner.close();
					l_exit = true;
					break;
				default :
					System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
			}
			if (l_exit)
				break;
			System.out.println();
		}
	}
}
