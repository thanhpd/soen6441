package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;

public class ExecuteOrderPhase extends GamePlayPhase{
//one way
    @Override
    void performAction(GameEngine p_engine) {
      p_engine.executePlayerOrders();
    }
}
