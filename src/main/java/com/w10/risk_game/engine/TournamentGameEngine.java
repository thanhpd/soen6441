package com.w10.risk_game.engine;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.common.base.Joiner;
import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.exceptions.ApplicationException;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.phases.PreLoadPhase;
import com.w10.risk_game.utils.CommandInterpreter;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.TournamentCommandInterpreter;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The GameEngine class is responsible for managing the game flow, handling user
 * input, and executing commands in the Risk game.
 *
 * @author Omnia Alam
 */
public class TournamentGameEngine {

	private final GamePlayController d_gamePlayController;
	private final MapEditorController d_mapEditorController;

	public static Phase Phase;
	public static String l_mainCommand = "tournament -M euprope.map test.map -D";
	ArrayList<String> l_listofMaps= new ArrayList<>();
	ArrayList<String> l_listofPlayers= new ArrayList<>();

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
	public TournamentGameEngine() {
		//Arraylist of gamemaps
		//Arraylist of players
		this.d_mapEditorController = new MapEditorController();
		this.d_gamePlayController = new GamePlayController(d_mapEditorController);
	}

	/**
	 * The start() function is the main loop of the game engine, where it handles
	 * user input and executes the corresponding commands based on the current game
	 * phase.
	 */
	public void start() {
		Player l_player;

			try {
				// Display a user input request
				Logger.log(Constants.USER_INPUT_REQUEST);

				// Create a Scanner to read the input from the user
				Scanner l_scanner = new Scanner(System.in);

				// Read the user's input and log the command that was entered
				l_mainCommand = l_scanner.nextLine();
				Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + l_mainCommand);

				// Get the main command and argument list from the entered command
				String[] l_argList = TournamentCommandInterpreter.GetArgumentList(l_mainCommand);
				l_listofMaps= TournamentCommandInterpreter.getListofMaps(l_argList);
				l_listofPlayers=TournamentCommandInterpreter.getListofPlayers(l_argList);
				Logger.log("");
				
			} catch (Exception e) {
				Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
				Logger.log(e.getMessage());
				Logger.log("");
			}
		}
	
/**
	 * The function takes a command as input and returns the main command by
	 * splitting it on spaces.
	 *
	 * @param p_command
	 *            The parameter `p_command` is a string that represents a command
	 *            input from the user.
	 *
	 * @throws ApplicationException
	 *             If the user enters an invalid command, show 'Please enter a valid
	 *             command!' to the user
	 *
	 * @return The method is returning the main command from the given input
	 *         command.
	 */
	public static String GetMainCommand(String p_command) throws ApplicationException {
		if (p_command.isBlank())
			throw new ApplicationException(Constants.USER_INPUT_ERROR_COMMAND_EMPTY);
		return p_command.split(Constants.REGEX_SPLIT_ON_SPACE)[0];
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
