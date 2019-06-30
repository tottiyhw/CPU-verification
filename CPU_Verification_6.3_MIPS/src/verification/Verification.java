package verification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import others.AddNo;
import others.EliminateDuplicate;
import others.PathMerge;
import others.ReadTheorems;
import proving_model.ConditionValue;
import proving_model.Conditions;
import proving_model.CtrlSignalFormula;
import proving_model.Formula;
import proving_model.FormulaSet;
import proving_model.PathFormula;
import proving_model.PortDataFormula;
import proving_model.RegContentFormula;
import cpu_model.cpu.CPU;
import cpu_model.element.CtrlPort;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import cpu_model.element.Reg;


//CPU��֤
//main�������

//1.��ȡ�ļ������ɹ�ʽ��
//2.֤��������֤������
//3.������ļ�

public class Verification {
	
	private static String rootPath = "data\\";
	private static String instructionListFilepath = rootPath + "instruction_list.txt";	
	
	private static void init() throws Exception {
		
		/*
		//test CPU constructing time: 10ms
		Timer.start();
		CPU.addData("123");
		Timer.stop();
		System.out.println(Timer.getRunTime());
		*/
		
		//initialize system
		CPU.init();
		
		System.out.print(String.format("%-25s","Initializing system: "));
		Timer.start();
		
		String testFilepath = rootPath + "test.xlsx";		
		Input.startTest(testFilepath);
		
		Timer.stop();
		System.out.println(Timer.getRunTime() + "ms");				
		
	}
	
	public static void startInstructionMode() throws Exception{
		
		init();				
		
		File f = new File(instructionListFilepath);
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		String s = null;				
		while ((s = br.readLine()) != null) {
			
			//debug
			System.out.println(String.format("%-25s", s + ": "));
			
			String name = s.substring(s.indexOf(" ") + 1);
			String inputFilepath = rootPath + s + "\\" + name + ".xlsx";
			String filepath = rootPath + s + "\\" + name;
			
			Timer.start();	
			
			CPU.init();
			Input.startInstructionMode(inputFilepath);
			Deduce.start();
			CheckAndOutput.start(filepath);
			
			System.out.print(String.format("%-10s", Timer.getRunTime() + "ms"));
			
			System.out.print(String.format("%-15s", CheckAndOutput.getResult()));			
			
			System.out.print("\r\n");
			
		}			
		
		br.close();		

	}
	
