package com.w10.risk_game.models;

public class Continent {
  private int continentId;
  private String continentName;

  public Continent() {

  }

  private Continent(int continentId, String continentName) {
    this.continentId = continentId;
    this.continentName = continentName;
  }

  // Getter
  public int continentId() {
    return continentId;
  }

  // Setter
  public void continentId(int continentId) {
    this.continentId = continentId;
  }

  // Getter
  public String continentName() {
    return continentName;
  }

  // Setter
  public void continentName(String continentName) {
    this.continentName = continentName;
  }

}
