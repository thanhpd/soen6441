package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.MapDisplay;
import com.w10.risk_game.utils.MapEditor;
import com.w10.risk_game.utils.MapReader;

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

	/**
	 * Game Engine constructor
	 */
	public GameEngine() {
		d_gameMap = new GameMap();
		d_players = new HashMap<>();
	}

	/**
	 * The function "loadMap" loads a map file using a MapReader object and assigns
	 * the loaded map to the "d_gameMap" variable.
	 *
	 * @author Sherwyn Dsouza
	 * @param p_fileName
	 *            The parameter "p_fileName" is a String that represents the name of
	 *            the file that contains the map data.
	 */
	public void loadMap(String p_fileName) {
		MapReader l_mapReader = new MapReader();
		d_gameMap = l_mapReader.loadMapFile(p_fileName);
		d_mapEditor = new MapEditor(d_gameMap);
	}

	/**
	 * The function "showMap" is a placeholder for adding logic to display a map.
	 */
	public void showMap() {
		MapDisplay l_displayMap = new MapDisplay();
		l_displayMap.formatMap(d_gameMap, true);
	}

	/**
	 * The function creates a player with a given name and adds it to a map of
	 * players, checking for duplicate names.
	 *
	 * @author Sherwyn Dsouza
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player being created.
	 */
	public void createPlayer(String p_playerName) {
		try {
			Player l_player = new Player(p_playerName.trim(), new ArrayList<Country>(), null, 0);
			if (!d_players.containsKey(p_playerName.trim())) {
				d_players.put(p_playerName, l_player);
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
	 * @author Sherwyn Dsouza
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player that needs to be removed.
	 */
	public void removePlayer(String p_playerName) {
		try {
			p_playerName = p_playerName.trim();
			List<String> l_playerNames = new ArrayList<>(d_players.keySet());
			if (l_playerNames.size() > 1) {
				List<Country> l_ownedCounriesOfPlayer = d_players.get(p_playerName).getCountriesOwned();

				l_playerNames.remove(p_playerName);
				int i = 0;

				while (i < l_ownedCounriesOfPlayer.size()) {
					d_players.get(l_playerNames.get(i % l_playerNames.size())).getCountriesOwned()
							.add(l_ownedCounriesOfPlayer.get(i));
					i += 1;
				}
			}
			d_players.remove(p_playerName.trim());
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
		d_players.forEach((p_playerName, p_player) -> {
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
			d_players.forEach((p_playerName, p_player) -> {
				p_player.setCountriesOwned(new ArrayList<Country>());
			});
			Map<Integer, Country> l_countries = d_gameMap.getCountries();
			List<String> l_playerNames = new ArrayList<>(d_players.keySet());
			int l_noOfPlayers = d_players.size();

			if (l_noOfPlayers > l_countries.size()) {
				System.out.format(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES, l_countries.size(), l_noOfPlayers);
				return;
			}

			int i = 0;
			while (i < l_countries.size()) {
				String l_playerName = l_playerNames.get(i % l_noOfPlayers);
				d_players.get(l_playerName).getCountriesOwned().add(l_countries.get(i + 1));
				i += 1;
			}

		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES);
		}
	}

	/**
	 * The function returns the number of players in a game.
	 *
	 * @return The number of players in the list "d_players".
	 * @author Sherwyn Dsouza
	 */
	public Integer getNoOfPlayers() {
		return d_players.size();
	}

	/**
	 * The function returns the game map.
	 *
	 * @return The method is returning an object of type GameMap.
	 * @author Sherwyn Dsouza
	 */
	public GameMap getGameMap() {
		return d_gameMap;
	}

	/**
	 * The function returns the details of a player based on their name.
	 *
	 * @param p_playerName
	 *            The name of the player for which you want to retrieve the details.
	 * @return The method is returning a Player object.
	 * @author Sherwyn Dsouza
	 */
	public Player getPlayerDetails(String p_playerName) {
		return d_players.get(p_playerName);
	}

}
