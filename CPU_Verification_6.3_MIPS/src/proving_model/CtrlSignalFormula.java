package proving_model;

import cpu_model.element.*;

//“控制信号”类型公式
//ctrl=0
//ctrl=1
public class CtrlSignalFormula extends Formula {
	
	private CtrlPort ctrl;
	private Data signal;
	
	//想想怎么限制data的值只能为0或1
	public CtrlSignalFormula(CtrlPort ctrl, Data signal) {
		this.ctrl = ctrl;
		this.signal = signal;
	}
	
	public CtrlPort getCtrl() {
		return ctrl;
	}
	
	public boolean signalIsActive() {
		return signal.nameIs("1");
	}

	public void assemble() {
		str = ctrl.getName() + "=" + signal.getName();
	}
	
	public boolean equals(Formula f) {
		if (this == f)
			return true;
		if (f instanceof CtrlSignalFormula) {
			CtrlSignalFormula csf = (CtrlSignalFormula) f;
			return this.ctrl.equals(csf.ctrl) && this.signal.equals(csf.signal);
		}
		return false;
	}
	
}
