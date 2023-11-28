package com.w10.risk_game.models.strategies;

import java.text.MessageFormat;
import java.util.List;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.GamePlayHelper;

/**
 * The CheaterPlayerStrategy class is a subclass of PlayerStrategy that
 * implements a strategy where the player conquers all neighboring countries and
 * doubles the armies in countries with enemy neighbors.
 */
public class CheaterPlayerStrategy extends PlayerStrategy {

	/**
	 * The constructor is used to initialize the data member d_player.
	 *
	 * @param p_player
	 *            The Player object.
	 */
	public CheaterPlayerStrategy(Player p_player) {
		super(p_player);
	}

	/**
	 * The issueOrder function conquers all neighboring countries and doubles the
	 * armies in countries with enemy neighbors, then sets the player's leftover
	 * armies to 0 and marks them as committed.
	 */
	@Override
	public void issueOrder() {
		// 1. Conquers all neighbor country
		conquerNeighborCountries();
		// 2. Double Armies in countries with enemy neighbor
		doubleArmiesInCountriesWithEnemyNeighbor();

		d_player.setLeftoverArmies(0);
		d_player.setHasCommitted(true);
	}

	/**
	 * The function allows a player to automatically conquer all neighboring
	 * countries
	 */
	private void conquerNeighborCountries() {
		// Get foreign enemy/neutral countries
		List<Country> l_foreignEnemyCountries = GamePlayHelper.GetForeignNeighbors(d_player);

		// Conquer all foreign enemy/neutral countries
		for (Country l_country : l_foreignEnemyCountries) {
			if (l_country.getOwner() != d_player) {
				String l_format = MessageFormat.format(Constants.STRATEGY_CHEATER_TAKE_OVER, d_player.getName(),
						l_country.getCountryName(),
						l_country.getOwner() != null ? l_country.getOwner().getName() : "Neutral");

				// For each foreign enemy/neutral country, reassign the owner and
				// update the player's country list
				if (l_country.getOwner() != null) {
					l_country.getOwner().removeCountry(l_country);
				}
				l_country.setOwner(d_player);
				d_player.addCountry(l_country);

				Logger.log(l_format);
			}
		}
	}

	/**
	 * The function doubles the number of armies in countries owned by the player if
	 * they have at least one enemy neighbor.
	 */
	private void doubleArmiesInCountriesWithEnemyNeighbor() {
		for (Country l_country : d_player.getCountriesOwned()) {
			if (l_country.getNeighbors().values().stream().anyMatch(p_country -> p_country.getOwner() != d_player)) {
				l_country.setArmyCount(l_country.getArmyCount() * 2);

				Logger.log(MessageFormat.format(Constants.STRATEGY_CHEATER_DOUBLE_ARMY, d_player.getName(),
						l_country.getCountryName()));
			}
		}
	}

	/**
	 * The function returns the name of a strategy called "cheater" for a player.
	 *
	 * @return The method is returning the constant value
	 *         "USER_INPUT_COMMAND_PLAYER_STRATEGY_CHEATER".
	 */
	@Override
	public String getStrategyName() {
		return Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_CHEATER;
	}
}
