package cpu_model.dlu;

import java.util.ArrayList;

import cpu_model.cpu.CPU;
import cpu_model.element.*;
import proving_model.Axiom;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;
import proving_model.RegContentFormula;



//简单寄存器
//只包括[R]、R.In、R.Out、CtrlR四个元素

public class Register extends DLU {
	private DataPort In;
	private DataPort Out;
	private DataPort Out1_0;
	private DataPort Out4_0;
	private DataPort Out26_31;
	private DataPort Out30_31;
	private CtrlPort Ctrl;
		
	public Register(String name) {
		
		this.name = name;		
		
		reg = addReg(this, name);
		In = addDataPort(this, name + ".In");
		Out = addDataPort(this, name + ".Out");
		Out1_0 = addDataPort(this, name + ".Out1_0");
		Out4_0 = addDataPort(this, name + ".Out4_0");
		Out26_31 = addDataPort(this, name + ".Out26_31");
		Out30_31 = addDataPort(this, name + ".Out30_31");
		Ctrl = addCtrlPort(this, "Ctrl" + name, "·");
	}
}
