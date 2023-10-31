package com.w10.risk_game.utils.loggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.w10.risk_game.utils.Constants;
import com.w10.risk_game.utils.Observer;

public class FileLogger implements Observer {
	BufferedWriter writer;

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
