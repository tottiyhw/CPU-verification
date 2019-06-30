package cpu_model.dlu;

import cpu_model.element.DataPort;

public class AddrSelMux extends DLU {

	private DataPort Sel;
	private DataPort Data;		
	private DataPort Out;	
		
	public AddrSelMux() {
		
		this.name = "AddrSelMux";
		
		Sel = addDataPort(this, name + ".Sel");
		Data = addDataPort(this, name + ".Data");		
		Out = addDataPort(this, name + ".Out");
				
	}

}
