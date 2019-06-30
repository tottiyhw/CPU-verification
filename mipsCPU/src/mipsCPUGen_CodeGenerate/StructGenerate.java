package mipsCPUGen_CodeGenerate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import mipsCPUGen_ComputeUnits.ComputeComponent;
import mipsCPUGen_ComputeUnits.ComputeComponentPort;
import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_ComputeUnits.Mux;
import mipsCPUGen_ComputeUnits.MuxChannel;
import mipsCPUGen_DatapathGenerate.DataPathInfo;
import mipsCPUGen_DatapathGenerate.DataPathOutput;
import mipsCPUGen_DatapathGenerate.DatapathInputInfo;
import mipsCPUGen_DatapathGenerate.DatapathProcess;
import mipsCPUGen_util.myComparator;

public class StructGenerate {
	private final static DatapathInputInfo inputinfo=new DatapathInputInfo();
	private TreeMap<String, Integer> muxSizemap=new TreeMap<String, Integer>();
	private TreeMap<String, DataPathInfo> mergedmap=new TreeMap<String, DataPathInfo>(new myComparator());
	private TreeMap<String, String> mergedDirectConnectMap=new TreeMap<String,String>();
	private ComputeComponetInfo info=null;
	private DatapathProcess dp=null;
	private CodeGen cg=new CodeGen();
	private ArrayList<String> topWireDef=new ArrayList<String>();
	private ArrayList<String> topWireNoMuxDef=new ArrayList<String>();
	private ArrayList<String> topInstanceList=new ArrayList<String>();
	private ArrayList<String> topInstanceNoMuxList=new ArrayList<String>();
	private ArrayList<String> CUWireDef=new ArrayList<String>();
	private ArrayList<String> CUInstance=new ArrayList<String>();
	private ArrayList<String> CUWireNoMuxDef=new ArrayList<String>();
	private ArrayList<String> CUInstanceNoMux=new ArrayList<String>();
	private ArrayList<String> InitialInstance=new ArrayList<String>();
	private TreeMap<String, String> ctrlmap=null;
	
	private TreeMap<String, Mux> MUXs=new TreeMap<String, Mux>(new myComparator());
	private ComputeComponent initial=new ComputeComponent("Initial","Initial_model");
	private String topcode="";
	private String CUcode="";
	private String CUNoMuxcode="";
	private String initialcode="";
	
	public StructGenerate(ComputeComponetInfo info,DatapathProcess dp,DataPathOutput dpo){
		this.info=info;
		this.dp=dp;
		this.mergedmap=dp.getMergedmap();
		this.mergedDirectConnectMap=dp.getMergedDirectConnectMap();
		this.muxSizemap=dp.getMuxSizemap();
		this.ctrlmap=dpo.getCtrlExpression();
		this.generateMUXStruct();
		this.generateTopWiredef();
		this.generateTopInstance();
		this.generateCUWiredef();
		this.generateCUInstance();
		this.generateInitial();
	}
	
