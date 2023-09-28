package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.w10.risk_game.models.GameMap;

public class MapReaderTest {

	MapReader d_mapReader = new MapReader();

	@Test
	public void testLoadMapFile() {
		String l_mapFilename = "europe.map";
		GameMap d_gameMap = d_mapReader.loadMapFile(l_mapFilename);
		assertNotNull(d_gameMap);
	}

}
