package com.w10.risk_game.commands;

import com.w10.risk_game.controllers.GameEngineController;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a test class for negotiate order
 *
 * @see Negotiate
 * @author Yajing Liu
 */
public class NegotiateTest {
	Player d_player1;
	Player d_player2;
	Order d_negotiateOrder;
	GameEngineController d_gameEngineController;

	/**
	 * This method is to set up the test environment
	 */
	@BeforeEach
	public void setUp() {
		// Set for player1
		Country l_country1 = new Country(1, "England", 1, 10);
		Country l_country2 = new Country(2, "France", 1, 10);
		List<Country> l_countries1 = new ArrayList<>();
		l_countries1.add(l_country1);
		List<Order> orderList1 = new ArrayList<>();
		d_player1 = new Player("Player1", l_countries1, orderList1, 10);
		l_country1.setOwner(d_player1);
		d_negotiateOrder = new Negotiate(d_player1, "Player2");
		Order l_advance1 = new Advance(l_country1, l_country2, 3);
		orderList1.add(d_negotiateOrder);
		orderList1.add(l_advance1);
		d_player1.setOrders(orderList1);

		// Set for player2
		List<Country> l_countries2 = new ArrayList<>();
		l_countries2.add(l_country2);
		List<Order> orderList2 = new ArrayList<>();
		d_player2 = new Player("Player2", l_countries2, new ArrayList<>(), 10);
		l_country2.setOwner(d_player2);
		Order l_order2 = new Advance(l_country2, l_country1, 3);
		orderList2.add(l_order2);
		d_player2.setOrders(orderList2);

		// Set for game engine controller
		List<Player> l_players = new ArrayList<>();
		l_players.add(d_player1);
		l_players.add(d_player2);
		MapEditorController l_mapEditorController = new MapEditorController();
		d_gameEngineController = new GameEngineController(l_mapEditorController);
		d_gameEngineController.setPlayerListForDiplomacy(l_players);
	}

	/**
	 * This method is to test the method of execute
	 */
	@Test
	public void executeTest() {
		d_negotiateOrder.execute();
		assertEquals(1, d_player1.getOrders().size());
		assertEquals(0, d_player2.getOrders().size());
	}

	/**
	 * This method is to test the method of validateOrder
	 */
	@Test
	public void validateOrderTest() {
		boolean l_isNotNegotiateSelf = Negotiate.ValidateOrder(d_player1, "Player1");
		assertEquals(false, l_isNotNegotiateSelf);
		boolean l_isNotNegotiatePlayerNotExist = Negotiate.ValidateOrder(d_player1, "Player3");
		assertEquals(false, l_isNotNegotiatePlayerNotExist);
	}
}
