package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.text.MessageFormat;

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

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

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
		// Check if the advancing player is the owner of the country from which armies
		// are being moved
		if (!this.d_advancingPlayerName.equals(this.d_countryFrom.getOwner().getName())) {
			Logger.log(MessageFormat.format(Constants.ADVANCE_NOT_OWNER, this.d_advancingPlayerName,
					this.d_countryFrom.getCountryName()));
			return;
		}
		// Check if the number of armies specified in the advance order is greater than
		// the number of armies currently present on the country from which the armies
		// are being moved.
		if (this.d_numOfArmies > this.d_countryFrom.getArmyCount()) {
			if (this.d_countryFrom.getArmyCount() == 0) {
				Logger.log(MessageFormat.format(Constants.ADVANCE_NO_ARMIES, this.d_advancingPlayerName,
						this.d_countryFrom.getCountryName()));
				return;
			}
			Logger.log(MessageFormat.format(Constants.ADVANCE_FEWER_ARMIES_AFTER_ATTACK, this.d_advancingPlayerName,
					this.d_numOfArmies, this.d_countryFrom.getCountryName(), this.d_countryTo.getCountryName()));
			this.d_numOfArmies = this.d_countryFrom.getArmyCount();
		}
		// Check if the target country is owned by the same player as the source country
		if (this.d_countryTo.getOwner() != null
				&& this.d_countryFrom.getOwner().getName().equals(this.d_countryTo.getOwner().getName())) {
			// This code block is simulating a movement of armies to player's own country
			this.d_countryFrom.setArmyCount(this.d_countryFrom.getArmyCount() - this.d_numOfArmies);
			this.d_countryTo.setArmyCount(this.d_numOfArmies + this.d_countryTo.getArmyCount());
			Logger.log(MessageFormat.format(Constants.ADVANCE_DEPLOY_SUCCEED, this.d_countryFrom.getOwner().getName(),
					this.d_numOfArmies, this.d_countryFrom.getCountryName(), this.d_countryTo.getCountryName()));
		} else {
			// Simulate a battle between two countries in the game
			this.d_countryFrom.setArmyCount(this.d_countryFrom.getArmyCount() - this.d_numOfArmies);
			Logger.log(MessageFormat.format(Constants.ADVANCE_BATTLE_START, this.d_countryFrom.getOwner().getName(),
					this.d_countryTo.getCountryName(), this.d_countryTo.getOwner().getName()));
			// Determine the number of attacking and defending armies for the battle
			int l_noOfAttackingArmies = this.d_numOfArmies;
			int l_noOfDefendingArmies = this.d_countryTo.getArmyCount();
			int l_leftoverAttackingArmies = (int) Math
					.max(Math.floor(l_noOfAttackingArmies - 0.7 * l_noOfDefendingArmies), 0);
			int l_leftoverDefendingArmies = (int) Math
					.max(Math.floor(l_noOfDefendingArmies - 0.6 * l_noOfAttackingArmies), 0);
			if (l_leftoverDefendingArmies == 0) {// attacker wins
				Logger.log(MessageFormat.format(Constants.ADVANCE_BATTLE_WON, this.d_countryFrom.getOwner().getName(),
						this.d_countryTo.getCountryName()));
				// Update army counts and ownership after the attacker wins
				this.d_countryTo.setArmyCount(l_leftoverAttackingArmies);
				this.d_countryTo.getOwner().removeCountry(d_countryTo);
				this.d_countryTo.setOwner(this.d_countryFrom.getOwner());
				this.d_countryFrom.getOwner().addCountry(d_countryTo);
				this.d_countryFrom.getOwner().setHasConqueredNewCountry(true);
			} else { // attacker loses
				Logger.log(MessageFormat.format(Constants.ADVANCE_BATTLE_LOST, this.d_countryFrom.getOwner().getName(),
						this.d_countryTo.getCountryName(), this.d_countryTo.getOwner().getName()));
				// Update the defender's army count after the battle
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
			Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "advance",
					"four"));
			return false;
		}
		// Step 2: Check whether the armies is positive integer
		String l_num = p_inputArray[3];
		for (int i = 0; i < l_num.length(); i++) {
			if (!Character.isDigit(l_num.charAt(i))) {
				Logger.log(Constants.PLAYER_ISSUE_ORDER_ARMIES_NOT_INTEGER);
				return false;
			}
		}
		return true;
	}

	/**
	 * The function try to add advance order to the player's order list
	 *
	 * @param p_player
	 *            the player who issue the order
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public static boolean ValidateIssueAdvanceOrder(Player p_player, String[] p_inputArray) {
		if (Advance.CheckValidAdvanceInput(p_inputArray)) {
			// Step 1: Get the variables needed to create an advance order
			String l_countryNameFrom = p_inputArray[1];
			String l_countryNameTo = p_inputArray[2];
			Country l_countryFrom = p_player.getCountriesOwned().stream()
					.filter(l_c -> l_c.getCountryName().equals(l_countryNameFrom)).findAny().orElse(null);
			Country l_countryTo = l_countryFrom != null
					? l_countryFrom.getNeighbors().values().stream()
							.filter(c -> c.getCountryName().equals(l_countryNameTo)).findAny().orElse(null)
					: null;
			int d_advanceArmies = Integer.parseInt(p_inputArray[3]);
			// Step 2: Check whether the order is valid
			if (l_countryFrom != null && l_countryTo != null && d_advanceArmies > 0 && CheckValidAdvanceOrder(p_player,
					d_advanceArmies, l_countryFrom.getArmyCount(), l_countryFrom.getCountryId())) {
				Order l_order = new Advance(l_countryFrom, l_countryTo, d_advanceArmies);
				p_player.addOrder(l_order);
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true;
			} else {
				if (l_countryFrom == null || l_countryTo == null) {
					if (l_countryFrom == null)
						Logger.log(MessageFormat.format(Constants.ADVANCE_INVALID_COUNTRY_NOT_OWNED, l_countryNameFrom,
								p_player.getName()));
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
	 * @param p_player
	 *            the player who issue the order
	 * @param p_noOfArmiesToAdvance
	 *            number of armies to advance
	 * @param p_currentArmiesOnCountry
	 *            current armies on country
	 * @param p_advanceFromCountryId
	 *            country id to advance from
	 * @return boolean value to show whether the player can advance
	 */
	private static boolean CheckValidAdvanceOrder(Player p_player, int p_noOfArmiesToAdvance,
			int p_currentArmiesOnCountry, int p_advanceFromCountryId) {
		int l_totalArmiesDeployed = p_currentArmiesOnCountry;
		// Iterate through existing orders to check total armies deployed or moved
		for (Order l_order : p_player.getOrders()) {
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
