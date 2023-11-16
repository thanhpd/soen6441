package com.w10.risk_game.utils.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.w10.risk_game.models.ConquestGameMap;
import com.w10.risk_game.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The ConquestMapDriverTest class tests the functionality of the
 * ConquestMapDriver class
 */
public class ConquestMapDriverTest {
	private ConquestMapDriver d_mapDriver;

	/**
	 * The setUp() function is used to initialize a ConquestMapDriver object before
	 * each test case.
	 */
	@BeforeEach
	public void setUp() {
		d_mapDriver = new ConquestMapDriver();
	}

	/**
	 * The testLoadMapFile function tests the loading of a map file and checks if
	 * the game map object is not null, and if the number of continents and
	 * countries in the map match the expected values.
	 */
	@Test
	public void testLoadMapFile() {
		String l_mapFilename = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe-conquest.map";
		ConquestGameMap d_gameMap = d_mapDriver.loadMapFile(l_mapFilename);
		assertNotNull(d_gameMap);
		assertEquals(7, d_gameMap.getContinents().size());
		assertEquals(50, d_gameMap.getCountries().size());
	}
}
