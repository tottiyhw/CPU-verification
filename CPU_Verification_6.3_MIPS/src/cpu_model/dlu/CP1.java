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

public class CP1 extends DLU {
//	输入端口
	private DataPort cc;
	private DataPort tf;
	private DataPort RReg;
	private DataPort WReg;
	private DataPort WData;
//	输出端口
	private DataPort fp;
	private DataPort RData;
//	控制端口
	private CtrlPort Ctrl;
		
	public CP1() {
		this.name = "CP1";	
		
		reg = addReg(this, name);
		cc = addDataPort(this, name + ".cc");
		tf = addDataPort(this, name + ".tf");
		RReg = addDataPort(this, name + ".RReg");
		WReg = addDataPort(this, name + ".WReg");
		WData = addDataPort(this, name + ".Wdata");
		RData = addDataPort(this, name + ".Rdata");
		fp = addDataPort(this, name + ".fp");
		
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");				
	}
}