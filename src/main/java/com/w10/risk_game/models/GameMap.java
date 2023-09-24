package com.w10.risk_game.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    return l_countries.values()
        .stream()
        .filter(counrty -> counrty.getCountryName().equals(p_countryNmae))
        .findFirst()
        .get();
  }

  public Continent getContinentByName(String p_continentName) {
    return l_continents.values()
        .stream()
        .filter(counrty -> counrty.getContinentName().equals(p_continentName))
        .findFirst()
        .get();
  }

  public Map<Integer, Continent> getContinents() {
    return l_continents;
  }

  public boolean containsCountry(int p_id) {
    return l_countries.containsKey(p_id);
  }
/*
 * Method overloading- polymorphism
 */
  public boolean containsCountry(String p_countryName) {
      return l_countries.values()
        .stream()
        .filter(counrty -> counrty.getCountryName().equals(p_countryName))
        .findFirst()
        .isPresent();
  }

  public boolean containsContinent(int p_id){
    return l_continents.containsKey(p_id);
  }

  /*
 * Method overloading- polymorphism
 * 
 */
  public boolean containsContinent(String p_continentName){
    return l_continents.values()
        .stream()
        .filter(continent -> continent.getContinentName().equals(p_continentName))
        .findFirst()
        .isPresent();
  }

  public Continent getContinentById(int p_continentId) {
    return l_continents.get(p_continentId);
  }
public ArrayList<Country> getCountriesOfContinent(int p_continentId){
  return l_countries.values()
          .stream()
          .filter(country -> country.getContinentId() == p_continentId)
          .collect(Collectors.toCollection(ArrayList:: new));
}
  public void addCountries(Map<Integer, Country> p_countries) {
    this.l_countries.putAll(p_countries);
  }

  public void addContinentes(Map<Integer, Continent> p_continents) {
    this.l_continents.putAll(p_continents);
  }

}