	private void generateMUXStruct(){
		HashMap<String, String> mux_regs = inputinfo.getMux_regs();
		Iterator<Entry<String, DataPathInfo>> iter=mergedmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, DataPathInfo> entry = (Map.Entry<String, DataPathInfo>) iter.next();
			DataPathInfo infoelement=entry.getValue();
			String mux=infoelement.getMux();
			String ctrlname=infoelement.getCtrlname();
			String muxdestination=mux_regs.get(mux);
			int sep=muxdestination.indexOf(".");
			String unit=muxdestination.substring(0, sep);
			String port=muxdestination.substring(sep+1);
			int size=info.getPortSize(unit, port);
			if(MUXs.containsKey(mux)){
				String name=mux;
				String ctrlvalue=infoelement.getCtrlvalueCLKpart()+"&"+infoelement.getCtrlvalueOPandFUNCpart();
				String source=infoelement.getSource().replace(".", "_");
				MuxChannel channel=new MuxChannel(name,ctrlname,ctrlvalue,source,size);
				Mux item=MUXs.get(mux);
				item.addChannel(channel);
				MUXs.put(mux, item);
			}else{
				String name=mux;
				String ctrlvalue=infoelement.getCtrlvalueCLKpart()+"&"+infoelement.getCtrlvalueOPandFUNCpart();
				String source=infoelement.getSource().replace(".", "_");
				MuxChannel channel=new MuxChannel(name,ctrlname,ctrlvalue,source,size);
				Mux item=new Mux(name,size);
				item.addChannel(channel);
				MUXs.put(mux, item);
			}
			info.addAndSetCUPort(ctrlname, "output", ctrlname, "muxCtrl",1);
		}
	}
	
	private void generateTopWiredef(){
		topWireDef.add("module mipsCPU(clr,clk);");
		topWireDef.add("input clk,clr;");
		topWireNoMuxDef.add("module CPU(clr,clk);");
		topWireNoMuxDef.add("input clk,clr;");
		Iterator<Entry<String, Mux>> iter=MUXs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Mux> entry = (Map.Entry<String, Mux>) iter.next();
			String muxname=entry.getKey();
			Mux muxelement=entry.getValue();
			int datasize=muxelement.getDataSize();
			String value="";
			int chsize=muxelement.getChannelSize();
			for(int i=0;i<chsize;i++){
				MuxChannel ch = muxelement.getChannels().get(i);
				String ctrlname=ch.getCtrlname();
				value="wire "+ctrlname+";";
				topWireDef.add(value);
				if(chsize>1){
					topWireNoMuxDef.add(value);
				}
			}
			if(datasize==1){
				value="wire "+muxname+"_Out;";
			}else{
				value="wire ["+(datasize-1)+":0]"+muxname+"_Out;";
			}
			int channelsize=muxelement.getChannelSize();
			if(channelsize>1){
				topWireNoMuxDef.add(value);
			}
			topWireDef.add(value);
		}
		
		ArrayList<String> index=cg.getAppearedIndex();
		HashMap<String,ComputeComponent> computeUnitMap=info.getComputeUnitMap();
		for(int i=0;i<index.size();i++){
			String compont=index.get(i);
			ComputeComponent cc=computeUnitMap.get(compont);
			if(cc.isAppeared()){
				ArrayList<ComputeComponentPort> portlist=cc.getPortList();
				for(int j=0;j<portlist.size();j++){
					ComputeComponentPort cp=portlist.get(j);
					if(cp.getPortType().equals("clr")||cp.getPortType().equals("clk")
							||cp.getPortType().equals("regsCtrl")||cp.getPortType().equals("muxCtrl")){
						continue;
					}else if(cp.isAppeared()){
						String wiredef;
						if(cp.getPortType().equals("ctrl")){
							wiredef="wire "+cp.getPortInstance()+";";
							topWireDef.add(wiredef);
							topWireNoMuxDef.add(wiredef);
						}else{
							int datasize=cp.getSize();
							String inout=cp.getInOut();
							if(inout.equals("output")){
								String portname=cp.getName();
								if(datasize==1){
									wiredef="wire "+compont+"_"+portname+";";
								}else{
									wiredef="wire ["+(datasize-1)+":0]"+compont+"_"+portname+";";
								}
								topWireDef.add(wiredef);
								topWireNoMuxDef.add(wiredef);
							}else{
								continue;
							}
						}
					}else{
						continue;
					}
				}
			}else{
				continue;
			}
		}
	}
	
	private void generateTopInstance(){
		HashMap<String, String> regs_mux = inputinfo.getRegs_mux();
		ArrayList<String> index=cg.getAppearedIndex();
		HashMap<String,ComputeComponent> computeUnitMap=info.getComputeUnitMap();
		for(int i=0;i<index.size();i++){
			String compont=index.get(i);
			ComputeComponent cc=computeUnitMap.get(compont);
			if(cc.isAppeared()){
				String ccname=cc.getName();
				String cctype=cc.getComponentType();
				String wiredef=cctype+" "+ccname+"(";
				String wireNoMuxdef=cctype+" "+ccname+"(";
				ArrayList<ComputeComponentPort> portlist=cc.getPortList();
				for(int j=0;j<portlist.size();j++){
					ComputeComponentPort cp=portlist.get(j);
					String cpname=cp.getName();
					if(cp.getPortType().equals("clr")||cp.getPortType().equals("clk")){
						wiredef=wiredef+"."+cpname+"("+cpname+"),";
						wireNoMuxdef=wireNoMuxdef+"."+cpname+"("+cpname+"),";
					}else if(cp.getPortType().equals("ctrl")){
						if(cp.isAppeared()){
							wiredef=wiredef+"."+cpname+"("+cp.getPortInstance()+"),";
							wireNoMuxdef=wireNoMuxdef+"."+cpname+"("+cp.getPortInstance()+"),";
						}else{
							wiredef=wiredef+"."+cpname+"(1'b0),";
							wireNoMuxdef=wireNoMuxdef+"."+cpname+"(1'b0),";
						}
					}else if(cp.isAppeared()){
						String inout=cp.getInOut();
						if(inout.equals("input")){
							if(regs_mux.containsKey(ccname+"."+cpname)){
								String mux=regs_mux.get(ccname+"."+cpname);
								int channelsize=muxSizemap.get(mux);
								wiredef=wiredef+"."+cpname+"("+mux+"_Out),";
								if(channelsize==1){
									Mux muxbefore=MUXs.get(mux);
									String source=muxbefore.getChannels().get(0).getSource();
									wireNoMuxdef=wireNoMuxdef+"."+cpname+"("+source+"),";
								}else{
									wireNoMuxdef=wireNoMuxdef+"."+cpname+"("+mux+"_Out),";
								}
							}else{
								Iterator<Entry<String, String>> iterdir=mergedDirectConnectMap.entrySet().iterator();
								while (iterdir.hasNext()) {
									Map.Entry<String, String> entry = (Map.Entry<String, String>) iterdir.next();
									String source=entry.getKey();
									String destination=entry.getValue();
									if(destination.equals(ccname+"."+cpname)){
										wiredef=wiredef+"."+cpname+"("+source.replaceAll("\\.", "_")+"),";
										wireNoMuxdef=wireNoMuxdef+"."+cpname+"("+source.replaceAll("\\.", "_")+"),";
									}
								}
							}
						}else{
							if(cp.getPortType().equals("regsCtrl")){
								wiredef=wiredef+"."+cpname+"("+cp.getPortInstance()+"),";
								wireNoMuxdef=wireNoMuxdef+"."+cpname+"("+cp.getPortInstance()+"),";
							}else if(cp.getPortType().equals("muxCtrl")){
								String cpnametemp=cpname.substring(4, cpname.indexOf("_"));
								int channelsize=muxSizemap.get(cpnametemp);
								wiredef=wiredef+"."+cpname+"("+cp.getPortInstance()+"),";
								if(channelsize>1){
									wireNoMuxdef=wireNoMuxdef+"."+cpname+"("+cp.getPortInstance()+"),";
								}
							}else{
								String connectinstance1=ccname+"_"+cpname;
								wiredef=wiredef+"."+cpname+"("+connectinstance1+"),";
								wireNoMuxdef=wireNoMuxdef+"."+cpname+"("+connectinstance1+"),";
							}
						}
					}else{
						continue;
					}
				}
				wiredef=wiredef.substring(0, wiredef.length()-1)+");";
				wireNoMuxdef=wireNoMuxdef.substring(0, wireNoMuxdef.length()-1)+");";
				topInstanceList.add(wiredef);
				topInstanceNoMuxList.add(wireNoMuxdef);
			}else{
				continue;
			}
		}
		
		Iterator<Entry<String, Mux>> iter=MUXs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Mux> entry = (Map.Entry<String, Mux>) iter.next();
			String muxname=entry.getKey();
			Mux muxelement=entry.getValue();
			int datasize=muxelement.getDataSize();
			int channelsize=muxelement.getChannelSize();
			String value="";
			if(channelsize==1){
				MuxChannel temp = muxelement.getChannels().get(0);
				String source=temp.getSource();
				String chctrlvalue=temp.getCtrlname();
				String outvalue=muxname+"_Out";
				value="Mux"+channelsize+"_"+(datasize)+"module "+muxname+"(.Mux_1("+source+"),.SelMux_1("+chctrlvalue+"),.Out("+outvalue+"));";
			}else{
				value="Mux"+channelsize+"_"+(datasize)+"module "+muxname+"(";
				String outvalue=muxname+"_Out";
				for(int i=0;i<channelsize;i++){
					MuxChannel temp = muxelement.getChannels().get(i);
					String source=temp.getSource();
					String chctrlvalue=temp.getCtrlname();
					value=value+".Mux_"+(i+1)+"("+source+"),.SelMux_"+(i+1)+"("+chctrlvalue+"),";
				}
				value=value+".Out("+outvalue+"));";
				topInstanceNoMuxList.add(value);
			}
			topInstanceList.add(value);
		}
		topInstanceNoMuxList.add("endmodule");
		topInstanceList.add("endmodule");
	}
	
	private void generateCUWiredef(){
		CUWireDef=new ArrayList<String>();
		CUWireNoMuxDef=new ArrayList<String>();
		
		initial.addPort("clr", "input", "clr",1);
		initial.addPort("clk","input", "clk",1);
/*		initial.addPort("Op","input", "data",6);
		initial.addPort("IRFunc","input", "data",6);
		initial.addPort("IR","input", "data",32);
		initial.addPort("Func","output", "data",6);
		initial.addPort("MemFunc","output", "data",6);
		initial.addPort("TrapAddr","output", "data",32);*/
		initial.addPort("P0","output", "data",1);
		initial.addPort("P1","output", "data",1);
		initial.addPort("P2","output", "data",1);
		initial.addPort("P3","output", "data",1);
		initial.addPort("P4","output", "data",1);
		initial.addPort("Reset","output", "data",1);
		
		String temp="module CU_module(clr,clk";
		String tempNoMux="module CU_module(clr,clk";
		HashMap<String,ComputeComponent> computeUnitMap=info.getComputeUnitMap();
		ComputeComponent cc=computeUnitMap.get("CU");
		ArrayList<ComputeComponentPort> portlist=cc.getPortList();
		for(int i=0;i<portlist.size();i++){
			String wiredef;
			ComputeComponentPort cp=portlist.get(i);
			if(cp.getPortType().equals("clr")||cp.getPortType().equals("clk")){
				wiredef="input "+cp.getName()+";";
				CUWireDef.add(wiredef);
				CUWireNoMuxDef.add(wiredef);
			}else if(cp.isAppeared()||cp.getPortType().equals("regsCtrl")||cp.getPortType().equals("muxCtrl")){
				int size=cp.getSize();
				String inout=cp.getInOut();
				String portname=cp.getName();
				if(size==1){
					wiredef=inout+" "+portname+";";
				}else{
					wiredef=inout+" ["+(size-1)+":0]"+portname+";";
				}
				
				if(cp.getInOut().equals("output")){
					if(cp.getPortType().equals("regsCtrl")){
						wiredef="output "+cp.getName()+";";;
						CUWireDef.add(wiredef);
						CUWireNoMuxDef.add(wiredef);
						tempNoMux=tempNoMux+","+portname;
					}else if(cp.getPortType().equals("muxCtrl")){
						wiredef="output "+cp.getName()+";";;
						CUWireDef.add(wiredef);
						String cpnametemp=portname.substring(4, portname.indexOf("_"));
						int channelsize=muxSizemap.get(cpnametemp);
						if(channelsize>1){
							CUWireNoMuxDef.add(wiredef);
							tempNoMux=tempNoMux+","+portname;
						}
					}else{
						initial.addPort(portname,"output","data",size);
						CUWireDef.add(wiredef);
						CUWireNoMuxDef.add(wiredef);
						tempNoMux=tempNoMux+","+portname;
					}
				}else{
					if(cp.getName().equals("Op")||cp.getName().equals("IRFunc")||cp.getName().equals("IRFunc1")||cp.getName().equals("IRFunc2")
							||cp.getName().equals("IR")){
						initial.addPort(portname,"input","data",size);
					}
					if(cp.getName().equals("IR")){
						initial.addPort("nop","output","data",1);
					}
					CUWireDef.add(wiredef);
					CUWireNoMuxDef.add(wiredef);
					tempNoMux=tempNoMux+","+portname;
				}
				temp=temp+","+portname;
			}
		}
		temp=temp+");";
		CUWireDef.add("wire P0,P1,P2,P3,P4,Reset;");
		CUWireDef.add(0, temp);
		tempNoMux=tempNoMux+");";
		CUWireNoMuxDef.add("wire P0,P1,P2,P3,P4,Reset;");
		CUWireNoMuxDef.add(0, tempNoMux);
	}
	private void generateCUInstance(){
		CUInstanceNoMux=new ArrayList<String>();
		CUInstance=new ArrayList<String>();
		String wiredef="Initial_model Initial(";
		ArrayList<ComputeComponentPort> initialportlist=initial.getPortList();
		for(int i=0;i<initialportlist.size();i++){
			ComputeComponentPort port=initialportlist.get(i);
			String name=port.getName();
			wiredef=wiredef+"."+name+"("+name+"),";
		}
		
		TreeMap<String, String> OPFuncNameValue=dp.getOPFuncNameValueMap();
		Iterator<Entry<String, String>> iter=OPFuncNameValue.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			String name=entry.getKey();
			wiredef=wiredef+"."+name+"("+name+"),";
			initial.addPort(name, "output", "data", 1);
			CUWireDef.add("wire "+name+";");
		}
		wiredef=wiredef.substring(0, wiredef.length()-1)+");";
		CUInstance.add(wiredef);
		CUInstanceNoMux.add(wiredef);
		Iterator<Entry<String, String>> iter1=ctrlmap.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter1.next();
			String name=entry.getKey().replaceAll("\\.", "_");
			String value=entry.getValue();
			wiredef="assign "+name+"="+value+";";
			CUInstance.add(wiredef);
			if(name.startsWith("CtrlMux")){
				String cpnametemp=name.substring(4, name.indexOf("_"));
				int channelsize=muxSizemap.get(cpnametemp);
				if(channelsize>1){
					CUInstanceNoMux.add(wiredef);
				}
			}else{
				CUInstanceNoMux.add(wiredef);
			}
		}
		CUInstance.add("endmodule");
		CUInstanceNoMux.add("endmodule");
	}
	private void generateInitial(){
		ArrayList<ComputeComponentPort> initialportlist=initial.getPortList();
		String instance="module Initial_model(clr,clk,";
		String wiredef="input clr,clk;\n";
		for(int i=0;i<initialportlist.size();i++){
			ComputeComponentPort port=initialportlist.get(i);
			String name=port.getName();
			if(name.equals("clk")||name.equals("clr")){
				continue;
			}else{
				instance=instance+name+",";
				String inout=port.getInOut();
				int size=port.getSize();
				if(size==1){
					wiredef=wiredef+inout+" "+name+";\n";
				}else{
					wiredef=wiredef+inout+" ["+(size-1)+":0]"+name+";\n";
				}
			}
		}
		instance=instance.substring(0, instance.length()-1)+");";
		InitialInstance.add(instance);
		InitialInstance.add(wiredef);
		InitialInstance.add("wire [2:0]clkCounterOut;");
		InitialInstance.add("Counter count(.clk(clk),.clr(clr),.dataout(clkCounterOut));");
		InitialInstance.add("CP_generate clkgen(.dataIn(clkCounterOut),.Reset(Reset),.p0(P0),.p1(P1),.p2(P2),.p3(P3),.p4(P4));");
		
		TreeMap<String, String> OPFuncNameValue=dp.getOPFuncNameValueMap();
		Iterator<Entry<String, String>> iter=OPFuncNameValue.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			String name=entry.getKey();
			String value=entry.getValue();
			wiredef="assign "+name+"="+value+";";
			InitialInstance.add(wiredef);
		}
		if(initial.getPort("nop")!=null){
			InitialInstance.add("assign nop=~IR[31]&~IR[30]&~IR[29]&~IR[28]&~IR[27]&~IR[26]&~IR[25]&~IR[24]&~IR[23]&~IR[22]&~IR[21]&~IR[20]&~IR[19]&~IR[18]&~IR[17]&~IR[16]&~IR[15]&~IR[14]&~IR[13]&~IR[12]&~IR[11]&~IR[10]&~IR[9]&~IR[8]&~IR[7]&~IR[6]&~IR[5]&~IR[4]&~IR[3]&~IR[2]&~IR[1]&~IR[0];");
		}
		/*
		ArrayList<String> funcvaluelist=inputinfo.getfuncvaluelist();
		String funcvalue="assign Func=";
		for(int i=0;i<funcvaluelist.size();i++){
			funcvalue=funcvalue+"("+funcvaluelist.get(i)+")|";
		}
		funcvalue=funcvalue.substring(0, funcvalue.length()-1)+";";
		*/
		if(initial.getPort("TrapAddr")!=null){
			InitialInstance.add("assign TrapAddr=32'h80000200;");
		}
