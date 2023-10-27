package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;
import com.w10.risk_game.models.Phase;

public abstract class GamePlayPhase implements Phase {

    abstract void performAction(GameEngine p_engine);
}
