package com.w10.risk_game.models.tournament;

import java.util.ArrayList;
import java.util.List;

/**
 * The TournamentOptions class is used to store and retrieve information about
 * the map, player strategies, number of games, and maximum number of tries for
 * a tournament.
 */
public class TournamentOptions {

	/**
	 * list for Map
	 */
	private List<String> d_Map = new ArrayList<>();
	/**
	 * List to hold player strategy
	 */
	private List<String> d_PlayerStrategies = new ArrayList<>();
	/**
	 * number of games
	 */
	private int d_Games;
	/**
	 * maximum number of tries
	 */
	private int d_MaxTries;

	/**
	 * method to get map
	 *
	 * @return the map
	 */
	public List<String> getMap() {
		return d_Map;
	}

	/**
	 * method to get player strategies
	 *
	 * @return player strategies
	 */
	public List<String> getPlayerStrategies() {
		return d_PlayerStrategies;
	}

	/**
	 * method to get games
	 *
	 * @return number of games
	 */
	public int getGames() {
		return d_Games;
	}

	/**
	 * method to set game
	 *
	 * @param p_Games
	 *            number of games
	 */
	public void setGames(int p_Games) {
		d_Games = p_Games;
	}

	/**
	 * method to get maximum tried per game
	 *
	 * @return the maximum tries
	 */
	public int getMaxTries() {
		return d_MaxTries;
	}

	/**
	 * method to set maximum tries
	 *
	 * @param p_MaxTries
	 *            maximum tries
	 */
	public void setMaxTries(int p_MaxTries) {
		d_MaxTries = p_MaxTries;
	}
}
