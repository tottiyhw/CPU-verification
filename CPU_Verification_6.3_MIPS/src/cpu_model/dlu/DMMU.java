package cpu_model.dlu;

import cpu_model.element.CtrlPort;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.RegContentFormula;

public class DMMU extends DLU {

	private DataPort PID;
	private DataPort IEA;
	private DataPort IEAR;
	private DataPort Addr;
	private DataPort AddrR;
	private DataPort Hit;
	private CtrlPort Ctrl;
		
	public DMMU() {
		
		this.name = "DMMU";		
		
		reg = addReg(this, name);
		PID = addDataPort(this, name + ".PID");
		IEA = addDataPort(this, name + ".IEA");
		IEAR = addDataPort(this, name + ".IEAR");
		Addr = addDataPort(this, name + ".Addr");
		AddrR = addDataPort(this, name + ".AddrR");
		Hit = addDataPort(this, name + ".Hit");		
		Ctrl = addCtrlPort(this, "Ctrl" + name, "¡¤");
				
	}
}
