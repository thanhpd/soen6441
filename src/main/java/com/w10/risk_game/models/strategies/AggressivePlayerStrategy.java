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

	@Override
	public boolean issueDeployOrder(String[] p_inputArray) {
		// String l_countryId = getStrongestCountryId() + "";
		// String l_num = p_inputArray[2];

		// // Validate the order
		// if (Deploy.ValidateOrder(d_Player, l_countryId, l_num)) {
		// 	// If the order is valid, create a Deploy order and add it to the list of orders
		// 	Order order = new Deploy(d_Player, Integer.parseInt(p_inputArray[1]), Integer.parseInt(p_inputArray[2]));
		// 	d_Player.getOrders().add(order);

		// 	// Deploy the specified number of armies to the country
		// 	d_Player.deployArmies(Integer.parseInt(p_inputArray[2]));

		// 	// Log that the deploy order was successful
		// 	Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
		// 	return true; // Return true indicating the successful execution of the order
		// } else {
		// 	// Log if the deploy order was incorrect or invalid
		// 	Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
		// 			Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY));
		 	return false; // Return false for an unsuccessful order execution
		// }
	}

	protected int getStrongestCountryId() {
		return d_Player.getCountriesOwned().stream().max(Comparator.comparing(Country::getArmyCount)).get()
				.getCountryId();
	}

	@Override
	public boolean issueAdvanceOrder(String[] p_inputArray) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'issueAdvanceOrder'");
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

}
