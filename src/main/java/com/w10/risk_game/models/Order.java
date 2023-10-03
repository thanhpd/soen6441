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

	/**
	 * The function returns the player object.
	 *
	 * @return The method is returning the object of type Player.
	 */
	public Player getPlayer() {
		return d_player;
	}

	/**
	 * The function getOrderType() returns the order type.
	 *
	 * @return The method is returning a String value.
	 */
	public String getOrderType() {
		return d_orderType;
	}

	/**
	 * The function returns the country ID.
	 *
	 * @return The method returns an integer value, specifically the value of the
	 *         variable "d_countryId".
	 */
	public int getCountryId() {
		return d_countryId;
	}

	/**
	 * The function returns the value of the variable d_num.
	 *
	 * @return The method is returning the value of the variable "d_num".
	 */
	public int getNum() {
		return d_num;
	}

	/**
	 * The function sets the value of the "d_player" variable to the provided
	 * "Player" object.
	 *
	 * @param d_player
	 *            The parameter "d_player" is of type "Player" and is used to set
	 *            the value of the instance variable "d_player" in the current
	 *            class.
	 */
	public void setPlayer(Player d_player) {
		this.d_player = d_player;
	}

	/**
	 * The function sets the order type for a specific object.
	 *
	 * @param d_orderType
	 *            The parameter "d_orderType" is a string that represents the type
	 *            of order.
	 */
	public void setOrderType(String d_orderType) {
		this.d_orderType = d_orderType;
	}

	/**
	 * The function sets the value of the countryId variable.
	 *
	 * @param d_countryId
	 *            The parameter "d_countryId" is an integer that represents the
	 *            country ID.
	 */
	public void setCountryId(int d_countryId) {
		this.d_countryId = d_countryId;
	}

	/**
	 * The function sets the value of the variable "d_num" to the provided input.
	 *
	 * @param d_num
	 *            The parameter "d_num" is an integer that is used to set the value
	 *            of the variable "d_num" in the current object.
	 */
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
	 * This function gets the number of armies and deploys them to the country
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
