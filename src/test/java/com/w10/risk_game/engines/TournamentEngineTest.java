package com.w10.risk_game.engines;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * The TournamentEngineTest class contains two test methods that test the start
 * method of the TournamentEngine class with different sets of player strategy
 * names, maps, games count, and max turns.
 */
public class TournamentEngineTest {
	/**
	 * The testStart() function starts a tournament with a set of player strategy
	 * names, maps, number of games, and maximum turns.
	 */
	@Test
	void testStart() {
		Set<String> p_playerStrategyNames = Set.of("Cheater", "Aggressive");
		Set<String> maps = Set.of("src/test/resources/maps/europe-conquest.map");
		int gamesCount = 2;
		int maxTurns = 10;
		TournamentEngine l_tournamentController = new TournamentEngine();
		l_tournamentController.startGame(p_playerStrategyNames, maps, gamesCount, maxTurns);

	}

	/**
	 * The testStart1 function starts a tournament with a set of player strategy
	 * names, a set of maps, a number of games, and a maximum number of turns.
	 */
	@Test
	void testStart1() {
		Set<String> p_playerStrategyNames = Set.of("Aggressive", "Random", "Benevolent");
		Set<String> maps = Set.of("europe.map", "france.map");
		int gamesCount = 2;
		int maxTurns = 20;
		TournamentEngine l_tournamentController = new TournamentEngine();
		l_tournamentController.startGame(p_playerStrategyNames, maps, gamesCount, maxTurns);

	}
}
