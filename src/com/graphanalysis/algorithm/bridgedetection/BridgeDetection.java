/*FileName:BridgeDetection.java
 * Date:2015.05.13
 * Author:Yan Lingyong
 * Description: Class use for bridgeDetection
 * */

package com.graphanalysis.algorithm.bridgedetection;

import java.util.Stack;
import java.util.Vector;

import org.json.JSONArray;

import com.graphanalysis.algorithm.bridgedetection.Bridge;
import com.graphanalysis.algorithm.bridgedetection.NodeForDetection;
import com.graphanalysis.algorithm.implement.ExecParameter;
import com.graphanalysis.algorithm.implement.ExecReturn;
import com.graphanalysis.algorithm.interfaces.AlgorithmInterface;
import com.graphanalysis.graphbase.commondefine.GraphType;
import com.graphanalysis.graphbase.implement.Graph;
import com.graphanalysis.graphbase.implement.GraphException;

public class BridgeDetection  implements BridgeDetectionNodeState,AlgorithmInterface{
	public BridgeDetection(){
	}
	
//construct a vector store the nodes information.
	private static Vector<NodeForDetection> constructNodeList(int number){
		Vector<NodeForDetection> res = new Vector<NodeForDetection>();
		for(int i=0;i<number;i++)
			res.add(new NodeForDetection(i,-1,-1,i));
		return res;
	}
	
	/*Here is the function used to detect bridge.
	 *The main idea is that we choose one node as a root node,and then use the DFS method to
	 * determine whether a edge linked a node and his father a bridge edge
	 * */
	private Bridge detectBridge(Graph graph){
		try{
		if(graph == null){
			throw new Exception("size shouldn't be zero!");
		}
		}catch(Exception e){
			System.out.println(e);
		}
		try{
		if(graph.getType()!=GraphType.UNDirectedGraph)
			throw new GraphException("bridge detection don't allow directed graph");
		}catch(Exception e){
			System.out.println(e);
			return new Bridge();
		}
		/*a stack used to make the DFS method work.It stores the node ID,which is associated with the Node Vector 'nodes'*/
		Bridge res = new Bridge();
		Stack<Integer> nodeStack = new Stack<Integer>();
		Vector<NodeForDetection> nodes = constructNodeList(graph.getNodeNum());
		
		nodeStack.push(ROOT);//we set the node 0 as the root as default
		nodes.get(ROOT).setDepth(ROOT);//the root's depth should be 0(ROOT)
		
		while(nodeStack.size()!=0){
			int current = nodeStack.pop();
			NodeForDetection currentNode = nodes.get(current);
			currentNode.reach();
			
			Vector<Integer> lkNodes = graph.getAdjList(current);
			
			
			for(;lkNodes!=null && currentNode.visitNum<lkNodes.size();currentNode.visitNum++){
				if(lkNodes.get(currentNode.visitNum) == currentNode.getFather())
					continue;
				
				int in = lkNodes.get(currentNode.visitNum);
				NodeForDetection sonNode = nodes.get(in);
				
				/*if we have route back from a branch of current node(while current node's lastunReached state is true)*/
				if(currentNode.getLastSonState()){
					currentNode.setLastSonState(LASTNODEISNOTNEW);
					currentNode.setAncestor(currentNode.getAncestor()<sonNode.getAncestor()?currentNode.getAncestor():sonNode.getAncestor());
					if(sonNode.getAncestor()>currentNode.getDepth())
						res.addPath(current, in,1);
					continue;
				}
				
				if(sonNode.getState() == UNREACHED){
					nodeStack.push(current);
					currentNode.setLastSonState(LASTNODEISNEW);
					sonNode.set(currentNode.getDepth()+1,current,currentNode.getDepth()+1);//we set the new reached node depth as his father's depth+1,ancestor as itself
					nodeStack.push(in);
					break;
				}else{
					currentNode.setAncestor(currentNode.getAncestor()<sonNode.getDepth()?currentNode.getAncestor():sonNode.getDepth());
				}
			}
		}
		return res;
	}
	
	private  Bridge execB(Graph myGraph){
		Bridge b = null;
		b= detectBridge(myGraph);
		return b;
	}
	
	@Override
	public ExecReturn exec(ExecParameter args) {
		// TODO 自动生成的方法存根
		if(args.size()!=1 || args.get(0).getClass()!=Graph.class)
			return null;
		Graph myGraph = (Graph) args.get(0);
		JSONArray jArray = this.execB(myGraph).packetToJSON();
		ExecReturn res = new ExecReturn();
		res.addResult(jArray);
		return res;
	}
}
