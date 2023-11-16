package com.w10.risk_game.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.exceptions.ApplicationException;


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
        
        // var tournament = new TournamentController();
        // tournament.(Set.of("Aggressive", "Cheater", "Random"),
        //      Set.of("europe.map", "test.map"),
        //       2, 2);
		
    }
}
