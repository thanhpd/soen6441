package com.w10.risk_game.utils;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

/**
 * @author Omnia Alam
 *         This class operates on the current Game map
 *         It adds and removes Gamemap elements
 * @returns an updated gamemap after edit performs
 */

public class MapEditor {
  private GameMap l_gameMap;

  public MapEditor(GameMap originalMap) {
    this.l_gameMap = originalMap;
  }

  /**
   * 
   * @param p_countryId
   * @param p_countryName
   * @param p_continentName
   * @return string based on operation performed
   *         add operation on the current gamemap
   */

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
    if (l_gameMap.containsContinent(p_continentName) == false) {
      return "Continent doesnot exists!";
    }

    // Country l_country = new Country(p_countryId, p_countryName, p_continentId);

    // l_gameMap.get.add(l_country);
    return p_countryName + " is Added!";
  }

  /**
   * 
   * @param p_continentId
   * @param p_continentName
   * @return string based on operation performed
   *         add operation on the current gamemap
   */
  public String addContinent(int p_continentId, String p_continentName) {
    if (l_gameMap.containsContinent(p_continentName)) {
      return "Continent name already exists!";
    }
    if (l_gameMap.containsContinent(p_continentId)) {
      return "Continent id already exists!";
    }

    Continent l_continent = new Continent(p_continentId, p_continentName);
    l_gameMap.getContinents().put(l_gameMap.getContinents().size() + 1, l_continent);

    return p_continentName + " is added!";
  }

  /**
   * 
   * @param p_countryId
   * @param p_neighbourCountryId
   */
  public void addNeighbor(int p_countryId, int p_neighbourCountryId) {

  }

  /**
   * 
   * @param p_countryId
   */
  public void removeCountry(int p_countryId) {
    l_gameMap.getCountries().entrySet().removeIf(entry -> p_countryId == entry.getValue().getCountryId());
  }

  /**
   * 
   * @param p_continentName
   * @return string based on operation performed
   *         Looks for the continent elements if they exist
   *         Remove operation on the current Game map
   *         if exists then get all the adjacent countries and remove the
   *         countries before it removes the continent
   */
  public String removeContinent(String p_continentName) {
    if (StringUtils.isBlank(p_continentName)) {
      return "Continent Name is empty!";
    }
    if (l_gameMap.containsContinent(p_continentName) == false) {
      return "Continent Name doesnot exists";
    }
    Continent l_toRemove = l_gameMap.getContinentByName(p_continentName);
    String l_countriesRemoved = "";
    ArrayList<Country> l_countriesToRemove = l_gameMap.getCountriesOfContinent(l_toRemove.getContinentId());
    for (Country country : l_countriesToRemove) {
      removeCountry(country.getCountryId());
      l_countriesRemoved = country.getCountryName() + ", ";
    }
    l_gameMap.getContinents().remove(l_toRemove.getContinentId());
    return p_continentName + " is removed!" + l_countriesRemoved + "these countries also removed!";
  }

  public void removeNeighbour(int p_countryId, int p_neighbourCountryId) {

  }

  public void validateMap() {

  }
}
