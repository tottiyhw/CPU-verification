package cpu_model.dlu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpu_model.cpu.CPU;
import cpu_model.element.CtrlPort;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import cpu_model.element.Element;
import cpu_model.element.Reg;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.RegContentFormula;
import proving_model.Axiom;

public class IMMU extends DLU {

	private Reg reg;
	private DataPort PID;
	private DataPort IEA;
	private DataPort Addr;
	private DataPort Hit;
	private CtrlPort Ctrl;
		
	public IMMU() {
		
		this.name = "IMMU";		
		
		reg = addReg(this, name);
		PID = addDataPort(this, name + ".PID");
		IEA = addDataPort(this, name + ".IEA");
		Addr = addDataPort(this, name + ".Addr");
		Hit = addDataPort(this, name + ".Hit");		
		Ctrl = addCtrlPort(this, "Ctrl" + name, "¡¤");
	}	
}
