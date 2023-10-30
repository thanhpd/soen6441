package com.w10.risk_game.models;
import java.util.Set;

import com.w10.risk_game.controllers.GameEngine;

public abstract class Phase {

    public abstract void loadMap(String p_filePath);
    public abstract void addCountry(int p_countryId, String p_countryName, String p_continentName);
    public abstract void addContinent(String p_continentName, int p_bonus);
    public abstract void addNeighbor(int p_countryId, int p_neighborCountryId);
    public abstract void removeCountry(int p_countryId);
    public abstract void removeContinent(String p_continentName);
    public abstract void removeNeighbor(int p_countryId, int p_neighborCountryId);
    public abstract void saveMap(String p_mapFilePath);
    public abstract void showMap();
    public abstract void createPlayer(String p_playerName);
    public abstract void removePlayer(String p_playerName);
    public abstract void showAllPlayers();
    public abstract boolean assignCountries();
    public abstract boolean editMap(String p_mapFilePath);
    public abstract boolean checkIfMapIsValid();
    public abstract boolean executePlayerOrders();
    public abstract boolean checkIfOrdersCanBeExecuted();
    





    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state "
        + this.getClass().getSimpleName() );
        }
} 
   
    
