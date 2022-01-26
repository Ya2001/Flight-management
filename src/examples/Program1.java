package examples;

import flights.BookingResponse;
import flights.Flight;
import flights.SystemManager;
import flights.SystemManagerException;

public class Program1 {

	public static void main(String[] args) {
		SystemManager mgr = new SystemManager();
		try {
			Flight newFlight = mgr.createFlight("DELTA", "ED", "NY", 2020, 01, 01, 50);
			System.out.println(newFlight);

			mgr.queueBookingRequest("ED", "NY", 2020, 01, 01, "Lucy");

			BookingResponse response = mgr.processNextBooking();
			System.out.println(response.getReason());
			System.out.println(response);
		} catch (SystemManagerException ex) {
		}
	}

}
