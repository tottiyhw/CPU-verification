package mipsCPUGen_Expressions;

import java.util.HashMap;

import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_util.CharReaderIterator;
import mipsCPUGen_util.CompilingException;

public class StringExpression implements IExpression{
	private CharReaderIterator iter=null;
	private String unitName;
	private String portName;
	private int size;
	private String expressionValue;
	public StringExpression(CharReaderIterator iter){
		this.iter=iter;
	}
	@Override
	public void evaluate(ComputeComponetInfo unitInfo,
			HashMap<String, String> regs_mux, int InOut, int key)
			throws CompilingException {
		// TODO Auto-generated method stub
		if(iter.contains("{")||iter.contains("}")||iter.contains("[")||iter.contains("]")){
			throw new CompilingException("格式错误，包含不适合位置的括号:" + iter);
		}else{
			
			String unitpart;
			String portpart;
			String connectInstance;
			int sepposition=iter.getCPosition(".");
			if(sepposition==-1){
				unitpart=iter.toString();
				portpart="Out";
			}else{
				unitpart=iter.getIncludeSubString(0, sepposition);
				String secondpart=iter.getExcludeSubString(sepposition, iter.length());
				if(secondpart.contains(".")){
					portpart=secondpart.replace(".", "_");
				}else{
					portpart=secondpart;
				}
			}
			boolean valid;
			if(InOut==0){
				if(key==0){
					connectInstance=unitpart+"_"+portpart;
					valid=unitInfo.setPortAndUnit(unitpart, portpart, connectInstance);
				}else{
					valid=true;
				}
				
			}else{
				String mux=regs_mux.get(iter.toString());
				valid=unitInfo.setPortAndUnit(unitpart, portpart, mux+"_Out");
			}
			
			if(valid){
				if(key==0){
					this.unitName=unitpart;
					this.portName=portpart;
					this.size=unitInfo.getPortSize(unitpart, portpart);
					this.expressionValue=unitName+"_"+portName;
				}else{
					this.unitName=unitpart;
					this.portName=iter.toString().replace(".", "_");
					this.size=1;
					this.expressionValue=iter.toString().replace(".", "_");
				}
			}else{
				throw new CompilingException("没有这个部件或者端口:" + iter);
			}
		}
	}
	@Override
	public String getUnitName() {
		// TODO Auto-generated method stub
		return this.unitName;
	}
	@Override
	public String getPortName() {
		// TODO Auto-generated method stub
		return this.portName;
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
