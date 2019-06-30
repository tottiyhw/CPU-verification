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

public class CP0 extends DLU {
//	输入端口
	private DataPort ASIDIn;
	private DataPort RReg;
	private DataPort WReg;
	private DataPort WData;
	private DataPort EPCIn;
	private DataPort ExCodeIn;
//	输出端口
	private DataPort ASID;
	private DataPort RData;
//	控制端口
	private CtrlPort CtrlASIDIn;
	private CtrlPort CtrlEPCIn;
	private CtrlPort CtrlExCodeIn;
	private CtrlPort Ctrl;
		
	public CP0() {
		
		this.name = "CP0";	
		
		reg = addReg(this, name);
		RReg = addDataPort(this, name + ".RReg");
		WReg = addDataPort(this, name + ".WReg");
		WData = addDataPort(this, name + ".Wdata");
		EPCIn = addDataPort(this, name + ".EPCIn");
		ExCodeIn = addDataPort(this, name + ".ExCodeIn");
		ASIDIn = addDataPort(this, name + ".ASIDIn");
		ASID = addDataPort(this, name + ".ASID");
		RData = addDataPort(this, name + ".Rdata");
		CtrlASIDIn = addCtrlPort(this, "CtrlASIDIn", ".");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");				
		CtrlEPCIn = addCtrlPort(this, "CtrlEPCIn", "EPC");				
		CtrlExCodeIn = addCtrlPort(this, "CtrlExCodeIn", "ExCode");
		
	}
}
