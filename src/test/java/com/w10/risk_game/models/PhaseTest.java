package com.w10.risk_game.models;

import org.junit.jupiter.api.Test;

import com.w10.risk_game.controllers.RiskGame;
import com.w10.risk_game.models.phases.PreLoad;

public class PhaseTest {

	@Test
	public void TestMapEditorPhase() {
		RiskGame engine = new RiskGame();
		engine.setPhase(new PreLoad(engine));
		engine.editMap(null);

	}
}
