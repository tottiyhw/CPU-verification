package cpu_model.dlu;

import cpu_model.element.DataPort;

public class BaseAddrSel extends DLU {
//	输入端口
	private DataPort Sel;
	private DataPort PC;
//	输出端口
	private DataPort Out;	
		
	public BaseAddrSel() {
		this.name = "BaseAddrSel";
		
		Sel = addDataPort(this, name + ".Sel");
		PC = addDataPort(this, name + ".PC");
		Out = addDataPort(this, name + ".Out");
	}

}
