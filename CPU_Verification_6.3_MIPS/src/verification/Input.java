package verification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import proving_model.*;
import cpu_model.cpu.*;
import cpu_model.element.*;

public class Input {
	
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
		
	private static String instructionName;
	
	private static void init() throws IOException{
		
		workbook = null;
		sheet = null;		
		instructionName = null;		
		FormulaSet.clear();
		
	}
	
	public static void startTest(String filepath) throws Exception {
		init();
		workbook = new XSSFWorkbook(filepath);
		sheet = workbook.getSheetAt(0);
		getCell(0, 0);
	}
	
	//Instruction Mode
	public static void startInstructionMode(String filepath) throws Exception {
		
		init();
		
		//打开excel文件
		workbook = new XSSFWorkbook(filepath);
						
		readSemantics();
		readInstructionPath();
				
		//关闭文件
//		workbook.close();
		
		FormulaSet.number();
		
		//debugFormulaSet();
		//printFormulaSet(filepath);
				
	}
	
	//CPU Mode
	public static void startCPUMode(String CPUPathFilepath, String semanticsFilepath) throws IOException {
		
		init();
		
		//读取第一个文件中的指称语义
		workbook = new XSSFWorkbook(semanticsFilepath);
		readSemantics();
//		workbook.close();
		
		//读取第二个文件中的CPU通路
		workbook = new XSSFWorkbook(CPUPathFilepath);
		readCPUPath();
//		workbook.close();
		
		FormulaSet.number();
		
		//debugFormulaSet();
		
	}
	
	//读取文件第一页的指称语义
	private static void readSemantics() {
		
		sheet = workbook.getSheetAt(0);		
		int rowNum = 0;
		
		//指令名称
		instructionName = getCell(rowNum++, 1);
		
		//指令格式
		String instructionForm = getCell(rowNum++, 1);
		CPU.setInstructionForm(instructionForm);
		
		//前置条件
		do {
			String s = getCell(rowNum++, 1);
			Formula f = regDataToFormula(s);
			FormulaSet.add(0, f);			
		} while (getCell(rowNum, 0).isEmpty() && !getCell(rowNum, 1).isEmpty());
		
		//后置条件
		do {
			String s = getCell(rowNum++, 1);
			Formula f = regDataToFormula(s);
			FormulaSet.add(CPU.StageSum + 1, f);
		} while (getCell(rowNum,0).isEmpty() && !getCell(rowNum, 1).isEmpty());		
		
	}
	
	//读取指令通路
	private static void readInstructionPath() {
		
		sheet = workbook.getSheetAt(1);
		
		//所有控制端口
		ArrayList<CtrlPort> ctrlPortList = null;
				
		//有效控制端口矩阵。stageSum个阶段，每个阶段一个列表，包含该阶段所有有效的控制端口。
		ArrayList<ArrayList<CtrlPort>> activeCtrlPortMatrix = new ArrayList<ArrayList<CtrlPort>>();
		for (int i = 0; i < CPU.StageSum; i++)
			activeCtrlPortMatrix.add(new ArrayList<CtrlPort>());		
		
		//开始遍历
		int rowNum = 1;
		int stage = -1;
		while (true) {
		
			String s1 = getCell(rowNum, 0);
			String s2 = getCell(rowNum, 1);
			String s3 = getCell(rowNum, 2);
			rowNum++;
			
			if (!s1.isEmpty()) {
				stage++;
				if (s2.isEmpty() && s3.isEmpty())
					continue;				
			}
			else if (s2.isEmpty() && s3.isEmpty())
				break;
			
			//通路
			if (stage % 2 == 0) {
				Formula f = pathToFormula(s2, s3);
				for (int j = 1; j <= CPU.StageSum; j++)
					FormulaSet.add(j, f);
			}
			//控制信号
			else {
				CtrlPort port = CPU.getCtrlPort(s2, s3);
				activeCtrlPortMatrix.get(stage / 2).add(port);
			}									

		}
		
		//获得CPU激活的所有寄存器和多路选择器控制端口
		ctrlPortList = CPU.getCtrlPortList();
		
		//添加控制信号公式到公式集
		Formula f = null;
		for (int i = 0; i < CPU.StageSum; i++) {
			for (CtrlPort port : ctrlPortList) {
				if (activeCtrlPortMatrix.get(i).contains(port))
					f = ctrlSignalToFormula(port, 1);
				else
					f = ctrlSignalToFormula(port, 0);
				FormulaSet.add(i + 1, f);
			}
		}						
	
	}
		
