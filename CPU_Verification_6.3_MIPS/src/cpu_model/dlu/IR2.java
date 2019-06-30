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

public class IR2 extends DLU {
	
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
		
	public IR2() {
		
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
