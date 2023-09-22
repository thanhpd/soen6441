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

	private void runInitializeMapStep() {
		System.out.println("Enter name of map file:");
		String l_mapFileName = d_input.nextLine();
		MapReader l_mapReader = new MapReader();
		d_gameMap = l_mapReader.loadMapFile(l_mapFileName);
	}

	private void runInitializePlayerStep() {
		System.out.println("Enter number of players: ");
		Integer l_numberofplayers = Integer.parseInt(d_input.nextLine());
		for (int i = 0; i < l_numberofplayers; i++) {
			System.out.format("Enter name of player %d: ", i + 1);
			String l_playerName = d_input.nextLine();
			Player player = new Player(l_playerName, null, null, i);
			d_players.add(player);
		}
	}

	public void runStartUpPhase() {
		runInitializeMapStep();
		runInitializePlayerStep();
	}

	public void runGameEngine() {

	}

}
