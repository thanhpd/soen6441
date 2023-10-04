package com.w10.risk_game.views;

import java.util.Scanner;

import com.w10.risk_game.controllers.GameEngine;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.CommandInterpreter;
import com.w10.risk_game.utils.Constants;

/**
 * The GameUI class handles the command line user interface for the game,
 * including the map editor, start-up phase and gameplay phase.
 *
 * @author Sherwyn Dsouza
 */
public class GameUI {

	private GameEngine d_gameEngine;
	private boolean d_startGamePhase;

	public static String Command = "";

	/**
	 * The `GameUI` constructor initializes a new instance of the `GameEngine` class
	 * and sets the `d_gameEngine` variable to refer to it. It also sets the
	 * `d_startGamePhase` variable to `false`.
	 */
	public GameUI() {
		this.d_gameEngine = new GameEngine();
		this.d_startGamePhase = false;
	}

	/**
	 * The function "runStartUpPhase" handles the start-up phase of a game, taking
	 * user input and executing corresponding commands through the Game Engine.
	 *
	 */
	public void runStartUpPhase() {
		System.out.println(Constants.STARTUP_PHASE_ENTRY_STRING);
		boolean l_exit = false;

		while (!l_exit) {
			try {
				System.out.print(Constants.USER_INPUT_REQUEST);
				Scanner l_scanner = new Scanner(System.in);
				Command = l_scanner.nextLine();
				String l_mainCommand = CommandInterpreter.GetMainCommand(Command);
				String[] l_argList = CommandInterpreter.GetArgumentList(Command);

				switch (l_mainCommand) {
					// Map editor phase
					case Constants.USER_INPUT_COMMAND_LOADMAP :
						String[] l_mapName = l_argList[1].split("/");
						System.out.println(Constants.CLI_LOAD_MAP + l_mapName[l_mapName.length - 1]);
						this.d_gameEngine.loadMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_SAVEMAP :
						this.d_gameEngine.saveMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_SHOWMAP :
						System.out.println(Constants.CLI_SHOW_MAP);
						this.d_gameEngine.showMap();
						break;
					case Constants.USER_INPUT_COMMAND_EDITMAP :
						this.d_gameEngine.editMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_CONTINENT :
						if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							this.d_gameEngine.addContinent(l_argList[2].toString(), Integer.parseInt(l_argList[3]));
						} else if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							this.d_gameEngine.removeContinent(l_argList[2]);
						}
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_COUNTRY :
						if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							this.d_gameEngine.addCountry(Integer.parseInt(l_argList[2]), l_argList[3].toString(),
									l_argList[4]);
						} else if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							this.d_gameEngine.removeCountry(Integer.parseInt(l_argList[2]));
						}
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR :
						if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							this.d_gameEngine.addNeighbor(Integer.parseInt(l_argList[2]),
									Integer.parseInt(l_argList[3]));
						} else if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							this.d_gameEngine.removeNeighbor(Integer.parseInt(l_argList[2]),
									Integer.parseInt(l_argList[3]));
						}
						break;
					case Constants.USER_INPUT_COMMAND_VALIDATEMAP :
						this.d_gameEngine.checkIfMapIsValid();
						break;

					// Gameplay: Start up phase
					case Constants.USER_INPUT_COMMAND_GAMEPLAYER :
						if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_ADD)) {
							this.d_gameEngine.createPlayer(l_argList[2].toString());
						} else if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_REMOVE)) {
							this.d_gameEngine.removePlayer(l_argList[2].toString());
						} else if (l_argList[1].equals(Constants.USER_INPUT_COMMAND_OPTION_SHOW_ALL)) {
							this.d_gameEngine.showAllPlayers();
						}
						break;
					case Constants.USER_INPUT_COMMAND_ASSIGN_COUNTRIES :
						System.out.println(Constants.CLI_ASSIGN_COUNTRIES);
						if (this.d_gameEngine.assignCountries() && this.d_gameEngine.checkIfGameCanBegin()) {
							l_exit = true;
							this.d_startGamePhase = true;
						}
						break;

					// Others
					case Constants.USER_INPUT_COMMAND_QUIT :
						l_scanner.close();
						l_exit = true;
						break;
					default :
						System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
				}
				System.out.println();
				if (l_exit) {
					if (this.d_startGamePhase)
						this.runGamePlayPhase();
					break;
				}
			} catch (Exception e) {
				System.out.println(Constants.USER_INPUT_SOME_ERROR_OCCURRED);
			}
		}
	}

	/**
	 * The function "runGamePlayPhase" runs the gameplay phase of a game, taking
	 * user input and executing commands accordingly through the Game Engine.
	 *
	 */
	public void runGamePlayPhase() {
		System.out.println(Constants.GAMEPLAY_PHASE_ENTRY_STRING);
		boolean l_exit = false;
		Player l_player;

		while (!l_exit) {
			if (!d_gameEngine.checkIfOrdersCanBeIssued()) {
				if (d_gameEngine.checkIfOrdersCanBeExecuted()) {
					System.out.println(Constants.GAME_ENGINE_EXECUTING_ORDERS);
					d_gameEngine.executePlayerOrders();
				} else
					continue;
			}
			l_player = d_gameEngine.getCurrentPlayer();
			System.out.println(Constants.CLI_ISSUE_ORDER_PLAYER + l_player.getName() + ":");
			System.out.format(Constants.GAME_ENGINE_ISSUE_ORDER_NUMBER_OF_ARMIES, l_player.getLeftoverArmies());
			System.out.println();
			try {
				System.out.print(Constants.USER_INPUT_REQUEST);
				Scanner l_scanner = new Scanner(System.in);
				Command = l_scanner.nextLine();
				String l_mainCommand = CommandInterpreter.GetMainCommand(Command);
				String[] l_argList = CommandInterpreter.GetArgumentList(Command);

				switch (l_mainCommand) {
					// Show Map Command
					case Constants.USER_INPUT_COMMAND_SHOWMAP :
						System.out.println(Constants.CLI_SHOW_MAP);
						this.d_gameEngine.showMap();
						break;
					// Issue Order Command
					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY :
						d_gameEngine.issuePlayerOrder();
						break;
					// Others
					case Constants.USER_INPUT_COMMAND_QUIT :
						l_scanner.close();
						l_exit = true;
						break;
					default :
						System.out.println(Constants.USER_INPUT_COMMAND_INVALID);
				}
				if (l_exit) {
					break;
				}
				System.out.println();
			} catch (Exception e) {
				System.out.println(Constants.USER_INPUT_SOME_ERROR_OCCURRED);
			}
		}
	}
}
