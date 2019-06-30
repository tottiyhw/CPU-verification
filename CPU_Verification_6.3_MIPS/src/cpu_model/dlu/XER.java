package cpu_model.dlu;

import cpu_model.cpu.CPU;
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

public class XER extends DLU {

	private DataPort SOIn;
	private DataPort OVIn;
	private DataPort CAIn;
	private DataPort SOOut;
	private DataPort OVOut;
	private DataPort CAOut;
	private CtrlPort CtrlSO;
	private CtrlPort CtrlOV;
	private CtrlPort CtrlCA;
		
	public XER() {
		
		this.name = "XER";	
		
		reg = addReg(this, name);
		SOIn = addDataPort(this, name + ".SOIn");
		OVIn = addDataPort(this, name + ".OVIn");
		CAIn = addDataPort(this, name + ".CAIn");
		SOOut = addDataPort(this, name + ".SOOut");
		OVOut = addDataPort(this, name + ".OVOut");
		CAOut = addDataPort(this, name + ".CAOut");
		CtrlSO = addCtrlPort(this, "Ctrl" + name + "SO", "SO");
		CtrlOV = addCtrlPort(this, "Ctrl" + name + "OV", "OV");
		CtrlCA = addCtrlPort(this, "Ctrl" + name + "CA", "CA");
		
	}
	
}
