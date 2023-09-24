package com.w10.risk_game.utils;

import java.util.ArrayList;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

public class MapEditor {
  private GameMap l_originalMap;
  ArrayList<Country> l_countries;
  ArrayList<Continent> l_continents;

  public MapEditor(GameMap originalMap) {
    this.l_originalMap = originalMap;
    l_countries = new ArrayList<>(originalMap.getCountries().values());
    l_continents = new ArrayList<>(originalMap.getContinents().values());
  }

  public void addCounrty(int p_countryId, String p_countryName, int p_continentId) {
    Country l_country = new Country(p_countryId, p_countryName, p_continentId);
    l_countries.add(l_country);
  }

  public void addContinent(int p_continentId, String p_continentName) {
    Continent continent = new Continent(p_continentId, p_continentName);
    l_continents.add(continent);
  }

  public void addNeighbor(int p_countryId, int p_neighbourCountryId) {
   
  }

  public void removeCountry(int p_countryId) {
    l_countries.removeIf(country -> p_countryId == country.getCountryId());
  }

  public void removeContinent(int p_continentId) {
    l_continents.removeIf(continent -> p_continentId == continent.getContinentId());
  }

  public void removeNeighbour(int p_countryId, int p_neighbourCountryId) {

  }

  public void validateMap() {

  }
}
