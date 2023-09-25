package com.w10.risk_game.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
	private Player d_player;

	@BeforeEach
	public void beforeAllPlayerTests() {
		d_player = new Player("TestPlayerName", null, List.of(), 10);
	}

	@Test
	public void testAddArmies() {
		d_player.addArmies(2);
		assertEquals(12, d_player.getLeftoverArmies());
	}

	@Test
	public void testDeployArmies() {
		d_player.deployArmies(3);
		assertEquals(7, d_player.getLeftoverArmies());
	}
}
