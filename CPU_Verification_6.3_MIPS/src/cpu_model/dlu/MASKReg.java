package cpu_model.dlu;

import cpu_model.element.CtrlPort;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;

public class MASKReg extends DLU {
//	����˿�
	private DataPort Start;
	private DataPort End;
//	����˿�
	private DataPort Out;
//	���ƶ˿�
	private CtrlPort Ctrl;
		
	public MASKReg(){
		this.name = "MASKReg";	
		reg = addReg(this, name);
		Start = addDataPort(this, name + ".Start");
		End = addDataPort(this, name + ".End");
		Out = addDataPort(this, name + ".Out");
		Ctrl = addCtrlPort(this, "Ctrl" + name,  "��");
	}

}
