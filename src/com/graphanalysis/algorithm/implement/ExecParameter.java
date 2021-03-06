package com.graphanalysis.algorithm.implement;

import java.util.Vector;

/**
 * @author Yan	Lingyong
 *用于在执行方法时填入参数
 */
public class ExecParameter {
	private Vector<Object> parameters;
	public ExecParameter(){
		this.parameters = new Vector<Object>();
	}
	public void addParameter(Object parameter){
		this.parameters.add(parameter);
	}
	public Object get(int i){
		if (i<this.parameters.size())
			return this.parameters.get(i);
		else
			return null;
	}
	public int size(){
		return this.parameters.size();
	}
	
	public void clear(){
		this.parameters.clear();
	}
}
