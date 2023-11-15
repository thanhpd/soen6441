package com.w10.risk_game.models;

import java.util.Set;

import com.w10.risk_game.commands.Command;
import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.engine.GameEngine;

/**
 * The `Phase` class is an abstract class that represents a phase in a Risk game
 * and provides methods for various game commands.
 *
 * @author Omnia Alam, Sherwyn Dsouza
 */
public abstract class Phase {
	protected GameEngine d_gameEngine;
	protected GamePlayController d_gamePlayController;
	protected MapEditorController d_mapEditorController;

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * The constructor for the Phase class.
	 *
	 * @param p_gameEngine
	 *            The parameter p_gameEngine is a GameEngine object.
	 */
	protected Phase(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
		this.d_gamePlayController = p_gameEngine.getGame();
		this.d_mapEditorController = p_gameEngine.getMapEditorController();
	}

	// map commands
	/**
	 * The function is an abstract method that loads a map from a file.
	 *
	 * @param p_filePath
	 *            The file path of the map file that needs to be loaded.
	 */
	public abstract void loadMap(String p_filePath);

	/**
	 * The abstract method "showMap()" is declared without an implementation.
	 */
	public abstract void showMap();

	// map editor commands
	/**
	 * The function is an abstract method that takes a file path as input and
	 * returns a boolean indicating whether the map was successfully edited.
	 *
	 * @param p_mapFilePath
	 *            The file path of the map that needs to be edited.
	 * @return A boolean value is being returned.
	 */
	public abstract boolean editMap(String p_mapFilePath);

	/**
	 * The function adds a country to a collection with the specified country ID,
	 * country name, and continent name.
	 *
	 * @param p_countryId
	 *            The unique identifier for the country.
	 * @param p_countryName
	 *            The name of the country you want to add.
	 * @param p_continentName
	 *            The parameter "p_continentName" is a String that represents the
	 *            name of the continent to which the country belongs.
	 */
	public abstract void addCountry(int p_countryId, String p_countryName, String p_continentName);

	/**
	 * This function adds a continent with a given name and bonus to a game.
	 *
	 * @param p_continentName
	 *            The name of the continent that you want to add.
	 * @param p_bonus
	 *            The p_bonus parameter represents the bonus value associated with
	 *            the continent.
	 */
	public abstract void addContinent(String p_continentName, int p_bonus);

	/**
	 * The function adds a neighbor country to a given country.
	 *
	 * @param p_countryId
	 *            The ID of the country to which you want to add a neighbor.
	 * @param p_neighborCountryId
	 *            The ID of the neighboring country that you want to add.
	 */
	public abstract void addNeighbor(int p_countryId, int p_neighborCountryId);

	/**
	 * The function removes a country based on its ID.
	 *
	 * @param p_countryId
	 *            The parameter "p_countryId" is the unique identifier of the
	 *            country that you want to remove.
	 */
	public abstract void removeCountry(int p_countryId);

	/**
	 * The function removes a continent from a collection.
	 *
	 * @param p_continentName
	 *            The name of the continent that you want to remove.
	 */
	public abstract void removeContinent(String p_continentName);

	/**
	 * The function removes a neighbor from a country.
	 *
	 * @param p_countryId
	 *            The ID of the country from which you want to remove a neighbor.
	 * @param p_neighborCountryId
	 *            The ID of the neighbor country that you want to remove from the
	 *            list of neighbors for the specified country.
	 */
	public abstract void removeNeighbor(int p_countryId, int p_neighborCountryId);

	/**
	 * The function saves a map to a specified file path.
	 *
	 * @param p_mapFilePath
	 *            The file path where the map will be saved.
	 */
	public abstract void saveMap(String p_mapFilePath);

	/**
	 * The function is an abstract method that checks if a map is valid and returns
	 * a boolean value.
	 *
	 * @return A boolean value is being returned.
	 */
	public abstract boolean checkIfMapIsValid();

	/**
	 * The function "nextPhase" is an abstract method that does not have an
	 * implementation and must be defined in a subclass.
	 */
	public abstract void nextPhase();

	// game play commands
	/**
	 * The function creates a player with the given player name.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player that you want to create.
	 */
	public abstract void createPlayer(String p_playerName);

	/**
	 * The function removes a player from a game.
	 *
	 * @param p_playerName
	 *            The name of the player that you want to remove.
	 */
	public abstract void removePlayer(String p_playerName);

	/**
	 * The abstract method "showAllPlayers()" is declared without an implementation.
	 */
	public abstract void showAllPlayers();

	// startup phase commands
	/**
	 * The function "assignCountries" is an abstract method that returns a boolean
	 * value.
	 *
	 * @return A boolean value is being returned.
	 */
	public abstract boolean assignCountries();

	// reinforcement phase commands
	/**
	 * The function is an abstract method that returns a boolean value indicating
	 * whether or not player reinforcements have been assigned.
	 *
	 * @return A boolean value is being returned.
	 */
	public abstract boolean assignPlayerReinforcements();

	// issue order commands
	/**
	 * The function "issuePlayerOrder" is an abstract method that does not have an
	 * implementation and must be implemented by any class that extends the abstract
	 * class containing this method.
	 */
	public abstract void issuePlayerOrder();

	// execute order commands
	/**
	 * The function "executeAllPlayerOrders" is an abstract method that does not
	 * have an implementation and must be implemented by any class that extends the
	 * abstract class containing this method.
	 */
	public abstract void executeAllPlayerOrders();

	// end command
	/**
	 * The "endGame" function is an abstract method that does not have an
	 * implementation and must be defined in a subclass.
	 */
	/**
	 * The "endGame" function is an abstract method that does not have an
	 * implementation and must be defined in a subclass.
	 */
	public abstract void endGame();

	/**
	 * The above function is an abstract method that represents the concept of
	 * moving to the next element in a sequence.
	 */
	public abstract void next();

	/**
	 * The function returns a set of available commands.
	 *
	 * @return The method is returning a Set of Command objects.
	 */
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
		Logger.log("Invalid command in state " + getPhaseName());
	}

	/**
	 * The function returns the name of the class without the package name.
	 *
	 * @return The method is returning the name of the class without the package
	 *         name.
	 */
	private String getClassName() {
		String name = this.getClass().getName();
		return name.substring(name.lastIndexOf('.') + 1, name.length());
	}

	/**
	 * The function prints the available commands in a specific phase.
	 */
	public void printAvailableCommand() {
		String avaliableCommandsText = getAvailableCommands().toString();
		Logger.log("\nYou are in the " + getPhaseName().toUpperCase() + ". Commands avaliable are: "
				+ avaliableCommandsText);
	}
}
