package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class ALU1bit extends DLU {
		
	private DataPort A;
	private DataPort B;
	private DataPort CAIn;
	private DataPort Func;
	private DataPort Out;
	private DataPort CMP;
	private DataPort OV;
	private DataPort CA;
		
	public ALU1bit() {		
		
		this.name = "ALU1bit";
		
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");
		CAIn = addDataPort(this, name + ".CAIn");
		Func = addDataPort(this, name + ".Func");
		Out = addDataPort(this, name + ".Out");
		CMP = addDataPort(this, name + ".CMP");
		OV = addDataPort(this, name + ".OV");
		CA = addDataPort(this, name + ".CA");
		
	}

}