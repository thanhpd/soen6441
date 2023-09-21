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

    /**
     * 
     * @return the map file directory
     */
    public String getMapFolerPath() {
        return System.getProperty("user.dir") + "/src/main/resources/maps/";
    }

    /**
     * 
     * 
     * @param scanner
     * @return Returns a list of countries with their id as key
     */

    public Map<Integer, Country> readCountries(Scanner scanner) {

        String line;
        HashMap<Integer, Country> countries = new HashMap<Integer, Country>();
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.equals("[continents]") || line.equals("[borders]") || line.isEmpty()) {
                break;
            }

            Country country = mapCountry(line);
            countries.put(country.getCountryId(), country);

        }
        return countries;
    }

    /**
     * 
     * @return Returns a list of neighbouring countries with their parent country id
     */

    public Map<String, Country> parseBorders() {
        return null;
    }

    /**
     * 
     * @param scanner
     * @return Returns a list of contnents with their id as key
     */
    public Map<Integer, Continent> readCointinents(Scanner scanner) {

        String line;
        HashMap<Integer, Continent> continents = new HashMap<Integer, Continent>();
        int continentId=1;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.equals("[countries]") || line.equals("[borders]") || line.isEmpty()) {
                break;
            }
            
            Continent continent = mapContinent(line,continentId);
            continents.put(continent.getContinentId(), continent);
            continentId++;
        }
        return continents;
    }

    /**
     * 
     * @param line
     * @return returns Country objects for each line
     */

    public Country mapCountry(String line) {
        Country country = new Country();
        String[] splitted = line.split(" ");
        country.setContinentId(Integer.parseInt(splitted[0]));
        country.setCountryName(splitted[1]);
        country.setContinentId(Integer.parseInt(splitted[2]));
        return country;
    }

    /**
     * 
     * @param line
     * @return returns Continent objecct for each line
     */

    public Continent mapContinent(String line,int continentId) {
        Continent continent = new Continent();
        String[] splitted = line.split(" ");
        continent.setContinentId(continentId);
        continent.setContinentName(splitted[0]);
        return continent;
    }

    /**
     * 
     * @param line
     * 
     */
    public void mapAdjacentCountry(String line) {

        Country country = new Country();
        String[] splitted = line.split(" ");
        int key = Integer.parseInt(splitted[0]);
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 1; i < splitted.length; i++) {
            values.add(Integer.parseInt(splitted[i]));
        }
        country.adjacentCountries(key, values);
    }

    /**
     * Reading the map file
     * 
     * @param mapFilename
     */
    public void readMapFile(String mapFilename) {
        Map<Integer, Country> countries = new HashMap<Integer, Country>();
        Map<Integer, Continent> continents = new HashMap<Integer, Continent>();

        String path = getMapFolerPath() + "" + mapFilename;
        try {
            FileReader reader = new FileReader(path);

            Scanner scanner = new Scanner(reader);
            String line;

            // read unitl continents
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.equals("[continents]")) {
                    continents=readCointinents(scanner);
                    break;
                }
            }
            
            // read until countries
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.equals("[countries]")) {
                    countries = readCountries(scanner);
                    
                    break;
                }
            }

            // read unitl borders
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.equals("[borders]")) {
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        if (line.equals("[continents]") || line.equals("[countries]") || line.isEmpty()) {
                            break;
                        }
                        mapAdjacentCountry(line);
                    }
                }
            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

    }
}
