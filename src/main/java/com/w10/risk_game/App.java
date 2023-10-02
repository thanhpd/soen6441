package com.w10.risk_game;

import com.w10.risk_game.views.GameUI;

/**
 * Initialize the Application
 */
public class App {
	/**
	 * @param args
	 *            - passed in arguments for the application. The code is the main
	 *            method of the application. It initializes a GameEngine object and
	 *            starts a loop that prompts the user for input and performs
	 *            different actions based on the input.
	 */

	/**
	 * The main function creates a GameUI object and runs the startup phase of the
	 * game.
	 */
	public static void main(String[] args) {
		GameUI l_gameUI = new GameUI();
		l_gameUI.runStartUpPhase();
	}
}
