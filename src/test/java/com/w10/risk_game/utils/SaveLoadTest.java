package com.w10.risk_game.utils;

import com.w10.risk_game.commands.*;
import com.w10.risk_game.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the SaveLoad class
 * @version 1.0.0
 * @author Yajing Liu
 */
public class SaveLoadTest {
	SaveLoad d_saveLoad;

	/**
	 * This method is to set up the test environment
	 */
	@BeforeEach
	public void setup(){
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
		List <Country> l_countriesList2 = new ArrayList<>();
		l_countriesList2.add(l_country3);
		List<CardType> l_cardTypes1 = new ArrayList<>();
		l_cardTypes1.add(CardType.BOMB);
		l_cardTypes1.add(CardType.AIRLIFT);
		List<CardType> l_cardTypes2 = new ArrayList<>();
		l_cardTypes2.add(CardType.BLOCKADE);
		l_cardTypes2.add(CardType.DIPLOMACY);
		Player l_player1 = new Player("Player1", l_countriesList1, new ArrayList<Order>(),5,l_cardTypes1, false);
		l_country1.setOwner(l_player1);
		l_country2.setOwner(l_player1);
		// Set orders
		Order deploy1 = new Deploy(l_player1, l_country1.getCountryId(), 3);
		Order advance1 = new Advance(l_country1, l_country2, 3);
		Order bomb1 = new Bomb(l_player1, Integer.toString(l_country3.getCountryId()));
		Order blockade1 = new Blockade(l_player1, Integer.toString(l_country3.getCountryId()));
		Order airlift1 = new Airlift(l_player1, Integer.toString(l_country1.getCountryId()), Integer.toString(l_country2.getCountryId()), Integer.toString(2));
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
		Player l_player2 = new Player("Player2", l_countriesList2, new ArrayList<Order>(),7,l_cardTypes2, false);
		l_country3.setOwner(l_player2);
		List<Player> l_players = new ArrayList<>();
		l_players.add(l_player1);
		l_players.add(l_player2);
		d_saveLoad = new SaveLoad(l_gameMap, l_players);
	}
	/**
	 * This method is to test the save and load function
	 * It tests whether the save data is the same as the load data
	 */
	@Test
	public void testSaveLoad(){
		// Save and get save data
		d_saveLoad.saveGame("testSave");
		GameMap l_gameMapForSave = d_saveLoad.getGameMapForSave();
		Map<Integer,Country> l_countriesForSave = l_gameMapForSave.getCountries();
		Map<Integer,Continent> l_continentsForSave = l_gameMapForSave.getContinents();
		List<Player> l_playerListForSave = d_saveLoad.getPlayerListForSave();
		// Load and get load data
		d_saveLoad.loadGame("testSave");
		GameMap l_gameMapForLoad = d_saveLoad.getGameMapForLoad();
		Map<Integer,Country> l_countriesForLoad = l_gameMapForLoad.getCountries();
		Map<Integer,Continent> l_continentsForLoad = l_gameMapForLoad.getContinents();
		List<Player> l_playerListForLoad = d_saveLoad.getPlayerListForLoad();
		// Compare the size of the data
		assertEquals(l_gameMapForSave.getCountries().size(), l_gameMapForLoad.getCountries().size());
		assertEquals(l_gameMapForSave.getContinents().size(), l_gameMapForLoad.getContinents().size());
		assertEquals(l_playerListForSave.size(), l_playerListForLoad.size());
		// Compare Country of save data and load data
		for (Map.Entry<Integer, Country> entry : l_countriesForSave.entrySet()){
			assertEquals(entry.getValue().getCountryId(), l_countriesForLoad.get(entry.getKey()).getCountryId());
			assertEquals(entry.getValue().getCountryName(), l_countriesForLoad.get(entry.getKey()).getCountryName());
			assertEquals(entry.getValue().getContinentId(), l_countriesForLoad.get(entry.getKey()).getContinentId());
			assertEquals(entry.getValue().getArmyCount(), l_countriesForLoad.get(entry.getKey()).getArmyCount());
			assertEquals(entry.getValue().getOwner().getName(), l_countriesForLoad.get(entry.getKey()).getOwner().getName());
			assertEquals(entry.getValue().getNeighbors().size(), l_countriesForLoad.get(entry.getKey()).getNeighbors().size());
			for (Map.Entry<Integer, Country> entry2 : entry.getValue().getNeighbors().entrySet()){
				assertEquals(entry2.getValue().getCountryName(), l_countriesForLoad.get(entry.getKey()).getNeighbors().get(entry2.getKey()).getCountryName());
			}
		}
		// Compare Continent of save data and load data
		for (Map.Entry<Integer, Continent> entry : l_continentsForSave.entrySet()){
			assertEquals(entry.getValue().getContinentId(), l_continentsForLoad.get(entry.getKey()).getContinentId());
			assertEquals(entry.getValue().getContinentName(), l_continentsForLoad.get(entry.getKey()).getContinentName());
			assertEquals(entry.getValue().getBonus(), l_continentsForLoad.get(entry.getKey()).getBonus());
			assertEquals(entry.getValue().getCountries().size(), l_continentsForLoad.get(entry.getKey()).getCountries().size());
			for (int i = 0; i < entry.getValue().getCountries().size(); i++){
				assertEquals(entry.getValue().getCountries().get(i).getCountryName(), l_continentsForLoad.get(entry.getKey()).getCountries().get(i).getCountryName());
			}
		}
		// Compare Player of save data and load data
		for (int i = 0; i < l_playerListForSave.size(); i++){
			assertEquals(l_playerListForSave.get(i).getName(), l_playerListForLoad.get(i).getName());
			assertEquals(l_playerListForSave.get(i).getCountriesOwned().size(), l_playerListForLoad.get(i).getCountriesOwned().size());
			for (int j = 0; j < l_playerListForSave.get(i).getCountriesOwned().size(); j++){
				assertEquals(l_playerListForSave.get(i).getCountriesOwned().get(j).getCountryName(), l_playerListForLoad.get(i).getCountriesOwned().get(j).getCountryName());
			}
			assertEquals(l_playerListForSave.get(i).getOrders().size(), l_playerListForLoad.get(i).getOrders().size());
			for (int j = 0; j < l_playerListForSave.get(i).getOrders().size(); j++){
				assertEquals(l_playerListForSave.get(i).getOrders().get(j).getClass(), l_playerListForLoad.get(i).getOrders().get(j).getClass());
			}
			assertEquals(l_playerListForSave.get(i).getLeftoverArmies(), l_playerListForLoad.get(i).getLeftoverArmies());
			assertEquals(l_playerListForSave.get(i).getPlayerCards().size(), l_playerListForLoad.get(i).getPlayerCards().size());
			for (int j = 0; j < l_playerListForSave.get(i).getPlayerCards().size(); j++){
				assertEquals(l_playerListForSave.get(i).getPlayerCards().get(j), l_playerListForLoad.get(i).getPlayerCards().get(j));
			}
			assertEquals(l_playerListForSave.get(i).getHasCommitted(), l_playerListForLoad.get(i).getHasCommitted());
		}
	}
}
