package com.w10.risk_game.utils;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

/**
 * The MapDisplay class is responsible for displaying the game map, including
 * continent and country information, and optionally showing the number of
 * armies in each country.
 *
 * @author Sherwyn Dsouza
 */
public class MapDisplay {
	private final LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

	/**
	 * The `displayMap` function takes a `GameMap` object and a boolean flag as
	 * parameters, and prints a formatted table displaying information about the
	 * continents, countries, and their neighbors in the map.
	 *
	 * @param p_map
	 *            The `p_map` parameter is an instance of the `GameMap` class, which
	 *            represents the game map containing continents, countries, and
	 *            their connections.
	 * @param p_showArmies
	 *            The parameter `p_showArmies` is a boolean value that determines
	 *            whether to display the number of armies in each country or not. If
	 *            `p_showArmies` is `true`, the number of armies will be displayed.
	 *            If `p_showArmies` is `false`, the number of
	 */
	public void displayMap(GameMap p_map, boolean p_showArmies) {
		Formatter l_formatter = new Formatter();
		try {
			String l_table, l_mapDisplayLine, l_mapDisplayColumnNames;

			// Set up table look based on if countries and reinforcements have been assigned
			if (p_showArmies) {
				l_table = Constants.MAP_DISPLAY_TABLE2_FORMAT_PATTERN;
				l_mapDisplayLine = Constants.MAP_DISPLAY_TABLE2_LINE;
				l_mapDisplayColumnNames = Constants.MAP_DISPLAY_TABLE2_COLUMN_NAMES;
			} else {
				l_table = Constants.MAP_DISPLAY_TABLE1_FORMAT_PATTERN;
				l_mapDisplayLine = Constants.MAP_DISPLAY_TABLE1_LINE;
				l_mapDisplayColumnNames = Constants.MAP_DISPLAY_TABLE1_COLUMN_NAMES;
			}

			// Print the table header to the console and file
			l_formatter.format(l_mapDisplayLine);
			d_logger.log(l_formatter.toString());
			l_formatter.close();

			l_formatter = new Formatter();
			l_formatter.format(l_mapDisplayColumnNames);
			d_logger.log(l_formatter.toString());
			l_formatter.close();

			l_formatter = new Formatter();
			l_formatter.format(l_mapDisplayLine);
			d_logger.log(l_formatter.toString());
			l_formatter.close();

			// Iterate over every continent and country in the map and print table data
			Iterator<Map.Entry<Integer, Continent>> l_continentIterator = p_map.getContinents().entrySet().iterator();

			while (l_continentIterator.hasNext()) {
				Map.Entry<Integer, Continent> l_continentMap = (Map.Entry<Integer, Continent>) l_continentIterator
						.next();
				Integer l_continentId = l_continentMap.getKey();
				Continent l_continent = p_map.getContinents().get(l_continentId);
				Iterator<Country> l_countryIterator = l_continent.getCountries().iterator();

				while (l_countryIterator.hasNext()) {
					Country l_country = (Country) l_countryIterator.next();
					ArrayList<String> l_neighborNames = new ArrayList<>();
					// Fetching neighbor details
					for (Country neighbor : l_country.getNeighbors().values()) {
						l_neighborNames.add(neighbor.getCountryId() + "-" + neighbor.getCountryName());
					}
					String l_neighborValue = String.join(", ", l_neighborNames);
					l_formatter = new Formatter();
					// Prepare and format the table with country details
					if (p_showArmies) {
						l_formatter.format(l_table, l_country.getCountryName(), l_country.getCountryId(),
								l_continent.getContinentName(), l_continent.getBonus(), l_neighborValue,
								l_country.getOwner() == null
										? Constants.MAP_DISPLAY_NEUTRAL_COUNTRY
										: l_country.getOwner().getName(),
								l_country.getArmyCount());
					} else {
						l_formatter.format(l_table, l_country.getCountryName(), l_country.getCountryId(),
								l_continent.getContinentName(), l_continent.getBonus(), l_neighborValue);
					}
					d_logger.log(l_formatter.toString());
					l_formatter.close();
				}
			}
		} catch (Exception e) {
			// Log an error if the map display fails
			d_logger.log(Constants.MAP_DISPLAY_CANNOT_DISPLAY_MAP);
			l_formatter.close();
		}
	}
}
