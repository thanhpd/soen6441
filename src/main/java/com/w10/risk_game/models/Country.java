package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Country implements Serializable {

  private int d_countryId;
  private String d_countryName;
  private int d_continentId;
  private Map<Integer, Country> d_neighbors = new HashMap<>();

  public Country() {

  }

  public Country(int countryId, String countryName, int continentId) {
    this.d_countryId = countryId;
    this.d_countryName = countryName;
    this.d_countryId = continentId;
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

  public void addNeighbor(Country p_neighbourCountry) {
    d_neighbors.put(p_neighbourCountry.d_countryId, p_neighbourCountry);
  }

  public Map<Integer, Country> getNeighbors() {
    return d_neighbors;
  }
}
