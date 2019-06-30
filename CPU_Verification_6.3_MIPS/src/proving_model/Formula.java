package proving_model;

//��ʽ
public abstract class Formula {

	protected String str = null;
	
	public boolean isPathFormula(){
		return this instanceof PathFormula;
	}
	
	public boolean isCtrlSignalFormula(){
		return this instanceof CtrlSignalFormula;
	}
	
	public boolean isPortDataFormula(){
		return this instanceof PortDataFormula;
	}
	
	public boolean isRegContentFormula(){
		return this instanceof RegContentFormula;
	}
		
	abstract void assemble();			
			
	abstract boolean equals(Formula f);

	public String getStr() {
		if(str==null)
			assemble();
		return str;
	}
	
}
