package mipsCPUGen_ComputeUnits;
import java.util.HashMap;

public class ComputeComponetInfo {
	private HashMap<String,ComputeComponent> computeUnitMap=new HashMap<String,ComputeComponent>();
	public ComputeComponetInfo(){
		this.init();
	}

	private void init(){
		ComputeComponent PC=new ComputeComponent("PC","PC_module");
		PC.addPort("clr", "input", "clr",1);
		PC.addPort("CtrlPC","input", "ctrl",1);
		PC.addPort("CtrlPCInc","input", "ctrl",1);
		PC.addPort("In","input", "data",32);
		PC.addPort("CIA","output", "data",32);
		PC.addPort("Out","output", "data",32);
		computeUnitMap.put("PC", PC);
		
		ComputeComponent GPR_module=new ComputeComponent("GPR","GPR_module");
		GPR_module.addPort("clr", "input", "clr",1);
		GPR_module.addPort("CtrlGPR","input", "ctrl",1);
		GPR_module.addPort("RReg1","input", "data",5);
		GPR_module.addPort("RReg2","input", "data",5);
		GPR_module.addPort("WReg","input", "data",5);
		GPR_module.addPort("WData","input", "data",32);
		GPR_module.addPort("Rdata1","output", "data",32);
		GPR_module.addPort("Rdata2","output", "data",32);
		computeUnitMap.put("GPR", GPR_module);
		
		ComputeComponent IMem=new ComputeComponent("IMem","IMemory_module");
		IMem.addPort("clr","input", "clr",1);
		IMem.addPort("CtrlIMem","input", "ctrl",1);
//		IMem.addPort("CtrlCache","input", "ctrl",1);
		IMem.addPort("Wdata","input", "data",32);
		IMem.addPort("RAddr","input", "data",32);
		IMem.addPort("ASID","input", "data",8);
		IMem.addPort("Out","output", "data",32);
//		IMem.addPort("Miss","output", "data",1);
//		IMem.addPort("CacheMiss","output", "data",1);
		computeUnitMap.put("IMem", IMem);
		
		ComputeComponent OVReg=new ComputeComponent("OVReg","OVReg_module");
		OVReg.addPort("clr", "input", "clr",1);
		OVReg.addPort("CtrlOVReg","input", "ctrl",1);
		OVReg.addPort("In","input", "data",1);
		OVReg.addPort("Out","output", "data",1);
		computeUnitMap.put("OVReg", OVReg);
		
		ComputeComponent ConditionReg=new ComputeComponent("ConditionReg","ConditionReg_module");
		ConditionReg.addPort("clr", "input", "clr",1);
		ConditionReg.addPort("CtrlConditionReg","input", "ctrl",1);
		ConditionReg.addPort("In","input", "data",1);
		ConditionReg.addPort("Out","output", "data",1);
		computeUnitMap.put("ConditionReg", ConditionReg);
		
		ComputeComponent MemDataSel=new ComputeComponent("MemDataSel","MemDataSel_module");
		MemDataSel.addPort("In","input", "data",32);
		MemDataSel.addPort("Func","input", "data",6);
		MemDataSel.addPort("Addr","input", "data",2);
		MemDataSel.addPort("GPRIn","input", "data",32);
		MemDataSel.addPort("Out","output", "data",32);
		computeUnitMap.put("MemDataSel", MemDataSel);
		
		ComputeComponent MDU=new ComputeComponent("MDU","MDU_module");
		MDU.addPort("Lo","input", "data",32);
		MDU.addPort("Hi","input", "data",32);
		MDU.addPort("A","input", "data",32);
		MDU.addPort("B","input", "data",32);
		MDU.addPort("Func","input", "data",6);
		MDU.addPort("lo","output", "data",32);
		MDU.addPort("hi","output", "data",32);
		computeUnitMap.put("MDU", MDU);
		
		ComputeComponent Lo=new ComputeComponent("Lo","Lo_module");
		Lo.addPort("clr", "input", "clr",1);
		Lo.addPort("CtrlLo","input", "ctrl",1);
		Lo.addPort("In","input", "data",32);
		Lo.addPort("Out","output", "data",32);
		computeUnitMap.put("Lo", Lo);
		
		ComputeComponent LLbit=new ComputeComponent("LLbit","LLbit_module");
		LLbit.addPort("clr", "input", "clr",1);
		LLbit.addPort("CtrlLLbit","input", "ctrl",1);
		LLbit.addPort("In","input", "data",1);
		LLbit.addPort("Out","output", "data",1);
		computeUnitMap.put("LLbit", LLbit);
		
		ComputeComponent PIDReg=new ComputeComponent("PIDReg","PIDReg_module");
		PIDReg.addPort("clr", "input", "clr",1);
		PIDReg.addPort("CtrlPIDReg","input", "ctrl",1);
		PIDReg.addPort("In","input", "data",8);
		PIDReg.addPort("Out","output", "data",8);
		computeUnitMap.put("PIDReg", PIDReg);
		
		ComputeComponent LIMMEXT=new ComputeComponent("LIMMEXT","LIMMEXT_module");
		LIMMEXT.addPort("In","input", "data",16);
		LIMMEXT.addPort("Out","output", "data",32);
		computeUnitMap.put("LIMMEXT", LIMMEXT);
		
		ComputeComponent IR=new ComputeComponent("IR","IR_module");
		IR.addPort("clr","input", "clr",1);
		IR.addPort("In","input", "data",32);
		IR.addPort("CtrlIR","input", "ctrl",1);
		IR.addPort("Out","output", "data",32);
		computeUnitMap.put("IR", IR);
		
		ComputeComponent IMMSEXT=new ComputeComponent("IMMSEXT","IMMSEXT_module");
		IMMSEXT.addPort("In","input", "data",16);
		IMMSEXT.addPort("Out","output", "data",32);
		computeUnitMap.put("IMMSEXT", IMMSEXT);
		
		ComputeComponent IMMEXT=new ComputeComponent("IMMEXT","IMMEXT_module");
		IMMEXT.addPort("In","input", "data",16);
		IMMEXT.addPort("Out","output", "data",32);
		computeUnitMap.put("IMMEXT", IMMEXT);
		
		ComputeComponent Hi=new ComputeComponent("Hi","Hi_module");
		Hi.addPort("clr", "input", "clr",1);
		Hi.addPort("CtrlHi","input", "ctrl",1);
		Hi.addPort("In","input", "data",32);
		Hi.addPort("Out","output", "data",32);
		computeUnitMap.put("Hi", Hi);
		
		ComputeComponent DR=new ComputeComponent("DR","DR_module");
		DR.addPort("clr", "input", "clr",1);
		DR.addPort("CtrlDR","input", "ctrl",1);
		DR.addPort("In","input", "data",32);
		DR.addPort("Out","output", "data",32);
		computeUnitMap.put("DR", DR);
		
		ComputeComponent DR0=new ComputeComponent("DR0","DR0_module");
		DR0.addPort("clr", "input", "clr",1);
		DR0.addPort("CtrlDR0","input", "ctrl",1);
		DR0.addPort("In","input", "data",32);
		DR0.addPort("Out","output", "data",32);
		computeUnitMap.put("DR0", DR0);
		
		ComputeComponent DMem=new ComputeComponent("DMem","DMemory_module");
		DMem.addPort("clr","input", "clr",1);
		DMem.addPort("CtrlDMem","input", "ctrl",1);
//		DMem.addPort("CtrlCache","input", "ctrl",1);
		DMem.addPort("WData","input", "data",32);
		DMem.addPort("WAddr","input", "data",32);
		DMem.addPort("RAddr","input", "data",32);
		DMem.addPort("ASID","input", "data",8);
		DMem.addPort("Out","output", "data",32);
//		DMem.addPort("Miss","output", "data",1);
//		DMem.addPort("CacheMiss","output", "data",1);
		computeUnitMap.put("DMem", DMem);

		ComputeComponent CU=new ComputeComponent("CU","CU_module");
		CU.addPort("clr", "input", "clr",1);
		CU.addPort("clk", "input", "clk",1);
		CU.addPort("Op","input", "data",6);
		CU.addPort("IRFunc","input", "data",6);
		CU.addPort("IRFunc1","input", "data",5);
		CU.addPort("IRFunc2","input", "data",5);
		CU.addPort("zero","input", "data",1);
		CU.addPort("OV","input", "data",1);
		CU.addPort("lt","input", "data",1);
		CU.addPort("gt","input", "data",1);
		CU.addPort("LLbit","input", "data",1);
		CU.addPort("fp","input", "data",1);
		CU.addPort("IR","input", "data",32);
		CU.addPort("IMiss","input", "data",1);
//		CU.addPort("ICacheMiss","input", "data",1);
		CU.addPort("DMiss","input", "data",1);
//		CU.addPort("DCacheMiss","input", "data",1);
		CU.addPort("TrapAddr","output", "data",32);
		CU.addPort("TrapAddr","output", "data",32);
//		CU.addPort("MemFunc","output", "data",6);
//		CU.addPort("Func","output", "data",6);
		computeUnitMap.put("CU", CU);
		
		ComputeComponent CP1=new ComputeComponent("CP1","CP1_module");
		CP1.addPort("clr","input", "clr",1);
		CP1.addPort("WReg","input", "data",5);
		CP1.addPort("Wdata","input", "data",32);
		CP1.addPort("RReg","input", "data",5);
		CP1.addPort("tf","input", "data",1);
		CP1.addPort("cc","input", "data",3);
		CP1.addPort("CtrlCP1","input", "ctrl",1);
		CP1.addPort("fp","output", "data",1);
		CP1.addPort("Rdata","output", "data",32);
		CP1.addPort("Out","output", "data",32);
		computeUnitMap.put("CP1", CP1);
		
		ComputeComponent CP0=new ComputeComponent("CP0","CP0_module");
		CP0.addPort("clr","input", "clr",1);
		CP0.addPort("WReg","input", "data",5);
		CP0.addPort("Wdata","input", "data",32);
		CP0.addPort("RReg","input", "data",5);
		CP0.addPort("ExCodeIn","input", "data",5);
		CP0.addPort("EPCIn","input", "data",32);
		CP0.addPort("ASIDIn","input", "data",8);
		CP0.addPort("CtrlCP0_EPC","input", "ctrl",1);
		CP0.addPort("CtrlCP0_ASID","input", "ctrl",1);
		CP0.addPort("CtrlCP0_ExCode","input", "ctrl",1);
		CP0.addPort("CtrlCP0","input", "ctrl",1);
		CP0.addPort("Rdata","output", "data",32);
		CP0.addPort("EPC","output", "data",32);
		CP0.addPort("ASID","output", "data",8);
		computeUnitMap.put("CP0", CP0);
		
		ComputeComponent CountUnit=new ComputeComponent("CountUnit","CountUnit_module");
		CountUnit.addPort("In","input", "data",32);
		CountUnit.addPort("Func","input", "data",6);
		CountUnit.addPort("Out","output", "data",32);
		computeUnitMap.put("CountUnit", CountUnit);
		
		ComputeComponent CMPU=new ComputeComponent("CMPU","CMPU_module");
		CMPU.addPort("A","input", "data",32);
		CMPU.addPort("B","input", "data",32);
		CMPU.addPort("Func","input", "data",6);
		CMPU.addPort("zero","output", "data",1);
		CMPU.addPort("lt","output", "data",1);
		CMPU.addPort("gt","output", "data",1);
		computeUnitMap.put("CMPU", CMPU);
		
		ComputeComponent B=new ComputeComponent("B","B_module");
		B.addPort("clr", "input", "clr",1);
		B.addPort("CtrlB","input", "ctrl",1);
		B.addPort("In","input", "data",32);
		B.addPort("Out","output", "data",32);
		computeUnitMap.put("B", B);
		
		ComputeComponent ALUOut=new ComputeComponent("ALUOut","ALUOut_module");
		ALUOut.addPort("clr", "input", "clr",1);
		ALUOut.addPort("CtrlALUOut","input", "ctrl",1);
		ALUOut.addPort("In","input", "data",32);
		ALUOut.addPort("Out","output", "data",32);
		computeUnitMap.put("ALUOut", ALUOut);
		
		ComputeComponent ALU=new ComputeComponent("ALU","ALU_module");
		ALU.addPort("A","input", "data",32);
		ALU.addPort("B","input", "data",32);
		ALU.addPort("Func","input", "data",6);
		ALU.addPort("Out","output", "data",32);
		ALU.addPort("OV","output", "data",1);
		computeUnitMap.put("ALU", ALU);
		
		ComputeComponent ADDREXT=new ComputeComponent("ADDREXT","ADDREXT_module");
		ADDREXT.addPort("In","input", "data",26);
		ADDREXT.addPort("PCpart","input", "data",4);
		ADDREXT.addPort("Out","output", "data",32);
		computeUnitMap.put("ADDREXT", ADDREXT);
		
		ComputeComponent A=new ComputeComponent("A","A_module");
		A.addPort("clr", "input", "clr",1);
		A.addPort("CtrlA","input", "ctrl",1);
		A.addPort("In","input", "data",32);
		A.addPort("Out","output", "data",32);
		computeUnitMap.put("A", A);
		
		ComputeComponent SU=new ComputeComponent("SU","Shift_unit");
		SU.addPort("Data", "input", "data",32);
		SU.addPort("Shamt","input", "data",5);
		SU.addPort("Func","input", "data",6);
		SU.addPort("Out","output", "data",32);
		computeUnitMap.put("SU", SU);
		
		ComputeComponent SEXT=new ComputeComponent("SEXT","SEXT_module");
		SEXT.addPort("In","input", "data",16);
		SEXT.addPort("Out","output", "data",32);
		computeUnitMap.put("SEXT", SEXT);
		
		
	}
	public HashMap<String,ComputeComponent> getComputeUnitMap(){
		return this.computeUnitMap;
	}
	public int getPortSize(String unitname,String portname){
		ComputeComponent unit=computeUnitMap.get(unitname);
		if(unit!=null){
			ComputeComponentPort port=unit.getPort(portname);
			if(port!=null){
				return port.getSize();
			}else{
				System.out.println(unitname+":"+portname+":端口不存在");
				return -1;
			}
		}else{
			System.out.println(unitname+":"+portname+":部件不存在");
			return -1;
		}
	}
	
