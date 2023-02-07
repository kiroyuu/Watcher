package com.joelhelkala.watcherGui.frames.dialogs;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.joelhelkala.watcherGui.frames.WelcomePage;

public class Dialog {
	private static JFrame frame;
	
	public Dialog() {
		frame = WelcomePage.getFrame();
	}
	
	// Default error message dialog
	public static void ErrorDialog() {
		String message = "Something went wrong.";
		String title = "ERROR!";
		JOptionPane.showMessageDialog(frame, message, title,
	               JOptionPane.ERROR_MESSAGE);
	}
	
	/*
	 * Shows a very basic dialog
	 */
	public static void showHelpDialog() {
		JOptionPane.showMessageDialog(frame,
			    "For questions about the app, contact the developer at joel.helkala@gmail.com",
			    "Help!",
			    JOptionPane.PLAIN_MESSAGE);
	}
	
	public static int yesNoDialog(String message, String title) {
		int result = JOptionPane.showConfirmDialog(frame, message, title,
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE);
		
		return result;
	}
}
