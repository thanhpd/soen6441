package com.w10.risk_game.models.tournament;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.LookAndFeel;

import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.phases.PreLoadPhase;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

public class Tournament {

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	public static String Command = "";
	public Tournament(){

	}
	public void start(){
		boolean l_exit = false;
		Player l_player;

		while (!l_exit) {
	try {
				// Display a user input request
				Logger.log("You are in Tournament Game Mode");
				Logger.log("Sample Command: tournament -M Map1.map,Map2.map -P strategy1,strategy2 -G noOfGames -D noOfTurns");
				Logger.log("Available commands: [tournament, exit]");
				Logger.log(Constants.USER_INPUT_REQUEST);
				

				// Create a Scanner to read the input from the user
				Scanner l_scanner = new Scanner(System.in);

				// Read the user's input and log the command that was entered
				Command = l_scanner.nextLine();
				parseCommand(Command);
				Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + Command);

				///stargame
				if (Command.equals(Constants.USER_INPUT_COMMAND_QUIT))
				l_exit = true;
				
			default :
				Logger.log(Constants.USER_INPUT_ERROR_COMMAND_INVALID);

	}


	}
	public void startTournament() {
		Scanner l_scanner = new Scanner(System.in);
		
		String l_tournamentCommand = l_scanner.nextLine();

		parseCommand(l_tournamentCommand);

	}

	private TournamentOptions parseCommand(String p_tournamentCommand) {
		TournamentOptions d_options = new TournamentOptions();
		List<String> l_commandList = Arrays.asList(p_tournamentCommand.split(" "));
		String l_MapValue = l_commandList.get(l_commandList.indexOf("-M") + 1);
		String l_PlayerTypes = l_commandList.get(l_commandList.indexOf("-P") + 1);
		String l_GameCount = l_commandList.get(l_commandList.indexOf("-G") + 1);
		String l_maxTries = l_commandList.get(l_commandList.indexOf("-D") + 1);
		d_options.getMap().addAll(Arrays.asList(l_MapValue.split(",")));
		d_options.getPlayerStrategies().addAll(Arrays.asList(l_PlayerTypes.split(",")));
		int l_NumOfGames = Integer.parseInt(l_GameCount);
		int l_NumofTurns = Integer.parseInt(l_maxTries);
		d_options.setGames(l_NumOfGames);
		d_options.setMaxTries(l_NumofTurns);
		return d_options;
		 
	}

}
