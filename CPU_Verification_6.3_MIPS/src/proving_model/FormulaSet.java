package proving_model;

import java.util.ArrayList;

import cpu_model.cpu.CPU;


//¹«Ê½¼¯
public class FormulaSet {

	private static ArrayList<ArrayList<NumberedFormula>> partList = null;
		
	private static int size = 0;
	
	public static void clear() {
		partList = new ArrayList<ArrayList<NumberedFormula>>();
		for(int i = 0; i < CPU.StageSum + 2; i++)
			partList.add(new ArrayList<NumberedFormula>());	
		size = 0;
	}
	
	public static int size(int part) {
		return partList.get(part).size();
	}
	
	public static void add(int part, Formula f) {
		NumberedFormula nf = new NumberedFormula(f);
		partList.get(part).add(nf);
	}
	
	public static NumberedFormula get(int part, int num) {
		return partList.get(part).get(num);
	}	
	
	public static Procedure getAsProcedure(int part, int num) {
		NumberedFormula nf = get(part,num);
		Formula f = nf.getFormula();
		Justification jf = new Justification(nf);
		Procedure pd = new Procedure(f,jf);
		return pd;
	}
	
	public static void number() {
		int num = 0;
		for(ArrayList<NumberedFormula> nfs : partList)
			for(NumberedFormula nf : nfs)
				nf.setNumber(num++);
		size = num;
	}
	
	public static int getSize() {
		return size;
	}
	
}
