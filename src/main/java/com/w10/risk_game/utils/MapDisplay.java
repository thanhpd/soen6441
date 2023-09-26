package com.w10.risk_game.utils;

import java.util.ArrayList;
import java.util.Map;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.models.Player;

import dnl.utils.text.table.TextTable;

public class MapDisplay {

	public String[] populateRow(Country p_country, Continent p_continent, Player p_player, boolean p_showArmies) {

		ArrayList<String> l_neighborNames = new ArrayList<>();
		for (Country neighbor : p_country.getNeighbors().values()) {
			l_neighborNames.add(neighbor.getCountryId() + "-" + neighbor.getCountryName());
		}

		String l_neighborValue = String.join(", ", l_neighborNames);
		String[] l_values = new String[3];
		if (p_showArmies == false) {
			l_values = new String[5];
			l_values[0] = (p_country.getContinentId() + "(" + p_continent.getContinentName() + ")");
			l_values[1] = "" + p_continent.getBouns();
			l_values[2] = "" + p_country.getCountryId();
			l_values[3] = p_country.getCountryName();
			l_values[4] = l_neighborValue;
		} else {
			try {

				l_values = new String[7];
				l_values[0] = (p_country.getContinentId() + "(" + p_continent.getContinentName() + ")");
				l_values[1] = "" + p_continent.getBouns();
				l_values[2] = "" + p_country.getCountryId();
				l_values[3] = p_country.getCountryName();
				l_values[4] = l_neighborValue;

				if (p_player.hasCountry(p_country.getCountryId()) == true && p_player != null) {
					l_values[5] = p_player.getName();
					l_values[6] = "" + p_player.getLeftoverArmies();
				} else {
					l_values[5] = " ";
					l_values[6] = " ";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

		return l_values;

	}

	public void formatMap(GameMap map, boolean p_showArmies) {
		String[] l_columnNames;
		if (p_showArmies == false) {
			l_columnNames = new String[]{"ID(Continent Name)", "Bonus", "CountryID", "CountryName", "ID(Neighbors)"};
		} else {
			l_columnNames = new String[]{"ID(Continent Name)", "Bonus", "CountryID", "CountryName", "ID(Neighbors)",
					"Player", "Armies"};
		}

		Map<Integer, Country> l_countries = map.getCountries();
		Object[][] l_data = new Object[l_countries.size()][l_columnNames.length];
		int d_count = 0;
		for (Map.Entry<Integer, Country> entry : l_countries.entrySet()) {
			l_data[d_count] = populateRow(entry.getValue(), map.getContinentById(entry.getValue().getContinentId()),
					map.getPlayerbyCountry(entry.getValue().getCountryId()), p_showArmies);
			d_count++;

		}

		TextTable l_mapTable = new TextTable(l_columnNames, l_data);
		l_mapTable.setAddRowNumbering(true);
		l_mapTable.setSort(0);
		l_mapTable.printTable();

	}
}
