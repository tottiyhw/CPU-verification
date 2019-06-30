package cpu_model.dlu;

import cpu_model.element.CtrlPort;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;

public class MASKReg extends DLU {
//	输入端口
	private DataPort Start;
	private DataPort End;
//	输出端口
	private DataPort Out;
//	控制端口
	private CtrlPort Ctrl;
		
	public MASKReg(){
		this.name = "MASKReg";	
		reg = addReg(this, name);
		Start = addDataPort(this, name + ".Start");
		End = addDataPort(this, name + ".End");
		Out = addDataPort(this, name + ".Out");
		Ctrl = addCtrlPort(this, "Ctrl" + name,  "·");
	}

}
