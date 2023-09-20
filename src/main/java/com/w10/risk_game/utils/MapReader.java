package com.w10.risk_game.utils;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;



public class MapReader {
    
    Continent continent= new Continent();
    ArrayList<Country> countries = new ArrayList<Country>();
    Map<Integer, ArrayList<Integer>> adjacentCountriesMap = new HashMap<>();

    public String getMapFolerPath() {
        return System.getProperty("user.dir") + "/src/main/resources/maps/";
    }

    public void mapCountry(String line){
        Country country= new Country();
        String[] splitted =line.split(" ");
        country.countryId(Integer.parseInt(splitted[0]));
        country.countryName(splitted[1]);
        country.continentId(Integer.parseInt(splitted[2]));
        countries.add(country);
    }

    
    public Continent mapContinent(String line){
        String[] splitted =line.split(" ");
        continent.continentId(Integer.parseInt(splitted[0]));
        continent.continentName(splitted[1]);
        return continent; 
    }

    public void mapAdjacentCountry(String line){
        Country country= new Country();
        String[] splitted =line.split(" ");
        int key=Integer.parseInt(splitted[0]);
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 1; i < splitted.length; i++) {
            values.add(Integer.parseInt(splitted[i]));
        }
        country.adjacentCountries(key, values);
        adjacentCountriesMap.put(key, values);
    }

    public void readMapFile(String mapFilename) {
     
            String path = getMapFolerPath() + "" + mapFilename;
            try {
                FileReader reader= new FileReader(path);
        
            Scanner scanner= new Scanner(reader);
            String line;


           /*  while(scanner.hasNextLine()){
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
            }*/
                while(scanner.hasNextLine()){
                line=scanner.nextLine();
                    if(line.equals("[borders]")){
                        while(scanner.hasNextLine()){
                        line=scanner.nextLine();
                        if(line.equals("[continents]")||line.equals("[countries]")||line.isEmpty()){
                        break;
                        }
                        mapAdjacentCountry(line);
                    }
                    }
            }

            
            for (int i = 0; i < countries.size(); i++) {
                System.out.println(countries.get(i));
                }
           System.out.println(adjacentCountriesMap);
            

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
         
        
        }
    }
    


