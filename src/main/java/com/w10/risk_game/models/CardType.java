package com.w10.risk_game.models;

import java.util.Random;

public enum CardType {

	BOMB, BLOCKADE, AIRLIFT, DIPLOMACY;

	public static CardType getRandomCard() {
		Random d_Random = new Random();
		CardType d_Type = values()[d_Random.nextInt(values().length)];
		return d_Type;
	}
}
