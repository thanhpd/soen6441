package com.w10.risk_game;

import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.utils.MapEditor;
import com.w10.risk_game.utils.MapReader;

/**
 * @author Omnia Alam
 * 
 */
public class Load {
    public static void main(String[] args) throws InterruptedException {

        String mapName = "europe.map";
        MapReader mapReader = new MapReader();
        GameMap gameMap=mapReader.readMapFile(mapName);
        MapEditor mapEditor= new MapEditor(gameMap);


    }
}
