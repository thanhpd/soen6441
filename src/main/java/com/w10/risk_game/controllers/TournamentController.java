package com.w10.risk_game.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.w10.risk_game.exceptions.ApplicationException;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.MatchResult;
import com.w10.risk_game.models.strategies.AggressivePlayerStrategy;
import com.w10.risk_game.models.strategies.BenevolentPlayerStrategy;
import com.w10.risk_game.models.strategies.CheaterPlayerStrategy;
import com.w10.risk_game.models.strategies.RandomPlayerStrategy;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The TournamentController class is responsible for managing and running a
 * series of games on different maps using a set of player strategies.
 */
public class TournamentController {

	private GamePlayController d_gamePlayController;
	private MapEditorController d_mapEditorController;

	public static Phase Phase;

	ArrayList<String> l_listofMaps = new ArrayList<>();
	ArrayList<String> l_listofPlayers = new ArrayList<>();

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
	 * The start function runs a series of games on different maps using a set of
	 * player strategies, and stores the results in a list.
	 *
	 * @param p_playerStrategyNames
	 *            A set of strings representing the names of the player strategies.
	 * @param p_maps
	 *            A set of strings representing the names of different maps.
	 * @param p_gamesCount
	 *            The number of games to be played for each map.
	 * @param p_maxTurns
	 *            The parameter `p_maxTurns` represents the maximum number of turns
	 *            allowed in a game.
	 */
	public void start(Set<String> p_playerStrategyNames, Set<String> p_maps, int p_gamesCount, int p_maxTurns) {
		ArrayList<MatchResult> l_listofMatchResults = new ArrayList<MatchResult>();

		for (var map : p_maps) {

			for (int i = 1; i <= p_gamesCount; i++) {
				d_mapEditorController = new MapEditorController();
				d_gamePlayController = new GamePlayController(d_mapEditorController);

				var players = createPlayers(p_playerStrategyNames);
				var l_result = playGame(map, players, p_maxTurns);
				MatchResult l_winner = new MatchResult(l_result, i, map);
				l_listofMatchResults.add(l_winner);
				displayResult(l_listofMatchResults);
			}

		}
	}

	/**
	 * The function creates a set of players based on a set of player strategy
	 * names, assigns the corresponding strategy to each player, and returns the set
	 * of players.
	 *
	 * @param p_playerStrategyNames
	 *            A set of strings representing the names of player strategies.
	 * @return The method is returning a Set of Player objects.
	 */
	private Set<Player> createPlayers(Set<String> p_playerStrategyNames) {
		Set<Player> l_listofPlayers = new HashSet<>();
		for (String l_strategy : p_playerStrategyNames) {
			d_gamePlayController.createPlayer(l_strategy, l_strategy);
			Player l_player = d_gamePlayController.getPlayerDetails(l_strategy);
			switch (l_strategy) {
				case "Random" :
					l_player.setStrategy(new RandomPlayerStrategy(l_player));
					break;
				case "Cheater" :
					l_player.setStrategy(new CheaterPlayerStrategy(l_player));
					break;
				case "Benevolent" :
					l_player.setStrategy(new BenevolentPlayerStrategy(l_player));
					break;
				case "Aggressive" :
					l_player.setStrategy(new AggressivePlayerStrategy(l_player));
					break;
				default :
					break;
			}

			l_listofPlayers.add(l_player);
		}

		return l_listofPlayers;
	}

	/**
	 * The function "playGame" takes a map, a set of players, and a maximum number
	 * of turns as input, and simulates a game by assigning countries to players and
	 * allowing each player to take turns until a winner is determined or the
	 * maximum number of turns is reached.
	 *
	 * @param p_map
	 *            The parameter "p_map" is a string that represents the map being
	 *            used for the game.
	 * @param p_players
	 *            The parameter "p_players" is a Set of Player objects. It
	 *            represents the set of players participating in the game.
	 * @param p_maxTurns
	 *            The maximum number of turns that the game will be played for.
	 * @return The method is returning a String value. If there is a winner, it will
	 *         return the name of the winner. If there is no winner after the
	 *         maximum number of turns, it will return "Draw".
	 */
	protected String playGame(String p_map, Set<Player> p_players, int p_maxTurns) {

		d_mapEditorController.loadMap(Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "" + p_map);
		d_gamePlayController.assignCountries();
		d_gamePlayController.assignPlayersReinforcements();
		d_gamePlayController.showMap();
		for (int i = 1; i <= p_maxTurns; i++) {
			for (int j = 1; j <= d_gamePlayController.getNoOfPlayers(); j++) {
				d_gamePlayController.issuePlayerOrder();
				if (d_gamePlayController.getWinner() != null) {
					return d_gamePlayController.getWinner();
				}

			}

			d_gamePlayController.showMap();

		}

		return "Draw";
	}

	/**
	 * The function displays the results of a tournament by printing the game count,
	 * map, and player name for each match result in a given list.
	 *
	 * @param l_listofMatchResults
	 *            The parameter `l_listofMatchResults` is a List of MatchResult
	 *            objects.
	 */
	public void displayResult(List<MatchResult> l_listofMatchResults) {
		// System.out.println("Result of the trounament:");
		// System.out.println(String.format("%-10s%-10s%-10s", "Game Count", "Map",
		// "Result"));
		// for (MatchResult result : l_listofMatchResults) {

		// System.out.println(String.format("%-10s%-10s%-10d", result.l_gameCount,
		// result.l_map, result.l_playerName));
		// }

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
