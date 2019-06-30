package mipsCPUGen_Expressions;

import java.util.HashMap;

import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_util.CompilingException;


public interface IExpression {

	public void evaluate(ComputeComponetInfo unitInfo, HashMap<String, String> regs_mux,int InOut,int key)throws CompilingException;

	public String getUnitName();

	public String getPortName();

	public int getSize();

	public String getExpressionValue();
	
	public String toString();
	
}
