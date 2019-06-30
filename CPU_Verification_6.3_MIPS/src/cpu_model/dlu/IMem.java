package cpu_model.dlu;

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

public class IMem extends DLU {
	
//	输入端口
	private DataPort RAddr;
	private DataPort ASID; //MIPS 非MMU
	private DataPort PID; //PPC 非MMU
	private DataPort Addr; //PPC 非MMU
//	输出端口
	private DataPort RData;
	private DataPort MEM8WordOut;
	private DataPort Out;
//	控制端口
	private CtrlPort Ctrl;
		
	public IMem() {
		this.name = "IMem";				
		reg = addReg(this, name);
		PID = addDataPort(this, name + ".PID"); //PPC 非MMU
		Addr = addDataPort(this, name + ".Addr"); //PPC 非MMU
		RAddr = addDataPort(this, name + ".RAddr");
		ASID = addDataPort(this, name + ".ASID");
		MEM8WordOut = addDataPort(this, name + ".MEM8WordOut");
		Out = addDataPort(this, name + ".Out");
		RData = addDataPort(this, name + ".RData");	//PPC 非MMU
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");
		
	}
}
