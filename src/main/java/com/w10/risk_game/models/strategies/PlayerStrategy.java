package com.w10.risk_game.models.strategies;

import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

public abstract class PlayerStrategy {
    
	protected static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();    
    protected Player d_Player;

    public PlayerStrategy(Player p_player) {
        this.d_Player = p_player;
    }

    public abstract boolean issueDeployOrder(String[] p_inputArray);
}
