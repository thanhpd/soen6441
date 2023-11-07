package com.w10.risk_game.models;

import java.util.Random;

public enum CardType {

	BOMB, BLOCKADE, AIRLIFT, DIPLOMACY;

	/**
	 * The getRandomCard function returns a random CardType value.
	 *
	 * @return The method is returning a random CardType.
	 */
	public static CardType getRandomCard() {
		// Random d_Random = new Random();
		CardType d_Type = AIRLIFT;
		return d_Type;
	}
}
