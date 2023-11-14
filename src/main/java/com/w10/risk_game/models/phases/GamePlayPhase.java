package com.w10.risk_game.models.phases;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.models.Phase;

/**
 * The GamePlayPhase class is an abstract class that extends the Phase class and
 * provides default implementations for gameplay-related methods.
 *
 * @author Sherwyn Dsouza
 */
public abstract class GamePlayPhase extends Phase {

	/**
	 * The constructor for the GamePlayPhase class.
	 *
	 * @param p_gameEngine
	 *                     The parameter p_gameEngine is a GameEngine object.
	 */
	public GamePlayPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * The loadMap function prints an invalid command message.
	 *
	 * @param p_filePath
	 *                   The file path of the map that needs to be loaded.
	 */
	@Override
	public void loadMap(String p_filePath) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The addCountry function prints an invalid command message.
	 *
	 * @param p_countryId
	 *                        The unique identifier for the country.
	 * @param p_countryName
	 *                        The name of the country that you want to add.
	 * @param p_continentName
	 *                        The parameter "p_continentName" is a String that
	 *                        represents the
	 *                        name of the continent where the country belongs to.
	 */
	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The addContinent function prints an invalid command message.
	 *
	 * @param p_continentName
	 *                        The name of the continent that you want to add.
	 * @param p_bonus
	 *                        The p_bonus parameter is an integer that represents
	 *                        the bonus
	 *                        value for the continent.
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The addNeighbor function prints an invalid command message.
	 *
	 * @param p_countryId
	 *                            The ID of the country to which you want to add a
	 *                            neighbor.
	 * @param p_neighborCountryId
	 *                            The parameter p_neighborCountryId is an integer
	 *                            that represents
	 *                            the ID of the neighboring country that you want to
	 *                            add.
	 */
	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.printInvalidCommandMessage();
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
		this.printInvalidCommandMessage();
	}

	/**
	 * The function removes a continent, but it currently only prints an invalid
	 * command message.
	 *
	 * @param p_continentName
	 *                        The parameter p_continentName is a String that
	 *                        represents the name
	 *                        of the continent that needs to be removed.
	 */
	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function removes a neighbor from a country.
	 *
	 * @param p_countryId
	 *                            The ID of the country from which you want to
	 *                            remove a neighbor.
	 * @param p_neighborCountryId
	 *                            The ID of the neighbor country that you want to
	 *                            remove.
	 */
	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function "saveMap" prints an invalid command message.
	 *
	 * @param p_mapFilePath
	 *                      The file path where the map will be saved.
	 */

	@Override
	public void saveMap(String p_mapFilePath, String p_mapType) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The showMap() function calls the showMap() function of the d_EditorController
	 * object.
	 */
	@Override
	public void showMap() {
		this.d_gamePlayController.showMap();
	}

	/**
	 * The function editMap prints an invalid command message and returns false.
	 *
	 * @param p_mapFilePath
	 *                      The parameter "p_mapFilePath" is a String that
	 *                      represents the file
	 *                      path of the map that needs to be edited.
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
