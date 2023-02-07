package com.joelhelkala.watcherGui.frames.subframes;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import com.joelhelkala.watcherGui.Datatypes.MeasurementType.DataType;
import com.joelhelkala.watcherGui.Colors.Colors;
import com.joelhelkala.watcherGui.Nodes.Node.Node;
import com.joelhelkala.watcherGui.frames.panels.LineChartPanel;

public class Histograms extends JPanel {
	private LineChartPanel lumiChart;
	private LineChartPanel moistChart;
	private LineChartPanel tempChart;

	public Histograms() {
		JScrollPane scroll = new JScrollPane();

		JButton next = new JButton("Next");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(next);
        SpringLayout layout = new SpringLayout();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Colors.gray);
        setLayout(new BorderLayout());
        
        mainPanel.add(addTempChart(30, 0));
        mainPanel.add(addLumiChart(30, 350));
        mainPanel.add(addMoistChart(30, 700));
        
        
        mainPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 1500));
        scroll.setPreferredSize(new Dimension(500,500));
        scroll.setViewportView(mainPanel);
        scroll.setBackground(Colors.gray);
        add(scroll);
        add(buttonPanel,BorderLayout.SOUTH);

        setSize(500, 600);
        setVisible(true);
	}
	
	private JPanel addMoistChart(int x, int y) {
		JPanel chartPanel = new JPanel();
		chartPanel.setBackground(Colors.errorText);
		chartPanel.setBounds(x,y,800,300);
		chartPanel.setLayout(new BorderLayout());
		
		moistChart = new LineChartPanel("Moisture", new ArrayList<>());
		moistChart.setBackground(Colors.buttonColor);
		chartPanel.add(moistChart, BorderLayout.CENTER);
		return chartPanel;
	}
	
	private JPanel addLumiChart(int x, int y) {
		JPanel chartPanel = new JPanel();
		chartPanel.setBackground(Colors.errorText);
		chartPanel.setBounds(x,y,800,300);
		chartPanel.setLayout(new BorderLayout());
		
		lumiChart = new LineChartPanel("Luminosity", new ArrayList<>());
		lumiChart.setBackground(Colors.buttonColor);
		chartPanel.add(lumiChart, BorderLayout.CENTER);
		return chartPanel;
	}
	
	private JPanel addTempChart(int x, int y) {
		JPanel chartPanel = new JPanel();
		chartPanel.setBackground(Colors.errorText);
		chartPanel.setBounds(x,y,800,300);
		chartPanel.setLayout(new BorderLayout());
		
		tempChart = new LineChartPanel("Temperature", new ArrayList<>());
		tempChart.setBackground(Colors.buttonColor);
		chartPanel.add(tempChart, BorderLayout.CENTER);
		return chartPanel;
	}
	
	public void updateData(Node node) {
		tempChart.updateData(node.getData(), DataType.TEMP);
		lumiChart.updateData(node.getData(), DataType.LUMI);
		moistChart.updateData(node.getData(), DataType.HUMID);
	}
}
