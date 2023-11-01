package com.w10.risk_game.commands;

import com.w10.risk_game.utils.Constants;

public enum Command {

    LOAD_MAP (Constants.USER_INPUT_COMMAND_LOADMAP),
    SHOW_MAP(Constants.USER_INPUT_COMMAND_SHOWMAP),
    EDIT_CONTINENT (Constants.USER_INPUT_COMMAND_EDIT_CONTINENT),
	EDIT_COUNTRY (Constants.USER_INPUT_COMMAND_EDIT_COUNTRY),
	EDIT_NEIGHBOR (Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR);
    
    private final String name;

    Command(String commandName){
        this.name = commandName;
    }

    public String getName(){
        return name;
    }
}
