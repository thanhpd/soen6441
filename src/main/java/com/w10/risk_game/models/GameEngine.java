package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.HashMap;

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
			Player l_player = new Player(p_playerName.trim(), null, null, 0);
			d_players.put(p_playerName, l_player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removePlayer(String p_playerName) {
		try {
			d_players.remove(p_playerName.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showAllPlayers() {
		d_players.forEach((p_playerName, p_player) -> {
			System.out.println(p_playerName);
		});
	}

	public void assignCountries() {
		// TO-DO: Add assign countries logic
	}

}
