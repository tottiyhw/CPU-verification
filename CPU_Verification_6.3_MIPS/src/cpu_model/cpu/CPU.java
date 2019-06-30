package cpu_model.cpu;


import java.util.ArrayList;
import java.util.HashMap;

import cpu_model.dlu.*;
import cpu_model.element.*;
import proving_model.*;


//CPU类
//实例化为CPU对象
//拥有存储备用DLU的静态变量，和管理DLU和Element的静态方法

public class CPU {
	
	//DLU仓库
	private static ArrayList<DLU> dluStore;
	
	//DLU列表
	private static ArrayList<DLU> dluList;
	//数据列表
	private static ArrayList<Data> dataList;
	public static String testType;
	public static int StageSum;
//	public static String[] StageName = {"PRE","IF","IMMU","ID","EX","MEM","DMMU1","DMMU2","WB","POST"};
	public static String[] StageName = {"PRE","IF","ID","EX","MEM","WB","POST"};
	public static HashMap<String, ArrayList<Axiom>> theorems;
	
	public static void init(int stageSum, String[] stageName) throws Exception {
		StageSum = stageSum;
		StageName = stageName;
	}
	
	//初始化，将CPU里的DLU和Data全部移除
	public static void init() throws Exception {
		if (dluStore==null){
			loadDLUStore();
		}
		dluList = new ArrayList<DLU>();
		dataList = new ArrayList<Data>();
	}
		
