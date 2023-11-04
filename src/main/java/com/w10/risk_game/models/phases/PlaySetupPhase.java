package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

/**
 * The PlaySetupPhase class is a subclass of GamePlayPhase that handles the
 * setup phase of the game, including creating and removing players, assigning
 * countries, and transitioning to the main play phase.
 */
public class PlaySetupPhase extends GamePlayPhase {
	public PlaySetupPhase(GameEngine d_gameEngine) {
		super(d_gameEngine);
	}
	/**
	 * The showMap() function calls the showMap() function of the d_EditorController
	 * object.
	 */
	@Override
	public void showMap() {
		this.d_EditorController.showMap();
	}
	/**
	 * The function executes player orders and returns false if the command is
	 * invalid.
	 *
	 * @return The method is returning a boolean value of false.
	 */
	@Override
	public boolean executePlayerOrders() {
		this.printInvalidCommandMessage();
		return false;
	}
	/**
	 * The function "checkIfOrdersCanBeExecuted" prints an invalid command message
	 * and returns false.
	 *
	 * @return The method is returning a boolean value of false.
	 */

	@Override
	public boolean checkIfOrdersCanBeExecuted() {
		this.printInvalidCommandMessage();
		return false;
	}

	/**
	 * The function creates a player with the given name.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player that you want to create.
	 */
	@Override
	public void createPlayer(String p_playerName) {
		this.d_Game.createPlayer(p_playerName);
	}

	/**
	 * The removePlayer function removes a player from the game.
	 *
	 * @param p_playerName
	 *            The name of the player that you want to remove from the game.
	 */
	@Override
	public void removePlayer(String p_playerName) {
		this.d_Game.removePlayer(p_playerName);
	}

	/**
	 * The function "showAllPlayers" calls the "showAllPlayers" function of the
	 * "d_Game" object.
	 */
	@Override
	public void showAllPlayers() {
		this.d_Game.showAllPlayers();
	}

	/**
	 * The function "assignCountries" checks if countries can be assigned and
	 * returns true if successful.
	 *
	 * @return The method is returning a boolean value.
	 */
	@Override
	public boolean assignCountries() {

		if (this.d_Game.assignCountries()) {
			next();
			return true;
		}
		return false;
	}

	/**
	 * The function checkIfGameCanBegin is currently empty and returns false.
	 *
	 * @return The method is returning a boolean value of false.
	 */
	@Override
	public boolean checkIfGameCanBegin() {
		// Not sure what to do
		return false;
	}

	/**
	 * The "next" function sets the phase of the game engine to the MainPlayPhase.
	 */
	@Override
	public void next() {
		d_gameEngine.setPhase(new MainPlayPhase(d_gameEngine));
	}

	/**
	 * The function returns a set of available commands.
	 *
	 * @return A Set of Command objects.
	 */
	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.assigncountries, Command.addplayer, Command.removeplayer, Command.showmap);
	}
	/**
	 * The nextPhase() function prints an invalid command message.
	 */
	@Override
	public void nextPhase() {
		this.printInvalidCommandMessage();
	}
}
