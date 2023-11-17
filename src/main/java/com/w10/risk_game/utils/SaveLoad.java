package com.w10.risk_game.utils;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.*;
import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.models.*;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The SaveLoad class is responsible for saving and loading the game.
 * @author Yajing
 */
public class SaveLoad {
	GameEngine d_gameEngine;
	MapEditorController d_mapEditorController;
	GamePlayController d_gamePlayController;
	DataStorage d_data;
	List<Player> d_playerList;
	Map<Integer, Country> d_countries;
	GameMap d_gameMap;
	/**
	 * Constructor for SaveLoad class.
	 * @param p_gameEngine The parameter `p_gameEngine` is a GameEngine object.
	 */
	public SaveLoad(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
		this.d_mapEditorController = p_gameEngine.getMapEditorController();
		this.d_gamePlayController = p_gameEngine.getGame();
		// TODO: Get Global PlayerList
		this.d_playerList = p_gameEngine.getGame().GetPlayerListForDiplomacy();
		// TODO: Get Global GameMap
		this.d_gameMap = p_gameEngine.getMapEditorController().getGameMap();
	}

	/**
	 * The function saveGame() saves the game.
	 */
	public void saveGame() {
		// TODO: Save File Path
		try{
			ObjectOutputStream l_out = new ObjectOutputStream(new FileOutputStream("save.dat"));
			d_data = new DataStorage();
			// Save Country Data
			for (Map.Entry<Integer,Country> entry : d_gameMap.getCountries().entrySet()) {
				d_data.d_countryIds.add(entry.getKey());
				d_data.d_countryNames.add(entry.getValue().getCountryName());
				d_data.d_belongContinentIds.add(entry.getValue().getContinentId());
				d_data.d_armyCounts.add(entry.getValue().getArmyCount());
				d_data.d_ownerNames.add(entry.getValue().getOwner().getName());
				d_data.d_numberOfNeighbors.add(entry.getValue().getNeighbors().size());
				for (Map.Entry<Integer, Country> neighbor: entry.getValue().getNeighbors().entrySet()){
					d_data.d_neighborIds.add(neighbor.getValue().getCountryId());
				}
			}
			// Save Continent Data
			for (Map.Entry<Integer,Continent> entry : d_gameMap.getContinents().entrySet()) {
				d_data.d_continentIds.add(entry.getKey());
				d_data.d_continentNames.add(entry.getValue().getContinentName());
				d_data.d_continentBonus.add(entry.getValue().getBonus());
			}
			// Save Player Data
			for (Player player : d_playerList) {
				d_data.d_playerNames.add(player.getName());
				d_data.d_playerLeftoverArmies.add(player.getLeftoverArmies());
				d_data.d_playerHasCommitted.add(player.getHasCommitted());
				// Save Order Data
				int numberOfOrders = player.getOrders().size();
				d_data.d_numberOfOrders.add(numberOfOrders);
				for (Order order : player.getOrders()) {
					saveOrder(order);
				}
				// Save Country Data
				d_data.d_numberOfCountries.add(player.getCountriesOwned().size());
				for (Country country : player.getCountriesOwned()) {
					d_data.d_ownedCountryIds.add(country.getCountryId());
				}
				// Save Card Data
				d_data.d_numberOfCards.add(player.getPlayerCards().size());
				for (CardType card : player.getPlayerCards()) {
					saveCard(card);
				}
			}
			l_out.writeObject(d_data);
		} catch (Exception e) {
			System.out.println("Cannot save the game");
		}
	}

