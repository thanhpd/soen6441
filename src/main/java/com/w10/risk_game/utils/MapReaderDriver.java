package com.w10.risk_game.utils;

import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MapReaderDriver {
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	private MapReaderDriver() {
		throw new IllegalStateException("Utility class");
	}

	public static GameMap readMapFile(String p_mapFilePath) {
		boolean l_useDominationMapReader = false;
		boolean l_useConquestMapReader = false;

		try {
			FileReader l_reader = new FileReader(p_mapFilePath);

			Scanner l_scanner = new Scanner(l_reader);
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

		} catch (FileNotFoundException e) {
			Logger.log(Constants.MAP_READER_FILE_NOT_FOUND);
		}

		if (l_useDominationMapReader) {
			DominationMapReader l_dominationMapReader = new DominationMapReader();
			return l_dominationMapReader.loadMapFile(p_mapFilePath);
		}

		if (l_useConquestMapReader) {
			MapReaderAdapter l_mapReaderAdapter = new MapReaderAdapter(new ConquestMapReader());
			return l_mapReaderAdapter.loadMapFile(p_mapFilePath);
		}

		return null;
	}
}
