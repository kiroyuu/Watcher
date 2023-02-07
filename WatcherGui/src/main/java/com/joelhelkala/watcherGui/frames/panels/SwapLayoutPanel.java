package com.joelhelkala.watcherGui.frames.panels;

import javax.swing.JPanel;
import java.awt.CardLayout;

public class SwapLayoutPanel {
	JPanel panelContainer = new JPanel();
	CardLayout cl = new CardLayout();
	
	public SwapLayoutPanel() {
		panelContainer.setLayout(cl);
		
	}
}