	//读取CPU通路
	private static void readCPUPath() {
		
		sheet = workbook.getSheetAt(0);		
		
		//所有控制端口
		ArrayList<CtrlPort> ctrlPortList = null;			
		
		//有效控制端口。stageSum个阶段，每个阶段一个列表，包含该阶段所有有效的控制端口。
		ArrayList<ArrayList<CtrlPort>> activeCtrlPortMatrix = new ArrayList<ArrayList<CtrlPort>>();
		for (int i = 0; i < CPU.StageSum; i++)
			activeCtrlPortMatrix.add(new ArrayList<CtrlPort>());
		
		//开始遍历
		int rowNum = 1;
		while (true) {
			
			String s1 = getCell(rowNum, 0);
			String s2 = getCell(rowNum, 1);
			String s3 = getCell(rowNum, 2);
			String s4 = getCell(rowNum, 3);
			String s5 = getCell(rowNum, 4);
			
			if (s1.isEmpty())
				break;
			
			/*
			//直连
			if (s5.isEmpty()) {
				Formula f = pathToFormula(s1, s4);
				for (int i = 1; i <= 5; i++)
					FormulaSet.add(i, f);
			}
			*/
			
			//寄存器控制信号
			if (s2.isEmpty() && s3.isEmpty()) {
				
				CtrlPort port = CPU.getCtrlPort(s1, s4);
							
				if (s5.contains(instructionName + "&IF"))
					activeCtrlPortMatrix.get(0).add(port);
				if (s5.contains(instructionName + "&ID"))
					activeCtrlPortMatrix.get(1).add(port);
				if (s5.contains(instructionName + "&EX"))
					activeCtrlPortMatrix.get(2).add(port);
				if (s5.contains(instructionName + "&MEM"))
					activeCtrlPortMatrix.get(3).add(port);
				if (s5.contains(instructionName + "&WB"))
					activeCtrlPortMatrix.get(4).add(port);
			}
			
			//多路选择器通路
			else {
				
				//通路结构
				Formula f1 = pathToFormula(s1, s2);
				Formula f2 = pathToFormula(s3, s4);
				for (int i = 1; i <= CPU.StageSum; i++) {
					FormulaSet.add(i, f1);
					FormulaSet.add(i, f2);
				}
				
				//Mux控制信号
				CtrlPort port = CPU.getCtrlPort("Ctrl" + s2);				
				if (s5.contains(instructionName))
					for (int i = 0; i < CPU.StageSum; i++)
						activeCtrlPortMatrix.get(i).add(port);
			}				
				
			rowNum++;
		}
		
		//获得CPU激活的所有寄存器和多路选择器控制端口
		ctrlPortList = CPU.getCtrlPortList();
		
		//添加控制信号公式到公式集
		Formula f = null;
		for (int i = 0; i < CPU.StageSum; i++) {
			for (CtrlPort port : ctrlPortList) {
				if (activeCtrlPortMatrix.get(i).contains(port))
					f = ctrlSignalToFormula(port, 1);
				else
					f = ctrlSignalToFormula(port, 0);
				FormulaSet.add(i + 1, f);
			}
		}	

	}
	
	
	//将寄存器状态转换成公式
	private static Formula regDataToFormula(String str) {
		
		//按[]和=分隔开	
		int i1 = str.indexOf("[");
		int i2 = str.indexOf("=");
		String s1 = str.substring(0, i1);
		String s2 = str.substring(i1 + 1, i2 - 1);
		String s3 = str.substring(i2 + 1);
		
		Reg reg = null;
		Data addr = null;
		Data data = null;
		
		//不带地址的单寄存器结构
		if (s1.isEmpty()) {
			reg = CPU.getReg(s2);
			data = CPU.addData(s3); 
		}
		//带地址的复合寄存器结构
		else {
			reg = CPU.getReg(s1);
			addr = CPU.addData(s2);
			data = CPU.addData(s3);
		}
			
		return new RegContentFormula(reg, addr, data);
		
	}
	
	//将控制信号转换成公式
	private static Formula ctrlSignalToFormula(CtrlPort port, int value) {
		if (value == 0)
			return new CtrlSignalFormula(port, CPU.addData("0"));
		else if (value == 1)
			return new CtrlSignalFormula(port, CPU.addData("1"));
		return null;
	}

	//将通路结构转换成公式
	private static Formula pathToFormula(String port1, String port2) {
		return (Formula) new PathFormula(CPU.getDataPort(port1), CPU.getDataPort(port2));
	}
	
	//取出一个Cell并转为String，含空Cell判断
	private static String getCell(int rowNum, int cellNum){
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
	
	//debug
	static void debugFormulaSet() {
		for (int i = 0; i < CPU.StageSum + 1; i++) {
			for (int j = 0; j < FormulaSet.size(i); j++)				
				System.out.println(FormulaSet.get(i,j).getFormula().getStr());
			System.out.println();
		}
	}
		
	
	static void printFormulaSet(String s) throws Exception {
		
		String filepath = s.substring(0, s.indexOf(".xlsx")) + " FormulaSet.txt";
		File file = new File(filepath);
		BufferedWriter bw = new BufferedWriter(new PrintWriter(file));
		
		//print instruction name
		bw.append("---------------------------------- " + instructionName + " ----------------------------------\r\n\r\n");
		
		//print formula set		
		bw.append("---------------------------------- Formula Set ----------------------------------\r\n\r\n");
		String[] a = CPU.StageName;
		for (int part = 0; part < CPU.StageSum + 2; part++) {
			bw.append(a[part]);
			for (int num = 0; num < FormulaSet.size(part); num++)
				bw.append("\t" + FormulaSet.get(part,num).getStr() + "\r\n");
			bw.append("\r\n");
		}
		
		bw.close();

	}
	
}
