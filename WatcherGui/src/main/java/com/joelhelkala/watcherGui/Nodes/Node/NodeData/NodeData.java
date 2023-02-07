package com.joelhelkala.watcherGui.Nodes.Node.NodeData;

import java.time.LocalDateTime;

public class NodeData {
	private final LocalDateTime measured_at;
	private final Integer temperature;
	private final Integer humidity;
	private final Integer luminosity;
	
	public NodeData() {
		this.measured_at = null;
		this.temperature = null;
		this.humidity = null;
		this.luminosity = null;
	}
	
	public NodeData(LocalDateTime measured_at, Integer temperature, Integer humidity, Integer luminosity) {
		this.measured_at = measured_at;
		this.temperature = temperature;
		this.humidity = humidity;
		this.luminosity = luminosity;
	}
	
	public LocalDateTime getTime() {
		return measured_at;
	}

	public Integer getTemperature() {
		return temperature;
	}
	
	public Integer getHumidity() {
		return humidity;
	}

	public Integer getLuminosity() {
		return luminosity;
	}
}
