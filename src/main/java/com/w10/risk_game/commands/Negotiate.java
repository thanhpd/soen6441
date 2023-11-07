package com.w10.risk_game.commands;

import com.w10.risk_game.controllers.GameEngineController;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Formatter;
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
		if (ValidateOrder(d_currentPlayer, d_playerName)) {
			d_logger.log(MessageFormat.format(Constants.NEGOTIATE_CARD_USED, d_currentPlayer.getName(),
					d_playerToNegotiate.getName()));
			List<Order> l_otherOrders = GameEngineController.getOtherOrders();
			List<Order> l_otherOrdersAfterNegotiate = new ArrayList<>();
			boolean l_isNegotiate = false;
			for (Order l_order : l_otherOrders) {
				// negotiation prevents the attack initiated by current player
				if ((l_order instanceof Advance) && (((Advance) l_order).getCountryNameFrom().getOwner().getName()
						.equals(d_currentPlayer.getName())
						&& ((Advance) l_order).getCountryNameTo().getOwner().getName()
								.equals(d_playerToNegotiate.getName()))) {
					d_logger.log(MessageFormat.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_currentPlayer.getName(),
							d_playerToNegotiate.getName()));
					l_isNegotiate = true;
					continue;
				}
				// negotiation prevents the attack initiated by negotiating player
				if ((l_order instanceof Advance) && (((Advance) l_order).getCountryNameFrom().getOwner().getName()
						.equals(d_playerToNegotiate.getName())
						&& ((Advance) l_order).getCountryNameTo().getOwner().getName()
								.equals(d_currentPlayer.getName()))) {
					d_logger.log(MessageFormat.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_playerToNegotiate.getName(),
							d_currentPlayer.getName()));
					l_isNegotiate = true;
					continue;
				}
				l_otherOrdersAfterNegotiate.add(l_order);
			}
			GameEngineController.setOtherOrders(l_otherOrdersAfterNegotiate);
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
		if (p_currentPlayer.getName().equals(p_playerId)) {
			d_logger.log(Constants.NEGOTIATE_SELF);
			return false;
		}
		List<Player> l_players = GameEngineController.getPlayerListForDiplomacy();
		for (Player l_player : l_players) {
			if (l_player.getName().equals(p_playerId)) {
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
