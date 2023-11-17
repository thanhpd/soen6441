package com.w10.risk_game.models.strategies;

import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.w10.risk_game.commands.Advance;
import com.w10.risk_game.commands.Airlift;
import com.w10.risk_game.commands.Bomb;
import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.GamePlayHelper;

/**
 * The RandomPlayerStrategy class is a subclass of PlayerStrategy that
 * implements random player strategy in a game.
 */
public class RandomPlayerStrategy extends PlayerStrategy {

	/**
	 * The constructor is used to initialize the data member d_player.
	 *
	 * @param p_player
	 *            The Player object.
	 */
	public RandomPlayerStrategy(Player p_player) {
		super(p_player);
	}

	/**
	 * The issueOrder function deploys troops on a random country, uses cards,
	 * advances on a random country, and sets the player's hasCommitted flag to
	 * true.
	 */
	@Override
	public void issueOrder() {
		deployOnRandomCountry();
		useCards();
		advanceOnRandomCountry();
		d_player.setHasCommitted(true);
	}

	/**
	 * The function "deployOnRandomCountry" deploys a random number of armies to a
	 * randomly selected country owned by the player.
	 */
	private void deployOnRandomCountry() {
		// Get a random country owned by the player
		Random random = new Random();
		int l_index = random.nextInt(d_player.getCountriesOwned().size());
		Country l_randomOwnedCountry = d_player.getCountriesOwned().get(l_index);

		// Deploy all armies in the pool to the random country
		if (l_randomOwnedCountry != null && d_player.getLeftoverArmies() > 0) {
			String[] l_deployOrder = {Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY,
					Integer.toString(l_randomOwnedCountry.getCountryId()),
					Integer.toString(this.d_player.getLeftoverArmies())};
			Logger.log(
					MessageFormat.format(Constants.STRATEGY_ISSUE_ORDER, String.join(Constants.SPACE, l_deployOrder)));
			Deploy.ValidateIssueDeployOrder(d_player, l_deployOrder);
		}
	}

	/**
	 * The function "useCards" checks if the player has a bomb or airlift card and
	 * uses them accordingly.
	 */
	private void useCards() {
		// If the player has a bomb card, use that card
		if (d_player.getPlayerCards().contains(CardType.BOMB)) {
			// Select a random enemy neighbor to bomb
			List<Country> l_neighbors = GamePlayHelper.GetForeignNeighbors(d_player);
			Country l_randomEnemyNeighbor = l_neighbors.get(new Random().nextInt(l_neighbors.size()));

			// Create a bomb command
			if (l_randomEnemyNeighbor != null) {
				String[] d_bombOrder = {Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB,
						Integer.toString(l_randomEnemyNeighbor.getCountryId())};
				Logger.log(MessageFormat.format(Constants.STRATEGY_ISSUE_ORDER,
						String.join(Constants.SPACE, d_bombOrder)));
				Bomb.ValidateIssueBombOrder(d_player, d_bombOrder);
			}
		}

		// If the player has an airlift card, use that card
		if (d_player.getPlayerCards().contains(CardType.AIRLIFT)) {
			// Select two random countries owned by the player
			Random random = new Random();
			List<Country> l_countriesOwned = d_player.getCountriesOwned();
			Country l_country1 = l_countriesOwned.get(random.nextInt(l_countriesOwned.size()));
			Country l_country2 = l_countriesOwned.get(random.nextInt(l_countriesOwned.size()));

			// Create an airlift command
			if (l_country1 != null && l_country2 != null && l_country1 != l_country2) {
				String[] d_airliftOrder = {Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT,
						Integer.toString(l_country1.getCountryId()), Integer.toString(l_country2.getCountryId()),
						Integer.toString(random.nextInt(l_country1.getArmyCount()) + 1)};
				Logger.log(MessageFormat.format(Constants.STRATEGY_ISSUE_ORDER,
						String.join(Constants.SPACE, d_airliftOrder)));
				Airlift.ValidateIssueAirliftOrder(d_player, d_airliftOrder);
			}
		}
	}

	/**
	 * The function "advanceOnRandomCountry" issues an advance order to a random
	 * neighboring country. This can be used to attack or to fortify.
	 */
	private void advanceOnRandomCountry() {
		// For each country owned by the player
		for (Country l_country : d_player.getCountriesOwned()) {
			if (l_country.getArmyCount() > 0) {
				// Get a random neighbor of the country
				Random l_random = new Random();
				int l_neighborId = l_random.nextInt(l_country.getNeighbors().size());
				Country l_randomNeighbor = l_country.getNeighbors().values().stream().collect(Collectors.toList())
						.get(l_neighborId);

				// Issue an advance order to the random neighbor
				String[] l_advanceOrder = {Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE, l_country.getCountryName(),
						l_randomNeighbor.getCountryName(),
						Integer.toString(l_random.nextInt(l_country.getArmyCount()) + 1)};
				Logger.log(MessageFormat.format(Constants.STRATEGY_ISSUE_ORDER,
						String.join(Constants.SPACE, l_advanceOrder)));
				Advance.ValidateIssueAdvanceOrder(d_player, l_advanceOrder);
			}
		}
	}

	/**
	 * The function returns the name of a random player strategy.
	 *
	 * @return The method is returning the constant value
	 *         "USER_INPUT_COMMAND_PLAYER_STRATEGY_RANDOM".
	 */
	@Override
	public String getStrategyName() {
		return Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_RANDOM;
	}
}
