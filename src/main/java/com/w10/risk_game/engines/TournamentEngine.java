package com.w10.risk_game.engines;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.MatchResult;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The TournamentEngine class is responsible for managing and running a series
 * of games on different maps using a set of player strategies.
 */
public class TournamentEngine {

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

	public TournamentEngine() {
		this.d_mapEditorController = new MapEditorController();
		this.d_gamePlayController = new GamePlayController(d_mapEditorController);
	}

	/**
	 * The startGame function runs a tournament by playing multiple games on
	 * different maps with a given set of player strategies, and then displays the
	 * results.
	 *
	 * @param p_playerStrategyNames
	 *            A set of strings representing the names of the player strategies.
	 * @param p_maps
	 *            A set of strings representing the names of the maps to be used in
	 *            the game.
	 * @param p_gamesCount
	 *            The number of games to be played in the tournament.
	 * @param p_maxTurns
	 *            The parameter `p_maxTurns` represents the maximum number of turns
	 *            allowed in each game.
	 */
	public void startGame(Set<String> p_playerStrategyNames, Set<String> p_maps, int p_gamesCount, int p_maxTurns) {
		ArrayList<MatchResult> l_listofMatchResults = new ArrayList<MatchResult>();
		if (p_maps.contains(Constants.USER_INPUT_COMMAND_DUPLICATE)) {
			Logger.log(Constants.TOURNAMENT_MAP_DUPLICATE_STRING);
		} else if (p_playerStrategyNames.contains(Constants.USER_INPUT_COMMAND_DUPLICATE)) {
			Logger.log(Constants.TOURNAMENT_PLAYER_DUPLICATE_STRING);
		} else if (p_gamesCount < 1 || p_gamesCount > 5) {
			Logger.log(Constants.TOURNAMENT_NUMBER_OF_GAMES);
		} else if (p_maxTurns < 10 || p_maxTurns > 50) {
			Logger.log(Constants.TOURNAMENT_NUMBER_OF_TURNS);
		} else {
			for (var map : p_maps) {
				Logger.log("####### For the map: " + map + "-----------------#################");
				for (int i = 1; i <= p_gamesCount; i++) {
					Logger.log(i + "####### Game Number: " + i + "-----------------#################");
					d_mapEditorController = new MapEditorController();
					d_gamePlayController = new GamePlayController(d_mapEditorController);
					var players = createPlayers(p_playerStrategyNames);
					var l_result = playGame(map, players, p_maxTurns);
					MatchResult l_winner = new MatchResult(l_result, i, map);
					l_listofMatchResults.add(l_winner);
				}
			}
			displayResult(l_listofMatchResults);
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

		d_mapEditorController.loadMap(Constants.DEFAULT_GAME_MAP_FOLDER_PATH + "" + p_map);
		d_gamePlayController.assignCountries();
		d_gamePlayController.assignPlayersReinforcements();
		for (int i = 1; i <= p_maxTurns; i++) {
			Logger.log("########## Number of turn:  " + i + "#################");
			for (int j = 1; j <= d_gamePlayController.getNoOfPlayers(); j++) {
				d_gamePlayController.issuePlayerOrder();
				d_gamePlayController.showMap();
				if (this.d_gamePlayController.checkIfGameIsOver()) {
					if (d_gamePlayController.getWinner() != null) {
						return d_gamePlayController.getWinner();
					}
				}
			}
			d_gamePlayController.assignPlayersReinforcements();
			d_gamePlayController.executePlayerOrders();
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
		Logger.log("Result of the tournament:");
		Formatter l_formatter = new Formatter();
		String l_table, l_resultDisplayColumnNames, l_displayLine;
		l_table = Constants.RESULT_DISPLAY_TABLE_FORMAT_PATTERN;
		l_displayLine = Constants.RESULT_DISPLAY_TABLE_LINE;
		l_resultDisplayColumnNames = Constants.RESULT_DISPLAY_TABLE_COLUMN_NAMES;

		// Print the table header to the console and file
		l_formatter.format(l_displayLine);
		Logger.log(l_formatter.toString());
		l_formatter.close();

		l_formatter = new Formatter();
		l_formatter.format(l_resultDisplayColumnNames);
		Logger.log(l_formatter.toString());
		l_formatter.close();

		l_formatter = new Formatter();
		l_formatter.format(l_displayLine);
		Logger.log(l_formatter.toString());
		l_formatter.close();

		for (MatchResult result : l_listofMatchResults) {
			l_formatter = new Formatter();
			l_formatter.format(l_table, result.d_gameCount, result.d_map, result.d_playerName);
			Logger.log(l_formatter.toString());
			l_formatter.close();
		}

	}

}
