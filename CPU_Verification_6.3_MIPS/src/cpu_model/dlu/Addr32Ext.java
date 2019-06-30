package cpu_model.dlu;

import cpu_model.element.DataPort;

public class Addr32Ext extends DLU{
	protected DataPort In;
	protected DataPort Out;
	
	public Addr32Ext() {
		
		this.name = "Addr32Ext";
		
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		
	}
}
