package com.w10.risk_game.commands;

import com.w10.risk_game.models.CardType;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.text.MessageFormat;
import java.util.Formatter;

/**
 * This class is the Advance order class. It extends the Order class. It defines
 * the specific executing behavior for advancing.
 *
 * @see Order
 * @author Darlene-Naz
 */
public class Advance extends Order {
	private Country d_countryFrom;
	private Country d_countryTo;
	private int d_numOfArmies;
	private String d_advancingPlayerName;

	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	/**
	 * This is a constructor of the Advance class
	 *
	 * @param p_countryFrom
	 *            The country from where the armies are to move
	 * @param p_countryTo
	 *            The country to where the armies are to move
	 * @param p_numOfArmies
	 *            The number of armies that the order is issued to
	 */
	public Advance(Country p_countryFrom, Country p_countryTo, int p_numOfArmies) {
		this.d_countryFrom = p_countryFrom;
		this.d_countryTo = p_countryTo;
		this.d_numOfArmies = p_numOfArmies;
		this.d_advancingPlayerName = p_countryFrom.getOwner().getName();
	}

	/**
	 * This function is used to get country name from
	 *
	 * @return country name from
	 */
	public Country getCountryNameFrom() {
		return d_countryFrom;
	}

	/**
	 * This function is used to get country name to
	 *
	 * @return country name to
	 */
	public Country getCountryNameTo() {
		return d_countryTo;
	}

	/**
	 * The function returns the number of armies.
	 *
	 * @return The method is returning the value of the variable "d_numOfArmies".
	 */
	public int getNumOfArmies() {
		return d_numOfArmies;
	}

	/**
	 * This method extends the execute method in the Order class. It gets the number
	 * of armies and advances them to the country
	 */
	public void execute() {
		if (!this.d_advancingPlayerName.equals(this.d_countryFrom.getOwner().getName())) {
			d_logger.log(MessageFormat.format(Constants.ADVANCE_NOT_OWNER, this.d_advancingPlayerName,
					this.d_countryFrom.getCountryName()));
			return;
		}
		if (this.d_countryTo.getOwner() != null
				&& this.d_countryFrom.getOwner().getName().equals(this.d_countryTo.getOwner().getName())) {
			// This code block is simulating a movement of armies to player's own country
			this.d_countryFrom.setArmyCount(this.d_countryFrom.getArmyCount() - this.d_numOfArmies);
			this.d_countryTo.setArmyCount(this.d_numOfArmies + this.d_countryTo.getArmyCount());
			d_logger.log(MessageFormat.format(Constants.ADVANCE_DEPLOY_SUCCEED, this.d_countryFrom.getOwner().getName(),
					this.d_numOfArmies, this.d_countryTo.getCountryName()));
		} else {
			this.d_countryFrom.setArmyCount(this.d_countryFrom.getArmyCount() - this.d_numOfArmies);
			// This code block is simulating a battle between two countries in the game.
			d_logger.log(MessageFormat.format(Constants.ADVANCE_BATTLE_START, this.d_countryFrom.getOwner().getName(),
					this.d_countryTo.getCountryName(), this.d_countryTo.getOwner().getName()));
			int l_noOfAttackingArmies = this.d_numOfArmies;
			int l_noOfDefendingArmies = this.d_countryTo.getArmyCount();
			int l_leftoverAttackingArmies = (int) Math
					.max(Math.floor(l_noOfAttackingArmies - 0.7 * l_noOfDefendingArmies), 0);
			int l_leftoverDefendingArmies = (int) Math
					.max(Math.floor(l_noOfDefendingArmies - 0.6 * l_noOfAttackingArmies), 0);
			if (l_leftoverDefendingArmies == 0) {// attacker wins
				d_logger.log(MessageFormat.format(Constants.ADVANCE_BATTLE_WON, this.d_countryFrom.getOwner().getName(),
						this.d_countryTo.getCountryName()));
				this.d_countryTo.setArmyCount(l_leftoverAttackingArmies);
				this.d_countryTo.getOwner().removeCountry(d_countryTo);
				this.d_countryTo.setOwner(this.d_countryFrom.getOwner());
				this.d_countryFrom.getOwner().addCountry(d_countryTo);
				this.d_countryFrom.getOwner().addCard(CardType.getRandomCard());
			} else { // attacker loses
				d_logger.log(
						MessageFormat.format(Constants.ADVANCE_BATTLE_LOST, this.d_countryFrom.getOwner().getName(),
								this.d_countryTo.getCountryName(), this.d_countryTo.getOwner().getName()));
				this.d_countryTo.setArmyCount(l_leftoverDefendingArmies);
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
			d_logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "advance",
					"four"));
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
