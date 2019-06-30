package mipsCPUGen_DatapathGenerate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import mipsCPUGen_util.ExcelCellProcessor;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class generateEveryInstructionPath {
	private final static String everyInstructionFile="everyInstruction\\";
	private String datapathFile="";
	private ArrayList<DataPathInfo> pathInfoList=new ArrayList<DataPathInfo>();
	private ArrayList<String> everyInstructionPath=new ArrayList<String>();
	
	public generateEveryInstructionPath(String input){
		this.datapathFile=input;
		try {
			this.datapathProcess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<String> getEveryInstructionPath(){
		return this.everyInstructionPath;
	}
	private void datapathProcess() throws IOException {
//		HashMap<String, String> pathNO_name = inputinfo.getpathNO_name();
		@SuppressWarnings("deprecation")
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(datapathFile);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		if (xssfSheet == null) {
			System.out.println(datapathFile+" is empty!");;
		}
		String lastNum="";
		String laststage="";
		String clk;
		int inline=1;
		int pathNO=0;
		for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			if (xssfRow == null) {
				continue;
			}
			XSSFCell xssfCell0 = xssfRow.getCell(0);
			XSSFCell xssfCell1 = xssfRow.getCell(1);
			XSSFCell xssfCell2 = xssfRow.getCell(2);
			XSSFCell xssfCell3 = xssfRow.getCell(3);
			XSSFCell xssfCell4 = xssfRow.getCell(4);
			
			
			
			String datapathName=ExcelCellProcessor.getValue(xssfCell0);
			String stage=ExcelCellProcessor.getValue(xssfCell1);
			String source=ExcelCellProcessor.getValue(xssfCell2);
			String destination=ExcelCellProcessor.getValue(xssfCell3);
			String condition=ExcelCellProcessor.getValue(xssfCell4);
			
			XSSFCell writexssfCell0 = xssfRow.createCell(7);
			XSSFCell writexssfCell1 = xssfRow.createCell(8);
			if(destination.equals("!")||destination.equals("++"))
			{
				writexssfCell0.setCellValue(destination);
				writexssfCell1.setCellValue(source);
			}else
			{
				writexssfCell0.setCellValue(source);
				writexssfCell1.setCellValue(destination);
			}
			
			if(datapathName.isEmpty()||datapathName.equals("")){
				datapathName=lastNum;
				inline++;
			}else{
				inline=1;
				pathNO++;
			}
			lastNum=datapathName;
			if(stage.isEmpty()){
				stage=laststage;
			}
			laststage=stage;
			
			if(stage.equals("IF")){
//				clk="(P|P5)";
				clk="(Reset|P4)";
			}else if(stage.equals("ID")||stage.equals("IF/ID")){
				clk="P0";
			}else if(stage.equals("EX")||stage.equals("ID/EX")){
				clk="P1";
			}else if(stage.equals("MEM")||stage.equals("EX/MEM")){
				clk="P2";
			}else if(stage.equals("WB")||stage.equals("MEM/WB")){
				clk="P3";
			}/*else if(stage.equals("EXC")||stage.equals("WB/EXC")){
				clk="P4";
			}else if(stage.equals("EXC/IF")){
				clk="P5";
			}*/else if(stage.equals("WB/IF")){
				clk="P4";
			}else{
				clk=null;
				System.out.println("通路表阶段设计错误:"+rowNum);
			}
			
			if((source==null&&destination==null)||(source.isEmpty()&&destination.isEmpty())){
				DataPathInfo element=new DataPathInfo(rowNum,inline,pathNO,stage,clk,source,destination,condition,datapathName);
				pathInfoList.add(element);
			}else if((source==null&&destination!=null)||(source!=null&&destination==null)){
				System.out.println(datapathName+":"+rowNum+":数据通路源端口或者目的端口为空");
			}else if((source.isEmpty()&&!destination.isEmpty())||(!source.isEmpty()&&destination.isEmpty())){
				System.out.println(datapathName+":"+rowNum+":数据通路源端口或者目的端口为空");
			}else if(source.isEmpty()&&destination.isEmpty()){
				if(!condition.isEmpty()){
					System.out.println(datapathName+":"+rowNum+":源、目的都为空，但是控制因子不为空");
				}
			}/*else if(!stage.equals("IF")&&condition.isEmpty()){
				System.out.println(datapathNum+":"+rowNum+":控制因子为空");
			}*/else{
				DataPathInfo element=new DataPathInfo(rowNum,inline,pathNO,stage,clk,source,destination,condition,datapathName);
				pathInfoList.add(element);
			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream("0简单通路.xlsx");
			xssfWorkbook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<ArrayList<DataPathInfo>> parse(){
		return this.parse(this.pathInfoList);
	}
	public ArrayList<ArrayList<DataPathInfo>> parse(ArrayList<DataPathInfo> pathInfoList){
		ArrayList<ArrayList<DataPathInfo>> instructionDataPathInfobookList=new ArrayList<ArrayList<DataPathInfo>>();
		ArrayList<DataPathInfo> instructionListItem=new ArrayList<DataPathInfo>();
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		int lastpathnumber=-1;
		int j=0;
		String lastInsrtuction="";
		for(int i=0;i<pathInfoList.size();i++){
			DataPathInfo item=pathInfoList.get(i);
			int pathnumber=item.getPathNO();
			String filename=item.getPathName();
			String stage=item.getStage();
			String source=item.getSource();
			String destination=item.getDestination();
			String condition=item.getFactor();
			if(pathnumber==lastpathnumber){
				XSSFRow writeRow=writeSheet.createRow(j);
				XSSFCell writexssfCell0 = writeRow.createCell(0);
				XSSFCell writexssfCell1 = writeRow.createCell(1);
				XSSFCell writexssfCell2 = writeRow.createCell(2);
				XSSFCell writexssfCell3 = writeRow.createCell(3);
				XSSFCell writexssfCell4 = writeRow.createCell(4);
				writexssfCell0.setCellValue(pathnumber);
				writexssfCell1.setCellValue(stage);
				writexssfCell2.setCellValue(source);
				writexssfCell3.setCellValue(destination);
				writexssfCell4.setCellValue(condition);
				j++;
				instructionListItem.add(item);
			}else{
				if(i!=0){
					try {
						FileOutputStream outputStream = new FileOutputStream(everyInstructionFile+lastpathnumber+"_"+lastInsrtuction+".xlsx");
						muxwritebook.write(outputStream);
						outputStream.close();
						instructionDataPathInfobookList.add(instructionListItem);
						everyInstructionPath.add(everyInstructionFile+lastpathnumber+"_"+lastInsrtuction);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				instructionListItem=new ArrayList<DataPathInfo>();
				instructionListItem.add(item);
				muxwritebook=new XSSFWorkbook();
				writeSheet=muxwritebook.createSheet();
				j=0;
				XSSFRow writeRow=writeSheet.createRow(j);
				XSSFCell writexssfCell0 = writeRow.createCell(0);
				XSSFCell writexssfCell1 = writeRow.createCell(1);
				XSSFCell writexssfCell2 = writeRow.createCell(2);
				XSSFCell writexssfCell3 = writeRow.createCell(3);
				XSSFCell writexssfCell4 = writeRow.createCell(4);
				writexssfCell0.setCellValue(pathnumber);
				writexssfCell1.setCellValue(stage);
				writexssfCell2.setCellValue(source);
				writexssfCell3.setCellValue(destination);
				writexssfCell4.setCellValue(condition);
				j++;
			}
			lastpathnumber=pathnumber;
			lastInsrtuction=filename;
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(everyInstructionFile+lastpathnumber+"_"+lastInsrtuction+".xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
			instructionDataPathInfobookList.add(instructionListItem);
			everyInstructionPath.add(everyInstructionFile+lastpathnumber+"_"+lastInsrtuction);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instructionDataPathInfobookList;
	}
	
	public ArrayList<DataPathInfo> getpathInfoList(){
		return this.pathInfoList;
	}
}
