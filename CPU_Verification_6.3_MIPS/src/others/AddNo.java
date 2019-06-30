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

public class AddNo {
	private static XSSFWorkbook srcFile = null;
	private static XSSFSheet srcSheet = null;
	private String filepath;
	private FileInputStream inputStream;
	private FileOutputStream fos;
	
//	AddNo an = new AddNo("data\\mipsCPUGen12.xlsx");
//	an.doAddNo();
	public AddNo(String file) throws IOException{
		filepath = file;
		inputStream = new FileInputStream(filepath);
		srcFile = new XSSFWorkbook(inputStream);
		srcSheet = srcFile.getSheetAt(0);
	}
	
	public void doAddNo() throws IOException {		
		int i, no;
		String cell;
		i = 0;
		no = 0;
		while (!getCell(srcSheet, i, 1).isEmpty() || !getCell(srcSheet, i, 2).isEmpty()){
			if (!getCell(srcSheet, i, 0).isEmpty()){
				cell = getCell(srcSheet, i, 0);
				no++;
				setCell(srcSheet, i, 0, no + "." + cell);
			}
			i++;
		}
		// 合并结果写入目标文件
		fos = new FileOutputStream(filepath);
		srcFile.write(fos);
		System.out.println("add no succeed ...");	
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
