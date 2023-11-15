package com.w10.risk_game.models.phases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.engine.GameEngine;
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
		GameEngine l_GameEngine = new GameEngine();
		GameEngine.SetPhase(new PreLoadPhase(l_GameEngine));
		phase = GameEngine.Phase;
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
	@Test
	void testAvailableCommands() {
		assertEquals("[loadmap]", phase.getAvailableCommands().toString());

	}

	/**
	 * The testNextIfMapValid function tests if the phase loads a valid map
	 * correctly.
	 */
	@Test
	void testNextIfMapValid() {
		phase.loadMap(Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map");
		phase = GameEngine.Phase;
		assertEquals("PostLoad PHASE", GameEngine.Phase.getPhaseName());
	}

	/**
	 * The "end" function sets the "phase" variable to null.
	 */
	@AfterEach
	public void end() {
		phase = null;
	}

}
