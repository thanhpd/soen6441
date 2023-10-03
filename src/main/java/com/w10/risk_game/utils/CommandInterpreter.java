package com.w10.risk_game.utils;

import com.w10.risk_game.exceptions.ApplicationException;

/**
 * The CommandInterpreter class provides methods to extract the main command and
 * argument list from a given command input.
 */
public class CommandInterpreter {

	// The `private CommandInterpreter()` is a private constructor of the
	// `CommandInterpreter` class.
	private CommandInterpreter() {
	}

	/**
	 * The function `getMainCommand` takes a command as input and returns the main
	 * command by splitting it on spaces.
	 *
	 * @param p_command
	 *            The parameter `p_command` is a string that represents a command
	 *            input from the user.
	 * @return The method is returning the main command from the given input
	 *         command.
	 */
	public static String GetMainCommand(String p_command) throws ApplicationException {
		if (p_command.isBlank())
			throw new ApplicationException(Constants.USER_INPUT_COMMAND_INVALID);
		return p_command.split(Constants.REGEX_SPLIT_ON_SPACE)[0];
	}

	/**
	 * The function takes a command as input and splits it into an array of
	 * arguments, throwing an exception if the command is invalid.
	 *
	 * @param p_command
	 *            The parameter `p_command` is a string that represents a command.
	 * @return The method is returning a String array.
	 */
	public static String[] GetArgumentList(String p_command) throws ApplicationException {
		String[] l_argumentList = p_command.split(Constants.REGEX_SPLIT_ON_SPACE);
		if (l_argumentList.length == 0)
			throw new ApplicationException(Constants.USER_INPUT_COMMAND_INVALID);
		return l_argumentList;
	}
}
