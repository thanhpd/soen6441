package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

import java.util.List;

/**
 * This class is the Advance order class. It extends the Order class. It defines
 * the specific executing behavior for advancing.
 *
 * @see Order
 * @author Darlene-Naz
 */
public class Advance extends Order {
	private Country d_countryNameFrom;
	private Country d_countryNameTo;
	private int d_numOfArmies;

	/**
	 * This is a constructor of the Advance class
	 *
	 * @param p_countryNameFrom
	 *            The country name from where the armies are to move
	 * @param p_countryNameTo
	 *            The country name to where the armies are to move
	 * @param p_numOfArmies
	 *            The number of armies that the order is issued to
	 */
	public Advance(Country p_countryNameFrom, Country p_countryNameTo, int p_numOfArmies) {
		this.d_countryNameFrom = p_countryNameFrom;
		this.d_countryNameTo = p_countryNameTo;
		this.d_numOfArmies = p_numOfArmies;
	}

	/**
	 * This method extends the execute method in the Order class. It gets the number
	 * of armies and advances them to the country
	 */
	public void execute() {
		if (this.d_countryNameFrom.getOwner().getName().equals(this.d_countryNameTo.getOwner().getName())) {
			this.d_countryNameTo.setArmyCount(this.d_numOfArmies + this.d_countryNameTo.getArmyCount());
		} else {
			// attack
		}
	}

}
