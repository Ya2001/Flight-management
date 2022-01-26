package flights;

import java.util.Calendar;
import java.util.Date;

public class Flight implements Comparable<Flight> {
	private String airlineName, origin, destination, flightID;
	private Calendar date;
	private int capacity;
	private String[] seats;
	private int seatCounter;

	public Flight(String airlineName, String origin, String destination, Calendar date, String flightID, int capacity) {
		this.airlineName = airlineName;
		this.origin = origin;
		this.destination = destination;
		this.date = date;
		this.flightID = flightID;
		this.capacity = capacity;
		seats = new String[capacity];
		seatCounter = 0;
	}

	public boolean isFull() {
		return (seatCounter == capacity);
	}

	public Calendar getDate() {
		return date;
	}

	public int getAvailableSeats() {
		return capacity - seatCounter;
	}

	public void bookSeat(String person) {
		seats[seatCounter] = person;
		seatCounter++;
	}

	@Override
	public String toString() {
		return flightID + ": " + airlineName + ", " + origin + ", " + destination + ", " + Utils.showDate(date) + ", "
				+ getAvailableSeats();
	}

	@Override
	public int hashCode() {
		return (int) origin.hashCode() * destination.hashCode() + date.hashCode();
	}

	@Override
	public int compareTo(Flight otherFlight) {
		if (this.hashCode() > otherFlight.hashCode()) {
			return 1;
		}
		if (this.hashCode() < otherFlight.hashCode()) {
			return -1;
		} else {
			return 0;
		}
	}
}
