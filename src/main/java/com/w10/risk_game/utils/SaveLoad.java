package com.w10.risk_game.utils;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.commands.*;
import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.models.*;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The SaveLoad class is responsible for saving and loading the game.
 *
 * @author Yajing
 */
public class SaveLoad {
	GameEngine d_gameEngine;
	DataStorage d_dataForSave;
	DataStorage d_dataForLoad;
	List<Player> d_playerListForSave;
	List<Player> d_playerListForLoad;
	Map<Integer, Country> d_countriesForLoad;
	GameMap d_gameMapForSave;
	GameMap d_gameMapForLoad;
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	/**
	 * Constructor for SaveLoad class.
	 *
	 * @param p_gameEngine
	 *            The parameter `p_gameEngine` is a GameEngine object.
	 */
	public SaveLoad(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
		// TODO: Get Global PlayerList
		this.d_playerListForSave = p_gameEngine.getGame().GetPlayerListForDiplomacy();
		// TODO: Get Global GameMap
		this.d_gameMapForSave = p_gameEngine.getMapEditorController().getGameMap();
	}

	/**
	 * Constructor for SaveLoad class. This constructor is only used for testing.
	 *
	 * @param p_gameMap
	 *            GameMap
	 * @param p_playerList
	 *            List of players
	 */
	public SaveLoad(GameMap p_gameMap, List<Player> p_playerList) {
		this.d_gameMapForSave = p_gameMap;
		this.d_playerListForSave = p_playerList;
	}
	/**
	 * The function getPlayerListForSave() returns the player list for save.
	 *
	 * @return a list of players to be saved.
	 */
	public List<Player> getPlayerListForSave() {
		return d_playerListForSave;
	}
	/**
	 * The function getPlayerListForLoad() returns the player list for load.
	 *
	 * @return a list of players to be loaded.
	 */
	public List<Player> getPlayerListForLoad() {
		return d_playerListForLoad;
	}

	/**
	 * The function getGameMapForSave() returns the game map for save.
	 *
	 * @return a game map to be saved.
	 */
	public GameMap getGameMapForSave() {
		return d_gameMapForSave;
	}

	/**
	 * The function getGameMapForLoad() returns the game map for load.
	 *
	 * @return a game map to be loaded.
	 */
	public GameMap getGameMapForLoad() {
		return d_gameMapForLoad;
	}

	/**
	 * The function saveGame() saves the game.
	 */
	public void saveGame(String p_fileName) {
		String l_filePath = Constants.SAVE_LOAD_FILE_PATH + p_fileName + ".dat";
		try {
			ObjectOutputStream l_out = new ObjectOutputStream(new FileOutputStream(l_filePath));
			d_dataForSave = new DataStorage();
			// Save Country Data
			for (Map.Entry<Integer, Country> entry : d_gameMapForSave.getCountries().entrySet()) {
				d_dataForSave.d_countryIds.add(entry.getKey());
				d_dataForSave.d_countryNames.add(entry.getValue().getCountryName());
				d_dataForSave.d_belongContinentIds.add(entry.getValue().getContinentId());
				d_dataForSave.d_armyCounts.add(entry.getValue().getArmyCount());
				d_dataForSave.d_ownerNames.add(entry.getValue().getOwner().getName());
				d_dataForSave.d_numberOfNeighbors.add(entry.getValue().getNeighbors().size());
				for (Map.Entry<Integer, Country> neighbor : entry.getValue().getNeighbors().entrySet()) {
					d_dataForSave.d_neighborIds.add(neighbor.getValue().getCountryId());
				}
			}
			// Save Continent Data
			for (Map.Entry<Integer, Continent> entry : d_gameMapForSave.getContinents().entrySet()) {
				d_dataForSave.d_continentIds.add(entry.getKey());
				d_dataForSave.d_continentNames.add(entry.getValue().getContinentName());
				d_dataForSave.d_continentBonus.add(entry.getValue().getBonus());
			}
			// Save Player Data
			for (Player player : d_playerListForSave) {
				d_dataForSave.d_playerNames.add(player.getName());
				d_dataForSave.d_playerLeftoverArmies.add(player.getLeftoverArmies());
				d_dataForSave.d_playerHasCommitted.add(player.getHasCommitted());
				// Save Order Data
				int numberOfOrders = player.getOrders().size();
				d_dataForSave.d_numberOfOrders.add(numberOfOrders);
				for (Order order : player.getOrders()) {
					saveOrder(order);
				}
				// Save Country Data
				d_dataForSave.d_numberOfCountries.add(player.getCountriesOwned().size());
				for (Country country : player.getCountriesOwned()) {
					d_dataForSave.d_ownedCountryIds.add(country.getCountryId());
				}
				// Save Card Data
				d_dataForSave.d_numberOfCards.add(player.getPlayerCards().size());
				for (CardType card : player.getPlayerCards()) {
					saveCard(card);
				}
			}
			l_out.writeObject(d_dataForSave);
			l_out.close();
			Logger.log(Constants.SAVE_SUCCESS);
		} catch (Exception e) {
			Logger.log(Constants.SAVE_FAIL);
		}
	}

