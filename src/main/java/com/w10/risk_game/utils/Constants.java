package com.w10.risk_game.utils;

/**
 * The Constants class contains various Game Map constant values used throughout
 * the code.
 */
public final class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class");
	}

	// Menu
	public static final String STARTUP_PHASE_ENTRY_STRING = "!!!   Hi Welcome to Risk Game   !!!\n"
			+ "You are in the Startup phase:\n" + "You can enter any of the below commands:\n"
			+ "-> loadmap <filepath>\n" + "-> savemap <filepath>\n" + "-> showmap\n" + "-> editmap <filepath>\n"
			+ "-> validatemap\n" + "-> editcontinent -add <continentID> <continentvalue> -remove <continentID>\n"
			+ "-> editcountry -add <countryID> <countryName> <continentID> -remove <countryID>\n"
			+ "-> editneighbor -add <countryID> <neighborcountryID> -remove <countryID> <neighborcountryID>\n"
			+ "-> gameplayer -add <playername> -remove <playername>\n" + "-> assigncountries\n" + "-> quit\n";

	public static final String GAMEPLAY_PHASE_ENTRY_STRING = "You are now in the Gameplay phase:\n"
			+ "You can enter any of the below commands:\n" + "-> showmap\n" + "-> deploy <CountryId> <No. of armies>\n"
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
	public static final String USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY = "deploy";
	public static final String USER_INPUT_COMMAND_INVALID = "Please enter a valid command!";
	public static final String USER_INPUT_SOME_ERROR_OCCURRED = "Some errors occurred!";
	public static final String USER_INPUT_COMMAND_QUIT = "quit";

	// CLI output command constants
	public static final String CLI_LOAD_MAP = "Loading Map: ";
	public static final String CLI_SHOW_MAP = "Show Map...";
	public static final String CLI_GAME_PLAYER_REMOVE = "Removed Player: ";
	public static final String CLI_ASSIGN_COUNTRIES = "Assigning Countries...";
	public static final String CLI_ISSUE_ORDER_PLAYER = "Issuing orders for player ";

	// Game Engine Class Constants
	public static final String GAME_ENGINE_ERROR_PRINTING_COUNTRY_DETAILS = "Error in printing country name!";
	public static final String GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES = "Cannot assign %d countries to %d players!";
	public static final String GAME_ENGINE_ERROR_ADD_PLAYER = "Player cannot be added!";
	public static final String GAME_ENGINE_ERROR_REMOVE_PLAYER = "Player cannot be removed!";
	public static final String GAME_ENGINE_ERROR_PLAYER_NAME_DOESNT_EXIST = "Player doesn't exist!";
	public static final String GAME_ENGINE_ERROR_PLAYER_NAME_ALREADY_EXISTS = "Player already exists!";
	public static final String GAME_ENGINE_CANNOT_LOAD_MAP = "Cannot load map!";
	public static final String GAME_ENGINE_FAILED_TO_EDIT_MAP = "Error in editing map!";
	public static final String GAME_ENGINE_FAILED_TO_VALIDATE_MAP = "Error in validating map!";
	public static final String GAME_ENGINE_MAP_NOT_CREATED = "Map is not created!";
	public static final String GAME_ENGINE_MAP_VALID = "Map is valid";
	public static final String GAME_ENGINE_MAP_INVALID = "Map is invalid!";
	public static final String GAME_ENGINE_MAP_EDIT_SUCCESS = "Map loaded successfully, please start editing..!";
	public static final String GAME_ENGINE_ERROR_MAP_DOES_NOT_EXIST = "File %s does not exist.%nCreating new map named %s.";
	public static final String GAME_ENGINE_ERROR_CREATE_MAP = "Error creating map named %s. Please try again.%n%s";
	public static final String GAME_ENGINE_CANNOT_SAVE_MAP = "Cannot save map";
	public static final String GAME_ENGINE_EXECUTING_ORDERS = "Executing orders now...";
	public static final String GAME_ENGINE_ISSUE_ORDER_NUMBER_OF_ARMIES = "You can issue orders for %d armies";

	// Other constants
	public static final String REGEX_SPLIT_ON_SPACE = "\\s+";
	public static final String SPACE = " ";
	public static final String NEW_LINE = "\n";

	// Maps Folder Path
	public static final String DEFAULT_GAME_MAP_TEST_FOLDER_PATH = "src/test/resources/maps/";

	// Map Reader Constants
	public static final String MAP_READER_FILE_NOT_FOUND = "Cannot find file with the entered filename!";

	// Player Class Constants
	public static final String PLAYER_ISSUE_ORDER_START = "Please enter your order in the format of \"deploy <CountryId> <No. of armies>\".";
	public static final String PLAYER_ISSUE_ORDER_INPUT_NOT_THREE_PARTS = "Invalid input! The command should contain three parts. Please try again.";
	public static final String PLAYER_ISSUE_ORDER_COUNTRY_ID_NOT_INTEGER = "Invalid input! The country id should be a positive integer. Please try again.";
	public static final String PLAYER_ISSUE_ORDER_ARMIES_NOT_INTEGER = "Invalid input! The number of armies should be a positive integer. Please try again.";
	public static final String PLAYER_ISSUE_ORDER_INVALID_ORDER_TYPE = "Invalid input! The order type should be \"deploy\". Please try again.";
	public static final String PLAYER_ISSUE_ORDER_INVALID_COUNTRY = "Invalid input! The country id should be one of the countries owned by the player. Please try again.";
	public static final String PLAYER_ISSUE_ORDER_INVALID_ARMIES = "Invalid input! The entered number should be less than the number of reinforcement soldiers. Please try again.";
	public static final String PLAYER_ISSUE_ORDER_INVALID_ARMIES_ZERO = "Invalid input! The number of reinforcement soldiers should be greater than zero. Please try again.";

	// Map Editor Constants
	public static final String MAP_EDITOR_EMPTY_COUNTRY_NAME = "Country name is empty!";
	public static final String MAP_EDITOR_COUNTRY_NAME_EXIST = "Country name already exists!";
	public static final String MAP_EDITOR_COUNTRY_ID_EXIST = "Country ID already exists!";
	public static final String MAP_EDITOR_CONTINENT_NOT_EXIST = "Continent does not exist!";
	public static final String MAP_EDITOR_ADD_COUNTRY = " is added, and to the continent with id: ";
	public static final String MAP_EDITOR_CONTINENT_NAME_EXIST = "Continent name already exists!";
	public static final String MAP_EDITOR_ADD_CONTINENT = " is added!";
	public static final String MAP_EDITOR_COUNTRY_NOT_EXIST = "Country does not exist! Please add it to the map first";
	public static final String MAP_EDITOR_NEIGHBOR_COUNTRY_NOT_EXIST = "Neighbor country does not exist! Please add it to the map first";
	public static final String MAP_EDITOR_CONNECTION_EXIST = "Connection already exists!";
	public static final String MAP_EDITOR_ADD_NEIGHBOR = " added to ";
	public static final String MAP_EDITOR_COUNTRY_ID_NOT_EXIST = "Country ID does not exist";
	public static final String MAP_EDITOR_COUNTRY_REMOVED = "Country is removed!";
	public static final String MAP_EDITOR_REMOVED = " is removed!";
	public static final String MAP_EDITOR_COUNTRIES_REMOVED = "Linked countries were also removed!";
	public static final String MAP_EDITOR_CONNECTION_NOT_EXIST = "Connection does not exist!";
	public static final String MAP_EDITOR_NEIGHBOR_REMOVED = " removed from ";
	public static final String MAP_EDITOR_AFTER_CONTINENT_ADDED = "###############After Continent added";
	public static final String MAP_EDITOR_AFTER_COUNTRY_ADDED = "###############After Country added ";
	public static final String MAP_EDITOR_AFTER_NEIGHBOR_ADDED = "###############After Neighbor added";
	public static final String MAP_EDITOR_AFTER_CONTINENT_REMOVE = "###############After Removing Continent ";
	public static final String MAP_EDITOR_AFTER_COUNTRY_REMOVE = "###############After Removing country";
	public static final String MAP_EDITOR_AFTER_NEIGHBOR_REMOVE = "###############After Removing neighbor";

	// Map Validator Constants
	public static final String MAP_VALIDATOR_EMPTY_MAP = "MapValidator: The map is empty!";
	public static final String MAP_VALIDATOR_CONTINENT_NOT_DECLARED = "MapValidator: The continent(s) of some countries are not declared!";
	public static final String MAP_VALIDATOR_NEIGHBOR_NOT_DECLARED = "MapValidator: The neighbor(s) of some countries are not declared!";
	public static final String MAP_VALIDATOR_COUNTRY_AS_ITS_OWN_NEIGHBOR = "MapValidator: Some countries are referring to itself as a neighbor";
	public static final String MAP_VALIDATOR_COUNTRY_INACCESSIBLE = "MapValidator: Some countries are inaccessible";
	public static final String MAP_VALIDATOR_COUNTRY_NOT_FULLY_CONNECTED = "MapValidator: Some continents are not fully-connected";

	// Game Map
	public static final String MAP_DISPLAY_ID = "ID(Continent Name)";
	public static final String MAP_DISPLAY_BONUS = "Bonus";
	public static final String MAP_DISPLAY_COUNTRY_ID = "CountryID";
	public static final String MAP_DISPLAY_COUNTRY_NAME = "CountryName";
	public static final String MAP_DISPLAY_NEIGHBOR_ID = "ID(Neighbors)";
	public static final String MAP_DISPLAY_PLAYER = "Player";
	public static final String MAP_DISPLAY_ARMIES = "Armies";
	// Map Reader
	public static final String MAP_READER_MAP = "[map]";
	public static final String MAP_READER_CONTINENTS = "[continents]";
	public static final String MAP_READER_COUNTRIES = "[countries]";
	public static final String MAP_READER_BORDERS = "[borders]";
	public static final String MAP_SAVE_ERROR = "Unable to save file. Please try again.";

	// Reinforcements Class Constants
	public static final int REINFORCEMENTS_MIN_NUMBER_OF_ARMIES = 3;

}
