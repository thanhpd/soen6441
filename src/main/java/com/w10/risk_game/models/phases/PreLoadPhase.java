package com.w10.risk_game.models.phases;

import java.util.HashSet;
import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

/**
 * The PreLoadPhase class is a subclass of MapEditorPhase that handles the
 * pre-loading phase of a game, allowing the user to load a map file and
 * transition to the next phase.
 */
public class PreLoadPhase extends MapEditorPhase {

	public PreLoadPhase(GameEngine p_GameEngine) {
		super(p_GameEngine);
	}

	@Override
	public void loadMap(String p_filePath) {
		this.d_mapEditorController.loadMap(p_filePath);
		next();
	}

	public void next() {
		d_gameEngine.setPhase(new PostLoadPhase(d_gameEngine));
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
	public void nextPhase() {
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
		this.printInvalidCommandMessage();
	}

	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.LOAD_MAP);
	}

	@Override
	public boolean checkIfMapIsValid() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public void issuePlayerOrder() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void executeAllPlayerOrders() {
		this.printInvalidCommandMessage();
	}
}
