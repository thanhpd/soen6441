package com.w10.risk_game.commands;

import com.w10.risk_game.models.CardType;
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
			this.d_countryNameFrom.setArmyCount(this.d_countryNameFrom.getArmyCount() - this.d_numOfArmies);
			this.d_countryNameTo.setArmyCount(this.d_numOfArmies + this.d_countryNameTo.getArmyCount());
		} else {
			this.d_countryNameFrom.setArmyCount(this.d_countryNameFrom.getArmyCount() - this.d_numOfArmies);
			// attack
			int l_noOfAttackingArmies = this.d_numOfArmies;
			int l_noOfDefendingArmies = this.d_countryNameTo.getArmyCount();
			int l_leftoverAttackingArmies = (int) Math
					.max(Math.floor(l_noOfAttackingArmies - 0.7 * l_noOfDefendingArmies), 0);
			int l_leftoverDefendingArmies = (int) Math
					.max(Math.floor(l_noOfDefendingArmies - 0.6 * l_noOfAttackingArmies), 0);
			if (l_leftoverDefendingArmies == 0) {
				this.d_countryNameTo.setArmyCount(l_leftoverAttackingArmies);
				this.d_countryNameTo.setOwner(this.d_countryNameFrom.getOwner());
				this.d_countryNameFrom.getOwner().addCard(CardType.getRandomCard());
			} else {
				this.d_countryNameTo.setArmyCount(l_leftoverDefendingArmies);
			}
		}
	}

}