	public static void startInstructionMode2(String insFile) throws Exception{
		init();	
		XSSFWorkbook srcFile = new XSSFWorkbook(rootPath + insFile);
		XSSFSheet srcSheet = srcFile.getSheetAt(0);
		String instructionName;
		int insStartLine = -1, insEndLine = -1, insNo = 0;
		int i, j, stage;
		double times = 0;
		String insID;
		//���п��ƶ˿�
		ArrayList<CtrlPort> ctrlPortList;
		//��Ч���ƶ˿ھ���stageSum���׶Σ�ÿ���׶�һ���б������ý׶�������Ч�Ŀ��ƶ˿ڡ�
		ArrayList<ArrayList<CtrlPort>> activeCtrlPortMatrix;
		while (!getCell(srcSheet, insEndLine + 1, 0).isEmpty()){
			insNo++;
			instructionName = getCell(srcSheet, insEndLine + 1, 6);
			insID = getCell(srcSheet, insEndLine + 1, 0);
			System.out.println(insID + ": ");
			String filepath = rootPath + CPU.testType + "\\" + insID + "\\" + instructionName + " -";
			Timer.start();
			CPU.init();
			//��ȡָ��ͨ·
			ctrlPortList = null;
			activeCtrlPortMatrix = new ArrayList<ArrayList<CtrlPort>>();
			for (j = 0; j < CPU.StageSum; j++)
				activeCtrlPortMatrix.add(new ArrayList<CtrlPort>());
			insStartLine = insEndLine + 1;
			i = insStartLine;
			stage = -1;
			FormulaSet.clear();
			do{
				String s1 = getCell(srcSheet, i, 1);
				String s2 = getCell(srcSheet, i, 2);
				String s3 = getCell(srcSheet, i, 3);
				if (!s1.isEmpty()) {
					stage++;
					if (s2.isEmpty() && s3.isEmpty()){
						i++;
						continue;		
					}
				}
				else if (s2.isEmpty() && s3.isEmpty()){
					break;
				}
				//ͨ·
				if (stage % 2 == 0) {
//					����6'b000000
					if (s2.indexOf("'") != -1){
						Data data = new Data(s2);
						DataPort dataPort = CPU.getDataPort(s3);
						Formula f = portDataToFormula(dataPort, data);
						FormulaSet.add((stage + 2) / 2, f);
					}
					else{
						try{
							Formula f = pathToFormula(s2, s3);
							for (j = 1; j <= CPU.StageSum; j++)
								FormulaSet.add(j, f);
						}
						catch (Exception e){
							System.out.println(e.getMessage());
						}
					}
				}
				//�����ź�
				else {
					CtrlPort port = CPU.getCtrlPort(s2, s3);
					activeCtrlPortMatrix.get(stage / 2).add(port);
				}
				i++;
			}while(getCell(srcSheet, i, 0).isEmpty() && (!getCell(srcSheet, i, 1).isEmpty() 
					|| !getCell(srcSheet, i, 2).isEmpty() || !getCell(srcSheet, i, 3).isEmpty()));
			//���CPU��������мĴ����Ͷ�·ѡ�������ƶ˿�
			ctrlPortList = CPU.getCtrlPortList();
			//��ӿ����źŹ�ʽ����ʽ��
			Formula f = null;
			for (j = 0; j < CPU.StageSum; j++) {
				for (CtrlPort port : ctrlPortList) {
					if (activeCtrlPortMatrix.get(j).contains(port))
						f = ctrlSignalToFormula(port, 1);
					else
						f = ctrlSignalToFormula(port, 0);
					FormulaSet.add(j + 1, f);
				}
			}
			insEndLine = i - 1;
			//��ȡָ������
			i = insStartLine;
			//ָ������
			instructionName = getCell(srcSheet, i++, 6);
			//ָ���ʽ
			String instructionForm = getCell(srcSheet, i++, 6);
			CPU.setInstructionForm(instructionForm);
			//ǰ������
			do {
				String s = getCell(srcSheet, i++, 6);
				f = regDataToFormula(s);
				FormulaSet.add(0, f);			
			}while (getCell(srcSheet, i, 5).isEmpty() && !getCell(srcSheet, i, 6).isEmpty());
			//��������
			do {
				String s = getCell(srcSheet, i++, 6);
				f = regDataToFormula(s);
				FormulaSet.add(CPU.StageSum + 1, f);
			}while (getCell(srcSheet, i, 5).isEmpty() && !getCell(srcSheet, i, 6).isEmpty());
			if (i - 1 > insEndLine){
				insEndLine = i - 1;
			}
//			��ȡ����
			Conditions.clearConds();
			for (i = insStartLine; i <= insEndLine; i++){
				if (!getCell(srcSheet, i, 4).isEmpty()){
					Conditions.add(getCell(srcSheet, i, 4));
				}
			}
			FormulaSet.number();
			Deduce.start();
			CheckAndOutput.completeConds();
//			������ֵ��������Ϸֱ���֤
			ArrayList<int[]> combinations = Conditions.generateConds();
			int c;
			for (c = 0; c < combinations.size(); c++){
				Conditions.setAll(combinations.get(c));
//				��ʼ��֤
				ctrlPortList = null;
				activeCtrlPortMatrix = new ArrayList<ArrayList<CtrlPort>>();
				for (j = 0; j < CPU.StageSum; j++){
					activeCtrlPortMatrix.add(new ArrayList<CtrlPort>());
				}
				stage = -1;
				FormulaSet.clear();
				i = insStartLine;
				do{
					String s1 = getCell(srcSheet, i, 1);
					String s2 = getCell(srcSheet, i, 2);
					String s3 = getCell(srcSheet, i, 3);
					String s4 = getCell(srcSheet, i, 4);
					if (!s1.isEmpty()) {
						stage++;
						if (s2.isEmpty() && s3.isEmpty()){
							i++;
							continue;		
						}
					}
					else if (s2.isEmpty() && s3.isEmpty()){
						break;
					}
					if (s4.isEmpty() || Conditions.judge(s4)){
//						if (!s4.isEmpty()){
//							System.out.println(i + ":" + s4 + "," + Conditions.judge(s4));
//						}
//						ͨ·
						if (stage % 2 == 0) {
//							����6'b000000
							if (s2.indexOf("'") != -1){
								Data data = new Data(s2);
								DataPort dataPort = CPU.getDataPort(s3);
								f = portDataToFormula(dataPort, data);
								FormulaSet.add((stage + 2) / 2, f);
							}
							else{
								try{
									f = pathToFormula(s2, s3);
									for (j = 1; j <= CPU.StageSum; j++){
										FormulaSet.add(j, f);
									}
								}
								catch (Exception e){
									System.out.println(e.getMessage());
								}
							}
						}
//						�����ź�
						else {
							CtrlPort port = CPU.getCtrlPort(s2, s3);
							activeCtrlPortMatrix.get(stage / 2).add(port);
						}
					}
					i++;
				}while(i <= insEndLine);
				//���CPU��������мĴ����Ͷ�·ѡ�������ƶ˿�
				ctrlPortList = CPU.getCtrlPortList();
				//��ӿ����źŹ�ʽ����ʽ��
				f = null;
				for (j = 0; j < CPU.StageSum; j++) {
					for (CtrlPort port : ctrlPortList) {
						if (activeCtrlPortMatrix.get(j).contains(port)){
							f = ctrlSignalToFormula(port, 1);
						}
						else{
							f = ctrlSignalToFormula(port, 0);
						}
						FormulaSet.add(j + 1, f);
					}
				}
				insEndLine = i - 1;
				//��ȡָ������
				i = insStartLine;
				//ָ������
				instructionName = getCell(srcSheet, i++, 6);
				//ָ���ʽ
				instructionForm = getCell(srcSheet, i++, 6);
				CPU.setInstructionForm(instructionForm);
				//ǰ������
				do {
					String s = getCell(srcSheet, i++, 6);
//					if (s.indexOf(" | ") != -1){
//						System.out.println(s.substring(s.indexOf(" | ") + 3, s.length() - 1));
//					}
					if (s.indexOf(" | ") == -1){
						f = regDataToFormula(s);
						FormulaSet.add(0, f);
					}
					else if (Conditions.judgeOriginal(s.substring(s.indexOf(" | ") + 3, s.length() - 1))){
						s = s.substring(0, s.indexOf(" | "));
						s = s.substring(0, s.indexOf('=') + 1) + s.substring(s.indexOf('=') + 2, s.length());
						f = regDataToFormula(s);
						FormulaSet.add(0, f);
					}
				}while (getCell(srcSheet, i, 5).isEmpty() && !getCell(srcSheet, i, 6).isEmpty());
				//��������
				do {
					String s = getCell(srcSheet, i++, 6);
//					System.out.println(s);
					if (s.indexOf(" | ") != -1){
						String st = s.substring(s.indexOf(" | ") + 3, s.length() - 1);
//						System.out.println(st);
					}
					if (s.indexOf(" | ") == -1){
						f = regDataToFormula(s);
						FormulaSet.add(CPU.StageSum + 1, f);
					}
					else if (Conditions.judgeOriginal(s.substring(s.indexOf(" | ") + 3, s.length() - 1))){
						s = s.substring(0, s.indexOf(" | "));
						s = s.substring(0, s.indexOf('=') + 1) + s.substring(s.indexOf('=') + 2, s.length());
						f = regDataToFormula(s);
						FormulaSet.add(CPU.StageSum + 1, f);
					}
				}while (getCell(srcSheet, i, 5).isEmpty() && !getCell(srcSheet, i, 6).isEmpty());
				FormulaSet.number();
				Deduce.start();
				CheckAndOutput.start(filepath + c);
				for (j = 0; j < Conditions.getConds().size(); j++){
					System.out.print(Conditions.getConds().get(j).getPort() + "=" + Conditions.getConds().get(j).getValue() + " ");
				}
				System.out.print(String.format("%-15s", CheckAndOutput.getResult()));
				System.out.print("\r\n");
			}
			System.out.println(String.format("%-10s", Timer.getRunTime() + "ms"));
			times += Timer.getRunTime();
			i = insEndLine + 1;
		}
		System.out.println(insNo + " instructions : " + times + "ms");
		System.out.println("average time : " + (times / insNo) + "ms");
	} 
	
