package mipsCPUGen_ComputeUnits;

import java.util.ArrayList;

public class ComputeComponent {
	private String name=null;
	private String componentType=null;
	private ArrayList<ComputeComponentPort> portList=null;
	private boolean appeared=false;
	
	public ComputeComponent(String name,String componentType){
		this.name=name;
		this.componentType=componentType;
		this.portList=new ArrayList<ComputeComponentPort>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getComponentType() {
		return componentType;
	}

	public ArrayList<ComputeComponentPort> getPortList() {
		return portList;
	}
	public void addPort(String name,String InOut,String portType,int size){
		ComputeComponentPort port=new ComputeComponentPort(name,InOut,portType,size);
		this.portList.add(port);
	}
	public void addPort(String name,String InOut,String portType,String portInstance,int size,boolean appeared){
		ComputeComponentPort port=new ComputeComponentPort(name,InOut,portType,portInstance,size,appeared);
		this.portList.add(port);
	}
	public void addPort(ComputeComponentPort port){
		this.portList.add(port);
	}
	public ComputeComponentPort getPort(String name){
		for(int i=0;i<portList.size();i++){
			ComputeComponentPort port=portList.get(i);
			if(port.getName().equals(name)){
				return port;
			}
		}
		return null;
	}

	public boolean isAppeared() {
		return appeared;
	}

	public void setAppeared(boolean appeared) {
		this.appeared = appeared;
	}
	public void setPortAppeared(String portName){
		for(int i=0;i<portList.size();i++){
			ComputeComponentPort port=portList.get(i);
			if(port.getName().equals(portName)){
				port.setAppeared(true);
			}
		}
	}
	public void setPortInstance(String portName,String instance){
		this.getPort(portName).setPortInstance(instance);
	}
	public String getPortInstance(String portName){
		return this.getPort(portName).getPortInstance();
	}

}
