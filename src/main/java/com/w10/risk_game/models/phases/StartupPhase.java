package com.w10.risk_game.models.phases;

import java.util.HashSet;
import java.util.Set;

import com.w10.risk_game.models.Command;
import com.w10.risk_game.utils.Phase;

public class StartupPhase extends GamePlayPhase{
    @Override
    public void type(){
        
    }

    @Override
    public Set<Command> getAvailableCommands() {
       var set =  new HashSet<Command>();
       //set.add(Command.EDIT_CONTINENT);
       // set.add (new EditContinentCommnad());
       return set;
    }
}
