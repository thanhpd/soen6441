package com.w10.risk_game.models;

public class MatchResult {
	public final String l_playerName;
	public final int l_gameCount;
	public final String l_map;

	public MatchResult(String p_playerName, int p_gameCount, String p_map) {
		this.l_playerName = p_playerName;
		this.l_gameCount = p_gameCount;
		this.l_map = p_map;
	}

}
