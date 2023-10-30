package com.w10.risk_game.models.phases;

import java.util.HashSet;
import java.util.Set;

import com.w10.risk_game.controllers.GameEngine;
import com.w10.risk_game.models.Phase;

public class PlaySetupPhase extends GamePlayPhase{
    public PlaySetupPhase(GameEngine p_GameEngine){
        super(p_GameEngine);
    }

    @Override
    public boolean executePlayerOrders() {
        super.printInvalidCommandMessage();
        return false;
    }

    @Override
    public boolean checkIfOrdersCanBeExecuted() {
        super.printInvalidCommandMessage();
        return false;
    }

    @Override
    public void createPlayer(String p_playerName) {
        super.d_GameEngine.createPlayer(p_playerName);
    }

    @Override
    public void removePlayer(String p_playerName) {
        super.d_GameEngine.removePlayer(p_playerName);
    }

    @Override
    public void showAllPlayers() {
        super.d_GameEngine.showAllPlayers();
    }

    @Override
    public boolean assignCountries() { 
       return super.d_GameEngine.assignCountries();
    }   
}
