package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.MapReader;

public class GameEngine {
	private GameMap d_gameMap;
	private HashMap<String, Player> d_players;

	public GameEngine() {
		d_gameMap = new GameMap();
		d_players = new HashMap<>();
	}

	/**
	 * @param p_fileName
	 */
	public void loadMap(String p_fileName) {
		MapReader l_mapReader = new MapReader();
		d_gameMap = l_mapReader.loadMapFile(p_fileName);
	}

	public void showMap() {
		// TO-DO: Add show map logic
	}

	public void createPlayer(String p_playerName) {
		try {
			List<Country> l_defaultCountryList = new ArrayList<>();
			Player l_player = new Player(p_playerName.trim(), l_defaultCountryList, null, 0);
			if (!d_players.containsKey(p_playerName.trim())) {
				d_players.put(p_playerName, l_player);
			} else {
				System.out.println(Constants.GAME_ENGINE_ERROR_PLAYER_NAME_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_ERROR_ADD_PLAYER);
		}
	}

	public void removePlayer(String p_playerName) {
		try {
			d_players.remove(p_playerName.trim());
		} catch (Exception e) {
			System.out.println(Constants.GAME_ENGINE_ERROR_REMOVE_PLAYER);
		}
	}

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

	public void assignCountries() {
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

	}

}
