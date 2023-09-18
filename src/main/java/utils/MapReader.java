package main.java.utils;

import main.java.models.GameMap;

public class MapReader {

    public String getMapFolerPath() {
        return System.getProperty("user.dir") + "/src/main/java/resources/maps/";
    }

    public void readMapFile(String mapFilename) {
     
            var path = getMapFolerPath() + "" + mapFilename;
            GameMap gameMap =new GameMap();
            gameMap.loadmap(path);

        }
    }


