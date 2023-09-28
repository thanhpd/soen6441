package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.w10.risk_game.models.GameMap;

public class MapReaderTest {

	MapReader mapReader = new MapReader();

	@Test
	public void testLoadMapFile() {
		// Provide a test map file (e.g., "testmap.map") in your resources folder
		String mapFilename = "europe.map";
		GameMap gameMap = mapReader.loadMapFile(mapFilename);

		assertNotNull(gameMap);
		// Add assertions to verify the correctness of the loaded GameMap object
	}
}
