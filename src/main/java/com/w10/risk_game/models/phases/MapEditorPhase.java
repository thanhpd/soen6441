package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;
import com.w10.risk_game.models.Phase;

public abstract class MapEditorPhase extends Phase {
    protected GameEngine d_GameEngine;
    public MapEditorPhase(GameEngine p_GameEngine){
        this.d_GameEngine=p_GameEngine;
    }

    @Override
    public void attack() {
        super.printInvalidCommandMessage();
    } @Override
    public void printInvalidCommandMessage() {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
    }

    @Override
    public void loadMap(String p_filePath) {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
    }

   

    @Override
    public void createPlayer(String p_playerName) {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
    }

    @Override
    public void removePlayer(String p_playerName) {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
    }

    @Override
    public void showAllPlayers() {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
    }

    @Override
    public boolean assignCountries() {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
        return false;
    }

    @Override
    public boolean editMap(String p_mapFilePath) {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
        return false;
    }

    @Override
    public boolean checkIfMapIsValid() {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
        return false;
    }

    @Override
    public boolean executePlayerOrders() {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
        return false;
    }

    @Override
    public boolean checkIfOrdersCanBeExecuted() {
        // TODO Auto-generated method stub
        super.printInvalidCommandMessage();
        return false;
    }
}
