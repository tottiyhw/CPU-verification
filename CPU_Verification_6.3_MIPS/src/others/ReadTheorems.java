package others;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cpu_model.cpu.CPU;
import proving_model.Axiom;

public class ReadTheorems {
	private static XSSFWorkbook srcFile = null;
	private static XSSFSheet srcSheet = null;
	String filepath;
	
	// ReadTheorems rt = new ReadTheorems("data\\theorems.xlsx");
	public ReadTheorems(String resultFilePath) throws IOException{
		filepath = resultFilePath;
		CPU.theorems = new HashMap<String, ArrayList<Axiom>>();
	}
	
	public void doRead() throws IOException {
		srcFile = new XSSFWorkbook(filepath);
		int i;
		// 读指称语义
		srcSheet = srcFile.getSheetAt(0);
		i = 1;
		while (!getCell(srcSheet, i, 0).isEmpty()){
			String cell0 = getCell(srcSheet, i, 0);
			String cell1 = getCell(srcSheet, i, 1);
			String cell2 = getCell(srcSheet, i, 2);
			if (!CPU.theorems.containsKey(cell0)){
				Axiom axiom = new Axiom(cell1);
				axiom.addItem(cell2);
				ArrayList<Axiom> axioms = new ArrayList<Axiom>();
				axioms.add(axiom);
				CPU.theorems.put(cell0, axioms);
			}
			else{
				ArrayList<Axiom> axioms = CPU.theorems.get(cell0);
				int k;
				for (k = 0; k < axioms.size(); k++){
					if (axioms.get(k).getName().equals(cell1)){
						CPU.theorems.get(cell0).get(k).addItem(cell2);
						break;
					}
				}
				if (k == axioms.size()){
					Axiom axiom = new Axiom(cell1);
					axiom.addItem(cell2);
					CPU.theorems.get(cell0).add(axiom);
				}
			}
			i++;
		}
		System.out.println("read theorems succeed ...");	
	}
	
	//取出一个Cell并转为String，含空Cell判断
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
}
