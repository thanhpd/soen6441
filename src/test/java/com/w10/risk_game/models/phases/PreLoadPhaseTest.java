package com.w10.risk_game.models.phases;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.controllers.GameEngineController;
import com.w10.risk_game.controllers.MapEditorController;

public class PreLoadPhaseTest {
	String d_filepath = "src/main/resources/maps/europe.map";

	GameEngine d_gameEngine;
	PreLoadPhase l_preLoadPhase;

	@BeforeEach
	public void setup() {
		d_gameEngine = new GameEngine();
		l_preLoadPhase = new PreLoadPhase(d_gameEngine);

	}
	@Test
	void testAddContinent() {

	}

	@Test
	void testLoadMap() {

	}
}
