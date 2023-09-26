package com.w10.risk_game;

import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.MapDisplay;
import com.w10.risk_game.utils.MapReader;

/**
 * @author Omnia Alam
 *
 */
public class Load {
	public static void main(String[] args) throws InterruptedException {

		String l_mapName = "europe.map";
		MapReader l_mapReader = new MapReader();
		GameMap l_gameMap = l_mapReader.loadMapFile(l_mapName);
		MapDisplay displayMap = new MapDisplay();
		displayMap.formatMap(l_gameMap, true);

	}
}
