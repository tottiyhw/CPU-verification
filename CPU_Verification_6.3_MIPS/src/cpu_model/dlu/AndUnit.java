package cpu_model.dlu;

import cpu_model.element.DataPort;

public class AndUnit extends DLU {
//	输入端口
	private DataPort A;
	private DataPort B;
//	输出端口
	private DataPort Out;
	private DataPort CMP;
		
	public AndUnit(){
		this.name = "AndUnit";	
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");
		Out = addDataPort(this, name + ".Out");
		CMP = addDataPort(this, name + ".CMP");
	}

}
