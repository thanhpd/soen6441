package com.w10.risk_game.commands;

import com.w10.risk_game.models.Country;

public class Advance extends Order{
		private Country d_countryNameFrom;
		private Country d_countryNameTo;

	public Country getCountryNameFrom() {
		return d_countryNameFrom;
	}

	public Country getCountryNameTo() {
		return d_countryNameTo;
	}

	@Override
		public void execute() {

		}
}
