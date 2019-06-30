package mipsCPUGen_DatapathGenerate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import mipsCPUGen_ComputeUnits.OPandFUNCpair;
import mipsCPUGen_util.ExcelCellProcessor;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DatapathInputInfo {
	private final static String muxFile="mux_def.xlsx";
	private HashMap<String, String> regs_mux = new HashMap<String, String>();
	private HashMap<String, String> mux_regs = new HashMap<String, String>();
	private HashMap<String, OPandFUNCpair> oPandFUNCinfo=new HashMap<String, OPandFUNCpair>();
	private HashMap<String, String> pathNO_name = new HashMap<String, String>();
	private ArrayList<String> funcvaluelist=new ArrayList<String>();

	public DatapathInputInfo(){
		try {
			this.generateMuxmap(regs_mux, mux_regs, oPandFUNCinfo,pathNO_name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("muxdef文件处理中出错或者opANDfunc文件处理出错");
			e.printStackTrace();
		}
	}
	
	private void generateMuxmap(HashMap<String, String> regs_mux, HashMap<String, String> mux_regs,
			HashMap<String, OPandFUNCpair> oPandFUNCinfo, HashMap<String,String> pathNO_name) throws IOException{
		
		@SuppressWarnings("deprecation")
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(muxFile);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		if (xssfSheet == null) {
			System.out.println(muxFile+" is empty!");
		}
		for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			if (xssfRow == null) {
				continue;
			}
			XSSFCell xssfCell1 = xssfRow.getCell(1);
			XSSFCell xssfCell2 = xssfRow.getCell(2);
			
			String mux=ExcelCellProcessor.getValue(xssfCell1);
			String regs=ExcelCellProcessor.getValue(xssfCell2);
			
			regs_mux.put(regs, mux);
			mux_regs.put(mux, regs);
		}
		
		XSSFSheet xssfSheet1 = xssfWorkbook.getSheetAt(1);
		if (xssfSheet1 == null) {
			System.out.println(muxFile+"OP and FUNC definition is empty!");;
		}
		for (int rowNum = 0; rowNum <= xssfSheet1.getLastRowNum(); rowNum++) {
			XSSFRow xssfRow = xssfSheet1.getRow(rowNum);
			if (xssfRow == null) {
				continue;
			}
			XSSFCell xssfCell0 = xssfRow.getCell(0);
			XSSFCell xssfCell1 = xssfRow.getCell(1);
			XSSFCell xssfCell2 = xssfRow.getCell(2);
			XSSFCell xssfCell3 = xssfRow.getCell(3);
			XSSFCell xssfCell4 = xssfRow.getCell(4);
			XSSFCell xssfCell5 = xssfRow.getCell(5);
			XSSFCell xssfCell6 = xssfRow.getCell(6);
			XSSFCell xssfCell8 = xssfRow.getCell(8);
			
			String datapath=ExcelCellProcessor.getValue(xssfCell0);
			String OP=ExcelCellProcessor.getValue(xssfCell1);
			String FUNC=ExcelCellProcessor.getValue(xssfCell2);
			String size=ExcelCellProcessor.getValue(xssfCell3);
			String funcpart=ExcelCellProcessor.getValue(xssfCell4);
			String cufuncvalue=ExcelCellProcessor.getValue(xssfCell5);
			String pathname=ExcelCellProcessor.getValue(xssfCell6);
			String cuvalue=ExcelCellProcessor.getValue(xssfCell8);
			
			int funcsize=Integer.parseInt(size);
			OPandFUNCpair pair=new OPandFUNCpair(OP,FUNC,funcsize,funcpart);
			oPandFUNCinfo.put(pathname, pair);
			pathNO_name.put(datapath, pathname);
			
			
			if(cufuncvalue==null||cufuncvalue.equals("")){
				continue;
			}else{
				String funcvalue;
				if(FUNC==null||FUNC.equals("")){
					funcvalue="{6{OP"+OP+"}}&"+cuvalue;
				}else{
					funcvalue="{6{OP"+OP+"&IR"+funcpart+FUNC+"}}&"+cuvalue;
				}
				funcvaluelist.add(funcvalue);
			}
		}
		
	}

	public HashMap<String, String> getRegs_mux() {
		return regs_mux;
	}

	public HashMap<String, String> getMux_regs() {
		return mux_regs;
	}

	public HashMap<String, OPandFUNCpair> getoPandFUNCinfo() {
		return oPandFUNCinfo;
	}

	public HashMap<String, String> getpathNO_name() {
		return pathNO_name;
	}
	
	public ArrayList<String> getfuncvaluelist(){
		return this.funcvaluelist;
	}
}
