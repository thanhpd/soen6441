package com.w10.risk_game.models;

import java.util.HashMap;
import java.util.Map;

public class GameMap {
  private Map<Integer, Country> l_countries = new HashMap<Integer, Country>();
  private Map<Integer, Continent> l_continents = new HashMap<Integer, Continent>();

  // Getter
  public Map<Integer, Country> getCountries() {
    return l_countries;
  }

  public Country getCountryById(int p_countryId) {
    return l_countries.get(p_countryId);
  }

  public Country getCountryByName(String p_countryNmae) {
    return l_countries.get(p_countryNmae);
  }

  public Continent gContinentByName(String p_continentName) {
    return l_continents.get(p_continentName);
  }

  public Map<Integer, Continent> getContinents() {
    return l_continents;
  }

  public Continent getContinentById(int p_continentId) {
    return l_continents.get(p_continentId);
  }

  public void addCountries(Map<Integer, Country> p_countries) {
    this.l_countries.putAll(p_countries);
  }

  public void addContinentes(Map<Integer, Continent> p_continents) {
    this.l_continents.putAll(p_continents);
  }

}
