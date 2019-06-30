package mipsCPUGen_CodeGenerate;

import java.util.ArrayList;

public class CodeGen {
	private ArrayList<String> componentAppearedIndex=null;
	public CodeGen(){
		this.init();
	}
	public ArrayList<String> getAppearedIndex(){
		return this.componentAppearedIndex;
	}
	private void init(){
		componentAppearedIndex=new ArrayList<String>();
		componentAppearedIndex.add("CU");
		componentAppearedIndex.add("PC");
		componentAppearedIndex.add("IMem");
		componentAppearedIndex.add("DMem");
		componentAppearedIndex.add("IR");
		componentAppearedIndex.add("GPR");
		componentAppearedIndex.add("A");
		componentAppearedIndex.add("B");
		componentAppearedIndex.add("ALUOut");
		componentAppearedIndex.add("ALU");
		componentAppearedIndex.add("SU");
		componentAppearedIndex.add("CMPU");
		componentAppearedIndex.add("IMMEXT");
		componentAppearedIndex.add("IMMSEXT");
		componentAppearedIndex.add("LIMMEXT");
		componentAppearedIndex.add("ADDREXT");
		componentAppearedIndex.add("SEXT");
		componentAppearedIndex.add("CountUnit");
		componentAppearedIndex.add("DR");
		componentAppearedIndex.add("DR0");
		componentAppearedIndex.add("LLbit");
		componentAppearedIndex.add("PIDReg");
		componentAppearedIndex.add("Hi");
		componentAppearedIndex.add("Lo");
		componentAppearedIndex.add("MDU");
		componentAppearedIndex.add("MemDataSel");
		componentAppearedIndex.add("CP0");
		componentAppearedIndex.add("CP1");
		componentAppearedIndex.add("OVReg");
		componentAppearedIndex.add("ConditionReg");
		/*
		ComputeComponent cu=unitInfo.getComputeUnitMap().get("CU");
		Iterator<Entry<String, DataPathInfo>> iter=mergedRegs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,DataPathInfo> entry = (Map.Entry<String,DataPathInfo>) iter.next();
			DataPathInfo infoelement=entry.getValue();
			String regsctrlname=infoelement.getCtrlname();
			cu.addPort(regsctrlname, "output", "data", regsctrlname, 1,true);
		}*/
	}
	/*
	public void generateMux(){
		muxList=new ArrayList<Mux>();
		Iterator<Entry<String, TreeMap<String, DataPathInfo>>> iter=mergedMux.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,TreeMap<String, DataPathInfo>> entry = (Map.Entry<String,TreeMap<String, DataPathInfo>>) iter.next();
			TreeMap<String, DataPathInfo> muxindexmap=entry.getValue();
			String muxName=entry.getKey();
			Mux mux=new Mux(muxName);
			int datasize=0;
			Iterator<Entry<String, DataPathInfo>> it=muxindexmap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String,DataPathInfo> itentry = (Map.Entry<String,DataPathInfo>) it.next();
				String key=itentry.getKey();
				DataPathInfo infoelement=itentry.getValue();
				String channelName=key.split("_")[1];
				String ctrlname=infoelement.getCtrlname();
				String ctrlvalue=infoelement.getCtrlname();
				String source=infoelement.getSource();
				datasize=infoelement.getSize();
				MuxChannel ch=new MuxChannel(channelName);
				ch.setCtrlname(ctrlname);
				ch.setCtrlvalue(ctrlvalue);
				ch.setSize(datasize);
				ch.setSource(source);
				mux.addChannel(ch);
			}
			mux.setDataSize(datasize);
			muxList.add(mux);
		}
		
	}
	
	public void generate(){
		
	}
	public void topCodeGen(){
		String code="module mipsCPU(clr,clk);\ninput clr,clk;\n";
		String componentConnectDef="";
		String componentDelication="";
		HashMap<String,ComputeComponent> componentInfo=unitInfo.getComputeUnitMap();
		ComputeComponent cu=unitInfo.getComputeUnitMap().get("CU");
		
		for(int i=0;i<componentAppearedIndex.size();i++){
			String componentName=componentAppearedIndex.get(i);
			ComputeComponent component=componentInfo.get(componentName);
			System.out.println(componentName);
			boolean componentAppeared=component.isAppeared();
			if(componentName.equals("CU")){
				continue;
			}
			if(componentAppeared){
				componentDelication=componentDelication+component.getComponentType()+" "+componentName+"(";
				ArrayList<ComputeComponentPort> portList=component.getPortList();
				for(int j=0;j<portList.size();j++){
					ComputeComponentPort port=portList.get(j);
					boolean portAppeared=port.isAppeared();
					String inout=port.getInOut();
					String type=port.getPortType();
					if(type.equals("clr")){
						componentDelication=componentDelication+".clr(clr),";
					}else if(type.equals("clk")){
						componentDelication=componentDelication+".clk(clk),";
					}else{
						if(portAppeared){
							if(inout.equals("input")){
								componentDelication=componentDelication+"."+port.getName()+"("+port.getPortInstance()+"),";
							}else{
								int size=port.getSize();
								String portConnect=port.getPortInstance();
								if(size==1){
									componentConnectDef=componentConnectDef+"wire "+portConnect+";\n";
								}else{
									componentConnectDef=componentConnectDef+"wire ["+(size-1)+":0]"+portConnect+";\n";
								}
								componentDelication=componentDelication+"."+port.getName()+"("+port.getPortInstance()+"),";
							}
						}
					}
				}
				componentDelication=componentDelication.substring(0, componentDelication.length()-1);
				componentDelication=componentDelication+");\n";
			}
		}
		
		
		String muxConnectDef="";
		String muxDelication="";
		String muxCtrl="";
		String muxOut="";
		for(int i=0;i<muxList.size();i++){
			Mux element=muxList.get(i);
			String type=element.getType();
			String name=element.getName();
			int datasize=element.getDataSize();
			ArrayList<MuxChannel> channels=element.getChannels();
			muxDelication=muxDelication+type+" "+name+"(";
			for(int j=0;j<channels.size();j++){
				MuxChannel ch=channels.get(j);
				String ctlrport="Ctrl_"+ch.getName();
				muxCtrl=muxCtrl+"wire "+ch.getCtrlname()+";\n";
				muxDelication=muxDelication+"."+ch.getName()+"("+ch.getSource()+"),."+ctlrport+"("+ch.getCtrlname()+"),";
				cu.addPort(ch.getCtrlname(), "output", "data",ch.getCtrlname(), 1,true);
			}
			muxDelication=muxDelication+".Out("+name+"_Out));\n";
			if(datasize==1){
				muxOut=muxOut+"wire "+name+"_Out;\n";
			}else{
				muxOut=muxOut+"wire ["+(datasize-1)+":0]"+name+"_Out;\n";
			}
		}
		muxConnectDef=muxCtrl+muxOut;
		
		ArrayList<ComputeComponentPort> cuPort=cu.getPortList();
		String CUdef=cu.getComponentType()+" CU"+"(";
		for(int i=0;i<cuPort.size();i++){
			ComputeComponentPort port=cuPort.get(i);
			boolean portAppeared=port.isAppeared();
			String type=port.getPortType();
			if(type.equals("clr")){
				CUdef=CUdef+".clr(clr),";
			}else if(type.equals("clk")){
				CUdef=CUdef+".clk(clk),";
			}else{
				if(portAppeared){
					if(port.getName().equals("IRop")){
						CUdef=CUdef+"."+port.getName()+"(IR_Out[31:26]),";
					}else if(port.getName().equals("IRfunc")){
						CUdef=CUdef+"."+port.getName()+"(IR_Out[5:0]),";
					}else if(port.getName().equals("IRfunc1")){
						CUdef=CUdef+"."+port.getName()+"(IR_Out[20:16]),";
					}else if(port.getName().equals("IRfunc2")){
						CUdef=CUdef+"."+port.getName()+"(IR_Out[25:21]),";
					}else{
						if(port.getName().equals("Func")){
							componentConnectDef=componentConnectDef+"wire [5:0]CU_Func;\n";
						}else if(port.getName().equals("DMemFunc")){
							componentConnectDef=componentConnectDef+"wire [5:0]CU_DMemFunc;\n";
						}else if(port.getName().equals("ExCode")){
							componentConnectDef=componentConnectDef+"wire [4:0]CU_ExCode;\n";
						}else if(port.getName().equals("TrapAddr")){
							componentConnectDef=componentConnectDef+"wire [31:0]CU_TrapAddr;\n";
						}
						
						CUdef=CUdef+"."+port.getName()+"("+port.getPortInstance()+"),";
					}
				}
			}
		}
		CUdef=CUdef.substring(0, CUdef.length()-1);
		CUdef=CUdef+");\n";
		code=code+muxConnectDef+componentConnectDef+muxDelication+componentDelication+CUdef+"endmodule\n";

//		System.out.println(code);
		try {
			FileWriter fw=new FileWriter("CPU.v");
			fw.write(code);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CUCodeGen(){
		String code="";
		String begin="module CU_module(";
		String inoutdef="";
		ComputeComponent cu=unitInfo.getComputeUnitMap().get("CU");
		ArrayList<ComputeComponentPort> portList=cu.getPortList();
		for(int j=0;j<portList.size();j++){
			ComputeComponentPort port=portList.get(j);
			boolean portAppeared=port.isAppeared();
			String portName=port.getName();
			String inout=port.getInOut();
			String type=port.getPortType();
			int size=port.getSize();
			if(portName.equals("clr")||portName.equals("clk")){
				begin=begin+portName+",";
				inoutdef=inoutdef+"input "+portName+";\n";
			}else{
				if(portAppeared){
					begin=begin+portName+",";
					if(size==1){
						inoutdef=inoutdef+inout+" "+portName+";\n";
					}else{
						inoutdef=inoutdef+inout+" ["+(size-1)+":0]"+portName+";\n";
					}
				}
			}
		}
		inoutdef=inoutdef+"wire P0,P1,P2,P3,P4;\n";
		begin=begin.substring(0, begin.length()-1);
		begin=begin+");\n";
		String ctrlvalueassign="";
		for(int i=0;i<muxList.size();i++){
			Mux element=muxList.get(i);
			ArrayList<MuxChannel> channels=element.getChannels();
			for(int j=0;j<channels.size();j++){
				MuxChannel ch=channels.get(j);
				ctrlvalueassign=ctrlvalueassign+"assign "+ch.getCtrlname()+"="+ch.getCtrlvalue()+";\n";
			}
		}
		Iterator<Entry<String, DataPathInfo>> iter=mergedRegs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,DataPathInfo> entry = (Map.Entry<String,DataPathInfo>) iter.next();
			DataPathInfo infoelement=entry.getValue();
			String regsctrlname=infoelement.getCtrlname();
			String regsctrlvalue=infoelement.getCtrlname();
			ctrlvalueassign=ctrlvalueassign+"assign "+regsctrlname+"="+regsctrlvalue+";\n";
		}
		String initialcode=getInitial(begin,oPandFUNCinfo);
		code=begin+inoutdef+initialcode+ctrlvalueassign+"endmodule\n";
//		System.out.println(code);
		
		try {
			FileWriter fw=new FileWriter("CU.v");
			fw.write(code);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getInitial(String UCinout,HashMap<String, OPandFUNCpair> oPandFUNCinfo){
		HashMap<String, String> opAndFunc=new HashMap<String, String>();
		String code="Initial_model Initial(.clr(clr),.clk(clk),";
		String initialmodule="module Initial_model(clr,clk,IRop,IRfunc,IRfunc1,IRfunc2,Func,DMemFunc,ExCode,TrapAddr,P0,P1,P2,P3,P4,";
		if(UCinout.contains(",IRop,")){
			code=code+".IRop(IRop),";
		}
		if(UCinout.contains(",IRfunc,")){
			code=code+".IRfunc(IRfunc),";
		}
		if(UCinout.contains(",IRfunc1,")){
			code=code+".IRfunc1(IRfunc1),";
		}
		if(UCinout.contains(",IRfunc2,")){
			code=code+".IRfunc2(IRfunc2),";
		}
		if(UCinout.contains(",IR,")){
			code=code+".IR(IR),";
		}
		if(UCinout.contains(",Func,")){
			code=code+".Func(Func),";
		}
		if(UCinout.contains(",DMemFunc,")){
			code=code+".DMemFunc(DMemFunc),";
		}
		if(UCinout.contains(",ExCode,")){
			code=code+".ExCode(ExCode),";
		}
		if(UCinout.contains(",TrapAddr,")){
			code=code+".TrapAddr(TrapAddr),";
		}
		code=code+".P0(P0),.P1(P1),.P2(P2),.P3(P3),.P4(P4),";
		for(int i=0;i<oPandFUNCinfo.size();i++){
			String key=(i+1)+"";
			OPandFUNCpair value=oPandFUNCinfo.get(key);
			String op="CtrlOP"+key+"_"+value.getOP();
			String func="CtrlFunc"+key+"_"+value.getFUNC();
			String opvalue=value.getOpexp();
			String funcvalue=value.getFuncexp();
			if(opAndFunc.containsKey(op)==false){
				opAndFunc.put(op, opvalue);
			}
			int funcsize=value.getFuncsize();
			if(funcsize>0){
				if(opAndFunc.containsKey(func)==false){
					opAndFunc.put(func, funcvalue);
				}
			}
		}
		String initialmodulewiredef="";
		Iterator<Entry<String, String>> iter=opAndFunc.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>) iter.next();
			String key=entry.getKey();
			String value=entry.getValue();
			code=code+"."+key+"("+key+"),";
			System.out.println("assign "+key+"="+value+";");
			initialmodule=initialmodule+key+",";
			initialmodulewiredef=initialmodulewiredef+"output "+key+";\n";
		}
		initialmodule=initialmodule.substring(0, initialmodule.length()-1)+");\n";
		System.out.println(initialmodule);
		System.out.println(initialmodulewiredef);
		code=code.substring(0, code.length()-1);
		code=code+");\n";
		return code;
	}*/
}
