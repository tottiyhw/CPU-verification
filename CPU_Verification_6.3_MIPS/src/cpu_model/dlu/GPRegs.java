package cpu_model.dlu;

import cpu_model.element.CtrlPort;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;

public class GPRegs extends DLU {

	private DataPort RReg1;
	private DataPort RReg2;
	private DataPort RReg3;
	private DataPort RData1;
	private DataPort RData2;
	private DataPort RData3;

	private DataPort Rdata1;
	private DataPort Rdata2;
	private DataPort Rdata3;
	private DataPort WReg;
	private DataPort WData;
	private DataPort WBReg;
	private DataPort WBData;
	private CtrlPort Ctrl;
		
	public GPRegs() {
		
		this.name = "GPRegs";	
		
		reg = addReg(this, name);
		RReg1 = addDataPort(this, name + ".RReg1");
		RReg2 = addDataPort(this, name + ".RReg2");
		RReg3 = addDataPort(this, name + ".RReg3");
		RData1 = addDataPort(this, name + ".RData1");
		RData2 = addDataPort(this, name + ".RData2");
		RData3 = addDataPort(this, name + ".RData3");
		Rdata1 = addDataPort(this, name + ".Rdata1");
		Rdata2 = addDataPort(this, name + ".Rdata2");
		Rdata3 = addDataPort(this, name + ".Rdata3");
		WReg = addDataPort(this, name + ".WReg");
		WData = addDataPort(this, name + ".WData");
		WBReg = addDataPort(this, name + ".WBReg");
		WBData = addDataPort(this, name + ".WBData");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "¡¤");
						
	}
}
