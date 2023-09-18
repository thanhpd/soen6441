package main.java.View;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class ShowMap {
    public void showmap(String filename){
        String filePath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\Resources\\Maps\\"+filename ;
		File file = new File(filePath);
        Scanner readMap= new Scanner(filePath);
         HashMap<String,Integer> continents= new HashMap<>(0, 0);
        if(readMap.hasNext()){
            if(readMap.nextLine().equals("[Map]")){
                //if()
            }

                if(readMap.nextLine().equals("[Continents]")){
               continents = readContinents(readMap);
            }
            }
        }
        public HashMap<String,Integer> readContinents(Scanner sc){
            HashMap<String,Integer> map = new HashMap<>(0, 0);
            String line;
            while(sc.hasNext()){
                line=sc.nextLine();
                if(line.equals("[Territories]")){
                    break;
                }

                String[] splitted =line.split("=");
                map.put(splitted[0],Integer.parseInt(splitted[1]));
            }

            return map;
        }
    }

