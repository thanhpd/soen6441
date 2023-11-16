package com.w10.risk_game.models.strategies;

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
		sortCountriesOwnedByArmyCount();
		deployOnStrongestCountry();
		attackWithStrongestCountry();
		moveArmyToMaximizeForce();
	}

	/**
	 * The function sorts the countries owned by a player based on their army count
	 * and assigns the country with the highest army count to a variable.
	 */
	private void sortCountriesOwnedByArmyCount() {
		this.d_strongestCountryOwned = d_player.getCountriesOwned().stream()
				.max(Comparator.comparingInt(Country::getArmyCount)).orElse(null);
	}

	/**
	 * The function "deployOnStrongestCountry" deploys the player's leftover armies
	 * on their strongest country.
	 */
	private void deployOnStrongestCountry() {
		if (d_strongestCountryOwned != null) {
			Deploy.ValidateIssueDeployOrder(d_player,
					new String[]{Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY,
							Integer.toString(this.d_strongestCountryOwned.getCountryId()),
							Integer.toString(this.d_player.getLeftoverArmies())});
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
					Bomb.ValidateIssueBombOrder(d_player, new String[]{Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB,
							Integer.toString(l_enemyCountryWithMaxArmy.getCountryId())});
				}
			}
			List<Country> l_enemyCountries = d_strongestCountryOwned.getNeighbors().values().stream()
					.filter(l_neighbor -> !this.d_player.getName().equals(l_neighbor.getOwner().getName()))
					.collect(Collectors.toList());
			int l_noOfArmiesOnStrongestCountry = Advance.GetTotalArmiesDeployed(d_player,
					d_strongestCountryOwned.getArmyCount(), d_strongestCountryOwned.getCountryId());
			for (Country l_enemyCountry : l_enemyCountries) {
				if (l_noOfArmiesOnStrongestCountry > 0
						&& l_enemyCountry.getArmyCount() < l_noOfArmiesOnStrongestCountry) {
					Advance.ValidateIssueAdvanceOrder(d_player,
							new String[]{Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE,
									d_strongestCountryOwned.getCountryName(), l_enemyCountry.getCountryName(),
									Integer.toString(l_enemyCountry.getArmyCount() + 1)});
					l_noOfArmiesOnStrongestCountry -= l_enemyCountry.getArmyCount() - 1;
				} else
					break;
			}
		}
	}

	/**
	 * The function moves the army from the strongest country owned by the player to
	 * a neighboring country with enemies in order to maximize force.
	 */
	private void moveArmyToMaximizeForce() {
		if (d_strongestCountryOwned.getArmyCount() > 0) {
			List<Country> l_neighborsWithEnemies = getNeighborsWithEnemies(d_strongestCountryOwned);
			Country l_toCountry = l_neighborsWithEnemies.stream().max(Comparator.comparingInt(Country::getArmyCount))
					.orElse(null);
			if (Objects.nonNull(d_strongestCountryOwned) && Objects.nonNull(l_toCountry)) {
				Advance.ValidateIssueAdvanceOrder(d_player,
						new String[]{Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE,
								d_strongestCountryOwned.getCountryName(), l_toCountry.getCountryName(),
								Integer.toString(d_strongestCountryOwned.getArmyCount())});
			}
		}
	}

	/**
	 * The function returns a list of neighboring countries that have at least one
	 * enemy country.
	 *
	 * @param p_fromCountry
	 *            The country from which we want to find neighbors with enemies.
	 * @return The method is returning a list of countries that are neighbors of the
	 *         given country and have at least one neighbor that is owned by a
	 *         different player.
	 */
	private List<Country> getNeighborsWithEnemies(Country p_fromCountry) {
		return p_fromCountry.getNeighbors().values().stream().takeWhile(l_neighbor -> {
			Long count = l_neighbor.getNeighbors().values().stream().filter(
					l_farNeighbor -> !l_farNeighbor.getOwner().getName().equals(p_fromCountry.getOwner().getName()))
					.count();
			return count > 0;
		}).collect(Collectors.toList());
	}
}
