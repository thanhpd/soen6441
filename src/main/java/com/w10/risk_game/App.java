package com.w10.risk_game;

import java.util.Scanner;

/**
 * Initialize the Application
 */
public class App {
	/**
	 * @param args passed in arguments for the application
	 */
	public static void main(String[] args) {
		App.printToTerminal();
	}

	/**
	 * This test method prints out a line in the terminal
	 */
	private static void printToTerminal() {
		start();
	}

	public static void start() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("");
		System.out.println("==================================");
		System.out.println("\t\t\t Warzone");
		System.out.println("==================================");
		System.out.println("\t\t\t Main Menu");
		System.out.println("\t=======================");
		System.out.println("\t\t 1. New Game");
		System.out.println("\t\t 2. Load Game");
		System.out.println("\t\t 3. Exit");
		System.out.println("\t=======================");
		System.out.println("\t\tSelect the option");
		System.out.println("==================================");

		int option = scanner.nextInt();

		switch (option) {
			case 1:
				System.out.println("Starting a New Game");
				break;
			case 2:
				System.out.println("Loading a Game");
				break;
			case 3:
				System.out.println("Exiting the Game");
				break;
			default:
				System.out.println("Invalid Option. Please choose a valid option.");
		}
	}
}
