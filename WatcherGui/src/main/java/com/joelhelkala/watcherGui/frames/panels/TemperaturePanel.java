package com.joelhelkala.watcherGui.frames.panels;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

public class TemperaturePanel extends AbstractValuePanel {
	
	private static final Character symbol = 0xB0;
	private static final String title = "Temperature";
	
	public TemperaturePanel() {
		super(symbol, title);
	}
	
	public TemperaturePanel(Integer value, LocalDateTime date) {
	 	super(value, symbol, title);
	 	Integer year = date.getYear();
	 	Integer month = date.getMonthValue();
	 	Integer day_num = date.getDayOfMonth();
	 	
	 	Integer hours = date.getHour();
	 	Integer minutes = date.getMinute();
	 	
	 	String date_value = "" + day_num + '.' + month + "." + year + " " + hours + ":" + minutes;
		JLabel day = new JLabel(date_value);
		day.setForeground(java.awt.Color.white);
		day.setHorizontalAlignment(SwingConstants.CENTER);
		add(day, BorderLayout.NORTH);
	}
	
	private static String getDay() {
		Calendar cal = Calendar.getInstance();  
		Format f = new SimpleDateFormat("EEEE");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date = f.format(new Date()) + " " + sdf.format(cal.getTime()); 
		return date;  
	}
}
