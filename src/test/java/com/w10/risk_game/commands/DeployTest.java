package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * This is a test class for deploy order
 *
 * @author Yajing Liu
 */
public class DeployTest {
	Order d_order1;
	Player d_player1;
	/**
	 * This method is to set up the test environment
	 */
	@BeforeEach
	public void setUp() {
		List<Country> l_countries = new ArrayList<>();
		l_countries.add(new Country(1, "England", 1, 0));
		d_player1 = new Player("Player1", l_countries, new ArrayList<>(), 10);
		d_order1 = new Deploy(d_player1, 1, 6);
	}
	/**
	 * This method is to test the method of deploy
	 */
	@Test
	public void executeTest() {
		// Test deploy
		d_order1.execute();
		assertEquals(6, d_player1.getCountriesOwned().get(0).getArmyCount());
	}
	/**
	 * This method is to test the method of checkValidCountry. It will provide one
	 * invalid input and check whether the method can return false. The invalid
	 * input is the second part of the input is not contained in the countries owned
	 * by the player.
	 */
	@Test
	public void checkValidCountryTest() {
		String l_input = "deploy 0 6";
		String[] l_inputArray = l_input.split(" ");
		String l_countryId = l_inputArray[1];
		List<Country> l_countries = d_player1.getCountriesOwned();
		boolean l_isValidCountry = Deploy.CheckValidCountry(l_countries, l_countryId);
		assertEquals(false, l_isValidCountry);
	}
	/**
	 * This method is to test the method of checkValidArmy. It will provide one
	 * invalid input and check whether the method can return false. The invalid
	 * input is the third part of the input is more than the number of leftover
	 * armies.
	 */
	@Test
	public void checkValidArmyTest() {
		// d_player1 has 10 armies in total
		String l_input = "deploy 1 20";
		String[] l_inputArray = l_input.split(" ");
		String l_num = l_inputArray[2];
		boolean l_isValidNum = Deploy.CheckValidArmy(d_player1, Integer.parseInt(l_num));
		assertEquals(false, l_isValidNum);
	}
}
