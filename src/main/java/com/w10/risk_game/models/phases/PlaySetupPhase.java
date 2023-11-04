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
		this.d_mapEditorController.showMap(false);
	}

	@Override
	public void createPlayer(String p_playerName) {
		this.d_gameEngineController.createPlayer(p_playerName);
	}

	@Override
	public void removePlayer(String p_playerName) {
		this.d_gameEngineController.removePlayer(p_playerName);
	}

	@Override
	public void showAllPlayers() {
		this.d_gameEngineController.showAllPlayers();
	}

	@Override
	public boolean assignCountries() {
		if (this.d_gameEngineController.assignCountries()) {
			next();
			return true;
		}
		return false;
	}

	@Override
	public void endGame() {
		this.printInvalidCommandMessage();
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
	public void nextPhase() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new ReinforcementPhase(d_gameEngine));
	}

	@Override
	public boolean assignPlayerReinforcements() {
		this.printInvalidCommandMessage();
		return false;
	}

	@Override
	public Set<Command> getAvailableCommands() {
		return Set.of(Command.ASSIGN_COUNTRIES, Command.GAME_PLAYER_CREATE, Command.GAME_PLAYER_REMOVE,
				Command.SHOW_MAP);
	}
}
