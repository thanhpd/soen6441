package com.w10.risk_game.models;


public class Border {
    private int countryId;
    private int[] adjacentCountry;

    public Border(){

    }

    private Border(int countryId,int [] adjacentCountry){
        this.countryId = countryId;
       
    }

    // Getter
  public int countryId() {
    return countryId;
  }

  // Setter
  public void countryId(int countryId) {
    this.countryId = countryId;
  }
}

