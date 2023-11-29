package com.w10.risk_game.models.strategies;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.w10.risk_game.commands.Advance;
import com.w10.risk_game.commands.Bomb;
import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

/**
 * The AggressivePlayerStrategy class represents a strategy for a player in a
 * game, where the player deploys armies on their strongest country, attacks
 * neighboring countries with their strongest country, and moves armies to
 * maximize force.
 *
 * @author Darlene-Naz
 */
public class AggressivePlayerStrategy extends PlayerStrategy {
	private Country d_strongestCountryOwned;

	public AggressivePlayerStrategy(Player p_player) {
		super(p_player);
	}

	/**
	 * The function "issueOrder" sorts the countries owned by the player based on
	 * army count, deploys on the strongest country, attacks with the strongest
	 * country, and moves the army to maximize force.
	 */
	@Override
	public void issueOrder() {
		setStrongestCountryOwned();
		deployOnStrongestCountry();
		attackWithStrongestCountry();
		moveArmyToMaximizeForce();
		this.d_player.setHasCommitted(true);
	}

	/**
	 * The function sorts the countries owned by a player based on their army count
	 * and assigns the country with the highest army count to a variable.
	 */
	private void setStrongestCountryOwned() {
		this.d_strongestCountryOwned = d_player.getCountriesOwned().stream()
				.max(Comparator.comparingInt(Country::getArmyCount)).orElse(null);
	}

	/**
	 * The function "deployOnStrongestCountry" deploys the player's leftover armies
	 * on their strongest country.
	 */
	private void deployOnStrongestCountry() {
		if (d_strongestCountryOwned != null) {
			String[] l_deployOrder = {Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY,
					Integer.toString(this.d_strongestCountryOwned.getCountryId()),
					Integer.toString(this.d_player.getLeftoverArmies())};
			Logger.log(
					MessageFormat.format(Constants.STRATEGY_ISSUE_ORDER, String.join(Constants.SPACE, l_deployOrder)));
			Deploy.ValidateIssueDeployOrder(d_player, l_deployOrder);
		}
	}

	/**
	 * The function "attackWithStrongestCountry" allows the player to attack
	 * neighboring countries with their strongest country, using a bomb if
	 * available.
	 */
	private void attackWithStrongestCountry() {
		if (d_strongestCountryOwned != null) {
			if (d_player.getPlayerCards().contains(CardType.BOMB)) {
				Country l_enemyCountryWithMaxArmy = d_player.getCountriesOwned().stream()
						.flatMap(l_country -> l_country.getNeighbors().values().stream())
						.filter(l_neighbor -> !this.d_player.getName().equals(l_neighbor.getOwner().getName()))
						.max(Comparator.comparingInt(Country::getArmyCount)).orElse(null);
				if (l_enemyCountryWithMaxArmy != null) {
					String[] d_bombOrder = {Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB,
							Integer.toString(l_enemyCountryWithMaxArmy.getCountryId())};
					Logger.log(MessageFormat.format(Constants.STRATEGY_ISSUE_ORDER,
							String.join(Constants.SPACE, d_bombOrder)));
					Bomb.ValidateIssueBombOrder(d_player, d_bombOrder);
				}
			}
			Country l_enemyNeighborWithMaxArmy = d_strongestCountryOwned.getNeighbors().values().stream()
					.filter(l_neighbor -> !this.d_player.getName().equals(l_neighbor.getOwner().getName()))
					.max(Comparator.comparingInt(Country::getArmyCount)).orElse(null);
			int l_noOfArmiesOnStrongestCountry = Advance.GetTotalArmiesDeployed(d_player,
					d_strongestCountryOwned.getArmyCount(), d_strongestCountryOwned.getCountryId());
			if (l_enemyNeighborWithMaxArmy != null && l_noOfArmiesOnStrongestCountry > 1) {
				String[] l_advanceOrder = {Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE,
						d_strongestCountryOwned.getCountryName(), l_enemyNeighborWithMaxArmy.getCountryName(),
						Integer.toString(l_noOfArmiesOnStrongestCountry - 1)};
				Logger.log(MessageFormat.format(Constants.STRATEGY_ISSUE_ORDER,
						String.join(Constants.SPACE, l_advanceOrder)));
				Advance.ValidateIssueAdvanceOrder(d_player, l_advanceOrder);
			}
		}
	}

