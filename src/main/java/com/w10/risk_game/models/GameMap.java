package com.w10.risk_game.models;

import java.util.HashMap;
import java.util.Map;

public class GameMap {
  private Map<Integer, Country> countries = new HashMap<Integer, Country>();
  private Map<Integer, Continent> continents = new HashMap<Integer, Continent>();

  // Getter
  public Map<Integer, Country> getCountries() {
    return countries;
  }

  public Map<Integer, Continent> getContinents() {
    return continents;
  }

  public Continent getContinentById(int continentId) {
    return continents.get(continentId);
  }

  public void addCountries(Map<Integer, Country> countries) {
    this.countries.putAll(countries);
  }

  public void addContinentes(Map<Integer, Continent> continents) {
    this.continents.putAll(continents);
  }

}
