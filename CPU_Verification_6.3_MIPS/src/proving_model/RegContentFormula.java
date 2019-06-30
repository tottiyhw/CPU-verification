package proving_model;

import cpu_model.element.*;


//“寄存器数据”类型公式
//[PC]=data
//GPRegs[reg]=data

public class RegContentFormula extends Formula {
	
	private Reg reg;
	private Data addr;
	private Data data;
	
	public RegContentFormula(Reg reg, Data data) {
		this.reg = reg;
		this.addr = null;
		this.data = data;
	}
	
	public RegContentFormula(Reg reg, Data addr, Data data) {
		this.reg = reg;
		this.addr = addr;
		this.data = data;
	}
	
	public Reg getReg() {
		return reg;
	}
	
	public Data getAddr() {
		return addr;
	}
	
	public Data getData() {
		return data;
	}
	
	public void assemble() {
		if (addr == null)
			str = "[" + reg.getName() + "]=" + data.getName();
		else
			str = reg.getName() + "[" + addr.getName() + "]=" + data.getName();				
	}
	
	public boolean equals(Formula f) {
		if (this == f)
			return true;
		if (f instanceof RegContentFormula) {
			RegContentFormula rdf = (RegContentFormula) f;
			if (this.addr == null && rdf.addr == null)
				return this.reg.equals(rdf.reg) && this.data.equals(rdf.data);
			else if (this.addr != null && rdf.addr != null)
				return this.reg.equals(rdf.reg) && this.addr.equals(rdf.addr) && this.data.equals(rdf.data);
		}
		return false;
	}


}
