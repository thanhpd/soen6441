package com.w10.risk_game.models.phases;

import java.util.HashSet;
import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

// In Preload, all commands except LoadMap Is available
public class PreLoad extends MapEditorPhase {

	public PreLoad(GameEngine p_GameEngine) {
		super(p_GameEngine);
	}

	@Override
	public void loadMap(String p_filePath) {
		this.d_Game.loadMap(p_filePath);
		next();
	}

	public void next() {
		d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(int p_countryId) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void saveMap(String p_mapFilePath) {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();
	}

	@Override
	public void showMap() {
		this.printInvalidCommandMessage();
	}

	@Override
	public boolean checkIfGameCanBegin() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public Set<Command> getAvaliableCommands() {
		return Set.of(Command.LOAD_MAP);
	}
}
