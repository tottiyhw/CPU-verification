package others;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ChangeCells {

	private static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet = null;
	
	public static void main(String[] args) throws IOException {
		
		File f = new File("data//instruction_list.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		String s = null;				
		while ((s = br.readLine()) != null) {
			
			String name = s.substring(s.indexOf(" ") + 1);
			String filepath = "data\\" + s + "\\" + name + ".xlsx";
			workbook = new XSSFWorkbook(filepath);
			sheet = workbook.getSheetAt(0);
			
			String cellContent = getCell(3, 1);
			if (cellContent.equals("PC[CIA]=addr"))
				setCell(3, 1, "PC[NIA]=addr");
		}
		
		br.close();
			
	}
		
	private static void setCell(int rowNum, int cellNum, String s) {
		XSSFRow row = sheet.getRow(rowNum);
		if (row == null)
			return;
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null)
			return;
		
		cell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
		cell.setCellValue(true);
		//cell.setCellValue(s);
		//cell.setCellValue(new XSSFRichTextString(s));
		
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
	
	
}
