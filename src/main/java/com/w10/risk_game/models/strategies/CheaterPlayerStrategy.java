package com.w10.risk_game.models.strategies;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

public class CheaterPlayerStrategy extends PlayerStrategy {

	public CheaterPlayerStrategy(Player p_player) {
		super(p_player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * A cheater computer playerstrategy whoseissueOrder() method conquers all the
	 * immediate neighboring enemy countries, and then doubles the number of armies
	 * on its countries that have enemy neighbors. Note that in order to achieve
	 * this, the cheater’s strategy implementation will still be called when the
	 * issueOrder() method, but will not end up creating orders, but rather
	 * implement the above-stated behavior by directly affecting the map during the
	 * order creation phase.
	 */
	@Override
	public void issueOrder() {
		// 1. Conquers all neighbor country

		conquerNeighborCountries();
		// 2. Double Armies in coutnires with enemy neighbor
		doubleArmiesInCountriesWithEnemyNeighbor();
	}

	protected void conquerNeighborCountries() {
		d_player.getCountriesOwned();

	}

	protected void doubleArmiesInCountriesWithEnemyNeighbor() {

	}

	@Override
	public String getStrategyName() {
		return Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_CHEATER;
	}

}