	//ȡ��һ��Cell��תΪString������Cell�ж�
	private static String getCell(XSSFSheet sheet, int rowNum, int cellNum){
		XSSFRow row = sheet.getRow(rowNum);
		if (row==null)
			return "";
		XSSFCell cell = row.getCell(cellNum);
		if (cell==null)
			return "";
		if (cell.getCellType()==XSSFCell.CELL_TYPE_BLANK)
			return "";
		return cell.toString();
	}
	
	//���Ĵ���״̬ת���ɹ�ʽ
	private static Formula regDataToFormula(String str) {
		
		//��[]��=�ָ���	
		int i1 = str.indexOf("[");
		int i2 = str.indexOf("=");
		String s1 = str.substring(0, i1);
		String s2 = str.substring(i1+1, i2-1);
		String s3 = str.substring(i2+1);
		
		Reg reg = null;
		Data addr = null;
		Data data = null;
		
		//������ַ�ĵ��Ĵ����ṹ
		if (s1.isEmpty()) {
			reg = CPU.getReg(s2);
			data = CPU.addData(s3); 
		}
		//����ַ�ĸ��ϼĴ����ṹ
		else {
			reg = CPU.getReg(s1);
			addr = CPU.addData(s2);
			data = CPU.addData(s3);
		}
			
		return new RegContentFormula(reg, addr, data);
		
	}
	
