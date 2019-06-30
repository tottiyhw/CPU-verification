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
	
//	����˿�
	private DataPort RAddr;
	private DataPort ASID; //MIPS ��MMU
	private DataPort PID; //PPC ��MMU
	private DataPort Addr; //PPC ��MMU
//	����˿�
	private DataPort RData;
	private DataPort MEM8WordOut;
	private DataPort Out;
//	���ƶ˿�
	private CtrlPort Ctrl;
		
	public IMem() {
		this.name = "IMem";				
		reg = addReg(this, name);
		PID = addDataPort(this, name + ".PID"); //PPC ��MMU
		Addr = addDataPort(this, name + ".Addr"); //PPC ��MMU
		RAddr = addDataPort(this, name + ".RAddr");
		ASID = addDataPort(this, name + ".ASID");
		MEM8WordOut = addDataPort(this, name + ".MEM8WordOut");
		Out = addDataPort(this, name + ".Out");
		RData = addDataPort(this, name + ".RData");	//PPC ��MMU
		Ctrl = addCtrlPort(this, "Ctrl" + name, "��");
		
	}
}
