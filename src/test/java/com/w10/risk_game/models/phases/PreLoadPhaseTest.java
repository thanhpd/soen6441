package com.w10.risk_game.models.phases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.GameEngine;
import com.w10.risk_game.models.Phase;
import com.w10.risk_game.utils.Constants;

public class PreLoadPhaseTest {

    Phase phase;

    @BeforeEach
    public void setup(){

            GameEngine l_GameEngine= new GameEngine();
            GameEngine.SetPhase(new PreLoadPhase(l_GameEngine));
            phase = GameEngine.Phase;
        }

    @Test
    void testPhaseName() {
            assertEquals("PreLoad PHASE", phase.getPhaseName());

    }

        @Test
    void testAvailableCommands() {
            assertEquals("[loadmap]", phase.getAvailableCommands().toString());

    }
   
    @Test
    void testNext() {
        phase.loadMap(Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "test.map");
        phase=GameEngine.Phase;
        assertEquals("PostLoad PHASE", phase.getPhaseName());
    }

   
}
