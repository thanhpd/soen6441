package com.w10.risk_game.models;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;
import com.w10.risk_game.controllers.GameEngineController;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import com.w10.risk_game.controllers.MapEditorController;

/**
 * The `Phase` class is an abstract class that represents a phase in a Risk game
 * and provides methods for various game commands.
 */
public abstract class Phase {
	protected GameEngine d_gameEngine;
	protected GameEngineController d_gameEngineController;
	protected MapEditorController d_mapEditorController;

	private final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	protected Phase(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
		this.d_gameEngineController = p_gameEngine.getGame();
		this.d_mapEditorController = p_gameEngine.getMapEditorController();
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

	public abstract boolean checkIfMapIsValid();

	public abstract void nextPhase();

	// game play commands
	public abstract void createPlayer(String p_playerName);

	public abstract void removePlayer(String p_playerName);

	public abstract void showAllPlayers();

	// startup phase commands
	public abstract boolean assignCountries();

	// reinforcement phase commands
	public abstract boolean assignPlayerReinforcements();

	// issue order commands
	public abstract void issuePlayerOrder();

	// execute order commands
	public abstract void executeAllPlayerOrders();

	// end command
	public abstract void endGame();

	public abstract void next();

	public abstract Set<Command> getAvailableCommands();

	/**
	 * The function returns the name of the class.
	 *
	 * @return The method is returning the name of the class.
	 */
	public String getPhaseName() {
		return getClassName().substring(0, getClassName().length() - 5) + " PHASE";
	}

	/**
	 * The function prints an error message indicating an invalid command in the
	 * current state.
	 */
	public void printInvalidCommandMessage() {
		d_logger.log("Invalid command in state " + getPhaseName());
	}

	/**
	 * The function "getClassName" returns the name of the class without the package
	 * name.
	 *
	 * @return The method is returning the name of the class without the package
	 *         name.
	 */
	private String getClassName() {
		String name = this.getClass().getName();
		return name.substring(name.lastIndexOf('.') + 1, name.length());
	}

	/**
	 * The function prints the available commands in the currefnt phase.
	 */
	public void printAvailableCommand() {
		String avaliableCommandsText = getAvailableCommands().toString();
		d_logger.log("\nYou are in the " + getPhaseName().toUpperCase() + ". Commands avaliable are: "
				+ avaliableCommandsText);
	}
}
