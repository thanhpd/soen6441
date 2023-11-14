package com.w10.risk_game.models.strategies;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.w10.risk_game.controllers.GamePlayController;
import com.w10.risk_game.controllers.MapEditorController;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.GameMap;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

public class RandomPlayerStrategyTest {
	private GamePlayController d_gamePlayController;
	private MapEditorController d_mapEditorController;
	Player d_player1;
	Player d_player2;
	GameMap d_GameMap;
	@BeforeEach
	public void setup() {
		d_mapEditorController = new MapEditorController();
		String l_mapFilePath = Constants.DEFAULT_GAME_MAP_TEST_FOLDER_PATH + "europe.map";
		d_mapEditorController.loadMap(l_mapFilePath);
		d_gamePlayController = new GamePlayController(d_mapEditorController);

		d_player1 = new Player("RandomPlayer1", new ArrayList<Country>(), null, 10,
				new RandomPlayerStrategy(d_player1));
		d_player2 = new Player("RandomPlayer2", new ArrayList<Country>(), null, 15,
				new RandomPlayerStrategy(d_player2));

		d_gamePlayController.createPlayer(d_player1.getName());
		d_gamePlayController.createPlayer(d_player2.getName());
		d_GameMap = new GameMap();
		d_gamePlayController.assignCountries();

	}
	@Test
	void testAttackRandomNeighbor() {

	}

	@Test
	void testDeployOnRandomCountry() {

	}

	@Test
	void testIssueAdvanceOrder() {

	}

	@Test
	void testIssueDeployOrder() {

	}

	@Test
	void testIssueOrder() {
		// d_player1.issueOrder();
	}

	@Test
	void testMoveArmiesRandomCountry() {

	}
}