	//�������ź�ת���ɹ�ʽ
	private static Formula ctrlSignalToFormula(CtrlPort port, int value) {
		if (value == 0)
			return new CtrlSignalFormula(port, CPU.addData("0"));
		else if (value == 1)
			return new CtrlSignalFormula(port, CPU.addData("1"));
		return null;
	}
	
	//��ͨ·�ṹת���ɹ�ʽ
	private static Formula pathToFormula(String port1, String port2) throws Exception {
//		����[:]
		String port = port1;
		if (port.indexOf(":") != -1){
			port = port.replace(":", "_");
		}
		if (port.indexOf("[") != -1){
			port = port.replace("[", "");
			port = port.replace("]", "");
		}
		if (CPU.getDataPort(port) == null){
			throw(new Exception(port1 + "�����ڣ�" + port1 + "," + port2));
		}
		else if (CPU.getDataPort(port2) == null){
			throw(new Exception(port2 + " �����ڣ�" + port1 + "," + port2));
		}
		return (Formula) new PathFormula(CPU.getDataPort(port), CPU.getDataPort(port2));
	}
	
	//���˿�����ת���ɹ�ʽ
	private static Formula portDataToFormula(DataPort port, Data data) {
		return new PortDataFormula(port, data);
	}
	
	public static void startCPUMode() throws Exception{
		
		init();		
		
		String cpuRootPath = rootPath + "CPU\\";
		String cpuPathFilepath = cpuRootPath + "CPU_structure.xlsx";
		
		File f = new File(instructionListFilepath);
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		int totalTime = 0;
		
		String s = null;
		while((s = br.readLine())!=null) {
			
			//debug
			System.out.print(String.format("%-25s",s+": "));
			
			String name = s.substring(s.indexOf(" ") + 1);
			String semanticsFilepath = rootPath + s + "\\" + name + ".xlsx";
			String filepath = cpuRootPath + s;
			
			//�½��ļ���
			//new File(filepath).mkdir();
			
			Timer.start();
			
			CPU.init();
			Input.startCPUMode(cpuPathFilepath,semanticsFilepath);
			Deduce.start();
			CheckAndOutput.start(filepath + "\\" + name);
			
			System.out.print(String.format("%-10s",Timer.getRunTime()+"ms"));
			totalTime += Timer.getRunTime();
			
			System.out.print(String.format("%-15s",CheckAndOutput.getResult()));
			
			System.out.print("\r\n");			
							
		}
			
		System.out.print(String.format("%-25s", "Total Time:"));	
		System.out.print(String.format("%-10s", totalTime + "ms"));
		System.out.print("\r\n");
		
		br.close();		

	}
	
