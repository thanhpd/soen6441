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
  public int countryId() {
    return countryId;
  }

  // Setter
  public void countryId(int countryId) {
    this.countryId = countryId;
  }

  // Getter
  public String countryName() {
    return countryName;
  }

  // Setter
  public void countryName(String countryName) {
    this.countryName = countryName;
  }

  // Getter
  public int continentId() {
    return continentId;
  }

  // Setter
  public void continentId(int continentId) {
    this.continentId = continentId;
  }

  public String toString(){
    return countryId+" "+countryName+" "+continentId;
  }

}
