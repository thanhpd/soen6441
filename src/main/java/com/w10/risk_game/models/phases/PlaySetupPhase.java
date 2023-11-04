package com.w10.risk_game.models.phases;

import java.util.Set;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.Command;

/**
 * The PlaySetupPhase class is a subclass of GamePlayPhase that handles the
 * setup phase of the game, including creating and removing players, assigning
 * countries, and transitioning to the main play phase.
 */
public class PlaySetupPhase extends GamePlayPhase {
	public PlaySetupPhase(GameEngine d_gameEngine) {
		super(d_gameEngine);
	}
	@Override
	public void showMap() {
		this.d_EditorController.showMap();
	}
	@Override
	public boolean executePlayerOrders() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public boolean checkIfOrdersCanBeExecuted() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public void createPlayer(String p_playerName) {
		this.d_Game.createPlayer(p_playerName);
	}

	@Override
	public void removePlayer(String p_playerName) {
		this.d_Game.removePlayer(p_playerName);
	}

	@Override
	public void showAllPlayers() {
		this.d_Game.showAllPlayers();
	}

	@Override
	public boolean assignCountries() {

		if (this.d_Game.assignCountries()) {
			next();
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIfGameCanBegin() {
		// Not sure what to do
		return false;
	}
	@Override
	public void nextPhase() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new MainPlayPhase(d_gameEngine));
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.ASSIGN_COUNTRIES, Command.GAME_PLAYER_CREATE, Command.GAME_PLAYER_REMOVE,
				Command.SHOW_MAP);
	}
}
