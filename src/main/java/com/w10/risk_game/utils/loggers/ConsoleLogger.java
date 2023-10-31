package com.w10.risk_game.utils.loggers;

import com.w10.risk_game.utils.Observer;

public class ConsoleLogger implements Observer {
	@Override
	public void update(String p_data) {
		System.out.println(p_data);
	}
}
