package com.w10.risk_game.utils;

import java.util.ArrayList;
import java.util.Map;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

import dnl.utils.text.table.TextTable;

public class MapDisplay {

	public String[] populateRow(Country p_country, Continent p_continent, boolean p_showArmies) {
		String[] l_values = new String[5];

		ArrayList<String> l_neighborNames = new ArrayList<>();
		for (Country neighbor : p_country.getNeighbors().values()) {
			l_neighborNames.add(neighbor.getCountryId() + "-" + neighbor.getCountryName());
		}

		String l_neighborValue = String.join(", ", l_neighborNames);

		l_values[0] = "" + p_country.getCountryId();
		l_values[1] = p_country.getCountryName();
		if (p_showArmies) {

		}
		l_values[2] = (p_country.getContinentId() + "(" + p_continent.getContinentName() + ")");
		l_values[3] = "" + p_continent.getBouns();
		l_values[4] = l_neighborValue;
		

		return l_values;

	}

	public void formatMap(GameMap map) {
		String[] l_columnNames = { "Country ID", "Country Name", "ID(Continent Name)","Bonus", "ID(Neighbors)" };

		Map<Integer, Country> l_countries = map.getCountries();
		Object[][] l_data = new Object[l_countries.size()][l_columnNames.length];
		int d_count = 0;
		for (Map.Entry<Integer, Country> entry : l_countries.entrySet()) {
			l_data[d_count] = populateRow(entry.getValue(), map.getContinentById(entry.getValue().getContinentId()),
					false);
			d_count++;

		}

		TextTable l_mapTable = new TextTable(l_columnNames, l_data);
		l_mapTable.setAddRowNumbering(true);
		l_mapTable.setSort(0);
		l_mapTable.printTable();

	}
}
