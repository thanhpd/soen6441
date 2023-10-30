package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;

public class PostLoad extends MapEditorPhase {

    public PostLoad(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    public void loadMap() {

        System.out.println("map has already been loaded");
    }
         @Override
        public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
           super.d_GameEngine.addCountry(p_countryId, p_countryName, p_continentName);
        }
         @Override
        public void addContinent(String p_continentName, int p_bonus){
            super.d_GameEngine.addContinent(p_continentName, p_bonus);
        }
         @Override
        public void addNeighbor(int p_countryId, int p_neighborCountryId){
            super.d_GameEngine.addNeighbor( p_countryId, p_neighborCountryId);
        }
         @Override
        public void removeCountry(int p_countryId){
            super.d_GameEngine.removeCountry(p_countryId);
        }
         @Override
        public void removeContinent(String p_continentName){
            super.d_GameEngine.removeContinent(p_continentName);
        }
     @Override       
        public void removeNeighbor(int p_countryId, int p_neighborCountryId){
            super.d_GameEngine.removeNeighbor(p_countryId, p_neighborCountryId);
        }
         @Override
        public void saveMap(String p_mapFilePath) {
            super.d_GameEngine.saveMap(p_mapFilePath);
        }

        @Override
        public void showMap() {
            // TODO Auto-generated method stub
            super.d_GameEngine.showMap();
        }
}
