package com.w10.risk_game.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.w10.risk_game.engines.TournamentEngine;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The TournamentModeController class is responsible for handling user input and
 * executing commands in a tournament game mode.
 */
public class TournamentModeController {

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	public static String Command = "";

	public TournamentModeController() {

	}

	/**
	 * The start function takes an App object and a Scanner object as parameters,
	 * and then enters a loop where it prompts the user for input, reads the input,
	 * and performs different actions based on the input.
	 *
	 * @param p_scanner
	 *            The parameter `p_scanner` is a `Scanner` object that is used to
	 *            read user input from the console. It is passed to the `start`
	 *            method so that the method can read user input and process it
	 *            accordingly.
	 */
	public void start(Scanner p_scanner) {
		boolean l_exit = false;

		while (!l_exit) {
			try {
				// Display a user input request
				Logger.log(Constants.TOURNAMENT_PHASE_ENTRY);
				Logger.log(Constants.TOURNAMENT_PHASE_ENTRY1);
				Logger.log(Constants.TOURNAMENT_PHASE_ENTRY2);
				Logger.log("Maps are being read from - " + Constants.DEFAULT_GAME_MAP_FOLDER_PATH);
				Logger.log(Constants.USER_INPUT_REQUEST);

				// Read the user's input and log the command that was entered

				Command = p_scanner.nextLine();
				List<String> l_mainCommand = new ArrayList<String>();
				l_mainCommand = Arrays.asList(Command.split(" "));
				switch (l_mainCommand.get(0)) {
					case Constants.USER_INPUT_COMMAND_TOURNAMENTMODE :
						parseCommand(Command);
						Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + Command);

						// Quit command
					case Constants.USER_INPUT_COMMAND_QUIT :
						l_exit = true;
						break;

					default :
						Logger.log(Constants.USER_INPUT_ERROR_COMMAND_INVALID);
				}
				Logger.log("");
				if (l_exit) {
					break;
				}
			}

			catch (Exception e) {
				Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
				Logger.log(e.getMessage());
				Logger.log("");
			}
		}
	}

	/**
	 * The function "parseCommand" takes a tournament command as input, splits it
	 * into a list of commands, and then uses the TournamentEngine class to start a
	 * game with the extracted values.
	 *
	 * @param p_tournamentCommand
	 *            The `p_tournamentCommand` parameter is a string that represents
	 *            the command for starting a tournament game.
	 */
	private void parseCommand(String p_tournamentCommand) {
		if (((extractValues(p_tournamentCommand, "-P")).contains("Human"))
				|| ((extractValues(p_tournamentCommand, "-P")).contains("human"))) {
			Logger.log(Constants.TOURNAMENT_PHASE_ENTRY3);
		} else {
			TournamentEngine tournamentEngine = new TournamentEngine();
			tournamentEngine.startGame(extractValues(p_tournamentCommand, "-P"),
					extractValues(p_tournamentCommand, "-M"), extractValue(p_tournamentCommand, "-G"),
					extractValue(p_tournamentCommand, "-D"));
		}
	}

	/**
	 * The function extracts values from a command string that follow a specific
	 * flag.
	 *
	 * @param p_command
	 *            The p_command parameter is a string that represents the command
	 *            from which we want to extract values. It could be any command that
	 *            contains the flag we are interested in.
	 * @param p_flag
	 *            The p_flag parameter is a String that represents the flag used in
	 *            the command. A flag is typically a command-line argument that
	 *            starts with a hyphen or double hyphen and is used to modify the
	 *            behavior of a command. For example, in the command "java -jar
	 *            myprogram.jar -f
	 * @return The method is returning a Set of Strings that have been extracted
	 *         from the input command string.
	 */
	public static Set<String> extractValues(String p_command, String p_flag) {
		Set<String> d_extractedValues = new HashSet<String>();

		// Define the pattern for extracting values after the flag
		Pattern d_pattern = Pattern.compile(p_flag + "\\s(\\S+)");
		Matcher d_matcher = d_pattern.matcher(p_command);

		if (d_matcher.find()) {
			String l_values = d_matcher.group(1); // Extract values captured by the group
			String[] d_splitValues = l_values.split(",");

			for (String l_value : d_splitValues) {
				if (!d_extractedValues.add(l_value)) {
					d_extractedValues.add(Constants.USER_INPUT_COMMAND_DUPLICATE);
				}
				d_extractedValues.add(l_value);
			}
		}

		return d_extractedValues;
	}

	/**
	 * The function extracts a value from a command string based on a specified flag
	 * and returns it as an integer.
	 *
	 * @param p_command
	 *            The p_command parameter is a string that represents a command or
	 *            input from the user. It is the string from which we want to
	 *            extract a value.
	 * @param p_flag
	 *            The p_flag parameter is a string that represents a flag or keyword
	 *            that is used to identify the value that needs to be extracted from
	 *            the p_command string.
	 * @return The method is returning an integer value.
	 */
	public static int extractValue(String p_command, String p_flag) {
		String l_extractedValue = "";

		// Define the pattern for extracting value after the flag
		Pattern d_pattern = Pattern.compile(p_flag + "\\s(\\S+)");
		Matcher d_matcher = d_pattern.matcher(p_command);

		if (d_matcher.find()) {
			l_extractedValue = d_matcher.group(1); // Extract value captured by the group
		}

		return Integer.parseInt(l_extractedValue);
	}
}