	/**
	 * The function loadGame() loads the game.
	 */
	public void loadGame(){
		// TODO: Load File Path
		try {
			ObjectInputStream l_in = new ObjectInputStream(getClass().getResourceAsStream("save.dat"));
			d_data = (DataStorage) l_in.readObject();
			// Load Country Data
			ArrayList <Integer> l_countryIds = d_data.d_countryIds;
			ArrayList <String> l_countryNames = d_data.d_countryNames;
			ArrayList <Integer> l_belongContinentIds = d_data.d_belongContinentIds;
			ArrayList <Integer> l_armyCounts = d_data.d_armyCounts;
			ArrayList <String> l_ownerNames = d_data.d_ownerNames;
			ArrayList <Integer> l_numberOfNeighbors = d_data.d_numberOfNeighbors;
			ArrayList <Integer> l_neighborIds = d_data.d_neighborIds;
			int l_countryNumber = l_countryIds.size();
			d_countries = new HashMap<>();
			for (int i = 0; i < l_countryNumber; i++){
				Country l_country = new Country(l_countryIds.get(i), l_countryNames.get(i), l_belongContinentIds.get(i), l_armyCounts.get(i));
				d_countries.put(l_countryIds.get(i), l_country);
			}
			for (int i = 0; i < l_countryNumber; i++){
				Country l_country = d_countries.get(l_countryIds.get(i));
				int l_currentCountryNumberOfNeighbors = l_numberOfNeighbors.get(i);
				for (int j = 0; j < l_currentCountryNumberOfNeighbors; j++){
					Country l_neighbor = d_countries.get(l_neighborIds.remove(0));
					l_country.addNeighbor(l_neighbor);
				}
			}
			// Load Continent Data
			ArrayList <Integer> l_continentIds = d_data.d_continentIds;
			ArrayList <String> l_continentNames = d_data.d_continentNames;
			ArrayList <Integer> l_continentBonus = d_data.d_continentBonus;
			int l_continentNumber = l_continentIds.size();
			Map<Integer, Continent> l_continents = new HashMap<>();
			for (int i = 0; i < l_continentNumber; i++){
				Continent l_continent = new Continent(l_continentIds.get(i), l_continentNames.get(i), l_continentBonus.get(i));
				l_continents.put(l_continentIds.get(i), l_continent);
			}
			for (int i = 0; i < l_countryNumber; i++){
				Country l_country = d_countries.get(l_countryIds.get(i));
				Continent l_continent = l_continents.get(l_belongContinentIds.get(i));
				l_continent.addCountry(l_country);
			}
			// Load Player Data
			ArrayList <String> l_playerNames = d_data.d_playerNames;
			ArrayList <Integer> l_playerLeftoverArmies = d_data.d_playerLeftoverArmies;
			ArrayList <Boolean> l_playerHasCommitted = d_data.d_playerHasCommitted;
			ArrayList <Integer> l_numberOfOrders = d_data.d_numberOfOrders;
			ArrayList <Integer> l_numberOfCountries = d_data.d_numberOfCountries;
			ArrayList <Integer> l_ownedCountryIds = d_data.d_ownedCountryIds;
			ArrayList <Integer> l_numberOfCards = d_data.d_numberOfCards;
			int l_playerNumber = l_playerNames.size();
			List<Player> l_playerList = new ArrayList<>();
			for (int i = 0; i < l_playerNumber; i++){
				Player l_player = new Player(l_playerNames.get(i), new ArrayList<>(), new ArrayList<>(), l_playerLeftoverArmies.get(i), new ArrayList<>(), l_playerHasCommitted.get(i));
				// Load Order Data
				int l_currentPlayerNumberOfOrders = l_numberOfOrders.get(i);
				List <Order> l_orderList = loadOrder(l_player, l_currentPlayerNumberOfOrders);
				l_player.setOrders(l_orderList);
				// Load Country Data
				int l_currentPlayerNumberOfCountries = l_numberOfCountries.get(i);
				for (int j = 0; j < l_currentPlayerNumberOfCountries; j++){
					Country l_country = d_countries.get(l_ownedCountryIds.remove(0));
					l_country.setOwner(l_player);
					l_player.addCountry(l_country);
				}
				// Load Card Data
				int l_currentPlayerNumberOfCards = l_numberOfCards.get(i);
				List <CardType> l_cardList = loadCard(l_currentPlayerNumberOfCards);
				l_player.setPlayerCards(l_cardList);
				l_playerList.add(l_player);
			}
			// TODO: Set Global PlayerList
			// TODO: Set Global GameMap

		}catch (Exception e){
			System.out.println("Cannot load the game");
		}
	}
	/**
	 * The function saveOrder() saves the order.
	 * @param p_order The parameter `p_order` is an Order object.
	 */
	private void saveOrder(Order p_order){
		if (p_order instanceof Advance) {
			d_data.d_orderTypes.add("Advance");
			d_data.d_advanceCountryFromIds.add(Integer.toString(((Advance) p_order).getCountryNameFrom().getCountryId()));
			d_data.d_advanceCountryToIds.add(Integer.toString(((Advance) p_order).getCountryNameTo().getCountryId()));
			d_data.d_advanceNums.add(((Advance) p_order).getNumOfArmies());
		} else if (p_order instanceof Airlift) {
			d_data.d_orderTypes.add("Airlift");
			d_data.d_airliftCountryFromIds.add(Integer.toString(((Airlift) p_order).getSourceCountryId()));
			d_data.d_airliftCountryToIds.add(Integer.toString(((Airlift) p_order).getTargetCountryId()));
			d_data.d_airliftNums.add(Integer.toString(((Airlift) p_order).getArmyToAirlift()));
		} else if (p_order instanceof Blockade) {
			d_data.d_orderTypes.add("Blockade");
			d_data.d_blockadeCountryIds.add(((Blockade) p_order).getCountryIdToBlock());
		} else if (p_order instanceof Bomb) {
			d_data.d_orderTypes.add("Bomb");
			d_data.d_bombCountryIds.add(((Bomb) p_order).getCountryIdToBomb());
		} else if (p_order instanceof Deploy) {
			d_data.d_orderTypes.add("Deploy");
			d_data.d_deployCountryIds.add(((Deploy) p_order).getCountryId());
			d_data.d_deployNums.add(((Deploy) p_order).getNum());
		} else if (p_order instanceof Negotiate) {
			d_data.d_orderTypes.add("Negotiate");
			d_data.d_negotiatePlayerName.add(((Negotiate) p_order).getPlayerName());
		}
	}
	/**
	 * The function saveCard() saves the card.
	 * @param p_card The parameter `p_card` is a CardType object.
	 */
	private void saveCard(CardType p_card) {
		if (p_card == CardType.AIRLIFT) {
			d_data.d_cards.add("Airlift");
		} else if (p_card == CardType.BOMB) {
			d_data.d_cards.add("Bomb");
		} else if (p_card == CardType.BLOCKADE){
			d_data.d_cards.add("Blockade");
		} else if (p_card == CardType.DIPLOMACY) {
			d_data.d_cards.add("Diplomacy");
		}
	}

