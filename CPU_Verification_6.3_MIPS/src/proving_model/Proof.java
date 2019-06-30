package proving_model;

import java.util.ArrayList;

import cpu_model.cpu.CPU;

//证明序列
public class Proof {

	private static int curStage = -1;
	private static ArrayList<ArrayList<Procedure>> stageList = null;
	private static int size = 0;
	
	public static void clear() {
		stageList = new ArrayList<ArrayList<Procedure>>();
		for(int i = 0; i < CPU.StageSum + 2; i++){
			stageList.add(new ArrayList<Procedure>());
		}
		size = 0;
	}
	
	public static void add(Formula f, Justification j) {
		add(new Procedure(f, j));
	}
	
	//添加一个步骤，并运行CPU
	public static void add(Procedure pd) {
		onlyAdd(pd);		
		CPU.addProcedure(pd);
	}

	//只是添加一个步骤
	public static void onlyAdd(Procedure pd) {
		
		//debug
		//System.out.println(pd.getFormula().getStr());
		
		stageList.get(curStage).add(pd);		
	}
		
	public static Procedure get(int stage, int num) {
		return stageList.get(stage).get(num);
	}
		
	public static int size(int stage) {
		return stageList.get(stage).size();
	}
		
	//编号
	public static void number() {
		int num = 0;
		for (int i = 0; i < CPU.StageSum + 1; i++)
			for (Procedure pd : stageList.get(i))
				if (pd.isUsed())
					pd.setNumber(num++);
		size = num;
	}

	public static void setCurStage(int stage) {
		
		//debug
		//System.out.println("\nstage: " + stage + "\n");
		
		curStage = stage;		
	}

	public static Procedure lookFor(int stage, Formula f) {
		for(Procedure pd : stageList.get(stage))
			if(pd.getFormula().equals(f))
				return pd;
		return null;
	}

	public static void mark(Procedure pd) {
		pd.setUsed();
		ArrayList<Procedure> conditionList = pd.getConditionList();
		for(Procedure condition : conditionList)
			mark(condition);
	}
	
	public static int getSize() {
		return size;
	}
	
	
}
