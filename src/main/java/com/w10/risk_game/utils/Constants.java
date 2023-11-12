package com.w10.risk_game.utils;

/**
 * The Constants class contains various constant values used throughout the
 * code.
 */
public final class Constants {

	/**
	 * This is private constructor for a class called Constants. This constructor
	 * throws an IllegalStateException with the message "Constants class"
	 */
	private Constants() {
		throw new IllegalStateException("Constants class");
	}

	// Menu constants
	public static final String STARTUP_PHASE_ENTRY_STRING = "!!!   Hi Welcome to Risk Game   !!!\n";

	// User input command constants
	public static final String USER_INPUT_REQUEST = "Please enter a command: ";
	public static final String USER_INPUT_COMMAND_ENTERED = "Command entered is: ";
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
	public static final String USER_INPUT_COMMAND_OPTION_NEXTPHASE = "next";
	public static final String USER_INPUT_COMMAND_OPTION_REMOVE = "-remove";
	public static final String USER_INPUT_COMMAND_OPTION_SHOW_ALL = "-showAll";
	public static final String USER_INPUT_COMMAND_ASSIGN_COUNTRIES = "assigncountries";
	public static final String USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY = "deploy";
	public static final String USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE = "advance";
	public static final String USER_INPUT_ISSUE_ORDER_COMMAND_BOMB = "bomb";
	public static final String USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE = "blockade";
	public static final String USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT = "airlift";
	public static final String USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE = "negotiate";
	public static final String USER_INPUT_ISSUE_ORDER_COMMAND_COMMIT = "commit";
	public static final String USER_INPUT_ERROR_COMMAND_EMPTY = "The command cannot be empty!";
	public static final String USER_INPUT_ERROR_COMMAND_INVALID = "Please enter a valid command!";
	public static final String USER_INPUT_ERROR_SOME_ERROR_OCCURRED = "Some errors occurred!";
	public static final String USER_INPUT_ERROR_ARG_LIST_INVALID = "The provided command option(s) is invalid!";
	public static final String USER_INPUT_COMMAND_QUIT = "quit";

	// CLI output command constants
	public static final String CLI_LOAD_MAP = "Loading Map: ";
	public static final String CLI_SHOW_MAP = "Displaying Map";
	public static final String CLI_GAME_PLAYER_REMOVE = "Removed Player: ";
	public static final String CLI_GAME_PLAYER_CREATE = "Created Player: {0}";
	public static final String CLI_ASSIGN_COUNTRIES = "Assigning Countries...";
	public static final String CLI_ASSIGN_REINFORCEMENTS = "Reinforcements have been assigned to the players.";
	public static final String CLI_ISSUE_ORDER_PLAYER = "Issuing orders for player ";
	public static final String CLI_ITERATION_OPTION = "\n===========\nRunning the {0} option with the parameters {1}.\n";

	// Game Engine constants
	public static final String GAME_ENGINE_ERROR_PRINTING_COUNTRY_DETAILS = "Error in printing country name!";
	public static final String GAME_ENGINE_ERROR_ASSIGNING_COUNTRIES = "Cannot assign {0} countries to {1} players!";
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
	public static final String GAME_ENGINE_ERROR_MAP_DOES_NOT_EXIST = "File {0} does not exist!\nCreating new map named {0}.";
	public static final String GAME_ENGINE_ERROR_CREATE_MAP = "Error creating map named {0}. Please try again.\n{1}";
	public static final String GAME_ENGINE_CANNOT_SAVE_MAP = "Cannot save map";
	public static final String GAME_ENGINE_EXECUTING_ORDERS = "Executing orders now...";
	public static final String GAME_ENGINE_ISSUE_ORDER_NUMBER_OF_ARMIES = "You can issue orders for {0} armies";
	public static final String GAME_ENGINE_ISSUE_ORDER_PHASE_STRING = "ISSUEORDER PHASE";
	public static final String GAME_ENGINE_GAME_OVER = "GAME OVER!!! ";
	public static final String GAME_ENGINE_END_GAME = " is the winner of the game!!!";

	// Other constants
	public static final String REGEX_SPLIT_ON_SPACE = "\\s+";
	public static final String SPACE = " ";
	public static final String EQUAL = "=";
	public static final String COMMA = ",";
	public static final String NEW_LINE = "\n";

	// Maps Folder Path
	public static final String DEFAULT_GAME_MAP_TEST_FOLDER_PATH = "src/test/resources/maps/";

	// Map Reader constants
	public static final String MAP_READER_FILE_NOT_FOUND = "Cannot find file with the entered filename!";

