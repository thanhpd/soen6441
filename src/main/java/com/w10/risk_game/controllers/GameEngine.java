package com.w10.risk_game.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.models.Order;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.MapDisplay;
import com.w10.risk_game.utils.MapEditor;
import com.w10.risk_game.utils.MapReader;
import com.w10.risk_game.utils.MapValidator;
import com.w10.risk_game.utils.Reinforcements;

/**
 * The GameEngine class is responsible for managing the game map, players,
 * issuing orders, executing orders and their interactions in a game.
 *
 * @author Sherwyn Dsouza
 */
public class GameEngine {
	private GameMap d_gameMap;
	private HashMap<String, Player> d_players;
	private MapEditor d_mapEditor;
	private boolean d_isCountriesAssigned;
	private MapReader d_mapReader;
	private MapDisplay d_displayMap;
	private Player d_currentPlayer;
	private int d_currentPlayerIndex;
	private List<Player> d_playerList;

	/**
	 * Game Engine constructor
	 */
	public GameEngine() {
		this.d_gameMap = new GameMap();
		this.d_players = new HashMap<>();
		this.d_isCountriesAssigned = false;
		this.d_mapReader = new MapReader();
		this.d_displayMap = new MapDisplay();
		this.d_currentPlayerIndex = 0;
	}

