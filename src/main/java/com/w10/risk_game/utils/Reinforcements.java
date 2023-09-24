package com.w10.risk_game.utils;

import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.GameMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Reinforcements {
	public void reinforcementPhase(Player player) {
		int armies = player.getLeftoverArmies();
		List<Country> playerCountries = player.getCountriesOwned();
		GameMap gameMap = new GameMap();
		List<Country> allCountries = new ArrayList<Country>(gameMap.getCountries().values());
		// Calculate continent bonus armies
		List<String> groupPlayerCountries = groupCountries(playerCountries);
		List<String> groupAllCountries = groupCountries(allCountries);
		int bonus = 0;
		for (int i = 0; i < groupPlayerCountries.size(); i++) {
			if (groupPlayerCountries.get(i).equals(groupAllCountries.get(i))) {
				// Need the bonus value of the continent
				//bonus += gameMap.getContinents().get(i + 1).getBonus();
			}
		}
		// Calculate the number of reinforcement armies
		int countrySize = playerCountries.size();
		int reinforceArmies =(int) (Math.floor(countrySize / 3) + bonus);;
		player.setLeftoverArmies(armies + reinforceArmies);
	}

	private List<String> groupCountries(List<Country> countries) {
		GameMap gameMap = new GameMap();
		int continentNum = gameMap.getContinents().size();
		List<String> groupCountries = new ArrayList<String>();
		for (int i = 0; i < continentNum; i++) {
			groupCountries.add("");
		}
		for (Country country : countries) {
			int continentId = country.getContinentId();
			int countryId = country.getCountryId();
			if (groupCountries.get(continentId - 1) == "") {
				groupCountries.set(continentId - 1, countryId + "");
			} else {
				groupCountries.set(continentId - 1, " " + countryId);
			}
		}
		// Order the country ids in each continent
		for (String groupCountry : groupCountries) {
			List <String> countryIds = new ArrayList<String>();
			for (String countryId : groupCountry.split(" ")) {
				countryIds.add(countryId);
			}
			Collections.sort(countryIds);
			groupCountry = String.join(" ", countryIds);
		}
		return groupCountries;
	}
}
