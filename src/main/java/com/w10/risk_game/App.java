package com.w10.risk_game;

import java.util.Scanner;

import com.w10.risk_game.controllers.GameEngine;
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
		GameEngine l_gameEngine = new GameEngine();
		System.out.println(Constants.STARTUP_PHASE_ENTRY_STRING);
		boolean l_exit = false;
		while (true) {
			try {
				System.out.print(Constants.USER_INPUT_REQUEST);
				Scanner l_scanner = new Scanner(System.in);
				String l_choice = l_scanner.nextLine();
				String[] l_userInputArr = l_choice.trim().split(Constants.REGEX_SPLIT_ON_SPACE);

				switch (l_userInputArr[0]) {
					// Map editor phase
					case Constants.USER_INPUT_COMMAND_LOADMAP :
						System.out.println(Constants.CLI_LOAD_MAP + l_userInputArr[1]);
						l_gameEngine.loadMap(l_userInputArr[1]);
						break;
					case Constants.USER_INPUT_COMMAND_SAVEMAP :
						l_gameEngine.saveMap(l_userInputArr[1]);
						break;
					case Constants.USER_INPUT_COMMAND_SHOWMAP :
						System.out.println(Constants.CLI_SHOW_MAP);
						l_gameEngine.showMap();
						break;
					case Constants.USER_INPUT_COMMAND_EDITMAP :
						l_gameEngine.editMap(l_userInputArr[1]);
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_CONTINENT :
						if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							l_gameEngine.addContinent(l_userInputArr[2].toString(),
									Integer.parseInt(l_userInputArr[3]));
						} else if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							l_gameEngine.removeContinent(Integer.parseInt(l_userInputArr[2]));
						}
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_COUNTRY :
						if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							l_gameEngine.addCountry(Integer.parseInt(l_userInputArr[2]), l_userInputArr[3].toString(),
									Integer.parseInt(l_userInputArr[4]));
						} else if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							l_gameEngine.removeCountry(Integer.parseInt(l_userInputArr[2]));
						}
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR :
						if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							l_gameEngine.addNeighbor(Integer.parseInt(l_userInputArr[2]),
									Integer.parseInt(l_userInputArr[3]));
						} else if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							l_gameEngine.removeNeighbor(Integer.parseInt(l_userInputArr[2]),
									Integer.parseInt(l_userInputArr[3]));
						}
						break;
					case Constants.USER_INPUT_COMMAND_VALIDATEMAP :
						l_gameEngine.checkIfMapIsValid();
						break;

					// Gameplay: Start up phase
					case Constants.USER_INPUT_COMMAND_GAMEPLAYER :
						if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							l_gameEngine.createPlayer(l_userInputArr[2].toString());
						} else if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							l_gameEngine.removePlayer(l_userInputArr[2].toString());
						} else if (l_userInputArr[1].equals(Constants.USER_INPUT_COMMAND_OPTION_SHOW_ALL)) {
							l_gameEngine.showAllPlayers();
						}
						break;
					case Constants.USER_INPUT_COMMAND_ASSIGN_COUNTRIES :
						System.out.println(Constants.CLI_ASSIGN_COUNTRIES);
						l_gameEngine.assignCountries();
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
			} catch (Exception e) {
				System.out.println(Constants.USER_INPUT_SOME_ERROR_OCCURRED);
			}
		}
	}
}
