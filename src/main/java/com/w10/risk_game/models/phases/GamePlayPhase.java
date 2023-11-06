package com.w10.risk_game.models.phases;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.models.Phase;

/**
 * The GamePlayPhase class is an abstract class that extends the Phase class and
 * provides default implementations for map-related methods.
 *
 * @author Sherwyn Dsouza
 */
public abstract class GamePlayPhase extends Phase {

	/**
	 * The constructor for the GamePlayPhase class.
	 *
	 * @param p_gameEngine
	 *            The parameter p_gameEngine is a GameEngine object.
	 */
	public GamePlayPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void loadMap(String p_filePath) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(int p_countryId) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function "saveMap" prints an invalid command message.
	 *
	 * @param p_mapFilePath
	 *            The file path where the map will be saved.
	 */

	@Override
	public void saveMap(String p_mapFilePath) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The showMap() function calls the showMap() function of the d_EditorController
	 * object.
	 */
	@Override
	public void showMap() {
		this.d_gameEngineController.showMap();
	}

	/**
	 * The function editMap in Java prints an invalid command message and returns
	 * false.
	 *
	 * @param p_mapFilePath
	 *            The parameter "p_mapFilePath" is a String that represents the file
	 *            path of the map that needs to be edited.
	 * @return The method is returning a boolean value of false.
	 */

	@Override
	public boolean editMap(String p_mapFilePath) {
		this.printInvalidCommandMessage();
		return false;
	}

	/**
	 * The function checkIfMapIsValid() returns false.
	 *
	 * @return The method is returning a boolean value of false.
	 */
	@Override
	public boolean checkIfMapIsValid() {
		return false;
	}

}
