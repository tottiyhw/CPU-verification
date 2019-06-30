package cpu_model.dlu;

import cpu_model.element.DataPort;

public class TrapUnit extends DLU {
//	����˿�
	private DataPort TO;
	private DataPort A;
	private DataPort B;
//	����˿�
	private DataPort Out;		
		
	public TrapUnit() {
		this.name = "TrapUnit";		
		
		TO = addDataPort(this, name + ".TO");
		A = addDataPort(this, name + ".A");
		B = addDataPort(this, name + ".B");
		Out = addDataPort(this, name + ".Out");		
	}

}
