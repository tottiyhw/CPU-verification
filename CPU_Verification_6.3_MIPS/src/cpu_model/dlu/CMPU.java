package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class CMPU extends DLU {

	private DataPort A;
	private DataPort B;
	private DataPort Func;
	private DataPort Out;
	private DataPort zero;
	private DataPort lt;
	private DataPort gt;
	
	public CMPU() {
		
		this.name = "CMPU";
		
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");
		Func = addDataPort(this, name + ".Func");
		Out = addDataPort(this, name + ".Out");		
		zero = addDataPort(this, name + ".zero");
		lt = addDataPort(this, name + ".lt");
		gt = addDataPort(this, name + ".gt");
	}
	
}
