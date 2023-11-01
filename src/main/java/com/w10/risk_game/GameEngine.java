package com.w10.risk_game;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import com.w10.risk_game.controllers.RiskGame;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.phases.PreLoad;
import com.w10.risk_game.utils.CommandInterpreter;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The GameUI class handles the command line user interface for the game,
 * including the map editor, start-up phase and gameplay phase.
 *
 */
public class GameEngine {

	private final RiskGame d_riskGame;
	private boolean d_startGamePhase;
	private Formatter d_formatter;

	public static String Command = "";
	private final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	private Phase phase;

	public void setPhase(Phase phase) {

		this.phase = phase;
		phase.printAvailableCommand();
	}

	/**
	 * The `GameUI` constructor initializes a new instance of the `GameEngine` class
	 * and sets the `d_gameEngine` variable to refer to it. It also sets the
	 * `d_startGamePhase` variable to `false`.
	 */
	public GameEngine() {
		this.d_riskGame = new RiskGame();
		this.d_startGamePhase = false;
	}

	/**
	 * The function "runStartUpPhase" handles the start-up phase of a game, taking
	 * user input and executing corresponding commands through the Game Engine.
	 *
	 */
	public void start() {
		setPhase(new PreLoad(this));

		// while loop
		// if load map command
		// 1. sePhaseLoadMpa
		// 2. this.phase.loadMap();
		// d_logger.log(Constants.STARTUP_PHASE_ENTRY_STRING);
		boolean l_exit = false;

		while (!l_exit) {
			try {
				d_logger.log(Constants.USER_INPUT_REQUEST);
				Scanner l_scanner = new Scanner(System.in);
				Command = l_scanner.nextLine();
				d_logger.log(Constants.USER_INPUT_COMMAND_ENTERED + Command);
				String l_mainCommand = CommandInterpreter.GetMainCommand(Command);
				String[] l_argList = CommandInterpreter.GetArgumentList(Command);
				ArrayList<ArrayList<String>> l_listOfOptions = CommandInterpreter.GetCommandOptions(Command);

				CommandInterpreter.CheckValidArgumentOptions(l_argList, l_mainCommand, l_listOfOptions);

				switch (l_mainCommand) {
					// Map editor phase
					case Constants.USER_INPUT_COMMAND_LOADMAP :
						String[] l_mapName = l_argList[1].split("/");
						d_logger.log(Constants.CLI_LOAD_MAP + l_mapName[l_mapName.length - 1]);
						// this.d_riskGame.loadMap(l_argList[1]);
						// setPhase(new PreLoad(this));
						this.phase.loadMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_SAVEMAP :
						this.phase.saveMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_SHOWMAP :
						d_logger.log(Constants.CLI_SHOW_MAP);
						this.phase.showMap();
						break;
					case Constants.USER_INPUT_COMMAND_EDITMAP :
						this.phase.editMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_CONTINENT :
						// Process all provided command options by a loop
						for (ArrayList<String> l_options : l_listOfOptions) {
							this.displayLoopIterationMessage(l_options);
							String optionName = l_options.get(0);
							switch (optionName) {
								case Constants.USER_INPUT_COMMAND_OPTION_ADD :
									this.phase.addContinent(l_options.get(1), Integer.parseInt(l_options.get(2)));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_REMOVE :
									this.phase.removeContinent(l_options.get(1));
									break;
							}
						}
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_COUNTRY :
						// Process all provided command options by a loop
						for (ArrayList<String> l_options : l_listOfOptions) {
							this.displayLoopIterationMessage(l_options);
							String optionName = l_options.get(0);
							switch (optionName) {
								case Constants.USER_INPUT_COMMAND_OPTION_ADD :
									this.phase.addCountry(Integer.parseInt(l_options.get(1)), l_options.get(2),
											l_options.get(3));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_REMOVE :
									this.phase.removeCountry(Integer.parseInt(l_options.get(1)));
									break;
							}
						}
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR :
						// Process all provided command options by a loop
						for (ArrayList<String> l_options : l_listOfOptions) {
							this.displayLoopIterationMessage(l_options);
							String optionName = l_options.get(0);
							switch (optionName) {
								case Constants.USER_INPUT_COMMAND_OPTION_ADD :
									this.phase.addNeighbor(Integer.parseInt(l_options.get(1)),
											Integer.parseInt(l_options.get(2)));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_REMOVE :
									this.phase.removeNeighbor(Integer.parseInt(l_options.get(1)),
											Integer.parseInt(l_options.get(2)));
									break;
							}
						}
						break;
					case Constants.USER_INPUT_COMMAND_VALIDATEMAP :
						this.phase.checkIfMapIsValid();
						break;

					// Gameplay: Start up phase
					case Constants.USER_INPUT_COMMAND_GAMEPLAYER :
						// Process all provided command options by a loop
						for (ArrayList<String> l_options : l_listOfOptions) {
							this.displayLoopIterationMessage(l_options);
							String optionName = l_options.get(0);
							switch (optionName) {
								case Constants.USER_INPUT_COMMAND_OPTION_ADD :
									this.phase.createPlayer(l_options.get(1));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_REMOVE :
									this.phase.removePlayer(l_options.get(1));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_SHOW_ALL :
									this.phase.showAllPlayers();
									break;
							}
						}
						break;
					case Constants.USER_INPUT_COMMAND_ASSIGN_COUNTRIES :
						d_logger.log(Constants.CLI_ASSIGN_COUNTRIES);
						if (this.phase.assignCountries() && this.phase.checkIfGameCanBegin()) {
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
						d_logger.log(Constants.USER_INPUT_ERROR_COMMAND_INVALID);
				}
				d_logger.log("");
				if (l_exit) {
					if (this.d_startGamePhase)
						this.runGamePlayPhase();
					break;
				}
			} catch (Exception e) {
				d_logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
				d_logger.log(e.getMessage());
				d_logger.log("");
			}
		}
	}

	/**
	 * The function "runGamePlayPhase" runs the gameplay phase of a game, taking
	 * user input and executing commands accordingly through the Game Engine.
	 *
	 */
	public void runGamePlayPhase() {
		d_logger.log(Constants.GAMEPLAY_PHASE_ENTRY_STRING);
		boolean l_exit = false;
		Player l_player;

		while (!l_exit) {
			if (!d_riskGame.checkIfOrdersCanBeIssued()) {
				if (d_riskGame.checkIfOrdersCanBeExecuted()) {
					d_logger.log(Constants.GAME_ENGINE_EXECUTING_ORDERS);
					d_riskGame.executePlayerOrders();
				} else
					continue;
			}
			l_player = d_riskGame.getCurrentPlayer();
			d_logger.log(Constants.CLI_ISSUE_ORDER_PLAYER + l_player.getName() + ":");

			this.d_formatter = new Formatter();
			this.d_formatter.format(Constants.GAME_ENGINE_ISSUE_ORDER_NUMBER_OF_ARMIES, l_player.getLeftoverArmies());
			d_logger.log(this.d_formatter.toString());
			this.d_formatter.close();
			d_logger.log("");

			try {
				d_logger.log(Constants.USER_INPUT_REQUEST);
				Scanner l_scanner = new Scanner(System.in);
				Command = l_scanner.nextLine();
				d_logger.log(Constants.USER_INPUT_COMMAND_ENTERED + Command);

				String l_mainCommand = CommandInterpreter.GetMainCommand(Command);
				String[] l_argList = CommandInterpreter.GetArgumentList(Command);

				CommandInterpreter.CheckValidArgumentOptions(l_argList, l_mainCommand, null);

				switch (l_mainCommand) {
					// Show Map Command
					case Constants.USER_INPUT_COMMAND_SHOWMAP :
						System.out.println(Constants.CLI_SHOW_MAP);
						this.phase.showMap();
						break;
					// Issue Order Command
					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY :
						d_riskGame.issuePlayerOrder();
						break;
					// Others
					case Constants.USER_INPUT_COMMAND_QUIT :
						l_scanner.close();
						l_exit = true;
						break;
					default :
						d_logger.log(Constants.USER_INPUT_ERROR_COMMAND_INVALID);
				}
				if (l_exit) {
					break;
				}
				d_logger.log("");
			} catch (Exception e) {
				d_logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
				d_logger.log(e.getMessage());
				d_logger.log("");
			}
		}
	}

	/**
	 * The function displays a message with loop iteration options.
	 *
	 * @param p_options
	 *            An ArrayList of Strings containing the options for the loop
	 *            iteration.
	 */
	private void displayLoopIterationMessage(ArrayList<String> p_options) {
		this.d_formatter = new Formatter();
		this.d_formatter.format(Constants.CLI_ITERATION_OPTION, p_options.get(0),
				p_options.subList(1, p_options.size()));
		d_logger.log(this.d_formatter.toString());
		this.d_formatter.close();
	}

	public RiskGame getGame() {
		return this.d_riskGame;
	}
}