	public void addAndSetCUPort(String portname,String inout,String connect,String type,int size){
		ComputeComponent unit=computeUnitMap.get("CU");
		ComputeComponentPort port=unit.getPort(portname);
		if(port==null){
			unit.setAppeared(true);
			unit.addPort(portname, inout, type, connect, size, true);
		}else{
			unit.setAppeared(true);
			port.setAppeared(true);
			port.setPortInstance(connect);
		}
		computeUnitMap.put("CU", unit);
	}
	
	
	
	public boolean setPortAndUnit(String unitname,String portname,String connectInstance){
		ComputeComponent unit=computeUnitMap.get(unitname);
		if(unit==null){
			System.out.println("没有这个部件"+unitname);
			return false;
		}else{
			ComputeComponentPort port=unit.getPort(portname);
			if(port==null){
				System.out.println("没有这个端口"+unitname+":"+portname);
				return false;
			}else{
				unit.setAppeared(true);
				port.setAppeared(true);
				port.setPortInstance(connectInstance);
				return true;
			}
		}
	}
	public boolean checkValidPortAndUnit(String unitname,String portname){
		ComputeComponent unit=computeUnitMap.get(unitname);
		if(unit==null){
			return false;
		}else{
			ComputeComponentPort port=unit.getPort(portname);
			if(port==null){
				return false;
			}else{
				unit.setAppeared(true);
				port.setAppeared(true);
				return true;
			}
		}
	}
	public boolean setPortConnect(String unitname,String portname,String connectInstance){
		ComputeComponent unit=computeUnitMap.get(unitname);
		if(unit==null){
			return false;
		}else{
			ComputeComponentPort port=unit.getPort(portname);
			if(port==null){
				return false;
			}else{
				port.setPortInstance(connectInstance);
				return true;
			}
		}
	}

}
