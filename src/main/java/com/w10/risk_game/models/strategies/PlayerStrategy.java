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

	/**
	 * The constructor is used to initialize the data member d_player.
	 *
	 * @param p_player
	 *            The Player object.
	 */
	public PlayerStrategy(Player p_player) {
		this.d_player = p_player;
	}

	/**
	 * The issueOrder function is an abstract function that is implemented by the
	 * subclasses of PlayerStrategy.
	 */
	public abstract void issueOrder();

	/**
	 * The getStrategyName function is an abstract function that is implemented by
	 * the subclasses of PlayerStrategy.
	 *
	 * @return The method is returning the name of the strategy.
	 */
	public abstract String getStrategyName();

}
