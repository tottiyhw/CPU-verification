package cpu_model.dlu;

import cpu_model.element.DataPort;



public class IMMSEXT extends DLU {
	protected DataPort In;
	protected DataPort Out;
	public IMMSEXT() {
		
		this.name = "IMMSEXT";
		
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		
	}
	
}