	public static void main(String args[]) throws Exception{
//		/*
		Scanner in = new Scanner(System.in);
		String testPath = in.nextLine();
		CPU.testType = testPath;
		if (testPath.equals("MIPS")){
//			ָ����֤
			String[] stageName = {"PRE", "IF", "ID", "EX", "MEM", "WB", "POST"};
			CPU.init(5, stageName);
			ReadTheorems rt = new ReadTheorems("data\\theorems_MIPS.xlsx");
			rt.doRead();
			Verification.startInstructionMode2("instruction_list_MIPS.xlsx");
		}
		else if (testPath.equals("MIPS MMU")){
			String[] stageName = {"PRE", "IF", "IMMU", "ID", "EX", "MEM", "DMMU1", "DMMU2", "WB", "POST"};
			CPU.init(8, stageName);
			ReadTheorems rt = new ReadTheorems("data\\theorems_MIPS_MMU.xlsx");
			rt.doRead();
			Verification.startInstructionMode2("instruction_list_MIPS_MMU.xlsx");
		}
		else if (testPath.equals("PPC")){
//			ָ����֤
			String[] stageName = {"PRE", "IF", "ID", "EX", "MEM", "WB", "POST"};
			CPU.init(5, stageName);
			ReadTheorems rt = new ReadTheorems("data\\theorems_PPC.xlsx");
			rt.doRead();
			Verification.startInstructionMode2("instruction_list_PPC.xlsx");
		}
		else if (testPath.equals("PPC MMU")){
//			ָ����֤
			String[] stageName = {"PRE", "IF", "IMMU", "ID", "EX", "MEM", "DMMU1", "DMMU2", "WB", "POST"};
			CPU.init(8, stageName);
			ReadTheorems rt = new ReadTheorems("data\\theorems_PPC_MMU.xlsx");
			rt.doRead();
			Verification.startInstructionMode2("instruction_list_PPC_MMU.xlsx");
		}
//		*/

//		
//		CPU��֤
//		Verification.startCPUMode();
//		����ָ��ͨ·�ϲ�
//		PathMerge pm = new PathMerge("data\\new.xlsx");
//		pm.doPathMerge();
//		EliminateDuplicate ed = new EliminateDuplicate("data\\pre_list.xlsx");
//		ed.doEliminate();
//		AddNo an = new AddNo("data\\mipsCPUGen12.xlsx");
//		an.doAddNo();
//		Conditions.clearConds();
//		Conditions.add("(CU.IMemHit|CU.ICacheHit)");
//		Conditions.add("(CU.IMemHit&~CU.ICacheHit)");
//		Conditions.add("((CU.DMemHit&~CU.DCacheHit))&(CU.IMemHit|CU.ICacheHit)");
//		ArrayList<ConditionValue> cv = Conditions.getConds();
//		for (int i = 0; i < cv.size(); i++){
//			System.out.println(cv.get(i).getPort());
//			cv.get(i).setValue(1);
//		}
//		System.out.println(Conditions.judge("((CU.DMemHit&CU.DCacheHit))&(CU.IMemHit|CU.ICacheHit)"));
//		System.out.println(Conditions.judge("((CU.DMemHit&~CU.DCacheHit))&(CU.IMemHit|CU.ICacheHit)"));
//		Conditions.generateConds();
	}
		
}
