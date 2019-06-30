package proving_model;
import java.util.ArrayList;

//¿Ì”…
public class Justification {
	
	private String name;
	private NumberedFormula premise; 
	private ArrayList<Procedure> conditionList = new ArrayList<Procedure>();	
	private String str = null;
	
	public Justification(NumberedFormula nf){
		this.name = "Premise";
		this.premise = nf;
	}
	
	public Justification(String theoremName, ArrayList<Procedure> pds){
		this.name = theoremName;
		this.conditionList = pds;
	}
	
	public Justification(String theoremName){
		this.name = theoremName;
	}
	
	public Justification(String theoremName, Procedure pd){
		this.name = theoremName;
		this.conditionList.add(pd);
	}
	
	public Justification(String theoremName, Procedure pd1, Procedure pd2){
		this.name = theoremName;
		this.conditionList.add(pd1);
		this.conditionList.add(pd2);
	}
	
	public Justification(String theoremName, Procedure pd1, Procedure pd2, Procedure pd3){
		this.name = theoremName;
		this.conditionList.add(pd1);
		this.conditionList.add(pd2);
		this.conditionList.add(pd3);
	}
	
	public Justification(String theoremName, Procedure pd1, Procedure pd2, Procedure pd3, Procedure pd4){
		this.name = theoremName;
		this.conditionList.add(pd1);
		this.conditionList.add(pd2);
		this.conditionList.add(pd3);
		this.conditionList.add(pd4);
	}
	
	public String getStr() {
		assemble();
		return str;
	}
	
	void assemble(){
		str = name + "(";
		if(name.equals("Premise"))
			str = str + premise.getNumber() + ")";
		else {
			for (Procedure condition : conditionList){
				str = str + "," + condition.getNumber();
			}
			if (conditionList.size() > 0){
				str = str.replaceFirst(",", "") + ")";
			}
			else{
				str = str + ")";
			}
		}
	}

	public ArrayList<Procedure> getConditionList() {
		return conditionList;
	}
}
