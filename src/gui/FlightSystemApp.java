package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import flights.BookingRequest;
import flights.BookingResponse;
import flights.Flight;
import flights.SystemManager;
import flights.SystemManagerException;

public class FlightSystemApp {

	static SystemManager mgr;

	public void listFlightsPanel(JPanel panel) {
		JPanel tabPanel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(tabPanel, BoxLayout.Y_AXIS);
		tabPanel.setLayout(boxlayout);

		JPanel buttonP = new JPanel();
		JButton button = new JButton("Refresh");
		final JList<String> flightList = new JList<>();

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LinkedList<Flight> flights = mgr.listFlights();
				if (flights != null) {
					String[] flightStrings = new String[flights.size()];
					if (flights.isEmpty()) {
						// nothing to show
					} else {
						int idx = 0;
						for (Flight flight : flights) {
							flightStrings[idx] = flight.toString();
							idx++;
						}
						flightList.setListData(flightStrings);
					}
				}
			}
		});

		buttonP.add(button);
		tabPanel.add(buttonP);
		tabPanel.add(flightList);
		panel.add(tabPanel);
	}

	public void nextBookingPanel(JPanel panel) {
		JPanel tabPanel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(tabPanel, BoxLayout.Y_AXIS);
		tabPanel.setLayout(boxlayout);

		final JTextArea bookingArea = new JTextArea(20, 40);

		JPanel buttonP = new JPanel();
		JButton button = new JButton("Refresh");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookingRequest booking = mgr.nextBooking();
				if (booking == null) {
					bookingArea.setText("no current booking");
				} else {
					bookingArea.setText(booking.toString());
				}
			}
		});

		buttonP.add(button);

		tabPanel.add(buttonP);
		tabPanel.add(bookingArea);
		panel.add(tabPanel);
	}

	public void acceptBookingPanel(JPanel panel) {
		JPanel tabPanel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(tabPanel, BoxLayout.Y_AXIS);
		tabPanel.setLayout(boxlayout);

		final JTextArea bookingArea = new JTextArea(20, 40);

		JPanel buttonP = new JPanel();
		JButton button = new JButton("Take new booking");
		button.setBounds(50, 100, 95, 30);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BookingResponse response = mgr.processNextBooking();
					bookingArea.setText(response.toString());
				} catch (SystemManagerException ex) {
					bookingArea.setText(ex.getMessage());
				} catch (NullPointerException ex) {
					bookingArea.setText(ex.getMessage());
				}
			}
		});

		buttonP.add(button);
		tabPanel.add(buttonP);
		tabPanel.add(bookingArea);
		panel.add(tabPanel);
	}

	public void flightBookingPanel(JPanel panel) {
		JPanel tabPanel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(tabPanel, BoxLayout.Y_AXIS);
		tabPanel.setLayout(boxlayout);

		final JComboBox<String> originList = new JComboBox<>();
		originList.setEditable(true);
		originList.addActionListener(new FlightIDActionListener(mgr));

		final JComboBox<String> destinationList = new JComboBox<>();
		destinationList.setEditable(true);
		destinationList.addActionListener(new FlightIDActionListener(mgr));

		JPanel p = new JPanel(new SpringLayout());

		p.add(new JLabel("Origin", JLabel.TRAILING));
		p.add(originList);
		p.add(new JLabel("Destination", JLabel.TRAILING));
		p.add(destinationList);

		String[] labels = { "Year: ", "Month: ", "Day: ", "Customer: " };
		int numPairs = labels.length;

		final LinkedList<JTextField> fields = new LinkedList<>();
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			p.add(l);
			JTextField textField = new JTextField(10);
			l.setLabelFor(textField);
			p.add(textField);
			fields.add(textField);
		}

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(p, numPairs + 2, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		final JTextArea logArea = new JTextArea(10, 40);

		JPanel bookButtonP = new JPanel();
		JButton bookButton = new JButton("Book");

		JPanel loadBookingsP = new JPanel();
		JButton loadBookingsFileButton = new JButton("Or load from file");
		LoadBookingsChooser listener = new LoadBookingsChooser(mgr, logArea);
		loadBookingsFileButton.addActionListener(listener);

		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// TODO queue a new booking request.
					//
					// get the origin airport ID from originList
					// with originList.getSelectedItem().toString()
					//
					// same idea for the destination airport ID.
					//
					// The other fields:
					// year - fields.get(0).getText()
					// int month - fields.get(1)...
					// int day - fields.get(2)...
					// String person - fields.get(3)....
					//
					// to convert a String to an integer: Integer.parseInt(string)

					// TODO after you have queued the booking request, say so in the logArea text
					// area e.g. with setText(..)
				} catch (Exception ex) {
					// TODO if any of that throws an error, how about logging that in the logArea?
				}
			}
		});

		bookButtonP.add(bookButton);
		loadBookingsP.add(loadBookingsFileButton);
		tabPanel.add(p);
		tabPanel.add(bookButtonP);
		tabPanel.add(loadBookingsP);
		tabPanel.add(logArea);

		panel.add(tabPanel);
	}

	public void findFlightPanel(JPanel panel) {
		JPanel tabPanel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(tabPanel, BoxLayout.Y_AXIS);
		tabPanel.setLayout(boxlayout);

		JPanel p = new JPanel(new SpringLayout());
		String[] labels = { "Year: ", "Month: ", "Day: " };
		int numPairs = labels.length;

		final JComboBox<String> originList = new JComboBox<>();
		originList.setEditable(true);
		originList.addActionListener(new FlightIDActionListener(mgr));

		final JComboBox<String> destinationList = new JComboBox<>();
		destinationList.setEditable(true);
		destinationList.addActionListener(new FlightIDActionListener(mgr));

		p.add(new JLabel("Origin", JLabel.TRAILING));
		p.add(originList);
		p.add(new JLabel("Destination", JLabel.TRAILING));
		p.add(destinationList);

		final LinkedList<JTextField> fields = new LinkedList<>();
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			p.add(l);
			JTextField textField = new JTextField(10);
			l.setLabelFor(textField);
			p.add(textField);
			fields.add(textField);
		}

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(p, numPairs + 2, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		JPanel buttonP = new JPanel();
		JButton button = new JButton("Find");
		button.setBounds(50, 100, 95, 30);

		final JTextArea exceptionArea = new JTextArea(10, 40);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Flight flight = mgr.findFlight(originList.getSelectedItem().toString(),
							destinationList.getSelectedItem().toString(), Integer.parseInt(fields.get(0).getText()),
							Integer.parseInt(fields.get(1).getText()), Integer.parseInt(fields.get(2).getText()));
					exceptionArea.setText("Booking request received, not yet confirmed.");
					if (flight == null) {
						exceptionArea.setText("No flights available");
					} else {
						exceptionArea.setText("<FOUND> " + flight.toString());
					}
				} catch (NumberFormatException ex) {
					exceptionArea.setText("Unable to parse numbers in your form.");
				} catch (NullPointerException ex) {
					exceptionArea.setText(ex.getMessage());
				}

			}
		});

		buttonP.add(button);
		tabPanel.add(buttonP);
		tabPanel.add(p);
		tabPanel.add(exceptionArea);

		panel.add(tabPanel);
	}

	public void createFlightPanel(JPanel panel) {
		JPanel tabPanel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(tabPanel, BoxLayout.Y_AXIS);
		tabPanel.setLayout(boxlayout);

		JPanel p = new JPanel(new SpringLayout());
		String[] labels = { "Airline: ", "Year: ", "Month: ", "Day: ", "Capacity: " };
		int numPairs = labels.length;

		final JComboBox<String> originList = new JComboBox<>();
		originList.setEditable(true);
		originList.addActionListener(new FlightIDActionListener(mgr));

		final JComboBox<String> destinationList = new JComboBox<>();
		destinationList.setEditable(true);
		destinationList.addActionListener(new FlightIDActionListener(mgr));

		p.add(new JLabel("Origin", JLabel.TRAILING));
		p.add(originList);
		p.add(new JLabel("Destination", JLabel.TRAILING));
		p.add(destinationList);

		final LinkedList<JTextField> fields = new LinkedList<>();
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			p.add(l);
			JTextField textField = new JTextField(10);
			l.setLabelFor(textField);
			p.add(textField);
			fields.add(textField);
		}

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(p, numPairs + 2, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		final JTextArea logArea = new JTextArea(10, 40);

		JPanel buttonP = new JPanel();
		JButton button = new JButton("Create flight");

		JPanel loadBookingsP = new JPanel();
		JButton loadBookingsFileButton = new JButton("Or load from file");
		LoadFlightsChooser listener = new LoadFlightsChooser(mgr, logArea);
		loadBookingsFileButton.addActionListener(listener);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// TODO create the flight to add it to the Binary Search Tree
					//
					// Airline name: fields.get(0).getText()
					// Origin airport ID: fields.get(0).getText(), originList.getSelectedItem().toString()
					// Destination airport ID: similar, for the destinationList JComboBox
					// Year: field 1 from `fields`
					// Month: field 2 from `fields`
					// Day: field 3 from `fields`
					// Capacity: field 4 from `fields
					//
					// If you need to convert from a String to an Integer then use Integer.parseInt(..)

					logArea.setText("Flight added");
				}
			
