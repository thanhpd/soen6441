package com.w10.risk_game.commands;

import com.w10.risk_game.controllers.RiskGameController;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * This class is to negotiate with other players. In this turn, the two players can not attack each other.
 * @see Order
 * @author Yajing Liu
 */
public class Negotiate extends Order{
		private Player d_currentPlayer;
		private String d_playerId;
		private static Player d_playerToNegotiate;
		private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();
		/**
		 * This is a constructor of the Negotiate class
		 * @param p_player The player who issues the order
		 * @param p_playerId The player id of the player to negotiate with
		 */
		public Negotiate(Player p_player, String p_playerId) {
			this.d_currentPlayer = p_player;
			this.d_playerId = p_playerId;
		}

	/**
	 * This method extends the execute method in the Order class. It removes all the advance orders between these two players.
	 */
	@Override
		public void execute() {
			if (ValidateOrder(d_currentPlayer, d_playerId)){
				Formatter l_formatter = new Formatter();
				l_formatter.format(Constants.NEGOTIATE_CARD_USED, d_currentPlayer.getName(), d_playerToNegotiate.getName());
				d_logger.log(l_formatter.toString());
				List<Order> l_currentPlayerOrders = d_currentPlayer.getOrders();
				List<Order> l_playerToNegotiateOrders = d_playerToNegotiate.getOrders();
				List<Order> l_currentPlayerNewOrders = new ArrayList<>();
				List<Order> l_playerToNegotiateNewOrders = new ArrayList<>();
				// Step 1: remove all the advance orders between these two players in the current player's order list
				for (Order l_order : l_currentPlayerOrders){
					if (l_order instanceof Advance){
						Advance l_advance = (Advance) l_order;
						if (l_advance.getCountryNameFrom().getOwner().getName() == d_currentPlayer.getName() && l_advance.getCountryNameTo().getOwner().getName() == d_playerToNegotiate.getName()){
							l_formatter.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_currentPlayer.getName(), d_playerToNegotiate.getName());
							d_logger.log(l_formatter.toString());
							continue;
						}
					}
					l_currentPlayerNewOrders.add(l_order);
				}
				d_currentPlayer.setOrders(l_currentPlayerNewOrders);
				// Step 2: remove all the advance orders between these two players in the order list of the player to negotiate with
				for (Order l_order : l_playerToNegotiateOrders){
					if (l_order instanceof Advance){
						Advance l_advance = (Advance) l_order;
						if (l_advance.getCountryNameFrom().getOwner().getName() == d_playerToNegotiate.getName() && l_advance.getCountryNameTo().getOwner().getName() == d_currentPlayer.getName()){
							l_formatter.format(Constants.NEGOTIATE_ATTACK_PREVENT, d_playerToNegotiate.getName(), d_currentPlayer.getName());
							d_logger.log(l_formatter.toString());
							continue;
						}
					}
					l_playerToNegotiateNewOrders.add(l_order);
				}
				d_playerToNegotiate.setOrders(l_playerToNegotiateNewOrders);
				if (l_currentPlayerOrders.size() == l_currentPlayerNewOrders.size() && l_playerToNegotiateOrders.size() == l_playerToNegotiateNewOrders.size()){
					l_formatter.format(Constants.NEGOTIATE_NO_EFFECT, d_currentPlayer.getName(), d_playerToNegotiate.getName());
					d_logger.log(l_formatter.toString());
				}
				l_formatter.close();
			}
		}

	/**
	 * This method is to validate the order. It checks if the player to negotiate with exists.
	 * @param p_currentPlayer The player who issues the order
	 * @param p_playerId The player id of the player to negotiate with
	 * @return the boolean value to indicate if the order is valid
	 */
		public static boolean ValidateOrder(Player p_currentPlayer, String p_playerId) {
			if (p_currentPlayer.getPlayerId().equals(p_playerId)){
				d_logger.log(Constants.NEGOTIATE_SELF);
				return false;
			}
			List<Player> l_players = RiskGameController.getPlayerList();
			for (Player l_player : l_players){
				if (l_player.getPlayerId().equals(p_playerId)){
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
}
