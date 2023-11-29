package com.w10.risk_game.utils;

import com.w10.risk_game.commands.*;
import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.engines.SinglePlayerEngine;
import com.w10.risk_game.models.*;
import com.w10.risk_game.models.phases.IssueOrderPhase;
import com.w10.risk_game.models.strategies.*;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

import java.io.*;
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
	SinglePlayerEngine d_gameEngine;
	DataStorage d_dataForSave;
	DataStorage d_dataForLoad;
	HashMap<String, Player> d_playersForSave;
	HashMap<String, Player> d_playersForLoad;
	Map<Integer, Country> d_countriesForLoad;
	GameMap d_gameMapForSave;
	GameMap d_gameMapForLoad;
	GamePlayController d_gamePlayController;
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	/**
	 * Constructor for SaveLoad class.
	 *
	 * @param p_gameEngine
	 *            The parameter `p_gameEngine` is a SinglePlayerEngine object.
	 */
	public SaveLoad(SinglePlayerEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
		this.d_gamePlayController = p_gameEngine.getGame();
		this.d_playersForSave = d_gamePlayController.getPlayers();
		this.d_gameMapForSave = p_gameEngine.getMapEditorController().getGameMap();
		this.d_playersForLoad = new HashMap<>();
		this.d_gameMapForLoad = new GameMap();
	}

	/**
	 * The function getPlayerListForSave() returns the player list for save.
	 *
	 * @return players for save.
	 */
	public HashMap<String, Player> getPlayersForSave() {
		return d_playersForSave;
	}

	/**
	 * The function getPlayerListForLoad() returns the player list for load.
	 *
	 * @return players for load
	 */
	public HashMap<String, Player> getPlayersForLoad() {
		return d_playersForLoad;
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
		String l_filePath = getFilePath(p_fileName);
		try {
			ObjectOutputStream l_out = new ObjectOutputStream(new FileOutputStream(l_filePath));
			d_dataForSave = new DataStorage();
			// Save GamePlayController Data
			d_dataForSave.d_currentPlayerName = d_gamePlayController.getCurrentPlayer().getName();
			d_dataForSave.d_currentPlayerIndex = d_gamePlayController.getCurrentPlayerIndex();
			d_dataForSave.d_isCountriesAssigned = d_gamePlayController.getIsCountriesAssigned();
			for (Player player : d_gamePlayController.getPlayerList()) {
				d_dataForSave.d_gamePlayControllerPlayerNames.add(player.getName());
			}
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
			for (Map.Entry<String, Player> entry : d_playersForSave.entrySet()) {
				d_dataForSave.d_playerNames.add(entry.getValue().getName());
				d_dataForSave.d_playerLeftoverArmies.add(entry.getValue().getLeftoverArmies());
				d_dataForSave.d_playerHasCommitted.add(entry.getValue().getHasCommitted());
				PlayerStrategy l_playerStrategy = entry.getValue().getStrategy();
				d_dataForSave.d_playerStrategyNames.add(l_playerStrategy.getStrategyName());
				if (l_playerStrategy.getStrategyName() == Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_AGGRESSIVE) {
					d_dataForSave.d_playerStrongestCountryOwnedIds.add(
							((AggressivePlayerStrategy) l_playerStrategy).getStrongestCountryOwned().getCountryId());
				}
				// Save Order Data
				int numberOfOrders = entry.getValue().getOrders().size();
				d_dataForSave.d_numberOfOrders.add(numberOfOrders);
				for (Order order : entry.getValue().getOrders()) {
					saveOrder(order);
				}
				// Save Country Data
				d_dataForSave.d_numberOfCountries.add(entry.getValue().getCountriesOwned().size());
				for (Country country : entry.getValue().getCountriesOwned()) {
					d_dataForSave.d_ownedCountryIds.add(country.getCountryId());
				}
				// Save Card Data
				d_dataForSave.d_numberOfCards.add(entry.getValue().getPlayerCards().size());
				for (CardType card : entry.getValue().getPlayerCards()) {
					saveCard(card);
				}
			}
			l_out.writeObject(d_dataForSave);
			l_out.close();
			Logger.log(Constants.SAVE_SUCCESS + l_filePath);
		} catch (Exception e) {
			Logger.log(Constants.SAVE_FAIL);
		}
	}

	/**
	 * The function loadGame() loads the game.
	 */
	public void loadGame(String p_fileName) {
		String l_filePath = getFilePath(p_fileName);
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
			ArrayList<String> l_playerStrategyNames = d_dataForLoad.d_playerStrategyNames;
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
				// Load Strategy Data
				String l_playerStrategyName = l_playerStrategyNames.get(i);
				PlayerStrategy l_playerStrategy = loadStrategy(l_playerStrategyName, l_player);
				l_player.setStrategy(l_playerStrategy);
			}
			l_in.close();
			d_gameMapForLoad = l_gameMap;
			for (Player player : l_playerList) {
				d_playersForLoad.put(player.getName(), player);
				if (player.getName().equals(d_dataForLoad.d_currentPlayerName)) {
					d_gameEngine.getGame().setCurrentPlayer(player);
				}
			}
			d_gameEngine.getGame().setPlayers(d_playersForLoad);
			d_gameEngine.getGame().SetPlayerListForDiplomacy(l_playerList);
			d_gameEngine.getGame().setGameMap(d_gameMapForLoad);
			d_gameEngine.getGame().setCurrentPlayerIndex(d_dataForLoad.d_currentPlayerIndex);
			d_gameEngine.getGame().setIsCountriesAssigned(d_dataForLoad.d_isCountriesAssigned);
			List<Player> l_gamePlayControllerPlayerList = new ArrayList<>();
			for (String playerName : d_dataForLoad.d_gamePlayControllerPlayerNames) {
				l_gamePlayControllerPlayerList.add(d_playersForLoad.get(playerName));
			}
			d_gameEngine.getGame().setPlayerList(l_gamePlayControllerPlayerList);
			d_gameEngine.getMapEditorController().setGameMap(d_gameMapForLoad);
			// Load Phase Data
			Phase l_currentPhase = new IssueOrderPhase(d_gameEngine);
			SinglePlayerEngine.SetPhase(l_currentPhase);
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
			d_dataForSave.d_orderTypes.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE);
			d_dataForSave.d_advanceCountryFromIds
					.add(Integer.toString(((Advance) p_order).getCountryNameFrom().getCountryId()));
			d_dataForSave.d_advanceCountryToIds
					.add(Integer.toString(((Advance) p_order).getCountryNameTo().getCountryId()));
			d_dataForSave.d_advanceNums.add(((Advance) p_order).getNumOfArmies());
		} else if (p_order instanceof Airlift) {
			// Save information of Airlift
			d_dataForSave.d_orderTypes.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT);
			d_dataForSave.d_airliftCountryFromIds.add(Integer.toString(((Airlift) p_order).getSourceCountryId()));
			d_dataForSave.d_airliftCountryToIds.add(Integer.toString(((Airlift) p_order).getTargetCountryId()));
			d_dataForSave.d_airliftNums.add(Integer.toString(((Airlift) p_order).getArmyToAirlift()));
		} else if (p_order instanceof Blockade) {
			// Save information of Blockade
			d_dataForSave.d_orderTypes.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE);
			d_dataForSave.d_blockadeCountryIds.add(((Blockade) p_order).getCountryIdToBlock());
		} else if (p_order instanceof Bomb) {
			// Save information of Bomb
			d_dataForSave.d_orderTypes.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB);
			d_dataForSave.d_bombCountryIds.add(((Bomb) p_order).getCountryIdToBomb());
		} else if (p_order instanceof Deploy) {
			// Save information of Deploy
			d_dataForSave.d_orderTypes.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY);
			d_dataForSave.d_deployCountryIds.add(((Deploy) p_order).getCountryId());
			d_dataForSave.d_deployNums.add(((Deploy) p_order).getNum());
		} else if (p_order instanceof Negotiate) {
			// Save information of Negotiate
			d_dataForSave.d_orderTypes.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE);
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
			d_dataForSave.d_cards.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT);
		} else if (p_card == CardType.BOMB) {
			d_dataForSave.d_cards.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB);
		} else if (p_card == CardType.BLOCKADE) {
			d_dataForSave.d_cards.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE);
		} else if (p_card == CardType.DIPLOMACY) {
			d_dataForSave.d_cards.add(Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE);
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
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE : {
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
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT : {
					// Create airlift order
					int l_countryFromId = Integer.parseInt(d_dataForLoad.d_airliftCountryFromIds.remove(0));
					int l_countryToId = Integer.parseInt(d_dataForLoad.d_airliftCountryToIds.remove(0));
					int l_numOfArmies = Integer.parseInt(d_dataForLoad.d_airliftNums.remove(0));
					Order l_airlift = new Airlift(p_player, Integer.toString(l_countryFromId),
							Integer.toString(l_countryToId), Integer.toString(l_numOfArmies));
					l_orders.add(l_airlift);
					break;
				}
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE : {
					// Create blockade order
					String l_countryId = d_dataForLoad.d_blockadeCountryIds.remove(0);
					Order l_blockade = new Blockade(p_player, l_countryId);
					l_orders.add(l_blockade);
					break;
				}
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB : {
					// Create bomb order
					String l_countryId = d_dataForLoad.d_bombCountryIds.remove(0);
					Order l_bomb = new Bomb(p_player, l_countryId);
					l_orders.add(l_bomb);
					break;
				}
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY : {
					// Create deploy order
					int l_countryId = d_dataForLoad.d_deployCountryIds.remove(0);
					int l_numOfArmies = d_dataForLoad.d_deployNums.remove(0);
					Order l_deploy = new Deploy(p_player, l_countryId, l_numOfArmies);
					l_orders.add(l_deploy);
					break;
				}
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE :
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
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT :
					l_cardList.add(CardType.AIRLIFT);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB :
					l_cardList.add(CardType.BOMB);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE :
					l_cardList.add(CardType.BLOCKADE);
					break;
				case Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE :
					l_cardList.add(CardType.DIPLOMACY);
					break;
			}
		}
		return l_cardList;
	}

	/**
	 * This function loads player strategy.
	 *
	 * @param p_stateName
	 *            strategy name
	 * @param p_player
	 *            player
	 */
	public PlayerStrategy loadStrategy(String p_stateName, Player p_player) {
		PlayerStrategy l_playerStrategy = null;
		switch (p_stateName) {
			case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_HUMAN :
				l_playerStrategy = new HumanPlayerStrategy(p_player);
				break;
			case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_AGGRESSIVE :
				l_playerStrategy = new AggressivePlayerStrategy(p_player);
				Country l_country = d_countriesForLoad.get(d_dataForLoad.d_playerStrongestCountryOwnedIds.remove(0));
				((AggressivePlayerStrategy) l_playerStrategy).setStrongestCountryOwned(l_country);
				break;
			case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_BENEVOLENT :
				l_playerStrategy = new BenevolentPlayerStrategy(p_player);
				break;
			case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_CHEATER :
				l_playerStrategy = new CheaterPlayerStrategy(p_player);
				break;
			case Constants.USER_INPUT_COMMAND_PLAYER_STRATEGY_RANDOM :
				l_playerStrategy = new RandomPlayerStrategy(p_player);
				break;
		}
		return l_playerStrategy;
	}

	/**
	 * This function gets the file path based on condition whether user input the
	 * full path or just file name.
	 *
	 * @param p_fileName
	 *            file name
	 * @return file path
	 */
	public String getFilePath(String p_fileName) {
		String l_filePath = "";
		if (p_fileName.contains("/")) {
			l_filePath = p_fileName;
		} else {
			l_filePath = Constants.SAVE_LOAD_FILE_PATH + p_fileName + ".dat";
		}
		return l_filePath;
	}
}
