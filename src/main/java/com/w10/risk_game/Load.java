package com.w10.risk_game;

import utils.MapReader;

/**
 * @author Omnia Alam
 * 
 */
public class Load {
    public static void main(String[] args) throws InterruptedException {

        String mapName = "europe.map";
        MapReader mapReader = new MapReader();
        mapReader.readMapFile(mapName);

    }
}