	//第一次使用要加载DLU仓库
	private static void loadDLUStore() throws Exception {
		
		dluStore = new ArrayList<DLU>();
		
		dluStore.add(new PC());
		if (testType.contains("MIPS")){
			dluStore.add(new IR());
		}
		else{
			dluStore.add(new IR2());
		}
		
		dluStore.add(new IMem());
		dluStore.add(new DMem());
		dluStore.add(new GPR());
		dluStore.add(new XER());
				
		dluStore.add(new Register("PIDReg"));
		dluStore.add(new Register("A"));
		dluStore.add(new Register("B"));
		dluStore.add(new Register("ALUOut"));
		dluStore.add(new Register("MDUOut"));
		dluStore.add(new Register("CMPUOut"));
		dluStore.add(new Register("CAReg"));
		dluStore.add(new Register("OVReg"));		
		dluStore.add(new Register("DR1bit"));
		dluStore.add(new Register("DR4bit"));
		dluStore.add(new Register("LR"));
		dluStore.add(new Register("DR"));
		dluStore.add(new Register("Hi"));
		dluStore.add(new Register("Lo"));
		dluStore.add(new Register("ConditionReg"));
		dluStore.add(new Register("LLbit"));
		
//		MMU、Cache相关
		dluStore.add(new Register("IAddrReg"));
		dluStore.add(new Register("IMMUHitReg"));
		dluStore.add(new Register("ICacheReg"));
		dluStore.add(new Register("ICacheHitReg"));
		dluStore.add(new Register("DAddrReg"));
		dluStore.add(new Register("DMMUHitReg"));
		dluStore.add(new Register("DCacheReg"));
		dluStore.add(new Register("DCacheHitReg"));
		dluStore.add(new Register("DR8Word"));
		dluStore.add(new Register("DRReg"));
		dluStore.add(new Register("DR"));
		dluStore.add(new IMMU());
		dluStore.add(new ICache());
		dluStore.add(new IRMux());
		dluStore.add(new DMMU());
		dluStore.add(new DCache());
		dluStore.add(new DRMux());
		
		if (testType.contains("MIPS")){
			dluStore.add(new CU());
		}
		else{
			dluStore.add(new CU2());
		}
		dluStore.add(new ALU());
		dluStore.add(new MDU());
		dluStore.add(new CMPU());
		dluStore.add(new SU());
		dluStore.add(new CP0());
		dluStore.add(new CP1());
		dluStore.add(new CountUnit());
		dluStore.add(new ADDREXT());
		
		dluStore.add(new IMMEXT());
		dluStore.add(new LIMMEXT());
		dluStore.add(new IMMSEXT());
		dluStore.add(new SEXT());
		
		dluStore.add(new MemDataSel());
		
		dluStore.add(new Mux(1,4));
		dluStore.add(new Mux(2,1));
		dluStore.add(new Mux(3,1));
		dluStore.add(new Mux(4,1));
		dluStore.add(new Mux(5,1));
		dluStore.add(new Mux(6,1));
		dluStore.add(new Mux(7,1));
		dluStore.add(new Mux(8,1));
		dluStore.add(new Mux(9,2));
		dluStore.add(new Mux(10,8));
		dluStore.add(new Mux(11,2));
		dluStore.add(new Mux(12,1));
		dluStore.add(new Mux(13,1));
		dluStore.add(new Mux(14,1));
		dluStore.add(new Mux(15,1));
		dluStore.add(new Mux(16,1));
		dluStore.add(new Mux(17,1));
		dluStore.add(new Mux(18,2));
		dluStore.add(new Mux(19,2));
		dluStore.add(new Mux(20,1));
		dluStore.add(new Mux(21,1));
		dluStore.add(new Mux(22,1));
		dluStore.add(new Mux(23,1));
		dluStore.add(new Mux(24,4));
		dluStore.add(new Mux(25,2));		
		dluStore.add(new Mux(26,1));
		dluStore.add(new Mux(27,1));
		dluStore.add(new Mux(28,1));
		dluStore.add(new Mux(29,1));
		dluStore.add(new Mux(30,2));
		dluStore.add(new Mux(31,1));
		dluStore.add(new Mux(32,1));
		dluStore.add(new Mux(33,1));
		dluStore.add(new Mux(34,2));
		dluStore.add(new Mux(35,1));
		dluStore.add(new Mux(36,1));
		dluStore.add(new Mux(37,1));
		dluStore.add(new Mux(38,3));
		dluStore.add(new Mux(39,2));
		dluStore.add(new Mux(40,1));
		dluStore.add(new Mux(41,1));
		dluStore.add(new Mux(42,1));
		dluStore.add(new Mux(43,1));
		dluStore.add(new Mux(44,1));
		dluStore.add(new Mux(45,1));
		dluStore.add(new Mux(46,1));
		dluStore.add(new Mux(47,1));
		dluStore.add(new Mux(48,1));
		dluStore.add(new Mux(49,1));
		dluStore.add(new Mux(50,3));
		dluStore.add(new Mux(51,3));
		dluStore.add(new Mux(52,1));
		dluStore.add(new Mux(53,1));
		dluStore.add(new Mux(54,2));
		dluStore.add(new Mux(55,1));
		dluStore.add(new Mux(56,2));
		dluStore.add(new Mux(57,1));
		dluStore.add(new Mux(58,1));
		dluStore.add(new Mux(59,1));
		dluStore.add(new Mux(60,1));
		dluStore.add(new Mux(61,1));
		dluStore.add(new Mux(62,1));
		dluStore.add(new Mux(63,1));
		dluStore.add(new Mux(64,1));
		
//		PPC
		dluStore.add(new ShamtReg());
		dluStore.add(new GPRegs());
		dluStore.add(new DataCmb());
		dluStore.add(new CRRegs());
		dluStore.add(new ORGate());
		dluStore.add(new AddrSelMux());
		dluStore.add(new Addr24Ext());
		
//		PPC MMU
		dluStore.add(new Addr14Ext());
		dluStore.add(new Addr32Ext());
		dluStore.add(new ALU1bit());
		dluStore.add(new AndOrUnit());
		dluStore.add(new AndUnit());
		dluStore.add(new BaseAddrSel());
		dluStore.add(new BU());
		dluStore.add(new CTR());
		dluStore.add(new MASKReg());
		dluStore.add(new TrapUnit());
		dluStore.add(new Register("A1bit"));
		dluStore.add(new Register("B1bit"));
		dluStore.add(new Register("ALU1bitOut"));
		
		int i;
		for (i = 0; i < dluStore.size(); i++){
			String str = dluStore.get(i).getClass().getName();
			str = str.replaceAll("cpu_model.dlu.", "");
			if (theorems.containsKey(str)){
				dluStore.get(i).axioms = new ArrayList<Axiom>();
				int j;
				for (j = 0; j < theorems.get(str).size(); j++){
					if (theorems.get(str).get(j).getName().contains("#name#")){
						String aName = theorems.get(str).get(j).getName();
						aName = aName.replaceAll("#name#", dluStore.get(i).getName());
						Axiom aA = new Axiom(aName);
						int k;
						for (k = 0; k < theorems.get(str).get(j).getList().size(); k++){
							String aStr = theorems.get(str).get(j).getList().get(k);
							aStr = aStr.replaceAll("#name#", dluStore.get(i).getName());
							aA.addItem(aStr);
						}
						dluStore.get(i).axioms.add(aA);
					}
					else{
						dluStore.get(i).axioms.add(theorems.get(str).get(j));
					}
				}
			}
		}
	}
		
