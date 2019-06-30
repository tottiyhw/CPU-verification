package cpu_model.dlu;

import cpu_model.element.DataPort;

public class BU extends DLU {
//	输入端口
	private DataPort LI;
	private DataPort AA;
	private DataPort CIA;
	private DataPort Func;
	private DataPort CTRIn;
	private DataPort CRIn;
	private DataPort BO;
	private DataPort BI;
//	输出端口
	private DataPort Out;		
	private DataPort CTROK;
	private DataPort CondOK;	
		
	public BU() {
		this.name = "BU";		
		
		LI = addDataPort(this, name + ".LI");
		AA = addDataPort(this, name + ".AA");
		CIA = addDataPort(this, name + ".CIA");
		Func = addDataPort(this, name + ".Func");
		CTRIn = addDataPort(this, name + ".CTRIn");
		CRIn = addDataPort(this, name + ".CRIn");
		BO = addDataPort(this, name + ".BO");
		BI = addDataPort(this, name + ".BI");
		Out = addDataPort(this, name + ".Out");		
		CTROK = addDataPort(this, name + ".ctr_ok");		
		CondOK = addDataPort(this, name + ".cond_ok");		
	}

}
