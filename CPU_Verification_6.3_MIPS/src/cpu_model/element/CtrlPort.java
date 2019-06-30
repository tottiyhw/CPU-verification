package cpu_model.element;

import cpu_model.dlu.DLU;
import proving_model.Procedure;

public class CtrlPort extends Element {

	//数据通路中表示控制信号的符号，如·、++等。
	private String sign = null;
	
	private Procedure ctrlSignal = null;
	
	public CtrlPort(DLU dlu, String name){		
		super(dlu, name);		
	}
	
	public CtrlPort(DLU dlu, String name, String sign){				
		super(dlu, name);
		this.sign = sign;		
	}
	
	public void clear() {
		ctrlSignal = null;				
	}
	
	public void setCtrlSignal(Procedure pd) {
		ctrlSignal = pd;
	}
	
	public Procedure getCtrlSignal() {
		return ctrlSignal;
	}
	
	public boolean isActive() {
		if (ctrlSignal != null) {
			return ctrlSignal.signalIsActive();
		}
		return false;			
	}
	
	public boolean notActive() {
		if (ctrlSignal != null) {
			return !ctrlSignal.signalIsActive();
		}
		return false;
	}
	
	public boolean signIs(String s) {
		if(this.sign!=null && this.sign.equals(s))
			return true;
		return false;
	}
	
	public boolean equals(CtrlPort c) {
		return this.nameIs(c.getName());
	}
	
	
}
