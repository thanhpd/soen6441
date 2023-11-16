package com.w10.risk_game.controllers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.common.base.Joiner;
import com.w10.risk_game.commands.Order;
import com.w10.risk_game.exceptions.ApplicationException;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.phases.PreLoadPhase;
import com.w10.risk_game.models.strategies.AggressivePlayerStrategy;
import com.w10.risk_game.models.strategies.BenevolentPlayerStrategy;
import com.w10.risk_game.models.strategies.CheaterPlayerStrategy;
import com.w10.risk_game.models.strategies.RandomPlayerStrategy;
import com.w10.risk_game.utils.CommandInterpreter;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The GameEngine class is responsible for managing the game flow, handling user
 * input, and executing commands in the Risk game.
 *
 * @author Omnia Alam
 */
public class TournamentController {

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


	public TournamentController() {
		this.d_mapEditorController = new MapEditorController();
		this.d_gamePlayController = new GamePlayController(d_mapEditorController);
	}

	/**
	 * The start() function is the main loop of the game engine, where it handles
	 * user input and executes the corresponding commands based on the current game
	 * phase.
	 */
	// public void start(Set<String> playerStrategiesName, Set<String> maps, int gamesCount, int maxTurns) {
		

	// 	for(var map : maps) { // for each map play gamesCount mach

	// 	for (int i = 1; i <= gamesCount; i++) {
	// 		var players =  CreatepLayers();
	// 		var winner = playGame(map, players, maxTurns);
	// 		// save winner for display purpose
	// 	}

	// 	}
	// }

	private Set<Player> createPlayers(Set<String> playerStrategyNames) {
		Set<Player> l_listofPlayers= new HashSet<>();
		for (String l_strategy : playerStrategyNames) {
			d_gamePlayController.createPlayer(l_strategy);
			Player l_player= d_gamePlayController.getPlayerDetails(l_strategy);
			switch(l_strategy){
				case "Random":
				l_player.setStrategy(new RandomPlayerStrategy(l_player));
				break;
				case "Cheater":
				l_player.setStrategy(new CheaterPlayerStrategy(l_player));
				break;
				case "Benevolent":
				l_player.setStrategy(new BenevolentPlayerStrategy(l_player));
				break;
				case "Aggressive":
				l_player.setStrategy(new AggressivePlayerStrategy(l_player));
				break;
				default:
				break;
			}
			
			l_listofPlayers.add(l_player);
		}
		
		return l_listofPlayers;
	}

	// protected String playGame(String map, Set<Player> players, int maxTurns) {

	// 	// initi everthimng

	// 	for (int i = 1; i<= maxTurns; i++) {
	// 		for(Player player : players) {
				
	// 		// issue order or play turn for each player	
	// 		}
	// 	}

	// 	// returns the winner name
	// 	//after play game add it to the list
	// }

	public void displayResult(List<String> winners) {

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
