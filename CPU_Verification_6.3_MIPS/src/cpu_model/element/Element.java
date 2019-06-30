package cpu_model.element;

import cpu_model.dlu.DLU;

//元素
public abstract class Element {
	
	//所属DLU、项名称
	protected DLU dlu;
	protected String name;	
	
	public Element(DLU dlu, String name) {
		this.dlu = dlu;
		this.name = name;
	}
	
	public abstract void clear();
		
	public boolean isDataPort(){
		return this instanceof DataPort;
	}	
	
	public boolean isCtrlPort(){
		return this instanceof CtrlPort;
	}
	
	public boolean isReg(){
		return this instanceof Reg;
	}
	
	public DLU getDLU() {
		return dlu;
	}
	
	public boolean dluNameIs(String s) {
		return this.dlu.getName().equals(s);			
	}
		
	public String getName(){
		return name;
	}

	public boolean nameIs(String s) {		
		return this.name.equals(s);			
	}
	
}
