package com.w10.risk_game;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.ConsoleLogger;
import com.w10.risk_game.utils.loggers.FileLogger;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * Initialize the Application
 */
public class App {

	private final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	public App() {
		d_logger.attach(new ConsoleLogger());
		//d_logger.attach(new FileLogger());
	}

	public static void main(String[] args) {
		App d_app = new App();
		d_app.startGame();
	}

	public void startGame() {
		GameEngine l_gameEngine = new GameEngine();
		try {
			l_gameEngine.start();
		} catch (Exception e) {
			d_logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
			d_logger.log(e.getMessage());
		}
	}
}
