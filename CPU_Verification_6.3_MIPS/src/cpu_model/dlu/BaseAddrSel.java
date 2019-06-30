package cpu_model.dlu;

import cpu_model.element.DataPort;

public class BaseAddrSel extends DLU {
//	����˿�
	private DataPort Sel;
	private DataPort PC;
//	����˿�
	private DataPort Out;	
		
	public BaseAddrSel() {
		this.name = "BaseAddrSel";
		
		Sel = addDataPort(this, name + ".Sel");
		PC = addDataPort(this, name + ".PC");
		Out = addDataPort(this, name + ".Out");
	}

}
