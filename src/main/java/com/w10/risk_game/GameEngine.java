package com.w10.risk_game;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.common.base.Joiner;
import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.phases.PreLoadPhase;
import com.w10.risk_game.models.strategies.BenevolentPlayerStrategy;
import com.w10.risk_game.utils.CommandInterpreter;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The GameEngine class is responsible for managing the game flow, handling user
 * input, and executing commands in the Risk game.
 *
 * @author Sherwyn Dsouza
 */
public class GameEngine {

	private final GamePlayController d_gamePlayController;
	private final MapEditorController d_mapEditorController;

	public static Phase Phase;
	public static String Command = "";

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * The function sets the phase of an object and then prints the available
	 * commands for that phase.
	 *
	 * @param p_phase
	 *            The parameter "p_phase" is of type "Phase".
	 */
	public static void SetPhase(Phase p_phase) {
		Phase = p_phase;
		Phase.printAvailableCommand();
	}

	/**
	 * The `GameEngine` constructor initializes a new instance of the
	 * `MapEditorController` class and `GamePlayController` class.
	 */
	public GameEngine() {
		this.d_mapEditorController = new MapEditorController();
		this.d_gamePlayController = new GamePlayController(d_mapEditorController);
	}

	/**
	 * The start() function is the main loop of the game engine, where it handles
	 * user input and executes the corresponding commands based on the current game
	 * phase.
	 */
	public void start() {
		SetPhase(new PreLoadPhase(this));
		boolean l_exit = false;
		Player l_player;

		while (!l_exit) {

			// Check if in issue order phase
			if (Phase.getPhaseName().equalsIgnoreCase(Constants.GAME_ENGINE_ISSUE_ORDER_PHASE_STRING)) {
				if (!d_gamePlayController.checkIfOrdersCanBeIssued()) {
					if (d_gamePlayController.checkIfOrdersCanBeExecuted()) {
						Phase.next();
						Logger.log(Constants.GAME_ENGINE_EXECUTING_ORDERS);
						Phase.executeAllPlayerOrders();

						// Check if the game is over after executing the orders
						if (this.d_gamePlayController.checkIfGameIsOver()) {
							Logger.log(Constants.GAME_ENGINE_GAME_OVER + this.d_gamePlayController.getWinner()
									+ Constants.GAME_ENGINE_END_GAME);
							break;
						}
						Phase.next();
						Phase.assignPlayerReinforcements();
					} else
						continue;
				}
				l_player = this.d_gamePlayController.getCurrentPlayer();
				Logger.log(Constants.CLI_ISSUE_ORDER_PLAYER + l_player.getName() + ":");

				// If non-human strategy then issue order
				if (!l_player.getStrategy().getStrategyName()
						.equals(Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_HUMAN)) {
					l_player.issueOrder();
					continue;
				}

				Logger.log(MessageFormat.format(Constants.GAME_ENGINE_ISSUE_ORDER_NUMBER_OF_ARMIES,
						l_player.getLeftoverArmies()));

				// Display Player Cards
				if (!l_player.getPlayerCards().isEmpty()) {
					Logger.log(MessageFormat.format(Constants.SHOW_PLAYER_CARDS,
							Joiner.on(", ").join(l_player.getPlayerCards())));
				} else {
					Logger.log(Constants.SHOW_PLAYER_CARDS_EMPTY);
				}
				Logger.log("");
			}

			try {
				// Display a user input request
				Logger.log(Constants.USER_INPUT_REQUEST);

				// Create a Scanner to read the input from the user
				Scanner l_scanner = new Scanner(System.in);

				// Read the user's input and log the command that was entered
				Command = l_scanner.nextLine();
				Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + Command);

				// Get the main command and argument list from the entered command
				String l_mainCommand = CommandInterpreter.GetMainCommand(Command);
				String[] l_argList = CommandInterpreter.GetArgumentList(Command);

				// Get a list of options for each argument in the command
				ArrayList<ArrayList<String>> l_listOfOptions = CommandInterpreter.GetCommandOptions(Command);

				// Check for the validity of the provided argument options based on the main
				// command
				CommandInterpreter.CheckValidArgumentOptions(l_argList, l_mainCommand, l_listOfOptions);

				switch (l_mainCommand) {
					// Map editor Phase commands
					case Constants.USER_INPUT_COMMAND_LOADMAP :
						String[] l_mapName = l_argList[1].split("/");
						Logger.log(Constants.CLI_LOAD_MAP + l_mapName[l_mapName.length - 1]);
						Phase.loadMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_SAVEMAP :
						Phase.saveMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_SHOWMAP :
						Logger.log(Constants.CLI_SHOW_MAP);
						Phase.showMap();
						break;
					case Constants.USER_INPUT_COMMAND_EDITMAP :
						Phase.editMap(l_argList[1]);
						break;
					case Constants.USER_INPUT_COMMAND_OPTION_NEXTPHASE :
						Phase.nextPhase();
						break;
					case Constants.USER_INPUT_COMMAND_EDIT_CONTINENT :
						// Process all provided command options by a loop
						for (ArrayList<String> l_options : l_listOfOptions) {
							// Log a message for the current iteration
							this.displayLoopIterationMessage(l_options);
							// Get the specific option name for this iteration
							String optionName = l_options.get(0);
							switch (optionName) {
								case Constants.USER_INPUT_COMMAND_OPTION_ADD :
									Phase.addContinent(l_options.get(1), Integer.parseInt(l_options.get(2)));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_REMOVE :
									Phase.removeContinent(l_options.get(1));
									break;
							}
						}
						break;

					case Constants.USER_INPUT_COMMAND_EDIT_COUNTRY :
						// Process all provided command options by a loop
						for (ArrayList<String> l_options : l_listOfOptions) {
							this.displayLoopIterationMessage(l_options);
							// Get the specific option name for this iteration
							String optionName = l_options.get(0);
							switch (optionName) {
								case Constants.USER_INPUT_COMMAND_OPTION_ADD :
									Phase.addCountry(Integer.parseInt(l_options.get(1)), l_options.get(2),
											l_options.get(3));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_REMOVE :
									Phase.removeCountry(Integer.parseInt(l_options.get(1)));
									break;
							}
						}
						break;

					case Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR :
						// Process all provided command options by a loop
						for (ArrayList<String> l_options : l_listOfOptions) {
							this.displayLoopIterationMessage(l_options);
							// Get the specific option name for this iteration
							String optionName = l_options.get(0);
							switch (optionName) {
								case Constants.USER_INPUT_COMMAND_OPTION_ADD :
									Phase.addNeighbor(Integer.parseInt(l_options.get(1)),
											Integer.parseInt(l_options.get(2)));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_REMOVE :
									Phase.removeNeighbor(Integer.parseInt(l_options.get(1)),
											Integer.parseInt(l_options.get(2)));
									break;
							}
						}
						break;

					case Constants.USER_INPUT_COMMAND_VALIDATEMAP :
						this.d_mapEditorController.checkIfMapIsValid();
						break;

					// Gameplay: Start up Phase commands
					case Constants.USER_INPUT_COMMAND_GAMEPLAYER :
						// Process all provided command options by a loop
						for (ArrayList<String> l_options : l_listOfOptions) {
							this.displayLoopIterationMessage(l_options);
							// Get the specific option name for this iteration
							String optionName = l_options.get(0);
							switch (optionName) {
								case Constants.USER_INPUT_COMMAND_OPTION_ADD :
									Phase.createPlayer(l_options.get(1), l_options.get(2));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_REMOVE :
									Phase.removePlayer(l_options.get(1));
									break;
								case Constants.USER_INPUT_COMMAND_OPTION_SHOW_ALL :
									Phase.showAllPlayers();
									break;
							}
						}
						break;

					case Constants.USER_INPUT_COMMAND_ASSIGN_COUNTRIES :
						Logger.log(Constants.CLI_ASSIGN_COUNTRIES);
						if (Phase.assignCountries())
							Phase.assignPlayerReinforcements();
						break;

					// Issue Order Commands
					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY :

					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT :

					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE :

					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE :

					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB :

					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE :

					case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT :
						Phase.issuePlayerOrder();
						if (Command.equals(Constants.USER_INPUT_COMMAND_QUIT))
							l_exit = true;
						break;

					// Other commands
					case Constants.USER_INPUT_COMMAND_QUIT :
						l_scanner.close();
						l_exit = true;
						break;
					default :
						Logger.log(Constants.USER_INPUT_ERROR_COMMAND_INVALID);
				}
				Logger.log("");
				if (l_exit) {
					break;
				}
			} catch (Exception e) {
				Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
				Logger.log(e.getMessage());
				Logger.log("");
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
		Logger.log(MessageFormat.format(Constants.CLI_ITERATION_OPTION, p_options.get(0),
				p_options.subList(1, p_options.size())));
	}

	/**
	 * The function returns the game engine controller object.
	 *
	 * @return The method is returning an object of type GamePlayController.
	 */
	public GamePlayController getGame() {
		return this.d_gamePlayController;
	}

	/**
	 * The function returns the MapEditorController object.
	 *
	 * @return The method is returning an object of type MapEditorController.
	 */
	public MapEditorController getMapEditorController() {
		return this.d_mapEditorController;
	}
}
