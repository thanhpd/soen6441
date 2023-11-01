package com.w10.risk_game.models.phases;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.controllers.RiskGame;
import com.w10.risk_game.models.Phase;

public abstract class MapEditorPhase extends Phase {

	public MapEditorPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void printInvalidCommandMessage() {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void createPlayer(String p_playerName) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void removePlayer(String p_playerName) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void showAllPlayers() {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public boolean assignCountries() {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean editMap(String p_mapFilePath) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean checkIfMapIsValid() {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean executePlayerOrders() {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean checkIfOrdersCanBeExecuted() {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
		return false;
	}
}
