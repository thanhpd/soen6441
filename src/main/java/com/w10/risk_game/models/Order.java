package com.w10.risk_game.models;

import java.util.List;

/**
 * This class is used to create order objects d_player: the player who issues
 * the order d_orderType: the type of the order d_countryId: the country id that
 * the order is issued to d_num: the number of armies that the order is issued
 * to
 */
public class Order {
	private Player d_player;
	private String d_orderType;
	private int d_countryId;
	private int d_num;

	// Getter
	public Player getPlayer() {
		return d_player;
	}

	public String getOrderType() {
		return d_orderType;
	}

	public int getCountryId() {
		return d_countryId;
	}

	public int getNum() {
		return d_num;
	}

	// Setter
	public void setPlayer(Player d_player) {
		this.d_player = d_player;
	}

	public void setOrderType(String d_orderType) {
		this.d_orderType = d_orderType;
	}

	public void setCountryId(int d_countryId) {
		this.d_countryId = d_countryId;
	}

	public void setNum(int d_num) {
		this.d_num = d_num;
	}

	public Order(Player p_player, String p_orderType, int p_countryId, int p_num) {
		this.d_player = p_player;
		this.d_orderType = p_orderType;
		this.d_countryId = p_countryId;
		this.d_num = p_num;
	}

	/**
	 * This method is used to deploy the armies This method gets the number of
	 * armies to deploy from the player and deploys them to the country
	 */
	public void execute() {
		Player l_player = this.d_player;
		int l_countryId = this.d_countryId;
		int l_num = this.d_num;
		List<Country> l_countries = l_player.getCountriesOwned();
		for (Country l_country : l_countries) {
			if (l_country.getCountryId() == l_countryId) {
				int l_countryArmies = l_country.getArmyCount();
				l_country.setArmyCount(l_countryArmies + l_num);
			}
		}
	}
}
