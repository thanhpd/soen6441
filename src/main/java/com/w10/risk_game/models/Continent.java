package com.w10.risk_game.models;

/**
 * @author Omnia Alam
 */

public class Continent {
	private int d_continentId;
	private String d_continentName;
	private int d_bonus;

	public Continent() {

	}

	public Continent(int p_continentId, String p_continentName, int p_bouns) {
		this.d_continentId = p_continentId;
		this.d_continentName = p_continentName;
		this.d_bonus = p_bouns;
	}

	// Setter

	public void setBous(int p_bonus) {
		this.d_bonus = p_bonus;
	}

	// Getter
	public int getContinentId() {
		return d_continentId;
	}

	// Setter

	public void setContinentId(int p_continentId) {
		this.d_continentId = p_continentId;
	}

	// Getter
	public String getContinentName() {
		return d_continentName;
	}

	// Setter
	public void setContinentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}

	// Getter
	public int getBonus() {
		return d_bonus;
	}

	// Setter
	public void setBonus(int p_bouns) {
		this.d_bonus = p_bouns;
	}

	public String toString() {
		return d_continentId + " " + d_continentName + " " + d_bonus;
	}

}
