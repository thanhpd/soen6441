package com.w10.risk_game.utils;

import com.w10.risk_game.exceptions.ApplicationException;
import java.util.ArrayList;

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
	 * The function takes a command as input and returns the main command by
	 * splitting it on spaces.
	 *
	 * @param p_command
	 *            The parameter `p_command` is a string that represents a command
	 *            input from the user.
	 *
	 * @throws ApplicationException
	 *             If the user enters an invalid command, show 'Please enter a valid
	 *             command!' to the user
	 *
	 * @return The method is returning the main command from the given input
	 *         command.
	 */
	public static String GetMainCommand(String p_command) throws ApplicationException {
		if (p_command.isBlank())
			throw new ApplicationException(Constants.USER_INPUT_ERROR_COMMAND_EMPTY);
		return p_command.split(Constants.REGEX_SPLIT_ON_SPACE)[0];
	}

	/**
	 * The function takes a command as input and splits it into an array of
	 * arguments, throwing an exception if the command is invalid.
	 *
	 * @param p_command
	 *            The parameter `p_command` is a string that represents a command.
	 * @throws ApplicationException
	 *             If the user enters an invalid command, show 'Please enter a valid
	 *             command!' to the user
	 *
	 * @return The method is returning a String array.
	 */
	public static String[] GetArgumentList(String p_command) throws ApplicationException {
		String[] l_argumentList = p_command.split(Constants.REGEX_SPLIT_ON_SPACE);
		if (l_argumentList.length == 0)
			throw new ApplicationException(Constants.USER_INPUT_ERROR_COMMAND_EMPTY);
		return l_argumentList;
	}

	/**
	 * The function takes a command as input and returns a list of command options
	 * and their corresponding values.
	 *
	 * @param p_command
	 *            A string representing a command.
	 * @return The method is returning an ArrayList of ArrayLists of Strings. Each
	 *         ArrayList of Strings contains the option key as the first item, with
	 *         the remaining items being the corresponding values.
	 */
	public static ArrayList<ArrayList<String>> GetCommandOptions(String p_command) throws ApplicationException {
		ArrayList<ArrayList<String>> l_listOfOptions = new ArrayList<>();
		// Get all arguments from the command by splitting the string on spaces.
		String[] l_argumentList = p_command.split(Constants.REGEX_SPLIT_ON_SPACE);

		if (l_argumentList.length == 0)
			throw new ApplicationException(Constants.USER_INPUT_ERROR_COMMAND_INVALID);

		// Loop through the arguments
		for (String l_arg : l_argumentList) {
			if (l_arg.startsWith("-")) {
				// If the argument starts with a dash, it is an option key, create a new
				// ArrayList for it.
				ArrayList<String> l_optionKeyAndValues = new ArrayList<>();
				l_optionKeyAndValues.add(l_arg);
				l_listOfOptions.add(l_optionKeyAndValues);
			} else if (!l_listOfOptions.isEmpty()) {
				// If the argument does not start with a dash, it is a value for the latest
				// option key.
				// Add it to the latest option key ArrayList.
				ArrayList<String> l_latestOptionKeyAndValues = l_listOfOptions.get(l_listOfOptions.size() - 1);
				l_latestOptionKeyAndValues.add(l_arg);
			}
		}

		return l_listOfOptions;
	}
}
