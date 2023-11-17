package com.w10.risk_game.models.strategies;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.NoSuchElementException;

import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.commands.Order;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

public class AggressivePlayerStrategy extends PlayerStrategy {

	public AggressivePlayerStrategy(Player p_player) {
		super(p_player);
		// TODO Auto-generated constructor stub
	}

	protected int getStrongestCountryId() {
		return d_player.getCountriesOwned().stream().max(Comparator.comparing(Country::getArmyCount)).get()
				.getCountryId();
	}

	@Override
	public void issueOrder() {
		deployOnStrongestCountry();
		attackWithStrongestCountry();
		moveArmyToMaximizeForce();
	}

	protected void deployOnStrongestCountry() {

	}

	protected void attackWithStrongestCountry() {

	}

	protected void moveArmyToMaximizeForce() {

	}

	@Override
	public String getStrategyName() {
		return Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_AGGRESSIVE;
	}

}
