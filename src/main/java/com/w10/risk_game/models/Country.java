package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Country implements Serializable{

  private int countryId;
  private String countryName;
  private int continentId;
  private Map<Integer, ArrayList<Integer>> adjacentCountries = new HashMap<>();
  ArrayList <Country> neighbors= new ArrayList<>();

  public Country() {

  }

  private Country(int countryId, String countryName, int continentId) {
    this.countryId = countryId;
    this.countryName = countryName;
    this.continentId = continentId;
  }
 
  // Getter
  public int getCountryId() {
    return countryId;
  }

  // Setter
  public void setCountryId(int countryId) {
    this.countryId = countryId;
  }

  // Getter
  public String getCountryName() {
    return countryName;
  }

  // Setter
  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  // Getter
  public int getContinentId() {
    return continentId;
  }

  // Setter
  public void setContinentId(int continentId) {
    this.continentId = continentId;
  }
@Override
  public String toString(){
    StringBuilder builder =  new StringBuilder();
    builder.append(countryId+" "+countryName+" "+continentId);

    for (Country country : neighbors) {
      builder.append(" ");
      builder.append(country.countryId);
    } 
    return builder.toString();
  }

  public void addBorder(Country neighbourCountry){
    neighbors.add(neighbourCountry);
  }
}
