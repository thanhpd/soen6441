package com.w10.risk_game.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.LookAndFeel;

import com.w10.risk_game.engines.TournamentEngine;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.models.phases.PreLoadPhase;
import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

public class TournamentModeController {

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	public static String Command = "";
	public TournamentModeController(){

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
				
				//Quit command
				if (Command.equals(Constants.USER_INPUT_COMMAND_QUIT))
				l_scanner.close();
				l_exit = true;
				
	
	}catch (Exception e) {
		Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
		Logger.log(e.getMessage());
		Logger.log("");
	}
}
	}

	private void parseCommand(String p_tournamentCommand) {
		List<String> l_commandList= new ArrayList<String>();
		l_commandList = Arrays.asList(p_tournamentCommand.split(" "));
		Set<String> l_maps= new HashSet<String>();
		Set<String> l_players= new HashSet<String>();
		int noOfGames=0;
		int noOfTurns=0;
		for(int i=0; i<l_commandList.size();i++){
			if (l_commandList.get(i).equals("-M")){
				int j=i+1;
				while(!l_commandList.equals("-")){
					l_maps.add(l_commandList.get(i));
					j++;
				}	

			}
			if (l_commandList.get(i).equals("-P")){
				int j=i+1;
				while(!l_commandList.equals("-")){
					l_players.add(l_commandList.get(j));
					j++;
				}	

			}

			if (l_commandList.get(i).equals("-G")){
				noOfGames=Integer.parseInt(l_commandList.get(i++));
			}
			if (l_commandList.get(i).equals("-D")){
				noOfTurns=Integer.parseInt(l_commandList.get(i++));
			}

		}
		TournamentEngine tournamentEngine= new TournamentEngine();
		tournamentEngine.startGame(l_players,l_maps,noOfGames,noOfTurns);
		
	}
}
