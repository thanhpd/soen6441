package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

/**
 * The PostLoadPhase class is a subclass of MapEditorPhase that represents the
 * phase after loading a map in a game editor, allowing for editing and saving
 * of the map.
 */
public class PostLoadPhase extends MapEditorPhase {

	/**
	 * The constructor for the PostLoadPhase class.
	 *
	 * @param p_GameEngine
	 *            The parameter p_GameEngine is a GameEngine object.
	 */
	public PostLoadPhase(GameEngine p_GameEngine) {
		super(p_GameEngine);
	}

	/**
	 * The showMap() function calls the showMap() function in the
	 * d_mapEditorController object with the parameter set to false.
	 */
	@Override
	public void showMap() {
		this.d_mapEditorController.showMap(false);
	}

	/**
	 * The addCountry function adds a country to the map editor controller with the
	 * specified country ID, country name, and continent name.
	 *
	 * @param p_countryId
	 *            The unique identifier for the country being added.
	 * @param p_countryName
	 *            The name of the country that you want to add.
	 * @param p_continentName
	 *            The p_continentName parameter is a String that represents the name
	 *            of the continent where the country belongs to.
	 */
	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.d_mapEditorController.addCountry(p_countryId, p_countryName, p_continentName);
	}

	/**
	 * The function adds a continent with a given name and bonus to the editor
	 * controller.
	 *
	 * @param p_continentName
	 *            The name of the continent that you want to add. It should be a
	 *            String value.
	 * @param p_bonus
	 *            The p_bonus parameter is an integer that represents the bonus
	 *            value for the continent being added.
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.d_mapEditorController.addContinent(p_continentName, p_bonus);
	}

	/**
	 * The addNeighbor function adds a neighbor country to a given country.
	 *
	 * @param p_countryId
	 *            The ID of the country to which you want to add a neighbor.
	 * @param p_neighborCountryId
	 *            The parameter "p_neighborCountryId" represents the ID of the
	 *            neighboring country that you want to add to the current country.
	 */
	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_mapEditorController.addNeighbor(p_countryId, p_neighborCountryId);
	}

	/**
	 * The removeCountry function removes a country based on its ID.
	 *
	 * @param p_countryId
	 *            The parameter p_countryId is an integer that represents the unique
	 *            identifier of the country that needs to be removed.
	 */
	@Override
	public void removeCountry(int p_countryId) {
		this.d_mapEditorController.removeCountry(p_countryId);
	}

	/**
	 * The function removes a continent from the editor controller.
	 *
	 * @param p_continentName
	 *            The parameter "p_continentName" is a String that represents the
	 *            name of the continent that needs to be removed.
	 */
	@Override
	public void removeContinent(String p_continentName) {
		this.d_mapEditorController.removeContinent(p_continentName);
	}

	/**
	 * The removeNeighbor function removes a neighbor country from a given country.
	 *
	 * @param p_countryId
	 *            The ID of the country from which you want to remove a neighbor.
	 * @param p_neighborCountryId
	 *            The parameter "p_neighborCountryId" represents the ID of the
	 *            neighbor country that you want to remove from the country with the
	 *            ID "p_countryId".
	 */
	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_mapEditorController.removeNeighbor(p_countryId, p_neighborCountryId);
	}

	/**
	 * The function saves a map to a specified file path.
	 *
	 * @param p_mapFilePath
	 *            The parameter "p_mapFilePath" is a String that represents the file
	 *            path where the map will be saved.
	 */
	@Override
	public void saveMap(String p_mapFilePath) {
		this.d_mapEditorController.saveMap(p_mapFilePath);
	}

	/**
	 * The loadMap function prints an invalid command message.
	 *
	 * @param p_filePath
	 *            The parameter `p_filePath` is a string that represents the file
	 *            path of the map that needs to be loaded.
	 */
	@Override
	public void loadMap(String p_filePath) {
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
	 * The nextPhase() function sets the phase of the game engine to a new
	 * PlaySetupPhase.
	 */
	@Override
	public void nextPhase() {
		if (this.d_mapEditorController.checkIfMapIsValid()) {
			d_gameEngine.setPhase(new PlaySetupPhase(d_gameEngine));
		}
	}

	/**
	 * The function returns a set of available commands for a map editing program.
	 *
	 * @return A Set of Command objects.
	 */
	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.editmap, Command.editcountry, Command.editcontinent, Command.showmap,
				Command.editneighbor, Command.validatemap, Command.next);
	}

	/**
	 * The function checks if a map is valid.
	 *
	 * @return The method is returning a boolean value.
	 */
	@Override
	public boolean checkIfMapIsValid() {
		return this.d_mapEditorController.checkIfMapIsValid();
	}
	/**
	 * The function is an overridden method that calls itself recursively,
	 * potentially causing an infinite loop.
	 *
	 * @param p_mapFilePath
	 *            The file path of the map that you want to edit.
	 * @return The method is returning a boolean value.
	 */
	@Override
	public boolean editMap(String p_mapFilePath) {
		if (this.d_mapEditorController.editMap(p_mapFilePath)) {
			return true;
		}
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
	 * The "next" function prints an invalid command message.
	 */
	@Override
	public void next() {
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
}
