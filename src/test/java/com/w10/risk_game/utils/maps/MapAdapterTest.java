package com.w10.risk_game.utils.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The MapAdapterTest class tests the functionality of the MapAdapter class by
 * loading conquest and domination map files and verifying the expected number
 * of continents and countries.
 */
public class MapAdapterTest {
	private MapAdapter d_mapAdapter;

	/**
	 * The setUp() function initializes a MapAdapter object with a ConquestMapDriver
	 * object.
	 */
	@BeforeEach
	public void setUp() {
		d_mapAdapter = new MapAdapter(new ConquestMapDriver());
	}

	/**
	 * The function tests the loading of a Conquest map file and checks if the
	 * loaded map has the expected number of continents and countries.
	 */
	@Test
	public void testLoadConquestMapFile() {
		String l_mapFilename = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe-conquest.map";
		GameMap d_gameMap = d_mapAdapter.loadMapFile(l_mapFilename);
		assertNotNull(d_gameMap);
		assertEquals(7, d_gameMap.getContinents().size());
		assertEquals(50, d_gameMap.getCountries().size());
	}

	/**
	 * The function tests the loading of a Domination map file and checks if the
	 * loaded map has the expected number of continents and countries.
	 */
	@Test
	public void testLoadDominationMapFile() {
		String l_mapFilename = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		GameMap d_gameMap = d_mapAdapter.loadMapFile(l_mapFilename);
		assertNotNull(d_gameMap);
		// Expected these to be 0 because the adapter is supposed to load conquest map
		// format
		assertEquals(0, d_gameMap.getContinents().size());
		assertEquals(0, d_gameMap.getCountries().size());
	}
}
