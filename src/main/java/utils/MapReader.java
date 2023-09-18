package main.java.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

import main.java.models.Country;

public class MapReader {
    Country country= new Country();

    public String getMapFolerPath() {
        return System.getProperty("user.dir") + "/src/resources/maps/";
    }

    public Country mapCountry(String line){
        String[] splitted =line.split(" ");
        country.countryId(Integer.parseInt(splitted[0]));
        country.countryName(splitted[1]);
        country.continentId(Integer.parseInt(splitted[2]));
        return country;
    }

    public void readMapFile(String mapFilename) {
     
            var path = getMapFolerPath() + "" + mapFilename;
            try {
                FileReader reader= new FileReader(path);
        
            Scanner scanner= new Scanner(reader);
            HashMap<Integer,String> countryMap = new HashMap<Integer,String>();
            String line;
/**
 * Hash parse: load countries in to hashmap.
 */

            while(scanner.hasNextLine()){
                line=scanner.nextLine();
                if(line.equals("[countries]")){
                    while(scanner.hasNextLine()){
                        line=scanner.nextLine();
                        if(line.equals("[continents]")||line.equals("[borders]")||line.isEmpty()){
                        break;
                        }
                        mapCountry(line);
                       
                        
                            
                
                    }
                
                }
                
                
            }

           
            System.out.println(countryMap);

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
         
        
        }
    }
    


