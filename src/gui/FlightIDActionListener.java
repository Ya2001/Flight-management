package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import flights.SystemManager;

public class FlightIDActionListener implements ActionListener {

	SystemManager mgr;

	public FlightIDActionListener(SystemManager mgr) {
		this.mgr = mgr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("comboBoxEdited")) {
			JComboBox<String> combo = (JComboBox<String>) e.getSource();
			String airportID = combo.getSelectedItem().toString();
			combo.removeAllItems();

			// TODO look up the the airport IDs from the Trie in the System Manager
			//
			// For each airport ID in the Trie, add it to the auto-complete box
			//
			// Useful methods:
			// lookupAirportIDs
			// addItem (on JComboBox)
			// toUpperCase (on Strings)
			//
			// It's up to you how about capitalisation: the airport codes should appear as
			// capitalised characters. Either you store characters as capitalised characters
			// in the Trie, or you convert from/to capitalised when searching the Trie.

		}
	}

}