package mipsCPUGen_Expressions;

import java.util.HashMap;

import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_util.CharReaderIterator;
import mipsCPUGen_util.CompilingException;

public class LogicExpression implements IExpression{
	private CharReaderIterator iter=null;
	private int size=0;
	public LogicExpression (CharReaderIterator iter){
		this.iter=iter;
		this.size=1;
	}

	@Override
	public void evaluate(ComputeComponetInfo unitInfo,
			HashMap<String, String> regs_mux, int InOut, int key)
			throws CompilingException {
		// TODO Auto-generated method stub
		
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
		return size;
	}

	@Override
	public String getExpressionValue() {
		// TODO Auto-generated method stub
		return iter.toString().replaceAll(".", "_");
	}

}
