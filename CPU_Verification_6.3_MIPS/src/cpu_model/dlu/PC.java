package cpu_model.dlu;

import java.util.ArrayList;

import cpu_model.cpu.CPU;
import cpu_model.element.CtrlPort;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.RegContentFormula;
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.Axiom;

public class PC extends DLU {
	
	private DataPort In;
	private DataPort CIA;
	private DataPort CIA31_28;
	private DataPort Out;
	private DataPort NIA;//PPC ·ÇMMU
	private CtrlPort Ctrl;
	private CtrlPort CtrlInc;
		
	public PC() {
		this.name = "PC";
		
		reg = addReg(this, name);
		In = addDataPort(this, name + ".In");
		CIA = addDataPort(this, name + ".CIA");
		CIA31_28 = addDataPort(this, name + ".CIA31_28");
		Out = addDataPort(this, name + ".Out");
		NIA = addDataPort(this, name + ".NIA");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "¡¤");
		CtrlInc = addCtrlPort(this, "Ctrl" + name + "Inc", "++");
		
	}
	
}

