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
	 * The function `displayMap` takes a `GameMap` object and a boolean flag as
	 * input and displays the map information, including continent, country, and
	 * neighbor details, either with or without army counts depending on the flag.
	 *
	 * @param p_map
	 *            The `p_map` parameter is an instance of the `GameMap` class, which
	 *            represents the game map containing continents, countries, and
	 *            their connections.
	 * @param p_showArmies
	 *            The parameter "p_showArmies" is a boolean value that determines
	 *            whether or not to display the number of armies in each country on
	 *            the map. If it is set to true, the number of armies will be
	 *            displayed. If it is set to false, the number of armies will not be
	 *            displayed
	 */
	public void displayMap(GameMap p_map, boolean p_showArmies) {
		try {
			Formatter l_formatter = new Formatter();

			Iterator<Map.Entry<Integer, Continent>> l_iteratorForContinent = p_map.getContinents().entrySet()
					.iterator();

			String l_table1 = Constants.MAP_DISPLAY_TABLE1_FORMAT_PATTERN;
			String l_table2 = Constants.MAP_DISPLAY_TABLE2_FORMAT_PATTERN;

			if (p_showArmies) {
				l_formatter.format(Constants.MAP_DISPLAY_TABLE2_LINE);
				d_logger.log(l_formatter.toString());
				l_formatter.close();

				l_formatter = new Formatter();
				l_formatter.format(Constants.MAP_DISPLAY_TABLE2_COLUMN_NAMES);
				d_logger.log(l_formatter.toString());
				l_formatter.close();

				l_formatter = new Formatter();
				l_formatter.format(Constants.MAP_DISPLAY_TABLE2_LINE);
				d_logger.log(l_formatter.toString());
				l_formatter.close();
			} else {
				l_formatter.format(Constants.MAP_DISPLAY_TABLE1_LINE);
				d_logger.log(l_formatter.toString());
				l_formatter.close();

				l_formatter = new Formatter();
				l_formatter.format(Constants.MAP_DISPLAY_TABLE1_COLUMN_NAMES);
				d_logger.log(l_formatter.toString());
				l_formatter.close();

				l_formatter = new Formatter();
				l_formatter.format(Constants.MAP_DISPLAY_TABLE1_LINE);
				d_logger.log(l_formatter.toString());
				l_formatter.close();
			}

			while (l_iteratorForContinent.hasNext()) {
				Map.Entry<Integer, Continent> l_continentMap = (Map.Entry<Integer, Continent>) l_iteratorForContinent
						.next();
				Integer l_continentId = l_continentMap.getKey();
				Continent l_continent = p_map.getContinents().get(l_continentId);
				Iterator<Country> l_listIterator = l_continent.getCountries().iterator();

				while (l_listIterator.hasNext()) {
					Country l_country = (Country) l_listIterator.next();
					ArrayList<String> l_neighborNames = new ArrayList<>();
					for (Country neighbor : l_country.getNeighbors().values()) {
						l_neighborNames.add(neighbor.getCountryId() + "-" + neighbor.getCountryName());
					}
					String l_neighborValue = String.join(", ", l_neighborNames);
					l_formatter = new Formatter();
					if (p_showArmies) {
						l_formatter.format(l_table2, l_country.getCountryName(), l_country.getCountryId(),
								l_continent.getContinentName(), l_continent.getBonus(), l_neighborValue,
								l_country.getOwner().getName(), l_country.getArmyCount());
					} else {
						l_formatter.format(l_table1, l_country.getCountryName(), l_country.getCountryId(),
								l_continent.getContinentName(), l_continent.getBonus(), l_neighborValue);
					}
					d_logger.log(l_formatter.toString());
					l_formatter.close();
				}
			}
		} catch (Exception e) {
			d_logger.log(Constants.MAP_DISPLAY_CANNOT_DISPLAY_MAP);
		}
	}
}
