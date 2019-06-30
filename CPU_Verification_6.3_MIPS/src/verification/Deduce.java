package verification;



import proving_model.FormulaSet;
import proving_model.Procedure;
import proving_model.Proof;
import cpu_model.cpu.CPU;

public class Deduce {

	static void start(){
		
		Proof.clear();
		
//		/IF -> IF -> IMMU -> ID -> EX -> MEM -> DMMU1 -> DMMU2 -> WB -> WB/
		for (int stage = 0; stage < CPU.StageSum + 2; stage++) {
			
			Proof.setCurStage(stage);

			//debug
			//System.out.println("\n" + stage);
			
			//copy formulas in FormulaSet[PRE] to Proof[/IF]
			if (stage == 0)	{		
				for (int num = 0; num < FormulaSet.size(0); num++) {
					Procedure pd = FormulaSet.getAsProcedure(0, num);
//					System.out.println(pd.getStr());
					Proof.onlyAdd(pd);
				}
			}
			//copy REG_STATE-type procedures in Proof[WB] to Proof[WB/]
			else if (stage == CPU.StageSum + 1)	{			
				for (int num = 0; num < Proof.size(CPU.StageSum); num++) {
					Procedure pd = Proof.get(CPU.StageSum, num);
					if (pd.getFormula().isRegContentFormula())
						Proof.onlyAdd(pd);
				}
			}			
			//stage 1~stageSum
			else {
				
				CPU.clear();
				
				//add REG_STATE-type procedures in last stage of Proof to CPU model
				for (int num = 0; num < Proof.size(stage - 1); num++) {
					Procedure pd = Proof.get(stage - 1, num);
					if (pd.getFormula().isRegContentFormula()){
//						System.out.println(pd.getData().getName());
//						System.out.println(pd.getStr());
						CPU.addLastContent(pd);
//						System.out.println(stage + "," + CPU.getReg("IMem").getLastContentList().size());
					}
				}				
			
				//add FormulaSet[IF,ID,EX,MEM,WB] to Proof[IF,ID,EX,MEM,WB]
				for (int num = 0; num < FormulaSet.size(stage); num++) {
					Procedure pd = FormulaSet.getAsProcedure(stage, num);
					Proof.add(pd);
				}
			}
		//debug
		//System.out.println("\npost");
		}
	}		
}
