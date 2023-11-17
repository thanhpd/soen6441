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
		Set<String> p_playerStrategyNames = Set.of("Cheater", "Aggressive");
		Set<String> maps = Set.of("src/main/resources/maps/test.map");
		int gamesCount = 2;
		int maxTurns = 2;
		TournamentController l_tournamentController = new TournamentController();
		l_tournamentController.start(p_playerStrategyNames, maps, gamesCount, maxTurns);

	}
}
