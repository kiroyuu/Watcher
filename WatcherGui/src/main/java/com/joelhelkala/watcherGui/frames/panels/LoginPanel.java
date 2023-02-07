package com.joelhelkala.watcherGui.frames.panels;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	private JTextField userIDField;
	private JPasswordField passwordField;
	private JLabel emailLabel;
	private JLabel userPasswordLabel;
	private JLabel titleLabel;
	
	
	private final int fieldStartY = 50;
	private final int fieldSpacing = 30;
	
	public LoginPanel() {
		emailLabel = new JLabel("Email");
		userPasswordLabel = new JLabel("Password");
		titleLabel = new JLabel("Sign In");

		userIDField = new JTextField();
		passwordField = new JPasswordField();
		
		passwordField.setBounds(122, fieldStartY+fieldSpacing+5, 200, 26);

		titleLabel.setBounds(122,0,200,35);
		titleLabel.setFont(new Font(null,Font.PLAIN,25));
		titleLabel.setForeground(Color.white);

		userIDField.setBounds(122, fieldStartY, 200, 26);
		emailLabel.setBounds(64, fieldStartY+5, 77, 16);
		emailLabel.setForeground(Color.white);
		
		userPasswordLabel.setBounds(40, fieldStartY+fieldSpacing+10, 77, 16);
		userPasswordLabel.setForeground(Color.white);
		
		setLayout(null);
		add(titleLabel);
		add(emailLabel);
		add(userPasswordLabel);
		add(userIDField);
		add(passwordField);
		setBackground(Color.black);
	}
	
	public Map<String, String> getCredentials() {
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("email", userIDField.getText());
		credentials.put("password", String.valueOf(passwordField.getPassword()));
		return credentials;
	}
	
	// Clears the email and password fields
	public void ClearFields() {
		userIDField.setText("");
		passwordField.setText("");
		userIDField.requestFocus();
	}
}