	/**
	 * The function "loadMap" loads a map file, creates a game map object, and
	 * checks if the map is valid.
	 *
	 * @param p_filePath
	 *                   The parameter `p_filePath` is a String that represents the
	 *                   full
	 *                   path of the file from which the map will be loaded.
	 */
	public void loadMap(String p_filePath) {
		try {
			this.d_mapReader = new MapReader();
			this.d_gameMap = d_mapReader.loadMapFile(p_filePath);
			this.d_mapEditor = new MapEditor(this.d_gameMap);

			// Check if map is created and is valid
			if (this.d_gameMap.isMapCreated()) {
				if (!checkIfMapIsValid()) {
					this.d_gameMap = null;
					System.out.println(Constants.GAME_ENGINE_CANNOT_LOAD_MAP);
				}
			} else {
				System.out.println(Constants.GAME_ENGINE_MAP_NOT_CREATED);
			}

		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_CANNOT_LOAD_MAP);
		}
	}

	/**
	 * The function "showMap" checks if the map is valid and then formats and
	 * displays the map if it is.
	 *
	 */
	public void showMap() {
		if (checkIfMapIsValid())
			this.d_displayMap.formatMap(this.d_gameMap, this.d_players.size() > 0 && this.d_isCountriesAssigned);
	}

	/**
	 * The function creates a player with a given name and adds it to a map of
	 * players, checking for duplicate names.
	 *
	 * @param p_playerName
	 *                     The parameter "p_playerName" is a String that represents
	 *                     the name
	 *                     of the player being created.
	 */
	public void createPlayer(String p_playerName) {
		try {
			Player l_player = new Player(p_playerName.trim(), new ArrayList<Country>(), new ArrayList<Order>(), 0);
			if (!this.d_players.containsKey(p_playerName.trim())) {
				this.d_players.put(p_playerName, l_player);
				System.out.format(Constants.CLI_GAME_PLAYER_CREATE, p_playerName);
			} else {
				System.out.println(Constants.GAME_ENGINE_ERROR_PLAYER_NAME_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_ERROR_ADD_PLAYER);
		}
	}

	/**
	 * The function removes a player from a list of players in a game engine. It
	 * also randomly assigns the countries of the removed player to the remaining
	 * players
	 *
	 * @param p_playerName
	 *                     The parameter "p_playerName" is a String that represents
	 *                     the name
	 *                     of the player that needs to be removed.
	 *
	 */
	public void removePlayer(String p_playerName) {
		try {
			if (!this.d_players.containsKey(p_playerName)) {
				System.out.println(Constants.GAME_ENGINE_ERROR_PLAYER_NAME_DOESNT_EXIST);
				return;
			}
			p_playerName = p_playerName.trim();
			List<String> l_playerNames = new ArrayList<>(this.d_players.keySet());

			// Assign countries of removed player to remaining players
			if (l_playerNames.size() > 1) {
				List<Country> l_ownedCountriesOfPlayer = this.d_players.get(p_playerName).getCountriesOwned();
				Collections.shuffle(l_ownedCountriesOfPlayer);

				l_playerNames.remove(p_playerName);
				int i = 0;

				while (i < l_ownedCountriesOfPlayer.size()) {
					this.d_players.get(l_playerNames.get(i % l_playerNames.size())).getCountriesOwned()
							.add(l_ownedCountriesOfPlayer.get(i));
					l_ownedCountriesOfPlayer.get(i)
							.setOwner(this.d_players.get(l_playerNames.get(i % l_playerNames.size())));
					i += 1;
				}

				this.assignPlayersReinforcements();
			}
			this.d_players.remove(p_playerName.trim());
			System.out.println(Constants.CLI_GAME_PLAYER_REMOVE + p_playerName);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_ERROR_REMOVE_PLAYER);
		}
	}

	/**
	 * The function "showAllPlayers" prints the names of all players and the
	 * countries they own.
	 *
	 */
	public void showAllPlayers() {
		this.d_players.forEach((p_playerName, p_player) -> {
			System.out.println(p_playerName);
			for (Country c : p_player.getCountriesOwned()) {
				try {
					System.out.println(c.getCountryName());
				} catch (Exception e) {
					System.out.println(Constants.GAME_ENGINE_ERROR_PRINTING_COUNTRY_DETAILS);
				}
			}
			System.out.println();
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
			// Check if number of players is greater than 1
			if (this.d_players.size() <= 1)
				throw new Exception(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES);

			// If there are more players than countries throw error
			if (this.d_players.size() > this.d_gameMap.getCountries().size()) {
				System.out.format(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES, this.d_gameMap.getCountries().size(),
						this.d_players.size());
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
			this.assignPlayersReinforcements();
			this.d_isCountriesAssigned = true;
			this.d_playerList = new ArrayList<>(this.d_players.values());
			this.d_currentPlayer = this.d_playerList.get(0);

			return true;
		} catch (Exception e) {
			System.out.format(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES, this.d_gameMap.getCountries().size(),
					this.d_players.size());
			return false;
		}
	}

	/**
	 * The function assigns reinforcements to each player in the game.
	 *
	 */
	private void assignPlayersReinforcements() {
		for (Player l_player : this.d_players.values()) {
			l_player.setLeftoverArmies(0);
			Reinforcements.ReinforcementPhase(l_player, this.d_gameMap);
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
	 * The function returns the game map.
	 *
	 * @return The method is returning an object of type GameMap.
	 *
	 */
	public GameMap getGameMap() {
		return this.d_gameMap;
	}

	/**
	 * The function returns the details of a player based on their name.
	 *
	 * @param p_playerName
	 *                     The name of the player for which you want to retrieve the
	 *                     details.
	 * @return The method is returning a Player object.
	 *
	 */
	public Player getPlayerDetails(String p_playerName) {
		return this.d_players.get(p_playerName);
	}

	/**
	 * The function `editMap` checks if a map file exists, loads it if it does,
	 * creates a new file if it doesn't, and returns a boolean indicating success or
	 * failure.
	 *
	 * @param p_mapFilePath
	 *                      The parameter `p_mapFileName` is a String that
	 *                      represents the file
	 *                      name or path of the map file that needs to be edited.
	 * @return The method is returning a boolean value.
	 *
	 */
	public boolean editMap(String p_mapFilePath) {
		File l_file = new File(p_mapFilePath);

		// If file with the input filename exists then load that map else create a new
		// map file
		if (l_file.exists()) {
			this.loadMap(p_mapFilePath);
			System.out.println(Constants.GAME_ENGINE_MAP_EDIT_SUCCESS);
			return true;
		} else {
			String[] l_filePathSplit = p_mapFilePath.split("/");
			try {
				System.out.format(Constants.GAME_ENGINE_ERROR_MAP_DOES_NOT_EXIST, p_mapFilePath,
						l_filePathSplit[l_filePathSplit.length - 1]);
				return new File(l_filePathSplit[l_filePathSplit.length - 1]).createNewFile();
			} catch (IOException e) {
				System.out.format(Constants.GAME_ENGINE_ERROR_CREATE_MAP, p_mapFilePath, e.getMessage());
			}
		}
		return false;
	}

	/**
	 * The function adds a continent to a map editor and prints the output.
	 *
	 * @param p_continentName
	 *                        The parameter "p_continentName" is a String that
	 *                        represents the
	 *                        name of the continent that you want to add.
	 *
	 * @param p_bonus
	 *                        The p_bonus parameter is an integer that represents
	 *                        the bonus for
	 *                        each continent.
	 */
	public void addContinent(String p_continentName, int p_bonus) {
		try {
			String l_output = this.d_mapEditor.addContinent(p_continentName, p_bonus);
			System.out.println(l_output);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function adds a country to a map editor and prints the output.
	 *
	 * @param p_countryId
	 *                        The p_countryId parameter is an integer that
	 *                        represents the unique
	 *                        identifier for the country being added.
	 * @param p_countryName
	 *                        The parameter "p_countryName" is a String that
	 *                        represents the name
	 *                        of the country that you want to add.
	 * @param p_continentName
	 *                        The p_continentName parameter is an integer that
	 *                        represents the ID
	 *                        of the continent to which the country belongs.
	 *
	 */
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		try {
			String l_output = this.d_mapEditor.addCountry(p_countryId, p_countryName, p_continentName);
			System.out.println(l_output);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a continent from a map in a game editor.
	 *
	 * @param p_continentName
	 *                        The parameter "p_continentId" is an integer that
	 *                        represents the ID
	 *                        of the continent that needs to be removed.
	 *
	 */
	public void removeContinent(String p_continentName) {
		try {
			String l_output = this.d_mapEditor.removeContinent(p_continentName);
			System.out.println(l_output);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a country from the game map and updates the player's
	 * ownership if necessary.
	 *
	 * @param p_countryId
	 *                    The parameter `p_countryId` is an integer representing the
	 *                    ID of
	 *                    the country that needs to be removed.
	 *
	 */
	public void removeCountry(int p_countryId) {
		try {
			Player l_player = this.d_gameMap.getCountries().get(p_countryId).getOwner();
			String l_output = this.d_mapEditor.removeCountry(p_countryId);
			if (this.d_isCountriesAssigned) {
				removeCountryFromPlayer(p_countryId, l_player);
			}
			System.out.println(l_output);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function adds a neighbor country to a given country in a map editor.
	 *
	 * @param p_countryId
	 *                             The p_countryId parameter represents the ID of
	 *                             the country to
	 *                             which you want to add a neighbor.
	 * @param p_neighbourCountryId
	 *                             The parameter "p_neighbourCountryId" represents
	 *                             the ID of the
	 *                             neighboring country that you want to add to the
	 *                             country with the
	 *                             ID "p_countryId".
	 *
	 */
	public void addNeighbor(int p_countryId, int p_neighbourCountryId) {
		try {
			String l_output = this.d_mapEditor.addNeighbor(p_countryId, p_neighbourCountryId);
			System.out.println(l_output);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a neighbor country from a given country in a map editor.
	 *
	 * @param p_countryId
	 *                            The p_countryId parameter represents the ID of the
	 *                            country from
	 *                            which you want to remove a neighbor.
	 * @param p_neighborCountryId
	 *                            The parameter "p_neighborCountryId" represents the
	 *                            ID of the
	 *                            neighbor country that you want to remove from the
	 *                            country with ID
	 *                            "p_countryId".
	 *
	 */
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		try {
			String l_output = this.d_mapEditor.removeNeighbor(p_countryId, p_neighborCountryId);
			System.out.println(l_output);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a specific country from a player's list of owned
	 * countries.
	 *
	 * @param p_countryId
	 *                    The ID of the country that needs to be removed from the
	 *                    player's
	 *                    list of owned countries.
	 * @param p_player
	 *                    The parameter "p_player" is of type Player and represents
	 *                    the
	 *                    player from whom the country needs to be removed.
	 *
	 */
	private void removeCountryFromPlayer(int p_countryId, Player p_player) {
		try {
			List<Country> l_playerCountries = this.d_players.get(p_player.getName()).getCountriesOwned();
			l_playerCountries.removeIf(p_country -> p_country.getCountryId() == p_countryId);
			this.d_players.get(p_player.getName()).setCountriesOwned(l_playerCountries);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function checks if a game map is valid and prints a corresponding
	 * message.
	 *
	 * @return a boolean value indicate if the game map is valid
	 *
	 */
	public boolean checkIfMapIsValid() {
		try {
			// First check if the map is created, then if map is valid it will return true
			// else false
			if (this.d_gameMap.isMapCreated()) {
				if (MapValidator.IsMapCorrect(this.d_gameMap)) {
					System.out.println(Constants.GAME_ENGINE_MAP_VALID);
					return true;
				} else {
					System.out.println(Constants.GAME_ENGINE_MAP_INVALID);
					return false;
				}
			} else {
				System.out.println(Constants.GAME_ENGINE_MAP_NOT_CREATED);
				return false;
			}
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_VALIDATE_MAP);
			return false;
		}
	}

	/**
	 * The function checks if the game can begin by verifying if the map is created,
	 * there are at least two players, and countries have been assigned.
	 *
	 * @return The method is returning a boolean value.
	 *
	 */
	public boolean checkIfGameCanBegin() {
		return this.d_gameMap.isMapCreated() && this.d_players.size() > 1 && this.d_isCountriesAssigned;
	}

	/**
	 * The function saves the game map to a file if it is valid, otherwise it prints
	 * an error message.
	 *
	 * @param p_mapFilePath
	 *                      The full path of the file where the map will be saved.
	 *
	 */
	public void saveMap(String p_mapFilePath) {
		if (checkIfMapIsValid()) {
			this.d_gameMap.saveMap(p_mapFilePath);
		} else {
			System.out.println(Constants.GAME_ENGINE_CANNOT_SAVE_MAP);
		}
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

		if (this.d_currentPlayer.getLeftoverArmies() == 0) {
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
	 * returs True so that no more players have to issue orders and order execution
	 * can begin by the game engine
	 *
	 * @return The method is returning a boolean value.
	 *
	 */
	public boolean checkIfOrdersCanBeExecuted() {
		return this.d_playerList.isEmpty();
	}

	/**
	 * The function executes orders for each player, assigns reinforcements, and
	 * updates the current player.
	 *
	 * @return The method is returning a boolean value. If the execution of player
	 *         orders is successful, it returns true. If an exception occurs during
	 *         execution, it returns false.
	 *
	 */
	public boolean executePlayerOrders() {
		try {
			// Execute the orders of the players
			for (Player l_player : this.d_players.values()) {
				while (!l_player.getOrders().isEmpty()) {
					l_player.nextOrder().execute();
				}
			}
			// Reset the reinforcements of the players and start the Issue Order phase again
			this.assignPlayersReinforcements();
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

	public void conquerCount() {
		System.out.println("Conquer phase begins!");
		System.out.println(getCurrentPlayer() + " owns ");
	}
}
