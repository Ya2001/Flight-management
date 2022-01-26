package flights;

public class BookingRequest {
	String origin, destination, person;
	int year, month, day;

	public BookingRequest(String origin, String destination, int year, int month, int day, String person) {
		this.origin = origin;
		this.destination = destination;
		this.person = person;
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return ("From: " + origin + "\n" + "To: " + destination + "\n" + "When: " + year + "-" + month + "-" + day
				+ "\n" + "Who: " + person);
	}
}
