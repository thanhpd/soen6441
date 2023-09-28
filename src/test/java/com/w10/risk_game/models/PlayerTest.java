package com.w10.risk_game.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * The PlayerTest class is a JUnit test class that tests the functionality of
 * the Player class in a game.
 *
 * @author Darlene-Naz, Yajing Liu
 */
public class PlayerTest {
	private Player d_player;
	private Player d_player1;
	List<Country> d_countries;

	/**
	 * The function sets up a Player object with a specific name, no territories,
	 * and a starting army count of 10 for testing purposes.
	 */
	@BeforeEach
	public void beforeAllPlayerTests() {
		d_player = new Player("TestPlayerName", new ArrayList<Country>(), List.of(), 10);

		d_countries = new ArrayList<>();
		d_countries.add(new Country(1, "England", 1, 0));
		d_countries.add(new Country(2, "Scotland", 1, 0));
		d_player1 = new Player("Player1", d_countries, new ArrayList<>(), 10);
	}

	/**
	 * The testAddArmies function tests the addArmies method of the player object by
	 * adding 2 armies and checking if the leftover armies is equal to 12.
	 */
	@Test
	public void testAddArmies() {
		d_player.addArmies(2);
		assertEquals(12, d_player.getLeftoverArmies());
	}

	/**
	 * The testDeployArmies function tests the deployArmies method of the player
	 * object by asserting that the leftoverArmies value is 7 after deploying 3
	 * armies.
	 */
	@Test
	public void testDeployArmies() {
		d_player.deployArmies(3);
		assertEquals(7, d_player.getLeftoverArmies());
	}

	/**
	 * The issueOrderTest function tests the issueOrder method of the player object.
	 * It sets the input, and test whether the orders are added to the player's
	 * order list
	 */
	@Test
	@Disabled
	public void issueOrderTest() {
		String l_input1 = "deploy 1 6\ndeploy 2 4";
		System.setIn(new ByteArrayInputStream(l_input1.getBytes()));
		Scanner l_scanner = new Scanner(System.in);
		d_player1.issueOrder();
		assertEquals(2, d_player1.getOrders().size());
		assertEquals("deploy", d_player1.getOrders().get(0).getOrderType());
		assertEquals(1, d_player1.getOrders().get(0).getCountryId());
		assertEquals(6, d_player1.getOrders().get(0).getNum());
		assertEquals("deploy", d_player1.getOrders().get(1).getOrderType());
		assertEquals(2, d_player1.getOrders().get(1).getCountryId());
		assertEquals(4, d_player1.getOrders().get(1).getNum());
	}

	/**
	 * This method is to test the method of checkValidForm. It will provide three
	 * invalid inputs and check whether the method can return false. The three
	 * invalid inputs are: 1. There are not three parts in the input. 2. The second
	 * part is not an integer. 3. The third part is not an integer.
	 */
	@Test
	public void checkValidFormTest() {
		String l_input1 = "invalid input";
		boolean l_isValidForm = d_player1.checkValidForm(l_input1.split(" "));
		assertEquals(false, l_isValidForm);
		String l_input2 = "invalid input 1";
		boolean l_isValidForm2 = d_player1.checkValidForm(l_input2.split(" "));
		assertEquals(false, l_isValidForm2);
		String l_input3 = "invalid 1 input";
		boolean l_isValidForm3 = d_player1.checkValidForm(l_input3.split(" "));
		assertEquals(false, l_isValidForm3);
	}
	/**
	 * This method is to test the method of checkValidOrder. It will provide one
	 * invalid input and check whether the method can return false. The invalid
	 * input is the first part of the input is not "deploy".
	 */
	@Test
	public void checkValidOrderTest() {
		String l_input = "notDeploy 1 6";
		String[] l_inputArray = l_input.split(" ");
		String l_orderType = l_inputArray[0];
		boolean l_isValidOrder = d_player1.checkValidOrder(l_orderType);
		assertEquals(false, l_isValidOrder);
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
		boolean l_isValidCountry = d_player1.checkValidCountry(d_countries, l_countryId);
		assertEquals(false, l_isValidCountry);
	}

	/**
	 * This method is to test the method of checkValidNum. It will provide one
	 * invalid input and check whether the method can return false. The invalid
	 * input is the third part of the input is more than the number of leftover
	 * armies.
	 */
	@Test
	public void checkValidNumTest() {
		String l_input = "deploy 1 20";
		String[] l_inputArray = l_input.split(" ");
		String l_num = l_inputArray[2];
		int l_army = d_player1.getLeftoverArmies();
		boolean l_isValidNum = d_player1.checkValidNum(Integer.parseInt(l_num), l_army);
		assertEquals(false, l_isValidNum);
	}
}
