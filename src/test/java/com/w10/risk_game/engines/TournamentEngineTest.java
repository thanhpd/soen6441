package com.w10.risk_game.engines;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class TournamentEngineTest {
	@Test
	void testStart() {
		Set<String> p_playerStrategyNames = Set.of("Benevolent", "Random");
		Set<String> maps = Set.of("europe.map");
		int gamesCount = 2;
		int maxTurns = 200;
		TournamentEngine l_tournamentController = new TournamentEngine();
		l_tournamentController.start(p_playerStrategyNames, maps, gamesCount, maxTurns);

	}
}
