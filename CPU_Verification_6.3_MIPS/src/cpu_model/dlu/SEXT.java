package cpu_model.dlu;

import cpu_model.element.DataPort;

public class SEXT extends DLU {	
	
	protected DataPort In;
	protected DataPort Out;
	// ������λ������λ��չ
	public SEXT() {
		
		this.name = "SEXT";
		
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		
	}
	
}