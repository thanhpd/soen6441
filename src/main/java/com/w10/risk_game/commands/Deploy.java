package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

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

	public int getCountryId() {
		return d_countryId;
	}

	public int getNum() {
		return d_num;
	}
}
