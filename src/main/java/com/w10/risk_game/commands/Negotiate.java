package com.w10.risk_game.commands;

import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.models.CardType;
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
	private static Player PlayerToNegotiate;
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

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
		// Validate the negotiation order between the current player and the player to
		// negotiate with
		if (ValidateOrder(d_currentPlayer, d_playerName)) {
			// Log a message indicating the use of the negotiate card
			Logger.log(MessageFormat.format(Constants.NEGOTIATE_CARD_USED, d_currentPlayer.getName(),
					PlayerToNegotiate.getName()));

			// Retrieve other orders from the game
			List<Order> l_otherOrders = GamePlayController.GetOtherOrders();
			List<Order> l_otherOrdersAfterNegotiate = new ArrayList<>();
			boolean l_isNegotiate = false;

			// Iterate through other orders to handle negotiation effects
			for (Order l_order : l_otherOrders) {
				// Check if negotiation prevents the attack initiated by the current player
				if ((l_order instanceof Advance) && (((Advance) l_order).getCountryNameFrom().getOwner().getName()
						.equals(d_currentPlayer.getName())
						&& ((Advance) l_order).getCountryNameTo().getOwner().getName()
								.equals(PlayerToNegotiate.getName()))) {
					// Log a message indicating prevention of the attack initiated by the current
					// player
					Logger.log(MessageFormat.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_currentPlayer.getName(),
							PlayerToNegotiate.getName()));
					l_isNegotiate = true;
					continue;
				}

				// Check if negotiation prevents the attack initiated by the negotiating player
				if ((l_order instanceof Advance) && (((Advance) l_order).getCountryNameFrom().getOwner().getName()
						.equals(PlayerToNegotiate.getName())
						&& ((Advance) l_order).getCountryNameTo().getOwner().getName()
								.equals(d_currentPlayer.getName()))) {
					// Log a message indicating prevention of the attack initiated by the
					// negotiating player
					Logger.log(MessageFormat.format(Constants.NEGOTIATE_ATTACK_PREVENT, PlayerToNegotiate.getName(),
							d_currentPlayer.getName()));
					l_isNegotiate = true;
					continue;
				}

				// Add orders unaffected by the negotiation to the new list of orders
				l_otherOrdersAfterNegotiate.add(l_order);
			}

			// Update the list of orders after handling negotiation effects
			GamePlayController.SetOtherOrders(l_otherOrdersAfterNegotiate);

			// If negotiation had no effect on preventing attacks, log a message
			if (!l_isNegotiate) {
				Logger.log(MessageFormat.format(Constants.NEGOTIATE_NO_EFFECT, d_currentPlayer.getName(),
						PlayerToNegotiate.getName()));
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
		// Check if the current player and the specified player to negotiate are the
		// same
		if (p_currentPlayer.getName().equals(p_playerId)) {
			// Log a message indicating self-negotiation
			Logger.log(Constants.NEGOTIATE_SELF);
			return false; // Indicates that self-negotiation is not allowed
		}

		// Get the list of players available for diplomacy from the game
		List<Player> l_players = GamePlayController.GetPlayerListForDiplomacy();

		// Iterate through the players to find the player to negotiate with
		for (Player l_player : l_players) {
			// Check if the specified player to negotiate with is valid
			if (l_player.getName().equals(p_playerId)) {
				// Set the player to negotiate with
				PlayerToNegotiate = l_player;
				return true; // Indicates that the player to negotiate with is valid
			}
		}

		// Log a message if the specified player to negotiate with is not found
		Logger.log(MessageFormat.format(Constants.NEGOTIATE_NO_PLAYER, p_playerId));
		return false; // Indicates that the specified player to negotiate with is invalid
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
			Logger.log(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS);
			return false;
		}
		return true;
	}

	/**
	 * The function try to add diplomacy order to the player's order list
	 *
	 * @param p_player
	 *            the player who issue the order
	 * @param p_inputArray
	 *            the input string split by space
	 * @return boolean value to show whether the order is added successfully
	 */
	public static boolean ValidateIssueDiplomacyOrder(Player p_player, String[] p_inputArray) {
		String l_playerId = p_inputArray[1];
		String l_playerName = l_playerId; // Obtaining the player's ID

		// Check if the player has a diplomacy card
		if (p_player.hasCard(CardType.DIPLOMACY)) {
			// Validate the negotiation order
			if (ValidateOrder(p_player, l_playerName)) {
				// If the negotiation order is valid, create a Negotiate order and add it to the
				// list of orders
				Order order = new Negotiate(p_player, l_playerId);
				p_player.addOrder(order);

				// Remove the diplomacy card from the player's hand
				p_player.removeCard(CardType.DIPLOMACY);

				// Log a success message for the diplomacy order execution
				Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
				return true; // Return true for a successful order execution
			} else {
				// Log if the diplomacy order was incorrect or invalid
				Logger.log(MessageFormat.format(Constants.PLAYER_ISSUE_ORDER_INCORRECT,
						Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE));
				return false; // Return false for an unsuccessful order execution
			}
		} else {
			return false; // Return false if the player does not have a diplomacy card
		}
	}

}
