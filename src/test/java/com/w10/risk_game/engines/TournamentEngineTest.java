package com.w10.risk_game.engines;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class TournamentEngineTest {
	@Test
	void testStart() {
		Set<String> p_playerStrategyNames = Set.of("Aggressive", "Benevolent");
		Set<String> maps = Set.of("test-win.map");
		int gamesCount = 1;
		int maxTurns = 1;
		TournamentEngine l_tournamentController = new TournamentEngine();
		l_tournamentController.start(p_playerStrategyNames, maps, gamesCount, maxTurns);

	}
}
