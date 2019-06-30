package proving_model;

import cpu_model.element.*;

//“端口数据”类型公式
//port=data
public class PortDataFormula extends Formula {
	
	private DataPort port;
	private Data data;
	
	public PortDataFormula(DataPort port, Data data) {
		this.port = port;
		this.data = data;
	}
	
	public DataPort getPort() {
		return port;
	}
	
	public Data getData() {
		return data;
	}

	public void assemble() {
		str = port.getName() + "=" + data.getName();
	}
	
	public boolean isSame(Formula f) {
		if(f instanceof PortDataFormula) {
			PortDataFormula pdf = (PortDataFormula)f;
			return this.port==pdf.port && this.data==pdf.data;
		}
		return false;
	}
	
	public boolean equals(Formula f) {
		if (this == f)
			return true;
		if (f instanceof PortDataFormula) {
			PortDataFormula pdf = (PortDataFormula) f;
			return this.port.equals(pdf.port) && this.data.equals(pdf.data);
		}
		return false;
	}
	
}