	// Player constants
	public static final String PLAYER_ISSUE_ORDER_RESTART = "Please re-enter the invalid order. The following orders are available: \"deploy\", \"advance\", \"bomb\", \"blockade\", \"airlift\", \"negotiate\", \"commit\", \"showmap\" or \"quit\".";
	public static final String PLAYER_ISSUE_ORDER_COUNTRY_ID_NOT_INTEGER = "Invalid input! The country id should be a positive integer. Please try again.";
	public static final String PLAYER_ISSUE_ORDER_ARMIES_NOT_INTEGER = "Invalid input! The number of armies should be a positive integer.	Please try again.";
	public static final String PLAYER_ISSUE_ORDER_INVALID_INPUT_TYPE = "Invalid input! The order type should be \"deploy\", \"advance\", \"bomb\", \"blockade\", \"airlift\", \"negotiate\", \"commit\", \"showmap\" or \"quit\".";
	public static final String PLAYER_ISSUE_ORDER_DEPLOY_INVALID_COUNTRY = "Invalid input! The country id should be one of the countries owned by the player. Please try again.";
	public static final String PLAYER_ISSUE_ORDER_NO_CARD = "The player does not have that card. It will not be added to the list of orders.";
	public static final String BLOCKADE_CARD_NO_VALID_COUNTRY = "No valid country to block with ID = {0}.";
	public static final String AIRLIFT_CARD_NOT_ENOUGH_ARMIES = "Not enough armies to airlift!";
	public static final String AIRLIFT_CARD_NO_VALID_COUNTRY = "No valid country to airlift with ID = {0}.";
	public static final String PLAYER_ISSUE_ORDER_NOT_CONTAIN_ALL_NECESSARY_PARTS = "Invalid input! The {0} command should contain {1} parts. Please try again.";
	public static final String PLAYER_ISSUE_ORDER_INCORRECT = "This {0} order is invalid. It will not be added to the list of orders.";
	public static final String PLAYER_ISSUE_ORDER_SUCCEED = "This order is added to the list of orders successfully.";
	public static final String PLAYER_ISSUE_ORDER_COMMIT_INVALID = "You cannot commit before deploying all your armies.";
	public static final String PLAYER_ISSUE_ORDER_COMMIT_SUCCEED = "All orders are committed successfully.";
	public static final String SHOW_PLAYER_CARDS = "You have the following cards: {0}";
	public static final String SHOW_PLAYER_CARDS_EMPTY = "You don't have any cards.";

	// Map Editor constants
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
	public static final String MAP_EDITOR_COUNTRY_ID_NOT_EXIST = "Country ID does not exist!";
	public static final String MAP_EDITOR_COUNTRY_REMOVED = " Country is removed!";
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

	// Map Validator constants
	public static final String MAP_VALIDATOR_EMPTY_MAP = "MapValidator: The map is empty!";
	public static final String MAP_VALIDATOR_CONTINENT_NOT_DECLARED = "MapValidator: The continent(s) of some countries are not declared!";
	public static final String MAP_VALIDATOR_NEIGHBOR_NOT_DECLARED = "MapValidator: The neighbor(s) of some countries are not declared!";
	public static final String MAP_VALIDATOR_COUNTRY_AS_ITS_OWN_NEIGHBOR = "MapValidator: Some countries are referring to itself as a neighbor";
	public static final String MAP_VALIDATOR_COUNTRY_INACCESSIBLE = "MapValidator: Some countries are inaccessible";
	public static final String MAP_VALIDATOR_COUNTRY_NOT_FULLY_CONNECTED = "MapValidator: Some continents are not fully-connected";

	// Map Display constants
	public static final String MAP_DISPLAY_TABLE1_LINE = "+---------------+---+---------------+-----+---------------------------------------------------------------------------------------------------------+\n";
	public static final String MAP_DISPLAY_TABLE1_COLUMN_NAMES = "| Country       |ID | Continent     |Bonus| Neighbor Countries                                                                                      |\n";
	public static final String MAP_DISPLAY_TABLE1_FORMAT_PATTERN = "|%-15s|%-3s|%-15s|%-5s|%-105s|\n";
	public static final String MAP_DISPLAY_TABLE2_LINE = "+---------------+---+---------------+-----+---------------------------------------------------------------------------------------------------------+------------+--------+\n";
	public static final String MAP_DISPLAY_TABLE2_COLUMN_NAMES = "| Country       |ID | Continent     |Bonus| Neighbor Countries                                                                                      |   Owner    | Armies |\n";
	public static final String MAP_DISPLAY_TABLE2_FORMAT_PATTERN = "|%-15s|%-3s|%-15s|%-5s|%-105s|%-12s|%-8s|\n";
	public static final String MAP_DISPLAY_NEUTRAL_COUNTRY = "Neutral";
	public static final String MAP_DISPLAY_CANNOT_DISPLAY_MAP = "Cannot display map!";

