package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Country implements Serializable{

  private int countryId;
  private String countryName;
  private int continentId;
  private HashMap<Integer, ArrayList<Integer>> adjacentCountries = new HashMap<>();

  public Country() {

  }

  private Country(int countryId, String countryName, int continentId) {
    this.countryId = countryId;
    this.countryName = countryName;
    this.continentId = continentId;
  }

 

public HashMap<Integer, ArrayList<Integer>> adjacentCountries() {
 return adjacentCountries;
 }


  
 public void adjacentCountries(HashMap<Integer, ArrayList<Integer>> adjacentCountries) {
 this.adjacentCountries = adjacentCountries;
 }

 //Getter
 public ArrayList<Integer> adjacentCountries(Integer countryId) {
  return adjacentCountries.get(countryId);
  }
 //Setter
  public void adjacentCountries(Integer countryId, ArrayList<Integer> listOfNeighbours) {
  this.adjacentCountries.put(countryId, listOfNeighbours);
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

  public String toString(){
    return countryId+" "+countryName+" "+continentId;
  }

}
