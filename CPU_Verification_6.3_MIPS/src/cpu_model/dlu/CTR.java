package cpu_model.dlu;

import cpu_model.element.CtrlPort;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;

public class CTR extends DLU {

	private DataPort In;
	private DataPort Out;
	private CtrlPort Ctrl;
	private CtrlPort CtrlDec;
		
	public CTR() {
		this.name = "CTR";		
		
		reg = addReg(this, name);
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "¡¤");
		CtrlDec = addCtrlPort(this, "Ctrl" + name + "Dec", "--");
	}

}
