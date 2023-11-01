package com.w10.risk_game.models.phases;

import java.util.HashSet;
import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

// In Preload, only load map is a avaliable.
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
		super.printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		super.printInvalidCommandMessage();
	}

	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		super.printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(int p_countryId) {
		super.printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		super.printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		super.printInvalidCommandMessage();
	}

	@Override
	public void saveMap(String p_mapFilePath) {
		super.printInvalidCommandMessage();
	}

	@Override
	public void showMap() {
		super.printInvalidCommandMessage();
	}

	@Override
	public boolean checkIfGameCanBegin() {
		super.printInvalidCommandMessage();
		return false;
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.LOAD_MAP);
	}
}
