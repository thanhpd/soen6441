package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

public class ExecuteOrderPhase extends GamePlayPhase {

	protected ExecuteOrderPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void loadMap(String p_filePath) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void showMap() {
		this.printInvalidCommandMessage();
	}

	@Override
	public boolean editMap(String p_mapFilePath) {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void addContinent(String p_continentName, int p_bonus) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void addNeighbor(int p_countryId, int p_neighborCountryId) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(int p_countryId) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void saveMap(String p_mapFilePath) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void createPlayer(String p_playerName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removePlayer(String p_playerName) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void showAllPlayers() {
		this.printInvalidCommandMessage();

	}

	@Override
	public boolean assignCountries() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void nextPhase() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void issuePlayerOrder() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void executeAllPlayerOrders() {
		this.d_gameEngineController.executePlayerOrders();
		next();
	}

	@Override
	public boolean assignPlayerReinforcements() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new ReinforcementPhase(d_gameEngine));
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.NONE);
	}
}
