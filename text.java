import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class text {
	int id;
	String name;
	List<Integer> neighboringCountries;

	public Country(int id, String name, List<Integer> neighboringCountries) {
		this.id = id;
		this.name = name;
		this.neighboringCountries = neighboringCountries;
	}

	public String toString() {
		return "ID: " + id + ", Name: " + name + ", Neighbors: " + neighboringCountries;
	}
}

public class CountryDataExtraction {
	public static void main(String[] args) {
		String[] data = {
				"16 Italy 3 17 18 14 15",
				"21 France 4 1 22 6 24 15",
				"6 Belgium 1 1 21 7 24 9",
				"23 Portugal 4 22",
				"11 Czech_Rep 2 9 10 12 14",
				"1 England 1 2 3 4 21 5 6 7 8",
				"3 N_Ireland 1 1 2",
				"12 Slovakia 2 10 11 13 14",
				"10 Poland 2 8 9 11 12",
				"18 Sardinia 3 16 17 19 20",
				"20 Majorca 4 18 19 22",
				"22 Spain 4 4 20 21 23"
		};

		List<Country> countries = new ArrayList<>();

		for (String entry : data) {
			String[] parts = entry.split(" ", 2); // Split the string into ID, name, and neighbors
			int id = Integer.parseInt(parts[0]);
			String[] countryData = parts[1].split(" ", 2); // Split country name and neighbors
			String name = countryData[0];
			String[] neighborIDs = countryData[1].split(" ");

			List<Integer> neighbors = new ArrayList<>();
			for (String neighborID : neighborIDs) {
				neighbors.add(Integer.parseInt(neighborID));
			}

			Country country = new Country(id, name, neighbors);
			countries.add(country);
		}

		// Print the extracted data
		for (Country country : countries) {
			System.out.println(country);
		}
	}
}
