package com.joelhelkala.watcherGui.frames.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import com.joelhelkala.watcherGui.User.User;
import com.joelhelkala.watcherGui.frames.LoginPage;
import com.joelhelkala.watcherGui.httpRequests.HttpRequests;

public class RegisterPanel extends JPanel {
	private JTextField nameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	
	private int fieldStartY = 50;
	private int fieldSpacing = 30;
	
	private final Color textColor = Color.white; 
	
	public RegisterPanel() {
		setLayout(null);
		JLabel titleLabel = new JLabel("Register");
		titleLabel.setBounds(122,0,200,35);
		titleLabel.setFont(new Font(null,Font.PLAIN,25));
		titleLabel.setForeground(Color.white);
		add(titleLabel);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(64, fieldStartY+5, 77, 16);
		nameLabel.setForeground(textColor);
		add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(122, fieldStartY, 200, 26);
		add(nameField);
		nameField.setColumns(10);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(66, fieldStartY+fieldSpacing+5, 44, 16);
		emailLabel.setForeground(textColor);
		add(emailLabel);
		
		emailField = new JTextField();
		emailField.setBounds(122, fieldStartY+fieldSpacing, 200, 26);
		add(emailField);
		emailField.setColumns(10);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setBounds(39, fieldStartY+fieldSpacing*2+5, 61, 16);
		passLabel.setForeground(textColor);
		add(passLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(122, fieldStartY+fieldSpacing*2, 200, 26);
		add(passwordField);
		passwordField.setColumns(10);
		
		JLabel repeatPassLabel = new JLabel("Password");
		repeatPassLabel.setBounds(39, fieldStartY+fieldSpacing*3+5, 61, 16);
		repeatPassLabel.setForeground(textColor);
		add(repeatPassLabel);
		
		passwordField2 = new JPasswordField();
		passwordField2.setBounds(122, fieldStartY+fieldSpacing*3, 200, 26);
		add(passwordField2);
		passwordField2.setColumns(10);
		
		setBackground(Color.black);
	}
	
	public Map<String, String> getUserInfo() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("name", nameField.getText());
		info.put("email", emailField.getText());
		info.put("password", String.valueOf(passwordField.getPassword()));
		return info;
	}

	private boolean validateEmail(String email) {
		// TODO: Create regex for email verification
		if (email.length() > 0) return true;
		return false;
	}
	
	/*
	 * Checks if the form has all the required fields and 
	 * the password fields are equal
	 */
	public boolean validateForm() {
		User user = new User(nameField.getText(), emailField.getText(), passwordField.getSelectedText());
		//JOptionPane.showMessageDialog(null, "Only numbers are allowed!");
		if (user.getName().isEmpty()) return false;
		boolean valid_email = validateEmail(user.getEmail());
		if (!valid_email) return false;
		
		if (passwordField.getPassword().length == 0 || passwordField2.getPassword().length == 0) return false;
		if (!Arrays.equals(passwordField.getPassword(), passwordField2.getPassword())) return false;
		return true;
	}

	public void clierFields() {
		nameField.setText(null);
		emailField.setText(null);
		passwordField.setText(null);
		passwordField2.setText(null);
	}
}
