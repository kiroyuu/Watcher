package com.joelhelkala.watcherGui.frames.subframes;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.joelhelkala.watcherGui.Datatypes.MeasurementType.DataType;
import com.joelhelkala.watcherGui.Nodes.Node.Node;
import com.joelhelkala.watcherGui.frames.panels.HumidityPanel;
import com.joelhelkala.watcherGui.frames.panels.LineChartPanel;
import com.joelhelkala.watcherGui.frames.panels.LuminosityPanel;
import com.joelhelkala.watcherGui.frames.panels.TemperaturePanel;

public class NodeDataFrame extends JPanel {
	
	private static final int cardWidth = 300;
	private static final int cardPanelHeight = 300;

	private static final Color gray = new Color(45, 45, 45);
	private static final Color lightgray = new Color(73, 73, 73);
	
	private TemperaturePanel tempCard;
	private HumidityPanel humidCard;
	private LuminosityPanel lumiCard;
	private LineChartPanel chartCard;
	
	public NodeDataFrame(int x, int y, int width, int height) {
		setLayout(null);
		setBounds(x,y,width,height);
		setOpaque(true);
		setBackground(Color.GRAY);
	    
	    JPanel cardPanel = new JPanel();
		cardPanel.setBackground(gray);
		cardPanel.setBounds(0, 0, width, cardPanelHeight);
		cardPanel.setLayout(null);
		
		tempCard = new TemperaturePanel();
		tempCard.setBackground(lightgray);
		tempCard.setBounds(15, 10, cardWidth, cardPanelHeight-20);
		cardPanel.add(tempCard);
		
		// Updating this animation was hard so I just changed it to regular
		//humidCard = new ProgressBarCirclePanel().makeUI("Humidity", node.getRecentHumidity());
		humidCard = new HumidityPanel();
		humidCard.setBackground(lightgray);
		humidCard.setBounds(cardWidth+30, 10, cardWidth, cardPanelHeight-20);
		cardPanel.add(humidCard);
		
		lumiCard = new LuminosityPanel();
		lumiCard.setBackground(lightgray);
		lumiCard.setBounds(cardWidth*2+45, 10, cardWidth, cardPanelHeight-20);
		cardPanel.add(lumiCard);
		
		JPanel chartCardPanel = new JPanel();
		chartCardPanel.setBackground(gray);
		chartCardPanel.setBounds(0, cardPanelHeight, width, cardPanelHeight+10);
		chartCardPanel.setLayout(null);
		
		chartCard = new LineChartPanel("Temperature", new ArrayList<>());
		chartCard.setBackground(lightgray);
		chartCard.setBounds(15, 10, width-30, cardPanelHeight - 20);
		chartCardPanel.add(chartCard);
		
		add(cardPanel);
		add(chartCardPanel);
	}
	
	// Updates the frames data panels with given node information
	public void updateData(Node node) {
		tempCard.updateData(node.getRecentTemperature());
		humidCard.updateData(node.getRecentHumidity());
		lumiCard.updateData(node.getRecentLuminosity());
		chartCard.updateData(node.getData(), DataType.TEMP);
		
	}
}
