package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.DataPort;
import cpu_model.element.Data;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class MemDataSel extends DLU {
//	输入端口
	private DataPort In;
	private DataPort Addr;
	private DataPort Func;
	private DataPort GPRIn;
	private DataPort Data; //PPC
//	输出端口
	private DataPort Out;	
		
	public MemDataSel() {
		this.name = "MemDataSel";
		In = addDataPort(this, name + ".In");
		Addr = addDataPort(this, name + ".Addr");
		Func = addDataPort(this, name + ".Func");
		GPRIn = addDataPort(this, name + ".GPRIn");
		Data = addDataPort(this, name + ".Data");
		Out = addDataPort(this, name + ".Out");
	}
	
}