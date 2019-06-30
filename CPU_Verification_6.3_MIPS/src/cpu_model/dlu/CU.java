package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.RegContentFormula;

///*MIPS
public class CU extends DLU {

	private Reg reg;
	private DataPort Op;
	private DataPort IRFunc;
	private DataPort AA;
	private DataPort LK;
	private DataPort Func;
	private DataPort OV;
	private DataPort MemDataSelFunc;
	private DataPort IRFunc1;
	private DataPort IRFunc2;
	private DataPort fp;
	private DataPort zero;
	private DataPort gt;
	private DataPort lt;
	private DataPort IR;
	private DataPort TrapAddr;
//	MMU、Cache相关端口
	private DataPort IMemHit;
	private DataPort ICacheHit;
	private DataPort DMemHit;
	private DataPort DCacheHit;
	private DataPort ICacheWriteBack;
	private DataPort DCacheWriteBack;
		
	public CU() {		
		this.name = "CU";
		reg = addReg(this, name);
		Op = addDataPort(this, name + ".Op");
		IRFunc = addDataPort(this, name + ".IRFunc");
		AA = addDataPort(this, name + ".AA");
		LK = addDataPort(this, name + ".LK");
		OV = addDataPort(this, name + ".OV");
		Func = addDataPort(this, name + ".Func");
		MemDataSelFunc = addDataPort(this, name + ".MemDataSelFunc");
		IRFunc1 = addDataPort(this, name + ".IRFunc1");
		IRFunc2 = addDataPort(this, name + ".IRFunc2");
		fp = addDataPort(this, name + ".fp");
		zero = addDataPort(this, name + ".zero");
		gt = addDataPort(this, name + ".gt");
		lt = addDataPort(this, name + ".lt");
		IR = addDataPort(this, name + ".IR");
		TrapAddr = addDataPort(this, name + ".TrapAddr");
	
//		MMU、Cache相关端口
		IMemHit = addDataPort(this, name + ".IMemHit");
		ICacheHit = addDataPort(this, name + ".ICacheHit");
		DMemHit = addDataPort(this, name + ".DMemHit");
		DCacheHit = addDataPort(this, name + ".DCacheHit");
		ICacheWriteBack = addDataPort(this, name + ".ICacheWriteBack");
		DCacheWriteBack = addDataPort(this, name + ".DCacheWriteBack");
	}
	
	public void applyTheorems() {
		_CU();
	}
	
