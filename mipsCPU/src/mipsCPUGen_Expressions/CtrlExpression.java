package mipsCPUGen_Expressions;

import java.util.HashMap;

import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_util.CharReaderIterator;
import mipsCPUGen_util.CompilingException;

public class CtrlExpression implements IExpression{
	private CharReaderIterator iter=null;
	private String expressionValue=null;
	public CtrlExpression(CharReaderIterator iter){
		this.iter=iter;
	}
	@Override
	public void evaluate(ComputeComponetInfo unitInfo,
			HashMap<String, String> regs_mux, int InOut, int key)
			throws CompilingException {
		// TODO Auto-generated method stub
		if(InOut==1){
			if (iter.toString().equals("·")) {
				this.expressionValue = "Ctrl+";
			} else if (iter.toString().equals("++")) {
				this.expressionValue = "Ctrl+Inc";
			} else if (iter.toString().equals("--")) {
				this.expressionValue = "Ctrl+Dec";
			} else if (iter.toString().equals("<")) {
				this.expressionValue = "Ctrl+Clr";
			} else if (iter.toString().equals(">")) {
				this.expressionValue = "Ctrl+Set";
			} else {
				throw new CompilingException("不识别这个控制符号:" + iter);
			}
		}else{
			throw new CompilingException("左端不应出现:" + iter);
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
		return 1;
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
