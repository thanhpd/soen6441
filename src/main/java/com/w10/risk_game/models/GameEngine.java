package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.Scanner;

import com.w10.risk_game.utils.MapEditor;
import com.w10.risk_game.utils.MapReader;

public class GameEngine {
	private Scanner d_input;
	private GameMap d_gameMap;
	private ArrayList<Player> d_players;

	public GameEngine() {
		d_input = new Scanner(System.in);
		d_gameMap = new GameMap();
		d_players = new ArrayList<>();
	}

	public void loadMap(String p_fileName) {
		MapReader l_mapReader = new MapReader();
		d_gameMap = l_mapReader.loadMapFile(p_fileName);
	}

	public void showMap() {
		// TO-DO: Add show map logic
	}

	public void createPlayer(String p_playerName) {
		Player l_player = new Player(p_playerName, null, null, 0);
		d_players.add(l_player);
	}

	public void removePlayer(String p_playerName) {
		// TO-DO: Add remove player logic
	}

	// public void runStartUpPhase() {
	// runInitializeMapStep();
	// runInitializePlayerStep();
	// }

	public void runGameEngine() {

	}

}
