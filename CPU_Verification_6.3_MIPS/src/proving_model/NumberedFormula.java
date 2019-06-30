package proving_model;

//带序号的公式
public class NumberedFormula {
	
	private static String PREFIX = "F";
	private int number;
	private Formula formula;
	private String str = null;
	
	public NumberedFormula(Formula f) {
		this.formula = f;
	}

	public Formula getFormula() {
		return formula;
	}
	
	public void setNumber(int num) {
		this.number = num;
	}
	
	public String getNumber() {
		return PREFIX + number;
	}
	
	private void assemble() {
		str = getNumber() + "= " + formula.getStr();
	}
	
	public String getStr() {
		if(str==null)
			assemble();
		return str;
	}	
	
}
