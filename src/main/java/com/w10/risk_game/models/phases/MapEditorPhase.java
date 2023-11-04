package com.w10.risk_game.models.phases;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.controllers.RiskGameController;
import com.w10.risk_game.models.Phase;

/**
 * The MapEditorPhase class is an abstract class that extends the Phase class
 * and provides default implementations for various methods related to map
 * editing in a game.
 */
public abstract class MapEditorPhase extends Phase {

	public MapEditorPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void createPlayer(String p_playerName) {

		this.printInvalidCommandMessage();
	}

	@Override
	public void removePlayer(String p_playerName) {

		this.printInvalidCommandMessage();
	}

	@Override
	public void showAllPlayers() {

		this.printInvalidCommandMessage();
	}

	@Override
	public boolean assignCountries() {

		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean editMap(String p_mapFilePath) {

		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean checkIfMapIsValid() {

		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean executePlayerOrders() {

		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean checkIfOrdersCanBeExecuted() {

		this.printInvalidCommandMessage();
		return false;
	}
}
