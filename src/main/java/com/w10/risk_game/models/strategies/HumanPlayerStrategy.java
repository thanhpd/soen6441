package com.w10.risk_game.models.strategies;

import java.text.MessageFormat;

import com.w10.risk_game.commands.Advance;
import com.w10.risk_game.commands.Airlift;
import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.commands.Order;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

public class HumanPlayerStrategy extends PlayerStrategy {

	public HumanPlayerStrategy(Player p_player) {
		super(p_player);
	}

	/**
     * The function issueDeployOrder takes an input array, extracts the country ID and number of armies,
     * validates the order, creates a Deploy order and adds it to the list of orders, deploys the
     * specified number of armies to the country, and logs the success or failure of the order.
     * 
     * @param p_inputArray An array of strings containing the input values for the deploy order. The first
     * element is not used in this method. The second element (index 1) is the country ID, and the third
     * element (index 2) is the number of armies to deploy.
     * @return The method is returning a boolean value. If the deploy order is valid and successfully
     * executed, it returns true. If the deploy order is incorrect or invalid, it returns false.
     */
    public boolean issueDeployOrder(String[] p_inputArray) {
		// Extract country ID and number of armies from the input array
		String l_countryId = p_inputArray[1];
		String l_num = p_inputArray[2];

		// Validate the order
		if (Deploy.ValidateOrder(d_Player, l_countryId, l_num)) {
			// If the order is valid, create a Deploy order and add it to the list of orders
			Order order = new Deploy(d_Player, Integer.parseInt(p_inputArray[1]), Integer.parseInt(p_inputArray[2]));
			d_Player.getOrders().add(order);

			// Deploy the specified number of armies to the country
			d_Player.deployArmies(Integer.parseInt(p_inputArray[2]));

			// Log that the deploy order was successful
			Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
			return true; // Return true indicating the successful execution of the order
		} else {
			// Log if the deploy order was incorrect or invalid
			Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
					Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY));
			return false; // Return false for an unsuccessful order execution
		}
	}

	/**
	 * The function try to add advance order to the player's order list
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public boolean issueAdvanceOrder(String[] p_inputArray) {
		if (Advance.CheckValidAdvanceInput(p_inputArray)) {
			// Step 1: Get the variables needed to create an advance order
			String l_countryNameFrom = p_inputArray[1];
			String l_countryNameTo = p_inputArray[2];
			Country l_countryFrom = d_Player.getCountriesOwned().stream()
					.filter(l_c -> l_c.getCountryName().equals(l_countryNameFrom)).findAny().orElse(null);
			Country l_countryTo = l_countryFrom != null
					? l_countryFrom.getNeighbors().values().stream()
							.filter(c -> c.getCountryName().equals(l_countryNameTo)).findAny().orElse(null)
					: null;
			int d_advanceArmies = Integer.parseInt(p_inputArray[3]);
			// Step 2: Check whether the order is valid
			if (l_countryFrom != null && l_countryTo != null && d_advanceArmies > 0 && checkValidAdvanceOrder(
					d_advanceArmies, l_countryFrom.getArmyCount(), l_countryFrom.getCountryId())) {
				Order l_order = new Advance(l_countryFrom, l_countryTo, d_advanceArmies);
				d_Player.getOrders().add(l_order);
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true;
			} else {
				if (l_countryFrom == null || l_countryTo == null) {
					if (l_countryFrom == null)
						Logger.log(MessageFormat.format(Constants.ADVANCE_INVALID_COUNTRY_NOT_OWNED, l_countryNameFrom,
								d_Player.getName()));
					if (l_countryTo == null)
						Logger.log(MessageFormat.format(Constants.ADVANCE_INVALID_COUNTRY_NOT_NEIGHBOR, l_countryNameTo,
								l_countryNameFrom));
				} else if (d_advanceArmies <= 0)
					Logger.log(Constants.ADVANCE_INVALID_ARMY_LESS);
				else
					Logger.log(Constants.ADVANCE_INVALID_ARMY_MORE);
				return false;
			}
		}
		return false;

	}

	/**
	 * The function checks whether a player can advance X armies from a country Y
	 * onto a country Z or not
	 *
	 * @param p_noOfArmiesToAdvance
	 *            number of armies to advance
	 * @param p_currentArmiesOnCountry
	 *            current armies on country
	 * @param p_advanceFromCountryId
	 *            country id to advance from
	 * @return boolean value to show whether the player can advance
	 */
	private boolean checkValidAdvanceOrder(int p_noOfArmiesToAdvance, int p_currentArmiesOnCountry,
			int p_advanceFromCountryId) {
		int l_totalArmiesDeployed = p_currentArmiesOnCountry;
		// Iterate through existing orders to check total armies deployed or moved
		for (Order l_order : d_Player.getOrders()) {
			if ((l_order instanceof Deploy) && ((Deploy) l_order).getCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed += ((Deploy) l_order).getNum();
			} else if ((l_order instanceof Airlift)
					&& ((Airlift) l_order).getTargetCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed += ((Airlift) l_order).getArmyToAirlift();
			} else if ((l_order instanceof Airlift)
					&& ((Airlift) l_order).getSourceCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed -= ((Airlift) l_order).getArmyToAirlift();
			} else if ((l_order instanceof Advance)
					&& ((Advance) l_order).getCountryNameTo().getCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed += ((Advance) l_order).getNumOfArmies();
			} else if ((l_order instanceof Advance)
					&& ((Advance) l_order).getCountryNameFrom().getCountryId() == p_advanceFromCountryId) {
				l_totalArmiesDeployed -= ((Advance) l_order).getNumOfArmies();
			}
		}
		// Return true if the total armies deployed or moved is greater than or equal to
		// the requested number of armies to advance
		return l_totalArmiesDeployed >= p_noOfArmiesToAdvance;
	}
}
