package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The DataStorage class is responsible for storing data for saving and loading
 * the game.
 *
 * @author Yajing Liu
 */
public class DataStorage implements Serializable {
	// GamePlayController
	public String d_currentPlayerName;
	public int d_currentPlayerIndex;
	public ArrayList<String> d_gamePlayControllerPlayerNames = new ArrayList<String>();
	public boolean d_isCountriesAssigned;
	// Map
	// Country
	public ArrayList<Integer> d_countryIds = new ArrayList<Integer>();
	public ArrayList<String> d_countryNames = new ArrayList<String>();
	public ArrayList<Integer> d_belongContinentIds = new ArrayList<Integer>();
	public ArrayList<Integer> d_armyCounts = new ArrayList<Integer>();
	public ArrayList<String> d_ownerNames = new ArrayList<String>();
	public ArrayList<Integer> d_numberOfNeighbors = new ArrayList<Integer>();
	public ArrayList<Integer> d_neighborIds = new ArrayList<Integer>();
	// Continent
	public ArrayList<Integer> d_continentIds = new ArrayList<Integer>();
	public ArrayList<String> d_continentNames = new ArrayList<String>();
	public ArrayList<Integer> d_continentBonus = new ArrayList<Integer>();
	// Player
	public ArrayList<String> d_playerNames = new ArrayList<String>();
	public ArrayList<Integer> d_numberOfCountries = new ArrayList<Integer>();
	public ArrayList<Integer> d_ownedCountryIds = new ArrayList<Integer>();
	public ArrayList<Integer> d_numberOfOrders = new ArrayList<Integer>();
	public ArrayList<Integer> d_playerLeftoverArmies = new ArrayList<Integer>();
	public ArrayList<Integer> d_numberOfCards = new ArrayList<Integer>();
	public ArrayList<Boolean> d_playerHasCommitted = new ArrayList<Boolean>();
	public ArrayList<String> d_playerStrategyNames = new ArrayList<String>();
	public ArrayList<Integer> d_playerStrongestCountryOwnedIds = new ArrayList<Integer>();
	// Order
	public ArrayList<String> d_orderTypes = new ArrayList<String>();
	// Deploy
	public ArrayList<Integer> d_deployCountryIds = new ArrayList<Integer>();
	public ArrayList<Integer> d_deployNums = new ArrayList<Integer>();
	// Advance
	public ArrayList<String> d_advanceCountryFromIds = new ArrayList<String>();
	public ArrayList<String> d_advanceCountryToIds = new ArrayList<String>();
	public ArrayList<Integer> d_advanceNums = new ArrayList<Integer>();
	// Bomb
	public ArrayList<String> d_bombCountryIds = new ArrayList<String>();
	// Blockade
	public ArrayList<String> d_blockadeCountryIds = new ArrayList<String>();
	// Airlift
	public ArrayList<String> d_airliftCountryFromIds = new ArrayList<String>();
	public ArrayList<String> d_airliftCountryToIds = new ArrayList<String>();
	public ArrayList<String> d_airliftNums = new ArrayList<String>();
	// Negotiate
	public ArrayList<String> d_negotiatePlayerName = new ArrayList<String>();
	// Card
	public ArrayList<String> d_cards = new ArrayList<String>();
}
