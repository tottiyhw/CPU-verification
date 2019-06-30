package mipsCPUGen_util;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelCellProcessor {
	
	@SuppressWarnings("static-access")
	public static  String getValue(XSSFCell xssfCell) {
		if(xssfCell==null){
			return null;
		}else{
			if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(xssfCell.getBooleanCellValue());
			} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
				return String.valueOf((int)xssfCell.getNumericCellValue());
			} else {
				return String.valueOf(xssfCell.getStringCellValue());
			}
		}
	}

}
