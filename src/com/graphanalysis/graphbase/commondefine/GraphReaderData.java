package com.graphanalysis.graphbase.commondefine;

import java.util.HashMap;
import java.util.Vector;

import com.graphanalysis.graphbase.implement.Edge;
import com.graphanalysis.graphbase.implement.Node;

public class GraphReaderData {
	private Vector<Edge> edges = new Vector<Edge>();
	private Vector<Node> nodes = new Vector<Node>();
	private HashMap<Integer,Node> nodeMap = new HashMap<Integer,Node>();
	private HashMap<String,Integer> idMap = new HashMap<String,Integer>();
	private boolean type = false;
	private boolean weighted = false;
	private int id = 0;
	private String error_info="";
	public GraphReaderData(){
	}
	
	public Vector<Edge> getEdges(){
		return edges;
	}
	public void addEdge(Edge e){
		edges.add(e);
	}
	public void setType(boolean newType){
		type = newType;
	}
	public boolean type(){
		return type;
	}
	
	public void setWeight(boolean weight){
		this.weighted = weight;
	}
	public boolean weight(){
		return this.weighted;
	}
	
	public void addNode(String nodeName){
		if(!idMap.containsKey(nodeName)){
			idMap.put(nodeName, id);
			nodeMap.put(id, new Node(id,nodeName));
			id++;
			nodes.add(getNode(nodeName));
		}
	}
	
	public Node getNode(String nodeName){
		if(idMap.containsKey(nodeName)){
			return nodeMap.get(idMap.get(nodeName));
		}else{
			return null;
		}
	}
	
	public Vector<Node> getNodeSet(){
		return nodes;
	}
	
	public void setError(String error){
		this.error_info = error;
	}	
	public String getError(){
		return this.error_info;
	}
}
