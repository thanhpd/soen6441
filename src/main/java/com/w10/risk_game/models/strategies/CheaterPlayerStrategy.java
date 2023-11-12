package com.w10.risk_game.models.strategies;

import com.w10.risk_game.models.Player;

public class CheaterPlayerStrategy extends PlayerStrategy{

    public CheaterPlayerStrategy(Player p_player) {
        super(p_player);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean issueDeployOrder(String[] p_inputArray) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'issueDeployOrder'");
    }

    @Override
    public boolean issueAdvanceOrder(String[] p_inputArray) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'issueAdvanceOrder'");
    }

    @Override
    public void issueOrder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'issueOrder'");
    }
    
}
