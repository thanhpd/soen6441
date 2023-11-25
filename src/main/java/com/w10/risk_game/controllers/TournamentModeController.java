package com.w10.risk_game.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.LookAndFeel;

import com.w10.risk_game.App;
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
		App l_app= new App();

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
				List<String> l_mainCommand= new ArrayList<String>();
				l_mainCommand = Arrays.asList(Command.split(" "));
				switch (l_mainCommand.get(0)) {
				case Constants.USER_INPUT_COMMAND_TOURNAMENTMODE :
				parseCommand(Command);
				//Logger.log(Constants.USER_INPUT_COMMAND_ENTERED + Command);
				
				//Quit command
				case Constants.USER_INPUT_COMMAND_QUIT :
				l_app.startGame();
				l_scanner.close();
				l_exit = true;
				
				default :
						Logger.log(Constants.USER_INPUT_ERROR_COMMAND_INVALID);
					}
					Logger.log("");
					if (l_exit) {
						break;
					}
			}
	
	catch (Exception e) {
		Logger.log(Constants.USER_INPUT_ERROR_SOME_ERROR_OCCURRED);
		Logger.log(e.getMessage());
		Logger.log("");
	}
}
	}

	private void parseCommand(String p_tournamentCommand) {
		List<String> l_commandList= new ArrayList<String>();
		l_commandList = Arrays.asList(p_tournamentCommand.split(" "));
		
		TournamentEngine tournamentEngine= new TournamentEngine();
		tournamentEngine.startGame(extractValues(p_tournamentCommand, "-P"),extractValues(p_tournamentCommand, "-M"),extractValue(p_tournamentCommand, "-G"),extractValue(p_tournamentCommand, "-D"));
		
	}


	 public static Set<String> extractValues(String command, String flag) {
        Set<String> extractedValues = new HashSet<String>();
        
        // Define the pattern for extracting values after the flag
        Pattern pattern = Pattern.compile(flag + "\\s(\\S+)");
        Matcher matcher = pattern.matcher(command);
        
        if (matcher.find()) {
            String values = matcher.group(1); // Extract values captured by the group
            String[] splitValues = values.split(",");
            
            for (String value : splitValues) {
                extractedValues.add(value);
            }
        }
        
        return extractedValues;
    }

	public static int extractValue(String command, String flag) {
        String extractedValue = "";
        
        // Define the pattern for extracting value after the flag
        Pattern pattern = Pattern.compile(flag + "\\s(\\S+)");
        Matcher matcher = pattern.matcher(command);
        
        if (matcher.find()) {
            extractedValue = matcher.group(1); // Extract value captured by the group
        }
        
        return Integer.parseInt(extractedValue);
    }
}
