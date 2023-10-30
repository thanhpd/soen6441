package com.w10.risk_game.models;

public class Card {
	private CardType d_CardType;

	public Card() {
		d_CardType = CardType.getRandomCard();
	}

	public Card(CardType p_CardType) {
		d_CardType = p_CardType;
	}

	public CardType getCardType() {
		return d_CardType;
	}

	public void setCardType(CardType p_cardType) {
		d_CardType = p_cardType;
	}
}
