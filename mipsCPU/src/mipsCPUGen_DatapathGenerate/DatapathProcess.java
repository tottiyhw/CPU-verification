package mipsCPUGen_DatapathGenerate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import mipsCPUGen_ComputeUnits.OPandFUNCpair;
import mipsCPUGen_util.CPUConstants;
import mipsCPUGen_util.myComparator;
import mipsCPUGen_util.myComparator1;
import mipsCPUGen_util.myComparatorDatapath;

public class DatapathProcess {
	private ArrayList<DataPathInfo> instructionsList=null;
	private final static DatapathInputInfo inputinfo=new DatapathInputInfo();
	private TreeMap<String, ArrayList<DataPathInfo>> regsmap=new TreeMap<String,ArrayList<DataPathInfo>>();
	private TreeMap<String, TreeMap<DataPathInfo, DataPathInfo>> muxmap=new TreeMap<String, TreeMap<DataPathInfo, DataPathInfo>>(new myComparator());
	private TreeMap<String, DataPathInfo> directConnectMap=new TreeMap<String,DataPathInfo>(new myComparator1());
	private TreeMap<String, Integer> muxSizemap=new TreeMap<String, Integer>();
	private TreeMap<String, DataPathInfo> mergedmap=new TreeMap<String, DataPathInfo>(new myComparator());
	private TreeMap<String, String> mergedDirectConnectMap=new TreeMap<String,String>();
	private TreeMap<String, DataPathInfo> mergedregsmap=new TreeMap<String,DataPathInfo>();
	private TreeMap<String, String> OPFuncNameValue=new TreeMap<String,String>();
	
	
	public DatapathProcess(ArrayList<DataPathInfo> instructionsList){
		this.instructionsList=instructionsList;
	}
	
