package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import flights.SystemManager;
import flights.SystemManagerException;

@SuppressWarnings("serial")
public class LoadBookingsChooser extends JFrame implements ActionListener {

	SystemManager mgr;
	JTextArea logArea;

	LoadBookingsChooser(SystemManager mgr, JTextArea logArea) {
		this.mgr = mgr;
		this.logArea = logArea;
	}

	private void processFile(File f) {
		Scanner bookingsReader;
		try {
			bookingsReader = new Scanner(f);
			int numBookings = 0;
			while (bookingsReader.hasNextLine()) {
				String bookingData = bookingsReader.nextLine();
				List<String> bookingDetails = Arrays.asList(bookingData.split(","));
				mgr.queueBookingRequest(bookingDetails.get(0), bookingDetails.get(1),
						Integer.parseInt(bookingDetails.get(2)), Integer.parseInt(bookingDetails.get(3)),
						Integer.parseInt(bookingDetails.get(4)), bookingDetails.get(5));
				numBookings++;
			}
			bookingsReader.close();
			logArea.setText(numBookings + " bookings loaded from file.");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (SystemManagerException ex) {
			logArea.setText("Could not book: " + ex.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Booking file", "txt", "text");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			processFile(selectedFile);
		}

	}

}