	/**
	 * The function loadGame() loads the game.
	 */
	public void loadGame(String p_fileName) {
		String l_filePath = Constants.SAVE_LOAD_FILE_PATH + p_fileName + ".dat";
		GameMap l_gameMap = new GameMap();
		List<Player> l_playerList = new ArrayList<>();
		try {
			ObjectInputStream l_in = new ObjectInputStream(new FileInputStream(l_filePath));
			d_dataForLoad = (DataStorage) l_in.readObject();
			// Load Country Data
			ArrayList<Integer> l_countryIds = d_dataForLoad.d_countryIds;
			ArrayList<String> l_countryNames = d_dataForLoad.d_countryNames;
			ArrayList<Integer> l_belongContinentIds = d_dataForLoad.d_belongContinentIds;
			ArrayList<Integer> l_armyCounts = d_dataForLoad.d_armyCounts;
			ArrayList<String> l_ownerNames = d_dataForLoad.d_ownerNames;
			ArrayList<Integer> l_numberOfNeighbors = d_dataForLoad.d_numberOfNeighbors;
			ArrayList<Integer> l_neighborIds = d_dataForLoad.d_neighborIds;
			int l_countryNumber = l_countryIds.size();
			d_countriesForLoad = new HashMap<>();
			for (int i = 0; i < l_countryNumber; i++) {
				Country l_country = new Country(l_countryIds.get(i), l_countryNames.get(i), l_belongContinentIds.get(i),
						l_armyCounts.get(i));
				d_countriesForLoad.put(l_countryIds.get(i), l_country);
			}
			for (int i = 0; i < l_countryNumber; i++) {
				Country l_country = d_countriesForLoad.get(l_countryIds.get(i));
				int l_currentCountryNumberOfNeighbors = l_numberOfNeighbors.get(i);
				for (int j = 0; j < l_currentCountryNumberOfNeighbors; j++) {
					Country l_neighbor = d_countriesForLoad.get(l_neighborIds.remove(0));
					l_country.addNeighbor(l_neighbor);
				}
			}
			// Load Continent Data
			ArrayList<Integer> l_continentIds = d_dataForLoad.d_continentIds;
			ArrayList<String> l_continentNames = d_dataForLoad.d_continentNames;
			ArrayList<Integer> l_continentBonus = d_dataForLoad.d_continentBonus;
			int l_continentNumber = l_continentIds.size();
			Map<Integer, Continent> l_continents = new HashMap<>();
			for (int i = 0; i < l_continentNumber; i++) {
				Continent l_continent = new Continent(l_continentIds.get(i), l_continentNames.get(i),
						l_continentBonus.get(i));
				l_continents.put(l_continentIds.get(i), l_continent);
			}
			for (int i = 0; i < l_countryNumber; i++) {
				Country l_country = d_countriesForLoad.get(l_countryIds.get(i));
				Continent l_continent = l_continents.get(l_belongContinentIds.get(i));
				l_continent.addCountry(l_country);
			}
			l_gameMap.addCountries(d_countriesForLoad);
			l_gameMap.addContinents(l_continents);
			// Load Player Data
			ArrayList<String> l_playerNames = d_dataForLoad.d_playerNames;
			ArrayList<Integer> l_playerLeftoverArmies = d_dataForLoad.d_playerLeftoverArmies;
			ArrayList<Boolean> l_playerHasCommitted = d_dataForLoad.d_playerHasCommitted;
			ArrayList<Integer> l_numberOfOrders = d_dataForLoad.d_numberOfOrders;
			ArrayList<Integer> l_numberOfCountries = d_dataForLoad.d_numberOfCountries;
			ArrayList<Integer> l_ownedCountryIds = d_dataForLoad.d_ownedCountryIds;
			ArrayList<Integer> l_numberOfCards = d_dataForLoad.d_numberOfCards;
			int l_playerNumber = l_playerNames.size();
			for (int i = 0; i < l_playerNumber; i++) {
				Player l_player = new Player(l_playerNames.get(i), new ArrayList<>(), new ArrayList<>(),
						l_playerLeftoverArmies.get(i), new ArrayList<>(), l_playerHasCommitted.get(i));
				// Load Country Data
				int l_currentPlayerNumberOfCountries = l_numberOfCountries.get(i);
				for (int j = 0; j < l_currentPlayerNumberOfCountries; j++) {
					Country l_country = d_countriesForLoad.get(l_ownedCountryIds.remove(0));
					l_country.setOwner(l_player);
					l_player.addCountry(l_country);
				}
				// Load Order Data
				int l_currentPlayerNumberOfOrders = l_numberOfOrders.get(i);
				List<Order> l_orderList = loadOrder(l_player, l_currentPlayerNumberOfOrders);
				l_player.setOrders(l_orderList);
				// Load Card Data
				int l_currentPlayerNumberOfCards = l_numberOfCards.get(i);
				List<CardType> l_cardList = loadCard(l_currentPlayerNumberOfCards);
				l_player.setPlayerCards(l_cardList);
				l_playerList.add(l_player);
			}
			l_in.close();
			d_gameMapForLoad = l_gameMap;
			d_playerListForLoad = l_playerList;
			// TODO: Set Global PlayerList
			// TODO: Set Global GameMap
			Logger.log(Constants.LOAD_SUCCESS);
		} catch (Exception e) {
			Logger.log(Constants.LOAD_FAIL);
		}
	}
	/**
	 * The function saveOrder() saves the order.
	 *
	 * @param p_order
	 *            The parameter `p_order` is an Order object.
	 */
	private void saveOrder(Order p_order) {
		if (p_order instanceof Advance) {
			// Save information of Advance
			d_dataForSave.d_orderTypes.add("Advance");
			d_dataForSave.d_advanceCountryFromIds
					.add(Integer.toString(((Advance) p_order).getCountryNameFrom().getCountryId()));
			d_dataForSave.d_advanceCountryToIds
					.add(Integer.toString(((Advance) p_order).getCountryNameTo().getCountryId()));
			d_dataForSave.d_advanceNums.add(((Advance) p_order).getNumOfArmies());
		} else if (p_order instanceof Airlift) {
			// Save information of Airlift
			d_dataForSave.d_orderTypes.add("Airlift");
			d_dataForSave.d_airliftCountryFromIds.add(Integer.toString(((Airlift) p_order).getSourceCountryId()));
			d_dataForSave.d_airliftCountryToIds.add(Integer.toString(((Airlift) p_order).getTargetCountryId()));
			d_dataForSave.d_airliftNums.add(Integer.toString(((Airlift) p_order).getArmyToAirlift()));
		} else if (p_order instanceof Blockade) {
			// Save information of Blockade
			d_dataForSave.d_orderTypes.add("Blockade");
			d_dataForSave.d_blockadeCountryIds.add(((Blockade) p_order).getCountryIdToBlock());
		} else if (p_order instanceof Bomb) {
			// Save information of Bomb
			d_dataForSave.d_orderTypes.add("Bomb");
			d_dataForSave.d_bombCountryIds.add(((Bomb) p_order).getCountryIdToBomb());
		} else if (p_order instanceof Deploy) {
			// Save information of Deploy
			d_dataForSave.d_orderTypes.add("Deploy");
			d_dataForSave.d_deployCountryIds.add(((Deploy) p_order).getCountryId());
			d_dataForSave.d_deployNums.add(((Deploy) p_order).getNum());
		} else if (p_order instanceof Negotiate) {
			// Save information of Negotiate
			d_dataForSave.d_orderTypes.add("Negotiate");
			d_dataForSave.d_negotiatePlayerName.add(((Negotiate) p_order).getPlayerName());
		}
	}
	/**
	 * The function saveCard() saves the card.
	 *
	 * @param p_card
	 *            The parameter `p_card` is a CardType object.
	 */
	private void saveCard(CardType p_card) {
		if (p_card == CardType.AIRLIFT) {
			d_dataForSave.d_cards.add("Airlift");
		} else if (p_card == CardType.BOMB) {
			d_dataForSave.d_cards.add("Bomb");
		} else if (p_card == CardType.BLOCKADE) {
			d_dataForSave.d_cards.add("Blockade");
		} else if (p_card == CardType.DIPLOMACY) {
			d_dataForSave.d_cards.add("Diplomacy");
		}
	}

