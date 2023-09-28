package com.w10.risk_game.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

public class MapReaderTest {

	MapReader mapReader = new MapReader();

	@Test
	public void testLoadMapFile() {
		String mapFilename = "europe.map";
		GameMap gameMap = mapReader.loadMapFile(mapFilename);
		assertNotNull(gameMap);
	}

}
