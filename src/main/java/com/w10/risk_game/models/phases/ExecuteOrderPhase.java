package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.commands.Command;
import com.w10.risk_game.engines.SinglePlayerEngine;

/**
 * The ExecuteOrderPhase class is a subclass of GamePlayPhase that represents
 * the phase of the game where player orders are executed.
 *
 * @author Sherwyn Dsouza
 */
public class ExecuteOrderPhase extends GamePlayPhase {

	/**
	 * The constructor for the ExecuteOrderPhase class.
	 *
	 * @param p_gameEngine
	 *            The parameter p_gameEngine is a GameEngine object.
	 */
	protected ExecuteOrderPhase(SinglePlayerEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * The loadMap function prints an invalid command message.
	 *
	 * @param p_filePath
	 *            The parameter "p_filePath" is a String that represents the file
	 *            path of the map that needs to be loaded.
	 */
	@Override
	public void loadMap(String p_filePath) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The showMap() function prints an invalid command message.
	 */
	@Override
	public void showMap() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function editMap prints an invalid command message and returns false.
	 *
	 * @param p_mapFilePath
	 *            The file path of the map that needs to be edited.
	 * @return The method is returning a boolean value of false.
	 */
	@Override
	public boolean editMap(String p_mapFilePath) {
		this.printInvalidCommandMessage();
		return false;
	}

	/**
	 * The addCountry function prints an invalid command message.
	 *
	 * @param p_countryId
	 *            The unique identifier for the country. It could be an integer
	 *            value.
	 * @param p_countryName
	 *            The parameter p_countryName is a String that represents the name
	 *            of the country that you want to add.
	 * @param p_continentName
	 *            The parameter "p_continentName" is a String that represents the
	 *            name of the continent where the country belongs to.
	 */
	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.printInvalidCommandMessage();

	}

	/**
	 * The function addContinent takes a continent name and bonus value as
	 * parameters and prints an invalid command message.
	 *
	 * @param p_continentName
	 *            The name of the continent that you want to add. It is of type
	 *            String.
	 * @param p_bonus
	 *            The p_bonus parameter is an integer that represents the bonus
	 *            value associated with the continent.
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.printInvalidCommandMessage();

	}

	/**
	 * The addNeighbor function prints an invalid command message.
	 *
	 * @param p_countryId
	 *            The ID of the country to which you want to add a neighbor.
	 * @param p_neighborCountryId
	 *            The parameter "p_neighborCountryId" is an integer that represents
	 *            the ID of a neighboring country.
	 */
	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function removes a continent, but it currently only prints an invalid
	 * command message.
	 *
	 * @param p_continentName
	 *            The parameter "p_continentName" is a String that represents the
	 *            name of the continent that needs to be removed.
	 */
	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function "removeNeighbor" prints an invalid command message.
	 *
	 * @param p_countryId
	 *            The ID of the country from which you want to remove a neighbor.
	 * @param p_neighborCountryId
	 *            The parameter "p_neighborCountryId" represents the ID of the
	 *            neighbor country that needs to be removed from the list of
	 *            neighbors of a particular country.
	 */
	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function "saveMap" prints an invalid command message.
	 *
	 * @param p_mapFilePath
	 *            The parameter "p_mapFilePath" is a String that represents the file
	 *            path where the map will be saved.
	 * @param p_mapType
	 *            The parameter "p_mapType" is a String that represents the type of
	 *            map that needs to be saved.
	 */
	@Override
	public void saveMap(String p_mapFilePath, String p_mapType) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function "createPlayer" prints an invalid command message.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player that needs to be created.
	 *
	 * @param p_playerStrategy
	 *            The strategy of the player
	 */
	@Override
	public void createPlayer(String p_playerName, String p_playerStrategy) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function "removePlayer" prints an invalid command message.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player that needs to be removed.
	 */
	@Override
	public void removePlayer(String p_playerName) {
		this.printInvalidCommandMessage();

	}

	/**
	 * The function "showAllPlayers" prints an invalid command message.
	 */
	@Override
	public void showAllPlayers() {
		this.printInvalidCommandMessage();

	}

	/**
	 * The function "assignCountries" prints an invalid command message and returns
	 * false.
	 *
	 * @return The method is returning a boolean value of false.
	 */
	@Override
	public boolean assignCountries() {
		this.printInvalidCommandMessage();
		return false;
	}

	/**
	 * The endGame() function prints an invalid command message.
	 */
	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The nextPhase() function prints an invalid command message.
	 */
	@Override
	public void nextPhase() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function "issuePlayerOrder" prints an invalid command message.
	 */
	@Override
	public void issuePlayerOrder() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function executes all player orders and moves to the next turn.
	 */
	@Override
	public void executeAllPlayerOrders() {
		this.d_gamePlayController.executePlayerOrders();
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

	/**
	 * The "next" function sets the game engine's phase to the reinforcement phase.
	 */
	@Override
	public void next() {
		d_gameEngine.SetPhase(new ReinforcementPhase(d_gameEngine));
	}

	/**
	 * The function returns a set containing a single command, which is "none".
	 *
	 * @return A Set of Command objects is being returned.
	 */
	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.none);
	}
}
