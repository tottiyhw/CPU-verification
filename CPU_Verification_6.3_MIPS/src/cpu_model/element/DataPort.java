package cpu_model.element;

import java.util.ArrayList;

import cpu_model.dlu.DLU;
import proving_model.Procedure;

public class DataPort extends Element {

	private Procedure portData = null;	
	private ArrayList<Procedure> pathList = new ArrayList<Procedure>();
		
	public DataPort(DLU dlu, String name){				
		super(dlu, name);							
	}
	
	public void clear() {
		portData = null;
		pathList = new ArrayList<Procedure>();
	}
	
	public void setPortData(Procedure pd) {
		this.portData = pd;
	}
	
	public Procedure getPortData() {
		return portData;
	}
	
	public ArrayList<Procedure> getPathList() {
		return pathList;
	}
	
	public boolean hasData() {
		return (portData != null);
	}
	
	public Data getData() {
		return portData.getData();
	}
	
	public boolean hasPath() {
		return !pathList.isEmpty();
	}
	
	public void setPathList(ArrayList<Procedure> pds) {
		pathList = pds;		
	}
	
	public void addToPathList(Procedure pd) {
		pathList.add(pd);
	} 
	
	public boolean equals(DataPort d) {
		return this.nameIs(d.getName());
	}
	
}
