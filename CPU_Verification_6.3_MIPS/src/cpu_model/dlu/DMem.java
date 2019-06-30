package cpu_model.dlu;

import java.util.ArrayList;

import cpu_model.cpu.CPU;
import cpu_model.element.CtrlPort;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;
import proving_model.Axiom;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.RegContentFormula;

public class DMem extends DLU {

	
//	输入端口
	private DataPort WAddr;
	private DataPort WData;
	private DataPort RAddr;
	private DataPort Addr; //PPC 非MMU
	private DataPort MEM8WordWAddr;
	private DataPort MEM8WordWData;
	private DataPort ASID; //MIPS 非MMU
	private DataPort PID; //PPC 非MMU
//	输出端口
	private DataPort Out;
	private DataPort MEM8WordOut;
	private CtrlPort Ctrl;
	private CtrlPort Ctrl8Word;
		
	public DMem() {
		
		this.name = "DMem";		
		
		reg = addReg(this, name);
		PID = addDataPort(this, name + ".PID");
		ASID = addDataPort(this, name + ".ASID");
		WAddr = addDataPort(this, name + ".WAddr");
		WData = addDataPort(this, name + ".WData");
		RAddr = addDataPort(this, name + ".RAddr");
		Addr = addDataPort(this, name + ".Addr");
		MEM8WordWAddr = addDataPort(this, name + ".MEM8WordWAddr");
		MEM8WordWData = addDataPort(this, name + ".MEM8WordWData");
		Out = addDataPort(this, name + ".Out");
		MEM8WordOut = addDataPort(this, name + ".MEM8WordOut");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");
		Ctrl8Word = addCtrlPort(this, "Ctrl" + name + "8Word", "8Word");
	}
}
