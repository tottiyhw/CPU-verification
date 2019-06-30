package mipsCPUGen_DatapathGenerate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import mipsCPUGen_ComputeUnits.ComputeComponent;
import mipsCPUGen_ComputeUnits.ComputeComponentPort;
import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_util.myComparator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataPathOutput {
	private DatapathProcess dp=null;
	private TreeMap<String, TreeMap<DataPathInfo, DataPathInfo>> muxmap=null;
	private TreeMap<String, ArrayList<DataPathInfo>> regsmap=null;
	private TreeMap<String, DataPathInfo> directConnectMap=null;
	private TreeMap<String, Integer> muxSizemap=null;
	private TreeMap<String, DataPathInfo> mergedmap=null;
	private TreeMap<String, DataPathInfo> mergedregsmap=null;
	private TreeMap<String, String> mergedDirectConnectMap=null;
	private ComputeComponetInfo info=new ComputeComponetInfo();
	private TreeMap<String, String> ctrlmap=new TreeMap<String, String>(new myComparator());
	
	public DataPathOutput(DatapathProcess dp){
		this.dp=dp;
		this.muxmap=dp.getMuxmap();
		this.regsmap=dp.getRegsmap();
		this.directConnectMap=dp.getDirectConnectMap();
		this.muxSizemap=dp.getMuxSizemap();
		this.mergedmap=dp.getMergedmap();
		this.mergedregsmap=dp.getmergedregsmap();
		this.mergedDirectConnectMap=dp.getMergedDirectConnectMap();
	}
	
	public ComputeComponetInfo getComputeComponetInfo(){
		return this.info;
	}
	public TreeMap<String, String> getCtrlExpression(){
		return this.ctrlmap;
	}
	
	public void outputSimpleDatapath(String outfile){
		ArrayList<DataPathInfo> pathInfoList=dp.getInstructionsList();
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		for(int i=0;i<pathInfoList.size();i++){
			DataPathInfo item=pathInfoList.get(i);
			String name=item.getPathName();
			int datapath=item.getPathNO();
			int line=item.getInLine();
			String stage=item.getStage();
			String clk=item.getClk();
			String source=item.getSource();
			String mux=item.getMux();
			String destination=item.getDestination();
			String ctrlname=item.getCtrlname();
			String clkpart=item.getCtrlvalueCLKpart();
			String opfuncpart=item.getCtrlvalueOPandFUNCpart();
			
			String OPpart=item.getOPpart();
			String FUNCpart=item.getFUNCpart();
			
			String factor=item.getFactor();
			XSSFRow writeRow=writeSheet.createRow(i);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell1 = writeRow.createCell(1);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell4 = writeRow.createCell(4);
			XSSFCell writexssfCell5 = writeRow.createCell(5);
			XSSFCell writexssfCell6 = writeRow.createCell(6);
			XSSFCell writexssfCell7 = writeRow.createCell(7);
			XSSFCell writexssfCell8 = writeRow.createCell(8);
			XSSFCell writexssfCell9 = writeRow.createCell(9);
			XSSFCell writexssfCell10 = writeRow.createCell(10);
			XSSFCell writexssfCell11 = writeRow.createCell(11);
			XSSFCell writexssfCell12 = writeRow.createCell(12);
			XSSFCell writexssfCell13 = writeRow.createCell(13);
			writexssfCell0.setCellValue(name);
			writexssfCell1.setCellValue(datapath);
			writexssfCell2.setCellValue(line);
			writexssfCell3.setCellValue(stage);
			if((clk==null||clk.equals(""))&&destination.equals("CU.IRFunc")){
				writexssfCell4.setCellValue("P0");
			}else{
				writexssfCell4.setCellValue(clk);
			}
			writexssfCell5.setCellValue(source);
			if(mux!=null){
				writexssfCell6.setCellValue(mux);
				ctrlname="Ctrl"+mux;
			}
			writexssfCell7.setCellValue(destination);
			if(ctrlname!=null){
				writexssfCell8.setCellValue(ctrlname);
			}
			if(factor!=null){
				writexssfCell9.setCellValue(factor);
			}
			
			if(OPpart!=null){
				writexssfCell10.setCellValue(OPpart);
			}
			if(FUNCpart!=null){
				writexssfCell11.setCellValue(FUNCpart);
			}
			
			if(opfuncpart!=null){
				writexssfCell12.setCellValue(opfuncpart);
			}
			if(clkpart!=null&&!clkpart.equals("")){
				if(opfuncpart==null){
					writexssfCell13.setCellValue(clkpart);
				}else{
					writexssfCell13.setCellValue(clkpart+"&"+opfuncpart);
				}
			}else{
				if(opfuncpart==null){
					writexssfCell13.setCellValue("");
				}else{
					writexssfCell13.setCellValue(opfuncpart);
				}
			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(outfile+"1简单通路.xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void outputSigalSimpleDatapath(String outfile){
		ArrayList<DataPathInfo> pathInfoList=dp.getInstructionsList();
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		for(int i=0;i<pathInfoList.size();i++){
			DataPathInfo item=pathInfoList.get(i);
			String name=item.getPathName();
			int datapath=item.getPathNO();
			int line=item.getInLine();
			String clk=item.getClk();
			String source=item.getSource();
			String mux=item.getMux();
			int muxchannelsize;
			if(mux!=null){
				muxchannelsize=muxSizemap.get(mux);
			}else{
				muxchannelsize=0;
			}
			String destination=item.getDestination();
			String ctrlname=item.getCtrlname();
			String clkpart=item.getCtrlvalueCLKpart();
			String opfuncpart=item.getCtrlvalueOPandFUNCpart();
			XSSFRow writeRow=writeSheet.createRow(i);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell1 = writeRow.createCell(1);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell4 = writeRow.createCell(4);
			XSSFCell writexssfCell5 = writeRow.createCell(5);
			XSSFCell writexssfCell6 = writeRow.createCell(6);
			XSSFCell writexssfCell7 = writeRow.createCell(7);
			XSSFCell writexssfCell8 = writeRow.createCell(8);
			XSSFCell writexssfCell9 = writeRow.createCell(9);
			writexssfCell9.setCellValue(name);
			writexssfCell0.setCellValue(datapath);
			writexssfCell1.setCellValue(line);
			writexssfCell2.setCellValue(clk);
			writexssfCell3.setCellValue(source);
			if(mux!=null&&muxchannelsize>1){
				writexssfCell4.setCellValue(mux);
			}
			writexssfCell5.setCellValue(destination);
			if(ctrlname!=null){
				if(destination.equals("!")||muxchannelsize>1){
					writexssfCell6.setCellValue(ctrlname);
				}
			}
			if(clkpart!=null){
				if(destination.equals("!")||muxchannelsize>1){
					writexssfCell7.setCellValue(clkpart);
				}
			}
			if(opfuncpart!=null){
				if(destination.equals("!")||muxchannelsize>1){
					writexssfCell8.setCellValue(opfuncpart);
				}
			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(outfile+"1.xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void outputSortedDatapath(String outfile){
		
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		int rowNO=0;
		
		Iterator<Entry<String, TreeMap<DataPathInfo, DataPathInfo>>> iter=muxmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>> entry = (Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>>) iter.next();
			TreeMap<DataPathInfo, DataPathInfo> infoelementitem=entry.getValue();
		
			Iterator<Entry<DataPathInfo, DataPathInfo>> it=((TreeMap<DataPathInfo, DataPathInfo>) infoelementitem).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataPathInfo, DataPathInfo> itentry = (Map.Entry<DataPathInfo, DataPathInfo>) it.next();
				DataPathInfo item=itentry.getValue();
				String name=item.getPathName();
				int datapath=item.getPathNO();
				int inline=item.getInLine();
				String clk=item.getClk();
				String source=item.getSource();
				String mux=item.getMux();
				String muxchannel=item.getMuxchannel();
				String destination=item.getDestination();
				String ctrlname=item.getCtrlname();
				String clkpart=item.getCtrlvalueCLKpart();
				String opfuncpart=item.getCtrlvalueOPandFUNCpart();
				XSSFRow writeRow=writeSheet.createRow(rowNO);
				XSSFCell writexssfCell0 = writeRow.createCell(0);
				XSSFCell writexssfCell1 = writeRow.createCell(1);
				XSSFCell writexssfCell2 = writeRow.createCell(2);
				XSSFCell writexssfCell3 = writeRow.createCell(3);
				XSSFCell writexssfCell4 = writeRow.createCell(4);
				XSSFCell writexssfCell5 = writeRow.createCell(5);
				XSSFCell writexssfCell6 = writeRow.createCell(6);
				XSSFCell writexssfCell7 = writeRow.createCell(7);
				XSSFCell writexssfCell8 = writeRow.createCell(8);
				XSSFCell writexssfCell9 = writeRow.createCell(9);
				XSSFCell writexssfCell10 = writeRow.createCell(10);
				writexssfCell10.setCellValue(name);
				writexssfCell0.setCellValue(datapath);
				writexssfCell1.setCellValue(inline);
				if((clk==null||clk.equals(""))&&destination.equals("CU.IRFunc")){
					writexssfCell2.setCellValue("P0");
				}else{
					writexssfCell2.setCellValue(clk);
				}
				writexssfCell3.setCellValue(source);
				if(mux!=null){
					writexssfCell4.setCellValue(mux);
					writexssfCell5.setCellValue(muxchannel);
				}
				writexssfCell6.setCellValue(destination);
				if(ctrlname!=null){
					writexssfCell7.setCellValue(ctrlname);
				}
				if(clkpart!=null){
					writexssfCell8.setCellValue(clkpart);
				}
				if(opfuncpart!=null){
					writexssfCell9.setCellValue(opfuncpart);
				}
				rowNO++;
			}
		}
		
		Iterator<Entry<String, ArrayList<DataPathInfo>>> iterregs=regsmap.entrySet().iterator();
		while (iterregs.hasNext()) {
			Map.Entry<String, ArrayList<DataPathInfo>> entry = (Map.Entry<String, ArrayList<DataPathInfo>>) iterregs.next();
			ArrayList<DataPathInfo> ctrllist=entry.getValue();
			for(int i=0;i<ctrllist.size();i++){
				DataPathInfo item=ctrllist.get(i);
				String name=item.getPathName();
				int datapath=item.getPathNO();
				int inline=item.getInLine();
				String clk=item.getClk();
				String source=item.getSource();
				String mux=item.getMux();
				String destination=item.getDestination();
				String ctrlname=item.getCtrlname();
				String clkpart=item.getCtrlvalueCLKpart();
				String opfuncpart=item.getCtrlvalueOPandFUNCpart();
				XSSFRow writeRow=writeSheet.createRow(rowNO);
				XSSFCell writexssfCell0 = writeRow.createCell(0);
				XSSFCell writexssfCell1 = writeRow.createCell(1);
				XSSFCell writexssfCell2 = writeRow.createCell(2);
				XSSFCell writexssfCell3 = writeRow.createCell(3);
				XSSFCell writexssfCell4 = writeRow.createCell(4);
				XSSFCell writexssfCell6 = writeRow.createCell(6);
				XSSFCell writexssfCell7 = writeRow.createCell(7);
				XSSFCell writexssfCell8 = writeRow.createCell(8);
				XSSFCell writexssfCell9 = writeRow.createCell(9);
				XSSFCell writexssfCell10 = writeRow.createCell(10);
				writexssfCell10.setCellValue(name);
				writexssfCell0.setCellValue(datapath);
				writexssfCell1.setCellValue(inline);
				writexssfCell2.setCellValue(clk);
				writexssfCell3.setCellValue(source);
				if(mux!=null){
					writexssfCell4.setCellValue(mux);
				}
				writexssfCell6.setCellValue(destination);
				if(ctrlname!=null){
					writexssfCell7.setCellValue(ctrlname);
				}
				if(clkpart!=null){
					writexssfCell8.setCellValue(clkpart);
				}
				if(opfuncpart!=null){
					writexssfCell9.setCellValue(opfuncpart);
				}
				rowNO++;
			}
		}
		
		Iterator<Entry<String, DataPathInfo>> iterdirect=directConnectMap.entrySet().iterator();
		while (iterdirect.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iterdirect.next();
			DataPathInfo element=entry.getValue();
			String name=element.getPathName();
			int pathNO=element.getPathNO();
			int inline=element.getInLine();
			String clk=element.getClk();
			String source=element.getSource();
			String destination=element.getDestination();
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell1 = writeRow.createCell(1);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell6 = writeRow.createCell(6);
			XSSFCell writexssfCell10 = writeRow.createCell(10);
			writexssfCell10.setCellValue(name);
			writexssfCell0.setCellValue(pathNO);
			writexssfCell1.setCellValue(inline);
			writexssfCell2.setCellValue(clk);
			writexssfCell3.setCellValue(source);
			writexssfCell6.setCellValue(destination);
			rowNO++;
		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(outfile+"2简单通路——排序.xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public void outputSigalSortedDatapath(String outfile){
		
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		int rowNO=0;
		
		Iterator<Entry<String, TreeMap<DataPathInfo, DataPathInfo>>> iter=muxmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>> entry = (Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>>) iter.next();
			TreeMap<DataPathInfo, DataPathInfo> infoelementitem=entry.getValue();
		
			Iterator<Entry<DataPathInfo, DataPathInfo>> it=((TreeMap<DataPathInfo, DataPathInfo>) infoelementitem).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataPathInfo, DataPathInfo> itentry = (Map.Entry<DataPathInfo, DataPathInfo>) it.next();
				DataPathInfo item=itentry.getValue();
				String name=item.getPathName();
				int datapath=item.getPathNO();
				int inline=item.getInLine();
				String clk=item.getClk();
				String source=item.getSource();
				String mux=item.getMux();
				int muxchannelsize=muxSizemap.get(mux);
				String muxchannel=item.getMuxchannel();
				String destination=item.getDestination();
				String ctrlname=item.getCtrlname();
				String clkpart=item.getCtrlvalueCLKpart();
				String opfuncpart=item.getCtrlvalueOPandFUNCpart();
				XSSFRow writeRow=writeSheet.createRow(rowNO);
				XSSFCell writexssfCell0 = writeRow.createCell(0);
				XSSFCell writexssfCell1 = writeRow.createCell(1);
				XSSFCell writexssfCell2 = writeRow.createCell(2);
				XSSFCell writexssfCell3 = writeRow.createCell(3);
				XSSFCell writexssfCell4 = writeRow.createCell(4);
				XSSFCell writexssfCell5 = writeRow.createCell(5);
				XSSFCell writexssfCell6 = writeRow.createCell(6);
				XSSFCell writexssfCell7 = writeRow.createCell(7);
				XSSFCell writexssfCell8 = writeRow.createCell(8);
				XSSFCell writexssfCell9 = writeRow.createCell(9);
				XSSFCell writexssfCell10 = writeRow.createCell(10);
				writexssfCell10.setCellValue(name);
				writexssfCell0.setCellValue(datapath);
				writexssfCell1.setCellValue(inline);
				writexssfCell2.setCellValue(clk);
				writexssfCell3.setCellValue(source);
				if(mux!=null&&muxchannelsize>1){
					writexssfCell4.setCellValue(mux);
					writexssfCell5.setCellValue(muxchannel);
				}
				writexssfCell6.setCellValue(destination);
				if(ctrlname!=null){
					if(destination.equals("!")||muxchannelsize>1){
						writexssfCell7.setCellValue(ctrlname);
					}
				}
				if(clkpart!=null){
					if(destination.equals("!")||muxchannelsize>1){
						writexssfCell8.setCellValue(clkpart);
					}
				}
				if(opfuncpart!=null){
					if(destination.equals("!")||muxchannelsize>1){
						writexssfCell9.setCellValue(opfuncpart);
					}
				}
				rowNO++;
			}
		}
		
		Iterator<Entry<String, ArrayList<DataPathInfo>>> iterregs=regsmap.entrySet().iterator();
		while (iterregs.hasNext()) {
			Map.Entry<String, ArrayList<DataPathInfo>> entry = (Map.Entry<String, ArrayList<DataPathInfo>>) iterregs.next();
			ArrayList<DataPathInfo> ctrllist=entry.getValue();
			for(int i=0;i<ctrllist.size();i++){
				DataPathInfo item=ctrllist.get(i);
				String name=item.getPathName();
				int datapath=item.getPathNO();
				int inline=item.getInLine();
				String clk=item.getClk();
				String source=item.getSource();
				String mux=item.getMux();
				String destination=item.getDestination();
				String ctrlname=item.getCtrlname();
				String clkpart=item.getCtrlvalueCLKpart();
				String opfuncpart=item.getCtrlvalueOPandFUNCpart();
				XSSFRow writeRow=writeSheet.createRow(rowNO);
				XSSFCell writexssfCell0 = writeRow.createCell(0);
				XSSFCell writexssfCell1 = writeRow.createCell(1);
				XSSFCell writexssfCell2 = writeRow.createCell(2);
				XSSFCell writexssfCell3 = writeRow.createCell(3);
				XSSFCell writexssfCell4 = writeRow.createCell(4);
				XSSFCell writexssfCell6 = writeRow.createCell(6);
				XSSFCell writexssfCell7 = writeRow.createCell(7);
				XSSFCell writexssfCell8 = writeRow.createCell(8);
				XSSFCell writexssfCell9 = writeRow.createCell(9);
				XSSFCell writexssfCell10 = writeRow.createCell(10);
				writexssfCell10.setCellValue(name);
				writexssfCell0.setCellValue(datapath);
				writexssfCell1.setCellValue(inline);
				writexssfCell2.setCellValue(clk);
				writexssfCell3.setCellValue(source);
				if(mux!=null){
					writexssfCell4.setCellValue(mux);
				}
				writexssfCell6.setCellValue(destination);
				if(ctrlname!=null){
					writexssfCell7.setCellValue(ctrlname);
				}
				if(clkpart!=null){
					writexssfCell8.setCellValue(clkpart);
				}
				if(opfuncpart!=null){
					writexssfCell9.setCellValue(opfuncpart);
				}
				rowNO++;
			}
		}
		
		Iterator<Entry<String, DataPathInfo>> iterdirect=directConnectMap.entrySet().iterator();
		while (iterdirect.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iterdirect.next();
			DataPathInfo element=entry.getValue();
			String name=element.getPathName();
			int pathNO=element.getPathNO();
			int inline=element.getInLine();
			String clk=element.getClk();
			String source=element.getSource();
			String destination=element.getDestination();
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell1 = writeRow.createCell(1);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell6 = writeRow.createCell(6);
			XSSFCell writexssfCell10 = writeRow.createCell(10);
			writexssfCell10.setCellValue(name);
			writexssfCell0.setCellValue(pathNO);
			writexssfCell1.setCellValue(inline);
			writexssfCell2.setCellValue(clk);
			writexssfCell3.setCellValue(source);
			writexssfCell6.setCellValue(destination);
			rowNO++;
		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(outfile+"2.xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void outputDirectMuxSortedDatapath(String outfile){
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		int rowNO=0;
		
		Iterator<Entry<String, TreeMap<DataPathInfo, DataPathInfo>>> iter=muxmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>> entry = (Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>>) iter.next();
			String muxname=entry.getKey();
			TreeMap<DataPathInfo, DataPathInfo> infoelementitem=entry.getValue();
			Iterator<Entry<DataPathInfo, DataPathInfo>> it=((TreeMap<DataPathInfo, DataPathInfo>) infoelementitem).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataPathInfo, DataPathInfo> itentry = (Map.Entry<DataPathInfo, DataPathInfo>) it.next();
			
				DataPathInfo item=itentry.getValue();
				String name=item.getPathName();
				int datapath=item.getPathNO();
				int inline=item.getInLine();
				String clk=item.getClk();
				String source=item.getSource();
				String mux=item.getMux();
				
				int directmux=muxSizemap.get(muxname);
				if(directmux==1){
					String directmuxkey=item.getPathNO()+"_"+item.getInLine();
					directConnectMap.put(directmuxkey, item);
					continue;
				}
				String muxchannel=item.getMuxchannel();
				String destination=item.getDestination();
				String ctrlname=item.getCtrlname();
				String clkpart=item.getCtrlvalueCLKpart();
				String opfuncpart=item.getCtrlvalueOPandFUNCpart();
				XSSFRow writeRow=writeSheet.createRow(rowNO);
				XSSFCell writexssfCell0 = writeRow.createCell(0);
				XSSFCell writexssfCell1 = writeRow.createCell(1);
				XSSFCell writexssfCell2 = writeRow.createCell(2);
				XSSFCell writexssfCell3 = writeRow.createCell(3);
				XSSFCell writexssfCell4 = writeRow.createCell(4);
				XSSFCell writexssfCell5 = writeRow.createCell(5);
				XSSFCell writexssfCell6 = writeRow.createCell(6);
				XSSFCell writexssfCell7 = writeRow.createCell(7);
				XSSFCell writexssfCell8 = writeRow.createCell(8);
				XSSFCell writexssfCell9 = writeRow.createCell(9);
				XSSFCell writexssfCell10 = writeRow.createCell(10);
				writexssfCell10.setCellValue(name);
				writexssfCell0.setCellValue(datapath);
				writexssfCell1.setCellValue(inline);
				writexssfCell2.setCellValue(clk);
				writexssfCell3.setCellValue(source);
				if(mux!=null&&directmux>1){
					writexssfCell4.setCellValue(mux);
					writexssfCell5.setCellValue(muxchannel);
				}
				writexssfCell6.setCellValue(destination);
				if(ctrlname!=null&&directmux>1){
					writexssfCell7.setCellValue(ctrlname);
				}
				if(clkpart!=null&&directmux>1){
					writexssfCell8.setCellValue(clkpart);
				}
				if(opfuncpart!=null&&directmux>1){
					writexssfCell9.setCellValue(opfuncpart);
				}
				rowNO++;
			}
		}
		
		Iterator<Entry<String, ArrayList<DataPathInfo>>> iterregs=regsmap.entrySet().iterator();
		while (iterregs.hasNext()) {
			Map.Entry<String, ArrayList<DataPathInfo>> entry = (Map.Entry<String, ArrayList<DataPathInfo>>) iterregs.next();
			ArrayList<DataPathInfo> ctrllist=entry.getValue();
			for(int i=0;i<ctrllist.size();i++){
				DataPathInfo item=ctrllist.get(i);
				String name=item.getPathName();
				int datapath=item.getPathNO();
				int inline=item.getInLine();
				String clk=item.getClk();
				String source=item.getSource();
				String mux=item.getMux();
				String destination=item.getDestination();
				String ctrlname=item.getCtrlname();
				String clkpart=item.getCtrlvalueCLKpart();
				String opfuncpart=item.getCtrlvalueOPandFUNCpart();
				XSSFRow writeRow=writeSheet.createRow(rowNO);
				XSSFCell writexssfCell0 = writeRow.createCell(0);
				XSSFCell writexssfCell1 = writeRow.createCell(1);
				XSSFCell writexssfCell2 = writeRow.createCell(2);
				XSSFCell writexssfCell3 = writeRow.createCell(3);
				XSSFCell writexssfCell4 = writeRow.createCell(4);
				XSSFCell writexssfCell6 = writeRow.createCell(6);
				XSSFCell writexssfCell7 = writeRow.createCell(7);
				XSSFCell writexssfCell8 = writeRow.createCell(8);
				XSSFCell writexssfCell9 = writeRow.createCell(9);
				XSSFCell writexssfCell10 = writeRow.createCell(10);
				writexssfCell10.setCellValue(name);
				writexssfCell0.setCellValue(datapath);
				writexssfCell1.setCellValue(inline);
				writexssfCell2.setCellValue(clk);
				writexssfCell3.setCellValue(source);
				if(mux!=null){
					writexssfCell4.setCellValue(mux);
				}
				writexssfCell6.setCellValue(destination);
				if(ctrlname!=null){
					writexssfCell7.setCellValue(ctrlname);
				}
				if(clkpart!=null){
					writexssfCell8.setCellValue(clkpart);
				}
				if(opfuncpart!=null){
					writexssfCell9.setCellValue(opfuncpart);
				}
				rowNO++;
			}
		}
		
		Iterator<Entry<String, DataPathInfo>> iterdirect=directConnectMap.entrySet().iterator();
		while (iterdirect.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iterdirect.next();
			DataPathInfo element=entry.getValue();
			String name=element.getPathName();
			int pathNO=element.getPathNO();
			int inline=element.getInLine();
			String clk=element.getClk();
			String source=element.getSource();
			String destination=element.getDestination();
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell1 = writeRow.createCell(1);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell6 = writeRow.createCell(6);
			XSSFCell writexssfCell10 = writeRow.createCell(10);
			writexssfCell10.setCellValue(name);
			writexssfCell0.setCellValue(pathNO);
			writexssfCell1.setCellValue(inline);
			writexssfCell2.setCellValue(clk);
			writexssfCell3.setCellValue(source);
			writexssfCell6.setCellValue(destination);
			rowNO++;
		}
		
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(outfile+"2_1.xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int outputSigalMergedMap(String outfile){
		int returnvalue=1;
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		int rowNO=0;
		
		Iterator<Entry<String, DataPathInfo>> iter=mergedmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iter.next();
			DataPathInfo infoelement=entry.getValue();

			String source=infoelement.getSource();
			String destination=infoelement.getDestination();
			String muxchannel=infoelement.getMuxchannel();
			String mux=muxchannel.substring(0, muxchannel.indexOf("_"));
			int muxchannelsize=muxSizemap.get(mux);
			String ctrlname=infoelement.getCtrlname();
			ArrayList<CtrlPair> ctrllist=infoelement.getCtrlPair();
			String ctrlvalue="";
			for(int j=0;j<ctrllist.size();j++){
				CtrlPair pairitem=ctrllist.get(j);
				String clk=pairitem.getClk();
				String opfunc=pairitem.getOpAndfunc();
				if(clk!=null&&opfunc!=null){
					if(j==0){
						ctrlvalue="("+clk+"&"+opfunc+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+"&"+opfunc+")";
					}
				}else if(clk!=null&&opfunc==null){
					if(j==0){
						ctrlvalue="("+clk+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+")";
					}
				}else if(clk.equals("")){
					if(j==0){
						ctrlvalue="("+opfunc+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+opfunc+")";
					}
				}/*else{
					ctrlvalue=ctrlvalue;
				} */
			}
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell1 = writeRow.createCell(1);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell4 = writeRow.createCell(4);
			writexssfCell0.setCellValue(source);
			if(muxchannel!=null&&muxchannelsize>1){
				writexssfCell1.setCellValue(muxchannel);
			}
			writexssfCell2.setCellValue(destination);
			if(ctrlname!=null&&muxchannelsize>1){
				writexssfCell3.setCellValue(ctrlname);
			}
			if(muxchannelsize>1){
				writexssfCell4.setCellValue(ctrlvalue);
			}
			rowNO++;
			ctrlmap.put(ctrlname, ctrlvalue);
			if(!this.checkvalidSourceAndDestination(source, destination)){
				System.out.println("-------------"+infoelement.getPathNO()+":::"+infoelement.getPathName()+":::"+infoelement.getInLine()+":::"+infoelement.getLineNO());
				System.out.println("-------------"+source+":"+destination+"-------------");
				returnvalue=0;
			}
		}
		
		Iterator<Entry<String, DataPathInfo>> iterregs=mergedregsmap.entrySet().iterator();
		while (iterregs.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iterregs.next();
			DataPathInfo infoelement=entry.getValue();
			String source=infoelement.getSource();
			String destination=infoelement.getDestination();
			String ctrlname=infoelement.getCtrlname();
			ArrayList<CtrlPair> ctrllist=infoelement.getCtrlPair();
			String ctrlvalue="";
			for(int j=0;j<ctrllist.size();j++){
				CtrlPair pairitem=ctrllist.get(j);
				String clk=pairitem.getClk();
				String opfunc=pairitem.getOpAndfunc();
				if(clk!=null&&opfunc!=null){
					if(j==0){
						ctrlvalue="("+clk+"&"+opfunc+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+"&"+opfunc+")";
					}
				}else if(clk!=null&&opfunc==null){
					if(j==0){
						ctrlvalue="("+clk+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+")";
					}
				}/*else{
					ctrlvalue=ctrlvalue;
				}*/
			}
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell4 = writeRow.createCell(4);
			writexssfCell0.setCellValue(source);
			writexssfCell2.setCellValue(destination);
			if(ctrlname!=null){
				writexssfCell3.setCellValue(ctrlname);
			}
			writexssfCell4.setCellValue(ctrlvalue);
			rowNO++;

			ctrlmap.put(ctrlname, ctrlvalue);
			if(!this.checkvalidSourceAndRegsctrl(source, destination)){
				System.out.println("-------------"+infoelement.getPathNO()+":::"+infoelement.getPathName()+":::"+infoelement.getInLine()+":::"+infoelement.getLineNO());
				returnvalue=0;
			}
		}
		Iterator<Entry<String, String>> iterdirect=mergedDirectConnectMap.entrySet().iterator();
		while (iterdirect.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterdirect.next();
			String source=entry.getKey();
			String destination=entry.getValue();
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			writexssfCell0.setCellValue(source);
			writexssfCell2.setCellValue(destination);
			rowNO++;
			
			if(!this.checkvalidSourceAndDestination(source, destination)){
				System.out.println("-------------"+source+":::"+destination+"直连左右端出错");
				returnvalue=0;
			}
		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(outfile+"3.xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnvalue;
	}
	public int outputMergedMap(String outfile){
		int returnvalue=1;
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		int rowNO=1;
		
		XSSFRow writeRow=writeSheet.createRow(0);
		XSSFCell writexssfCell0 = writeRow.createCell(0);
		XSSFCell writexssfCell1 = writeRow.createCell(1);
		XSSFCell writexssfCell2 = writeRow.createCell(2);
		XSSFCell writexssfCell3 = writeRow.createCell(3);
		XSSFCell writexssfCell4 = writeRow.createCell(4);
		writexssfCell0.setCellValue("数据源端");
		writexssfCell1.setCellValue("多路选择器通道");
		writexssfCell2.setCellValue("目的端口");
		writexssfCell3.setCellValue("控制信号名称");
		writexssfCell4.setCellValue("控制信号值表达式");
		
		Iterator<Entry<String, DataPathInfo>> iter=mergedmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iter.next();
			DataPathInfo infoelement=entry.getValue();

			String source=infoelement.getSource();
			String destination=infoelement.getDestination();
			String muxchannel=infoelement.getMuxchannel();
			String ctrlname=infoelement.getCtrlname();
			ArrayList<CtrlPair> ctrllist=infoelement.getCtrlPair();
			String ctrlvalue="";
			for(int j=0;j<ctrllist.size();j++){
				CtrlPair pairitem=ctrllist.get(j);
				String clk=pairitem.getClk();
				String opfunc=pairitem.getOpAndfunc();
				if(clk!=null&&opfunc!=null){
					if(j==0){
						if(clk.equals("")){
							ctrlvalue="("+opfunc+")";
						}else{
							ctrlvalue="("+clk+"&"+opfunc+")";
						}
					}else{
						if(clk.equals("")){
							ctrlvalue=ctrlvalue+"|("+opfunc+")";
						}else{
							ctrlvalue=ctrlvalue+"|("+clk+"&"+opfunc+")";
						}
					}
				}else if(clk!=null&&opfunc==null){
					if(j==0){
						ctrlvalue="("+clk+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+")";
					}
				}/*else{
					ctrlvalue=ctrlvalue;
				}*/
			}
			writeRow=writeSheet.createRow(rowNO);
			writexssfCell0 = writeRow.createCell(0);
			writexssfCell1 = writeRow.createCell(1);
			writexssfCell2 = writeRow.createCell(2);
			writexssfCell3 = writeRow.createCell(3);
			writexssfCell4 = writeRow.createCell(4);
			writexssfCell0.setCellValue(source);
			if(muxchannel!=null){
				writexssfCell1.setCellValue(muxchannel);
			}
			writexssfCell2.setCellValue(destination);
			if(ctrlname!=null){
				writexssfCell3.setCellValue(ctrlname);
			}
			writexssfCell4.setCellValue(ctrlvalue);
			rowNO++;
			ctrlmap.put(ctrlname, ctrlvalue);
			if(!this.checkvalidSourceAndDestination(source, destination)){
				System.out.println("-------------"+infoelement.getPathNO()+":::"+infoelement.getPathName()+":::"+infoelement.getInLine()+":::"+infoelement.getLineNO());
				System.out.println("-------------"+source+":"+destination+"-------------");
				returnvalue=0;
			}
		}
		
		Iterator<Entry<String, DataPathInfo>> iterregs=mergedregsmap.entrySet().iterator();
		while (iterregs.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iterregs.next();
			DataPathInfo infoelement=entry.getValue();
			String source=infoelement.getSource();
			String destination=infoelement.getDestination();
			String ctrlname=infoelement.getCtrlname();
			ArrayList<CtrlPair> ctrllist=infoelement.getCtrlPair();
			String ctrlvalue="";
			for(int j=0;j<ctrllist.size();j++){
				CtrlPair pairitem=ctrllist.get(j);
				String clk=pairitem.getClk();
				String opfunc=pairitem.getOpAndfunc();
				if(clk!=null&&opfunc!=null){
					if(j==0){
						ctrlvalue="("+clk+"&"+opfunc+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+"&"+opfunc+")";
					}
				}else if(clk!=null&&opfunc==null){
					if(j==0){
						ctrlvalue="("+clk+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+")";
					}
				}/*else{
					ctrlvalue=ctrlvalue;
				}*/
			}
			writeRow=writeSheet.createRow(rowNO);
			writexssfCell0 = writeRow.createCell(0);
			writexssfCell1 = writeRow.createCell(1);
			writexssfCell2 = writeRow.createCell(2);
			writexssfCell3 = writeRow.createCell(3);
			writexssfCell4 = writeRow.createCell(4);
			writexssfCell0.setCellValue(source);
			writexssfCell2.setCellValue(destination);
			if(ctrlname!=null){
				writexssfCell3.setCellValue(ctrlname);
			}
			writexssfCell4.setCellValue(ctrlvalue);
			rowNO++;

			ctrlmap.put(ctrlname, ctrlvalue);
			if(!this.checkvalidSourceAndRegsctrl(source, destination)){
				System.out.println("-------------"+infoelement.getPathNO()+":::"+infoelement.getPathName()+":::"+infoelement.getInLine()+":::"+infoelement.getLineNO());
				returnvalue=0;
			}
		}
		Iterator<Entry<String, String>> iterdirect=mergedDirectConnectMap.entrySet().iterator();
		while (iterdirect.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterdirect.next();
			String source=entry.getKey();
			String destination=entry.getValue();
			writeRow=writeSheet.createRow(rowNO);
			writexssfCell0 = writeRow.createCell(0);
			writexssfCell2 = writeRow.createCell(2);
			writexssfCell0.setCellValue(source);
			writexssfCell2.setCellValue(destination);
			rowNO++;
			
			if(!this.checkvalidSourceAndDestination(source, destination)){
				System.out.println("-------------"+source+":::"+destination+"直连左右端出错");
				returnvalue=0;
			}
		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(outfile+"3简单通路合并——CPU通路.xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnvalue;
	}
	
	private boolean checkvalidSourceAndDestination(String source,String destination){
		int sourcesep=source.indexOf(".");
		int dessep=destination.indexOf(".");
		int sourcesize=-1;
		int dessize=-1;
		
		if(source.startsWith("{")&&source.endsWith("}")){		//{，}表达式
			String sourcewithout=source.substring(1, source.length()-1);
			String[] everpart=sourcewithout.split(",");
			int size=0;
			for(int i=0;i<everpart.length;i++){
				String temp=everpart[i];
				temp=temp.trim();
				if(temp.contains("'")){
					size=size+Integer.parseInt(temp.substring(0, temp.indexOf("'")));
				}else if(temp.endsWith("]")&&temp.indexOf("[")!=-1){
					int portsep=temp.indexOf("[");
					int unitsep=temp.indexOf(".");
					int sizesep=temp.indexOf(":");
					if(unitsep==-1){
						System.out.println(source+":::"+destination+"------左端没有指定端口");
						return false;
					}
					String unittemp=temp.substring(0, unitsep);
					String porttemp=temp.substring(unitsep+1,portsep);
					if(sizesep==-1){
//						sizetemp=temp.substring(portsep+1, temp.length()-1);
						size=size+1;
					}else{
						String sizetemp1=temp.substring(portsep+1, sizesep);
						String sizetemp2=temp.substring(sizesep+1, temp.length()-1);
						size=size+Integer.parseInt(sizetemp1)-Integer.parseInt(sizetemp2)+1;
					}
					ComputeComponent cctemp=info.getComputeUnitMap().get(unittemp);
					if(cctemp.getPort(porttemp).getInOut().equals("output")){
						if(info.checkValidPortAndUnit(unittemp, porttemp)){
							continue;
						}else{
							System.out.println(unittemp+":::"+porttemp+"------不存在这样的部件或端口");
							return false;
						}
					}else{
						System.out.println(unittemp+":::"+porttemp+"------端口在左边必须是输出端口");
						return false;
					}
				}else{
					int unitsep=temp.indexOf(".");
					if(unitsep==-1){
						System.out.println(source+":::"+destination+"------左端没有指定端口");
						return false;
					}
					String unittemp=temp.substring(0, unitsep);
					String porttemp=temp.substring(unitsep+1,temp.length());
					if(info.getPortSize(unittemp, porttemp)==-1){
						return false;
					}
					size=size+info.getPortSize(unittemp, porttemp);
					ComputeComponent cctemp=info.getComputeUnitMap().get(unittemp);
					if(cctemp==null){
						System.out.println(unittemp+":部件不存在");
						return false;
					}
					if(cctemp.getPort(porttemp)==null){
						System.out.println(unittemp+":"+unittemp+":端口不存在");
						return false;
					}
					if(cctemp.getPort(porttemp).getInOut().equals("output")){
						if(info.checkValidPortAndUnit(unittemp, porttemp)){
							continue;
						}else{
							System.out.println(unittemp+":::"+porttemp+"------不存在这样的部件或端口");
							return false;
						}
					}else{
						System.out.println(unittemp+":::"+porttemp+"------端口在左边必须是输出端口");
						return false;
					}
				}
			}
			sourcesize=size;
		}else if(source.contains("'")){//数字
			sourcesize=Integer.parseInt(source.substring(0, source.indexOf("'")));
		}else if(source.endsWith("]")&&source.indexOf("[")!=-1){
			int portsep=source.indexOf("[");
			int unitsep=source.indexOf(".");
			int sizesep=source.indexOf(":");
			if(sourcesep==-1){
				System.out.println(source+":::"+destination+"------左端没有指定端口");
				return false;
			}
			String unittemp=source.substring(0, unitsep);
			String porttemp=source.substring(unitsep+1,portsep);
			if(sizesep==-1){
				sourcesize=1;
			}else{
				String sizetemp1=source.substring(portsep+1, sizesep);
				String sizetemp2=source.substring(sizesep+1, source.length()-1);
				sourcesize=Integer.parseInt(sizetemp1)-Integer.parseInt(sizetemp2)+1;
			}
			ComputeComponent cctemp=info.getComputeUnitMap().get(unittemp);
			if(!info.checkValidPortAndUnit(unittemp, porttemp)){
				System.out.println(unittemp+":::"+porttemp+"------不存在这样的部件或端口");
				return false;
			}else{
				if(cctemp.getPort(porttemp).getInOut().equals("input")){
					System.out.println(unittemp+":::"+porttemp+"------端口在左边必须是输出端口");
					return false;
				}
			}
		}else{
			if(sourcesep==-1){
				System.out.println(source+":::"+destination+"------左端没有指定端口");
				return false;
			}
			String sourceunit=source.substring(0, sourcesep);
			String sourceport=source.substring(sourcesep+1);
			sourcesize=info.getPortSize(sourceunit, sourceport);
			ComputeComponent cctemp=info.getComputeUnitMap().get(sourceunit);
			if(!info.checkValidPortAndUnit(sourceunit, sourceport)){
				System.out.println(sourceunit+":::"+sourceport+"------不存在这样的部件或端口");
				return false;
			}else{
				if(cctemp.getPort(sourceport).getInOut().equals("input")){
					System.out.println(sourceunit+":::"+sourceport+"------端口在左边必须是输出端口");
					return false;
				}
			}
		}
		
		if(dessep==-1){
			System.out.println(source+":::"+destination+"------右端没有指定端口");
			return false;
		}
		if(destination.contains("'")||destination.contains("{")||destination.contains("}")||destination.contains("[")||
				destination.contains("]")||destination.contains(",")){
			System.out.println(source+":::"+destination+"------右端格式错误，不能包含：'、{、}、【、】、，等字符");
			return false;
		}
		String destunit=destination.substring(0, dessep);
		String destport=destination.substring(dessep+1);
		ComputeComponent cctemp=info.getComputeUnitMap().get(destunit);
		if(cctemp==null){
			System.out.println(source+":::"+destination+"------不存在右端的部件");
			return false;
		}else if(destunit.equals("CU")){
			ComputeComponentPort cp=cctemp.getPort(destport);
			String connectinstance=source.replaceAll("\\.", "_");
			if(cp==null){
				System.out.println("waring::::::"+source+":::"+destination+"------CU自动添加了输入端口定义："+destination);
				info.addAndSetCUPort(destport, "input","data",connectinstance, sourcesize);
				return true;
			}else{
				dessize=info.getPortSize(destunit, destport);
				if(sourcesize==dessize){
					info.setPortAndUnit(destunit, destport,connectinstance);
					return true;
				}else{
					System.out.println(source+":::"+destination+"------左右端端口宽度不匹配");
					System.out.println(sourcesize+":"+dessize);
					return false;
				}
			}
		}else{
			ComputeComponentPort cp=cctemp.getPort(destport);
			if(cp==null){
				System.out.println(source+":::"+destination+"------不存在右端的端口");
				return false;
			}else{
				if(cp.getInOut().equals("input")){
					dessize=cp.getSize();
					if(sourcesize==dessize){
						String connectinstance=source.replaceAll("\\.", "_");
						cp.setAppeared(true);
						cp.setPortInstance(connectinstance);
						return true;
					}else{
						System.out.println(source+":::"+destination+"------左右端端口宽度不匹配");
						return false;
					}
				}else{
					System.out.println(source+":::"+destination+"------右端的端口为输出端口，不能出现在右端");
					return false;
				}
			}
		}
	}
		
	/*
	private boolean checkvalidSourceAndDestination(String source,String destination){
		int sourcesep=source.indexOf(".");
		int dessep=destination.indexOf(".");
		int sourcesize=-1;
		int dessize=-1;
		if(source.contains("'")){		//数字
			sourcesize=Integer.parseInt(source.substring(0, source.indexOf("'")));
			if(destination.contains("'")){
				System.out.println(source+":::"+destination+"------右端不能包含常数");
				return false;
			}else if(destination.startsWith("{")){
				System.out.println(source+":::"+destination+"------右端不能是位连接符合类型");
				return false;
			}else if(destination.contains("[")){
				System.out.println(source+":::"+destination+"------右端不能是位选择表达式");
				return false;
			}else{
				sourcesize=Integer.parseInt(source.substring(0,sourcesep));
				String desunit=destination.substring(0, dessep);
				String desport=destination.substring(dessep+1);
				if(desport.equals("CU")){
					info.addAndSetCUPort(desport, "input", sourcesize);
					return true;
				}else{
					dessize=info.getPortSize(desunit, desport);
					if(sourcesize==dessize){
						if(info.setPortAndUnit(desunit, desport,source)){
							return true;
						}else{
							System.out.println(desunit+":::"+desport+"------不存在这样的部件或端口");
							return false;
						}
					}else{
						System.out.println(source+":::"+destination+"------左右端数据宽度不匹配");
						return false;
					}
				}
			}
		}else if(source.startsWith("{")&&source.endsWith("}")){		//{，}表达式
			String sourcewithout=source.substring(1, source.length()-1);
			String[] everpart=sourcewithout.split(",");
			int size=0;
			for(int i=0;i<everpart.length;i++){
				String temp=everpart[i];
				if(temp.contains("'")){
					size=size+Integer.parseInt(temp.substring(0, temp.indexOf("'")));
				}else{
					if(temp.endsWith("]")&&temp.indexOf("[")!=-1){
						int portsep=temp.indexOf("[");
						int unitsep=temp.indexOf(".");
						int sizesep=temp.indexOf(":");
						String unittemp=temp.substring(0, unitsep);
						String porttemp=temp.substring(unitsep+1,portsep);
						String sizetemp;
						if(sizesep==-1){
//							sizetemp=temp.substring(portsep+1, temp.length()-1);
							size=size+1;
						}else{
							String sizetemp1=temp.substring(portsep+1, sizesep);
							String sizetemp2=temp.substring(sizesep+1, temp.length()-1);
							size=size+Integer.parseInt(sizetemp2)-Integer.parseInt(sizetemp1)+1;
						}
						ComputeComponent cctemp=info.getComputeUnitMap().get(unittemp);
						if(cctemp.getPort(porttemp).getInOut().equals("output")){
							String connectinstance=unittemp+"_"+porttemp;
							if(info.setPortAndUnit(unittemp, porttemp,connectinstance)){
								continue;
							}else{
								System.out.println(unittemp+":::"+porttemp+"------不存在这样的部件或端口");
								return false;
							}
						}else{
							System.out.println(unittemp+":::"+porttemp+"------端口在左边必须是输出端口");
							return false;
						}
					}else{
						int unitsep=temp.indexOf(".");
						String unittemp=temp.substring(0, unitsep);
						String porttemp=temp.substring(unitsep+1,temp.length()-1);
						size=size+info.getPortSize(unittemp, porttemp);
						ComputeComponent cctemp=info.getComputeUnitMap().get(unittemp);
						if(cctemp.getPort(porttemp).getInOut().equals("output")){
							String connectinstance=unittemp+"_"+porttemp;
							if(info.setPortAndUnit(unittemp, porttemp,connectinstance)){
								continue;
							}else{
								System.out.println(unittemp+":::"+porttemp+"------不存在这样的部件或端口");
								return false;
							}
						}else{
							System.out.println(unittemp+":::"+porttemp+"------端口在左边必须是输出端口");
							return false;
						}
					}
				}
			}
			sourcesize=size;
			String desunit=destination.substring(0, dessep);
			String desport=destination.substring(dessep+1);
			if(desport.equals("CU")){
				info.addAndSetCUPort(desport, "input", sourcesize);
				return true;
			}else{
				dessize=info.getPortSize(desunit, desport);
				if(sourcesize==dessize){
					if(info.setPortAndUnit(desunit, desport,source.replaceAll("\\.", "_"))){
						return true;
					}else{
						System.out.println(desunit+":::"+desport+"------不存在这样的部件或端口");
						return false;
					}
				}else{
					System.out.println(source+":::"+destination+"------左右端数据宽度不匹配");
					return false;
				}
			}
		}else if(source.endsWith("]")&&source.indexOf("[")!=-1){
			int portsep=source.indexOf("[");
			int unitsep=source.indexOf(".");
			int sizesep=source.indexOf(":");
			int size;
			if(sourcesep==-1||dessep==-1){
				System.out.println(source+":::"+destination+"------左端或者右端没有指定端口");
				return false;
			}
			String unittemp=source.substring(0, unitsep);
			String porttemp=source.substring(unitsep+1,portsep);
			String sizetemp;
			if(sizesep==-1){
//				sizetemp=source.substring(portsep+1, source.length()-1);
				size=1;
			}else{
				String sizetemp1=source.substring(portsep+1, sizesep);
				String sizetemp2=source.substring(sizesep+1, source.length()-1);
				size=Integer.parseInt(sizetemp2)-Integer.parseInt(sizetemp1)+1;
			}
			ComputeComponent cctemp=info.getComputeUnitMap().get(unittemp);
			if(cctemp.getPort(porttemp).getInOut().equals("output")){
				String connectinstance=unittemp+"_"+porttemp;
				if(info.setPortAndUnit(unittemp, porttemp,connectinstance)&&
						info.setPortAndUnit(unittemp, porttemp,connectinstance)){
					return true;
				}else{
					System.out.println(unittemp+":::"+porttemp+"------不存在这样的部件或端口");
					return false;
				}
			}else{
				System.out.println(unittemp+":::"+porttemp+"------端口在左边必须是输出端口");
				return false;
			}
		}else{
			if(sourcesep==-1||dessep==-1){
				System.out.println(source+":::"+destination+"------左端或者右端没有指定端口");
				return false;
			}
			String sourceunit=source.substring(0, sourcesep);
			String sourceport=source.substring(sourcesep+1);
			sourcesize=info.getPortSize(sourceunit, sourceport);
			ComputeComponent cctemp=info.getComputeUnitMap().get(sourceunit);
			if(cctemp.getPort(sourceport).getInOut().equals("output")){
				String connectinstance=sourceunit+"_"+sourceport;
				if(info.checkValidPortAndUnit(sourceunit, sourceport)){
					String desunit=destination.substring(0, dessep);
					String desport=destination.substring(dessep+1);
					if(desport.equals("CU")){
						info.addAndSetCUPort(desport, "input",connectinstance, sourcesize);
						return true;
					}else{
						dessize=info.getPortSize(desunit, desport);
						if(sourcesize==dessize){
							if(info.setPortAndUnit(desunit, desport,connectinstance)){
								return true;
							}else{
								System.out.println(desunit+":::"+desport+"------不存在这样的部件或端口");
								return false;
							}
						}else{
							System.out.println(source+":::"+destination+"------左右端数据宽度不匹配");
							return false;
						}
					}
				}else{
					System.out.println(sourceunit+":::"+sourceport+"------不存在这样的部件或端口");
					return false;
				}
			}else{
				System.out.println(sourceunit+":::"+sourceport+"------端口在左边必须是输出端口");
				return false;
			}
		}
	}
	*/
	
	private boolean checkvalidSourceAndRegsctrl(String source,String destination){
		String unitname;
		if(source.contains(".")){
			unitname=source.substring(0, source.indexOf("."));
		}else{
			unitname=source;
		}
		String portname="";
		if(destination.equals("!")){
			portname="Ctrl"+source.replaceAll("\\.", "_");
		}else if(destination.equals("++")){
			portname="Ctrl"+source.replaceAll("\\.", "_")+"Inc";
		}else if(destination.equals("--")){
			portname="Ctrl"+source.replaceAll("\\.", "_")+"Dec";
		}
		
		ComputeComponent cctemp=info.getComputeUnitMap().get("CU");
		ComputeComponentPort cp=cctemp.getPort(portname);
		if(cp==null){
			System.out.println("waring::::::"+source+":::"+destination+"------CU自动添加了输入端口定义："+portname);
			info.addAndSetCUPort(portname, "output",portname, "regsCtrl",1);
		}else{
			int dessize=cp.getSize();
			if(dessize==1){
				info.setPortAndUnit("CU", portname,portname);
			}else{
				System.out.println(source+":::"+destination+"------左右端端口宽度不匹配");
				return false;
			}
		}
		
		ComputeComponent cctemp1=info.getComputeUnitMap().get(unitname);
		/*if(cctemp1==null){
			System.out.println(unitname);
			System.out.println(unitname+"部件不存在");
			return false;
		}
		if(cctemp1.getPort(portname)==null){
			System.out.println(unitname+":"+portname+"端口不存在");
			return false;
		}*/
		if(cctemp1.getPort(portname).getInOut().equals("input")&&cctemp1.getPort(portname).getPortType().equals("ctrl")){
			info.setPortAndUnit(unitname, portname,portname);
			return true;
		}else{
			System.out.println(source+":::"+destination+"------不存在这样的部件或端口");
			return false;
		}
	}
	
	
	public void outputDirectMergedMap(String outfile){
		XSSFWorkbook muxwritebook=new XSSFWorkbook();
		XSSFSheet writeSheet=muxwritebook.createSheet();
		int rowNO=0;
		
		Iterator<Entry<String, DataPathInfo>> iter=mergedmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iter.next();
			String muxname=entry.getKey();
			DataPathInfo infoelement=entry.getValue();

			int directmux=muxSizemap.get(muxname.substring(0, muxname.indexOf("_")));
			if(directmux==1){
				continue;
			}
		
			String source=infoelement.getSource();
			String destination=infoelement.getDestination();
			String muxchannel=infoelement.getMuxchannel();
			String ctrlname=infoelement.getCtrlname();
			ArrayList<CtrlPair> ctrllist=infoelement.getCtrlPair();
			String ctrlvalue="";
			for(int j=0;j<ctrllist.size();j++){
				CtrlPair pairitem=ctrllist.get(j);
				String clk=pairitem.getClk();
				String opfunc=pairitem.getOpAndfunc();
				if(clk!=null&&opfunc!=null){
					if(j==0){
						ctrlvalue="("+clk+"&"+opfunc+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+"&"+opfunc+")";
					}
				}else if(clk!=null&&opfunc==null){
					if(j==0){
						ctrlvalue="("+clk+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+")";
					}
				}/*else{
					ctrlvalue=ctrlvalue;
				}*/
			}
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell1 = writeRow.createCell(1);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell4 = writeRow.createCell(4);
			writexssfCell0.setCellValue(source);
			if(muxchannel!=null){
				writexssfCell1.setCellValue(muxchannel);
			}
			writexssfCell2.setCellValue(destination);
			if(ctrlname!=null){
				writexssfCell3.setCellValue(ctrlname);
			}
			writexssfCell4.setCellValue(ctrlvalue);
			rowNO++;
		}
		
		Iterator<Entry<String, DataPathInfo>> iterregs=mergedregsmap.entrySet().iterator();
		while (iterregs.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iterregs.next();
			DataPathInfo infoelement=entry.getValue();
			String source=infoelement.getSource();
			String destination=infoelement.getDestination();
			String ctrlname=infoelement.getCtrlname();
			ArrayList<CtrlPair> ctrllist=infoelement.getCtrlPair();
			String ctrlvalue="";
			for(int j=0;j<ctrllist.size();j++){
				CtrlPair pairitem=ctrllist.get(j);
				String clk=pairitem.getClk();
				String opfunc=pairitem.getOpAndfunc();
				if(clk!=null&&opfunc!=null){
					if(j==0){
						ctrlvalue="("+clk+"&"+opfunc+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+"&"+opfunc+")";
					}
				}else if(clk!=null&&opfunc==null){
					if(j==0){
						ctrlvalue="("+clk+")";
					}else{
						ctrlvalue=ctrlvalue+"|("+clk+")";
					}
				}/*else{
					ctrlvalue=ctrlvalue;
				}*/
			}
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			XSSFCell writexssfCell3 = writeRow.createCell(3);
			XSSFCell writexssfCell4 = writeRow.createCell(4);
			writexssfCell0.setCellValue(source);
			writexssfCell2.setCellValue(destination);
			if(ctrlname!=null){
				writexssfCell3.setCellValue(ctrlname);
			}
			writexssfCell4.setCellValue(ctrlvalue);
			rowNO++;

		}
		Iterator<Entry<String, String>> iterdirect=mergedDirectConnectMap.entrySet().iterator();
		while (iterregs.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterdirect.next();
			String source=entry.getValue();
			String destination=entry.getValue();
			XSSFRow writeRow=writeSheet.createRow(rowNO);
			XSSFCell writexssfCell0 = writeRow.createCell(0);
			XSSFCell writexssfCell2 = writeRow.createCell(2);
			writexssfCell0.setCellValue(source);
			writexssfCell2.setCellValue(destination);
			rowNO++;
		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(outfile+"3_1.xlsx");
			muxwritebook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
