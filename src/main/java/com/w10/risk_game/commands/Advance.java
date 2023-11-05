package com.w10.risk_game.commands;

import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.util.Formatter;
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
	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

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
	 * This function is used to get country name from
	 *
	 * @return country name from
	 */
	public Country getCountryNameFrom() {
		return d_countryNameFrom;
	}

	/**
	 * This function is used to get country name to
	 *
	 * @return country name to
	 */
	public Country getCountryNameTo() {
		return d_countryNameTo;
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
	/**
	 * This function is used to check the input format for advance command.
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the input format is valid
	 */
	public static boolean CheckValidAdvanceInput(String[] p_inputArray) {
		// Step 1: Check the length of the input
		if (p_inputArray.length != 4) {
			Formatter l_formatter = new Formatter();
			l_formatter.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "advance", "four");
			d_logger.log(l_formatter.toString());
			l_formatter.close();
			return false;
		}
		// Step 2: Check whether the armies is positive integer
		String l_num = p_inputArray[3];
		for (int i = 0; i < l_num.length(); i++) {
			if (!Character.isDigit(l_num.charAt(i))) {
				d_logger.log(Constants.PLAYER_ISSUE_ORDER_ARMIES_NOT_INTEGER);
				return false;
			}
		}
		return true;
	}
}
