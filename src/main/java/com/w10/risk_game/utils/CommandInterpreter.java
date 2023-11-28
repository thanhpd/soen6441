package com.w10.risk_game.utils;

import com.w10.risk_game.exceptions.ApplicationException;
import java.util.ArrayList;
import java.util.Arrays;

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
	 *                  The parameter `p_command` is a string that represents a
	 *                  command
	 *                  input from the user.
	 *
	 * @throws ApplicationException
	 *                              If the user enters an invalid command, show
	 *                              'Please enter a valid
	 *                              command!' to the user
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
	 *                  The parameter `p_command` is a string that represents a
	 *                  command.
	 * @throws ApplicationException
	 *                              If the user enters an invalid command, show
	 *                              'Please enter a valid
	 *                              command!' to the user
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
	 * The function `GetCommandOptions` takes a command string as input and returns
	 * a list of options and their corresponding values.
	 *
	 * @param p_command
	 *                  The parameter `p_command` is a string representing a
	 *                  command.
	 * @return The method is returning an ArrayList of ArrayLists of Strings.
	 * @exception ApplicationException
	 *                                 If the user enters an invalid command, show
	 *                                 'Please enter a
	 *                                 valid command!' to the user
	 */
	public static ArrayList<ArrayList<String>> GetCommandOptions(String p_command) throws ApplicationException {
		ArrayList<ArrayList<String>> l_listOfOptions = new ArrayList<>();
		// Get all arguments from the command by splitting the string on spaces.
		String[] l_argumentList = p_command.split(Constants.REGEX_SPLIT_ON_SPACE);

		if (l_argumentList.length == 0) {
			throw new ApplicationException(Constants.USER_INPUT_ERROR_COMMAND_INVALID);
		}

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

	/**
	 * The function checks if the given command and its arguments/options are valid
	 * based on predefined rules.
	 *
	 * @param p_argList
	 *                        An array of strings representing the arguments passed
	 *                        to the
	 *                        command.
	 * @param p_command
	 *                        The command that the user has entered.
	 * @param p_listOfOptions
	 *                        An ArrayList of ArrayLists of Strings. Each inner
	 *                        ArrayList
	 *                        represents a set of options for a command.
	 * @exception ApplicationException
	 *                                 Global general application exception
	 */
	public static void CheckValidArgumentOptions(String[] p_argList, String p_command,
			ArrayList<ArrayList<String>> p_listOfOptions) throws ApplicationException {
		// Define commands that require arguments
		String[] l_commandsWithArgToCheck = new String[] { Constants.USER_INPUT_COMMAND_LOADMAP,
				Constants.USER_INPUT_COMMAND_SAVEMAP, Constants.USER_INPUT_COMMAND_EDITMAP,
				Constants.USER_INPUT_COMMAND_TOURNAMENTMODE,
				Constants.USER_INPUT_COMMAND_EDIT_CONTINENT, Constants.USER_INPUT_COMMAND_EDIT_COUNTRY,
				Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR, Constants.USER_INPUT_COMMAND_GAMEPLAYER,
				Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY };
		// Check if the command requires arguments and if there's at least one argument
		// for the command
		if (Arrays.asList(l_commandsWithArgToCheck).contains(p_command) && p_argList.length < 2) {
			System.out.println("hello");
			throw new ApplicationException(Constants.USER_INPUT_ERROR_ARG_LIST_INVALID);
		}

		// Define commands that require options
		String[] l_commandsWithOptionsToCheck = new String[] { Constants.USER_INPUT_COMMAND_EDIT_CONTINENT,
				Constants.USER_INPUT_COMMAND_EDIT_COUNTRY, Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR,
				Constants.USER_INPUT_COMMAND_GAMEPLAYER, Constants.USER_INPUT_COMMAND_TOURNAMENTMODE,
		};

		// Check if the command requires options and if there's at least one option for
		// the command
		if (Arrays.asList(l_commandsWithOptionsToCheck).contains(p_command)) {
			// Check if the user has not provided any options
			if (p_listOfOptions.isEmpty())
				throw new ApplicationException(Constants.USER_INPUT_ERROR_ARG_LIST_INVALID);
			for (ArrayList<String> l_options : p_listOfOptions) {
				int l_minOptionsLength = GetMinOptionsLength(p_command, l_options);

				if (l_options.size() < l_minOptionsLength) {
					throw new ApplicationException(Constants.USER_INPUT_ERROR_ARG_LIST_INVALID);
				}
			}
		}
	}

	/**
	 * The function returns the minimum length of options required based on the
	 * given command and options.
	 *
	 * @param p_command
	 *                  The command that the user has entered. It is a string value.
	 * @param l_options
	 *                  An ArrayList of strings containing the options provided by
	 *                  the
	 *                  user.
	 * @return The method is returning the minimum length of options required for a
	 *         given command and options list.
	 */
	private static int GetMinOptionsLength(String p_command, ArrayList<String> l_options) {
		int l_minOptionsLength = 2;

		// For adding continent, there should be 2 values
		if (p_command.equals(Constants.USER_INPUT_COMMAND_EDIT_CONTINENT)
				&& l_options.get(0).equals(Constants.USER_INPUT_COMMAND_OPTION_ADD))
			l_minOptionsLength = 3;
		// For editing neighbor, there should be 2 values
		else if (p_command.equals(Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR))
			l_minOptionsLength = 3;
		// For adding a country, there should be 3 values
		else if (p_command.equals(Constants.USER_INPUT_COMMAND_EDIT_COUNTRY)
				&& l_options.get(0).equals(Constants.USER_INPUT_COMMAND_OPTION_ADD))
			l_minOptionsLength = 4;
		return l_minOptionsLength;
	}
}
