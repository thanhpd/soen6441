package com.w10.risk_game.utils;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.*;
import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.models.*;
import com.w10.risk_game.models.strategies.HumanPlayerStrategy;
import com.w10.risk_game.models.strategies.RandomPlayerStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the SaveLoad class
 *
 * @version 1.0.0
 * @author Yajing Liu
 */
public class SaveLoadTest {
	SaveLoad d_save;
	SaveLoad d_load;
	GameEngine d_gameEngine1;
	GameEngine d_gameEngine2;

	/**
	 * This method is to set up the test environment
	 */
	@BeforeEach
	public void setup() {
		// Set up countries
		Country l_country1 = new Country(1, "England", 1, 3);
		Country l_country2 = new Country(2, "Scotland", 1, 4);
		Country l_country3 = new Country(3, "Canada", 2, 5);
		l_country1.addNeighbor(l_country2);
		l_country2.addNeighbor(l_country1);
		l_country1.addNeighbor(l_country3);
		l_country3.addNeighbor(l_country1);
		// Set up continents
		Continent l_continent1 = new Continent(1, "Europe", 5);
		l_continent1.addCountry(l_country1);
		l_continent1.addCountry(l_country2);
		Continent l_continent2 = new Continent(2, "North America", 5);
		l_continent2.addCountry(l_country3);
		// Set up map
		Map<Integer, Country> l_countries = new HashMap<>();
		l_countries.put(1, l_country1);
		l_countries.put(2, l_country2);
		l_countries.put(3, l_country3);
		Map<Integer, Continent> l_continents = new HashMap<>();
		l_continents.put(1, l_continent1);
		l_continents.put(2, l_continent2);
		GameMap l_gameMap = new GameMap();
		l_gameMap.addCountries(l_countries);
		l_gameMap.addContinents(l_continents);
		// Set player
		List<Country> l_countriesList1 = new ArrayList<>();
		l_countriesList1.add(l_country1);
		l_countriesList1.add(l_country2);
		List<Country> l_countriesList2 = new ArrayList<>();
		l_countriesList2.add(l_country3);
		List<CardType> l_cardTypes1 = new ArrayList<>();
		l_cardTypes1.add(CardType.BOMB);
		l_cardTypes1.add(CardType.AIRLIFT);
		List<CardType> l_cardTypes2 = new ArrayList<>();
		l_cardTypes2.add(CardType.BLOCKADE);
		l_cardTypes2.add(CardType.DIPLOMACY);
		Player l_player1 = new Player("Player1", l_countriesList1, new ArrayList<Order>(), 5, l_cardTypes1, false);
		l_country1.setOwner(l_player1);
		l_country2.setOwner(l_player1);
		// Set orders
		Order deploy1 = new Deploy(l_player1, l_country1.getCountryId(), 3);
		Order advance1 = new Advance(l_country1, l_country2, 3);
		Order bomb1 = new Bomb(l_player1, Integer.toString(l_country3.getCountryId()));
		Order blockade1 = new Blockade(l_player1, Integer.toString(l_country3.getCountryId()));
		Order airlift1 = new Airlift(l_player1, Integer.toString(l_country1.getCountryId()),
				Integer.toString(l_country2.getCountryId()), Integer.toString(2));
		Order negotiate1 = new Negotiate(l_player1, "Player2");
		List<Order> l_orders = new ArrayList<>();
		l_orders.add(deploy1);
		l_orders.add(advance1);
		l_orders.add(bomb1);
		l_orders.add(blockade1);
		l_orders.add(airlift1);
		l_orders.add(negotiate1);
		l_player1.setOrders(l_orders);
		// Set player
		Player l_player2 = new Player("Player2", l_countriesList2, new ArrayList<Order>(), 7, l_cardTypes2, false);
		l_country3.setOwner(l_player2);
		l_player1.setStrategy(new HumanPlayerStrategy(l_player1));
		l_player2.setStrategy(new RandomPlayerStrategy(l_player2));
		HashMap<String, Player> l_players = new HashMap<>();
		l_players.put(l_player1.getName(), l_player1);
		l_players.put(l_player2.getName(), l_player2);
		// Set GameEngine
		d_gameEngine1 = new GameEngine();
		d_gameEngine1.getMapEditorController().setGameMap(l_gameMap);
		d_gameEngine1.getGame().setPlayers(l_players);
		d_gameEngine1.getGame().setGameMap(l_gameMap);
		d_gameEngine1.getGame().setCurrentPlayer(l_player1);
		d_gameEngine1.getGame().setIsCountriesAssigned(true);
		List<Player> l_playerList = new ArrayList<>();
		l_playerList.add(l_player1);
		l_playerList.add(l_player2);
		d_gameEngine1.getGame().setPlayerList(l_playerList);
		d_gameEngine1.getGame().SetPlayerListForDiplomacy(l_playerList);
		d_gameEngine1.getGame().setCurrentPlayerIndex(0);
		// Set SaveLoad
		d_save = new SaveLoad(d_gameEngine1);
		d_gameEngine2 = new GameEngine();
		d_load = new SaveLoad(d_gameEngine2);
	}
	/**
	 * This method is to test the save and load function It tests whether the save
	 * data is the same as the load data
	 */
	@Test
	public void testSaveLoad() {
		// Save and get save data
		d_save.saveGame(Constants.SAVE_LOAD_TEST_FILE_NAME);
		GameMap l_gameMapForSave = d_save.getGameMapForSave();
		Map<Integer, Country> l_countriesForSave = l_gameMapForSave.getCountries();
		Map<Integer, Continent> l_continentsForSave = l_gameMapForSave.getContinents();
		HashMap<String, Player> l_playersForSave = d_save.getPlayersForSave();
		// Load and get load data
		d_load.loadGame(Constants.SAVE_LOAD_TEST_FILE_NAME);
		GameMap l_gameMapForLoad = d_load.getGameMapForLoad();
		Map<Integer, Country> l_countriesForLoad = l_gameMapForLoad.getCountries();
		Map<Integer, Continent> l_continentsForLoad = l_gameMapForLoad.getContinents();
		HashMap<String, Player> l_playersForLoad = d_load.getPlayersForLoad();
		// Compare GamePlayController data
		assertEquals(d_gameEngine1.getGame().getCurrentPlayer().getName(),
				d_gameEngine2.getGame().getCurrentPlayer().getName());
		assertEquals(d_gameEngine1.getGame().getCurrentPlayerIndex(), d_gameEngine2.getGame().getCurrentPlayerIndex());
		assertEquals(d_gameEngine1.getGame().getIsCountriesAssigned(),
				d_gameEngine2.getGame().getIsCountriesAssigned());
		assertEquals(d_gameEngine1.getGame().getPlayerList().size(), d_gameEngine2.getGame().getPlayerList().size());
		assertEquals(d_gameEngine1.getGame().GetPlayerListForDiplomacy().size(),
				d_gameEngine2.getGame().GetPlayerListForDiplomacy().size());
		for (int i = 0; i < d_gameEngine1.getGame().getPlayerList().size(); i++) {
			assertEquals(d_gameEngine1.getGame().getPlayerList().get(i).getName(),
					d_gameEngine2.getGame().getPlayerList().get(i).getName());
		}
		for (int i = 0; i < d_gameEngine1.getGame().GetPlayerListForDiplomacy().size(); i++) {
			assertEquals(d_gameEngine1.getGame().GetPlayerListForDiplomacy().get(i).getName(),
					d_gameEngine2.getGame().GetPlayerListForDiplomacy().get(i).getName());
		}
		// Compare the size of the data
		assertEquals(l_gameMapForSave.getCountries().size(), l_gameMapForLoad.getCountries().size());
		assertEquals(l_gameMapForSave.getContinents().size(), l_gameMapForLoad.getContinents().size());
		assertEquals(l_playersForSave.size(), l_playersForLoad.size());
		// Compare Country of save data and load data
		for (Map.Entry<Integer, Country> entry : l_countriesForSave.entrySet()) {
			assertEquals(entry.getValue().getCountryId(), l_countriesForLoad.get(entry.getKey()).getCountryId());
			assertEquals(entry.getValue().getCountryName(), l_countriesForLoad.get(entry.getKey()).getCountryName());
			assertEquals(entry.getValue().getContinentId(), l_countriesForLoad.get(entry.getKey()).getContinentId());
			assertEquals(entry.getValue().getArmyCount(), l_countriesForLoad.get(entry.getKey()).getArmyCount());
			assertEquals(entry.getValue().getOwner().getName(),
					l_countriesForLoad.get(entry.getKey()).getOwner().getName());
			assertEquals(entry.getValue().getNeighbors().size(),
					l_countriesForLoad.get(entry.getKey()).getNeighbors().size());
			for (Map.Entry<Integer, Country> entry2 : entry.getValue().getNeighbors().entrySet()) {
				assertEquals(entry2.getValue().getCountryName(),
						l_countriesForLoad.get(entry.getKey()).getNeighbors().get(entry2.getKey()).getCountryName());
			}
		}
		// Compare Continent of save data and load data
		for (Map.Entry<Integer, Continent> entry : l_continentsForSave.entrySet()) {
			assertEquals(entry.getValue().getContinentId(), l_continentsForLoad.get(entry.getKey()).getContinentId());
			assertEquals(entry.getValue().getContinentName(),
					l_continentsForLoad.get(entry.getKey()).getContinentName());
			assertEquals(entry.getValue().getBonus(), l_continentsForLoad.get(entry.getKey()).getBonus());
			assertEquals(entry.getValue().getCountries().size(),
					l_continentsForLoad.get(entry.getKey()).getCountries().size());
			for (int i = 0; i < entry.getValue().getCountries().size(); i++) {
				assertEquals(entry.getValue().getCountries().get(i).getCountryName(),
						l_continentsForLoad.get(entry.getKey()).getCountries().get(i).getCountryName());
			}
		}
		// Compare Player of save data and load data
		for (Map.Entry<String, Player> entry : l_playersForSave.entrySet()) {
			assertEquals(entry.getValue().getName(), l_playersForLoad.get(entry.getKey()).getName());
			assertEquals(entry.getValue().getCountriesOwned().size(),
					l_playersForLoad.get(entry.getKey()).getCountriesOwned().size());
			for (int i = 0; i < entry.getValue().getCountriesOwned().size(); i++) {
				assertEquals(entry.getValue().getCountriesOwned().get(i).getCountryName(),
						l_playersForLoad.get(entry.getKey()).getCountriesOwned().get(i).getCountryName());
			}
			assertEquals(entry.getValue().getOrders().size(), l_playersForLoad.get(entry.getKey()).getOrders().size());
			for (int i = 0; i < entry.getValue().getOrders().size(); i++) {
				assertEquals(entry.getValue().getOrders().get(i).getClass(),
						l_playersForLoad.get(entry.getKey()).getOrders().get(i).getClass());
			}
			assertEquals(entry.getValue().getLeftoverArmies(),
					l_playersForLoad.get(entry.getKey()).getLeftoverArmies());
			assertEquals(entry.getValue().getPlayerCards().size(),
					l_playersForLoad.get(entry.getKey()).getPlayerCards().size());
			for (int i = 0; i < entry.getValue().getPlayerCards().size(); i++) {
				assertEquals(entry.getValue().getPlayerCards().get(i),
						l_playersForLoad.get(entry.getKey()).getPlayerCards().get(i));
			}
			assertEquals(entry.getValue().getStrategy().getClass(),
					l_playersForLoad.get(entry.getKey()).getStrategy().getClass());
			assertEquals(entry.getValue().getHasCommitted(), l_playersForLoad.get(entry.getKey()).getHasCommitted());
		}
	}
}
