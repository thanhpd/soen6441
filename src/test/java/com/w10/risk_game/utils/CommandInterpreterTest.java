package com.w10.risk_game.utils;

import com.w10.risk_game.exceptions.ApplicationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The class contains test cases to validate the correctness of the command
 * interpreter functions.
 */
public class CommandInterpreterTest {

	/**
	 * The function tests whether a command with options can be parsed correctly
	 *
	 * @throws ApplicationException
	 *             if the command is empty
	 */
	@Test
	public void testParsingCommandOptionsCorrect() throws ApplicationException {
		// If the command is empty, the list of options should be empty
		String l_cmdWithoutOptions = "loadmap src/main/resources/maps/europe.map";
		var l_listOfOptions = CommandInterpreter.GetCommandOptions(l_cmdWithoutOptions);
		assertEquals(0, l_listOfOptions.size());

		// If the command has multiple options, the list of options should contain all
		// of them
		String l_cmdWithRepeatingOptions = "editcontinent -add continent1 10 -remove continent1 -add continent2 20 -remove continent2";
		l_listOfOptions = CommandInterpreter.GetCommandOptions(l_cmdWithRepeatingOptions);

		// Check by size of each ArrayList
		assertEquals(4, l_listOfOptions.size());
		assertEquals(3, l_listOfOptions.get(0).size());
		assertEquals(2, l_listOfOptions.get(1).size());
		assertEquals(3, l_listOfOptions.get(2).size());
		assertEquals(2, l_listOfOptions.get(3).size());

		// Check by contents of each ArrayList
		assertTrue(l_listOfOptions.stream()
				.allMatch(l_option -> l_option.get(0).equals("-add") || l_option.get(0).equals("-remove")));
	}
}
