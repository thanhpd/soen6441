package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Omnia Alam
 */

public class Country implements Serializable {

  private int d_countryId;
  private String d_countryName;
  private int d_continentId;
  private Map<Integer, Country> d_neighbors = new HashMap<>();

  public Country() {

  }

  public Country(int p_countryId, String p_countryName, int p_continentId) {
    this.d_countryId = p_countryId;
    this.d_countryName = p_countryName;
    this.d_continentId = p_continentId;
  }

  // Getter
  public int getCountryId() {
    return d_countryId;
  }

  // Setter
  public void setCountryId(int p_countryId) {
    this.d_countryId = p_countryId;
  }

  // Getter
  public String getCountryName() {
    return d_countryName;
  }

  // Setter
  public void setCountryName(String p_countryName) {
    this.d_countryName = p_countryName;
  }

  // Getter
  public int getContinentId() {
    return d_continentId;
  }

  // Setter
  public void setContinentId(int p_continentId) {
    this.d_continentId = p_continentId;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(d_countryId + " " + d_countryName + " " + d_continentId);

    for (Country country : d_neighbors.values()) {
      builder.append(" ");
      builder.append(country.d_countryId);
    }
    return builder.toString();
  }
/*
 * API: Checks for different parameters if the neighbor exists
 * 
 */
  public boolean hasNeighbor(int p_neighbourCountryId) {
    return d_neighbors.containsKey(p_neighbourCountryId);
  }

  public boolean hasNeighbor(String p_neighborCoutryName) {
    for (var neighborCountry : d_neighbors.values()) {
      if (neighborCountry.getCountryName().equals(p_neighborCoutryName)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasNeighbor(Country p_country) {
    return d_neighbors.containsKey(p_country.getCountryId());
  }


  
  public void addNeighbor(Country p_neighbourCountry) {
    d_neighbors.put(p_neighbourCountry.d_countryId, p_neighbourCountry);
  }

  public Map<Integer, Country> getNeighbors() {
    return d_neighbors;
  }
}
