package com.w10.risk_game.models.phases;

import com.w10.risk_game.engines.SinglePlayerEngine;
import com.w10.risk_game.models.Phase;

/**
 * The MapEditorPhase class is an abstract class that extends the Phase class
 * and provides default implementations for various methods related to map
 * editing in a game.
 *
 * @author Omnia Alam
 */
public abstract class MapEditorPhase extends Phase {

	/**
	 * The constructor for the MapEditorPhase class.
	 *
	 * @param p_gameEngine
	 *            The parameter p_gameEngine is a GameEngine object.
	 */
	public MapEditorPhase(SinglePlayerEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * The createPlayer function prints an invalid command message because it isn't
	 * in a correct phase.
	 *
	 * @param p_playerName
	 *            The parameter "p_playerName" is a String that represents the name
	 *            of the player that you want to create.
	 *
	 * @param p_playerStrategy
	 *            The strategy of the player
	 */
	@Override
	public void createPlayer(String p_playerName, String p_playerStrategy) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function prints an invalid command message because it isn't in a correct
	 * phase.
	 *
	 * @param p_playerName
	 *            The name of the player that needs to be removed.
	 */
	@Override
	public void removePlayer(String p_playerName) {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function prints an invalid command message because it isn't in a correct
	 * phase.
	 */
	@Override
	public void showAllPlayers() {
		this.printInvalidCommandMessage();
	}

	/**
	 * The function prints an invalid command message because it isn't in a correct
	 * phase and returns false.
	 *
	 * @return The method is returning a boolean value of false.
	 */
	@Override
	public boolean assignCountries() {
		this.printInvalidCommandMessage();
		return false;
	}

}
