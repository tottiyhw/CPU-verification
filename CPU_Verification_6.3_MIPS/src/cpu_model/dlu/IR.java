package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.CtrlPort;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.RegContentFormula;
//MIPS
///*
public class IR extends DLU {
	
	private Reg reg;
	private DataPort In;
	private DataPort Out;
	private DataPort Out31_26;
	private DataPort Out25_0;
	private DataPort Out25_6;
	private DataPort Out25_21;
	private DataPort Out20_6;
	private DataPort Out20_16;
	private DataPort Out20_18;
	private DataPort Out16;
	private DataPort Out17;
	private DataPort Out15_0;
	private DataPort Out15_6;
	private DataPort Out15_11;
	private DataPort Out10_0;
	private DataPort Out10_3;
	private DataPort Out10_6;
	private DataPort Out5_0; 
	private DataPort Out2_0; 
	private CtrlPort Ctrl;
	
	static private String instructionForm;	
	private Justification j = null;	
		
	public IR() {
		
		this.name = "IR";
		
		reg = addReg(this, name);
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		Out31_26 = addDataPort(this, name + ".Out31_26");
		Out25_0 = addDataPort(this, name + ".Out25_0");
		Out25_6 = addDataPort(this, name + ".Out25_6");
		Out25_21 = addDataPort(this, name + ".Out25_21");
		Out20_18 = addDataPort(this, name + ".Out20_18");
		Out20_16 = addDataPort(this, name + ".Out20_16");
		Out20_6 = addDataPort(this, name + ".Out20_6");
		Out17 = addDataPort(this, name + ".Out17");
		Out16 = addDataPort(this, name + ".Out16");
		Out15_11 = addDataPort(this, name + ".Out15_11");
		Out15_0 = addDataPort(this, name + ".Out15_0");
		Out15_6 = addDataPort(this, name + ".Out15_6");
		Out10_6 = addDataPort(this, name + ".Out10_6");
		Out10_3 = addDataPort(this, name + ".Out10_3");
		Out10_0 = addDataPort(this, name + ".Out10_0");
		Out5_0 = addDataPort(this, name + ".Out5_0");
		Out2_0 = addDataPort(this, name + ".Out2_0");
		Ctrl = addCtrlPort(this, "Ctrl" + name,  "·");
		
	}		
	
	public static void setInstructionForm(String s) {
		instructionForm = s;		
	}
	
	public void applyTheorems() {
		
		_IR_Out();
		_IR_Hold();
		_IR_Write();
		
	}
		
	private void _IR_Hold() {
		
//		IR-Hold	
//		_[IR]=data, CtrlIR=0 |- [IR]=data
		
		if (!reg.hasLastContent() || !Ctrl.notActive() || reg.hasCurContent()){
			return;
		}
		
		Procedure a = reg.getLastContent();
		Procedure b = Ctrl.getCtrlSignal();
		
		Data data = a.getData();
		
		Formula f = new RegContentFormula(reg, data);
		Justification j = new Justification("IR-Hold", a, b);
		Proof.add(f, j);
	}
	
	private void _IR_Write() {
		//IR-Write	IR.In=inst, CtrlIR=1 |- [IR]=inst	
		
		if (!In.hasData() || !Ctrl.isActive() || reg.hasCurContent()){
			return;
		}
		Procedure a = In.getPortData();
		Procedure b = Ctrl.getCtrlSignal();
		
		Data data = a.getData();
		
		Formula f = new RegContentFormula(reg, data);
		Justification j = new Justification("IR-Write", a, b);
		Proof.add(f, j);		
	}
	