	/**
	 * The function loadOrder() loads the order.
	 * @param p_player The parameter `p_player` is current player.
	 * @param p_numberOfOrder The parameter `p_numberOfOrder` is the number of orders of current player.
	 * @return a list of orders.
	 */
	private List<Order> loadOrder(Player p_player, int p_numberOfOrder){
		List<Order> l_orders = new ArrayList<>();
		for (int i = 0; i < p_numberOfOrder; i++){
			String l_orderType = d_data.d_orderTypes.remove(0);
			switch (l_orderType) {
				case "Advance": {
					int l_countryFromId = Integer.parseInt(d_data.d_advanceCountryFromIds.remove(0));
					int l_countryToId = Integer.parseInt(d_data.d_advanceCountryToIds.remove(0));
					int l_numOfArmies = d_data.d_advanceNums.remove(0);
					Country l_countryFrom = d_countries.get(l_countryFromId);
					Country l_countryTo = d_countries.get(l_countryToId);
					Order l_advance = new Advance(l_countryFrom, l_countryTo, l_numOfArmies);
					l_orders.add(l_advance);
					break;
				}
				case "Airlift": {
					int l_countryFromId = Integer.parseInt(d_data.d_airliftCountryFromIds.remove(0));
					int l_countryToId = Integer.parseInt(d_data.d_airliftCountryToIds.remove(0));
					int l_numOfArmies = Integer.parseInt(d_data.d_airliftNums.remove(0));
					Order l_airlift = new Airlift(p_player, Integer.toString(l_countryFromId), Integer.toString(l_countryToId), Integer.toString(l_numOfArmies));
					l_orders.add(l_airlift);
					break;
				}
				case "Blockade": {
					String l_countryId = d_data.d_blockadeCountryIds.remove(0);
					Order l_blockade = new Blockade(p_player, l_countryId);
					l_orders.add(l_blockade);
					break;
				}
				case "Bomb": {
					String l_countryId = d_data.d_bombCountryIds.remove(0);
					Order l_bomb = new Bomb(p_player, l_countryId);
					l_orders.add(l_bomb);
					break;
				}
				case "Deploy": {
					int l_countryId = d_data.d_deployCountryIds.remove(0);
					int l_numOfArmies = d_data.d_deployNums.remove(0);
					Order l_deploy = new Deploy(p_player, l_countryId, l_numOfArmies);
					l_orders.add(l_deploy);
					break;
				}
				case "Negotiate":
					String l_playerName = d_data.d_negotiatePlayerName.remove(0);
					Order l_negotiate = new Negotiate(p_player, l_playerName);
					l_orders.add(l_negotiate);
					break;
			}
	}
		return l_orders;
	}

	/**
	 * The function loadCard() loads the card.
	 * @param p_cardNum The parameter `p_cardNum` is the number of cards of current player.
	 * @return a list of cards.
	 */
	public List <CardType> loadCard (int p_cardNum){
		List <CardType> l_cardList = new ArrayList<>();
		for (int i = 0; i < p_cardNum; i++){
			String l_cardType = d_data.d_cards.remove(0);
			switch (l_cardType) {
				case "Airlift":
					l_cardList.add(CardType.AIRLIFT);
					break;
				case "Bomb":
					l_cardList.add(CardType.BOMB);
					break;
				case "Blockade":
					l_cardList.add(CardType.BLOCKADE);
					break;
				case "Diplomacy":
					l_cardList.add(CardType.DIPLOMACY);
					break;
			}
		}
		return l_cardList;
	}
}
