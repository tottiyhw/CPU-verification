package cpu_model.dlu;

import cpu_model.element.DataPort;



public class LIMMEXT extends DLU {
	protected DataPort In;
	protected DataPort Out;
	// ��������λ��չ
	public LIMMEXT() {
		
		this.name = "LIMMEXT";		
		
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		
	}
	
}
