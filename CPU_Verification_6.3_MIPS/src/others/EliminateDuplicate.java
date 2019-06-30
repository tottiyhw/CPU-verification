package others;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EliminateDuplicate {
	private static XSSFWorkbook srcFile = null;
	private static XSSFSheet srcSheet = null;
	private String filepath;
	private FileInputStream inputStream;
	private FileOutputStream fos;
	
//	EliminateDuplicate ed = new EliminateDuplicate("data\\PPC with MMU.xlsx");
//	ed.doEliminateDuplicate();
	public EliminateDuplicate(String file) throws IOException{
		filepath = file;
		inputStream = new FileInputStream(filepath);
		srcFile = new XSSFWorkbook(inputStream);
		srcSheet = srcFile.getSheetAt(0);
	}
	
	public void doEliminate() throws IOException {		
		int i, j, stage;
		String[] cell = new String[3];
		for (i = 0; i < cell.length; i++){
			cell[i] = "";
		}
		String[] stages = {"IF", "IF/IMMU", "IMMU", "IMMU/ID", "ID", "ID/EX", "EX", "EX/MEM",
				"MEM", "MEM/DMMU1", "DMMU1", "DMMU1/DMMU2", "DMMU2", "DMMU2/WB", "WB", "WB/IF"};
		i = 0;
		j = 0;
		stage = 15;
		String str = "";
		while (!getCell(srcSheet, i, 0).isEmpty()){
			for (j = 0; j < 3; j++){
				if (!getCell(srcSheet, i, j).equals(cell[j])){
					cell[j] = getCell(srcSheet, i, j);
					if (j == 1){
						setCell(srcSheet, i, 0, (int)Double.parseDouble(cell[j]) + "." + getCell(srcSheet, i, 0));
					}
					if (j == 2){
						stage = (stage + 1) % 16;
						str = cell[j];
						if (str.equals("MEM/WB") && stage == 9){
							setCell(srcSheet, i, 2, "MEM/DMMU1");
						}
						else if (str.equals("MEM/WB") && stage == 13){
							setCell(srcSheet, i, 2, "DMMU2/WB");
						}
						else{
							while (!str.equals(stages[stage])){
								srcSheet.shiftRows(i, srcSheet.getLastRowNum(), 1, true, false); 
								srcSheet.createRow(i);
								setCell(srcSheet, i, j, stages[stage]);
								stage = (stage + 1) % 16;
								i++;
							}
						}
					}
					
				}
				else{
					setCell(srcSheet, i, j, "");
				}
			}
			i++;
		}
		str = getCell(srcSheet, i - 1, 2);
		while (!str.equals(stages[15])){
			srcSheet.createRow(i);
			setCell(srcSheet, i, 2, stages[stage]);
			str = stages[stage];
			stage = (stage + 1) % 16;
			i++;
		}
		// 合并结果写入目标文件
		fos = new FileOutputStream(filepath);
		srcFile.write(fos);
		System.out.println("eliminate duplicate succeed ...");	
	}
	
	private static void setCell(XSSFSheet sheet, int rowNum, int cellNum, String s) {
		XSSFRow row = sheet.getRow(rowNum);
		if(row == null)
			row = sheet.createRow(rowNum);
		XSSFCell cell = row.createCell((short)cellNum);
		
		cell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
		cell.setCellValue(s);
		
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
