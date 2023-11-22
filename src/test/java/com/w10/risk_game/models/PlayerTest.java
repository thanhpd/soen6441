package com.w10.risk_game.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.commands.Advance;
import com.w10.risk_game.commands.Airlift;
import com.w10.risk_game.commands.Blockade;
import com.w10.risk_game.commands.Bomb;
import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.commands.Negotiate;
import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.controllers.MapEditorController;

/**
 * The PlayerTest class is a JUnit test class that tests the functionality of
 * the Player class in a game.
 *
 * @author Darlene-Naz, Yajing Liu
 */
class PlayerTest {
	private Player d_player;
	private Player d_player1;
	private Player d_player2;
	GamePlayController d_gamePlayController;

	/**
	 * The function sets up a Player object with a specific name, no territories,
	 * and a starting army count of 10 for testing purposes.
	 */
	@BeforeEach
	void beforeAllPlayerTests() {
		d_player = new Player("TestPlayerName", new ArrayList<Country>(), List.of(), 10);

		// Set for country
		Country l_country1 = new Country(1, "England", 1, 0);
		Country l_country2 = new Country(2, "Scotland", 1, 0);
		Country l_country3 = new Country(3, "Wales", 1, 0);
		Country l_country4 = new Country(4, "Ireland", 1, 3);
		l_country1.addNeighbor(l_country2);
		l_country2.addNeighbor(l_country1);
		l_country1.addNeighbor(l_country3);
		l_country3.addNeighbor(l_country1);

		// Set for player1
		List<Country> l_countries1 = new ArrayList<>();
		l_countries1.add(l_country1);
		l_countries1.add(l_country3);
		d_player1 = new Player("Player1", l_countries1, new ArrayList<>(), 10);
		l_country1.setOwner(d_player1);
		l_country3.setOwner(d_player1);

		// Set for player2
		List<Country> l_countries2 = new ArrayList<>();
		l_countries2.add(l_country2);
		l_countries2.add(l_country4);
		d_player2 = new Player("Player2", l_countries2, new ArrayList<>(), 10);
		l_country2.setOwner(d_player2);
		l_country4.setOwner(d_player2);

		// Set for game engine controller
		List<Player> l_players = new ArrayList<>();
		l_players.add(d_player1);
		l_players.add(d_player2);
		MapEditorController l_mapEditorController = new MapEditorController();
		d_gamePlayController = new GamePlayController(l_mapEditorController);
		d_gamePlayController.SetPlayerListForDiplomacy(l_players);
	}

	/**
	 * The testAddArmies function tests the addArmies method of the player object by
	 * adding 2 armies and checking if the leftover armies is equal to 12.
	 */
	@Test
	void testAddArmies() {
		d_player.addArmies(2);
		assertEquals(12, d_player.getLeftoverArmies());
	}

	/**
	 * The testDeployArmies function tests the deployArmies method of the player
	 * object by asserting that the leftoverArmies value is 7 after deploying 3
	 * armies.
	 */
	@Test
	void testDeployArmies() {
		d_player.deployArmies(3);
		assertEquals(7, d_player.getLeftoverArmies());
	}

