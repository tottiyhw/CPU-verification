package cpu_model.dlu;

import cpu_model.element.DataPort;



public class IMMEXT extends DLU {	
	protected DataPort In;
	protected DataPort Out;
	// ����λ��չ
	public IMMEXT() {
		
		this.name = "IMMEXT";
		
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		
	}
	
}
