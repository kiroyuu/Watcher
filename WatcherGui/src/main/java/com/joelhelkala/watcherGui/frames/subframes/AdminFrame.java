package com.joelhelkala.watcherGui.frames.subframes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.joelhelkala.watcherGui.Colors.Colors;
import com.joelhelkala.watcherGui.Datatypes.UserType;
import com.joelhelkala.watcherGui.frames.WelcomePage;
import com.joelhelkala.watcherGui.frames.dialogs.Dialog;
import com.joelhelkala.watcherGui.httpRequests.HttpRequests;

public class AdminFrame extends JPanel implements ActionListener {
	private JPanel panel;
	private GridBagConstraints c;
	
	public AdminFrame() {
		UpdateUsers();
	}
	
	/*
	 * Clears the Jpanel and makes new userfields
	 */
	private void UpdateUsers() {
		removeAll();
		List<UserType> users = HttpRequests.GetUsers();
		
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(Colors.gray);
		JLabel label = new JLabel("Admin");
		label.setForeground(Color.white);
		label.setHorizontalAlignment(SwingConstants.CENTER);
	    add(label, BorderLayout.NORTH);
	    panel = new JPanel(new GridBagLayout());
	    panel.setBackground(Colors.gray);
		add(panel, BorderLayout.CENTER);
		c = new GridBagConstraints();
		c.insets = new Insets(4, 4, 4, 4);
		
	    for(UserType user : users) {
	    	CreateUserField(user);
	    }
	    c.weighty = 1.0;
	    panel.add(Box.createGlue(), c);
	    revalidate();
	}
	
	/*
	 * Creates a user column for editing user details
	 */
	private void CreateUserField(UserType user) {
		JTextField name = new JTextField(20);
		name.setText(user.getName());
		String username = user.getName();
	    panel.add(name, c);

	    JTextField email = new JTextField(20);
	    String usermail = user.getEmail();
	    email.setText(user.getEmail());
	    panel.add(email, c);
	    
	    JCheckBox enabled = new JCheckBox("Enabled", user.getEnabled());
	    enabled.setForeground(Color.white);
	    panel.add(enabled, c);
	    
	    JCheckBox locked = new JCheckBox("Locked", user.getLocked());
	    locked.setForeground(Color.white);
	    panel.add(locked, c);
	    
	    JButton delete = new JButton("Delete");
	    delete.addActionListener( evt->{
	        performActionOnId( user.getId() );
	    });
	    panel.add(delete, c);

	    c.gridwidth = GridBagConstraints.REMAINDER;
	    JButton save = new JButton("Save");
	    save.addActionListener( evt -> {
	    	performActionOnSave(name.getText(), email.getText(), enabled.isSelected(), locked.isSelected(), user.getId());
	    });
	    panel.add(save, c);
	    c.gridwidth = 1;
	}
	
	/*
	 * Handles the save button press
	 * Sends update request to server with new info
	 */
	private void performActionOnSave(String name, String email, Boolean enabled, Boolean locked, Long id) {
		if (!WelcomePage.handleSave()) return;
		
		if(!HttpRequests.updateUserAdmin(name, email, enabled, locked, id)) Dialog.ErrorDialog(); 
		UpdateUsers();
	}

	// Delete user with given id
	private void performActionOnId(Long id) {
		if (!WelcomePage.handleSave()) return;
		
		if(HttpRequests.deleteUser(id)) UpdateUsers();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
