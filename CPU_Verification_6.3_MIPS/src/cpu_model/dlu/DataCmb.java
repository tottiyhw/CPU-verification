package cpu_model.dlu;

import cpu_model.element.DataPort;

public class DataCmb extends DLU {

	private DataPort A;
	private DataPort B;		
	private DataPort Out;		
		
	public DataCmb() {
		
		this.name = "DataCmb";		
		
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");		
		Out = addDataPort(this, name + ".Out");
		
	}

}
