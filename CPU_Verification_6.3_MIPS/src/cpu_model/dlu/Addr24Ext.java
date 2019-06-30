package cpu_model.dlu;

import cpu_model.element.DataPort;

public class Addr24Ext extends DLU{
	protected DataPort In;
	protected DataPort Out;
	// ·ûºÅÎ»À©Õ¹
	public Addr24Ext() {
		
		this.name = "Addr24Ext";
		
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		
	}
}