	private void _CU() {
		//CU.Op=op, CU.IRFunc=irfunc |- CU.Func=func
		if (Op.hasData() && IRFunc.hasData() && !Func.hasData()) {
			op_func("31", "56", "alu_and");
			op_func("31", "57", "alu_and");
			op_func("31", "120", "alu_andc");
			op_func("31", "121", "alu_andc");
			op_func("31", "952", "alu_nand");
			op_func("31", "953", "alu_nand");
			op_func("31", "248", "alu_nor");
			op_func("31", "249", "alu_nor");
			op_func("31", "888", "alu_or");
			op_func("31", "889", "alu_or");
			op_func("31", "824", "alu_orc");
			op_func("31", "825", "alu_orc");
			op_func("31", "632", "alu_xor");
			op_func("31", "633", "alu_xor");
			op_func("31", "568", "alu_eqv");
			op_func("31", "569", "alu_eqv");			
		 	
			op_func("31", "532", "alu_add");
			op_func("31", "533", "alu_add");			
			op_func("31", "1556", "alu_add");
			op_func("31", "1557", "alu_add");
			op_func("31", "20", "alu_add");
			op_func("31", "21", "alu_add");
			op_func("31", "1044", "alu_add");
			op_func("31", "1045", "alu_add");
			op_func("31", "276", "alu_adde");
			op_func("31", "277", "alu_adde");
			op_func("31", "1300", "alu_adde");
			op_func("31", "1301", "alu_adde");			
			op_func("31", "468", "alu_adde");
			op_func("31", "469", "alu_adde");
			op_func("31", "1492", "alu_adde");
			op_func("31", "1493", "alu_adde");
			op_func("31", "404", "alu_adde");
			op_func("31", "405", "alu_adde");
			op_func("31", "1428", "alu_adde");
			op_func("31", "1429", "alu_adde");
			
			op_func("31", "208", "alu_neg");
			op_func("31", "209", "alu_neg");
			op_func("31", "1232", "alu_neg");
			op_func("31", "1233", "alu_neg");
			
			op_func("31", "80", "alu_subf");
			op_func("31", "81", "alu_subf");
			op_func("31", "1104", "alu_subf");
			op_func("31", "1105", "alu_subf");
			op_func("31", "16", "alu_subf");
			op_func("31", "17", "alu_subf");
			op_func("31", "1040", "alu_subf");
			op_func("31", "1041", "alu_subf");
			op_func("31", "272", "alu_subfe");
			op_func("31", "273", "alu_subfe");
			op_func("31", "1296", "alu_subfe");
			op_func("31", "1297", "alu_subfe");
			op_func("31", "464", "alu_subfe");
			op_func("31", "465", "alu_subfe");
			op_func("31", "1488", "alu_subfe");
			op_func("31", "1489", "alu_subfe");
			op_func("31", "400", "alu_subfe");
			op_func("31", "401", "alu_subfe");
			op_func("31", "1424", "alu_subfe");
			op_func("31", "1425", "alu_subfe");
			
			op_func("31", "982", "mdu_div");
			op_func("31", "983", "mdu_div");
			op_func("31", "2006", "mdu_div");
			op_func("31", "2007", "mdu_div");
			op_func("31", "918", "mdu_divu");
			op_func("31", "919", "mdu_divu");
			op_func("31", "1942", "mdu_divu");
			op_func("31", "1943", "mdu_divu");			
			op_func("31", "150", "mdu_mul");
			op_func("31", "151", "mdu_mul");
			op_func("31", "22", "mdu_mulu");
			op_func("31", "23", "mdu_mulu");			
			op_func("31", "470", "mdu_mul");
			op_func("31", "471", "mdu_mul");
			op_func("31", "1494", "mdu_mul");
			op_func("31", "1495", "mdu_mul");
			
			op_func("31", "0", "cmpu_cmps");
			op_func("31", "64", "cmpu_cmpu");
			
			op_func("31", "48", "su_sl");
			op_func("31", "49", "su_sl");
			op_func("31", "1584", "su_sra");
			op_func("31", "1585", "su_sra");
			op_func("31", "1648", "su_sra");
			op_func("31", "1649", "su_sra");
			op_func("31", "1072", "su_sr");
			op_func("31", "1073", "su_sr");
			
			//1.12
			op_func("19", "514", "alu_and");
			op_func("19", "258", "alu_andc");
			op_func("19", "578", "alu_eqv");
			op_func("19", "450", "alu_nand");
			op_func("19", "66", "alu_nor");
			op_func("19", "898", "alu_or");
			op_func("19", "386", "alu_xor");
			
		}
		
		//CU.Op=op, CU.IRFunc=irfunc |- CU.Func=func, CU.MemDataSelFunc=mds_func
		if (Op.hasData() && IRFunc.hasData() && !Func.hasData() && !MemDataSelFunc.hasData()) {
			
			//1.9 & 1.10
			op_func_mds("31", "238", "alu_add", "mds_lbz");
			op_func_mds("31", "174", "alu_add", "mds_lbz");
			op_func_mds("31", "750", "alu_add", "mds_lha");
			op_func_mds("31", "686", "alu_add", "mds_lha");
			op_func_mds("31", "1580", "alu_add", "mds_lhbr");
			op_func_mds("31", "622", "alu_add", "mds_lhz");
			op_func_mds("31", "558", "alu_add", "mds_lhz");
			op_func_mds("31", "1068", "alu_add", "mds_lwbr");
			op_func_mds("31", "110", "alu_add", "mds_lwz");
			op_func_mds("31", "46", "alu_add", "mds_lwz");
			op_func_mds("31", "494", "alu_add", "mds_stb");
			op_func_mds("31", "430", "alu_add", "mds_stb");
			op_func_mds("31", "1836", "alu_add", "mds_sthbr");
			op_func_mds("31", "878", "alu_add", "mds_sth");
			op_func_mds("31", "814", "alu_add", "mds_sth");
			op_func_mds("31", "1324", "alu_add", "mds_stwbr");
			op_func_mds("31", "366", "alu_add", "mds_stw");
			op_func_mds("31", "302", "alu_add", "mds_stw");
		
		}
		
		//CU.Op=op |- CU.Func=func	
		if (Op.hasData() && !Func.hasData()) {
			
			op("28", "alu_and");
			op("29", "alu_and");
			op("24", "alu_or");
			op("25", "alu_or");
			op("26", "alu_xor");
			op("27", "alu_xor");
			op("14", "alu_add");			
			op("12", "alu_add");
			op("13", "alu_add");
			op("15", "alu_add");			
			op("8", "alu_subf");						
			op("7", "mdu_mul");		
			op("11", "cmpu_cmps");
			op("10", "cmpu_cmpu");	
			
		}
		
		//CU.Op=op |- CU.Func=func, CU.MemDataSelFunc=mds_func
		if (Op.hasData() && !Func.hasData() && !MemDataSelFunc.hasData()) {
		
			//load & store
			op_mds("34", "alu_add", "mds_lbz");
			op_mds("35", "alu_add", "mds_lbz");
			op_mds("42", "alu_add", "mds_lha");
			op_mds("43", "alu_add", "mds_lha");
			op_mds("40", "alu_add", "mds_lhz");
			op_mds("41", "alu_add", "mds_lhz");
			op_mds("32", "alu_add", "mds_lwz");
			op_mds("33", "alu_add", "mds_lwz");
			op_mds("38", "alu_add", "mds_stb");
			op_mds("39", "alu_add", "mds_stb");
			op_mds("44", "alu_add", "mds_sth");
			op_mds("45", "alu_add", "mds_sth");
			op_mds("36", "alu_add", "mds_stw");
			op_mds("37", "alu_add", "mds_stw");		
			
		}
		
		//CU.Op=op, CU.AA=aa, CU.LK=lk |- CU.Func=func		
		if (Op.hasData() && AA.hasData() && LK.hasData() && !Func.hasData()) {
			
			op_aa_lk("18", "0", "0", "alu_add");
			op_aa_lk("18", "0", "1", "alu_add");
			
		}			
		
		//CU.Op=op, CU.IRFunc2=irfunc2 |- CU.Func=func
		if (Op.hasData() && IRFunc2.hasData() && !Func.hasData()){
			op_b("17", "8", "alu_add");
		}
	}

