package com.w10.risk_game.utils;

import java.util.ArrayList;

import com.w10.risk_game.exceptions.ApplicationException;

public class CommandInterpreter {

	private CommandInterpreter() {
	}

	public static String getMainCommand(String p_command) throws ApplicationException {
		if (p_command.isBlank())
			throw new ApplicationException(Constants.USER_INPUT_COMMAND_INVALID);
		return p_command.split(Constants.REGEX_SPLIT_ON_SPACE)[0];
	}

	public static String[] getArgumentList(String p_Command) throws ApplicationException {
		String[] l_argumentList = p_Command.split(Constants.REGEX_SPLIT_ON_SPACE);
		if (l_argumentList.length == 0)
			throw new ApplicationException(Constants.USER_INPUT_COMMAND_INVALID);
		return l_argumentList;
	}
}
