package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;
import com.w10.risk_game.models.Phase;

public abstract class GamePlayPhase extends Phase {
    protected GameEngine d_GameEngine;

    public GamePlayPhase(GameEngine p_GameEngine){
        this.d_GameEngine=p_GameEngine;
    }

    @Override
    public void loadMap(String p_filePath) {
        super.printInvalidCommandMessage();
    }

    @Override
    public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
        super.printInvalidCommandMessage();
    }

    @Override
    public void addContinent(String p_continentName, int p_bonus) {
        super.printInvalidCommandMessage();
    }

    @Override
    public void addNeighbor(int p_countryId, int p_neighborCountryId) {
        super.printInvalidCommandMessage();
    }

    @Override
    public void removeCountry(int p_countryId) {
        super.printInvalidCommandMessage();
    }

    @Override
    public void removeContinent(String p_continentName) {
        super.printInvalidCommandMessage();
    }

    @Override
    public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
        super.printInvalidCommandMessage();
    }

    @Override
    public void saveMap(String p_mapFilePath) {
        super.printInvalidCommandMessage();
    }



    @Override
    public void showMap() {
        super.printInvalidCommandMessage();
    }

  

    @Override
    public boolean editMap(String p_mapFilePath) {
        super.printInvalidCommandMessage();
        return false;
    }

    @Override
    public boolean checkIfMapIsValid() {
        return false;
    }

 

}
