package mipsCPUGen_ComputeUnits;

public class MuxChannel {
	private String name="";
	private String ctrlname="";
	private String ctrlvalue="";
	private String source="";
	private int size=0;
	
	public MuxChannel(String name,String ctrlname,String ctrlvalue,String source,int size){
		this.name=name;
		this.ctrlname=ctrlname;
		this.ctrlvalue=ctrlvalue;
		this.source=source;
		this.size=size;
	}
	public MuxChannel(String name){
		this.name=name;
	}

	public String getCtrlname() {
		return ctrlname;
	}

	public void setCtrlname(String ctrlname) {
		this.ctrlname = ctrlname;
	}

	public String getCtrlvalue() {
		return ctrlvalue;
	}

	public void setCtrlvalue(String ctrlvalue) {
		this.ctrlvalue = ctrlvalue;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}
	

}
