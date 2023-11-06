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

	private final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	/**
	 * The `App` constructor attaches the Console Logger and File Logger as
	 * Observers
	 */
	public App() {
		d_logger.attach(new ConsoleLogger());
		d_logger.attach(new FileLogger());
	}

	/**
	 * The main function creates an instance of the App class and calls its
	 * startGame method.
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
		d_logger.log(Constants.STARTUP_PHASE_ENTRY_STRING);
		GameEngine l_gameEngine = new GameEngine();
		try {
			l_gameEngine.start();
		} catch (Exception e) {
			d_logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
			d_logger.log(e.getMessage());
		}
	}
}
