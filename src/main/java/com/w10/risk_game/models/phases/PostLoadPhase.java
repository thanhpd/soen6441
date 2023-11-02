package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

public class PostLoadPhase extends MapEditorPhase {

	public PostLoadPhase(GameEngine p_GameEngine) {
		super(p_GameEngine);
	}
	@Override
	public void showMap() {
		this.d_Game.showMap();
	}
	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.d_Game.addCountry(p_countryId, p_countryName, p_continentName);
	}

	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.d_Game.addContinent(p_continentName, p_bonus);
	}

	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_Game.addNeighbor(p_countryId, p_neighborCountryId);
	}

	@Override
	public void removeCountry(int p_countryId) {
		this.d_Game.removeCountry(p_countryId);
	}

	@Override
	public void removeContinent(String p_continentName) {
		this.d_Game.removeContinent(p_continentName);
	}

	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_Game.removeNeighbor(p_countryId, p_neighborCountryId);
	}

	@Override
	public void saveMap(String p_mapFilePath) {
		this.d_Game.saveMap(p_mapFilePath);
		next();
	}

	@Override
	public void loadMap(String p_filePath) {
		this.printInvalidCommandMessage();
	}

	@Override
	public boolean checkIfGameCanBegin() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new PlaySetupPhase(d_gameEngine));
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.EDIT_CONTINENT, Command.EDIT_COUNTRY, Command.EDIT_NEIGHBOR, Command.SHOW_MAP);
	}
}
