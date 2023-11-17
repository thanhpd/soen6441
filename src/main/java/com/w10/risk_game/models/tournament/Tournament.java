package com.w10.risk_game.models.tournament;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.w10.risk_game.utils.loggers.LogEntryBuffer;

public class Tournament {

	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();

	// Default constructor
	public Tournament() {
		// You can initialize variables or perform other setup tasks here
	}

	public void startTournament() {
		Scanner l_Scanner = new Scanner(System.in);
		Logger.log("You are in Tournament Game Mode");
		Logger.log("Sample Command: tournament -M Map1.map,Map2.map -P strategy1,strategy2 -G noOfGames -D noOfTurns");
		String l_TournamentCommand = l_Scanner.nextLine();

		parseCommand(l_TournamentCommand);

	}

	private void parseCommand(String p_TournamentCommand) {
		TournamentOptions d_Options = new TournamentOptions();
		List<String> l_CommandList = Arrays.asList(p_TournamentCommand.split(" "));
		String l_MapValue = l_CommandList.get(l_CommandList.indexOf("-M") + 1);
		String l_PlayerTypes = l_CommandList.get(l_CommandList.indexOf("-P") + 1);
		String l_GameCount = l_CommandList.get(l_CommandList.indexOf("-G") + 1);
		String l_maxTries = l_CommandList.get(l_CommandList.indexOf("-D") + 1);
		d_Options.getMap().addAll(Arrays.asList(l_MapValue.split(",")));
		d_Options.getPlayerStrategies()
				.addAll(Arrays.asList(l_PlayerTypes.split(",")));
		int l_NumOfGames = Integer.parseInt(l_GameCount);
		int l_NumofTurns = Integer.parseInt(l_maxTries);
		d_Options.setGames(l_NumOfGames);
		d_Options.setMaxTries(l_NumofTurns);

		// System.out.println("Print");
		// System.out.println(d_Options.getMap());
		// System.out.println(d_Options.getPlayerStrategies());
		// System.out.println(d_Options.getMaxTries());
		// System.out.println(d_Options.getGames());
	}

}