	/**
	 * The function loadOrder() loads the order.
	 *
	 * @param p_player
	 *            The parameter `p_player` is current player.
	 * @param p_numberOfOrder
	 *            The parameter `p_numberOfOrder` is the number of orders of current
	 *            player.
	 * @return a list of orders.
	 */
	private List<Order> loadOrder(Player p_player, int p_numberOfOrder) {
		List<Order> l_orders = new ArrayList<>();
		for (int i = 0; i < p_numberOfOrder; i++) {
			String l_orderType = d_dataForLoad.d_orderTypes.remove(0);
			switch (l_orderType) {
				case "Advance" : {
					// Create advance order
					int l_countryFromId = Integer.parseInt(d_dataForLoad.d_advanceCountryFromIds.remove(0));
					int l_countryToId = Integer.parseInt(d_dataForLoad.d_advanceCountryToIds.remove(0));
					int l_numOfArmies = d_dataForLoad.d_advanceNums.remove(0);
					Country l_countryFrom = d_countriesForLoad.get(l_countryFromId);
					Country l_countryTo = d_countriesForLoad.get(l_countryToId);
					Order l_advance = new Advance(l_countryFrom, l_countryTo, l_numOfArmies);
					l_orders.add(l_advance);
					break;
				}
				case "Airlift" : {
					// Create airlift order
					int l_countryFromId = Integer.parseInt(d_dataForLoad.d_airliftCountryFromIds.remove(0));
					int l_countryToId = Integer.parseInt(d_dataForLoad.d_airliftCountryToIds.remove(0));
					int l_numOfArmies = Integer.parseInt(d_dataForLoad.d_airliftNums.remove(0));
					Order l_airlift = new Airlift(p_player, Integer.toString(l_countryFromId),
							Integer.toString(l_countryToId), Integer.toString(l_numOfArmies));
					l_orders.add(l_airlift);
					break;
				}
				case "Blockade" : {
					// Create blockade order
					String l_countryId = d_dataForLoad.d_blockadeCountryIds.remove(0);
					Order l_blockade = new Blockade(p_player, l_countryId);
					l_orders.add(l_blockade);
					break;
				}
				case "Bomb" : {
					// Create bomb order
					String l_countryId = d_dataForLoad.d_bombCountryIds.remove(0);
					Order l_bomb = new Bomb(p_player, l_countryId);
					l_orders.add(l_bomb);
					break;
				}
				case "Deploy" : {
					// Create deploy order
					int l_countryId = d_dataForLoad.d_deployCountryIds.remove(0);
					int l_numOfArmies = d_dataForLoad.d_deployNums.remove(0);
					Order l_deploy = new Deploy(p_player, l_countryId, l_numOfArmies);
					l_orders.add(l_deploy);
					break;
				}
				case "Negotiate" :
					// Create negotiate order
					String l_playerName = d_dataForLoad.d_negotiatePlayerName.remove(0);
					Order l_negotiate = new Negotiate(p_player, l_playerName);
					l_orders.add(l_negotiate);
					break;
			}
		}
		return l_orders;
	}

	/**
	 * The function loadCard() loads the card.
	 *
	 * @param p_cardNum
	 *            The parameter `p_cardNum` is the number of cards of current
	 *            player.
	 * @return a list of cards.
	 */
	public List<CardType> loadCard(int p_cardNum) {
		List<CardType> l_cardList = new ArrayList<>();
		for (int i = 0; i < p_cardNum; i++) {
			String l_cardType = d_dataForLoad.d_cards.remove(0);
			// Create card based on card type
			switch (l_cardType) {
				case "Airlift" :
					l_cardList.add(CardType.AIRLIFT);
					break;
				case "Bomb" :
					l_cardList.add(CardType.BOMB);
					break;
				case "Blockade" :
					l_cardList.add(CardType.BLOCKADE);
					break;
				case "Diplomacy" :
					l_cardList.add(CardType.DIPLOMACY);
					break;
			}
		}
		return l_cardList;
	}
}
