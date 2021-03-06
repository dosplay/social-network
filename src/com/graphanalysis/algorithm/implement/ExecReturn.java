package com.graphanalysis.algorithm.implement;

import java.util.Vector;

/**
 * @author Yan	Lingyong
 *用于执行方法后保存参数
 */
public class ExecReturn {
	private Vector<Object> returns;
	public ExecReturn(){
		this.returns = new Vector<Object>();
	}
	public void addResult(Object parameter){
		this.returns.add(parameter);
	}
	public Object get(int i){
		if (i<this.returns.size())
			return this.returns.get(i);
		else
			return null;
	}
	public int size(){
		return this.returns.size();
	}
}
