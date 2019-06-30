package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class ADDREXT extends DLU {
//	输入端口
	private DataPort In;
	private DataPort PCpart;
//	输出端口
	private DataPort Out;
	
	public ADDREXT() {
		this.name = "ADDREXT";
		
		In = addDataPort(this, name + ".In");
		PCpart = addDataPort(this, name + ".PCpart");
		Out = addDataPort(this, name + ".Out");	
	}
}
