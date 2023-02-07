package com.joelhelkala.watcherGui.frames.subframes;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.joelhelkala.watcherGui.Colors.Colors;

import java.awt.BorderLayout;

public class FriendsFrame extends JPanel {
	
	public FriendsFrame(int x, int y, int width, int height) {
		setLayout(new BorderLayout());
		setBounds(x,y,width,height);
		setOpaque(true);
		setBackground(Colors.gray);
		JLabel label = new JLabel("Friends");
		label.setForeground(Color.white);
		label.setHorizontalAlignment(SwingConstants.CENTER);
	    add(label, BorderLayout.NORTH);
	}
}
