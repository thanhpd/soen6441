package com.w10.risk_game.models;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;
import com.w10.risk_game.controllers.RiskGame;

public abstract class Phase {
	protected GameEngine d_gameEngine;
	protected RiskGame d_Game;
	public Phase(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
		this.d_Game = p_gameEngine.getGame();
	}

	// map commands
	public abstract void loadMap(String p_filePath);

	public abstract void showMap();

	// map editor commands
	public abstract boolean editMap(String p_mapFilePath);

	public abstract void addCountry(int p_countryId, String p_countryName, String p_continentName);

	public abstract void addContinent(String p_continentName, int p_bonus);

	public abstract void addNeighbor(int p_countryId, int p_neighborCountryId);

	public abstract void removeCountry(int p_countryId);

	public abstract void removeContinent(String p_continentName);

	public abstract void removeNeighbor(int p_countryId, int p_neighborCountryId);

	public abstract void saveMap(String p_mapFilePath);

	// game play commands
	public abstract void createPlayer(String p_playerName);

	public abstract void removePlayer(String p_playerName);

	public abstract void showAllPlayers();

	// startup phase commands
	public abstract boolean assignCountries();

	// TODO: remove?
	public abstract boolean checkIfMapIsValid();

	public abstract boolean checkIfGameCanBegin();

	public abstract boolean executePlayerOrders();

	public abstract boolean checkIfOrdersCanBeExecuted();

	// TODO: Decide if the following methods needs to be used
	// // reinforcement commands
	// abstract public void reinforce();

	// // attack commands
	// abstract public void attack();

	// // fortify commands
	// abstract public void fortify();

	// // end command
	// abstract public void endGame();

	abstract public void next();

	public abstract Set<Command> getAvailableCommands();

	public String getPhaseName() {
		return getClassName();
	}

	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + getPhaseName());
	}

	private String getClassName() {
		var name = this.getClass().getName();
		return name.substring(name.lastIndexOf('.') + 1, name.length());
	}

	public void printAvailableCommand() {
		String avaliableCommandsText = getAvailableCommands().toString();//// String.join('-',
																			//// getAvaliableCommands().to);
		System.out.println("You are in " + getPhaseName() + " Phase. Command avaliable " + avaliableCommandsText);
	}
}
