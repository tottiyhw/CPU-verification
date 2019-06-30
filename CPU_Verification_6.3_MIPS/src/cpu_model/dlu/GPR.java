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
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.RegContentFormula;
import proving_model.Axiom;

public class GPR extends DLU {

	private DataPort RReg1;
	private DataPort RReg2;
	private DataPort RReg3;
	private DataPort RData1;
	private DataPort RData2;
	private DataPort RData3;
	private DataPort WReg;
	private DataPort WData;
	private CtrlPort Ctrl;
		
	public GPR() {
		
		this.name = "GPR";	
		
		reg = addReg(this, name);
		RReg1 = addDataPort(this, name + ".RReg1");
		RReg2 = addDataPort(this, name + ".RReg2");
		RReg3 = addDataPort(this, name + ".RReg3");
		RData1 = addDataPort(this, name + ".Rdata1");
		RData2 = addDataPort(this, name + ".Rdata2");
		RData3 = addDataPort(this, name + ".Rdata3");
		WReg = addDataPort(this, name + ".WReg");
		WData = addDataPort(this, name + ".WData");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "¡¤");
		
	}

}
