package cpu_model.dlu;

import java.util.ArrayList;

import cpu_model.cpu.CPU;
import cpu_model.element.CtrlPort;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;
import proving_model.Conditions;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.RegContentFormula;
import proving_model.Axiom;

public class ICache extends DLU {

//	输入端口
	private DataPort IEA;
	private DataPort WData;
//	输出端口
	private DataPort Out;
	private DataPort Hit;
//	控制端口
	private CtrlPort Ctrl;
		
	public ICache() {
		this.name = "ICache";		
		
		reg = addReg(this, name);
		IEA = addDataPort(this, name + ".IEA");
		WData = addDataPort(this, name + ".WData");
		Out = addDataPort(this, name + ".Out");
		Hit = addDataPort(this, name + ".Hit");		
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");
	}
}
