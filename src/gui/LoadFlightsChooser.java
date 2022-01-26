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
public class LoadFlightsChooser extends JFrame implements ActionListener {

	SystemManager mgr;
	JTextArea logArea;

	LoadFlightsChooser(SystemManager mgr, JTextArea logArea) {
		this.mgr = mgr;
		this.logArea = logArea;
	}

	private void processFile(File f) {
		Scanner flightsReader;
		try {
			flightsReader = new Scanner(f);
			int numFlights = 0;
			while (flightsReader.hasNextLine()) {
				String fligtData = flightsReader.nextLine();
				List<String> flightDetails = Arrays.asList(fligtData.split(","));
				mgr.createFlight(flightDetails.get(0), flightDetails.get(1), flightDetails.get(2),
						Integer.parseInt(flightDetails.get(3)), Integer.parseInt(flightDetails.get(4)),
						Integer.parseInt(flightDetails.get(5)), Integer.parseInt(flightDetails.get(6)));
				numFlights++;
			}
			flightsReader.close();
			logArea.setText(numFlights + " flights loaded from file.");
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Flights file", "txt", "text");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			processFile(selectedFile);
		}

	}

}
