package com.w10.risk_game.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.w10.risk_game.App;
import com.w10.risk_game.engines.TournamentEngine;
import com.w10.risk_game.models.Player;

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
	 * The start() function runs a loop that prompts the user for input, parses the
	 * input, and performs the corresponding action until the user enters the "exit"
	 * command.
	 */
	public void start() {
		boolean l_exit = false;
		Player l_player;
		App l_app = new App();

		while (!l_exit) {
			try {
				// Display a user input request
				Logger.log(Constants.TOURNAMENT_PHASE_ENTRY);
				Logger.log(Constants.TOURNAMENT_PHASE_ENTRY1);
				Logger.log(Constants.TOURNAMENT_PHASE_ENTRY2);
				Logger.log(Constants.USER_INPUT_REQUEST);

				// Create a Scanner to read the input from the user
				Scanner l_scanner = new Scanner(System.in);

				// Read the user's input and log the command that was entered

				Command = l_scanner.nextLine();
				List<String> l_mainCommand = new ArrayList<String>();
				l_mainCommand = Arrays.asList(Command.split(" "));
				switch (l_mainCommand.get(0)) {
					case Constants.USER_INPUT_COMMAND_TOURNAMENTMODE :
						parseCommand(Command);
						// Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + Command);

						// Quit command
					case Constants.USER_INPUT_COMMAND_QUIT :
						l_app.startGame();
						l_scanner.close();
						l_exit = true;

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
		List<String> l_commandList = new ArrayList<String>();
		l_commandList = Arrays.asList(p_tournamentCommand.split(" "));

		TournamentEngine tournamentEngine = new TournamentEngine();
		tournamentEngine.startGame(extractValues(p_tournamentCommand, "-P"), extractValues(p_tournamentCommand, "-M"),
				extractValue(p_tournamentCommand, "-G"), extractValue(p_tournamentCommand, "-D"));

	}

	/**
	 * The function extracts values from a command string that follow a specified
	 * flag.
	 *
	 * @param command
	 *            The `command` parameter is a string that represents the command
	 *            from which we want to extract values. It could be any string that
	 *            contains the flag and the values we are interested in.
	 * @param flag
	 *            The "flag" parameter is a string that represents the flag used in
	 *            the command to indicate the values that need to be extracted. For
	 *            example, if the command is "java -jar myprogram.jar --input
	 *            file.txt", the flag could be "--input".
	 * @return The method is returning a Set of Strings that represents the
	 *         extracted values from the given command string.
	 */
	public static Set<String> extractValues(String command, String flag) {
		Set<String> extractedValues = new HashSet<String>();

		// Define the pattern for extracting values after the flag
		Pattern pattern = Pattern.compile(flag + "\\s(\\S+)");
		Matcher matcher = pattern.matcher(command);

		if (matcher.find()) {
			String values = matcher.group(1); // Extract values captured by the group
			String[] splitValues = values.split(",");

			for (String value : splitValues) {
				extractedValues.add(value);
			}
		}

		return extractedValues;
	}

	/**
	 * The function extracts a value from a command string based on a specified
	 * flag.
	 *
	 * @param command
	 *            The `command` parameter is a string that represents the command
	 *            from which we want to extract a value. It could be any string that
	 *            contains the flag and the value we are interested in.
	 * @param flag
	 *            The "flag" parameter is a string that represents the flag or
	 *            keyword that is used to identify the value you want to extract
	 *            from the "command" string.
	 * @return The method is returning an integer value.
	 */
	public static int extractValue(String command, String flag) {
		String extractedValue = "";

		// Define the pattern for extracting value after the flag
		Pattern pattern = Pattern.compile(flag + "\\s(\\S+)");
		Matcher matcher = pattern.matcher(command);

		if (matcher.find()) {
			extractedValue = matcher.group(1); // Extract value captured by the group
		}

		return Integer.parseInt(extractedValue);
	}
}
