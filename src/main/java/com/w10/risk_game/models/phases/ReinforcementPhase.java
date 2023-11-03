package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;
import com.w10.risk_game.controllers.GameEngineController;
import com.w10.risk_game.utils.Constants;

public class ReinforcementPhase extends MainPlayPhase {
	public ReinforcementPhase(GameEngine p_GameEngine) {
		super(p_GameEngine);
	}

	@Override
	public void issueReinforcementOrders(String p_orderType) {
		if (p_orderType.equals(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY))
			d_gameEngineController.issuePlayerOrder();
		else if (p_orderType.equals(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT)) {
			// todo: implement airlift
		} else {
			// todo: implement airlift
		}
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

	@Override
	public void showMap() {
		this.d_gameEngineController.showMap();
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new AttackPhase(d_gameEngine));
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.DEPLOY, Command.AIRLIFT, Command.NEGOTIATE, Command.SHOW_MAP);
	}

}
