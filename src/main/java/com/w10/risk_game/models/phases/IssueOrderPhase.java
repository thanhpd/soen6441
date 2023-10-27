package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;


public class IssueOrderPhase extends GamePlayPhase{

    @Override
    void performAction(GameEngine p_engine) {
        p_engine.issuePlayerOrder();
    }
}
