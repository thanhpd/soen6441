package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;

// In Preload, all commands except LoadMap Is available
public class PreLoad extends MapEditorPhase{

    public PreLoad(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public void loadMap(String p_filePath) {
        d_GameEngine.loadMap(p_filePath);
        d_GameEngine.setPhase(new PostLoad(d_GameEngine));
    }

        public void next() {
            System.out.println("must load map");
              }

        @Override
        public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
            // TODO Auto-generated method stub
            super.printInvalidCommandMessage();
        }

        @Override
        public void addContinent(String p_continentName, int p_bonus) {
            // TODO Auto-generated method stub
            super.printInvalidCommandMessage();
        }

        @Override
        public void addNeighbor(int p_countryId, int p_neighborCountryId) {
            // TODO Auto-generated method stub
            super.printInvalidCommandMessage();
        }

        @Override
        public void removeCountry(int p_countryId) {
            // TODO Auto-generated method stub
            super.printInvalidCommandMessage();
        }

        @Override
        public void removeContinent(String p_continentName) {
            // TODO Auto-generated method stub
            super.printInvalidCommandMessage();
        }

        @Override
        public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
            // TODO Auto-generated method stub
            super.printInvalidCommandMessage();
        }

        @Override
        public void saveMap(String p_mapFilePath) {
            // TODO Auto-generated method stub
            super.printInvalidCommandMessage();
        }

        @Override
        public void showMap() {
            // TODO Auto-generated method stub
           super.printInvalidCommandMessage();
        }
}
