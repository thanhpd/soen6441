package com.w10.risk_game.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.MapDisplay;
import com.w10.risk_game.utils.MapEditor;
import com.w10.risk_game.utils.MapReader;
import com.w10.risk_game.utils.MapValidator;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The GameEngine class is responsible for managing the game map, players,
 * issuing orders, executing orders and their interactions in a game.
 *
 * @author Omnia Alam
 */
public class MapEditorController {
	private GameMap d_gameMap;
	private HashMap<String, Player> d_players;
	private MapEditor d_mapEditor;
	// The above code is declaring a variable named "d_mapEditor" of an unknown data
	// type. The code is also using the pound sign (#) to create a comment, which
	// means that the line "

	private boolean d_isCountriesAssigned;
	private MapReader d_mapReader;
	private MapDisplay d_displayMap;
	private Formatter d_formatter;

	private final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	/**
	 * Game Engine constructor
	 */
	public MapEditorController() {
		this.d_gameMap = new GameMap();
		this.d_players = new HashMap<>();
		this.d_isCountriesAssigned = false;
		this.d_mapReader = new MapReader();
		this.d_displayMap = new MapDisplay();
	}

	/**
	 * The function "loadMap" loads a map file, creates a game map object, and
	 * checks if the map is valid.
	 *
	 * @param p_filePath
	 *            The parameter `p_filePath` is a String that represents the full
	 *            path of the file from which the map will be loaded.
	 */
	public void loadMap(String p_filePath) {
		try {
			this.d_mapReader = new MapReader();
			this.d_gameMap = d_mapReader.loadMapFile(p_filePath);
			this.d_mapEditor = new MapEditor(this.d_gameMap);

			// Check if map is created and is valid
			if (this.d_gameMap.isMapCreated()) {
				if (!checkIfMapIsValid()) {
					this.d_gameMap = null;
					d_logger.log(Constants.GAME_ENGINE_CANNOT_LOAD_MAP);
				}
			} else {
				d_logger.log(Constants.GAME_ENGINE_MAP_NOT_CREATED);
			}

		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_CANNOT_LOAD_MAP);
		}
	}

	/**
	 * The function "showMap" checks if the map is valid and then formats and
	 * displays the map if it is.
	 *
	 */
	public void showMap() {
		if (checkIfMapIsValid()) {
			this.d_displayMap.displayMap(this.d_gameMap, this.d_players.size() > 0 && this.d_isCountriesAssigned);
		}
	}

	/**
	 * The function returns the game map.
	 *
	 * @return The method is returning an object of type GameMap.
	 *
	 */
	public GameMap getGameMap() {
		return this.d_gameMap;
	}

	/**
	 * The function `editMap` checks if a map file exists, loads it if it does,
	 * creates a new file if it doesn't, and returns a boolean indicating success or
	 * failure.
	 *
	 * @param p_mapFilePath
	 *            The parameter `p_mapFileName` is a String that represents the file
	 *            name or path of the map file that needs to be edited.
	 * @return The method is returning a boolean value.
	 *
	 */
	public boolean editMap(String p_mapFilePath) {
		// phase.doAcitin();
		// or phase.editMap
		// changePhase(new MapEditorPhase());

		File l_file = new File(p_mapFilePath);

		// If file with the input filename exists then load that map else create a new
		// map file
		if (l_file.exists()) {
			this.loadMap(p_mapFilePath);
			d_logger.log(Constants.GAME_ENGINE_MAP_EDIT_SUCCESS);
			return true;
		} else {
			String[] l_filePathSplit = p_mapFilePath.split("/");
			try {
				this.d_formatter = new Formatter();
				this.d_formatter.format(Constants.GAME_ENGINE_ERROR_MAP_DOES_NOT_EXIST, p_mapFilePath,
						l_filePathSplit[l_filePathSplit.length - 1]);
				d_logger.log(this.d_formatter.toString());
				boolean l_isFileCreated = new File(l_filePathSplit[l_filePathSplit.length - 1]).createNewFile();
				if (l_isFileCreated)
					this.loadMap(p_mapFilePath);
				return l_isFileCreated;
			} catch (IOException e) {
				this.d_formatter = new Formatter();
				this.d_formatter.format(Constants.GAME_ENGINE_ERROR_CREATE_MAP, p_mapFilePath, e.getMessage());
				d_logger.log(this.d_formatter.toString());
			} finally {
				this.d_formatter.close();
			}
		}
		return false;
	}

	/**
	 * The function adds a continent to a map editor and prints the output.
	 *
	 * @param p_continentName
	 *            The parameter "p_continentName" is a String that represents the
	 *            name of the continent that you want to add.
	 *
	 * @param p_bonus
	 *            The p_bonus parameter is an integer that represents the bonus for
	 *            each continent.
	 */
	public void addContinent(String p_continentName, int p_bonus) {
		try {
			String l_output = this.d_mapEditor.addContinent(p_continentName, p_bonus);
			d_logger.log(l_output);
		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function adds a country to a map editor and prints the output.
	 *
	 * @param p_countryId
	 *            The p_countryId parameter is an integer that represents the unique
	 *            identifier for the country being added.
	 * @param p_countryName
	 *            The parameter "p_countryName" is a String that represents the name
	 *            of the country that you want to add.
	 * @param p_continentName
	 *            The p_continentName parameter is an integer that represents the ID
	 *            of the continent to which the country belongs.
	 *
	 */
	public void addCountry(int p_countryId, String p_countryName, String p_continentName) {
		try {
			String l_output = this.d_mapEditor.addCountry(p_countryId, p_countryName, p_continentName);
			d_logger.log(l_output);
		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a continent from a map in a game editor.
	 *
	 * @param p_continentName
	 *            The parameter "p_continentId" is an integer that represents the ID
	 *            of the continent that needs to be removed.
	 *
	 */
	public void removeContinent(String p_continentName) {
		try {
			String l_output = this.d_mapEditor.removeContinent(p_continentName);
			d_logger.log(l_output);
		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a country from the game map and updates the player's
	 * ownership if necessary.
	 *
	 * @param p_countryId
	 *            The parameter `p_countryId` is an integer representing the ID of
	 *            the country that needs to be removed.
	 *
	 */
	public void removeCountry(int p_countryId) {
		try {
			Player l_player = this.d_gameMap.getCountries().get(p_countryId).getOwner();
			String l_output = this.d_mapEditor.removeCountry(p_countryId);
			if (this.d_isCountriesAssigned) {
				removeCountryFromPlayer(p_countryId, l_player);
			}
			d_logger.log(l_output);
		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function adds a neighbor country to a given country in a map editor.
	 *
	 * @param p_countryId
	 *            The p_countryId parameter represents the ID of the country to
	 *            which you want to add a neighbor.
	 * @param p_neighbourCountryId
	 *            The parameter "p_neighbourCountryId" represents the ID of the
	 *            neighboring country that you want to add to the country with the
	 *            ID "p_countryId".
	 *
	 */
	public void addNeighbor(int p_countryId, int p_neighbourCountryId) {
		try {
			String l_output = this.d_mapEditor.addNeighbor(p_countryId, p_neighbourCountryId);
			d_logger.log(l_output);
		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a neighbor country from a given country in a map editor.
	 *
	 * @param p_countryId
	 *            The p_countryId parameter represents the ID of the country from
	 *            which you want to remove a neighbor.
	 * @param p_neighborCountryId
	 *            The parameter "p_neighborCountryId" represents the ID of the
	 *            neighbor country that you want to remove from the country with ID
	 *            "p_countryId".
	 *
	 */
	public void removeNeighbor(int p_countryId, int p_neighborCountryId) {
		try {
			String l_output = this.d_mapEditor.removeNeighbor(p_countryId, p_neighborCountryId);
			d_logger.log(l_output);
		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a specific country from a player's list of owned
	 * countries.
	 *
	 * @param p_countryId
	 *            The ID of the country that needs to be removed from the player's
	 *            list of owned countries.
	 * @param p_player
	 *            The parameter "p_player" is of type Player and represents the
	 *            player from whom the country needs to be removed.
	 *
	 */
	private void removeCountryFromPlayer(int p_countryId, Player p_player) {
		try {
			List<Country> l_playerCountries = this.d_players.get(p_player.getName()).getCountriesOwned();
			l_playerCountries.removeIf(p_country -> p_country.getCountryId() == p_countryId);
			this.d_players.get(p_player.getName()).setCountriesOwned(l_playerCountries);
		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function checks if a game map is valid and prints a corresponding
	 * message.
	 *
	 * @return a boolean value indicate if the game map is valid
	 *
	 */
	public boolean checkIfMapIsValid() {
		try {
			// First check if the map is created, then if map is valid it will return true
			// else false
			if (this.d_gameMap.isMapCreated()) {
				if (MapValidator.IsMapCorrect(this.d_gameMap)) {
					d_logger.log(Constants.GAME_ENGINE_MAP_VALID);
					return true;
				} else {
					d_logger.log(Constants.GAME_ENGINE_MAP_INVALID);
					return false;
				}
			} else {
				d_logger.log(Constants.GAME_ENGINE_MAP_NOT_CREATED);
				return false;
			}
		} catch (Exception e) {
			d_logger.log(Constants.GAME_ENGINE_FAILED_TO_VALIDATE_MAP);
			return false;
		}
	}

	/**
	 * The function saves the game map to a file if it is valid, otherwise it prints
	 * an error message.
	 *
	 * @param p_mapFilePath
	 *            The full path of the file where the map will be saved.
	 *
	 */
	public void saveMap(String p_mapFilePath) {
		if (checkIfMapIsValid()) {
			this.d_gameMap.saveMap(p_mapFilePath);
		} else {
			d_logger.log(Constants.GAME_ENGINE_CANNOT_SAVE_MAP);
		}
	}

}
