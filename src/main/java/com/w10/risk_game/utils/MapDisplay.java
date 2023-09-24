package com.w10.risk_game.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.w10.risk_game.models.Continent;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;

import dnl.utils.text.table.TextTable;

public class MapDisplay {

    public String[] populateRow(Country country,Continent continent, boolean showArmies) {
        String[] values= new String[3];

        ArrayList<String> neighborNames = new ArrayList<>();
        for (Country neighbor : country.getNeighbors().values()) {
            neighborNames.add(neighbor.getCountryName());
        }

        String neighborValue = String.join(", ", neighborNames);
        
        values[0]= country.getCountryName();
        if(showArmies){

        }
        values[1] = (country.getContinentId()+"("+continent.getContinentName()+")");
        values[2] = neighborValue;
        
        return values;

    }

    public void formatMap(GameMap map) {
                String[] columnNames = {
                "Country Name",
                "Continent Name",
                "Neighbors"
        };

        Map<Integer, Country> countries = map.getCountries();
         Object[][] data = new Object[countries.size()][columnNames.length];
         int d_count=0;
        for (Map.Entry<Integer, Country> entry : countries.entrySet()) {
            data[d_count]=populateRow(entry.getValue(),map.getContinentById(entry.getValue().getContinentId()), false);
            d_count++;

            
        }


        TextTable tt = new TextTable(columnNames, data);
        tt.setAddRowNumbering(true);
        tt.setSort(0);
        tt.printTable();

    }
}
