package com.w10.risk_game.utils;

import java.util.ArrayList;
import java.util.Map;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.models.Player;

import dnl.utils.text.table.TextTable;

/**
 * The MapDisplay class contains methods to populate and format a table
 * displaying information about countries, continents, players, and neighboring
 * countries on a game map.
 *
 * @author Omnia Alam
 */
public class MapDisplay {

	/**
	 * The function "populateRow" takes in various parameters and returns an array
	 * of strings containing information about a country, continent, player, and
	 * neighboring countries.
	 *
	 * @param p_country
	 *            The p_country parameter represents a specific country object.
	 * @param p_continent
	 *            The p_continent parameter is an object of the Continent class.
	 * @param p_player
	 *            The parameter "p_player" is of type Player and represents a player
	 *            in the game.
	 * @param p_showArmies
	 *            A boolean value indicating whether to show the number of armies in
	 *            the row or not.
	 * @return The method is returning an array of strings.
	 */
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
			l_values[1] = "" + p_continent.getBonus();
			l_values[2] = "" + p_country.getCountryId();
			l_values[3] = p_country.getCountryName();
			l_values[4] = l_neighborValue;
		} else {
			try {

				l_values = new String[7];
				l_values[0] = (p_country.getContinentId() + "(" + p_continent.getContinentName() + ")");
				l_values[1] = "" + p_continent.getBonus();
				l_values[2] = "" + p_country.getCountryId();
				l_values[3] = p_country.getCountryName();
				l_values[4] = l_neighborValue;

				if (p_player != null && p_player.hasCountry(p_country.getCountryId())) {
					l_values[5] = p_player.getName();
					l_values[6] = "" + p_country.getArmyCount();
				} else {
					l_values[5] = " ";
					l_values[6] = " ";
				}
			} catch (Exception e) {
				System.out.println(Constants.MAP_DISPLAY_CANNOT_DISPLAY_MAP);
			}
		}
		return l_values;
	}

	/**
	 * The function prints a formatted table of the map's countries and their
	 * information.
	 *
	 * @param p_map
	 *            An instance of the GameMap class, which represents the game map
	 *            containing countries and continents.
	 * @param p_showArmies
	 *            A boolean value that determines whether or not to display the
	 *            number of armies in each country. If it is set to true, the table
	 *            will include a column for the number of armies. If it is set to
	 *            false, the table will not include this column
	 */
	public void formatMap(GameMap p_map, boolean p_showArmies) {
		String[] l_columnNames;

		if (p_showArmies) {
			// If true, create an array of column names with "MAP_DISPLAY_ARMIES"
			l_columnNames = new String[]{Constants.MAP_DISPLAY_ID, Constants.MAP_DISPLAY_BONUS,
					Constants.MAP_DISPLAY_COUNTRY_ID, Constants.MAP_DISPLAY_COUNTRY_NAME,
					Constants.MAP_DISPLAY_NEIGHBOR_ID, Constants.MAP_DISPLAY_PLAYER, Constants.MAP_DISPLAY_ARMIES};
		} else {
			// else create an array of column names without "MAP_DISPLAY_ARMIES"
			l_columnNames = new String[]{Constants.MAP_DISPLAY_ID, Constants.MAP_DISPLAY_BONUS,
					Constants.MAP_DISPLAY_COUNTRY_ID, Constants.MAP_DISPLAY_COUNTRY_NAME,
					Constants.MAP_DISPLAY_NEIGHBOR_ID};
		}

		Map<Integer, Country> l_countries = p_map.getCountries();
		Object[][] l_data = new Object[l_countries.size()][l_columnNames.length];
		int d_count = 0;
		for (Country l_country : l_countries.values()) {
			l_data[d_count] = populateRow(l_country, p_map.getContinentById(l_country.getContinentId()),
					l_country.getOwner(), p_showArmies);
			d_count++;
		}

		// Creating an instance of the `TextTable` class and initializing it
		TextTable l_mapTable = new TextTable(l_columnNames, l_data);
		l_mapTable.setAddRowNumbering(true);
		l_mapTable.setSort(0);
		l_mapTable.printTable();

	}
}
