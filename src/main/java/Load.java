package main.java;


import main.java.utils.MapReader;

/**
 * @author Omnia Alam
 * 
 */
public class Load {
    public static void main(String[] args)throws InterruptedException{

        var mapName = "europe.map";
        MapReader mapReader=new MapReader();
        mapReader.readMapFile(mapName);
    
    }
}
