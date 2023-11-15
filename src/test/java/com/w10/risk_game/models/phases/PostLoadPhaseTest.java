package com.w10.risk_game.models.phases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.engine.GameEngine;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.utils.Constants;

public class PostLoadPhaseTest {
	Phase phase;
	GameEngine l_GameEngine;

	/**
	 * The setup function initializes a GameEngine object and sets its phase to a
	 * PostLoadPhase.
	 */
	@BeforeEach
	public void setup() {

		l_GameEngine = new GameEngine();
		GameEngine.SetPhase(new PreLoadPhase(l_GameEngine));
		phase = GameEngine.Phase;
		phase.loadMap(Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map");
		phase = GameEngine.Phase;

	}

	/**
	 * The testPhaseName function tests that the phase name is "PostLoad PHASE".
	 */
	@Test
	void testPhaseName() {
		assertEquals("PostLoad PHASE", phase.getPhaseName());

	}
	/**
	 * The testGetAvailableCommands function checks if the available commands list
	 * contains the "editcountry" command.
	 */

	@Test
	void testGetAvailableCommands() {
		assertTrue(phase.getAvailableCommands().toString().contains("editcountry"));
	}

	/**
	 * The testNextPhaseIfMapValid function tests if the nextPhase method correctly
	 * updates the phase to "PlaySetup PHASE".
	 */
	@Test
	void testNextPhaseIfMapValid() {
		phase.nextPhase();
		phase = GameEngine.Phase;
		assertEquals("PlaySetup PHASE", phase.getPhaseName());
	}
	/**
	 * The testNextIfMapInvalid function tests if the nextPhase method in the
	 * PostLoadPhase class correctly sets the phase to PostLoadPhase when an invalid
	 * map is loaded.
	 */
	@Test
	void testNextIfMapInvalid() {
		GameEngine.SetPhase(new PreLoadPhase(l_GameEngine));
		phase = GameEngine.Phase;
		phase.loadMap(Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test24.map");
		phase = GameEngine.Phase;
		phase.nextPhase();
		phase = GameEngine.Phase;
		assertEquals("PreLoad PHASE", phase.getPhaseName());
	}

	/**
	 * The "end" function sets the "phase" variable to null.
	 */
	@AfterEach
	public void end() {
		phase = null;
	}
}