	//清空数据
	public static void clear() {
		for(DLU dlu : dluList)
			dlu.clear();
		
		//debug
		//System.out.println();
	}
	
	//添加上阶段寄存器内容
	public static void addLastContent(Procedure pd) {
		
		//debug
		//System.out.println(pd.getFormula().getStr());
		
		Formula f = pd.getFormula();
		RegContentFormula rdf = (RegContentFormula) f;
		Reg reg = rdf.getReg();	
		reg.addToLastContent(pd);
		reg.getDLU().applyTheorems();
		
	}
		
	//添加当前阶段步骤
	public static void addProcedure(Procedure pd) {
		//debug
		
		Formula f = pd.getFormula();		
		
		if (f.isPathFormula()) {
			PathFormula pf = (PathFormula) f;
			DataPort port1 = pf.getPort1();
			if (port1 == null){
				System.out.println("error!");
			}
			try{
				port1.addToPathList(pd);
			}
			catch (Exception e){
				
			}
			Path.applyTheorems(port1);
		}
		
		else if (f.isPortDataFormula()) {
			PortDataFormula pdf = (PortDataFormula) f;
			DataPort port = pdf.getPort();
			port.setPortData(pd);
			port.getDLU().applyTheorems();
			Path.applyTheorems(port);
		}
		
		else if (f.isCtrlSignalFormula()) {
			CtrlSignalFormula csf = (CtrlSignalFormula) f;
			CtrlPort ctrl = csf.getCtrl();
			ctrl.setCtrlSignal(pd);
			ctrl.getDLU().applyTheorems();
		}
		
		else if (f.isRegContentFormula()) {
			RegContentFormula rdf = (RegContentFormula) f;
			Reg reg = rdf.getReg();
			reg.addToCurContent(pd);
			reg.getDLU().applyTheorems();
		}
	}	
	
	//获取寄存元件
	public static Reg getReg(String name) {
		for (DLU dlu : dluStore)
			for (Element e : dlu.getElementList())
				if (e.isReg() && e.nameIs(name)) {
					if (!dluList.contains(dlu))
						dluList.add(dlu);
					return (Reg) e; 
				}
		return null;
	}
	
	//获取数据端口
	public static DataPort getDataPort(String name) {
		for (DLU dlu : dluStore)
			for (Element e : dlu.getElementList())
				if (e.isDataPort() && e.nameIs(name)) {
					if (!dluList.contains(dlu))
						dluList.add(dlu);
					return (DataPort) e;
				}
		if (name.equals("0.0"))
			return getDataPort("0");
		return null;
	}
	
	//通过名称获取控制端口（Mux）
	public static CtrlPort getCtrlPort(String name) {
		for (DLU dlu : dluStore)
			for (Element e : dlu.getElementList())
				if (e.isCtrlPort() && e.nameIs(name)) {
					if (!dluList.contains(dlu))
						dluList.add(dlu);
					return (CtrlPort) e; 
				}
		return null;
	}
	
	//通过dluName和sign获取控制端口（寄存器）
	public static CtrlPort getCtrlPort(String dluName, String sign) {
		for (DLU dlu : dluStore)
			for (Element e : dlu.getElementList())
				if (e.isCtrlPort() && e.dluNameIs(dluName)) {
					CtrlPort cp = (CtrlPort) e;
					if (cp.signIs(sign)) {
						if (!dluList.contains(dlu))
							dluList.add(dlu);
						return cp;
					}
				}
		return null;
	}		
		
	//在dataList中找指定name的data，如果不存在则添加一个新Data
	public static Data addData(String name) {
		for (Data data : dataList)
			if (data.nameIs(name))
				return data;
		Data data = new Data(name);
		dataList.add(data);
		return data;
	}
	
	//获取所有控制端口
	public static ArrayList<CtrlPort> getCtrlPortList() {
		ArrayList<CtrlPort> ctrlPortList = new ArrayList<CtrlPort>();
		for (DLU dlu : dluList)
			for (Element e : dlu.getElementList())
				if (e.isCtrlPort())
					ctrlPortList.add((CtrlPort) e);
		return ctrlPortList;
	}		

	public static void setInstructionForm(String instructionForm) {
		if (testType.contains("MIPS")){
			IR.setInstructionForm(instructionForm);	
		}
		else{
			IR2.setInstructionForm(instructionForm);	
		}
	}
	
}
