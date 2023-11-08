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
	private String d_filePath;

	/**
	 * The constructor for the FileLogger class. It creates a new file `log.txt` if
	 * not present and clears all existing data in the file.
	 *
	 * @param p_filePath
	 *            Path of the log file
	 */
	public FileLogger(String p_filePath) {
		this.d_filePath = p_filePath;
		try {
			File l_file = new File(d_filePath);
			BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_file));
			l_writer.write("");
			l_writer.flush();
			l_writer.close();
		} catch (IOException e) {
			System.out.println(Constants.LOGGER_FILE_ISSUE);
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
		try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(this.d_filePath, true))) {
			l_writer.append(p_data);
			l_writer.newLine();
		} catch (IOException e) {
			System.out.println(Constants.LOGGER_FILE_ISSUE);
		}
	}
}
