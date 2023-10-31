package com.w10.risk_game.models.phases;

import com.w10.risk_game.controllers.GameEngine;

public class PostLoad extends MapEditorPhase {

	public PostLoad(GameEngine p_GameEngine) {
		super(p_GameEngine);
	}

	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.d_gameEngine.addCountry(p_countryId, p_countryName, p_continentName);
	}

	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.d_gameEngine.addContinent(p_continentName, p_bonus);
	}

	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_gameEngine.addNeighbor(p_countryId, p_neighborCountryId);
	}

	@Override
	public void removeCountry(int p_countryId) {
		this.d_gameEngine.removeCountry(p_countryId);
	}

	@Override
	public void removeContinent(String p_continentName) {
		this.d_gameEngine.removeContinent(p_continentName);
	}

	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_gameEngine.removeNeighbor(p_countryId, p_neighborCountryId);
	}

	@Override
	public void saveMap(String p_mapFilePath) {
		this.d_gameEngine.saveMap(p_mapFilePath);
	}

	@Override
	public void showMap() {
		// TODO Auto-generated method stub
		this.d_gameEngine.showMap();
	}

	@Override
	public void loadMap(String p_filePath) {
		System.out.println("map has already been loaded");
	}

	@Override
	public boolean checkIfGameCanBegin() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'checkIfGameCanBegin'");
	}
}
