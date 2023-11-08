package com.w10.risk_game;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.ConsoleLogger;
import com.w10.risk_game.utils.loggers.FileLogger;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * Initialize the Application
 *
 * @author Sherwyn Dsouza
 */
public class App {

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

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
		GameEngine l_gameEngine = new GameEngine();
		try {
			l_gameEngine.start();
		} catch (Exception e) {
			Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
			Logger.log(e.getMessage());
		}
	}
}
