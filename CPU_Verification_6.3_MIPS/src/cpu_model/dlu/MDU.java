package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class MDU extends DLU {	
//	输入端口
	private DataPort A;
	private DataPort B;
	private DataPort Func;
	private DataPort Hi;
	private DataPort Lo;
//	输出端口
	private DataPort Out0_31;
	private DataPort Out32_63;
	private DataPort Out16_47;
	private DataPort CMP;
	private DataPort OV;
	private DataPort hi;
	private DataPort lo;
		
	public MDU() {
		
		this.name = "MDU";		
		
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");
		Hi = addDataPort(this, name + ".Hi");
		Lo = addDataPort(this, name + ".Lo");
		Func = addDataPort(this, name + ".Func");
		Out0_31 = addDataPort(this, name + ".Out0_31");
		Out32_63 = addDataPort(this, name + ".Out32_63");
		Out16_47 = addDataPort(this, name + ".Out16_47");
		CMP = addDataPort(this, name + ".CMP");
		OV = addDataPort(this, name + ".OV");
		hi = addDataPort(this, name + ".hi");
		lo = addDataPort(this, name + ".lo");
				
	}
}
