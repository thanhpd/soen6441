package com.w10.risk_game.commands;

import com.w10.risk_game.controllers.GameEngineController;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is to negotiate with other players. In this turn, the two players
 * can not attack each other.
 *
 * @see Order
 * @author Yajing Liu
 */
public class Negotiate extends Order {
	private Player d_currentPlayer;
	private String d_playerName;
	private static Player d_playerToNegotiate;
	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	/**
	 * This is a constructor of the Negotiate class
	 *
	 * @param p_player
	 *            The player who issues the order
	 * @param p_playerName
	 *            The player name of the player to negotiate with
	 */
	public Negotiate(Player p_player, String p_playerName) {
		this.d_currentPlayer = p_player;
		this.d_playerName = p_playerName;
	}

	/**
	 * This method extends the execute method in the Order class. It removes all the
	 * advance orders between these two players.
	 */
	@Override
	public void execute() {
		// Validate if the negotiation order is allowed between the current player and
		// the player to negotiate with

		if (ValidateOrder(d_currentPlayer, d_playerName)) {
			d_logger.log(MessageFormat.format(Constants.NEGOTIATE_CARD_USED, d_currentPlayer.getName(),
					d_playerToNegotiate.getName()));
			// Retrieve other orders in the game
			List<Order> l_otherOrders = GameEngineController.getOtherOrders();
			List<Order> l_otherOrdersAfterNegotiate = new ArrayList<>();
			boolean l_isNegotiate = false;
			// Iterate through other orders to check for potential attacks
			for (Order l_order : l_otherOrders) {
				if ((l_order instanceof Advance)
						&& (((Advance) l_order).getCountryNameFrom().getOwner().getName() == d_currentPlayer.getName()
								&& ((Advance) l_order).getCountryNameTo().getOwner().getName() == d_playerToNegotiate
										.getName())) {
					// Log prevention of attack due to negotiation
					d_logger.log(MessageFormat.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_currentPlayer.getName(),
							d_playerToNegotiate.getName()));
					l_isNegotiate = true;
					continue;
				}
				if ((l_order instanceof Advance) && (((Advance) l_order).getCountryNameFrom().getOwner()
						.getName() == d_playerToNegotiate.getName()
						&& ((Advance) l_order).getCountryNameTo().getOwner().getName() == d_currentPlayer.getName())) {
					// Log prevention of attack due to negotiation
					d_logger.log(MessageFormat.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_playerToNegotiate.getName(),
							d_currentPlayer.getName()));
					l_isNegotiate = true;
					continue;
				}
				// Add orders after the negotiation to the list
				l_otherOrdersAfterNegotiate.add(l_order);
			}
			// Update the orders without potential negotiations
			GameEngineController.setOtherOrders(l_otherOrdersAfterNegotiate);
			// If no negotiations impacted the other orders, log that the negotiation had no
			// effect
			if (!l_isNegotiate) {
				d_logger.log(MessageFormat.format(Constants.NEGOTIATE_NO_EFFECT, d_currentPlayer.getName(),
						d_playerToNegotiate.getName()));
			}
		}
	}

	/**
	 * This method is to validate the order. It checks if the player to negotiate
	 * with exists.
	 *
	 * @param p_currentPlayer
	 *            The player who issues the order
	 * @param p_playerId
	 *            The player id of the player to negotiate with
	 * @return the boolean value to indicate if the order is valid
	 */
	public static boolean ValidateOrder(Player p_currentPlayer, String p_playerId) {
		// Check if the current player and the player ID for negotiation are the same

		if (p_currentPlayer.getName().equals(p_playerId)) {
			d_logger.log(Constants.NEGOTIATE_SELF);
			return false;
		}
		// Retrieve the list of players available for diplomacy from the game controller
		List<Player> l_players = GameEngineController.getPlayerListForDiplomacy();
		// Iterate through the players to find the player to negotiate with
		for (Player l_player : l_players) {
			if (l_player.getName().equals(p_playerId)) {
				// Set the player to negotiate with and return true if found
				d_playerToNegotiate = l_player;
				return true;
			}
		}
		d_logger.log(MessageFormat.format(Constants.NEGOTIATE_NO_PLAYER, p_playerId));
		return false;
	}

	/**
	 * This function is used to check the input format for negotiate command.
	 *
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the input format is valid
	 */
	public static boolean CheckValidNegotiateInput(String[] p_inputArray) {
		if (p_inputArray.length != 2) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS);
			return false;
		}
		return true;
	}
}
