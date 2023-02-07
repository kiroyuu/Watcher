package com.joelhelkala.watcherGui.frames.panels;

public class LuminosityPanel extends AbstractValuePanel {
	private final static Character symbol = ' ';
	private final static String title = "Luminosity";
	
	public LuminosityPanel() {
		super(symbol, title);
	}
	
	public LuminosityPanel(int value) {
		super(value, symbol, title);
	}
}
