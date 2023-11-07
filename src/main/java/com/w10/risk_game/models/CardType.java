package com.w10.risk_game.models;

import java.util.Random;

public enum CardType {

	BOMB, BLOCKADE, AIRLIFT, DIPLOMACY;

	/**
	 * The GetRandomCard function returns a random CardType value.
	 *
	 * @return The method is returning a random CardType.
	 */
	public static CardType GetRandomCard() {
		Random d_Random = new Random();
		return values()[d_Random.nextInt(values().length)];
	}
}
