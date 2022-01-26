package search;

public class Airports {

	Trie airportIDs;
	String[] ids = { "ATL", "PEK", "LAX", "DXB", "HND", "LHR", "PVG", "CDG", "DFW", "CAN", "AMS", "HKG", "ICN", "FRA",
			"DEN", "DEL", "SIN", "BKK", "JFK", "KUL", "MAD", "SFO", "CTU", "CGK", "SZK", "BCN", "IST", "SEA", "LAS" }; 

	public Airports() {
		airportIDs = new Trie();
	}

	public Trie createAirportIDs() {
		for (String id : ids) {
			airportIDs.insertString(id);
		}
		return airportIDs;
	}

}