//		InitialInstance.add(funcvalue);
		InitialInstance.add("endmodule");
	}
	
	public void printTop(){
		System.out.println("-----------------MUX-----------------");
		for(int i=0;i<topWireDef.size();i++){
			System.out.println(topWireDef.get(i));
			topcode=topcode+topWireDef.get(i)+"\n";
		}
		for(int i=0;i<topInstanceList.size();i++){
			System.out.println(topInstanceList.get(i));
			topcode=topcode+topInstanceList.get(i)+"\n";
		}
		/*
		System.out.println("-----------------NO MUX-----------------");
		for(int i=0;i<topWireNoMuxDef.size();i++){
			System.out.println(topWireNoMuxDef.get(i));
			topNoMuxcode=topNoMuxcode+topWireNoMuxDef.get(i)+"\n";
			
		}
		for(int i=0;i<topInstanceNoMuxList.size();i++){
			System.out.println(topInstanceNoMuxList.get(i));
			topNoMuxcode=topNoMuxcode+topInstanceNoMuxList.get(i)+"\n";
		}*/
	}
	
	public void printCU(){
		for(int i=0;i<CUWireDef.size();i++){
			System.out.println(CUWireDef.get(i));
			CUcode=CUcode+CUWireDef.get(i)+"\n";
		}
		for(int i=0;i<CUInstance.size();i++){
			System.out.println(CUInstance.get(i));
			CUcode=CUcode+CUInstance.get(i)+"\n";
		}
	}
	public void printCUNoMux(){
		for(int i=0;i<CUWireNoMuxDef.size();i++){
			System.out.println(CUWireNoMuxDef.get(i));
			CUNoMuxcode=CUNoMuxcode+CUWireNoMuxDef.get(i)+"\n";
		}
		for(int i=0;i<CUInstanceNoMux.size();i++){
			System.out.println(CUInstanceNoMux.get(i));
			CUNoMuxcode=CUNoMuxcode+CUInstanceNoMux.get(i)+"\n";
		}
	}
	public void printInitial(){
		for(int i=0;i<InitialInstance.size();i++){
			System.out.println(InitialInstance.get(i));
			initialcode=initialcode+InitialInstance.get(i)+"\n";
		}
	}
	
	public void generateFile(String filepath,String prefix){
		try {
			FileWriter fw=new FileWriter(filepath+"mipsCPU.v");
			fw.write(topcode);
			fw.close();
		/*	fw=new FileWriter(filepath+"cpuNoMux"+prefix+".v");
			fw.write(topNoMuxcode);
			fw.close();*/
			fw=new FileWriter(filepath+"CU_module.v");
			fw.write(CUcode);
			fw.close();
		/*	fw=new FileWriter(filepath+"CUNoMuxcode"+prefix+".v");
			fw.write(CUcode);
			fw.close();*/
			fw=new FileWriter(filepath+"Initial_model"+prefix+".v");
			fw.write(initialcode);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