// TODO uncomment					
//				} catch (SystemManagerException ex) {
//					logArea.setText("Problem:\n" + ex.getMessage());
//				}
					catch (Exception ex) {
					logArea.setText("Problem:\n" + ex.getMessage());
				}
			}
		});

		buttonP.add(button);

		loadBookingsP.add(loadBookingsFileButton);

		tabPanel.add(p);
		tabPanel.add(buttonP);
		tabPanel.add(loadBookingsP);
		tabPanel.add(logArea);
		panel.add(tabPanel);

	}

	public FlightSystemApp() {
		JFrame frame = new JFrame("Flight Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);

		JTabbedPane tabs = new JTabbedPane();
		tabs.setBounds(50, 50, 200, 200);

		JTabbedPane flightsTabs = new JTabbedPane();
		JTabbedPane bookingsTabs = new JTabbedPane();

		JPanel pCreateFlight = new JPanel();
		createFlightPanel(pCreateFlight);
		flightsTabs.add("Create flight", pCreateFlight);

		JPanel pFindFlight = new JPanel();
		findFlightPanel(pFindFlight);
		flightsTabs.add("Find flight", pFindFlight);

		JPanel pListFlights = new JPanel();
		listFlightsPanel(pListFlights);
		flightsTabs.add("List flights", pListFlights);

		JPanel pBooking = new JPanel();
		flightBookingPanel(pBooking);
		bookingsTabs.add("Booking request", pBooking);

		JPanel pNextBooking = new JPanel();
		nextBookingPanel(pNextBooking);
		bookingsTabs.add("Next booking", pNextBooking);

		JPanel pAcceptBooking = new JPanel();
		acceptBookingPanel(pAcceptBooking);
		bookingsTabs.add("Accept booking", pAcceptBooking);

		tabs.add("Flights", flightsTabs);
		tabs.add("Bookings", bookingsTabs);

		frame.add(tabs);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		mgr = new SystemManager();
		new FlightSystemApp();
	}

}
