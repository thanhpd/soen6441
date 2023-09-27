package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.GameMap;

public class MapValidatorTest {
	MapReader d_mapReader = new MapReader();

	@Test
	public void testMapCorrectness() {
		GameMap l_gameMap = d_mapReader.loadMapFile("europe.map");
		assertTrue(MapValidator.isMapCorrect(l_gameMap));
	}

	@Test
	public void testMapWithIsolatedCountries() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-connected.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

	@Test
	public void testMapWithContinents() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-connected.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

	@Test
	public void testMapEmpty() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-empty.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));

		GameMap l_gameMap2 = d_mapReader.loadMapFile("test-empty2.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap2));
	}

	@Test
	@Disabled
	public void testMapHasNonExistentContinent() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-continents-exist.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

	@Test
	public void testMapHasNonExistentCountry() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-countries-exist.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

	@Test
	public void testMapHasSelfReferencingNeighbor() {
		GameMap l_gameMap = d_mapReader.loadMapFile("test-self-referencing.map");
		assertFalse(MapValidator.isMapCorrect(l_gameMap));
	}

}
