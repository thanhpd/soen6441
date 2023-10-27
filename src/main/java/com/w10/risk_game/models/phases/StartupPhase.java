package com.w10.risk_game.models.phases;

import java.util.HashSet;
import java.util.Set;

import com.w10.risk_game.controllers.GameEngine;
import com.w10.risk_game.models.Command;
import com.w10.risk_game.models.Phase;

public class StartupPhase extends GamePlayPhase{

    @Override
    void performAction(GameEngine p_engine) {
       // p_engine.init?
    }
    // @Override
    // public void type(){
        
    // }

    // @Override
    // public Set<Command> getAvailableCommands() {
    //    var set =  new HashSet<Command>();
    //    //set.add(Command.EDIT_CONTINENT);
    //    // set.add (new EditContinentCommnad());
    //    return set;
    // }

    
}
