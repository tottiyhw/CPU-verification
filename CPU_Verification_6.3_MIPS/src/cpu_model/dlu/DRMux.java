package cpu_model.dlu;

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

public class DRMux extends DLU {

	private Reg reg;
//	输入端口
	private DataPort MemData;
	private DataPort MemSel;
	private DataPort CacheData;
	private DataPort CacheSel;
//	输出端口
	private DataPort Out;
//	控制端口
	private CtrlPort Ctrl;
		
	public DRMux() {
		
		this.name = "DRMux";		
		
		reg = addReg(this, name);
		MemData = addDataPort(this, name + ".MemData");
		MemSel = addDataPort(this, name + ".MemSel");
		CacheData = addDataPort(this, name + ".CacheData");
		CacheSel = addDataPort(this, name + ".CacheSel");
		Out = addDataPort(this, name + ".Out");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");
				
	}
}