	// Map Reader constants
	public static final String DOMINATION_MAP_READER_MAP = "[map]";
	public static final String DOMINATION_MAP_READER_CONTINENTS = "[continents]";
	public static final String DOMINATION_MAP_READER_COUNTRIES = "[countries]";
	public static final String DOMINATION_MAP_READER_BORDERS = "[borders]";
	public static final String CONQUEST_MAP_READER_MAP = "[Map]";
	public static final String CONQUEST_MAP_READER_CONTINENTS = "[Continents]";
	public static final String CONQUEST_MAP_READER_TERRITORIES = "[Territories]";
	public static final String MAP_SAVE_ERROR = "Unable to save file. Please try again.";
	public static final String MAP_SAVE_SUCCESS = "Map Saved Successfully";

	// Reinforcements constants
	public static final int REINFORCEMENTS_MIN_NUMBER_OF_ARMIES = 3;

	// Logger constants
	public static final String LOGGER_FILE_PATH = "src/main/resources/logs/log.txt";
	public static final String LOGGER_FILE_TEST_PATH = "src/test/resources/logs/log.txt";
	public static final String LOGGER_FILE_ISSUE = "Issue with log file!";

	// Deploy constants
	public static final String DEPLOY_SUCCEED = "Player {0} deployed {1} armies to country {2}";
	public static final String DEPLOY_INVALID_ARMIES_ZERO = "Invalid input! The number of reinforcement soldiers should be greater than zero. Please try again.";
	public static final String DEPLOY_INVALID_ARMIES = "Invalid input! The entered number should be less than or equal to the number of reinforcement soldiers. Please try again.";

	// Negotiate constants
	public static final String NEGOTIATE_CARD_USED = "{0} is negotiating with {1}";
	public static final String NEGOTIATE_ATTACK_PREVENT = "Prevented attack from {0} to {1}";
	public static final String NEGOTIATE_NO_EFFECT = "No attack detected between {0} and {1}";
	public static final String NEGOTIATE_SELF = "Cannot negotiate with yourself";
	public static final String NEGOTIATE_NO_PLAYER = "No player with id {0}";

	// Advance constants
	public static final String ADVANCE_NOT_OWNER = "Advance order issued by {0} can no longer be executed as player no longer owns {1}.";
	public static final String ADVANCE_BATTLE_START = "{0} launches an attack on {1}, owned by {2}! The battle rages on...";
	public static final String ADVANCE_BATTLE_WON = "{0} dominates! You''ve conquered {1}. Keep the conquest going!";
	public static final String ADVANCE_BATTLE_LOST = "{0}''s forces have been defeated in the battle for {1}, owned by {2}.";
	public static final String ADVANCE_DEPLOY_SUCCEED = "{0} deployed {1} armies to {2}.";
	public static final String ADVANCE_INVALID_COUNTRY_NAME = "Invalid input! Country name {0} does not exist.";
	public static final String ADVANCE_INVALID_ARMY_LESS = "Invalid input! Number of armies must be greater than 0.";
	public static final String ADVANCE_INVALID_ARMY_MORE = "Invalid input! Number of armies to advance cannot be greater than those deployed on the country.";
	public static final String ADVANCE_INVALID_COUNTRY_NOT_OWNED = "Invalid input! Country {0} is not owned by {1}, or country {0} does not exist.";
	public static final String ADVANCE_INVALID_COUNTRY_NOT_NEIGHBOR = "Invalid input! Country {0} is not a neighbor of {1}, or country {0} does not exist.";

	// Bomb constants
	public static final String BOMB_SUCCEED = "Player {0} bombed country {1}. {2} lost half of their armies.";
	public static final String BOMB_CARD_NO_VALID_COUNTRY = "No valid country to bomb with ID = {0}.";

	// Blockade constants
	public static final String BLOCKADE_SUCCEED = "Player {0} blockaded the country {1}. Country {1} became neutral and has {2} armies now.";

	// Airlift constants
	public static final String AIRLIFT_SUCCEED = "{0} armies have been airlifed from {1} to {2}";
	public static final String AIRLIFT_COUNTRY_DOESNOT_BELONG_TO_PLAYER = "Country ID - {0} doesnt belong to {1}";
	public static final String AIRLIFT_COUNTRY_NOT_ENOUGH_ARMY = "Country {0} can airlift at most {1} armies";

}
