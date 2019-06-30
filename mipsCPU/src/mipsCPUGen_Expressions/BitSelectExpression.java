package mipsCPUGen_Expressions;

import java.util.HashMap;

import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_util.CharReaderIterator;
import mipsCPUGen_util.CompilingException;

public class BitSelectExpression implements IExpression{
	private CharReaderIterator iter=null;
	private String unitName=null;
	private String portName=null;
	private int size=0;
	private String expressionValue=null;
	public BitSelectExpression(CharReaderIterator iter){
		this.iter=iter;
	}
	
	@Override
	public void evaluate(ComputeComponetInfo unitInfo,
			HashMap<String, String> regs_mux, int InOut, int key)
			throws CompilingException {
		// TODO Auto-generated method stub
		if(InOut==0&&key==0){
			int sizebegin=iter.getCPosition("[");
			int sizeend=iter.getCPosition("]");
			int sizeSepraterPosition=iter.getCPosition(":");
			int size;
			if(sizeSepraterPosition==-1){
				size=1;
			}else{
				int i=Integer.parseInt(iter.getExcludeSubString(sizebegin, sizeSepraterPosition));
				int j=Integer.parseInt(iter.getExcludeSubString(sizeSepraterPosition, sizeend));
				if(i>=j)
					size=i-j+1;
				else
					size=j-i+1;
			}
			String unitpart;
			String portpart;
			String connectInstance;
			int sepposition = iter.getCPosition(".");
			if (sepposition != -1) {
				unitpart = iter.getIncludeSubString(0, sepposition);
				portpart = iter.getExcludeSubString(sepposition, sizebegin);
			} else {
				unitpart = iter.getIncludeSubString(0, sizebegin);
				portpart = "Out";
			}
			connectInstance=unitpart+"_"+portpart;
			boolean valid=unitInfo.setPortAndUnit(unitpart, portpart, connectInstance);
			if(valid){
				this.unitName = unitpart;
				this.portName = portpart;
				this.size = size;
				this.expressionValue = unitName + "_" + portName+"["+iter.getExcludeSubString(sizebegin, sizeend)+"]";
			}else{
				throw new CompilingException("没有这个部件或端口:" + iter);
			}
		}else{
			throw new CompilingException("右端不应出现:" + iter);
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
