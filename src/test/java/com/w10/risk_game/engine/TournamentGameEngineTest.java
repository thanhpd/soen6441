package com.w10.risk_game.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.exceptions.ApplicationException;
import com.w10.risk_game.utils.TournamentCommandInterpreter;

public class TournamentGameEngineTest {
 public static String l_mainCommand;
 public String [] l_listofMaps;
    @BeforeEach
    public void setup(){
        l_mainCommand = "tournament -M euprope.map test.map -D";
    }
    @Test
    void testGetMainCommand() {

    }


    @Test
    void testGetGame() {

    }

    @Test
    void testGetMapEditorController() {

    }

    @Test
    void testStart() {
    String[] l_argList;
    try {
        l_argList = TournamentCommandInterpreter.GetArgumentList(l_mainCommand);
         System.out.println(TournamentCommandInterpreter.getListofMaps(l_argList));
    } catch (ApplicationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
   
    
		
    }
}
