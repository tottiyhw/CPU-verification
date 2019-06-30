package cpu_model.dlu;

import cpu_model.element.CtrlPort;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;

public class ShamtReg extends DLU {

	private DataPort In;
	private DataPort In5bit;
	private DataPort In32bit;
	private DataPort Out;	
	private CtrlPort Ctrl;
		
	public ShamtReg() {
		
		this.name = "ShamtReg";
		
		reg = addReg(this, name);
		In = addDataPort(this, name + ".In");
		In5bit = addDataPort(this, name + ".In5bit");
		In32bit = addDataPort(this, name + ".In32bit");		
		Out = addDataPort(this, name + ".Out");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "¡¤");
				
	}

}
