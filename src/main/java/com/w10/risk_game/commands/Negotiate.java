package com.w10.risk_game.commands;

import com.w10.risk_game.controllers.GameEngineController;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

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
			Formatter l_formatter1 = new Formatter();
			l_formatter1.format(Constants.NEGOTIATE_CARD_USED, d_currentPlayer.getName(),
					d_playerToNegotiate.getName());
			d_logger.log(l_formatter1.toString());
			l_formatter1.close();
			List<Order> l_currentPlayerOrders = d_currentPlayer.getOrders();
			List<Order> l_playerToNegotiateOrders = d_playerToNegotiate.getOrders();
			List<Order> l_currentPlayerNewOrders = new ArrayList<>();
			List<Order> l_playerToNegotiateNewOrders = new ArrayList<>();
			// Step 1: remove all the advance orders between these two players in the
			// current player's order list
			for (Order l_order : l_currentPlayerOrders) {
				if ((l_order instanceof Advance)
						&& (((Advance) l_order).getCountryNameFrom().getOwner().getName() == d_currentPlayer.getName()
								&& ((Advance) l_order).getCountryNameTo().getOwner().getName() == d_playerToNegotiate
										.getName())) {
					Formatter l_formatter2 = new Formatter();
					l_formatter2.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_currentPlayer.getName(),
							d_playerToNegotiate.getName());
					d_logger.log(l_formatter2.toString());
					l_formatter2.close();
					continue;
				}
				l_currentPlayerNewOrders.add(l_order);
			}
			d_currentPlayer.setOrders(l_currentPlayerNewOrders);
			// Step 2: remove all the advance orders between these two players in the order
			// list of the player to negotiate with
			for (Order l_order : l_playerToNegotiateOrders) {
				if ((l_order instanceof Advance) && (((Advance) l_order).getCountryNameFrom().getOwner()
						.getName() == d_playerToNegotiate.getName()
						&& ((Advance) l_order).getCountryNameTo().getOwner().getName() == d_currentPlayer.getName())) {
					Formatter l_formatter3 = new Formatter();
					l_formatter3.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_playerToNegotiate.getName(),
							d_currentPlayer.getName());
					d_logger.log(l_formatter3.toString());
					l_formatter3.close();
					continue;
				}
				l_playerToNegotiateNewOrders.add(l_order);
			}
			d_playerToNegotiate.setOrders(l_playerToNegotiateNewOrders);
			if (l_currentPlayerOrders.size() == l_currentPlayerNewOrders.size()
					&& l_playerToNegotiateOrders.size() == l_playerToNegotiateNewOrders.size()) {
				Formatter l_formatter4 = new Formatter();
				l_formatter4.format(Constants.NEGOTIATE_NO_EFFECT, d_currentPlayer.getName(),
						d_playerToNegotiate.getName());
				d_logger.log(l_formatter4.toString());
				l_formatter4.close();
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
		Formatter l_formatter = new Formatter();
		l_formatter.format(Constants.NEGOTIATE_NO_PLAYER, p_playerId);
		d_logger.log(l_formatter.toString());
		l_formatter.close();
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
			Formatter l_formatter = new Formatter();
			l_formatter.format(Constants.PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS, "negotiate", "two");
			d_logger.log(l_formatter.toString());
			l_formatter.close();
			return false;
		}
		return true;
	}
}
