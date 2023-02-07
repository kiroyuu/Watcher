package com.joelhelkala.watcherGui.frames.subframes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.joelhelkala.watcherGui.Colors.Colors;
import com.joelhelkala.watcherGui.User.User;
import com.joelhelkala.watcherGui.frames.dialogs.Dialog;
import com.joelhelkala.watcherGui.httpRequests.HttpRequests;


/*
 *	Panel where user can modify its own info 
 */
public class SettingsFrame extends JPanel implements ActionListener {
	
	private JTextField nameField;
	private JTextField emailField;
	private JTextField descField;
	
	// constructor
	public SettingsFrame() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Colors.gray);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		panel.setBackground(Colors.gray);
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setForeground(Color.WHITE);
		panel.add(lblNewLabel, "4, 4");
		
		nameField = new JTextField();
		panel.add(nameField, "8, 4, left, default");
		nameField.setColumns(18);
		nameField.setText(User.getName());
		
		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setForeground(Color.WHITE);
		panel.add(lblNewLabel_1, "4, 8");
		
		emailField = new JTextField();
		emailField.setColumns(18);
		emailField.setText(User.getEmail());
		panel.add(emailField, "8, 8, left, default");
		
		JLabel lblNewLabel_2 = new JLabel("Description");
		lblNewLabel_2.setForeground(Color.WHITE);
		panel.add(lblNewLabel_2, "4, 12");
		
		descField = new JTextField();
		panel.add(descField, "8, 12, left, default");
		descField.setColumns(18);
		
		JButton btnSave= new JButton("Save");
		panel.add(btnSave, "8, 18, center, default");
		btnSave.addActionListener(this);		
	}
	
	/*
	 * Creates a field of title and textfield into given container
	 */
	private void CreateField(String labelText, String fieldValue, JTextField textField, JPanel container) {
		JLabel text = new JLabel(labelText);
		textField.setText(fieldValue);
		
		container.add(text);
		container.add(textField);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = Dialog.yesNoDialog("Are you sure you want to save?", "SAVE");
		
		if(result == JOptionPane.YES_OPTION) {
			boolean success = HttpRequests.updateUser(nameField.getText(), emailField.getText(), descField.getText());
			if(!success) Dialog.ErrorDialog();
		}
	}
}
