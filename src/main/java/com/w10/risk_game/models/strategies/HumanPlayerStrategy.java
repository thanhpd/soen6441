package com.w10.risk_game.models.strategies;

import java.util.Scanner;

import com.w10.risk_game.commands.Advance;
import com.w10.risk_game.commands.Airlift;
import com.w10.risk_game.commands.Blockade;
import com.w10.risk_game.commands.Bomb;
import com.w10.risk_game.commands.Deploy;
import com.w10.risk_game.commands.Negotiate;
import com.w10.risk_game.engines.SinglePlayerEngine;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

/**
 * The HumanPlayerStrategy class is a subclass of PlayerStrategy that allows a
 * human player to issue orders in a game.
 */
public class HumanPlayerStrategy extends PlayerStrategy {

	public HumanPlayerStrategy(Player p_player) {
		super(p_player);
	}

	/**
	 * This method is used to issue an order This method gets the command input,
	 * creates an order object and adds it to the list of orders
	 */
	public void issueOrder() {
		// Set the 'hasCommitted' flag to false for the current player
		d_player.setHasCommitted(false);
		// Retrieve the player's input command from the GameEngine
		String l_input = SinglePlayerEngine.Command;
		String[] l_inputArray = l_input.split(" ");
		boolean l_again = true;
		boolean l_failed = false;
		while (l_again) {
			Scanner l_scanner = new Scanner(System.in);
			// Step 1: Handle invalid input
			if (l_failed) {
				Logger.log(Constants.PLAYER_ISSUE_ORDER_RESTART);
				Logger.log(Constants.USER_INPUT_REQUEST);
				l_input = l_scanner.nextLine();
				Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + l_input);

				// Check if user enters quit after an invalid order
				if (l_input.trim().equals(Constants.USER_INPUT_COMMAND_QUIT)) {
					SinglePlayerEngine.Command = Constants.USER_INPUT_COMMAND_QUIT;
					break;
				}
				// Check if user enters showmap after an invalid order
				if (l_input.trim().equals(Constants.USER_INPUT_COMMAND_SHOWMAP)) {
					SinglePlayerEngine.Phase.showMap();
					continue;
				}
				l_inputArray = l_input.split(" ");
			}
			// Step 2: Check the input format
			boolean isValidOrderInput = d_player.checkValidOrderInput(l_inputArray);
			if (!isValidOrderInput) {
				l_failed = true;
				continue;
			}
			String l_orderType = l_inputArray[0];
			// Process the order based on the order type
			switch (l_orderType) {
				// Step 3: Create order object and add it to the list of orders
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY :
					l_failed = !Deploy.ValidateIssueDeployOrder(d_player, l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE :
					l_failed = !Advance.ValidateIssueAdvanceOrder(d_player, l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB :
					l_failed = !Bomb.ValidateIssueBombOrder(d_player, l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE :
					l_failed = !Blockade.ValidateIssueBlockadeOrder(d_player, l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT :
					l_failed = !Airlift.ValidateIssueAirliftOrder(d_player, l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE :
					l_failed = !Negotiate.ValidateIssueDiplomacyOrder(d_player, l_inputArray);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT :
					if (d_player.getLeftoverArmies() == 0) {
						d_player.setHasCommitted(true);
						Logger.log(Constants.PLAYER_ISSUE_ORDER_COMMIT_SUCCEED);
						l_failed = false;
					} else {
						Logger.log(Constants.PLAYER_ISSUE_ORDER_COMMIT_INVALID);
						l_failed = true;
					}
					break;
				default :
					Logger.log(Constants.PLAYER_ISSUE_ORDER_INVALID_INPUT_TYPE);
					l_failed = true;
			}
			// Check if the order failed; re-enter if true, exit the loop if false
			if (l_failed) {
				l_again = true;
			} else {
				l_again = false;
			}
		}
	}

	/**
	 * The function returns the name of a strategy for a human player.
	 *
	 * @return The method is returning the constant value
	 *         "Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_HUMAN".
	 */
	@Override
	public String getStrategyName() {
		return Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_HUMAN;
	}
}
