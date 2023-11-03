package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;
import com.w10.risk_game.controllers.GameEngineController;
import com.w10.risk_game.utils.Constants;

public class FortificationPhase extends MainPlayPhase {

	public FortificationPhase(GameEngine p_GameEngine) {
		super(p_GameEngine);
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
		if (p_orderType.equals(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE)) {
			// todo: call advance method of player
			System.out.println("attack!");
		} else {
			// todo: call bomb method of player
			System.out.println("bomb!");
		}
	}

	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new ReinforcementPhase(d_gameEngine));
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.BLOCKADE, Command.SHOW_MAP);
	}
}
