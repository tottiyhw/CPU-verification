package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class ALU extends DLU {
		
	private DataPort A;
	private DataPort B;
	private DataPort CAIn;
	private DataPort Func;
	private DataPort Out;
	private DataPort Out1_0;
	private DataPort CMP;
	private DataPort OV;
	private DataPort CA;
		
	public ALU() {		
		
		this.name = "ALU";
		
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");
		CAIn = addDataPort(this, name + ".CAIn");
		Func = addDataPort(this, name + ".Func");
		Out = addDataPort(this, name + ".Out");
		Out1_0 = addDataPort(this, name + ".Out1_0");
		CMP = addDataPort(this, name + ".CMP");
		OV = addDataPort(this, name + ".OV");
		CA = addDataPort(this, name + ".CA");
		
	}

}
