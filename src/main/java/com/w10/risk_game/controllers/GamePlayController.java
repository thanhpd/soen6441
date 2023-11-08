package com.w10.risk_game.controllers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.w10.risk_game.commands.Airlift;
import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.commands.Negotiate;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.commands.Order;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.Reinforcements;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The GamePlayController class is responsible for managing players, issuing
 * orders, executing orders and their interactions in a game.
 *
 * @author Sherwyn Dsouza
 */
public class GamePlayController {
	private GameMap d_gameMap;
	private HashMap<String, Player> d_players;
	private boolean d_isCountriesAssigned;
	private Player d_currentPlayer;
	private int d_currentPlayerIndex;
	private List<Player> d_playerList;
	private MapEditorController d_mapEditorController;
	private String d_winner;

	private static List<Order> OtherOrders = new ArrayList<>();
	private static List<Player> PlayerListForDiplomacy = new ArrayList<>();

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * The constructor of the GamePlayController class.
	 *
	 * @param p_mapEditorController
	 *            The map editor controller object.
	 */
	public GamePlayController(MapEditorController p_mapEditorController) {
		this.d_mapEditorController = p_mapEditorController;
		this.d_players = new HashMap<>();
		this.d_isCountriesAssigned = false;
		this.d_currentPlayerIndex = 0;
		this.d_winner = null;
	}

	/**
	 * The function returns the list of players in a game.
	 *
	 * @return a list of players in a game.
	 */
	public static List<Player> GetPlayerListForDiplomacy() {
		return PlayerListForDiplomacy;
	}

	/**
	 * The function sets the list of players in a game.
	 *
	 * @param p_playerListForDiplomacy
	 *            a list of players in a game.
	 */
	public void SetPlayerListForDiplomacy(List<Player> p_playerListForDiplomacy) {
		PlayerListForDiplomacy = p_playerListForDiplomacy;
	}

	/**
	 * The function returns a list of other orders.
	 *
	 * @return The method is returning a List of Order objects.
	 */
	public static List<Order> GetOtherOrders() {
		return OtherOrders;
	}

	/**
	 * The function sets the value of a static variable called "OtherOrders" to the
	 * provided list of Order objects.
	 *
	 * @param p_otherOrders
	 *            The parameter "p_otherOrders" is a List of Order objects.
	 */
	public static void SetOtherOrders(List<Order> p_otherOrders) {
		OtherOrders = p_otherOrders;
	}

