package com.joelhelkala.watcherGui.frames.panels;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Abstract class for displaying the values of temperature, humidity and luminosity
public abstract class AbstractValuePanel extends JPanel {
	private JLabel label;
	private Integer value;
	private Character symbol;
	
	/*
	 * Default constructor, 
	 */
	public AbstractValuePanel(Character symbol, String title) {
		this(null, symbol, title);
	}
	
	// Constructor with given value
	public AbstractValuePanel(Integer value, Character symbol, String title) {
		this.value = value;
		this.symbol = symbol;
		
		setLayout(new BorderLayout());
		
		String value_string = "No value"; 
		if(this.value != null)  value_string = this.value + "" + this.symbol;
		label = new JLabel(value_string);
		
		label.setForeground(java.awt.Color.white);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font(null, Font.BOLD, 40));
		add(label, BorderLayout.CENTER);
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setForeground(java.awt.Color.white);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font(null, Font.BOLD, 20));
		add(titleLabel, BorderLayout.SOUTH);
	}
	
	// Updates the label with new value
	public void updateData(int value) {
		label.setText(value + "" + symbol);
	}
}
