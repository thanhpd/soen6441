package com.w10.risk_game.utils;

public final class Constants {

	private Constants() {
		// restrict instantiation
	}

	// Menu
	public static final String STARTUP_PHASE_ENTRY_STRING = "!!!   Hi Welcome to Risk Game   !!!\n"
			+ "You can enter any of the below commands:\n" + "-> loadmap <filename>\n" + "-> savemap <filename>\n"
			+ "-> showmap\n" + "-> editmap\n" + "-> validatemap\n"
			+ "-> editcontinent -add <continentID> <continentvalue> -remove <continentID>\n"
			+ "-> editcountry -add <countryID> <countryvalue> <continentID> -remove <countryID>\n"
			+ "-> editneighbor -add <countryID> <neighborcountryID> -remove <countryID> <neighborcountryID>\n"
			+ "-> gameplayer -add <playername> -remove <playername>\n" + "-> assigncountries\n" + "-> start\n"
			+ "-> quit\n";

	// User input command constants
	public static final String USER_INPUT_REQUEST = "Please enter an input: ";
	public static final String USER_INPUT_COMMAND_LOADMAP = "loadmap";
	public static final String USER_INPUT_COMMAND_SHOWMAP = "showmap";
	public static final String USER_INPUT_COMMAND_SAVEMAP = "savemap";
	public static final String USER_INPUT_COMMAND_EDITMAP = "editmap";
	public static final String USER_INPUT_COMMAND_VALIDATEMAP = "validatemap";
	public static final String USER_INPUT_COMMAND_EDIT_CONTINENT = "editcontinent";
	public static final String USER_INPUT_COMMAND_EDIT_COUNTRY = "editcountry";
	public static final String USER_INPUT_COMMAND_EDIT_NEIGHBOR = "editneighbor";
	public static final String USER_INPUT_COMMAND_GAMEPLAYER = "gameplayer";
	public static final String USER_INPUT_COMMAND_OPTION_ADD = "-add";
	public static final String USER_INPUT_COMMAND_OPTION_REMOVE = "-remove";
	public static final String USER_INPUT_COMMAND_OPTION_SHOW_ALL = "-showAll";
	public static final String USER_INPUT_COMMAND_ASSIGN_COUNTRIES = "assigncountries";
	public static final String USER_INPUT_COMMAND_BEGIN_GAME = "start";
	public static final String USER_INPUT_COMMAND_INVALID = "Please enter a valid command!";
	public static final String USER_INPUT_COMMAND_QUIT = "quit";

	// CLI output command constants
	public static final String CLI_LOAD_MAP = "Loading Map: ";
	public static final String CLI_SHOW_MAP = "Show Map...";
	public static final String CLI_GAME_PLAYER_ADD = "Adding Player: ";
	public static final String CLI_GAME_PLAYER_REMOVE = "Removing Player: ";
	public static final String CLI_ASSIGN_COUNTRIES = "Assigning Countries...";
	public static final String CLI_ISSUE_ORDER_PLAYER = "Issuing orders for player ";

	// Game Engine Class Constants
	public static final String GAME_ENGINE_ERROR_PRINTING_COUNTRY_DETAILS = "Error in printing country name!";
	public static final String GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES = "Cannot assign %d countries to %d players!";
	public static final String GAME_ENGINE_ERROR_ADD_PLAYER = "Player cannot be added!";
	public static final String GAME_ENGINE_ERROR_REMOVE_PLAYER = "Player cannot be removed!";
	public static final String GAME_ENGINE_ERROR_PLAYER_NAME_ALREADY_EXISTS = "Player name already exists!";
	public static final String GAME_ENGINE_FAILED_TO_EDIT_MAP = "Error in editing map!";
	public static final String GAME_ENGINE_FAILED_TO_VALIDATE_MAP = "Error in validating map!";
	public static final String GAME_ENGINE_MAP_NOT_CREATED = "Map is not created!";
	public static final String GAME_ENGINE_MAP_VALID = "Map is valid";
	public static final String GAME_ENGINE_MAP_INVALID = "Map is invalid!";
	public static final String GAME_ENGINE_CANNOT_START_GAME = "Game cannot begin!";

	// Other constants
	public static final String REGEX_SPLIT_ON_SPACE = "\\s+";

	// Maps Folder Path
	public static final String GAME_MAP_FOLDER_PATH = "src/main/resources/maps/";

}
