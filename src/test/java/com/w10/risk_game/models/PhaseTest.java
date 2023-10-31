package com.w10.risk_game.models;

import org.junit.jupiter.api.Test;

import com.w10.risk_game.controllers.GameEngine;
import com.w10.risk_game.models.phases.PreLoad;

public class PhaseTest {

	@Test
	public void TestMapEditorPhase() {
		GameEngine engine = new GameEngine();
		engine.setPhase(new PreLoad(engine));
		engine.editMap(null);

	}
}
