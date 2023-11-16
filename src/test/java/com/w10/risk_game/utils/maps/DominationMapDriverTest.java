package com.w10.risk_game.utils.maps;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

import static org.junit.jupiter.api.Assertions.*;

import com.w10.risk_game.utils.Constants;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * The DominationMapDriverTest class is a JUnit test class that tests the
 * functionality of the DominationMapDriver class, which is responsible for
 * reading and parsing map files for a risk game.
 */
public class DominationMapDriverTest {
	private final DominationMapDriver d_mapDriver = new DominationMapDriver();

	/**
	 * The testLoadMapFile function tests if a map file can be successfully loaded
	 * and returns a non-null GameMap object.
	 */
	@Test
	public void testLoadMapFile() {
		String l_mapFilename = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		GameMap d_gameMap = d_mapDriver.loadMapFile(l_mapFilename);
		assertNotNull(d_gameMap);
		assertEquals(4, d_gameMap.getContinents().size());
		assertEquals(24, d_gameMap.getCountries().size());
	}

	/**
	 * The testReadCountries function tests the readCountries method by providing a
	 * sample input stream and a map of continents, and then asserts that the
	 * returned map of countries has the expected size.
	 */
	@Test
	public void testReadCountries() {
		// Prepare a sample input stream with country data
		String l_dummy_input = "1 Country1 1 1\n2 Country2 1 2\n3 Country3 2 3";
		InputStream l_inputStream = new ByteArrayInputStream(l_dummy_input.getBytes());
		Scanner l_input = new Scanner(l_inputStream);

		// Create a map of continents for testing
		Map<Integer, Continent> continents = Map.of(1, new Continent(1, "Continent1", 5), 2,
				new Continent(2, "Continent2", 3));

		Map<Integer, Country> l_countries = d_mapDriver.readCountries(l_input, continents);
		assertNotNull(l_countries);
		assertEquals(3, l_countries.size());
	}

	/**
	 * The testReadContinents function tests the readContinents method by providing
	 * a dummy input and asserting that the returned map of continents has the
	 * expected size.
	 */
	@Test
	public void testReadContinents() {
		String l_dummy_input = "Continent1 5\nContinent2 3";
		InputStream l_inputStream = new ByteArrayInputStream(l_dummy_input.getBytes());
		Scanner l_input = new Scanner(l_inputStream);

		Map<Integer, Continent> l_continents = d_mapDriver.readContinents(l_input);
		assertNotNull(l_continents);
		assertEquals(2, l_continents.size());
		// Add more assertions for the expected contents of the continents map here
	}
}
