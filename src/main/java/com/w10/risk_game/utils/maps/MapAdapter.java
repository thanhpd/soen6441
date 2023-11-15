package com.w10.risk_game.utils.maps;

import com.w10.risk_game.models.ConquestGameMap;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import java.util.HashMap;
import java.util.Map;

public class MapAdapter extends DominationMapDriver {
  private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
  private final ConquestMapDriver d_conquestMapReader;

  public MapAdapter(ConquestMapDriver p_conquestMapReader) {
    d_conquestMapReader = p_conquestMapReader;
  }

  public GameMap loadMapFile(String p_mapFilePath) {
    return translateToDominationGameMap(d_conquestMapReader.loadMapFile(p_mapFilePath));
  }

  @Override
  public void saveMap(String p_mapFilePath, GameMap p_gameMap) {
    d_conquestMapReader.saveMap(p_mapFilePath, translateToConquestGameMap(p_gameMap));
  }

  private GameMap translateToDominationGameMap(ConquestGameMap p_conquestGameMap) {
    GameMap l_gameMap = new GameMap();
    Map<Integer, Country> l_countries = new HashMap<>();
    Map<Integer, Continent> l_continents = new HashMap<>();

    for (Continent continent : p_conquestGameMap.getContinents().values()) {
      l_continents.put(continent.getContinentId(), continent);
    }

    for (Country country : p_conquestGameMap.getCountries().values()) {
      l_countries.put(country.getCountryId(), country);
    }

    l_gameMap.addCountries(l_countries);
    l_gameMap.addContinents(l_continents);

    return l_gameMap;
  }

  private ConquestGameMap translateToConquestGameMap(GameMap p_gameMap) {
    ConquestGameMap l_gameMap = new ConquestGameMap();
    Map<String, Country> l_countries = new HashMap<>();
    Map<String, Continent> l_continents = new HashMap<>();

    for (Continent continent : p_gameMap.getContinents().values()) {
      l_continents.put(continent.getContinentName(), continent);
    }

    for (Country country : p_gameMap.getCountries().values()) {
      l_countries.put(country.getCountryName(), country);
    }

    l_gameMap.addCountries(l_countries);
    l_gameMap.addContinents(l_continents);

    return l_gameMap;
  }
}
