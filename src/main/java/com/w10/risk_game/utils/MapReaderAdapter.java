package com.w10.risk_game.utils;

import com.w10.risk_game.models.ConquestGameMap;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import java.util.HashMap;
import java.util.Map;

public class MapReaderAdapter extends DominationMapReader {
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	private final ConquestMapReader d_conquestMapReader;

	public MapReaderAdapter(ConquestMapReader p_conquestMapReader) {
		d_conquestMapReader = p_conquestMapReader;
	}

	public GameMap loadMapFile(String p_mapFilePath) {
		return translate(d_conquestMapReader.loadMapFile(p_mapFilePath));
	}

	private GameMap translate(ConquestGameMap p_conquestGameMap) {
		GameMap l_gameMap = new GameMap();
		Map<Integer, Country> l_countries = new HashMap<>();
		Map<Integer, Continent> l_continents = new HashMap<>();

		for (Map.Entry<String, Continent> entry : p_conquestGameMap.getContinents().entrySet()) {
			l_continents.put(entry.getValue().getContinentId(), entry.getValue());
		}

		for (Map.Entry<String, Country> entry : p_conquestGameMap.getCountries().entrySet()) {
			l_countries.put(entry.getValue().getCountryId(), entry.getValue());
		}

		l_gameMap.addCountries(l_countries);
		l_gameMap.addContinents(l_continents);

		return l_gameMap;
	}
}
