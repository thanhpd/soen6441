package com.w10.risk_game.models.strategies;

import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The PlayerStrategy class is an abstract class that provides a framework for
 * implementing different strategies for a player in a game.
 */
public abstract class PlayerStrategy {

	protected static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	protected Player d_Player;

	public PlayerStrategy(Player p_player) {
		this.d_Player = p_player;
	}

	public abstract boolean issueDeployOrder(String[] p_inputArray);
	public abstract boolean issueAdvanceOrder(String[] p_inputArray);
	public abstract void issueOrder();
}
