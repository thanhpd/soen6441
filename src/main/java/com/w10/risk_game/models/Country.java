package com.w10.risk_game.models;

public class Country {

  private int countryId;
  private String countryName;
  private int continentId;

  public Country() {

  }

  private Country(int countryId, String countryName, int continentId) {
    this.countryId = countryId;
    this.countryName = countryName;
    this.continentId = continentId;
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

}
