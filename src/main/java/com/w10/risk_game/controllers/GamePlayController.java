package com.w10.risk_game.controllers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.w10.risk_game.commands.*;
import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.strategies.AggressivePlayerStrategy;
import com.w10.risk_game.models.strategies.BenevolentPlayerStrategy;
import com.w10.risk_game.models.strategies.CheaterPlayerStrategy;
import com.w10.risk_game.models.strategies.HumanPlayerStrategy;
import com.w10.risk_game.models.strategies.RandomPlayerStrategy;
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
	 * @param p_playerStrategy
	 *            The strategy of the player
	 */
	public void createPlayer(String p_playerName, String p_playerStrategy) {
		try {
			Player l_player = new Player(p_playerName.trim(), new ArrayList<>(), new ArrayList<>(), 0);

			// Set player strategy
			switch (p_playerStrategy) {
				case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_HUMAN :
					l_player.setStrategy(new HumanPlayerStrategy(l_player));
					break;
				case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_AGGRESSIVE :
					l_player.setStrategy(new AggressivePlayerStrategy(l_player));
					break;
				case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_BENEVOLENT :
					l_player.setStrategy(new BenevolentPlayerStrategy(l_player));
					break;
				case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_RANDOM :
					l_player.setStrategy(new RandomPlayerStrategy(l_player));
					break;
				case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_CHEATER :
					l_player.setStrategy(new CheaterPlayerStrategy(l_player));
					break;
			}

			// Check if the player name is not already present in the player list
			if (!this.d_players.containsKey(p_playerName.trim())) {
				// Add the new player to the player list
				this.d_players.put(p_playerName, l_player);

				// Add the new player to the list for diplomacy
				PlayerListForDiplomacy.add(l_player);

				// Log a message indicating successful creation of the player
				Logger.log(MessageFormat.format(Constants.CLI_GAME_PLAYER_CREATE, p_playerName).toString());
			} else {
				// Log an error message if the player name already exists
				Logger.log(Constants.GAME_ENGINE_ERROR_PLAYER_NAME_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			// Log an error message if an exception occurs during player creation
			Logger.log(Constants.GAME_ENGINE_ERROR_ADD_PLAYER);
		}
	}

	public void resetPlayerCreation() {
		this.d_players = new HashMap<>();
		this.d_isCountriesAssigned = false;
		this.d_currentPlayerIndex = 0;
		this.d_winner = null;
		// Log a message indicating that the player creation state has been reset
		Logger.log("Player creation state has been reset.");
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
		// Get the game map from the map editor controller
		this.d_gameMap = this.d_mapEditorController.getGameMap();

		try {
			// Check if the player name exists in the player list
			if (!this.d_players.containsKey(p_playerName)) {
				// Log an error message if the player name doesn't exist
				Logger.log(Constants.GAME_ENGINE_ERROR_PLAYER_NAME_DOESNT_EXIST);
				return;
			}

			// Remove the player from the player list
			this.d_players.remove(p_playerName.trim());
			// Remove the player from the list for diplomacy
			PlayerListForDiplomacy.removeIf(l_player -> l_player.getName().equals(p_playerName.trim()));

			// Log a message indicating the successful removal of the player
			Logger.log(Constants.CLI_GAME_PLAYER_REMOVE + p_playerName);
		} catch (Exception e) {
			e.printStackTrace();

			// Log an error message if an exception occurs during player removal
			Logger.log(Constants.GAME_ENGINE_ERROR_REMOVE_PLAYER);
		}
	}

	/**
	 * The function "showAllPlayers" prints the names of all players and the
	 * countries they own.
	 *
	 */
	public void showAllPlayers() {
		// Iterate through each player in the players' map
		this.d_players.forEach((p_playerName, p_player) -> {
			// Log the player's name
			Logger.log(p_playerName);

			// Print the countries owned by the player
			for (Country c : p_player.getCountriesOwned()) {
				try {
					// Try to log the country name
					Logger.log(c.getCountryName());
				} catch (Exception e) {
					// Log an error message if there's an issue printing country details
					Logger.log(Constants.GAME_ENGINE_ERROR_PRINTING_COUNTRY_DETAILS);
				}
			}

			// Log an empty line for clearer output separation
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
			// Get the game map from the map editor controller
			this.d_gameMap = this.d_mapEditorController.getGameMap();

			// Iterate through each player to assign reinforcements
			for (Player l_player : this.d_players.values()) {
				// Reset the leftover armies for each player
				l_player.setLeftoverArmies(0);

				// Assign reinforcements to the player based on the game map
				Reinforcements.AssignPlayerReinforcements(l_player, this.d_gameMap);
			}

			// Update the flag indicating countries are assigned for reinforcements
			this.d_isCountriesAssigned = true;

			// Log a message indicating successful reinforcement assignment
			Logger.log(Constants.CLI_ASSIGN_REINFORCEMENTS);

			return true; // Indicates successful reinforcement assignment
		} catch (Exception e) {
			// Log a generic error message if an exception occurs
			Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
			return false; // Indicates an error during reinforcement assignment
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
		// Check if the current player has no leftover armies and has committed orders
		if ((this.d_currentPlayer.getLeftoverArmies() == 0 && this.d_currentPlayer.getHasCommitted())
				|| this.d_currentPlayer.getCountriesOwned().isEmpty()) {
			// Remove the current player from the player list
			this.d_playerList.remove(d_currentPlayerIndex % this.d_playerList.size());

			// If the player list is empty, reset the current player and return false
			if (this.d_playerList.isEmpty()) {
				this.d_currentPlayer = null;
				return false;
			}

			// Update the current player to the next player in the list
			this.d_currentPlayer = this.d_playerList.get(d_currentPlayerIndex % this.d_playerList.size());
			return false; // Indicates orders cannot be issued
		}

		// Return true if the current player can issue orders
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
			// Lists to store specific types of orders
			List<Order> l_deployOrders = new ArrayList<>();
			List<Order> l_airliftOrders = new ArrayList<>();
			List<Order> l_negotiateOrders = new ArrayList<>();
			List<Order> l_bombOrders = new ArrayList<>();

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
					} else if (l_order instanceof Bomb) {
						l_bombOrders.add(l_order);
					} else {
						OtherOrders.add(l_order);
					}
				}
				l_player.setHasCommitted(false);
			}

			// Execute orders by their types
			for (Order l_order : l_deployOrders) {
				l_order.execute();
			}
			for (Order l_order : l_airliftOrders) {
				l_order.execute();
			}
			for (Order l_order : l_negotiateOrders) {
				l_order.execute();
			}
			for (Order l_order : l_bombOrders) {
				l_order.execute();
			}
			for (Order l_order : OtherOrders) {
				l_order.execute();
			}
			OtherOrders.removeAll(OtherOrders);

			// gift card if player has conquered new country
			for (Player l_player : this.d_players.values()) {
				if (l_player.hasConqueredNewCountry()) {
					l_player.addCard(CardType.GetRandomCard());
					l_player.setHasConqueredNewCountry(false);
				}
			}

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
			if (l_player.isEmpty())
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

	/**
	 * This function is to get all players in the game.
	 *
	 * @return the players in the game
	 */
	public HashMap<String, Player> getPlayers() {
		return d_players;
	}

	/**
	 * This function is to set all players in the game.
	 *
	 * @param d_players
	 *            the players in the game
	 */
	public void setPlayers(HashMap<String, Player> d_players) {
		this.d_players = d_players;
	}

	/**
	 * This function is to set the game map
	 *
	 * @param p_gameMap
	 *            the game map
	 */
	public void setGameMap(GameMap p_gameMap) {
		this.d_gameMap = p_gameMap;
	}

	/**
	 * This function is to set the current player
	 *
	 * @param p_currentPlayer
	 *            the current player
	 */
	public void setCurrentPlayer(Player p_currentPlayer) {
		this.d_currentPlayer = p_currentPlayer;
	}

	/**
	 * This function is to get the current player index
	 *
	 * @return the current player index
	 */
	public int getCurrentPlayerIndex() {
		return d_currentPlayerIndex;
	}

	/**
	 * This function is to set the current player index
	 *
	 * @param p_currentPlayerIndex
	 *            the current player index
	 */
	public void setCurrentPlayerIndex(int p_currentPlayerIndex) {
		this.d_currentPlayerIndex = p_currentPlayerIndex;
	}

	/**
	 * This function is to get player list
	 *
	 * @return player list
	 */
	public List<Player> getPlayerList() {
		return d_playerList;
	}

	/**
	 * This function is to set player list
	 *
	 * @param p_playerList
	 *            player list
	 */
	public void setPlayerList(List<Player> p_playerList) {
		this.d_playerList = p_playerList;
	}

	/**
	 * This function is to get variable isCountriesAssigned
	 *
	 * @return isCountriesAssigned
	 */
	public boolean getIsCountriesAssigned() {
		return d_isCountriesAssigned;
	}

	/**
	 * This function is to set variable isCountriesAssigned
	 *
	 * @param p_isCountriesAssigned
	 *            isCountriesAssigned
	 */
	public void setIsCountriesAssigned(boolean p_isCountriesAssigned) {
		this.d_isCountriesAssigned = p_isCountriesAssigned;
	}
}
