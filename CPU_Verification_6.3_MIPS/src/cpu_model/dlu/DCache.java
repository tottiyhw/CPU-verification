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

public class DCache extends DLU {

//	输入端口
	private DataPort IEA;
	private DataPort WData;
	private DataPort In;
//	输出端口
	private DataPort Out;
	private DataPort Hit;
	private DataPort RLineEA;
	private DataPort RLineData;
	private DataPort RLineDirty;
//	控制端口
	private CtrlPort Ctrl;
		
	public DCache() {
		this.name = "DCache";		
		
		reg = addReg(this, name);
//		输入端口
		IEA = addDataPort(this, name + ".IEA");
		In = addDataPort(this, name + ".In");
		WData = addDataPort(this, name + ".WData");
//		输出端口
		Out = addDataPort(this, name + ".Out");
		Hit = addDataPort(this, name + ".Hit");
		RLineEA = addDataPort(this, name + ".RLineEA");
		RLineData = addDataPort(this, name + ".RLineData");
		RLineDirty = addDataPort(this, name + ".RLineDirty");
//		控制端口
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");
		
	}
}
