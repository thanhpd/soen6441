package com.w10.risk_game.models;

/**
 * @author Omnia Alam
 */

public class Continent {
	private int d_continentId;
	private String d_continentName;
	private int d_bouns;

	public Continent() {

	}

	public Continent(int p_continentId, String p_continentName, int d_bouns) {
		this.d_continentId = p_continentId;
		this.d_continentName = p_continentName;
	}

	// Getter
	public int getBouns() {
		return d_bouns;
	}

	// Setter

	public void setBous(int p_bouns) {
		this.d_bouns = p_bouns;
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
	public int getbous() {
		return d_bouns;
	}

	// Setter
	public void setbous(int p_bouns) {
		this.d_bouns = p_bouns;
	}

	public String toString() {
		return d_continentId + " " + d_continentName + " " + d_bouns;
	}

}
