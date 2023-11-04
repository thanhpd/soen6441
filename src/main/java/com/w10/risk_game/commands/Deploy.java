package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.util.List;

/**
 * This class is the deploy order class. It extends the Order class. It defines
 * the specific executing behavior for deploying.
 *
 * @see Order
 * @author Yajing Liu
 */
public class Deploy extends Order {
	private Player d_player;
	private int d_countryId;
	private int d_num;
	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	/**
	 * This is a constructor of the Deploy class
	 *
	 * @param p_player
	 *            The player who issues the order
	 * @param p_countryId
	 *            The country id that the order is issued to
	 * @param p_num
	 *            The number of armies that the order is issued to
	 */
	public Deploy(Player p_player, int p_countryId, int p_num) {
		this.d_player = p_player;
		this.d_countryId = p_countryId;
		this.d_num = p_num;
	}
	/**
	 * This method extends the execute method in the Order class. It gets the number
	 * of armies and deploys them to the country
	 */
	public void execute() {
		List<Country> l_countries = d_player.getCountriesOwned();
		for (Country l_country : l_countries) {
			if (l_country.getCountryId() == d_countryId) {
				int l_countryArmies = l_country.getArmyCount();
				l_country.setArmyCount(l_countryArmies + d_num);
			}
		}
	}

	/**
	 * This method is used to validate the order. It checks whether the country id
	 * and the number of armies are valid
	 * 
	 * @param p_player
	 *            The player who issues the order
	 * @param p_countryId
	 *            The country id that the order is issued to
	 * @param p_num
	 *            The number of armies that the order is issued to
	 * @return boolean value to show whether the order is valid
	 */
	public static boolean validateOrder(Player p_player, String p_countryId, String p_num) {
		List<Country> l_countries = p_player.getCountriesOwned();
		boolean l_validCountry = checkValidCountry(l_countries, p_countryId);
		int l_num = Integer.parseInt(p_num);
		boolean l_validNum = checkValidArmy(p_player, l_num);
		if (l_validCountry && l_validNum) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function is used to check the country id for deploy command. The country
	 * id should be one of the countries owned by the player
	 *
	 * @param p_countries
	 *            the list of countries owned by the player
	 * @param p_countryId
	 *            the country id
	 * @return boolean value to show whether the country id is valid
	 */
	public static boolean checkValidCountry(List<Country> p_countries, String p_countryId) {
		for (Country country : p_countries) {
			if (country.getCountryId() == Integer.parseInt(p_countryId)) {
				return true;
			}
		}
		d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INVALID_COUNTRY);
		return false;
	}

	/**
	 * This function is used to check the number of armies for deploy command. The
	 * number of armies should be less than the number of leftover armies
	 *
	 * @param p_num
	 *            the number of armies
	 * @return boolean value to show whether the number of armies is valid
	 */
	public static boolean checkValidArmy(Player p_player, int p_num) {
		if (p_num <= 0) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INVALID_ARMIES_ZERO);
			return false;
		}
		if (p_num > p_player.getLeftoverArmies()) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INVALID_ARMIES);
			return false;
		}
		return true;
	}
}
