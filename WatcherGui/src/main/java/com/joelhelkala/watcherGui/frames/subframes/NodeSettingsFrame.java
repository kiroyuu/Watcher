package com.joelhelkala.watcherGui.frames.subframes;

import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.joelhelkala.watcherGui.Colors.Colors;
import com.joelhelkala.watcherGui.Nodes.Node.Node;
import com.joelhelkala.watcherGui.User.User;
import com.joelhelkala.watcherGui.frames.LoginPage;
import com.joelhelkala.watcherGui.frames.WelcomePage;
import com.joelhelkala.watcherGui.frames.dialogs.Dialog;
import com.joelhelkala.watcherGui.httpRequests.HttpRequests;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.*;

public class NodeSettingsFrame extends JPanel implements ActionListener{
	private JTextField descField = new JTextField();
	private JTextField locationField = new JTextField();
	private JTextField latField = new JTextField();
	private JTextField lonField = new JTextField();
	private Node current_node = null;
	
	private JButton reset_btn;
	private JButton save_btn;
	
	
	// Costructor
	public NodeSettingsFrame(int width, int height) {
		setLayout(null);
		setOpaque(true);
		setBackground(Colors.gray);
		
		JLabel descLabel = new JLabel("Description");
		descLabel.setForeground(Color.white);
		descLabel.setBounds(width/2-200,40, 100, 30);
		descField.setBounds(width/2-100, 40, 200, 30);
		add(descLabel);
		add(descField);
		
		JLabel locationLabel = new JLabel("Location");
		locationLabel.setForeground(Color.white);
		locationLabel.setBounds(width/2-200,100, 100, 30);
		locationField.setBounds(width/2-100, 100, 200, 30);
		add(locationLabel);
		add(locationField);
		
		JLabel latLabel = new JLabel("Latitude");
		latLabel.setForeground(Color.white);
		latLabel.setBounds(width/2-200,160, 100, 30);
		latField.setBounds(width/2-100, 160, 200, 30);
		add(latLabel);
		add(latField);
		
		JLabel lonLabel = new JLabel("Longitude");
		lonLabel.setForeground(Color.white);
		lonLabel.setBounds(width/2-200,220, 100, 30);
		lonField.setBounds(width/2-100, 220, 200, 30);
		add(lonLabel);
		add(lonField);
		
		reset_btn = new JButton("Reset");
		save_btn = new JButton("Save");
		reset_btn.addActionListener(this);
		save_btn.addActionListener(this);
		
		reset_btn.setBounds(width/2-300, 300, 50, 20);
		save_btn.setBounds(width/2+200, 300, 50, 20);
		add(reset_btn);
		add(save_btn);
	}

	// Updates the textfields according to the given node
	public void updateInformation(Node node) {
		current_node = node;
		descField.setText(node.getDescription());
		locationField.setText(node.getLocation());
		latField.setText(node.getLatitude().toString());
		lonField.setText(node.getLongitude().toString());
	}
	
	// Saves the current information from the textlabels
	// A confirmation window is prompted
	private void saveInformation() {
		boolean save = WelcomePage.handleSave();
		if(save && current_node != null) {
			boolean success = HttpRequests.updateNode(new Node(descField.getText(),
									Float.parseFloat(latField.getText()),
									Float.parseFloat(lonField.getText()),
									locationField.getText(),
									current_node.getId()));
			if(!success) {
				Dialog.ErrorDialog();
				return;
			}
			WelcomePage.updateComboBox();
		}
	}
	
	/*
	 * Validates all the textfields and returns
	 * a boolean based on if all are valid
	 */
	private boolean validFields() {
		// TODO: create implementation
		return true;
	}

	// This handles the reset and save button clicks
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == reset_btn) {
			updateInformation(current_node);
		} else if(e.getSource() == save_btn) {
			if(validFields()) saveInformation();
		}
	}
}