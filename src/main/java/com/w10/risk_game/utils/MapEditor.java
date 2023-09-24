package com.w10.risk_game.utils;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

public class MapEditor {
  private GameMap l_gameMap;

  public MapEditor(GameMap originalMap) {
    this.l_gameMap = originalMap;
  }

  public String addCounrty(int p_countryId, String p_countryName, String p_continentName) {
  
    if (StringUtils.isBlank(p_countryName)) {
      return "Country Name is empty!";
    }
    if (StringUtils.isBlank(p_continentName)) {
      return "Continent Name is emplty!";
    }
    if (l_gameMap.containsCountry(p_countryName)) {
      return "Country name already exists!";
    }
    if (l_gameMap.containsCountry(p_countryId)) {
      return "Country ID already exists!";
    }
    if(!(l_gameMap.containsContinent(p_continentName))){
      return "Continent doesnot exists!";
    }

    // Country l_country = new Country(p_countryId, p_countryName, p_continentId);

    // l_gameMap.get.add(l_country);
    return null;
  }

  public String addContinent(int p_continentId, String p_continentName) {
    if (l_gameMap.containsContinent(p_continentName)) {
      return "Continent name already exists!";
    }
    if (l_gameMap.containsContinent(p_continentId)) {
      return "Continent id already exists!";
    }

    Continent l_continent = new Continent(p_continentId, p_continentName);
    l_gameMap.getContinents().put(l_gameMap.getContinents().size() + 1, l_continent);

    return null;
  }

  public void removeCountry(int p_countryId) {
    l_gameMap.getCountries().entrySet().removeIf(entry -> p_countryId == entry.getValue().getCountryId());
  }

  public void removeContinent(int p_continentId) {
    l_gameMap.getContinents().entrySet().removeIf(entry -> p_continentId == entry.getValue().getContinentId());
  }

  public void removeNeighbour(int p_countryId, int p_neighbourCountryId) {

  }

  public void addNeighbor(int p_countryId, int p_neighbourCountryId) {

  }

  public void validateMap() {

  }
}
