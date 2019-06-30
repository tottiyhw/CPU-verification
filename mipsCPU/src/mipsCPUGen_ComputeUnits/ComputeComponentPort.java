package mipsCPUGen_ComputeUnits;

public class ComputeComponentPort {
	private String name=null;
	private String InOut=null;
	private String portType=null;
	private String portInstance=null;
	private int size=0;
	private boolean appeared=false;
	
	public ComputeComponentPort(String name){
		this.name=name;
	}
	public ComputeComponentPort(String name,String InOut,String portType,int size){
		this.name=name;
		this.InOut=InOut;
		this.portType=portType;
		this.size=size;
	}
	public ComputeComponentPort(String name,String InOut,String portType,String portInstance,int size,boolean appeared){
		this.name=name;
		this.InOut=InOut;
		this.portType=portType;
		this.portInstance=portInstance;
		this.size=size;
		this.appeared=appeared;
	}
	public String getName() {
		return name;
	}
	public String getInOut() {
		return InOut;
	}
	public String getPortType() {
		return portType;
	}
	public String getPortInstance() {
		return portInstance;
	}
	public void setPortInstance(String portInstance) {
		this.portInstance = portInstance;
	}
	public int getSize() {
		return size;
	}
	public boolean isAppeared() {
		return appeared;
	}
	public void setAppeared(boolean appeared) {
		this.appeared = appeared;
	}
	
	

}
