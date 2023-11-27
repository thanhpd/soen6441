package com.w10.risk_game.models.phases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.engines.SinglePlayerEngine;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.utils.Constants;

/**
 * The PreLoadPhaseTest class tests the functionality of the PreLoadPhase class
 * in a game engine.
 */
public class PreLoadPhaseTest {

	Phase phase;

	/**
	 * The setup function initializes a GameEngine object and sets its phase to a
	 * PreLoadPhase.
	 */
	@BeforeEach
	public void setup() {
		SinglePlayerEngine l_GameEngine = new SinglePlayerEngine();
		SinglePlayerEngine.SetPhase(new PreLoadPhase(l_GameEngine));
		phase = SinglePlayerEngine.Phase;
	}

	/**
	 * The testPhaseName function tests that the phase name is "PreLoad PHASE".
	 */
	@Test
	void testPhaseName() {
		assertEquals("PreLoad PHASE", phase.getPhaseName());

	}

	/**
	 * The testAvailableCommands function tests that the available commands in the
	 * phase object are correctly returned as a string.
	 */
	@Disabled
	@Test
	void testAvailableCommands() {
		assertTrue(phase.getAvailableCommands().toString().contains("loadmap"));
	}

	/**
	 * The testNextIfMapValid function tests if the phase loads a valid map
	 * correctly.
	 */
	@Test
	void testNextIfMapValid() {
		phase.loadMap(Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map");
		phase = SinglePlayerEngine.Phase;
		assertEquals("PostLoad PHASE", SinglePlayerEngine.Phase.getPhaseName());
	}

	/**
	 * The "end" function sets the "phase" variable to null.
	 */
	@AfterEach
	public void end() {
		phase = null;
	}

}