	private void _IR_Out() {
		//IR-Out
		//_[IR]={..} |- IR.Outxx=.., IR.Outxx==.., ...
		
		//寄存器里没东西
		if (!reg.hasLastContent()){
			return;
		}
		Procedure a = reg.getLastContent();
		j = new Justification("IR-Out", a);

		//取得指令码，去掉大括号，拆分成字段，装到fields中
		String str_inst = a.getData().getName();
		generateIROut(Out, str_inst);
		str_inst = str_inst.substring(1, str_inst.length()-1);
		String[] fields = str_inst.split(",");
		
		// R-Form
		// _[IR]={op, rs, rt, rd, shamt, func} |- 
		// IR[31:26]=op, IR[25:21]=rs, IR[20:16]=rt, IR[15:11]=rd, IR[5:0]=func
		if (instructionForm.equals("R-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_21, fields[1]);
			generateIROut(Out20_16, fields[2]);
			generateIROut(Out15_11, fields[3]);
			generateIROut(Out10_6, fields[4]);
			generateIROut(Out5_0, fields[5]);
		}
		// I-Form
		// _[IR]={op, rs, rt, imm} |- 
		// IR[31:26]=op, IR[25:21]=rs, IR[20:16]=rt, IR[15:0]=imm 
		else if (instructionForm.equals("I-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_21, fields[1]);
			generateIROut(Out20_16, fields[2]);
			generateIROut(Out15_0, fields[3]);
		}
		// J-Form
		// _[IR]={op, imm} |- 
		// IR[31:26]=op, IR[25:0]=imm
		else if (instructionForm.equals("J-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_0, fields[1]);
		}
		// B-Form
		// _[IR]={op, func1, cc, nd, tf, offset} |- 
		// IR[31:26]=op, IR[25:21]=func1, IR[20:18]=cc, IR[17]=nd, IR[16]=tf, IR[15:0]=offset
		else if (instructionForm.equals("B-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_21, fields[1]);
			generateIROut(Out20_18, fields[2]);
			generateIROut(Out17, fields[3]);
			generateIROut(Out16, fields[4]);
			generateIROut(Out15_0, fields[5]);
		}
		// M0-Form
		// _[IR]={cop0, func, rt, rd, 0, sel} |- 
		// IR[31:26]=cop0, IR[25:21]=func, IR[20:16]=rt, IR[15:11]=rd, IR[2:0]=sel
		else if (instructionForm.equals("M0-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_21, fields[1]);
			generateIROut(Out20_16, fields[2]);
			generateIROut(Out15_11, fields[3]);
			generateIROut(Out10_3, fields[4]);
			generateIROut(Out2_0, fields[5]);
		}
		// M1-Form
		// _[IR]={cop0, func, rt, rd, 0} |- 
		// IR[31:26]=cop0, IR[25:21]=func, IR[20:16]=rt, IR[15:11]=rd
		else if (instructionForm.equals("M1-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_21, fields[1]);
			generateIROut(Out20_16, fields[2]);
			generateIROut(Out15_11, fields[3]);
			generateIROut(Out10_0, fields[4]);
		}
		// M2-Form
		// _[IR]={op, rs, cc, 0, tf, rd, 0, func} |- 
		// IR[31:26]=op, IR[25:21]=rs, IR[20:18]=cc, IR[16]=tf, IR[15:11]=rd, IR[5:0]=func
		else if (instructionForm.equals("M2-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_21, fields[1]);
			generateIROut(Out20_18, fields[2]);
			generateIROut(Out17, fields[3]);
			generateIROut(Out16, fields[4]);
			generateIROut(Out15_11, fields[5]);
			generateIROut(Out10_6, fields[6]);
			generateIROut(Out5_0, fields[7]);
		}
		// M3-Form
		// _[IR]={op, rs, 0, func} |- 
		// IR[31:26]=op, IR[25:21]=rs, IR[5:0]=func
		else if (instructionForm.equals("M3-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_21, fields[1]);
			generateIROut(Out20_6, fields[2]);
			generateIROut(Out5_0, fields[3]);
		}
		// SC-Form
		// _[IR]={op, code, func} |- 
		// IR[31:26]=op, IR[25:6]=code, IR[5:0]=func
		else if (instructionForm.equals("SC-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_6, fields[1]);
			generateIROut(Out5_0, fields[2]);
		}
		// T-Form
		// _[IR]={op, rs, rt, code, func} |- 
		// IR[31:26]=op, IR[25:21]=rs, IR[20:16]=rt, IR[15:6]=code, IR[5:0]=func
		else if (instructionForm.equals("T-Form")){
			generateIROut(Out31_26, fields[0]);
			generateIROut(Out25_21, fields[1]);
			generateIROut(Out20_16, fields[2]);
			generateIROut(Out15_6, fields[3]);
			generateIROut(Out5_0, fields[4]);
		}
	}
		
	private void generateIROut(DataPort port, String s) {
		if(port.hasData()){
			return;
		}
		Data data =  CPU.addData(s);	
		Formula f = new PortDataFormula(port, data);		
		Proof.add(f, j);
	}

}
//*/
/*
//PPC
public class IR extends DLU {
	
	private Reg reg;
	private DataPort In;
	private DataPort Out0_5;
	private DataPort Out6_8;
	private DataPort Out6_10;
	private DataPort Out6_29;
	private DataPort Out11_15;
	private DataPort Out16_20;
	private DataPort Out16_29;
	private DataPort Out16_31;
	private DataPort Out21;
	private DataPort Out21_30;
	private DataPort Out21_31;
	private DataPort Out30;
	private DataPort Out31;
	private CtrlPort Ctrl;
	
	static private String instructionForm;	
	private Justification j = null;	
		
	public IR() {
		
		this.name = "IR";
		
		reg = addReg(this, name);
		In = addDataPort(this, name + ".In");
		Out0_5 = addDataPort(this, name + ".Out0_5");
		Out6_8 = addDataPort(this, name + ".Out6_8");
		Out6_10 = addDataPort(this, name + ".Out6_10");
		Out6_29 = addDataPort(this, name + ".Out6_29");
		Out11_15 = addDataPort(this, name + ".Out11_15");
		Out16_20 = addDataPort(this, name + ".Out16_20");
		Out16_29 = addDataPort(this, name + ".Out16_29");
		Out16_31 = addDataPort(this, name + ".Out16_31");
		Out21 = addDataPort(this, name + ".Out21");
		Out21_30 = addDataPort(this, name + ".Out21_30");
		Out21_31 = addDataPort(this, name + ".Out21_31");
		Out30 = addDataPort(this, name + ".Out30");
		Out31 = addDataPort(this, name + ".Out31");
		Ctrl = addCtrlPort(this, "Ctrl" + name,  "·");
		
	}		
	
	public static void setInstructionForm(String s) {
		instructionForm = s;		
	}
	
	public void applyTheorems() {
		
		_IR_Out();
		_IR_Hold();
		_IR_Write();
		
	}
		
	private void _IR_Hold() {
		
		//IR-Hold	_[IR]=data, CtrlIR=0 |- [IR]=data
		
		if (!reg.hasLastContent() || !Ctrl.notActive() || reg.hasCurContent())
			return;
		
		Procedure a = reg.getLastContent();
		Procedure b = Ctrl.getCtrlSignal();
		
		Data data = a.getData();
		
		Formula f = new RegContentFormula(reg, data);
		Justification j = new Justification("IR-Hold", a, b);
		Proof.add(f, j);
		
	}
	
	private void _IR_Write() {
		
		//IR-Write	IR.In=inst, CtrlIR=1 |- [IR]=inst	
		
		if (!In.hasData() || !Ctrl.isActive() || reg.hasCurContent())
			return;
		
		Procedure a = In.getPortData();
		Procedure b = Ctrl.getCtrlSignal();
		
		Data data = a.getData();
		
		Formula f = new RegContentFormula(reg, data);
		Justification j = new Justification("IR-Write", a, b);
		Proof.add(f, j);	
				
	}
	
	private void _IR_Out() {
		
		//IR-Out
		//_[IR]={..} |- IR.Outxx=.., IR.Outxx==.., ...
		
		//寄存器里没东西，还译个毛码啊！
		if (!reg.hasLastContent())
			return;
		
		Procedure a = reg.getLastContent();
		j = new Justification("IR-Out", a);
		
		//取得指令码，去掉大括号，拆分成字段，装到fields中
		String str_inst = a.getData().getName();
		str_inst = str_inst.substring(1, str_inst.length()-1);
		String[] fields = str_inst.split(",");
		
		//X-Form
		//_[IR]={op,rD,rA,rB,func} |- 
		//IR[0:5]=op, IR[6:10]=rD, IR[11:15]=rA, IR[16:20]=rB, IR[21:31]=func
		if(instructionForm.equals("X-Form")) {
			generateIROut(Out0_5, fields[0]);
			generateIROut(Out6_10, fields[1]);
			generateIROut(Out11_15, fields[2]);
			generateIROut(Out16_20, fields[3]);			
			generateIROut(Out21_31, fields[4]);
		}
		
		//D-Form
		//_[IR]={op,rD,rA,imm} |- 
		//IR[0:5]=op, IR[6:10]=rD, IR[11:15]=rA, IR[16:31]=imm
		else if(instructionForm.equals("D-Form")) {
			generateIROut(Out0_5, fields[0]);
			generateIROut(Out6_10, fields[1]);
			generateIROut(Out11_15, fields[2]);
			generateIROut(Out16_31, fields[3]);
		}
		
		//DC-Form
		//_[IR]={op,crfD,0,rA,imm} |- 
		//IR[0:5]=op, IR[6:8]=crfD, IR[11:15]=rA, IR[16:31]=imm
		else if(instructionForm.equals("DC-Form")) {
			generateIROut(Out0_5, fields[0]);
			generateIROut(Out6_8, fields[1]);
			generateIROut(Out11_15, fields[3]);
			generateIROut(Out16_31, fields[4]);
		}
		
		//I-Form
		//_[IR]={op,LI,AA,LK} |- 
		//IR[0:5]=op, IR[6:29]=LI, IR[30]=AA, IR[31]=LK
		else if(instructionForm.equals("I-Form")) {
			generateIROut(Out0_5, fields[0]);
			generateIROut(Out6_29, fields[1]);
			generateIROut(Out30, fields[2]);
			generateIROut(Out31, fields[3]);
		}
		
		//B-Form
		//_[IR]={op,BO,BI,BD,AA,LK} |- 
		//IR[0:5]=op, IR[6:10]=BO, IR[11:15]=BI, IR[16:29]=BD, IR[30]=AA, IR[31]=LK
		else if(instructionForm.equals("B-Form")) {
			generateIROut(Out0_5, fields[0]);
			generateIROut(Out6_10, fields[1]);
			generateIROut(Out11_15, fields[2]);
			generateIROut(Out16_29, fields[3]);
			generateIROut(Out30, fields[4]);
			generateIROut(Out31, fields[5]);
		}
		
		//XC-Form
		//_[IR]={op,crfD,0,rA,rB,func} |- 
		//IR[0:5]=op, IR[6:8]=crfD, IR[11:15]=rA, IR[16:20]=rB, IR[21:30]=func
		else if(instructionForm.equals("XC-Form")) {
			generateIROut(Out0_5, fields[0]);
			generateIROut(Out6_8, fields[1]);
			generateIROut(Out11_15, fields[3]);
			generateIROut(Out16_20, fields[4]);
			generateIROut(Out21_31, fields[5]);
		}
		
	}
		
	private void generateIROut(DataPort port, String s) {
		if(port.hasData())
			return;
		Data data =  CPU.addData(s);	
		Formula f = new PortDataFormula(port, data);		
		Proof.add(f, j);
	}

}
*/
