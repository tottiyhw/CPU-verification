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
		int insNum = 0;		// ָ����Ŀ
		int insHead = 0;	// ָ�������к�
		int insEnd = -1;	// ָ��β���к�
		String insName;		// ָ����
		int i, j;
		while((s = br.readLine()) != null) {			
			String name = s.substring(s.indexOf(" ") + 1);	// ָ��ͨ·�ļ���
			insName = name.substring(1, name.length() - 1);
			insNum++;
			insHead = insEnd + 1;
			i = insHead;
			// ����ָ����
			setCell(destSheet, i, 0, insName);
			// ָ��ͨ·�ļ�·��
			String filepath = "data\\" + s + "\\" + name + ".xlsx";
			srcFile = new XSSFWorkbook(filepath);
			String cellContent;
			// ��ָ������
			srcSheet = srcFile.getSheetAt(0);
			j = 0;
			while (!getCell(srcSheet, j, 0).isEmpty() || !getCell(srcSheet, j, 1).isEmpty()){
				setCell(destSheet, i, 5, getCell(srcSheet, j, 0));
				setCell(destSheet, i, 6, getCell(srcSheet, j, 1));
				insEnd = i > insEnd ? i : insEnd;
				i++;
				j++;
			}
			// ��ָ��ͨ·
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
			// �ϲ�ָ�������ڵ�Ԫ��
			System.out.println(insName + " done");
			destSheet.addMergedRegion(new CellRangeAddress(insHead, insEnd, 0, 0));
		}
		// �ϲ����д��Ŀ���ļ�
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
}
