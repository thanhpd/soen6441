package com.w10.risk_game.models;

/**
 * The MatchResult class represents the result of a match, including the
 * player's name, game count, and map.
 */
public class MatchResult {
	public final String d_playerName;
	public final int d_gameCount;
	public final String d_map;

	public MatchResult(String p_playerName, int p_gameCount, String p_map) {
		this.d_playerName = p_playerName;
		this.d_gameCount = p_gameCount;
		this.d_map = p_map;
	}

}
