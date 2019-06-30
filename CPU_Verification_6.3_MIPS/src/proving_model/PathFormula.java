package proving_model;

import cpu_model.element.DataPort;

//“通路”类型公式
//port1=>port2
public class PathFormula extends Formula {
	
	private DataPort port1;
	private DataPort port2;
	
	public PathFormula(DataPort port1, DataPort port2) {
		this.port1 = port1;
		this.port2 = port2;
	}
	
	public DataPort getPort1() {
		return port1;
	}
	
	public DataPort getPort2() {
		return port2;
	}
	
	public void assemble() {
		str = port1.getName() + "=>" + port2.getName();
	}
	
	public boolean equals(Formula f) {
		if (this == f)
			return true;
		if (f instanceof PathFormula) {
			PathFormula pf = (PathFormula) f;
			return this.port1.equals(pf.port1) && this.port2.equals(pf.port2);
		}
		return false;
	}

}
