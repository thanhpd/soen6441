package com.w10.risk_game.models;

import java.util.HashMap;
import java.util.Map;

public class GameMap {
private Map<Integer, Country> countries = new HashMap<Integer, Country>();
private Map<Integer, Continent> continents = new HashMap<Integer, Continent>();

//Getter
public  Map<Integer, Country> getCountry(){
    return countries;
  }

//Setter
public void setCountry(Map<Integer, Country> countries) {
    this.countries = countries;
  }

public  Map<Integer, Continent> getContinent(){
    return continents;
  }
//Setter
public void setContinent(Map<Integer, Continent> continents) {
    this. continents = continents;
  }

}
