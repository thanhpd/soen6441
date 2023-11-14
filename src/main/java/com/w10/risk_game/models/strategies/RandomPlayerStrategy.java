package com.w10.risk_game.models.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.w10.risk_game.commands.Advance;
import com.w10.risk_game.commands.Order;
import com.w10.risk_game.models.Country;
import com.w10.risk_game.models.Player;
import com.w10.risk_game.utils.Constants;

public class RandomPlayerStrategy extends PlayerStrategy {

	public RandomPlayerStrategy(Player p_player) {
		super(p_player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean issueDeployOrder(String[] p_inputArray) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'issueDeployOrder'");
	}

	@Override
	public boolean issueAdvanceOrder(String[] p_inputArray) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'issueAdvanceOrder'");
	}

	@Override
	public void issueOrder() {
		deployOnRandomCountry();
		attackRandomNeighbor();
		moveArmiesRandomCountry();
	}

	/**
	 * Deploys
	 */
	protected void deployOnRandomCountry() {

	}
	protected void attackRandomNeighbor() {
		Map<Country, ArrayList<Country>> countryAttackbleNeighbors = new HashMap<>();

		for (Country country : d_Player.getCountriesOwned()) {
			if (country.getArmyCount() > 0) {

				var enemies = getAtackbleNeighbors(country);

				if (enemies.size() > 0) {
					countryAttackbleNeighbors.put(country, enemies);
				}
			}
		}

		if (countryAttackbleNeighbors.size() < 1)
			return;

		// randmonize
		Random random = new Random();
		var countryEnemies = new ArrayList<>(countryAttackbleNeighbors.entrySet());
		var randomSelectedIndex = random.nextInt(countryEnemies.size());
		var countryEnemiesEntrySet = countryEnemies.get(randomSelectedIndex);

		var selectedOwnCountry = countryEnemiesEntrySet.getKey();
		var enemies = countryEnemiesEntrySet.getValue();
		var targetEnemyCountry = enemies.get(random.nextInt(enemies.size()));
		var armyCountToAttackWith = targetEnemyCountry.getArmyCount();// random.nextInt(selectedEnemyCountry.getArmyCount())
																		// + 1;

		// add orders
		Order l_order = new Advance(selectedOwnCountry, targetEnemyCountry, armyCountToAttackWith);
		d_Player.getOrders().add(l_order);
		Logger.log(Constants.PLAYER_ISSUE_ORDER_SUCCEED);
	}

	protected void moveArmiesRandomCountry() {

	}

	private ArrayList<Country> getAtackbleNeighbors(Country ownCountry) {
		ArrayList<Country> enemies = new ArrayList<>();
		for (var neighbor : ownCountry.getNeighbors().values()) {
			if (neighbor.getOwner() != d_Player) {
				enemies.add(neighbor);
			}
		}
		return enemies;
	}
}
