package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;

public class PlaySetupPhase extends GamePlayPhase {
	public PlaySetupPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
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

	@Override
	public void createPlayer(String p_playerName) {
		this.d_gameEngine.createPlayer(p_playerName);
	}

	@Override
	public void removePlayer(String p_playerName) {
		this.d_gameEngine.removePlayer(p_playerName);
	}

	@Override
	public void showAllPlayers() {
		this.d_gameEngine.showAllPlayers();
	}

	@Override
	public boolean assignCountries() {
		return this.d_gameEngine.assignCountries();
	}

	@Override
	public boolean checkIfGameCanBegin() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'checkIfGameCanBegin'");
	}
}