	/**
	 * This function tests whether the valid deploy order can be issued and whether
	 * the invalid deploy order can be prevented from adding to order list.
	 */
	@Test
	void issueDeployOrderTest() {
		// Test valid deploy order
		String l_command = "deploy";
		String l_countryId = "1";
		String l_armyCount = "5";
		String[] l_inputArray = {l_command, l_countryId, l_armyCount};
		boolean l_result = Deploy.ValidateIssueDeployOrder(d_player1, l_inputArray);
		assertEquals(true, l_result);
		int l_orderLength = d_player1.getOrders().size();
		assertEquals(1, l_orderLength);

		// Test deploy armies to a country that does not belong to the player
		l_countryId = "30";
		l_inputArray[1] = l_countryId;
		l_result = Deploy.ValidateIssueDeployOrder(d_player1, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(1, l_orderLength);

		// Test deploy armies more than the leftover armies
		l_countryId = "1";
		l_inputArray[1] = l_countryId;
		l_armyCount = "20";
		l_inputArray[2] = l_armyCount;
		l_result = Deploy.ValidateIssueDeployOrder(d_player1, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(1, l_orderLength);
	}

	/**
	 * This function tests whether the valid advance order can be issued and whether
	 * the invalid advance order can be prevented from adding to order list.
	 */
	@Test
	void issueAdvanceOrderTest() {
		// Prepare for advance order test
		String l_command = "deploy";
		String l_countryId = "1";
		String l_armyCount = "5";
		String[] l_inputArray1 = {l_command, l_countryId, l_armyCount};
		boolean l_result = Deploy.ValidateIssueDeployOrder(d_player1, l_inputArray1);
		assertEquals(true, l_result);

		// Test valid advance order between two countries owned by different users
		l_command = "advance";
		String l_countryNameFrom = "England";
		String l_countryNameTo = "Scotland";
		String l_numArmies = "2";
		String[] l_inputArray2 = {l_command, l_countryNameFrom, l_countryNameTo, l_numArmies};
		l_result = Advance.ValidateIssueAdvanceOrder(d_player1, l_inputArray2);
		assertEquals(true, l_result);
		int l_orderLength = d_player1.getOrders().size();
		assertEquals(2, l_orderLength);

		// Test valid advance order between two countries owned by the same user
		l_countryNameTo = "Wales";
		l_inputArray2[2] = l_countryNameTo;
		l_result = Advance.ValidateIssueAdvanceOrder(d_player1, l_inputArray2);
		assertEquals(true, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(3, l_orderLength);

		// Test advance order from a country that does not belong to the player
		l_countryNameFrom = "Scotland";
		l_inputArray2[1] = l_countryNameFrom;
		l_result = Advance.ValidateIssueAdvanceOrder(d_player1, l_inputArray2);
		assertEquals(false, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(3, l_orderLength);
		l_countryNameFrom = "England";
		l_inputArray2[1] = l_countryNameFrom;

		// Test advance order to a country that is not a neighbor of the source country
		l_countryNameTo = "Ireland";
		l_inputArray2[2] = l_countryNameTo;
		l_result = Advance.ValidateIssueAdvanceOrder(d_player1, l_inputArray2);
		assertEquals(false, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(3, l_orderLength);
		l_countryNameTo = "Wales";
		l_inputArray2[2] = l_countryNameTo;

		// Test advance order with invalid number of armies
		l_numArmies = "20";
		l_inputArray2[3] = l_numArmies;
		l_result = Advance.ValidateIssueAdvanceOrder(d_player1, l_inputArray2);
		assertEquals(false, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(3, l_orderLength);
	}

	/**
	 * This function tests whether the valid bomb order can be issued and whether
	 * the invalid bomb order can be prevented from adding to order list.
	 */
	@Test
	void issueBombOrderTest() {
		// Test not have bomb card
		String l_command = "bomb";
		String l_countryId = "2";
		String[] l_inputArray = {l_command, l_countryId};
		boolean l_result = Bomb.ValidateIssueBombOrder(d_player1, l_inputArray);
		assertEquals(false, l_result);
		int l_orderLength = d_player1.getOrders().size();
		assertEquals(0, l_orderLength);

		// Test valid bomb order
		List<CardType> l_cardTypes = new ArrayList<>();
		l_cardTypes.add(CardType.BOMB);
		d_player1.setPlayerCards(l_cardTypes);
		l_result = Bomb.ValidateIssueBombOrder(d_player1, l_inputArray);
		assertEquals(true, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(1, l_orderLength);

		// Test bomb a country belong to the player
		l_countryId = "1";
		l_inputArray[1] = l_countryId;
		l_result = Bomb.ValidateIssueBombOrder(d_player1, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(1, l_orderLength);

		// Test bomb a country that is not a neighbor of the source country
		l_countryId = "4";
		l_inputArray[1] = l_countryId;
		l_result = Bomb.ValidateIssueBombOrder(d_player1, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player1.getOrders().size();
		assertEquals(1, l_orderLength);
	}

	/**
	 * This function tests whether the valid blockade order can be issued and
	 * whether the invalid blockade order can be prevented from adding to order
	 * list.
	 */
	@Test
	void issueBlockadeOrderTest() {
		// Test not have blockade card
		String l_command = "blockade";
		String l_countryId = "4";
		String[] l_inputArray = {l_command, l_countryId};
		boolean l_result = Blockade.ValidateIssueBlockadeOrder(d_player2, l_inputArray);
		assertEquals(false, l_result);
		int l_orderLength = d_player2.getOrders().size();
		assertEquals(0, l_orderLength);

		// Test valid blockade order
		List<CardType> l_cardTypes = new ArrayList<>();
		l_cardTypes.add(CardType.BLOCKADE);
		d_player2.setPlayerCards(l_cardTypes);
		l_result = Blockade.ValidateIssueBlockadeOrder(d_player2, l_inputArray);
		assertEquals(true, l_result);
		l_orderLength = d_player2.getOrders().size();
		assertEquals(1, l_orderLength);

		// Test blockade a country not belong to the player
		l_countryId = "1";
		l_inputArray[1] = l_countryId;
		l_result = Blockade.ValidateIssueBlockadeOrder(d_player2, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player2.getOrders().size();
		assertEquals(1, l_orderLength);
	}

	/**
	 * This function tests whether the valid airlift order can be issued and whether
	 * the invalid airlift order can be prevented from adding to order list.
	 */
	@Test
	void issueAirliftOrder() {
		// Test not have airlift card
		String l_command = "airlift";
		String l_countryIdFrom = "4";
		String l_countryIdTo = "2";
		String l_numArmies = "3";
		String[] l_inputArray = {l_command, l_countryIdFrom, l_countryIdTo, l_numArmies};
		boolean l_result = Airlift.ValidateIssueAirliftOrder(d_player2, l_inputArray);
		assertEquals(false, l_result);
		int l_orderLength = d_player2.getOrders().size();
		assertEquals(0, l_orderLength);

		// Test valid airlift order
		List<CardType> l_cardTypes = new ArrayList<>();
		l_cardTypes.add(CardType.AIRLIFT);
		d_player2.setPlayerCards(l_cardTypes);
		l_result = Airlift.ValidateIssueAirliftOrder(d_player2, l_inputArray);
		assertEquals(true, l_result);
		l_orderLength = d_player2.getOrders().size();
		assertEquals(1, l_orderLength);

		// Test airlift source country not belong to the player
		l_countryIdFrom = "1";
		l_inputArray[1] = l_countryIdFrom;
		l_result = Airlift.ValidateIssueAirliftOrder(d_player2, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player2.getOrders().size();
		assertEquals(1, l_orderLength);
		l_countryIdFrom = "4";
		l_inputArray[1] = l_countryIdFrom;

		// Test airlift destination country not belong to the player
		l_countryIdTo = "1";
		l_inputArray[2] = l_countryIdTo;
		l_result = Airlift.ValidateIssueAirliftOrder(d_player2, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player2.getOrders().size();
		assertEquals(1, l_orderLength);
		l_countryIdTo = "2";
		l_inputArray[2] = l_countryIdTo;

		// Test airlift armies more than the source country's armies
		l_numArmies = "20";
		l_inputArray[3] = l_numArmies;
		l_result = Airlift.ValidateIssueAirliftOrder(d_player2, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player2.getOrders().size();
		assertEquals(1, l_orderLength);
	}

	/**
	 * This function tests whether the valid negotiate order can be issued and
	 * whether the invalid negotiate order can be prevented from adding to order
	 * list.
	 */
	@Test
	void issueDiplomacyOrderTest() {
		// Test not have diplomacy card
		String l_command = "negotiate";
		String l_playerName = "Player1";
		String[] l_inputArray = {l_command, l_playerName};
		boolean l_result = Negotiate.ValidateIssueDiplomacyOrder(d_player2, l_inputArray);
		assertEquals(false, l_result);
		int l_orderLength = d_player2.getOrders().size();
		assertEquals(0, l_orderLength);

		// Test valid negotiate order
		List<CardType> l_cardTypes = new ArrayList<>();
		l_cardTypes.add(CardType.DIPLOMACY);
		d_player2.setPlayerCards(l_cardTypes);
		l_result = Negotiate.ValidateIssueDiplomacyOrder(d_player2, l_inputArray);
		assertEquals(true, l_result);
		l_orderLength = d_player2.getOrders().size();
		assertEquals(1, l_orderLength);

		// Test negotiate with the player self
		l_playerName = "Player2";
		l_inputArray[1] = l_playerName;
		l_result = Negotiate.ValidateIssueDiplomacyOrder(d_player2, l_inputArray);
		assertEquals(false, l_result);
		l_orderLength = d_player2.getOrders().size();
		assertEquals(1, l_orderLength);
	}
}
