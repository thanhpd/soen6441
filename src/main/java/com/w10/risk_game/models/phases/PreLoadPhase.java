package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

/**
 * The PreLoadPhase class is a subclass of MapEditorPhase that handles the
 * pre-loading phase of a game, allowing the user to load a map file and
 * transition to the next phase.
 *
 * @author Omnia Alam
 */
public class PreLoadPhase extends MapEditorPhase {

	/**
	 * The constructor for the PreLoadPhase class.
	 *
	 * @param p_gameEngine
	 *                     The parameter p_gameEngine is a GameEngine object.
	 */
	public PreLoadPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * The loadMap function loads a map from a file path and then proceeds to the
	 * next step.
	 *
	 * @param p_filePath
	 *                   The parameter `p_filePath` is a String that represents the
	 *                   file
	 *                   path of the map that needs to be loaded.
	 */
	@Override
	public void loadMap(String p_filePath) {
		this.d_mapEditorController.loadMap(p_filePath);
		next();
	}

	/**
	 * The next() function sets the phase of the game engine to the PostLoadPhase.
	 */
	public void next() {
		if (this.d_mapEditorController.checkIfMapIsValid()) {
			d_gameEngine.setPhase(new PostLoadPhase(d_gameEngine));
		}

	}

	/**
	 * The addCountry function prints an invalid command message.
	 *
	 * @param p_countryId
	 *                        The unique identifier for the country. It could be an
	 *                        integer
	 *                        value.
	 * @param p_countryName
	 *                        The name of the country that you want to add.
	 * @param p_continentName
	 *                        The p_continentName parameter is a String that
	 *                        represents the name
	 *                        of the continent where the country is located.
	 */
	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		super.printInvalidCommandMessage();
	}

	/**
	 * The addContinent function prints an invalid command message.
	 *
	 * @param p_continentName
	 *                        The name of the continent that you want to add.
	 * @param p_bonus
	 *                        The p_bonus parameter is an integer that represents
	 *                        the bonus
	 *                        value associated with the continent.
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		super.printInvalidCommandMessage();
	}

	/**
	 * The addNeighbor function prints an invalid command message.
	 *
	 * @param p_countryId
	 *                            The ID of the country to which you want to add a
	 *                            neighbor.
	 * @param p_neighborCountryId
	 *                            The parameter "p_neighborCountryId" is an integer
	 *                            that represents
	 *                            the ID of a neighboring country.
	 */
	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		super.printInvalidCommandMessage();
	}

	/**
	 * The removeCountry function prints an invalid command message.
	 *
	 * @param p_countryId
	 *                    The parameter p_countryId is an integer that represents
	 *                    the ID of
	 *                    the country that needs to be removed.
	 */
	@Override
	public void removeCountry(int p_countryId) {
		super.printInvalidCommandMessage();
	}

	/**
	 * The function removes a continent by printing an invalid command message.
	 *
	 * @param p_continentName
	 *                        The parameter p_continentName is a String that
	 *                        represents the name
	 *                        of the continent that needs to be removed.
	 */
	@Override
	public void removeContinent(String p_continentName) {
		super.printInvalidCommandMessage();
	}

	/**
	 * The function nextPhase() prints an invalid command message.
	 */
	@Override
	public void nextPhase() {
		super.printInvalidCommandMessage();
	}

	/**
	 * The removeNeighbor function prints an invalid command message.
	 *
	 * @param p_countryId
	 *                            The ID of the country from which you want to
	 *                            remove a neighbor.
	 * @param p_neighborCountryId
	 *                            The parameter "p_neighborCountryId" represents the
	 *                            ID of the
	 *                            neighbor country that you want to remove from the
	 *                            list of
	 *                            neighbors of a specific country.
	 */
	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		super.printInvalidCommandMessage();
	}

	/**
	 * The function saves a map to a specified file path and prints an invalid
	 * command message.
	 *
	 * @param p_mapFilePath
	 *                      The file path where the map will be saved.
	 */
	@Override
	public void saveMap(String p_mapFilePath) {
		super.printInvalidCommandMessage();
	}

	/**
	 * The showMap() function calls the printInvalidCommandMessage() function from
	 * the superclass.
	 */
	@Override
	public void showMap() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The endGame() function prints an invalid command message.
	 */
	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function returns a set of available commands, with only the "loadmap"
	 * command included.
	 *
	 * @return A Set of Command objects is being returned.
	 */
	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.loadmap);
	}

	/**
	 * The function checks if map is valid and prints an invalid command message if
	 * not.
	 *
	 * @return The method is returning a boolean value of false.
	 */
	@Override
	public boolean checkIfMapIsValid() {
		this.printInvalidCommandMessage();
		return false;
	}

	/**
	 * The function "issuePlayerOrder" prints an invalid command message.
	 */
	@Override
	public void issuePlayerOrder() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function executes all player orders and prints an invalid command
	 * message.
	 */
	@Override
	public void executeAllPlayerOrders() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function "assignPlayerReinforcements" prints an invalid command message
	 * and returns false.
	 *
	 * @return The method is returning a boolean value of false.
	 */
	@Override
	public boolean assignPlayerReinforcements() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean editMap(String p_mapFilePath) {
		this.printInvalidCommandMessage();
		return false;
	}
}
