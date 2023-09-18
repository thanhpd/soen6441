package main.java.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.java.models.GameMap;

public class MapReader {

    public String getMapFolerPath() {
        return System.getProperty("user.dir") + "/src/main/java/resources/maps/";
    }

    public void readMapFile(String mapFilename) {
     
            var path = getMapFolerPath() + "" + mapFilename;
            try {
                FileReader reader= new FileReader(path);
        
            Scanner scanner= new Scanner(reader);
            HashMap<Integer,String> countryMap = new HashMap<Integer,String>();
            String line;
/**
 * Hash parse: load countries in to hasmap.
 */

            while(scanner.hasNextLine()){
                line=scanner.nextLine();
                if(line.equals("[countries]")){
                    while(scanner.hasNextLine()){
                        line=scanner.nextLine();
                        if(line.equals("[continents]")||line.equals("[borders]")||line.isEmpty()){
                        break;
                        }
                        String[] splitted =line.split(" ");
                        countryMap.put(Integer.parseInt(splitted[0]),splitted[1]);
                        System.out.println(line);
                        
                            
                
                    }
                
                }
                
                
            }

            countryMap.forEach((key, value) -> System.out.println(key + " " + value));

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
         
        
        }
    }
    


