package com.w10.risk_game.commands;

import com.w10.risk_game.utils.Constants;

// The code snippet is defining an enum called `Command`. An enum is a special type in Java that
// represents a fixed set of constants.
public enum Command {

	loadmap(Constants.USER_INPUT_COMMAND_LOADMAP), showmap(Constants.USER_INPUT_COMMAND_SHOWMAP), editmap(
			Constants.USER_INPUT_COMMAND_EDITMAP), savemap(Constants.USER_INPUT_COMMAND_SAVEMAP), validatemap(
					Constants.USER_INPUT_COMMAND_VALIDATEMAP), editcontinent(
							Constants.USER_INPUT_COMMAND_EDIT_CONTINENT), next(
									Constants.USER_INPUT_COMMAND_OPTION_NEXTPHASE), editcountry(
											Constants.USER_INPUT_COMMAND_EDIT_COUNTRY), editneighbor(
													Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR), removeplayer(
															Constants.CLI_GAME_PLAYER_REMOVE), addplayer(
																	Constants.CLI_GAME_PLAYER_CREATE), assigncountries(
																			Constants.CLI_ASSIGN_COUNTRIES);

	// In this code, `private final String name;` is declaring a private final
	// instance variable `name` of
	// type String.
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
