package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.MapDisplay;
import com.w10.risk_game.utils.MapEditor;
import com.w10.risk_game.utils.MapReader;
import com.w10.risk_game.utils.MapValidator;
import com.w10.risk_game.utils.Reinforcements;

/**
 * The GameEngine class is responsible for managing the game map, players, and
 * their interactions in a game.
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
	private Reinforcements d_reinforcements;

	/**
	 * Game Engine constructor
	 */
	public GameEngine() {
		this.d_gameMap = new GameMap();
		this.d_players = new HashMap<>();
		this.d_isCountriesAssigned = false;
		this.d_mapReader = new MapReader();
		this.d_displayMap = new MapDisplay();
		this.d_reinforcements = new Reinforcements();
	}

	/**
	 * The function "loadMap" loads a map file using a MapReader object and assigns
	 * the loaded map to the "this.d_gameMap" variable.
	 *
	 * @author Sherwyn Dsouza
	 * @param p_fileName
	 *            The parameter "p_fileName" is a String that represents the name of
	 *            the file that contains the map data.
	 */
	public void loadMap(String p_fileName) {
		this.d_mapReader = new MapReader();
		this.d_gameMap = d_mapReader.loadMapFile(p_fileName);
		this.d_mapEditor = new MapEditor(this.d_gameMap);
	}

	/**
	 *
	 * The function "showMap" displays the map in a tabular form with info such as
	 * continent id, country, bonus, etc.
	 *
	 * @author Sherwyn Dsouza
	 */
	public void showMap() {
		this.d_displayMap.formatMap(this.d_gameMap, this.d_players.size() > 0 && this.d_isCountriesAssigned);
	}

	/**
	 * The function creates a player with a given name and adds it to a map of
	 * players, checking for duplicate names.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player being created.
	 * @author Sherwyn Dsouza
	 */
	public void createPlayer(String p_playerName) {
		try {
			Player l_player = new Player(p_playerName.trim(), new ArrayList<Country>(), new ArrayList<Order>(), 0);
			if (!this.d_players.containsKey(p_playerName.trim())) {
				this.d_players.put(p_playerName, l_player);
			} else {
				System.out.println(Constants.GAME_ENGINE_ERROR_PLAYER_NAME_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_ERROR_ADD_PLAYER);
		}
	}

	/**
	 * The function removes a player from a list of players in a game engine.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player that needs to be removed.
	 *
	 * @author Sherwyn Dsouza
	 */
	public void removePlayer(String p_playerName) {
		try {
			p_playerName = p_playerName.trim();
			List<String> l_playerNames = new ArrayList<>(this.d_players.keySet());
			if (l_playerNames.size() > 1) {
				List<Country> l_ownedCounriesOfPlayer = this.d_players.get(p_playerName).getCountriesOwned();

				l_playerNames.remove(p_playerName);
				int i = 0;

				while (i < l_ownedCounriesOfPlayer.size()) {
					this.d_players.get(l_playerNames.get(i % l_playerNames.size())).getCountriesOwned()
							.add(l_ownedCounriesOfPlayer.get(i));
					i += 1;
				}

				for (Player l_player : this.d_players.values()) {
					this.d_reinforcements.reinforcementPhase(l_player, this.d_gameMap);
				}
			}
			this.d_players.remove(p_playerName.trim());
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_ERROR_REMOVE_PLAYER);
		}
	}

	/**
	 * The function "showAllPlayers" prints the names of all players and the
	 * countries they own.
	 *
	 * @author Sherwyn Dsouza
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
	 * player receives at least one country.
	 *
	 * @author Sherwyn Dsouza
	 */
	public void assignCountries() {
		try {
			List<String> l_playerNames = new ArrayList<>(this.d_players.keySet());
			int l_noOfPlayers = this.d_players.size();

			if (l_noOfPlayers > this.d_gameMap.getCountries().size()) {
				System.out.format(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES, this.d_gameMap.getCountries().size(),
						l_noOfPlayers);
				return;
			}

			int i = 0;
			while (i < this.d_gameMap.getCountries().size()) {
				String l_playerName = l_playerNames.get(i % l_noOfPlayers);
				this.d_players.get(l_playerName).getCountriesOwned().add(this.d_gameMap.getCountries().get(i + 1));
				this.d_gameMap.getCountries().get(i + 1).setOwner(this.d_players.get(l_playerName));
				i += 1;
			}

			for (Player l_player : this.d_players.values()) {
				this.d_reinforcements.reinforcementPhase(l_player, this.d_gameMap);
			}

			this.d_isCountriesAssigned = true;
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES);
		}
	}

	/**
	 * The function returns the number of players in a game.
	 *
	 * @return The number of players in the list "this.d_players".
	 *
	 * @author Sherwyn Dsouza
	 */
	public Integer getNoOfPlayers() {
		return this.d_players.size();
	}

	/**
	 * The function returns the game map.
	 *
	 * @return The method is returning an object of type GameMap.
	 *
	 * @author Sherwyn Dsouza
	 */
	public GameMap getGameMap() {
		return this.d_gameMap;
	}

	/**
	 * The function returns the details of a player based on their name.
	 *
	 * @param p_playerName
	 *            The name of the player for which you want to retrieve the details.
	 * @return The method is returning a Player object.
	 *
	 * @author Sherwyn Dsouza
	 */
	public Player getPlayerDetails(String p_playerName) {
		return this.d_players.get(p_playerName);
	}

	public void editMap() {
		// todo: Add logic for edit map
	}

	/**
	 * The function adds a continent to a map editor and prints the output.
	 *
	 * @param p_continentId
	 *            The p_continentId parameter is an integer that represents the
	 *            unique identifier for the continent.
	 * @param p_ContinentName
	 *            The parameter "p_ContinentName" is a String that represents the
	 *            name of the continent that you want to add.
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
	 *            The p_countryId parameter is an integer that represents the unique
	 *            identifier for the country being added.
	 * @param p_countryName
	 *            The parameter "p_countryName" is a String that represents the name
	 *            of the country that you want to add.
	 * @param p_continentId
	 *            The p_continentId parameter is an integer that represents the ID
	 *            of the continent to which the country belongs.
	 *
	 * @author Sherwyn Dsouza
	 */
	public void addCountry(String p_countryName, String p_continentName) {
		try {
			String l_output = this.d_mapEditor.addCountry(p_countryName, p_continentName);
			System.out.println(l_output);
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a continent from a map in a game editor.
	 *
	 * @param p_continentId
	 *            The parameter "p_continentId" is an integer that represents the ID
	 *            of the continent that needs to be removed.
	 *
	 * @author Sherwyn Dsouza
	 */
	public void removeContinent(int p_continentId) {
		try {
			String l_output = this.d_mapEditor.removeContinent(p_continentId);
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
	 *            The parameter `p_countryId` is an integer representing the ID of
	 *            the country that needs to be removed.
	 *
	 * @author Sherwyn Dsouza
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
			e.printStackTrace();
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function adds a neighbor country to a given country in a map editor.
	 *
	 * @param p_countryId
	 *            The p_countryId parameter represents the ID of the country to
	 *            which you want to add a neighbor.
	 * @param p_neighbourCountryId
	 *            The parameter "p_neighbourCountryId" represents the ID of the
	 *            neighboring country that you want to add to the country with the
	 *            ID "p_countryId".
	 *
	 * @author Sherwyn Dsouza
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
	 *            The p_countryId parameter represents the ID of the country from
	 *            which you want to remove a neighbor.
	 * @param p_neighbourCountryId
	 *            The parameter "p_neighbourCountryId" represents the ID of the
	 *            neighbor country that you want to remove from the country with ID
	 *            "p_countryId".
	 *
	 * @author Sherwyn Dsouza
	 */
	public void removeNeighbor(int p_countryId, int p_neighbourCountryId) {
		try {
			String l_output = this.d_mapEditor.removeNeighbour(p_countryId, p_neighbourCountryId);
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
	 *            The ID of the country that needs to be removed from the player's
	 *            list of owned countries.
	 * @param p_player
	 *            The parameter "p_player" is of type Player and represents the
	 *            player from whom the country needs to be removed.
	 *
	 * @author Sherwyn Dsouza
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
	 * @author Sherwyn Dsouza
	 */
	public void checkIfMapIsValid() {
		try {
			if (this.d_gameMap.isMapCreated()) {
				if (MapValidator.isMapCorrect(this.d_gameMap)) {
					System.out.println(Constants.GAME_ENGINE_MAP_VALID);
				} else {
					System.out.println(Constants.GAME_ENGINE_MAP_INVALID);
				}
			} else {
				System.out.println(Constants.GAME_ENGINE_MAP_NOT_CREATED);
			}
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_FAILED_TO_VALIDATE_MAP);
		}
	}

	/**
	 * The function checks if the game can begin by verifying if the map is created,
	 * there are at least two players, and countries have been assigned.
	 *
	 * @return The method is returning a boolean value.
	 */
	private boolean checkIfGameCanBegin() {
		return this.d_gameMap.isMapCreated() && this.d_players.size() > 1 && this.d_isCountriesAssigned;
	}

	/**
	 * The startGameLoop function checks if the game can begin and then iterates
	 * through the players, allowing each player to issue an order.
	 */
	public void startGameLoop() {
		if (!checkIfGameCanBegin()) {
			System.out.println(Constants.GAME_ENGINE_CANNOT_START_GAME);
		} else {
			List<String> l_playerName = new ArrayList<>(this.d_players.keySet());
			int i = 0;
			while (l_playerName.size() > 0) {
				Player l_player = this.d_players.get(l_playerName.get(i % l_playerName.size()));
				if (l_player.getLeftoverArmies() == 0) {
					l_playerName.remove(i % l_playerName.size());
				} else {
					System.out.println(Constants.CLI_ISSUE_ORDER_PLAYER + l_player.getName().toString() + ":");
					l_player.issueOrder();
				}
				i += 1;
			}
			System.out.println("Executing orders now...");
			startExecution();
		}
	}

	/**
	 * The startExecution function iterates through each player and executes their
	 * orders.
	 */
	private void startExecution() {
		for (Player l_player : this.d_players.values()) {
			while (l_player.getOrders().size() != 0) {
				l_player.nextOrder().execute();
			}
		}
	}
}
