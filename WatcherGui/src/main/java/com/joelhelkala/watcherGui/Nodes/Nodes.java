package com.joelhelkala.watcherGui.Nodes;

import java.util.ArrayList;
import java.util.List;

import com.joelhelkala.watcherGui.Nodes.Node.Node;
import com.joelhelkala.watcherGui.httpRequests.HttpRequests;

public class Nodes {
	private static List<Node> nodes = new ArrayList<Node>();
	
	// Add a list of nodes 
	public static void AddNodes(List<Node> list) {
		if(list == null) return;
		for(Node n : list) {
			AddNode(n);
		}
	}
	
	// Add a node to nodes list
	public static void AddNode(Node node) {
		nodes.add(node);
	}
	
	public static int size() {
		return nodes.size();
	}
	
	public static Node get(int index) {
		return nodes.get(index);
	}
	
	public static List<Node> getAll() {
		return nodes;
	}

	public static Node findByLocation(String location) {
		for(Node n : nodes) {
			if(n.getLocation().equals(location)) return n;
		}
		return null;
	}
	
	public static void updateNodes() {
		nodes.clear();
		AddNodes(HttpRequests.getNodes());
	}
}
