package com.w10.risk_game.utils;
import java.util.Set;

import com.w10.risk_game.models.Command;

public interface Phase {
    Set<Command> getAvailableCommands();
    void type(); 
    
} 
