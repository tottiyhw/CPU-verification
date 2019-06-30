package cpu_model.dlu;

import cpu_model.element.DataPort;

public class AndOrUnit extends DLU {
//	输入端口
	private DataPort A;
	private DataPort B;
	private DataPort Mask;
//	输出端口
	private DataPort Out;
	private DataPort CMP;
		
	public AndOrUnit(){
		this.name = "AndOrUnit";	
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");
		Mask = addDataPort(this, name + ".Mask");
		Out = addDataPort(this, name + ".Out");
		CMP = addDataPort(this, name + ".CMP");
	}

}
