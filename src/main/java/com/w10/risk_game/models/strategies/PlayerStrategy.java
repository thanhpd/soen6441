package com.w10.risk_game.models.strategies;

import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The PlayerStrategy class is an abstract class that provides a framework for
 * implementing different strategies for a player in a game.
 */
public abstract class PlayerStrategy {

	protected static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	protected Player d_player;

	public PlayerStrategy(Player p_player) {
		this.d_player = p_player;
	}

	public abstract void issueOrder();

	public abstract String getStrategyName();

}
