package com.w10.risk_game.commands;

import com.w10.risk_game.utils.Constants;

/**
 * The Command enum represents the possible commands that can be issued by a
 * user
 */
public enum Command {

	loadmap(Constants.USER_INPUT_COMMAND_LOADMAP), showmap(Constants.USER_INPUT_COMMAND_SHOWMAP), editmap(
			Constants.USER_INPUT_COMMAND_EDITMAP), savemap(Constants.USER_INPUT_COMMAND_SAVEMAP), validatemap(
					Constants.USER_INPUT_COMMAND_VALIDATEMAP), editcontinent(
							Constants.USER_INPUT_COMMAND_EDIT_CONTINENT), next(
									Constants.USER_INPUT_COMMAND_OPTION_NEXTPHASE), editcountry(
											Constants.USER_INPUT_COMMAND_EDIT_COUNTRY), editneighbor(
													Constants.CLI_GAME_PLAYER_REMOVE), gameplayer(
															Constants.USER_INPUT_COMMAND_GAMEPLAYER), assigncountries(
																	Constants.CLI_ASSIGN_COUNTRIES), deploy(
																			Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY), advance(
																					Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE), bomb(
																							Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB), blockade(
																									Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE), airlift(
																											Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT), negotiate(
																													Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE), commit(
																															Constants.USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT), none(
																																	""), quit(
																																			Constants.USER_INPUT_COMMAND_QUIT);

	private final String name;

	Command(String commandName) {
		this.name = commandName;
	}

	/**
	 * The getName() function returns the name of an object.
	 *
	 * @return The method is returning the value of the variable "name".
	 */
	public String getName() {
		return name;
	}
}
