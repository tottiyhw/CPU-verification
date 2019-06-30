package cpu_model.dlu;

import cpu_model.element.DataPort;

public class SEXT extends DLU {	
	
	protected DataPort In;
	protected DataPort Out;
	// 左移两位，符号位扩展
	public SEXT() {
		
		this.name = "SEXT";
		
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		
	}
	
}