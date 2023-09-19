package com.w10.risk_game.utils;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;



public class MapReader {
    Country country= new Country();
    Continent continent= new Continent();

    public String getMapFolerPath() {
        return System.getProperty("user.dir") + "/src/main/resources/maps/";
    }

    public Country mapCountry(String line){
        String[] splitted =line.split(" ");
        country.countryId(Integer.parseInt(splitted[0]));
        country.countryName(splitted[1]);
        country.continentId(Integer.parseInt(splitted[2]));
        return country;
    }

    public Continent mapContinent(String line){
        String[] splitted =line.split(" ");
        continent.continentId(Integer.parseInt(splitted[0]));
        continent.continentName(splitted[1]);
        return continent; 
    }

    public void readMapFile(String mapFilename) {
     
            String path = getMapFolerPath() + "" + mapFilename;
            try {
                FileReader reader= new FileReader(path);
        
            Scanner scanner= new Scanner(reader);
            String line;


            while(scanner.hasNextLine()){
                line=scanner.nextLine();
                    if(line.equals("[countries]")){
                        while(scanner.hasNextLine()){
                        line=scanner.nextLine();
                        //if(line.equals("[continents]")&&!line.equals("[countries]")&&!line.isEmpty()){
                        //mapContinent(line);
                        //}
                        
                        if(line.equals("[continents]")||line.equals("[borders]")||line.isEmpty()){
                        break;
                        }
                        mapCountry(line);
                        //System.out.println(mapCountry(line).countryId()+"-"+mapCountry(line).countryName());      
                    }
                    }
                
            }

           
            

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
         
        
        }
    }
    


