package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

/**
 * The PostLoadPhase class is a subclass of MapEditorPhase that represents the
 * phase after loading a map in a game editor, allowing for editing and saving
 * of the map.
 */
public class PostLoadPhase extends MapEditorPhase {

	public PostLoadPhase(GameEngine p_GameEngine) {
		super(p_GameEngine);
	}

	@Override
	public void showMap() {
		this.d_gameEngineController.showMap();
	}

	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.d_gameEngineController.addCountry(p_countryId, p_countryName, p_continentName);
	}

	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.d_gameEngineController.addContinent(p_continentName, p_bonus);
	}

	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_gameEngineController.addNeighbor(p_countryId, p_neighborCountryId);
	}

	@Override
	public void removeCountry(int p_countryId) {
		this.d_gameEngineController.removeCountry(p_countryId);
	}

	@Override
	public void removeContinent(String p_continentName) {
		this.d_gameEngineController.removeContinent(p_continentName);
	}

	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_gameEngineController.removeNeighbor(p_countryId, p_neighborCountryId);
	}

	@Override
	public void saveMap(String p_mapFilePath) {
		this.d_gameEngineController.saveMap(p_mapFilePath);
		next();
	}

	@Override
	public void loadMap(String p_filePath) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void issueReinforcementOrders(String p_orderType) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void issueAttackOrders(String p_orderType) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void issueFortifyOrders(String p_orderType) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
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
