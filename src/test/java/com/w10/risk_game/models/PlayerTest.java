package com.w10.risk_game.models;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * This is a test class for Player
 */
public class PlayerTest {
	Player player1;
	Player player2;
	List<Country> countries;
	/**
	 * This method is to set up the test environment
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		countries = new ArrayList<>();
		countries.add(new Country(1, "England", 1));
		countries.add(new Country(2, "Scotland", 1));
		player1 = new Player("Player1", countries, new ArrayList<>(), 10);
		player2 = new Player("Player2", countries, new ArrayList<>(), 10);
	}

	/**
	 * This method is to test the method of issueOrder
	 */
	@Test
	public void issueOrderTest() {
		// Test issueOrder
		String input1 = "deploy 1 6\ndeploy 2 4";
		System.setIn(new ByteArrayInputStream(input1.getBytes()));
		Scanner scanner = new Scanner(System.in);
		player1.issueOrder();
		assertEquals(2, player1.getOrders().size());
		assertEquals("deploy", player1.getOrders().get(0).getD_orderType());
		assertEquals(1, player1.getOrders().get(0).getD_countryId());
		assertEquals(6, player1.getOrders().get(0).getD_num());
		assertEquals("deploy", player1.getOrders().get(1).getD_orderType());
		assertEquals(2, player1.getOrders().get(1).getD_countryId());
		assertEquals(4, player1.getOrders().get(1).getD_num());
	}
}
