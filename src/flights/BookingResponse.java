package flights;

import java.util.Calendar;

public class BookingResponse {
	boolean success;
	private String reason, person, origin, destination;
	private String ticketNumber;
	Calendar date;

	public BookingResponse(boolean success, String reason, String origin, String destination, Calendar date,
			String person) {
		this.success = success;
		this.reason = reason;
		this.person = person;
		this.date = date;
		this.origin = origin;
		this.destination = destination;
		if (success) {
			ticketNumber = generateTicket();
		}
	}

	public boolean booked() {
		return success;
	}

	public String getReason() {
		return reason;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	private String generateTicket() {
		return Utils.genenerateRandomID('T');
	}

	@Override
	public String toString() {
		if (success) {
			return ("confirmed ticket: " + getTicketNumber() + "\n" + origin + " -> " + destination + "\n"
					+ Utils.showDate(date) + "\n" + person);

		} else {
			return ("booking unsuccessful: " + getReason());
		}
	}
}
