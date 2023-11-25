package com.w10.risk_game;

import java.util.Scanner;

import com.w10.risk_game.controllers.TournamentModeController;
import com.w10.risk_game.engines.SinglePlayerEngine;
import com.w10.risk_game.engines.TournamentEngine;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.ConsoleLogger;
import com.w10.risk_game.utils.loggers.FileLogger;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * Initialize the Application
 *
 * @author Sherwyn Dsouza, Omnia Alam
 */
public class App {

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	public static String Command = "";
	SinglePlayerEngine l_singlepPlayerEngine = new SinglePlayerEngine();
	TournamentModeController l_tournamentModeController = new TournamentModeController();
	/**
	 * The `App` constructor attaches the Console Logger and File Logger as
	 * Observers
	 */

	public App() {
		Logger.attach(new ConsoleLogger());
		Logger.attach(new FileLogger(Constants.LOGGER_FILE_PATH));

	}

	/**
	 * The main function creates an instance of the App class and calls its
	 * startGame method.
	 *
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		App d_app = new App();
		d_app.startGame();
	}

	/**
	 * The startGame() function logs a startup message, creates a GameEngine object,
	 * and starts the game, logging any exceptions that occur.
	 */
	public void startGame() {
		Logger.log(Constants.STARTUP_PHASE_ENTRY_STRING);
		Logger.log(Constants.STARTUP_PHASE_ENTRY_STRING1);
		Logger.log(Constants.STARTUP_PHASE_ENTRY_STRING2);

		boolean l_exit = false;
		while (!l_exit) {
			try {
				// Display a user input request
				Logger.log(Constants.USER_INPUT_REQUEST);

				// Create a Scanner to read the input from the user
				Scanner l_scanner = new Scanner(System.in);

				// Read the user's input and log the command that was entered
				Command = l_scanner.nextLine();
				Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + Command);
				switch (Command) {
					case Constants.USER_INPUT_COMMAND_SINGLEPLAYER :
						l_singlepPlayerEngine.start(this, l_scanner);
					case Constants.USER_INPUT_COMMAND_TOURNAMENT :
						l_tournamentModeController.start(this, l_scanner);
					case Constants.USER_INPUT_COMMAND_QUIT :
						l_scanner.close();
						l_exit = true;
						break;
					default :
						Logger.log(Constants.USER_INPUT_ERROR_COMMAND_INVALID);
				}
				Logger.log("");
				if (l_exit) {
					break;
				}
			} catch (Exception e) {
				Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
				e.printStackTrace(System.out);
			}
		}
	}
}
