package com.w10.risk_game.utils;

public final class Constants {

	private Constants() {
		// restrict instantiation
	}

	// Menu
	public static final String STARTUP_PHASE_ENTRY_STRING = "!!!   Hi Welcome to Risk Game   !!!\n"
			+ "You can enter any of the below commands:\n" + "-> loadmap <filename>\n" + "-> showmap\n"
			+ "-> gameplayer -add <playername>\n" + "-> gameplayer -remove <playername>\n" + "-> assigncountries\n"
			+ "-> quit\n";

	// User input command constants
	public static final String USER_INPUT_REQUEST = "Please enter an input: ";
	public static final String USER_INPUT_COMMAND_LOADMAP = "loadmap";
	public static final String USER_INPUT_COMMAND_SHOWMAP = "showmap";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER = "gameplayer";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER_OPTION_ADD = "-add";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER_OPTION_REMOVE = "-remove";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER_OPTION_SHOW_ALL = "-showAll";
	public static final String USER_INPUT_COMMAND_ASSIGN_COUNTRIES = "assigncountries";
	public static final String USER_INPUT_COMMAND_INVALID = "Please enter a valid command!";
	public static final String USER_INPUT_COMMAND_QUIT = "quit";

	// Game Engine Class Constants
	public static final String GAME_ENGINE_ERROR_PRINTING_COUNTRY_DETAILS = "Error in printing country name!";
	public static final String GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES = "Cannot assign %d countries to %d players!";
	public static final String GAME_ENGINE_ERROR_ADD_PLAYER = "Player cannot be added!";
	public static final String GAME_ENGINE_ERROR_REMOVE_PLAYER = "Player cannot be removed!";

	// Other constants
	public static final String REGEX_SPLIT_ON_SPACE = "\\s+";

}
