package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;
import java.util.ArrayList;
import java.util.List;

public class Bomb extends Order {
	private static final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();
	private final Player d_player;
	private final String d_countryIdToBomb;

	public Bomb(Player p_player, String p_countryId) {
		this.d_player = p_player;
		this.d_countryIdToBomb = p_countryId;
	}

	public void execute() {
		Country l_countryToBomb = getCountryToBomb(d_player, d_countryIdToBomb);

		if (l_countryToBomb != null) {
			int l_initArmyCount = l_countryToBomb.getArmyCount();
			int l_newArmyCount = l_initArmyCount / 2;
			l_countryToBomb.setArmyCount(l_newArmyCount);
		}
	}

	public static boolean validateOrder(Player p_player, String p_countryId) {
		Country l_countryToBomb = getCountryToBomb(p_player, p_countryId);

		return l_countryToBomb != null;
	}

	public static Country getCountryToBomb(Player p_player, String p_countryId) {
		if (p_countryId == null) {
			d_logger.log(Constants.PLAYER_ISSUE_ORDER_DEPLOY_INCORRECT);
			return null;
		}

		int l_countryId = Integer.parseInt(p_countryId);
		List<Country> l_neighbors = getForeignNeighbors(p_player);

		Country l_countryToBomb = l_neighbors.stream().filter(neighbor -> neighbor.getCountryId() == l_countryId)
			.findFirst().orElse(null);

		if (l_countryToBomb == null) {
			d_logger.log(Constants.BOMB_CARD_NO_VALID_COUNTRY);
		}

		return l_countryToBomb;
	}

	private static List<Country> getForeignNeighbors(Player p_player) {
		List<Country> l_countries = p_player.getCountriesOwned();
		List<Country> l_neighbors = new ArrayList<>();

		for (Country l_country : l_countries) {
			for (Country l_neighbor : l_country.getNeighbors().values()) {
				if (l_neighbor.getOwner() != p_player) {
					l_neighbors.add(l_neighbor);
				}
			}
		}

		return l_neighbors;
	}

}
