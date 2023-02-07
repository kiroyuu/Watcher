package com.joelhelkala.watcherGui.frames.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.joelhelkala.watcherGui.Datatypes.MeasurementType.DataType;
import com.joelhelkala.watcherGui.Nodes.Node.NodeData.NodeData;

public class LineChartPanel extends JPanel {
	
	private JFreeChart lineChart;
	private ChartPanel chartPanel;
	private CategoryPlot plot;
	
	// Constructor
	public LineChartPanel(String chartTitle, List<NodeData> list) {
		lineChart = ChartFactory.createLineChart(chartTitle, "DateTime","Temperature",
		         createDataset(list, DataType.TEMP),
		         PlotOrientation.VERTICAL,
		         true,true,false);
		
		chartPanel = new ChartPanel( lineChart );
	    chartPanel.setPreferredSize( new Dimension( 560 , 367 ) );
	    plot = (CategoryPlot) lineChart.getPlot();
	    
	    this.setLayout(new BorderLayout());
	    this.add(chartPanel, BorderLayout.CENTER);
	    this.validate();
	}
	
	// Create dataset from given nodedata list
	private DefaultCategoryDataset createDataset(List<NodeData> list, DataType type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		list.sort((a,b) -> a.getTime().compareTo(b.getTime()));
		
		for(NodeData data : list) {
			switch (type) {
			
			case TEMP:
				dataset.addValue(data.getTemperature(), "temp", data.getTime());
				break;
			case HUMID:
				dataset.addValue(data.getHumidity(), "humid", data.getTime());
				break;
			case LUMI:
				dataset.addValue(data.getLuminosity(), "lumi", data.getTime());
				break;
			}
		}
		
		return dataset;
	}

	// Update chart data
	public void updateData(List<NodeData> list, DataType type) {
		plot.setDataset(createDataset(list, type));
	}
}
