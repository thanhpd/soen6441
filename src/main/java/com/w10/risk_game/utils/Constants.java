package com.w10.risk_game.utils;

public final class Constants {

	private Constants() {
		// restrict instantiation
	}

	// Menu
	public static final String STARTUP_PHASE_ENTRY_STRING = "!!!   Hi Welcome to Risk Game   !!!\n" +
			"You can enter any of the below commands:\n" +
			"1. loadmap <filename>\n" +
			"2. showmap\n" +
			"3. gameplayer -add <playername>\n" +
			"4. gameplayer -remove <playername>\n" +
			"5. assigncountries\n" +
			"5. quit\n";

	// User input command constants
	public static final String USER_INPUT_REQUEST = "Please enter a input: ";
	public static final String USER_INPUT_COMMAND_LOADMAP = "loadmap";
	public static final String USER_INPUT_COMMAND_SHOWMAP = "showmap";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER = "gameplayer";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER_OPTION_ADD = "-add";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER_OPTION_REMOVE = "-remove";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER_OPTION_SHOW_ALL = "-showAll";
	public static final String USER_INPUT_COMMAND_ASSIGN_COUNTRIES = "assigncountries";
	public static final String USER_INPUT_COMMAND_INVALID = "Please enter a valid command!";
	public static final String USER_INPUT_COMMAND_QUIT = "quit";

	// Other constants
	public static final String REGEX_SPLIT_ON_SPACE = "\\s+";

}
