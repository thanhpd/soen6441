package com.w10.risk_game.utils.loggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.Observer;

/**
 * The FileLogger class is an implementation of the Observer interface that
 * writes data to a file.
 *
 * @author Sherwyn Dsouza
 */
public class FileLogger implements Observer {
	BufferedWriter writer;

	/**
	 * The constructor for the FileLogger class. It creates a new file `log.txt` if
	 * not present and clears all existing data in the file.
	 */
	public FileLogger() {
		try {
			File l_file = new File(Constants.LOGGER_FILE_NAME);
			this.writer = new BufferedWriter(new FileWriter(l_file));
			this.writer.write("");
			this.writer.flush();
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The update function appends a string to a log file.
	 *
	 * @param p_data
	 *            The parameter "p_data" is a string that represents the data to be
	 *            written to the file.
	 */
	@Override
	public void update(String p_data) {
		try {
			this.writer = new BufferedWriter(new FileWriter(Constants.LOGGER_FILE_NAME, true));
			this.writer.append(p_data);
			this.writer.newLine();
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