	/**
	 * The function moves the army from the strongest country owned by the player to
	 * a neighboring country with enemies in order to maximize force.
	 */
	private void moveArmyToMaximizeForce() {
		if (Objects.nonNull(d_strongestCountryOwned)) {
			int l_noOfArmiesOnStrongestCountry = Advance.GetTotalArmiesDeployed(d_player,
					d_strongestCountryOwned.getArmyCount(), d_strongestCountryOwned.getCountryId());
			// check for l_noOfArmiesOnStrongestCountry > 1 i.e. attack didn't happen as no
			// more neighboring enemies so we move to a neighbor with enemies
			if (l_noOfArmiesOnStrongestCountry > 1) {
				List<Country> l_neighborsWithEnemies = getNeighborsWithEnemies(d_strongestCountryOwned);
				Country l_toCountry = l_neighborsWithEnemies.stream()
						.max(Comparator.comparingInt(Country::getArmyCount)).orElse(null);
				if (Objects.nonNull(l_toCountry)) {
					String[] l_advanceOrder = {Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE,
							d_strongestCountryOwned.getCountryName(), l_toCountry.getCountryName(),
							Integer.toString(l_noOfArmiesOnStrongestCountry - 1)};
					Logger.log(MessageFormat.format(Constants.STRATEGY_ISSUE_ORDER,
							String.join(Constants.SPACE, l_advanceOrder)));
					Advance.ValidateIssueAdvanceOrder(d_player, l_advanceOrder);
				}
			}
		}
	}

	/**
	 * The function returns a list of neighboring countries of a given country that
	 * have at least one enemy neighbor, sorted by their army count in descending
	 * order.
	 *
	 * @param p_fromCountry
	 *            The parameter "p_fromCountry" represents the country for which we
	 *            want to find its neighboring countries that have enemies.
	 * @return The method is returning a list of countries that are neighbors of the
	 *         given "p_fromCountry" and have at least one neighbor that is owned by
	 *         a different player. The list is sorted in descending order based on
	 *         the number of armies in each country.
	 */
	private List<Country> getNeighborsWithEnemies(Country p_fromCountry) {
		return p_fromCountry.getNeighbors().values().stream().takeWhile(l_neighbor -> {
			Long count = l_neighbor.getNeighbors().values().stream().filter(
					l_farNeighbor -> !l_farNeighbor.getOwner().getName().equals(p_fromCountry.getOwner().getName()))
					.count();
			return count > 0;
		}).sorted(Comparator.comparingInt(Country::getArmyCount).reversed()).collect(Collectors.toList());
	}

	/**
	 * The function returns the name of a strategy called "Aggressive" as a constant
	 * string.
	 *
	 * @return The method is returning the constant value
	 *         "USER_INPUT_COMMAND_PLAYER_STRATEGY_AGGRESSIVE".
	 */
	@Override
	public String getStrategyName() {
		return Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_AGGRESSIVE;
	}

	/**
	 * The function returns the strongest country owned by the player.
	 *
	 * @return the strongest country owned by the player.
	 */
	public Country getStrongestCountryOwned() {
		return d_strongestCountryOwned;
	}

	/**
	 * The function sets the strongest country owned by the player.
	 *
	 * @param d_strongestCountryOwned
	 *            the strongest country owned by the player.
	 */
	public void setStrongestCountryOwned(Country d_strongestCountryOwned) {
		this.d_strongestCountryOwned = d_strongestCountryOwned;
	}
}
