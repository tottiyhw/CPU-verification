package cpu_model.dlu;

import cpu_model.element.DataPort;

public class Addr14Ext extends DLU {	
	protected DataPort In;
	protected DataPort Out;
	
	public Addr14Ext() {
		
		this.name = "Addr14Ext";
		
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		
	}

}
