package com.w10.risk_game.models.strategies;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.w10.risk_game.commands.Advance;
import com.w10.risk_game.commands.Airlift;
import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.commands.Negotiate;
import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

/**
 * The BenevolentPlayerStrategy class is a subclass of PlayerStrategy that
 * implements a strategy where the player issues deploy and advance orders to
 * their weakest country.
 *
 * @author Sherwyn Dsouza
 */
public class BenevolentPlayerStrategy extends PlayerStrategy {

	/**
	 * This is the constructor of the BenevolentPlayerStrategy class
	 *
	 * @param p_player
	 *            The player object representing the player who is initiating the
	 *            order.
	 */
	public BenevolentPlayerStrategy(Player p_player) {
		super(p_player);
	}

	/**
	 * The function issues deploy, advance and commit orders to the weakest country
	 * owned by the player.
	 */
	@Override
	public void issueOrder() {
		Country l_weakestCountry = this.getPlayerWeakestCountry();
		if (l_weakestCountry != null) {
			Deploy.ValidateIssueDeployOrder(d_player, this.getDeployOrder(l_weakestCountry));
			this.issueAdvanceOrdersToWeakestCountry(l_weakestCountry);
			if (d_player.hasCard(CardType.AIRLIFT) || d_player.hasCard(CardType.DIPLOMACY))
				this.issuePlayerCardOrders(l_weakestCountry);
			d_player.setHasCommitted(true);
		}
	}

	/**
	 * The function getPlayerWeakestCountry returns the weakest country owned by the
	 * player.
	 *
	 * @return The method is returning the weakest country owned by the player.
	 */
	private Country getPlayerWeakestCountry() {
		List<Country> l_listOfCountriesOwned = d_player.getCountriesOwned();
		Country l_weakestCountry = null;

		if (!l_listOfCountriesOwned.isEmpty()) {
			int l_minArmies = l_listOfCountriesOwned.get(0).getArmyCount();
			l_weakestCountry = l_listOfCountriesOwned.get(0);

			for (Country l_country : l_listOfCountriesOwned) {
				if (l_country.getArmyCount() < l_minArmies) {
					l_minArmies = l_country.getArmyCount();
					l_weakestCountry = l_country;
				}
			}
		}
		return l_weakestCountry;
	}

	/**
	 * The function returns an array of strings that represents a deploy order
	 * command in a game, with the weakest country and the player's leftover armies
	 * as parameters.
	 *
	 * @param l_weakestCountry
	 *            The parameter "l_weakestCountry" is of type "Country" and
	 *            represents the weakest country of the player in the game.
	 * @return The method is returning a String array.
	 */
	private String[] getDeployOrder(Country l_weakestCountry) {
		return new String[]{Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY,
				Integer.toString(l_weakestCountry.getCountryId()), Integer.toString(d_player.getLeftoverArmies())};
	}

	/**
	 * The function issues advance orders to the weakest country's neighbors.
	 *
	 * @param l_weakestCountry
	 *            The parameter "l_weakestCountry" represents the weakest country of
	 *            the player in the game. It is an object of the "Country" class.
	 */
	private void issueAdvanceOrdersToWeakestCountry(Country l_weakestCountry) {
		Collection<Country> l_weakCountryNeighbors = l_weakestCountry.getNeighbors().values();
		for (Country l_country : l_weakCountryNeighbors) {
			String[] l_advanceOrder = new String[]{Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE,
					l_country.getCountryName(), l_weakestCountry.getCountryName(),
					Integer.toString(l_country.getArmyCount())};
			Advance.ValidateIssueAdvanceOrder(d_player, l_advanceOrder);
		}
	}

	/**
	 * This function plays the Airlift and Negotiate cards if received by the player
	 *
	 * @param l_weakestCountry
	 *            The parameter "l_weakestCountry" is of type "Country" and
	 *            represents the weakest country of the player in the game.
	 */
	private void issuePlayerCardOrders(Country l_weakestCountry) {
		String[] l_order;

		if (d_player.hasCard(CardType.AIRLIFT)) {
			Country l_strongestCountry = d_player.getCountriesOwned().stream()
					.max(Comparator.comparingInt(Country::getArmyCount)).orElse(null);
			l_order = new String[]{Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT,
					Integer.toString(l_strongestCountry.getCountryId()),
					Integer.toString(l_weakestCountry.getCountryId()),
					Integer.toString(l_strongestCountry.getArmyCount())};
			Airlift.ValidateIssueAirliftOrder(d_player, l_order);
		}

		if (d_player.hasCard(CardType.DIPLOMACY)) {
			Country l_strongestEnemyCountry = l_weakestCountry.getNeighbors().values().stream()
					.filter(l_country -> !l_country.getOwner().getName().equals(d_player.getName()))
					.max(Comparator.comparingInt(Country::getArmyCount)).orElse(null);
			l_order = new String[]{Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE,
					Integer.toString(l_strongestEnemyCountry.getCountryId())};
			Negotiate.ValidateIssueDiplomacyOrder(d_player, l_order);
		}
	}
}