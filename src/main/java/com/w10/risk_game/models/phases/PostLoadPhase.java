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
		this.d_mapEditorController.showMap(false);
	}

	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.d_mapEditorController.addCountry(p_countryId, p_countryName, p_continentName);
	}

	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.d_mapEditorController.addContinent(p_continentName, p_bonus);
	}

	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_mapEditorController.addNeighbor(p_countryId, p_neighborCountryId);
	}

	@Override
	public void removeCountry(int p_countryId) {
		this.d_mapEditorController.removeCountry(p_countryId);
	}

	@Override
	public void removeContinent(String p_continentName) {
		this.d_mapEditorController.removeContinent(p_continentName);
	}

	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.d_mapEditorController.removeNeighbor(p_countryId, p_neighborCountryId);
	}

	@Override
	public void saveMap(String p_mapFilePath) {
		this.d_mapEditorController.saveMap(p_mapFilePath);
	}

	@Override
	public void loadMap(String p_filePath) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void nextPhase() {
		d_gameEngine.setPhase(new PlaySetupPhase(d_gameEngine));
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.EDIT_CONTINENT, Command.EDIT_COUNTRY, Command.EDIT_NEIGHBOR, Command.SHOW_MAP,
				Command.VALIDATE_MAP, Command.NEXT);
	}

	@Override
	public boolean checkIfMapIsValid() {
		return this.d_mapEditorController.checkIfMapIsValid();
	}

	@Override
	public void issuePlayerOrder() {
		this.printInvalidCommandMessage();

	}

	@Override
	public void executeAllPlayerOrders() {
		this.printInvalidCommandMessage();

	}

	@Override
	public void next() {
		this.printInvalidCommandMessage();
	}

	@Override
	public boolean assignPlayerReinforcements() {
		this.printInvalidCommandMessage();
		return false;
	}
}
