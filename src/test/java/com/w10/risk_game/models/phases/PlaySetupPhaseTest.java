package com.w10.risk_game.models.phases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.utils.Constants;

/**
 * The PlaySetupPhaseTest class is a JUnit test class that tests the
 * functionality of the PlaySetupPhase class in a game engine.
 */
public class PlaySetupPhaseTest {

	Phase phase;
	GameEngine l_GameEngine;

	/**
	 * The setup function initializes a GameEngine object, sets the phase to
	 * PreLoadPhase, loads a map, and advances to the next phase.
	 */
	@BeforeEach
	public void setup() {
		l_GameEngine = new GameEngine();
		GameEngine.SetPhase(new PreLoadPhase(l_GameEngine));
		phase = GameEngine.Phase;
		phase.loadMap(Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map");
		phase = GameEngine.Phase;
		phase.nextPhase();
		phase = GameEngine.Phase;
	}

	/**
	 * The testGetAvailableCommands function checks if the available commands list
	 * contains the "assigncountries" command.
	 */
	@Test
	void testGetAvailableCommands() {
		assertTrue(phase.getAvailableCommands().toString().contains("assigncountries"));
	}
	/**
	 * The testPhaseName function tests that the phase name is correctly returned as
	 * "PlaySetup PHASE".
	 */
	@Test
	void testPhaseName() {
		assertEquals("PlaySetup PHASE", phase.getPhaseName());

	}

	/**
	 * The testNext function tests the next phase of the game engine.
	 */
	@Test
	void testNext() {
		phase.createPlayer("player1", Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_HUMAN);
		phase.createPlayer("player2", Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_HUMAN);
		phase.assignCountries();
		phase = GameEngine.Phase;
		assertEquals("Reinforcement PHASE", phase.getPhaseName());

	}
	/**
	 * The "end" function sets the "phase" variable to null.
	 */
	@AfterEach
	public void end() {
		phase = null;
	}
}
