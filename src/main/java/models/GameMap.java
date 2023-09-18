package main.java.models;
import java.util.HashMap;
import java.util.Scanner;

public class GameMap {
    Scanner scanner;
     public void loadmap(String filepath){
        scanner= new Scanner(filepath);
            HashMap<String,Integer> map = new HashMap<String,Integer>();
            String line;
            while(scanner.hasNextLine()){
                line=scanner.nextLine();
                if(line.equals("[continents]")){
                line=scanner.nextLine();
                String[] splitted =line.split(" ");
                map.put(splitted[0],Integer.parseInt(splitted[1]));
                }
                if(line.equals("[countries]")){
                    break;
                }
                
            }

         
        }
}