	private void op_func(String str_op, String str_irfunc, String str_func) {
		
		//CU.Op=op, CU.IRFunc=irfunc |- CU.Func=func
		if (!Op.getData().nameIs(str_op) || !IRFunc.getData().nameIs(str_irfunc))
			return;
		
		Procedure a = Op.getPortData();
		Procedure b = IRFunc.getPortData();
		
		Data func = CPU.addData(str_func);
		
		Formula f = new PortDataFormula(Func, func);
		Justification j = new Justification("CU", a, b);
		Proof.add(f, j);
		
	}
	
	private void op_func_mds(String str_op, String str_irfunc, String str_func, String str_mds_func) {
		
		//CU.Op=op, CU.IRFunc=irfunc |- CU.Func=func, CU.MemDataSelFunc=mds_func
		
		if (!Op.getData().nameIs(str_op) || !IRFunc.getData().nameIs(str_irfunc))
			return;
		
		Procedure a = Op.getPortData();
		Procedure b = IRFunc.getPortData();
		
		Data func = CPU.addData(str_func);
		Data mds_func = CPU.addData(str_mds_func);
		
		Formula f = null;
		Justification j = new Justification("CU", a, b);
		
		f = new PortDataFormula(Func, func);
		Proof.add(f, j);
		
		f = new PortDataFormula(MemDataSelFunc, mds_func);
		Proof.add(f, j);
		
	}
	
	private void op(String str_op, String str_func) {

		//CU.Op=op |- CU.Func=func
		
		if (!Op.getData().nameIs(str_op))
			return;
		
		Procedure a = Op.getPortData();
				
		Data func = CPU.addData(str_func);
		
		Formula f = new PortDataFormula(Func, func);
		Justification j = new Justification("CU", a);
		Proof.add(f, j);
		
	}
	
	private void op_mds(String str_op, String str_func, String str_mds_func) {

		//CU.Op=op |- CU.Func=func, CU.MemDataSelFunc=mds_func
		
		if (!Op.getData().nameIs(str_op))
			return;
		
		Procedure a = Op.getPortData();
				
		Data func = CPU.addData(str_func);
		Data mds_func = CPU.addData(str_mds_func);
		
		Formula f = null;
		Justification j = new Justification("CU", a);
		
		f = new PortDataFormula(Func, func);
		Proof.add(f, j);
		
		f = new PortDataFormula(MemDataSelFunc, mds_func);
		Proof.add(f, j);
		
	}
	
