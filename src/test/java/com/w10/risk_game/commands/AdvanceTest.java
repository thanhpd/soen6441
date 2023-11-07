package com.w10.risk_game.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

class AdvanceTest {
	private Country d_country1;
	private Country d_country2;
	private Player d_player1;
	private Player d_player2;
	private Order d_order1;

	@BeforeEach
	public void setUp() {
		d_country1 = new Country(1, "England", 1, 10);
		d_country2 = new Country(2, "France", 1, 2);
		d_player1 = new Player("Player1", new ArrayList<>(), new ArrayList<>(), 0);
		d_player2 = new Player("Player2", new ArrayList<>(), new ArrayList<>(), 0);
		d_country1.setOwner(d_player1);
		d_country2.setOwner(d_player2);
		d_player1.addCountry(d_country1);
		d_player2.addCountry(d_country2);
		d_order1 = new Advance(d_country1, d_country2, 3);
	}

	@Test
	void testSameOwnerTransfer() {
		d_country2.setOwner(d_player1);
		d_order1.execute();
		assertEquals(5, d_country2.getArmyCount());
		assertEquals(7, d_country1.getArmyCount());
	}

	@Test
	void testBattleWon() {
		d_order1.execute();
		assertEquals(7, d_country1.getArmyCount());
		assertEquals(1, d_country2.getArmyCount());
		assertEquals("Player1", d_country2.getOwner().getName());
		assertEquals(0, d_player2.getCountriesOwned().size());
		assertEquals(2, d_player1.getCountriesOwned().size());
		assertEquals(1, d_player1.getPlayerCards().size());
	}

	@Test
	void testBattleLost() {
		d_order1 = new Advance(d_country2, d_country1, 2);
		d_order1.execute();
		assertEquals(0, d_country2.getArmyCount());
		assertEquals(8, d_country1.getArmyCount());
		assertEquals("Player1", d_country1.getOwner().getName());
	}
}
