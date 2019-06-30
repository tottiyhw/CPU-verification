package others;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PathMerge {
	private static XSSFWorkbook srcFile = null;
	private static XSSFWorkbook destFile = null;
	private static XSSFSheet srcSheet = null;
	private static XSSFSheet destSheet = null;
	private File resultFile;
	
	// PathMerge pm = new PathMerge("data\\new.xlsx");
	public PathMerge(String resultFilePath) throws IOException{
		destFile = new XSSFWorkbook();
		destSheet = destFile.createSheet("Path");
		resultFile = new File(resultFilePath);
	}
	
	public void doPathMerge() throws IOException {	
		File f = new File("data\\instruction_list.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		String s = null;
		int insNum = 0;		// 指令数目
		int insHead = 0;	// 指令首行行号
		int insEnd = -1;	// 指令尾行行号
		String insName;		// 指令名
		int i, j;
		while((s = br.readLine()) != null) {			
			String name = s.substring(s.indexOf(" ") + 1);	// 指令通路文件名
			insName = name.substring(1, name.length() - 1);
			insNum++;
			insHead = insEnd + 1;
			i = insHead;
			// 设置指令名
			setCell(destSheet, i, 0, insName);
			// 指令通路文件路径
			String filepath = "data\\" + s + "\\" + name + ".xlsx";
			srcFile = new XSSFWorkbook(filepath);
			String cellContent;
			// 读指称语义
			srcSheet = srcFile.getSheetAt(0);
			j = 0;
			while (!getCell(srcSheet, j, 0).isEmpty() || !getCell(srcSheet, j, 1).isEmpty()){
				setCell(destSheet, i, 5, getCell(srcSheet, j, 0));
				setCell(destSheet, i, 6, getCell(srcSheet, j, 1));
				insEnd = i > insEnd ? i : insEnd;
				i++;
				j++;
			}
			// 读指令通路
			srcSheet = srcFile.getSheetAt(1);
			j = 1;
			i = insHead;
			while (!getCell(srcSheet, j, 0).isEmpty() || !getCell(srcSheet, j, 1).isEmpty()){
				setCell(destSheet, i, 1, getCell(srcSheet, j, 0));
				setCell(destSheet, i, 2, getCell(srcSheet, j, 1));
				setCell(destSheet, i, 3, getCell(srcSheet, j, 2));
				insEnd = i > insEnd ? i : insEnd;
				i++;
				j++;
			}
			// 合并指令名所在单元格
			System.out.println(insName + " done");
			destSheet.addMergedRegion(new CellRangeAddress(insHead, insEnd, 0, 0));
		}
		// 合并结果写入目标文件
		FileOutputStream fos = new FileOutputStream(resultFile);
		destFile.write(fos);
		fos.flush();
		fos.close();
		br.close();
		System.out.println(insNum + " path merge succeed ...");	
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