	/**
	 * The function creates a player with a given name and adds it to a map of
	 * players, checking for duplicate names.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player being created.
	 */
	public void createPlayer(String p_playerName) {
		try {
			Player l_player = new Player(p_playerName.trim(), new ArrayList<Country>(), new ArrayList<Order>(), 0);
			if (!this.d_players.containsKey(p_playerName.trim())) {
				this.d_players.put(p_playerName, l_player);
				PlayerListForDiplomacy.add(l_player);
				Logger.log(MessageFormat.format(Constants.CLI_GAME_PLAYER_CREATE, p_playerName).toString());
			} else {
				Logger.log(Constants.GAME_ENGINE_ERROR_PLAYER_NAME_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_ERROR_ADD_PLAYER);
		}
	}

	/**
	 * The function removes a player from a list of players in a game engine.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player that needs to be removed.
	 *
	 */
	public void removePlayer(String p_playerName) {
		this.d_gameMap = this.d_mapEditorController.getGameMap();
		try {
			if (!this.d_players.containsKey(p_playerName)) {
				Logger.log(Constants.GAME_ENGINE_ERROR_PLAYER_NAME_DOESNT_EXIST);
				return;
			}
			this.d_players.remove(p_playerName.trim());
			Logger.log(Constants.CLI_GAME_PLAYER_REMOVE + p_playerName);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.log(Constants.GAME_ENGINE_ERROR_REMOVE_PLAYER);
		}
	}

	/**
	 * The function "showAllPlayers" prints the names of all players and the
	 * countries they own.
	 *
	 */
	public void showAllPlayers() {
		this.d_players.forEach((p_playerName, p_player) -> {
			Logger.log(p_playerName);
			for (Country c : p_player.getCountriesOwned()) {
				try {
					Logger.log(c.getCountryName());
				} catch (Exception e) {
					Logger.log(Constants.GAME_ENGINE_ERROR_PRINTING_COUNTRY_DETAILS);
				}
			}
			Logger.log("");
		});
	}

	/**
	 * The function assigns countries to players in a game, ensuring that each
	 * player owns a certain number of countries.
	 *
	 * @return The method is returning a boolean value.
	 *
	 */
	public boolean assignCountries() {
		try {
			this.d_gameMap = this.d_mapEditorController.getGameMap();

			// Check if number of players is greater than 1
			if (this.d_players.size() <= 1)
				throw new Exception(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES);

			// If there are more players than countries throw error
			if (this.d_players.size() > this.d_gameMap.getCountries().size()) {
				Logger.log(MessageFormat.format(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES,
						this.d_gameMap.getCountries().size(), this.d_players.size()));
				return false;
			}

			List<String> l_playerNames = new ArrayList<>(this.d_players.keySet());
			int l_noOfPlayers = this.d_players.size();
			List<Country> l_countries = new ArrayList<>(this.d_gameMap.getCountries().values());
			Collections.shuffle(l_countries);

			// Randomly assign countries to players
			int i = 0;
			while (i < l_countries.size()) {
				String l_playerName = l_playerNames.get(i % l_noOfPlayers);
				this.d_players.get(l_playerName).getCountriesOwned().add(l_countries.get(i));
				l_countries.get(i).setOwner(this.d_players.get(l_playerName));
				i += 1;
			}

			// Assign reinforcements to the players based on countries
			this.d_playerList = new ArrayList<>(this.d_players.values());
			this.d_currentPlayer = this.d_playerList.get(0);

			return true;
		} catch (Exception e) {
			Logger.log(MessageFormat.format(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES,
					this.d_gameMap.getCountries().size(), this.d_players.size()));
			return false;
		}
	}

	/**
	 * The function assigns reinforcements to players in a game, based on the game
	 * map.
	 *
	 * @return The method is returning a boolean value.
	 */
	public boolean assignPlayersReinforcements() {
		try {
			this.d_gameMap = this.d_mapEditorController.getGameMap();
			for (Player l_player : this.d_players.values()) {
				l_player.setLeftoverArmies(0);
				Reinforcements.AssignPlayerReinforcements(l_player, this.d_gameMap);
			}
			this.d_isCountriesAssigned = true;
			Logger.log(Constants.CLI_ASSIGN_REINFORCEMENTS);
			return true;
		} catch (Exception e) {
			Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
			return false;
		}
	}

	/**
	 * The function returns the number of players in a game.
	 *
	 * @return The number of players in the list "this.d_players".
	 *
	 */
	public Integer getNoOfPlayers() {
		return this.d_players.size();
	}

	/**
	 * The function returns the details of a player based on their name.
	 *
	 * @param p_playerName
	 *            The name of the player for which you want to retrieve the details.
	 * @return The method is returning a Player object.
	 *
	 */
	public Player getPlayerDetails(String p_playerName) {
		return this.d_players.get(p_playerName);
	}

	/**
	 * The function checks if the game can begin by verifying if the map is created,
	 * there are at least two players, and countries have been assigned.
	 *
	 * @return The method is returning a boolean value.
	 *
	 */
	public boolean checkIfGameCanBegin() {
		this.d_gameMap = this.d_mapEditorController.getGameMap();
		return this.d_gameMap.isMapCreated() && this.d_players.size() > 1 && this.d_isCountriesAssigned;
	}

	/**
	 * The function "issuePlayerOrder" updates the current player and calls the
	 * "issueOrder" method for the current player.
	 *
	 */
	public void issuePlayerOrder() {
		this.d_currentPlayer.issueOrder();
		this.d_currentPlayerIndex += 1;
		this.d_currentPlayer = this.d_playerList.get(d_currentPlayerIndex % this.d_playerList.size());
	}

	/**
	 * The function checks if the current player has any leftover armies and removes
	 * them from the player list if they don't, then returns true if there are
	 * leftover armies and false otherwise.
	 *
	 * @return The method is returning a boolean value.
	 *
	 */
	public boolean checkIfOrdersCanBeIssued() {
		if (this.d_currentPlayer.getLeftoverArmies() == 0 && this.d_currentPlayer.getHasCommitted()) {
			this.d_playerList.remove(d_currentPlayerIndex % this.d_playerList.size());
			if (this.d_playerList.isEmpty()) {
				this.d_currentPlayer = null;
				return false;
			}
			this.d_currentPlayer = this.d_playerList.get(d_currentPlayerIndex % this.d_playerList.size());
			return false;
		}
		return true;
	}

	/**
	 * The function checks if there are any players in the player list. If empty, it
	 * returns True so that no more players have to issue orders and order execution
	 * can begin by the game engine
	 *
	 * @return The method is returning a boolean value.
	 *
	 */
	public boolean checkIfOrdersCanBeExecuted() {
		return this.d_playerList.isEmpty();
	}

	/**
	 * The function executes the orders of the players in a specific order and then
	 * re-initializes variables for the next turn.
	 *
	 * @return The method is returning a boolean value. If the execution of the
	 *         player orders is successful, it returns true. If there is an
	 *         exception during the execution, it returns false.
	 */
	public boolean executePlayerOrders() {
		try {
			List<Order> l_deployOrders = new ArrayList<>();
			List<Order> l_airliftOrders = new ArrayList<>();
			List<Order> l_negotiateOrders = new ArrayList<>();

			// Execute the orders of the players
			for (Player l_player : this.d_players.values()) {
				while (!l_player.getOrders().isEmpty()) {
					Order l_order = l_player.nextOrder();
					if (l_order instanceof Deploy) {
						l_deployOrders.add(l_order);
					} else if (l_order instanceof Airlift) {
						l_airliftOrders.add(l_order);
					} else if (l_order instanceof Negotiate) {
						l_negotiateOrders.add(l_order);
					} else {
						OtherOrders.add(l_order);
					}
				}
			}

			for (Order l_order : l_deployOrders) {
				l_order.execute();
			}
			for (Order l_order : l_airliftOrders) {
				l_order.execute();
			}
			for (Order l_order : l_negotiateOrders) {
				l_order.execute();
			}
			for (Order l_order : OtherOrders) {
				l_order.execute();
			}
			OtherOrders.removeAll(OtherOrders);

			// Re-initialize variables used in the Issue Order phase again
			this.d_currentPlayerIndex = 0;
			this.d_playerList = new ArrayList<>(this.d_players.values());
			this.d_currentPlayer = this.d_playerList.get(0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * The function getCurrentPlayer() returns the current player.
	 *
	 * @return The method is returning the current player.
	 *
	 */
	public Player getCurrentPlayer() {
		return this.d_currentPlayer;
	}

	/**
	 * The function "showMap" calls the "showMap" method of the
	 * "d_mapEditorController" object.
	 */
	public void showMap() {
		this.d_mapEditorController.showMap(true);
	}

	/**
	 * The function checks if the game is over by iterating through all countries
	 * and checking if they have the same owner.
	 *
	 * @return The method is returning a boolean value.
	 */
	public boolean checkIfGameIsOver() {
		String l_player = "";
		for (Country l_country : this.d_mapEditorController.getGameMap().getCountries().values()) {
			// If no owner that means the country is neutral and the game is not over
			if (l_country.getOwner() == null)
				return false;
			if (l_player.equals(""))
				l_player = l_country.getOwner().getName();
			// Check if a country has a different owner
			else if (!l_player.equals(l_country.getOwner().getName()))
				return false;
		}
		this.d_winner = l_player;
		return true;
	}

	/**
	 * The function "getWinner" returns the winner of a game.
	 *
	 * @return The method is returning the value of the variable "d_winner".
	 */
	public String getWinner() {
		return this.d_winner;
	}
}