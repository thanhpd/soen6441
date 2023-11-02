package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * This is a test class for bomb card
 *
 */
public class BombTest {
	Order d_order1;
	Country d_country1;
	Country d_country2;
	Player d_player1;
	Player d_player2;

	/**
	 * This method is to set up the test environment
	 */
	@BeforeEach
	public void setUp() {
		d_country1 = new Country(1, "England", 1, 13);
		d_country2 = new Country(2, "France", 1, 5);
		d_country1.addNeighbor(d_country2);
		d_country2.addNeighbor(d_country1);
		d_player1 = new Player("Player1", new ArrayList<>(), new ArrayList<>(), 10);
		d_player2 = new Player("Player2", new ArrayList<>(), new ArrayList<>(), 10);
	}

	@Test
	public void testBombNeutralCountry() {
		ArrayList<Country> l_countries = new ArrayList<>();
		l_countries.add(d_country1);

		d_country1.setOwner(d_player1);
		d_player1.setCountriesOwned(l_countries);

		d_order1 = new Bomb(d_player1, "2");

		d_order1.execute();
		assertEquals(2, d_country2.getArmyCount());
	}

	@Test
	public void testBombEnemyCountry() {
		this.setUpOwnership();
		d_order1 = new Bomb(d_player2, "1");

		d_order1.execute();
		assertEquals(6, d_country1.getArmyCount());
	}

	@Test
	public void testCountryOwnership() {
		this.setUpOwnership();
		d_country1.setArmyCount(1);
		d_order1 = new Bomb(d_player2, "1");

		d_order1.execute();
		assertEquals(0, d_country1.getArmyCount());
		assertEquals(d_player1.getName(), d_country1.getOwner().getName());
	}

	private void setUpOwnership() {
		ArrayList<Country> l_countries1 = new ArrayList<>(){
			{
				add(d_country1);
			}
		};
		ArrayList<Country> l_countries2 = new ArrayList<>(){
			{
				add(d_country2);
			}
		};

		d_country1.setOwner(d_player1);
		d_player1.setCountriesOwned(l_countries1);

		d_country2.setOwner(d_player2);
		d_player2.setCountriesOwned(l_countries2);
	}
}
