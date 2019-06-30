package mipsCPUGen_DatapathGenerate;

import java.util.ArrayList;

import mipsCPUGen_Expressions.IExpression;
import mipsCPUGen_util.CPUConstants;

public class DataPathInfo {
	private int lineNO=-1;
	private int inLine=0;
	private int pathNO=-1;
	private String pathName=null;
	private String stage=null;
	private String clk=null;
	private String source=null;
	private String mux=null;
	private String muxchannel=null;
	private String destination=null;
	private String factor=null;
	private String ctrlname=null;
	private String ctrlvalueCLKpart=null;
	private String OPpart=null;
	private String FUNCpart=null;
	private String ctrlvalueOPandFUNCpart=null;
	private int ctrlType=CPUConstants.InitData;		//0表示寄存器控制信号，1表示多路选择器控制信号，在信号合并的时候有用,3表示直连不通过多路选择器
	private int size=0;
	private ArrayList<CtrlPair> CtrlPair=new ArrayList<CtrlPair>(); 
	private IExpression sourceExp=null;
	private IExpression destExp=null;
	
	public DataPathInfo(){
		
	}
	
	public DataPathInfo(int lineNO, int inLine, int pathNO, String stage, String clk, String source,
			String destination, String factor, String pathName) {
		super();
		this.lineNO = lineNO;
		this.inLine=inLine;
		this.pathNO = pathNO;
		this.stage = stage;
		this.clk = clk;
		this.source = source;
		this.destination = destination;
		this.factor = factor;
		this.pathName=pathName;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public int getPathNO() {
		return pathNO;
	}

	public void setPathNO(int pathNO) {
		this.pathNO = pathNO;
	}

	public int getInLine() {
		return inLine;
	}

	public void setInLine(int inLine) {
		this.inLine = inLine;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getClk() {
		return clk;
	}

	public void setClk(String clk) {
		this.clk = clk;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMux() {
		return mux;
	}

	public void setMux(String mux) {
		this.mux = mux;
	}

	public String getMuxchannel() {
		return muxchannel;
	}

	public void setMuxchannel(String muxchannel) {
		this.muxchannel = muxchannel;
	}

	public String getDestination() {
		return destination;
	}
	
	public String getFactor() {
		return factor;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCtrlname() {
		return ctrlname;
	}

	public void setCtrlname(String ctrlname) {
		this.ctrlname = ctrlname;
	}

	public int getLineNO() {
		return lineNO;
	}

	public void setLineNO(int lineNO) {
		this.lineNO = lineNO;
	}

	public String getCtrlvalueCLKpart() {
		return ctrlvalueCLKpart;
	}

	public void setCtrlvalueCLKpart(String ctrlvalueCLKpart) {
		this.ctrlvalueCLKpart = ctrlvalueCLKpart;
	}
	
	public String getOPpart() {
		return OPpart;
	}

	public void setOPpart(String oPpart) {
		OPpart = oPpart;
	}

	public String getFUNCpart() {
		return FUNCpart;
	}

	public void setFUNCpart(String fUNCpart) {
		FUNCpart = fUNCpart;
	}

	public String getCtrlvalueOPandFUNCpart() {
		return ctrlvalueOPandFUNCpart;
	}

	public void setCtrlvalueOPandFUNCpart(String ctrlvalueOPandFUNCpart) {
		this.ctrlvalueOPandFUNCpart = ctrlvalueOPandFUNCpart;
	}

	public int getCtrlType() {
		return ctrlType;
	}

	public void setCtrlType(int ctrlType) {
		this.ctrlType = ctrlType;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public IExpression getSourceExp() {
		return sourceExp;
	}

	public void setSourceExp(IExpression sourceExp) {
		this.sourceExp = sourceExp;
	}

	public IExpression getDestExp() {
		return destExp;
	}

	public void setDestExp(IExpression destExp) {
		this.destExp = destExp;
	}
	
	public ArrayList<CtrlPair> getCtrlPair() {
		return CtrlPair;
	}

	public void setCtrlPair(ArrayList<CtrlPair> ctrlPair) {
		CtrlPair = ctrlPair;
	}


}
