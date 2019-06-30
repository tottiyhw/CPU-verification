package mipsCPUGen_Expressions;

import java.util.HashMap;

import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_util.CharReaderIterator;
import mipsCPUGen_util.CompilingException;

public class ConstantsExpression implements IExpression{
	private CharReaderIterator iter=null;
	private String expressionValue=null;
	private int size=0;
	public ConstantsExpression(CharReaderIterator iter){
		this.iter=iter;
	}
	@Override
	public void evaluate(ComputeComponetInfo unitInfo,
			HashMap<String, String> regs_mux, int InOut, int key)
			throws CompilingException {
		// TODO Auto-generated method stub
		if(InOut==0){
			int sepposition = iter.getCPosition("'");
			if (sepposition != -1) {
				String sizestring = iter.getIncludeSubString(0, sepposition);
				this.size = Integer.parseInt(sizestring);
				this.expressionValue = iter.toString();
			} else {
				throw new CompilingException("常数格式有错误:" + iter);
			}
		}else{
			throw new CompilingException("右端不应出现:" + iter);
		}
		
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
