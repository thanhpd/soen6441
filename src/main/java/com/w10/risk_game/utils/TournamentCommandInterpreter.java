package com.w10.risk_game.utils;

import java.util.ArrayList;

import com.w10.risk_game.exceptions.ApplicationException;

public class TournamentCommandInterpreter {
    /**
	 * The function takes a command as input and splits it into an array of
	 * arguments, throwing an exception if the command is invalid.
	 *
	 * @param p_command
	 *            The parameter `p_command` is a string that represents a command.
	 * @throws ApplicationException
	 *             If the user enters an invalid command, show 'Please enter a valid
	 *             command!' to the user
	 *
	 * @return The method is returning a String array.
	 */
	public static String[] GetArgumentList(String p_command) throws ApplicationException {
		String[] l_argumentList = p_command.split(Constants.REGEX_SPLIT_ON_SPACE);
		if (l_argumentList.length == 0)
			throw new ApplicationException(Constants.USER_INPUT_ERROR_COMMAND_EMPTY);
		return l_argumentList;
	}
    

    public static ArrayList<String> getListofMaps(String p_argList[]){
        ArrayList<String> l_listofMaps= new ArrayList<>();
        int i=0;
        while(p_argList[i].equals("-M")){
            l_listofMaps.add(p_argList[i]);
            if(p_argList[i].equals("-")){
            break;
        }
         i++;
        }
        return l_listofMaps;
    }
        public static ArrayList<String> getListofPlayers(String p_argList[]){
        ArrayList<String> l_listofMaps= new ArrayList<>();
        int i=0;
        while(p_argList[i].equals("-D")){
            l_listofMaps.add(p_argList[i]);
            if(p_argList[i].equals("-")){
            break;
        }
         i++;
        }
        return l_listofMaps;
    }
}
