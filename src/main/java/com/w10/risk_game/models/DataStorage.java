package com.w10.risk_game.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The DataStorage class is responsible for storing data for saving and loading
 * the game.
 * @author Yajing Liu
 */
public class DataStorage implements Serializable {
	// Phase
	// TODO: Phase Data;
	// Map
	// TODO: Map Data;
	// Player
	public ArrayList<String> d_playerNames = new ArrayList<String>();
	public ArrayList<Integer> d_numberOfCountries = new ArrayList<Integer>();
	public ArrayList<Integer> d_numberOfOrders = new ArrayList<Integer>();
	public ArrayList<Integer> d_playerLeftoverArmies = new ArrayList<Integer>();
	public ArrayList<Integer> d_numberOfCards = new ArrayList<Integer>();
	public ArrayList<Boolean> d_playerHasCommitted = new ArrayList<Boolean>();
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
	// TODO: loggers
}
