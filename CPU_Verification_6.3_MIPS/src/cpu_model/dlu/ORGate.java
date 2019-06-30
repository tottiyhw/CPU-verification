package cpu_model.dlu;

import cpu_model.element.DataPort;

public class ORGate extends DLU {

	private DataPort A;
	private DataPort B;
	private DataPort Out;
		
	public ORGate() {
		
		this.name = "ORGate";	
		
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");
		Out = addDataPort(this, name + ".Out");
		
	}

}
