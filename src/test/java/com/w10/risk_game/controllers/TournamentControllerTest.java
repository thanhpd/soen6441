package com.w10.risk_game.controllers;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class TournamentControllerTest {
	@Test
	void testGetMainCommand() {

	}

	@Test
	void testSetPhase() {

	}

	@Test
	void testDisplayResult() {

	}

	@Test
	void testGetGame() {

	}

	@Test
	void testGetMapEditorController() {

	}

	@Test
	void testPlayGame() {

	}

	@Test
	void testStart() {
		Set<String> p_playerStrategyNames = Set.of("Aggressive", "Benevolent");
		Set<String> maps = Set.of("test-win.map");
		int gamesCount = 1;
		int maxTurns = 1;
		TournamentController l_tournamentController = new TournamentController();
		l_tournamentController.start(p_playerStrategyNames, maps, gamesCount, maxTurns);

	}
}
