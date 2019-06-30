package mipsCPUGen_DatapathGenerate;

public class CtrlPair {
	private String clk=null;
	private String opAndfunc=null;
	
	public CtrlPair(String clk,String opAndfunc){
		this.clk=clk;
		this.opAndfunc=opAndfunc;
	}

	public String getClk() {
		return clk;
	}

	public void setClk(String clk) {
		this.clk = clk;
	}

	public String getOpAndfunc() {
		return opAndfunc;
	}

	public void setOpAndfunc(String opAndfunc) {
		this.opAndfunc = opAndfunc;
	}
	
	

}