	public ArrayList<DataPathInfo> getInstructionsList(){
		return this.instructionsList;
	}
	public TreeMap<String, TreeMap<DataPathInfo, DataPathInfo>> getMuxmap(){
		return this.muxmap;
	}
	public TreeMap<String, ArrayList<DataPathInfo>> getRegsmap(){
		return this.regsmap;
	}
	public TreeMap<String, DataPathInfo> getDirectConnectMap(){
		return this.directConnectMap;
	}
	public TreeMap<String, Integer> getMuxSizemap(){
		return this.muxSizemap;
	}
	public TreeMap<String, DataPathInfo> getMergedmap(){
		return this.mergedmap;
	}
	public TreeMap<String, DataPathInfo> getmergedregsmap(){
		return this.mergedregsmap;
	}
	public TreeMap<String, String> getMergedDirectConnectMap(){
		return this.mergedDirectConnectMap;
	}
	public TreeMap<String, String> getOPFuncNameValueMap(){
		return this.OPFuncNameValue;
	}
	public void doInsertMux() throws IOException{
		HashMap<String, String> regs_mux = inputinfo.getRegs_mux();
		HashMap<String, OPandFUNCpair> oPandFUNCinfo=inputinfo.getoPandFUNCinfo();
		for(int i=0;i<instructionsList.size();i++){
			DataPathInfo element=instructionsList.get(i);
			String source=element.getSource();
			String destination=element.getDestination();
			if((source==null&&destination==null)||(source.isEmpty()&&destination.isEmpty())){
				continue;
			}
			String muxname=regs_mux.get(destination);
			String ctrlvalueCLKpart=null;
			String ctrlvalueOPandFUNCpart=null;
			String OPpart=null;
			String FUNCpart=null;
			String clock=element.getClk();
			if(destination.equals("CU.IRFunc")){
				ctrlvalueCLKpart="";
			}else if(element.getFactor()==null||element.getFactor().equals("")){
				ctrlvalueCLKpart=clock;
			}else{
				ctrlvalueCLKpart=clock+"&"+element.getFactor();
			}
			OPandFUNCpair op_func=oPandFUNCinfo.get(element.getPathName());
			if(op_func==null)
				System.out.println(i+"::::"+element.getPathName());
			String opvalue=op_func.getOP();
			String funcvalue=op_func.getFUNC();
			String funcpart=op_func.getFuncpart();
			OPFuncNameValue.put("CtrlOP"+opvalue, op_func.getOpexp());
			if(!(funcvalue==null||funcvalue.equals(""))){
				OPFuncNameValue.put("Ctrl"+funcpart+funcvalue, op_func.getFuncexp());
			}
			if(element.getStage().equals("IF")||element.getStage().equals("IF/ID")){
				ctrlvalueOPandFUNCpart=null;
				OPpart=null;
				FUNCpart=null;
			}else if(funcvalue==null||funcvalue.equals("")){
				ctrlvalueOPandFUNCpart="CtrlOP"+opvalue;
				OPpart="CtrlOP"+opvalue;
				FUNCpart=null;
			}else if(element.getDestination().equals("CU.IRFunc")||element.getDestination().equals("CU.IRFunc1")||element.getDestination().equals("CU.IRFunc2")){
				ctrlvalueOPandFUNCpart=null;
				OPpart=null;
				FUNCpart=null;
			}else{
				if(funcpart.equals("Func")){
					ctrlvalueOPandFUNCpart="CtrlOP"+opvalue+"&CtrlFunc"+funcvalue;
					OPpart="CtrlOP"+opvalue;
					FUNCpart="CtrlFunc"+funcvalue;
				}else if(funcpart.equals("Func1")){
					ctrlvalueOPandFUNCpart="CtrlOP"+opvalue+"&CtrlFunc1"+funcvalue;
					OPpart="CtrlOP"+opvalue;
					FUNCpart="CtrlFunc1"+funcvalue;
				}else if(funcpart.equals("Func2")){
					ctrlvalueOPandFUNCpart="CtrlOP"+opvalue+"&CtrlFunc2"+funcvalue;
					OPpart="CtrlOP"+opvalue;
					FUNCpart="CtrlFunc2"+funcvalue;
				}
			}
			CtrlPair clkAndOp=new CtrlPair(ctrlvalueCLKpart,ctrlvalueOPandFUNCpart);
			ArrayList<CtrlPair> list=new ArrayList<CtrlPair>();
			list.add(clkAndOp);
			if(muxname==null){
				if(destination.equals("CU.Op")
						||destination.equals("CU.IRFunc")
						||destination.equals("CU.IRFunc1")||destination.equals("CU.IRFunc2")
						){
					element.setCtrlType(CPUConstants.DirectConnect);
					element.setMux(null);
					element.setCtrlvalueCLKpart(null);
					element.setCtrlvalueOPandFUNCpart(null);
				}else if(destination.equals("!")||destination.equals("++")||destination.equals("--")
						||destination.equals("<")||destination.equals(">")){
					element.setCtrlType(CPUConstants.RegsCtrl);
					element.setMux(null);
					element.setCtrlvalueCLKpart(ctrlvalueCLKpart);
					element.setCtrlvalueOPandFUNCpart(ctrlvalueOPandFUNCpart);
					element.setOPpart(OPpart);
					element.setFUNCpart(FUNCpart);
				}else{
					System.out.println(element.getLineNO()+"没有定义多路选择器："+destination);
				}
			}else{
				element.setCtrlvalueCLKpart(ctrlvalueCLKpart);
				element.setCtrlType(CPUConstants.MuxConnect);
				element.setMux(muxname);
				if(element.getStage().equals("IF")){
					element.setCtrlvalueOPandFUNCpart(null);
					element.setOPpart(null);
					element.setFUNCpart(null);
				}else{
					element.setCtrlvalueOPandFUNCpart(ctrlvalueOPandFUNCpart);
					element.setOPpart(OPpart);
					element.setFUNCpart(FUNCpart);
				}
			}
			element.setCtrlPair(list);
		}
	}
	public void doSort() throws IOException {
		for(int i=0;i<instructionsList.size();i++){
			DataPathInfo element=instructionsList.get(i);
			if(element.getCtrlType()==CPUConstants.RegsCtrl){
				String ctrlname;
				if(element.getDestination().equals("!")){
					ctrlname="Ctrl"+element.getSource();
				}else if(element.getDestination().equals("++")){
					ctrlname="Ctrl"+element.getSource()+"Inc";
				}else if(element.getDestination().equals("--")){
					ctrlname="Ctrl"+element.getSource()+"Dec";
				}else if(element.getDestination().equals("<")){
					ctrlname="Ctrl"+element.getSource()+"Clr";
				}else{
					ctrlname="Ctrl"+element.getSource()+"Set";
				}
				element.setCtrlname(ctrlname);
				if(regsmap.containsKey(ctrlname)){
					ArrayList<DataPathInfo> item=regsmap.get(ctrlname);
					item.add(element);
					regsmap.put(ctrlname, item);
				}else{
					ArrayList<DataPathInfo> item= new ArrayList<DataPathInfo>();
					item.add(element);
					regsmap.put(ctrlname, item);
				}
			}else if(element.getCtrlType()==CPUConstants.MuxConnect){
				String mux=element.getMux();
				String ctrlname="Ctrl"+mux;
				element.setCtrlname(ctrlname);
				if(muxmap.containsKey(mux)){
					TreeMap<DataPathInfo, DataPathInfo> item=muxmap.get(mux);
					item.put(element, element);
					muxmap.put(mux, item);
				}else{
					TreeMap<DataPathInfo, DataPathInfo> item= new TreeMap<DataPathInfo, DataPathInfo>(new myComparatorDatapath());
					item.put(element, element);
					muxmap.put(mux, item);
				}
			}else if(element.getCtrlType()==CPUConstants.DirectConnect){
				String key=element.getPathNO()+"_"+element.getInLine();
				if(!directConnectMap.containsKey(key)){
					directConnectMap.put(key, element);
				}
			}else{
//				System.out.println("数据通路此行解析出错，既不是MUX通路、控制信号及直连："+element.getLineNO()+":"+element.getSource()+":"+element.getDestination());
			}
		}
	}
	public void doSort_addMuxChannel() throws IOException {
		
		Iterator<Entry<String, TreeMap<DataPathInfo, DataPathInfo>>> iter=muxmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>> entry = (Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>>) iter.next();
			String muxname=entry.getKey();
			TreeMap<DataPathInfo, DataPathInfo> item=entry.getValue();
			Iterator<Entry<DataPathInfo, DataPathInfo>> it=item.entrySet().iterator();
			String lastSource="";
			int i=0;
			while (it.hasNext()) {
				Map.Entry<DataPathInfo, DataPathInfo> itentry = (Map.Entry<DataPathInfo, DataPathInfo>) it.next();
				DataPathInfo infoelement=itentry.getValue();
				String source=infoelement.getSource();
				String ctrlname=infoelement.getCtrlname();
				if(source.equals(lastSource)){
					infoelement.setCtrlname(ctrlname+"_"+i);
					infoelement.setMuxchannel(muxname+"_"+i);
				}else{
					i++;
					infoelement.setCtrlname(ctrlname+"_"+i);
					infoelement.setMuxchannel(muxname+"_"+i);
					lastSource=source;
				}
			}
			muxSizemap.put(muxname, i);
		}
		/*
		Iterator<Entry<String, Integer>> iter1=muxSizemap.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter1.next();
			String key=entry.getKey();
			int value=entry.getValue();
			System.out.println("----"+key+":"+value+"-----");
		}*/
	}
	public void doMerge() throws IOException {
		
		Iterator<Entry<String, TreeMap<DataPathInfo, DataPathInfo>>> iter=muxmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>> entry = (Map.Entry<String, TreeMap<DataPathInfo, DataPathInfo>>) iter.next();
			TreeMap<DataPathInfo, DataPathInfo> item=entry.getValue();
			Iterator<Entry<DataPathInfo, DataPathInfo>> it=item.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataPathInfo, DataPathInfo> itentry = (Map.Entry<DataPathInfo, DataPathInfo>) it.next();
				DataPathInfo infoelement=itentry.getValue();
				
				String muxChannel=infoelement.getMuxchannel();
				boolean iscontain=false;
				if(mergedmap.containsKey(muxChannel)){
					DataPathInfo havecontainsitem=mergedmap.get(muxChannel);
					ArrayList<CtrlPair> list=havecontainsitem.getCtrlPair();
					for(int j=0;j<list.size();j++){
						CtrlPair pairitem=list.get(j);
						if(pairitem.getClk().equals(infoelement.getCtrlvalueCLKpart())){
							if(pairitem.getOpAndfunc()==null&&infoelement.getCtrlvalueOPandFUNCpart()==null){
								iscontain=true;
								break;
							}else if(pairitem.getOpAndfunc().equals(infoelement.getCtrlvalueOPandFUNCpart())){
								iscontain=true;
								break;
							}
						}
					}
					if(!iscontain){
						list.add(new CtrlPair(infoelement.getCtrlvalueCLKpart(),infoelement.getCtrlvalueOPandFUNCpart()));
					}
					mergedmap.put(muxChannel, havecontainsitem);
				}else{
					mergedmap.put(muxChannel, infoelement);
				}
			}
		}
		
		Iterator<Entry<String, DataPathInfo>> iterDirect=directConnectMap.entrySet().iterator();
		while (iterDirect.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iterDirect.next();
			DataPathInfo element=entry.getValue();
			String source=element.getSource();
			String destination=element.getDestination();
			if(!mergedDirectConnectMap.containsKey(source)){
				mergedDirectConnectMap.put(source, destination);
			}
		}
		
		Iterator<Entry<String, ArrayList<DataPathInfo>>> iterRegs=regsmap.entrySet().iterator();
		while (iterRegs.hasNext()) {
			Map.Entry<String, ArrayList<DataPathInfo>> entry = (Map.Entry<String, ArrayList<DataPathInfo>>) iterRegs.next();
			String key=entry.getKey();
			ArrayList<DataPathInfo> value=entry.getValue();
			
			DataPathInfo newMergedDatapathElement=new DataPathInfo();
			String destination="";
			String ctrlname="";
			String source="";
			ArrayList<CtrlPair> list=new ArrayList<CtrlPair>();
			for(int i=0;i<value.size();i++){
				DataPathInfo element=value.get(i);
				String clkpart=element.getCtrlvalueCLKpart();
				String opfuncpart=element.getCtrlvalueOPandFUNCpart();
				source=element.getSource();
				destination=element.getDestination();
				ctrlname=element.getCtrlname();
				boolean iscontain=false;
				for(int j=0;j<list.size();j++){
					CtrlPair pair=list.get(j);
					if(pair.getClk().equals(clkpart)){
						if((opfuncpart==null||opfuncpart.equals(""))&&pair.getOpAndfunc()==null){
							iscontain=true;
							continue;
						}else if(pair.getOpAndfunc().equals(opfuncpart)){
							iscontain=true;
							continue;
						}
					}
				}
				if(!iscontain){
					CtrlPair pair=new CtrlPair(clkpart,opfuncpart);
					list.add(pair);
				}
			}
			newMergedDatapathElement.setSource(source);
			newMergedDatapathElement.setDestination(destination);
			newMergedDatapathElement.setCtrlname(ctrlname);
			newMergedDatapathElement.setCtrlPair(list);
			mergedregsmap.put(key, newMergedDatapathElement);
		}
	}
	
}
