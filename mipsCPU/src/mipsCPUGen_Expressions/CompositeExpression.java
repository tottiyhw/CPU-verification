package mipsCPUGen_Expressions;

import java.util.ArrayList;
import java.util.HashMap;

import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_util.CharReaderIterator;
import mipsCPUGen_util.CompilingException;

public class CompositeExpression implements IExpression{
	private CharReaderIterator iter=null;
	private ArrayList<IExpression> parameter = new ArrayList<IExpression>();
	private int size;
	private String expressionValue;
	public CompositeExpression(CharReaderIterator iter){
		this.iter=iter;
	}
	public void add(IExpression parameter){
		this.parameter.add(parameter);
	}
	@Override
	public void evaluate(ComputeComponetInfo unitInfo,
			HashMap<String, String> regs_mux, int InOut, int key)
			throws CompilingException {
		// TODO Auto-generated method stub
		int sizevalue=0;
		String value="";
		for(int i=0;i<parameter.size();i++){
			IExpression ie=parameter.get(i);
			ie.evaluate(unitInfo, regs_mux, 0,0);
			if((ie instanceof StringExpression)||(ie instanceof BitSelectExpression)){
				unitInfo.setPortAndUnit(ie.getUnitName(), ie.getPortName(),ie.getUnitName()+"_"+ie.getPortName());
			}
			int sizevaluetemp=ie.getSize();
			sizevalue=sizevalue+sizevaluetemp;
			if(i<parameter.size()-1){
				value=value+ie.getExpressionValue()+",";
			}else{
				value=value+ie.getExpressionValue();
			}
		}
		value="{"+value+"}";
		this.size=sizevalue;
		this.expressionValue=value;
	}
	@Override
	public String getUnitName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPortName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return this.size;
	}
	@Override
	public String getExpressionValue() {
		// TODO Auto-generated method stub
		return this.expressionValue;
	}
	
	public String toString(){
		return this.iter.toString();
	}

}
