package com.w10.risk_game.utils.loggers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.utils.Constants;

/**
 * The LoggerTest class is a JUnit test class that tests the logging
 * functionality of a Logger class.
 *
 * @author Sherwyn Dsouza
 */
class LoggerTest {
	private static final LogEntryBuffer Logger = LogEntryBuffer.GetInstance();
	private ByteArrayOutputStream d_outputStream;

	/**
	 * The `beforeAllLoggingTests` function sets up logging for tests by attaching a
	 * console logger and a file logger, and redirecting the standard output to a
	 * ByteArrayOutputStream.
	 */
	@BeforeEach
	public void beforeAllLoggingTests() {
		Logger.attach(new ConsoleLogger());
		Logger.attach(new FileLogger(Constants.LOGGER_FILE_TEST_PATH));

		d_outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(d_outputStream));
	}

	/**
	 * The testLogging function tests if a given string is logged correctly to both
	 * the console and a file.
	 */
	@Test
	void testLogging() {
		String l_testString = "Testing Observer Design Pattern for Logging";

		Logger.log(l_testString);

		// Test if logged correctly to the console
		assertEquals(l_testString, d_outputStream.toString().trim());

		// Test if logged correctly to the file
		try {
			File l_file = new File(Constants.LOGGER_FILE_TEST_PATH);
			Scanner l_scanner = new Scanner(l_file);
			String l_data = l_scanner.nextLine();
			assertEquals(l_testString, l_data);
			l_scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
