package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.DataPort;
import cpu_model.element.Data;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class SU extends DLU {

	private DataPort Data;		
	private DataPort Shamt;
	private DataPort Func;
	private DataPort Out;
	private DataPort CMP;
	private DataPort CA;
		
	public SU() {
		
		this.name = "SU";
		
		Data = addDataPort(this, name + ".Data");		
		Shamt = addDataPort(this, name + ".Shamt");
		Func = addDataPort(this, name + ".Func");
		Out = addDataPort(this, name + ".Out");
		CMP = addDataPort(this, name + ".CMP");
		CA = addDataPort(this, name + ".CA");
				
	}
	
}
