package com.w10.risk_game.models;

public class Continent {
  private int continentId;
  private String continentName;

  public Continent() {

  }

  public Continent(int continentId, String continentName) {
    this.continentId = continentId;
    this.continentName = continentName;
  }

  // Getter
  public int getContinentId() {
    return continentId;
  }

  // Setter

  public void setContinentId(int continentId) {
    this.continentId = continentId;
  }

  // Getter
  public String getContinentName() {
    return continentName;
  }

  // Setter
  public void setContinentName(String continentName) {
    this.continentName = continentName;
  }
  public String toString(){
    return continentId+" "+continentName;
  }

}
