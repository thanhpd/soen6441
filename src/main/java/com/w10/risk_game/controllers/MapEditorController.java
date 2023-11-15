package com.w10.risk_game.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Scanner;

import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import com.w10.risk_game.utils.maps.ConquestMapDriver;
import com.w10.risk_game.utils.maps.DominationMapDriver;
import com.w10.risk_game.utils.maps.MapAdapter;
import com.w10.risk_game.utils.maps.MapDisplay;
import com.w10.risk_game.utils.maps.MapEditor;
import com.w10.risk_game.utils.maps.MapValidator;

/**
 * The MapEditorController class is responsible for managing the game map,
 * players, issuing orders, executing orders and their interactions in a game.
 *
 * @author Omnia Alam
 */
public class MapEditorController {
	private GameMap d_gameMap;
	private MapEditor d_mapEditor;
	private MapDisplay d_displayMap;

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * Game Engine constructor
	 */
	public MapEditorController() {
		this.d_gameMap = new GameMap();
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
			this.d_gameMap = readMapFile(p_filePath);
			this.d_mapEditor = new MapEditor(this.d_gameMap);

		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_CANNOT_LOAD_MAP);
		}
	}

	/**
	 * The function displays the game map, showing armies if specified.
	 *
	 * @param p_showArmies
	 *            A boolean value indicating whether or not to display the armies on
	 *            the map. If p_showArmies is true, the armies will be displayed on
	 *            the map. If p_showArmies is false, the armies will not be
	 *            displayed on the map.
	 */
	public void showMap(boolean p_showArmies) {
		if (checkIfMapIsValid()) {
			this.d_displayMap.displayMap(this.d_gameMap, p_showArmies);
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
		File l_file = new File(p_mapFilePath);

		// If the file with the given filename exists, load the existing map
		if (l_file.exists()) {
			this.loadMap(p_mapFilePath);
			Logger.log(Constants.GAME_ENGINE_MAP_EDIT_SUCCESS);
			return true; // Indicates successful map edit by loading the existing map
		} else {
			// If the file doesn't exist, try to create a new map file
			String[] l_filePathSplit = p_mapFilePath.split("/");
			try {
				Logger.log(MessageFormat.format(Constants.GAME_ENGINE_ERROR_MAP_DOES_NOT_EXIST, p_mapFilePath,
						l_filePathSplit[l_filePathSplit.length - 1]));

				// Create a new file with the given file path
				boolean l_isFileCreated = new File(l_filePathSplit[l_filePathSplit.length - 1]).createNewFile();

				// If the file is created successfully, load the new map
				if (l_isFileCreated) {
					this.loadMap(p_mapFilePath);
				}
				return l_isFileCreated; // Returns true if a new file is created, else false
			} catch (IOException e) {
				// Log an error message if an exception occurs during file creation
				Logger.log(MessageFormat.format(Constants.GAME_ENGINE_ERROR_CREATE_MAP, p_mapFilePath, e.getMessage()));
			}
		}
		return false; // Indicates an unsuccessful map edit
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
			Logger.log(l_output);
		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
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
			Logger.log(l_output);
		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
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
			Logger.log(l_output);
		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
		}
	}

	/**
	 * The function removes a country from the map editor and logs the output or an
	 * error message if an exception occurs.
	 *
	 * @param p_countryId
	 *            The parameter "p_countryId" is an integer that represents the ID
	 *            of the country that needs to be removed.
	 */
	public void removeCountry(int p_countryId) {
		try {
			String l_output = this.d_mapEditor.removeCountry(p_countryId);
			Logger.log(l_output);
		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
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
			Logger.log(l_output);
		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
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
			Logger.log(l_output);
		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_FAILED_TO_EDIT_MAP);
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
			// Check if the map is created
			if (this.d_gameMap.isMapCreated()) {
				// If the map is created, validate its correctness
				if (MapValidator.IsMapCorrect(this.d_gameMap)) {
					Logger.log(Constants.GAME_ENGINE_MAP_VALID);
					return true; // Indicates a valid map
				} else {
					Logger.log(Constants.GAME_ENGINE_MAP_INVALID);
					return false; // Indicates an invalid map
				}
			} else {
				Logger.log(Constants.GAME_ENGINE_MAP_NOT_CREATED);
				return false; // Indicates that the map is not created
			}
		} catch (Exception e) {
			Logger.log(Constants.GAME_ENGINE_FAILED_TO_VALIDATE_MAP);
			return false; // Indicates an exception occurred while validating the map
		}
	}

	/**
	 * The function saves the game map to a file if it is valid, otherwise it prints
	 * an error message.
	 *
	 * @param p_mapFilePath
	 *            The full path of the file where the map will be saved.
	 * @param p_mapType
	 *            The type of the map to save. 1 for Domination, 2 for Conquest.
	 *
	 */
	public void saveMap(String p_mapFilePath, String p_mapType) {
		if (checkIfMapIsValid()) {
			if (p_mapType == null || p_mapType.equals(Constants.MAP_FORMAT_DOMINATION)) {
				DominationMapDriver l_dominationMapReader = new DominationMapDriver();
				l_dominationMapReader.saveMap(p_mapFilePath, d_gameMap);
			} else if (p_mapType.equals(Constants.MAP_FORMAT_CONQUEST)) {
				MapAdapter l_mapReaderAdapter = new MapAdapter(new ConquestMapDriver());
				l_mapReaderAdapter.saveMap(p_mapFilePath, d_gameMap);
			}
		} else {
			Logger.log(Constants.GAME_ENGINE_CANNOT_SAVE_MAP);
		}
	}

	private GameMap readMapFile(String p_mapFilePath) {
		boolean l_useDominationMapReader = false;
		boolean l_useConquestMapReader = false;

		try {
			FileReader l_reader = new FileReader(p_mapFilePath);

			try (Scanner l_scanner = new Scanner(l_reader)) {
				String l_line;

				// read until continents
				while (l_scanner.hasNextLine()) {
					l_line = l_scanner.nextLine();
					if (l_line.equals(Constants.DOMINATION_MAP_READER_CONTINENTS)) {
						l_useDominationMapReader = true;
						break;
					}

					if (l_line.equals(Constants.CONQUEST_MAP_READER_CONTINENTS)) {
						l_useConquestMapReader = true;
						break;
					}
				}
			}

		} catch (FileNotFoundException e) {
			Logger.log(Constants.MAP_READER_FILE_NOT_FOUND);
		}

		if (l_useDominationMapReader) {
			DominationMapDriver l_dominationMapReader = new DominationMapDriver();
			return l_dominationMapReader.loadMapFile(p_mapFilePath);
		}

		if (l_useConquestMapReader) {
			MapAdapter l_mapReaderAdapter = new MapAdapter(new ConquestMapDriver());
			return l_mapReaderAdapter.loadMapFile(p_mapFilePath);
		}

		return null;
	}
}
