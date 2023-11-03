package com.w10.risk_game.commands;

import com.w10.risk_game.utils.Constants;

public enum Command {

	LOAD_MAP(Constants.USER_INPUT_COMMAND_LOADMAP), SHOW_MAP(Constants.USER_INPUT_COMMAND_SHOWMAP), EDIT_CONTINENT(
			Constants.USER_INPUT_COMMAND_EDIT_CONTINENT), EDIT_COUNTRY(
					Constants.USER_INPUT_COMMAND_EDIT_COUNTRY), EDIT_NEIGHBOR(
							Constants.USER_INPUT_COMMAND_EDIT_NEIGHBOR), GAME_PLAYER_REMOVE(
									Constants.CLI_GAME_PLAYER_REMOVE), GAME_PLAYER_CREATE(
											Constants.CLI_GAME_PLAYER_CREATE), ASSIGN_COUNTRIES(
													Constants.CLI_ASSIGN_COUNTRIES), DEPLOY(
															Constants.USER_INPUT_ISSUE_ORDER_COMMAND_DEPLOY), ADVANCE(
																	Constants.USER_INPUT_ISSUE_ORDER_COMMAND_ADVANCE), BOMB(
																			Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BOMB), BLOCKADE(
																					Constants.USER_INPUT_ISSUE_ORDER_COMMAND_BLOCKADE), AIRLIFT(
																							Constants.USER_INPUT_ISSUE_ORDER_COMMAND_AIRLIFT), NEGOTIATE(
																									Constants.USER_INPUT_ISSUE_ORDER_COMMAND_NEGOTIATE);

	private final String name;

	Command(String commandName) {
		this.name = commandName;
	}

	public String getName() {
		return name;
	}
}
