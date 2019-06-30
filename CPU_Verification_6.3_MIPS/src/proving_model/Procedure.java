package proving_model;

import java.util.ArrayList;

import cpu_model.element.Data;
import cpu_model.element.DataPort;

//Ö¤Ã÷²½Öè
public class Procedure{ 
	
	private static String PREFIX = "S";
	private int number;
	private Formula formula;
	private Justification jst;
	private boolean used = false;
	private String str = null;
	
	public Procedure(Formula f, Justification e){
		this.formula = f;
		this.jst = e;
	}
	
	public void assemble() {
		str = String.format("%-60s%s", getNumber() + "= " + formula.getStr(), jst.getStr());
	}
	
	public Formula getFormula() {
		return formula;
	}
	
	public Data getData() {
		if (formula instanceof PortDataFormula)
			return ((PortDataFormula) formula).getData();
		else if (formula instanceof RegContentFormula)
			return ((RegContentFormula) formula).getData();
		return null;
	}
	
	public DataPort getPort1() {
		if (formula instanceof PathFormula)
			return ((PathFormula) formula).getPort1();
		return null;
	}
	
	public DataPort getPort2() {
		if (formula instanceof PathFormula)
			return ((PathFormula) formula).getPort2();
		return null;
	}
	
	public boolean signalIsActive() {
		if (formula instanceof CtrlSignalFormula)
			return ((CtrlSignalFormula) formula).signalIsActive();
		return false;
	}
	
	public Data getAddr() {
		if (formula instanceof RegContentFormula)
			return ((RegContentFormula) formula).getAddr();
		return null;
	}
	
	public String getStr() {
		assemble();
		return str;
	}	

	public String getNumber() {
		return PREFIX + number;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	public void setNumber(int num) {
		this.number = num;
	}

	public void setUsed() {
		used = true;		
	}

	public ArrayList<Procedure> getConditionList() {
		return jst.getConditionList();
	}
	
}
