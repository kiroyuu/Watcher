package com.joelhelkala.watcherGui.frames;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;

import org.json.JSONObject;

import com.joelhelkala.watcherGui.User.User;
import com.joelhelkala.watcherGui.httpRequests.HttpRequests;

public class RegisterPage implements ActionListener, KeyListener {
	JFrame frame = new JFrame();
	JLabel welcomeLabel = new JLabel("Hello!");
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	
	private JButton cancelButton;
	private JButton confirmButton;
	
	RegisterPage(){
		welcomeLabel.setBounds(122,32,200,35);
		welcomeLabel.setFont(new Font(null,Font.PLAIN,25));
		welcomeLabel.setText("Register user");	
		frame.getContentPane().add(welcomeLabel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 420);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("First name");
		lblNewLabel.setBounds(33, 104, 77, 16);
		frame.getContentPane().add(lblNewLabel);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(122, 99, 200, 26);
		firstNameField.addKeyListener(this);
		frame.getContentPane().add(firstNameField);
		firstNameField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Last name");
		lblNewLabel_1.setBounds(33, 133, 67, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		lastNameField = new JTextField();
		lastNameField.setBounds(122, 128, 200, 26);
		lastNameField.addKeyListener(this);
		frame.getContentPane().add(lastNameField);
		lastNameField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setBounds(66, 177, 44, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		emailField = new JTextField();
		emailField.setBounds(122, 172, 200, 26);
		emailField.addKeyListener(this);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setBounds(39, 232, 61, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(122, 227, 200, 26);
		passwordField.addKeyListener(this);
		frame.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setBounds(39, 271, 61, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		passwordField2 = new JPasswordField();
		passwordField2.setBounds(122, 266, 200, 26);
		passwordField2.addKeyListener(this);
		frame.getContentPane().add(passwordField2);
		passwordField2.setColumns(10);
		
		confirmButton = new JButton("Confirm");
		confirmButton.setBounds(247, 333, 117, 29);
		confirmButton.setEnabled(false);
		confirmButton.addActionListener(this);
		frame.getContentPane().add(confirmButton);
		
		cancelButton = new JButton("Back");
		cancelButton.setBackground(Color.RED);
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setBounds(66, 333, 117, 29);
		cancelButton.addActionListener(this);
		frame.getContentPane().add(cancelButton);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancelButton) {
			frame.dispose();
			LoginPage login = new LoginPage();
		} else if (e.getSource() == confirmButton) {
			if(validateForm()) {
				JSONObject response = HttpRequests.RegisterUser(firstNameField.getText(), emailField.getText(), new String(passwordField.getPassword()));
				String message = response.getString("message");
				String title = "User registration";
				int icon = JOptionPane.INFORMATION_MESSAGE;
				if(response.getInt("status") < 300) {
					JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
					LoginPage loginPage = new LoginPage();
				} else JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
			}
		}
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
	private boolean validateForm() {
		User user = new User(firstNameField.getText(), emailField.getText(), passwordField.getSelectedText());
		//JOptionPane.showMessageDialog(null, "Only numbers are allowed!");
		if (user.getName().isEmpty()) return false;
		boolean valid_email = validateEmail(user.getEmail());
		if (!valid_email) return false;
		
		if (passwordField.getPassword().length == 0 || passwordField2.getPassword().length == 0) return false;
		if (!Arrays.equals(passwordField.getPassword(), passwordField2.getPassword())) return false;
		return true;
	}

	public void keyReleased(KeyEvent e) {
		if(validateForm()) confirmButton.setEnabled(true);
		else confirmButton.setEnabled(false);
		
	}

	public void keyTyped(KeyEvent e) {
		//
	}


	public void keyPressed(KeyEvent e) {
		//
	}
}