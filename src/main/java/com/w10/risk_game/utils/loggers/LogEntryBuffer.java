package com.w10.risk_game.utils.loggers;

import java.util.ArrayList;
import java.util.List;

import com.w10.risk_game.utils.Observable;
import com.w10.risk_game.utils.Observer;

public class LogEntryBuffer implements Observable {

	private static LogEntryBuffer Logger;

	List<Observer> d_observers = new ArrayList<>();

	private LogEntryBuffer() {
	}

	public static LogEntryBuffer getInstance() {
		if (Logger == null)
			Logger = new LogEntryBuffer();
		return Logger;
	}

	@Override
	public void notifyObservers(String p_data) {
		this.d_observers.forEach(l_observer -> l_observer.update(p_data));
	}

	@Override
	public void attach(Observer p_observer) {
		this.d_observers.add(p_observer);
	}

	@Override
	public void detach() {
		this.d_observers.clear();
	}

	public void log(String p_data) {
		notifyObservers(p_data);
	}

}
