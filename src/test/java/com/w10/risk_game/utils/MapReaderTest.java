package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

public class MapReaderTest {

	MapReader d_mapReader = new MapReader();

	@Test
	public void testLoadMapFile() {
		String l_mapFilename = "europe.map";
		GameMap d_gameMap = d_mapReader.loadMapFile(l_mapFilename);
		assertNotNull(d_gameMap);
	}

	@Test
	public void testReadCountries() {
		// Prepare a sample input stream with country data
		String l_dummy_input = "1 Country1 1 1\n2 Country2 1 2\n3 Country3 2 3";
		InputStream l_input_stream = new ByteArrayInputStream(l_dummy_input.getBytes());
		Scanner l_input = new Scanner(l_input_stream);

		// Create a map of continents for testing
		Map<Integer, Continent> continents = Map.of(
				1, new Continent(1, "Continent1", 5),
				2, new Continent(2, "Continent2", 3));

		Map<Integer, Country> countries = d_mapReader.readCountries(l_input, continents);
		assertNotNull(countries);
		assertEquals(3, countries.size());
	}

	@Test
	public void testReadContinents() {
		String l_dummy_input = "Continent1 5\nContinent2 3";
		InputStream l_input_stream = new ByteArrayInputStream(l_dummy_input.getBytes());
		Scanner l_input = new Scanner(l_input_stream);

		Map<Integer, Continent> continents = d_mapReader.readContinents(l_input);
		assertNotNull(continents);
		assertEquals(2, continents.size());
		// Add more assertions for the expected contents of the continents map here
	}
}
