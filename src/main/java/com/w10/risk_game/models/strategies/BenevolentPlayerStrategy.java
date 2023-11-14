package com.w10.risk_game.models.strategies;

import com.w10.risk_game.models.Player;

public class BenevolentPlayerStrategy extends PlayerStrategy {

	public BenevolentPlayerStrategy(Player p_player) {
		super(p_player);

	}

	@Override
	public void issueOrder() {
		getWeakContries();
		moveArmiesToWeakCountries();
	}

	protected void getWeakContries() {

	}

	protected void moveArmiesToWeakCountries() {

	}
}
