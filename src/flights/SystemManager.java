package flights;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Queue;

import search.Airports;
import search.BinarySearchTree;
import search.Trie;

public class SystemManager {

	private BinarySearchTree<Flight> flights;
	private Queue<BookingRequest> bookingQueue;
	private Trie airportIDs;

	public SystemManager() {
		flights = new BinarySearchTree<>();
		bookingQueue = new LinkedList<>();
		Airports airports = new Airports();
		airportIDs = airports.createAirportIDs();
	}

	public ArrayList<String> lookupAirportIDs(String lookup) {
		// TODO lookup airport from flightIDs Trie
		return null;
	}

	private void validateAirport(String name) throws SystemManagerException {
		// TODO
		// 1. string should have length 3
		// 2. should be all upper case
		// 3. should be an IATA code of one of the 30 busiest world airports
	}

	public Flight createFlight(String airlineName, String origin, String destination, int year, int month, int day,
			int capacity) throws SystemManagerException {
		String flightID = generateFlightID();
		Calendar date = new GregorianCalendar(year, month, day);

		// TODO use validateAirport on the origin and destination airport IDs

		Flight flight = new Flight(airlineName, origin, destination, date, flightID, capacity);

		// TODO add the flight to the binary search tree that stores created flight info

		return flight;
	}

	public Flight findFlight(String origin, String destination, int year, int month, int day) {
		Calendar date = new GregorianCalendar(year, month, day);
		// TODO find the flight in the `flights` binary search tree
		return null; // TODO
	}

	public LinkedList<Flight> listFlights() {
		// TODO use in-order traversal of the `flights` binary search tree to obtain
		// a list of flights in the binary search tree of created flights.
		return null; // TODO
	}

	public BookingRequest nextBooking() {
		// TODO have a look at the next booking request in the bookingQueue queue
		// without removing it from the queue.
		return null; // TODO
	}

	private void bookSeat(Flight flight, String person) throws SystemManagerException {
		// TODO if the flight is full, then deny the booking with an exception.

		// TODO otherwise, a seat can be booked on the flight.
	}

	public BookingResponse processNextBooking() throws SystemManagerException {
		// TODO if the booking queue is empty, an exception should be thrown.

		// TODO take a look at the next book request in the queue (without removing it
		// from the queue).

		// TODO find a flight with the constraints from the booking request.

		// TODO if there is no flight (e.g. the "found" flight is null), then return a
		// booking response indicating that the flight booking request was not
		// successful, giving a reason in the response.

		// TODO if we get this far, then book the seat for the flight and the person who
		// requested the booking. After doing this, remove the booking request from the
		// queue then return a booking response indicating a successful booking request.

		// TODO if the attempt to book a seat lets you know that the flight was full,
		// then return a booking response indicating this.

		return null; // TODO you could remove this
	}

	public void queueBookingRequest(String origin, String destination, int year, int month, int day, String person)
			throws SystemManagerException {
		Flight flight = findFlight(origin, destination, year, month, day);

		// TODO if the flight is nulll (i.e. none found), then you should throw an
		// exception saying so.

		// TODO otherwise create a booking request and add it to the booking queue.
	}

	private String generateFlightID() {
		return Utils.genenerateRandomID('F');
	}

}
