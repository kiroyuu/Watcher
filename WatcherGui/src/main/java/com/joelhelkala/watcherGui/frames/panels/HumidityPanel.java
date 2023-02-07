package com.joelhelkala.watcherGui.frames.panels;

public class HumidityPanel extends AbstractValuePanel {
	private static final Character symbol = '%';
	private static final String title = "Humidity";
	// Constructor
	public HumidityPanel() {
		super(symbol, title);
	}
	
	public HumidityPanel(int value) {
		super(value, symbol, title);
	}
}
