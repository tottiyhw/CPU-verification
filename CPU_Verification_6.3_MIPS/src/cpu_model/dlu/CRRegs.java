package cpu_model.dlu;

import cpu_model.element.CtrlPort;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;

public class CRRegs extends DLU {

//	输入端口
	private DataPort RReg1;
	private DataPort RReg2;
	private DataPort WReg;
	private DataPort RBitReg1;
	private DataPort RBitReg2;
	private DataPort R4BitReg1;
	private DataPort R4BitReg2;
	private DataPort WData;
	private DataPort CR0In;
	private DataPort W1bitReg;
	private DataPort W1bitData;
	private DataPort W4bitReg;
	private DataPort W4bitData;
//	输出端口
	private DataPort RData1;
	private DataPort RData2;
	private DataPort RBitdata1;
	private DataPort RBitdata2;
	private DataPort R4Bitdata1;
	private DataPort R4Bitdata2;
	private DataPort Out;
//	控制端口
	private CtrlPort Ctrl;
	private CtrlPort CtrlCR0;
	private CtrlPort CtrlW4bitRegs;
	private CtrlPort CtrlW1bitRegs;
		
	public CRRegs() {
		this.name = "CRRegs";	
		reg = addReg(this, name);
//		输入端口
		RReg1 = addDataPort(this, name + ".RReg1");
		RReg2 = addDataPort(this, name + ".RReg2");
		WReg = addDataPort(this, name + ".WReg");
		WData = addDataPort(this, name + ".WData");			
		CR0In = addDataPort(this, name + ".CR0In");
		W4bitReg = addDataPort(this, name + ".W4bitReg");
		W4bitData = addDataPort(this, name + ".W4bitData");
		RBitReg1 = addDataPort(this, name + ".RBitReg1");
		RBitReg2 = addDataPort(this, name + ".RBitReg2");
		R4BitReg1 = addDataPort(this, name + ".R4BitReg1");
		R4BitReg2 = addDataPort(this, name + ".R4BitReg2");
		W1bitReg = addDataPort(this, name + ".W1bitReg");
		W1bitData = addDataPort(this, name + ".W1bitData");
//		输出端口
		RData1 = addDataPort(this, name + ".RData1");
		RData2 = addDataPort(this, name + ".RData2");
		RBitdata1 = addDataPort(this, name + ".RBitdata1");
		RBitdata2 = addDataPort(this, name + ".RBitdata2");
		R4Bitdata1 = addDataPort(this, name + ".R4Bitdata1");
		R4Bitdata2 = addDataPort(this, name + ".R4Bitdata2");
		Out = addDataPort(this, name + ".Out");
//		控制端口
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");
		CtrlCR0 = addCtrlPort(this, "Ctrl" + name + "CR0", "CR0");
		CtrlW4bitRegs = addCtrlPort(this, "Ctrl" + name + "W4bitRegs", "W4bitRegs");
		CtrlW1bitRegs = addCtrlPort(this, "Ctrl" + name + "W1bitRegs", "W1bitRegs");
	}	

}
