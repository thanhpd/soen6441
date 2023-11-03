package com.w10.risk_game.models.phases;

import com.w10.risk_game.GameEngine;

public class MainPlayPhase extends PlaySetupPhase {

	public MainPlayPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void issueReinforcementOrders(String p_orderType) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void issueAttackOrders(String p_orderType) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void issueFortifyOrders(String p_orderType) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
	}

}
