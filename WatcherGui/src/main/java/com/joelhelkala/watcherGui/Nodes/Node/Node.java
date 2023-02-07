package com.joelhelkala.watcherGui.Nodes.Node;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.joelhelkala.watcherGui.Nodes.Node.NodeData.NodeData;

public class Node {
	private String description;
	private String location;
	private Float latitude;
	private Float longitude;
	private Integer id;
	private List<NodeData> data;
	
	// Constructor with basic information
	public Node(String description, Float latitude, Float longitude, String location, Integer id) {
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = location;
		this.id = id;
		this.data = new ArrayList<NodeData>();
	}
	
	// Constructor with jsonarray
	public Node(JSONArray arr) {
		if(arr.length() == 0) {
			this.description = "";
			this.location = "";
			this.latitude = 0f;
			this.longitude = 0f;
			this.id = null;
			this.data = new ArrayList<NodeData>();
		} else {			
			JSONObject node = arr.getJSONObject(0);
			JSONObject parent = node.getJSONObject("parent");
			this.description = parent.getString("description");
			this.location = parent.getString("location");
			this.latitude = parent.getFloat("latitude");
			this.longitude = parent.getFloat("longitude");
			this.data = new ArrayList<NodeData>();
			
			for(int i = 0; i < arr.length(); i++) {
				JSONObject measurements = arr.getJSONObject(i);
				LocalDateTime measured_at = LocalDateTime.parse(measurements.getString("measured_at"));
				Integer temperature = measurements.getInt("temperature");
				Integer humidity = measurements.getInt("humidity");
				Integer luminosity = measurements.getInt("luminosity");
				this.data.add(new NodeData(measured_at, temperature, humidity, luminosity));
			}
		}
	}

	public List<NodeData> getData() { return data; }
	
	// Gets the temperature reading of the most recent measurement
	public Integer getRecentTemperature() {
		NodeData data = findRecentData();
		if(data == null) return 0;
		return data.getTemperature();
	}

	public Integer getRecentHumidity() {
		NodeData data = findRecentData(); 
		if(data == null) return 0;
		return data.getHumidity();
	}	

	public Integer getRecentLuminosity() {
		NodeData data = findRecentData();
		if(data == null) return 0;
		return data.getLuminosity();
	}
	
	
	public LocalDateTime getRecentDate() {
		NodeData data = findRecentData();
		if(data == null) return null;
		return data.getTime();
	}
	
	private NodeData findRecentData() {
		if(data.size() == 0) return null;
		NodeData newest = data.get(0);
		for(int i = 1; i < data.size(); i++) {
			if(data.get(i).getTime().isAfter(newest.getTime())) newest = data.get(i);
		}
		return newest;
	}
	
	// Getters
	public Integer getId() { return id; }
	public String getLocation() { return location; }
	public String getDescription() { return description; }
	public Float getLatitude() { return latitude; }
	public Float getLongitude() { return longitude; }
	
	public void updateData(JSONArray nodeData) {
		data = new ArrayList<NodeData>();
		if(nodeData.length() == 0) return;		
		
		for(int i = 0; i < nodeData.length(); i++) {
			JSONObject obj = nodeData.getJSONObject(i);
			LocalDateTime measured_at = LocalDateTime.parse(obj.get("measured_at").toString());
			Integer temp = obj.getInt("temperature");
			Integer humidity = obj.getInt("humidity");
			Integer lux = obj.getInt("luminosity");
			data.add(new NodeData(measured_at, temp, humidity, lux));
		}
	}
}
