package main.java.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.java.models.GameMap;

public class MapReader {

    public String getMapFolerPath() {
        return System.getProperty("user.dir") + "/src/main/java/resources/maps/";
    }

    public GameMap readMapFile(String mapFilename) {
        try {
            var path = getMapFolerPath() + "" + mapFilename;
            FileReader reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            return null;
        }
    }