	private void op_aa_lk(String str_op, String str_aa, String str_lk, String str_func) {
		
		//CU.Op=op, CU.AA=aa, CU.LK=lk |- CU.Func=func
		
		if (!Op.getData().nameIs(str_op) || !AA.getData().nameIs(str_aa)
				|| !LK.getData().nameIs(str_lk))
			return;
		
		Procedure a = Op.getPortData();
		Procedure b = AA.getPortData();
		Procedure c = LK.getPortData();
		
		Data func = CPU.addData(str_func);
		
		Formula f = new PortDataFormula(Func, func);
		Justification j = new Justification("CU", a, b, c);
		Proof.add(f, j);
		
	}
	
	private void op_b(String str_op, String str_irfunc, String str_func) {
		
		//CU.Op=op, CU.IRFunc2=irfunc2 |- CU.Func=func
		
		if (!Op.getData().nameIs(str_op) || !IRFunc2.getData().nameIs(str_irfunc))
			return;
		
		Procedure a = Op.getPortData();
		Procedure b = IRFunc2.getPortData();
		
		Data func = CPU.addData(str_func);
		
		Formula f = new PortDataFormula(Func, func);
		Justification j = new Justification("CU", a, b);
		Proof.add(f, j);
		
	}
}
//*/

/*PPC
public class CU extends DLU {
	private Reg reg;
	private DataPort Op;
	private DataPort IRFunc;
	private DataPort AA;
	private DataPort LK;
	private DataPort RC;
	private DataPort Func;
	private DataPort MemDataSelFunc;
	private DataPort Trap;
	private DataPort TrapAddr;
	private DataPort ctr_ok;
	private DataPort cond_ok;
	private DataPort CTRDec;
//	Cache、MMU
	private DataPort IMemHit;
	private DataPort ICacheHit;
	private DataPort DMemHit;
	private DataPort DCacheHit;
	private DataPort DCacheWriteBack;
		
	public CU() {
		this.name = "CU";		
		reg = addReg(this, name);
		Op = addDataPort(this, name + ".Op");
		IRFunc = addDataPort(this, name + ".IRFunc");
		AA = addDataPort(this, name + ".AA");
		LK = addDataPort(this, name + ".LK");
		RC = addDataPort(this, name + ".RC");
		Func = addDataPort(this, name + ".Func");
		Trap = addDataPort(this, name + ".Trap");
		TrapAddr = addDataPort(this, name + ".TrapAddr");
		ctr_ok = addDataPort(this, name + ".ctr_ok");
		cond_ok = addDataPort(this, name + ".cond_ok");
		CTRDec = addDataPort(this, name + ".CTRDec");
		
		MemDataSelFunc = addDataPort(this, name + ".MemDataSelFunc");
		IMemHit = addDataPort(this, name + ".IMemHit");
		ICacheHit = addDataPort(this, name + ".ICacheHit");
		DMemHit = addDataPort(this, name + ".DMemHit");
		DCacheHit = addDataPort(this, name + ".DCacheHit");
		DCacheWriteBack = addDataPort(this, name + ".DCacheWriteBack");		
	}
	
	public void applyTheorems() {
		_CU();
		_CU_TrapAddr();
		_CU_Hold();
	}
	
	private void _CU() {
						
		//CU.Op=op, CU.IRFunc=irfunc |- CU.Func=func
		if (Op.hasData() && IRFunc.hasData() && !Func.hasData()) {
		
			op_func("31", "56", "alu_and");
			op_func("31", "57", "alu_and");
			op_func("31", "120", "alu_andc");
			op_func("31", "121", "alu_andc");
			op_func("31", "952", "alu_nand");
			op_func("31", "953", "alu_nand");
			op_func("31", "248", "alu_nor");
			op_func("31", "249", "alu_nor");
			op_func("31", "888", "alu_or");
			op_func("31", "889", "alu_or");
			op_func("31", "824", "alu_orc");
			op_func("31", "825", "alu_orc");
			op_func("31", "632", "alu_xor");
			op_func("31", "633", "alu_xor");
			op_func("31", "568", "alu_eqv");
			op_func("31", "569", "alu_eqv");			
		 	
			op_func("31", "532", "alu_add");
			op_func("31", "533", "alu_add");			
			op_func("31", "1556", "alu_add");
			op_func("31", "1557", "alu_add");
			op_func("31", "20", "alu_add");
			op_func("31", "21", "alu_add");
			op_func("31", "1044", "alu_add");
			op_func("31", "1045", "alu_add");
			op_func("31", "276", "alu_adde");
			op_func("31", "277", "alu_adde");
			op_func("31", "1300", "alu_adde");
			op_func("31", "1301", "alu_adde");			
			op_func("31", "468", "alu_adde");
			op_func("31", "469", "alu_adde");
			op_func("31", "1492", "alu_adde");
			op_func("31", "1493", "alu_adde");
			op_func("31", "404", "alu_adde");
			op_func("31", "405", "alu_adde");
			op_func("31", "1428", "alu_adde");
			op_func("31", "1429", "alu_adde");
			
			op_func("31", "208", "alu_neg");
			op_func("31", "209", "alu_neg");
			op_func("31", "1232", "alu_neg");
			op_func("31", "1233", "alu_neg");
			
			op_func("31", "80", "alu_subf");
			op_func("31", "81", "alu_subf");
			op_func("31", "1104", "alu_subf");
			op_func("31", "1105", "alu_subf");
			op_func("31", "16", "alu_subf");
			op_func("31", "17", "alu_subf");
			op_func("31", "1040", "alu_subf");
			op_func("31", "1041", "alu_subf");
			op_func("31", "272", "alu_subfe");
			op_func("31", "273", "alu_subfe");
			op_func("31", "1296", "alu_subfe");
			op_func("31", "1297", "alu_subfe");
			op_func("31", "464", "alu_subfe");
			op_func("31", "465", "alu_subfe");
			op_func("31", "1488", "alu_subfe");
			op_func("31", "1489", "alu_subfe");
			op_func("31", "400", "alu_subfe");
			op_func("31", "401", "alu_subfe");
			op_func("31", "1424", "alu_subfe");
			op_func("31", "1425", "alu_subfe");
			
			op_func("31", "982", "mdu_div");
			op_func("31", "983", "mdu_div");
			op_func("31", "2006", "mdu_div");
			op_func("31", "2007", "mdu_div");
			op_func("31", "918", "mdu_divu");
			op_func("31", "919", "mdu_divu");
			op_func("31", "1942", "mdu_divu");
			op_func("31", "1943", "mdu_divu");			
			op_func("31", "150", "mdu_mul");
			op_func("31", "151", "mdu_mul");
			op_func("31", "22", "mdu_mulu");
			op_func("31", "23", "mdu_mulu");			
			op_func("31", "470", "mdu_mul");
			op_func("31", "471", "mdu_mul");
			op_func("31", "1494", "mdu_mul");
			op_func("31", "1495", "mdu_mul");
			
			op_func("31", "0", "cmpu_cmps");
			op_func("31", "64", "cmpu_cmpu");
			
			op_func("31", "48", "su_sl");
			op_func("31", "49", "su_sl");
			op_func("31", "1584", "su_sra");
			op_func("31", "1585", "su_sra");
			op_func("31", "1648", "su_sra");
			op_func("31", "1649", "su_sra");
			op_func("31", "1072", "su_sr");
			op_func("31", "1073", "su_sr");
			
			//1.12
			op_func("19", "514", "alu_and");
			op_func("19", "258", "alu_andc");
			op_func("19", "578", "alu_eqv");
			op_func("19", "450", "alu_nand");
			op_func("19", "66", "alu_nor");
			op_func("19", "898", "alu_or");
			op_func("19", "386", "alu_xor");
			
		}
		
		//CU.Op=op, CU.IRFunc=irfunc |- CU.Func=func, CU.MemDataSelFunc=mds_func
		if (Op.hasData() && IRFunc.hasData() && !Func.hasData() && !MemDataSelFunc.hasData()) {
			
			//1.9 & 1.10
			op_func_mds("31", "238", "alu_add", "mds_lbz");
			op_func_mds("31", "174", "alu_add", "mds_lbz");
			op_func_mds("31", "750", "alu_add", "mds_lha");
			op_func_mds("31", "686", "alu_add", "mds_lha");
			op_func_mds("31", "1580", "alu_add", "mds_lhbr");
			op_func_mds("31", "622", "alu_add", "mds_lhz");
			op_func_mds("31", "558", "alu_add", "mds_lhz");
			op_func_mds("31", "1068", "alu_add", "mds_lwbr");
			op_func_mds("31", "110", "alu_add", "mds_lwz");
			op_func_mds("31", "46", "alu_add", "mds_lwz");
			op_func_mds("31", "494", "alu_add", "mds_stb");
			op_func_mds("31", "430", "alu_add", "mds_stb");
			op_func_mds("31", "1836", "alu_add", "mds_sthbr");
			op_func_mds("31", "878", "alu_add", "mds_sth");
			op_func_mds("31", "814", "alu_add", "mds_sth");
			op_func_mds("31", "1324", "alu_add", "mds_stwbr");
			op_func_mds("31", "366", "alu_add", "mds_stw");
			op_func_mds("31", "302", "alu_add", "mds_stw");
		
		}
		
		//CU.Op=op |- CU.Func=func	
		if (Op.hasData() && !Func.hasData()) {
			
			op("28", "alu_and");
			op("29", "alu_and");
			op("24", "alu_or");
			op("25", "alu_or");
			op("26", "alu_xor");
			op("27", "alu_xor");
			op("14", "alu_add");			
			op("12", "alu_add");
			op("13", "alu_add");
			op("15", "alu_add");			
			op("8", "alu_subf");						
			op("7", "mdu_mul");		
			op("11", "cmpu_cmps");
			op("10", "cmpu_cmpu");
			op("20", "su_rotl");
			
		}
		
		//CU.Op=op |- CU.Func=func, CU.MemDataSelFunc=mds_func
		if (Op.hasData() && !Func.hasData() && !MemDataSelFunc.hasData()) {
		
			//load & store
			op_mds("34", "alu_add", "mds_lbz");
			op_mds("35", "alu_add", "mds_lbz");
			op_mds("42", "alu_add", "mds_lha");
			op_mds("43", "alu_add", "mds_lha");
			op_mds("40", "alu_add", "mds_lhz");
			op_mds("41", "alu_add", "mds_lhz");
			op_mds("32", "alu_add", "mds_lwz");
			op_mds("33", "alu_add", "mds_lwz");
			op_mds("38", "alu_add", "mds_stb");
			op_mds("39", "alu_add", "mds_stb");
			op_mds("44", "alu_add", "mds_sth");
			op_mds("45", "alu_add", "mds_sth");
			op_mds("36", "alu_add", "mds_stw");
			op_mds("37", "alu_add", "mds_stw");		
			
		}
		
		//CU.Op=op, CU.AA=aa, CU.LK=lk |- CU.Func=func		
		if (Op.hasData() && AA.hasData() && LK.hasData() && !Func.hasData()) {
			
			op_aa_lk("18", "0", "0", "alu_add");
			op_aa_lk("18", "0", "1", "alu_add");
			
		}
		
		//CU.Op=op, CU.AA=aa, CU.LK=lk |- CU.Func=func		
		if (Op.hasData() && LK.hasData() && !Func.hasData()) {
			
			op_lk("18", "0", "alu_add");
			op_lk("18", "1", "alu_add");
			op_lk("16", "0", "alu_add");
		}
		
	}

	private void op_func(String str_op, String str_irfunc, String str_func) {
		
		//CU.Op=op, CU.IRFunc=irfunc |- CU.Func=func
		
		if (!Op.getData().nameIs(str_op) || !IRFunc.getData().nameIs(str_irfunc))
			return;
		
		Procedure a = Op.getPortData();
		Procedure b = IRFunc.getPortData();
		
		Data func = CPU.addData(str_func);
		
		Formula f = new PortDataFormula(Func, func);
		Justification j = new Justification("CU", a, b);
		Proof.add(f, j);
		
	}
	
	private void op_func_mds(String str_op, String str_irfunc, String str_func, String str_mds_func) {
		
		//CU.Op=op, CU.IRFunc=irfunc |- CU.Func=func, CU.MemDataSelFunc=mds_func
		
		if (!Op.getData().nameIs(str_op) || !IRFunc.getData().nameIs(str_irfunc))
			return;
		
		Procedure a = Op.getPortData();
		Procedure b = IRFunc.getPortData();
		
		Data func = CPU.addData(str_func);
		Data mds_func = CPU.addData(str_mds_func);
		
		Formula f = null;
		Justification j = new Justification("CU", a, b);
		
		f = new PortDataFormula(Func, func);
		Proof.add(f, j);
		
		f = new PortDataFormula(MemDataSelFunc, mds_func);
		Proof.add(f, j);
		
	}
	
	private void op(String str_op, String str_func) {

		//CU.Op=op |- CU.Func=func
		
		if (!Op.getData().nameIs(str_op))
			return;
		
		Procedure a = Op.getPortData();
				
		Data func = CPU.addData(str_func);
		
		Formula f = new PortDataFormula(Func, func);
		Justification j = new Justification("CU", a);
		Proof.add(f, j);
		
	}
	
	private void op_mds(String str_op, String str_func, String str_mds_func) {

		//CU.Op=op |- CU.Func=func, CU.MemDataSelFunc=mds_func
		
		if (!Op.getData().nameIs(str_op))
			return;
		
		Procedure a = Op.getPortData();
				
		Data func = CPU.addData(str_func);
		Data mds_func = CPU.addData(str_mds_func);
		
		Formula f = null;
		Justification j = new Justification("CU", a);
		
		f = new PortDataFormula(Func, func);
		Proof.add(f, j);
		
		f = new PortDataFormula(MemDataSelFunc, mds_func);
		Proof.add(f, j);
		
	}
	
	private void op_aa_lk(String str_op, String str_aa, String str_lk, String str_func) {
		
		//CU.Op=op, CU.AA=aa, CU.LK=lk |- CU.Func=func
		
		if (!Op.getData().nameIs(str_op) || !AA.getData().nameIs(str_aa)
				|| !LK.getData().nameIs(str_lk))
			return;
		
		Procedure a = Op.getPortData();
		Procedure b = AA.getPortData();
		Procedure c = LK.getPortData();
		
		Data func = CPU.addData(str_func);
		
		Formula f = new PortDataFormula(Func, func);
		Justification j = new Justification("CU", a, b, c);
		Proof.add(f, j);
		
	}
	
	private void op_lk(String str_op, String str_lk, String str_func) {
		
		//CU.Op=op, CU.LK=lk |- CU.Func=func
		
		if (!Op.getData().nameIs(str_op) || !LK.getData().nameIs(str_lk)){
			return;
		}
		Procedure pOp = Op.getPortData();
		Procedure pLK = LK.getPortData();
		Data func = CPU.addData(str_func);
		Formula f = new PortDataFormula(Func, func);
		Justification j = new Justification("CU", pOp, pLK);
		Proof.add(f, j);
	}
	
	private void _CU_TrapAddr() {
//		CU-TrapAddr
//		_CU[TrapAddr]=tAddr |- CTR.TrapAddr=tAddr
		
		if (!reg.hasLastContent() || !Trap.hasData() || TrapAddr.hasData()){
			return;
		}
		
		for (Procedure a : reg.getLastContentList()) {
			if (!a.getAddr().nameIs("TrapAddr")){
				continue;
			}				
			Data dOut = a.getData();
			
			Formula f = new PortDataFormula(TrapAddr, dOut);
			Justification j = new Justification("CU-TrapAddr", a);
			Proof.add(f, j);
			break;
		}
	}
	private void _CU_Hold() {
//		CU-Hold
//		_CU[TrapAddr]=data |- CU[TrapAddr]=data
		
		if (!reg.hasLastContent()){
			return;
		}
		for (Procedure a : reg.getLastContentList()) {
			Data addr = a.getAddr();
			//检查CRRegs[addr]是否有数据
			boolean flag = false;
			for (Procedure pd : reg.getCurContentList()){
				if (pd.getAddr().nameIs(addr.getName())){
					flag = true;
					break;
				}
			}
			//CRRegs[addr]有数据就不保持这个地址的数据了
			if (flag == true){
				continue;
			}
			Data data = a.getData();
			
			Formula f = new RegContentFormula(reg, addr, data);
			Justification j = new Justification("CU-Hold", a);
			Proof.add(f, j);
		}
	}
	
}
*/