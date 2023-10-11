package com.w10.risk_game.utils;

import com.w10.risk_game.exceptions.ApplicationException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandInterpreterTest {

	@Test
	public void testParsingCommandOptionsCorrect() throws ApplicationException {
		String l_cmdWithoutOptions = "loadmap src/main/resources/maps/europe.map";
		var l_listOfOptions = CommandInterpreter.GetCommandOptions(l_cmdWithoutOptions);
		assertEquals(0, l_listOfOptions.size());

		String l_cmdWithRepeatingOptions = "editcontinent -add continent1 10 -remove continent1 -add continent2 20 -remove continent2";
		l_listOfOptions = CommandInterpreter.GetCommandOptions(l_cmdWithRepeatingOptions);
		assertEquals(4, l_listOfOptions.size());
		assertEquals(3, l_listOfOptions.get(0).size());
		assertEquals(2, l_listOfOptions.get(1).size());
		assertEquals(3, l_listOfOptions.get(2).size());
		assertEquals(2, l_listOfOptions.get(3).size());
	}
}
