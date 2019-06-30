package verification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import cpu_model.cpu.CPU;
import proving_model.Conditions;
import proving_model.Formula;
import proving_model.FormulaSet;
import proving_model.NumberedFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class CheckAndOutput {

	private static boolean result = false;
	private static String filepath = null;
	private static String dirpath = null;
		
	public static void start(String s) throws Exception {
		filepath = s;
		dirpath = s.substring(0, s.lastIndexOf('\\'));
		File dir = new File(dirpath);
		if (!dir.exists() && !dir.isDirectory()){      
		    dir.mkdir();    
		}
		
		printFullProof();
		check();		
		printFormulaSet();
		printProof();
		printResult();
		
	}
	
	public static void completeConds(){
		boolean findAll = false;
		int no = 0;
		for (int i = 0; !findAll && i < CPU.StageSum + 2; i++) {
			for (int num = 0; !findAll && num < Proof.size(i); num++) {
				Procedure pd = Proof.get(i,num);
				String fStr = pd.getFormula().getStr();
				if (!fStr.contains("=>")){
					String left = fStr.split("=")[0];
					if (Conditions.indexOf(left) != -1){
						no = Conditions.indexOf(left);
						if (Conditions.getConds().get(no).getOriginal().equals("")){
							Conditions.getConds().get(no).setOriginal(fStr.split("=")[1]);
							no = Conditions.incReadyNum();
							if (no >= Conditions.getConds().size()){
								findAll = true;
							}
						}
					}
				}
			}
		}
//		for (no = 0; no < Conditions.getConds().size(); no++){
//			System.out.println(Conditions.getConds().get(no).getPort() + "=" + Conditions.getConds().get(no).getOriginal());
//		}
	}
	
	public static String getResult() {
		return (result ? "correctness" : "incorrectness");
	}
	
	private static void check() {
		
		//检查FormulaSet[POST]中每一条公式是否都在Proof[WB/]中
		//如果某条公式包含，则把这个公式的所有直接或间接条件打上“已引用”的记号
		//如果所有公式都包含，则验证正确
		
		result = true;
		for (int i = 0; i < FormulaSet.size(CPU.StageSum + 1); i++) {
			NumberedFormula nf = FormulaSet.get(CPU.StageSum + 1, i);
			Formula f = nf.getFormula();
			Procedure pd = Proof.lookFor(CPU.StageSum + 1, f);
			if (pd != null){
				Proof.mark(pd);
			}
			else{
				result = false;
			}
		}
		//将所有带有“已引用”标记的步骤编号
		Proof.number();
		
	}	
		
	static void printFormulaSet() throws Exception {
		
		String s = filepath + " FormulaSet.txt";
		File file = new File(s);
		BufferedWriter bw = new BufferedWriter(new PrintWriter(file));
		
		//print formula set		
		bw.append("\r\n---------------------------------- Formula Set ----------------------------------\r\n\r\n");
		String[] a = CPU.StageName;
		for (int part = 0; part < CPU.StageSum + 2; part++) {
			bw.append(a[part]);
			for (int num = 0; num < FormulaSet.size(part); num++){
				bw.append("\t" + FormulaSet.get(part,num).getStr() + "\r\n");
			}
			bw.append("\r\n");
		}
		
		bw.close();

	}	
	
	private static void printFullProof() throws Exception {
		
		String s = filepath + " FullProof.txt";
		File file = new File(s);
		BufferedWriter bw = new BufferedWriter(new PrintWriter(file));
			
		//print full proof
		bw.append("\r\n----------------------------------- FullProof -----------------------------------\r\n\r\n");
		String[] b = CPU.StageName;
		for (int i = 0; i < CPU.StageSum + 2; i++) {
			bw.append(b[i]);
			for (int num = 0; num < Proof.size(i); num++) {
				Procedure pd = Proof.get(i,num);
//				System.out.println("*" + pd.getFormula().getStr());
				bw.append("\t" + pd.getStr() + "\r\n");
//				System.out.println("*ok");
			}
			bw.append("\r\n");
		}
		
		bw.close();
		
	}
	
	private static void printProof() throws Exception {
		
		String s = filepath + " Proof.txt";
		File file = new File(s);
		BufferedWriter bw = new BufferedWriter(new PrintWriter(file));
			
		//print proof
		bw.append("\r\n------------------------------------- Proof -------------------------------------\r\n\r\n");
		String[] b = CPU.StageName;
		for (int i = 0; i < CPU.StageSum + 2; i++) {
			bw.append(b[i]);
			for (int num = 0; num < Proof.size(i); num++) {
				Procedure pd = Proof.get(i,num);
				if(pd.isUsed()){
					bw.append("\t" + pd.getStr() + "\r\n");
				}
			}
			bw.append("\r\n");
		}
		
		bw.close();
		
	}
	
	private static void printResult() throws Exception {
		
		String s = filepath + " Result.txt";
		File file = new File(s);
		BufferedWriter bw = new BufferedWriter(new PrintWriter(file));
			
		//print verification result
		bw.append("\r\n------------------------------ Verification Result ------------------------------\r\n\r\n");
		
		bw.append("Result: " + (result?"Correctness":"Incorrectness") + "\r\n");
		
		Timer.stop();
		bw.append("Used Time: " + Timer.getRunTime() + "ms\r\n");
		
		bw.append("Extracted Formulas: " + FormulaSet.getSize() + "\r\n");
		
		bw.append("Procedures: " + Proof.getSize() + "\r\n");
				
		bw.close();
		
	}
	
}
