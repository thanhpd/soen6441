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
class DeployTest {
	Order d_order1;
	Player d_player1;

	/**
	 * This method is to set up the test environment
	 */
	@BeforeEach
	void setUp() {
		List<Country> l_countries = new ArrayList<>();
		l_countries.add(new Country(1, "England", 1, 0));
		d_player1 = new Player("Player1", l_countries, new ArrayList<>(), 10);
		d_order1 = new Deploy(d_player1, 1, 6);
	}

	/**
	 * This method is to test the method of deploy
	 */
	@Test
	void executeTest() {
		// Test deploy
		d_order1.execute();
		assertEquals(6, d_player1.getCountriesOwned().get(0).getArmyCount());
	}

	/**
	 * This method is to test the method of checkValidDeployInput. It will provide
	 * three invalid inputs and check whether the method can return false. The three
	 * invalid inputs are: 1. There are not three parts in the input. 2. The second
	 * part is not an integer. 3. The third part is not an integer.
	 */
	@Test
	void checkValidDeployInputTest() {
		// Test Case 1: "invalid input"
		String l_input1 = "invalid input";
		boolean l_isValidForm = Deploy.CheckValidDeployInput(l_input1.split(" "));
		assertEquals(false, l_isValidForm);
		// Test Case 2: "invalid input 1"
		String l_input2 = "invalid input 1";
		boolean l_isValidForm2 = Deploy.CheckValidDeployInput(l_input2.split(" "));
		assertEquals(false, l_isValidForm2);
		// Test Case 3: "invalid 1 input"
		String l_input3 = "invalid 1 input";
		// Check whether the Deploy.CheckValidDeployInput method correctly identifies
		// invalid input
		boolean l_isValidForm3 = Deploy.CheckValidDeployInput(l_input3.split(" "));
		assertEquals(false, l_isValidForm3);
	}

	/**
	 * This method is to test the method of checkValidCountry. It will provide one
	 * invalid input and check whether the method can return false. The invalid
	 * input is the second part of the input is not contained in the countries owned
	 * by the player.
	 */
	@Test
	void checkValidCountryTest() {
		String l_input = "deploy 0 6";
		String[] l_inputArray = l_input.split(" ");
		String l_countryId = l_inputArray[1];
		// Fetch a list of countries owned by a player
		List<Country> l_countries = d_player1.getCountriesOwned();
		// Check if the country is valid for deployment
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
	void checkValidArmyTest() {
		// d_player1 has 10 armies in total
		String l_input = "deploy 1 20";
		String[] l_inputArray = l_input.split(" ");
		String l_num = l_inputArray[2];
		// Check if the number of armies is valid for deployment
		boolean l_isValidNum = Deploy.CheckValidArmy(d_player1, Integer.parseInt(l_num));
		assertEquals(false, l_isValidNum);
	}
}
