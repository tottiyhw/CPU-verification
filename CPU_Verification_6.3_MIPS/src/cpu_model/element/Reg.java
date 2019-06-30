package cpu_model.element;

import java.util.ArrayList;

import proving_model.Procedure;
import cpu_model.dlu.DLU;


//寄存元件
public class Reg extends Element {
	
	//与此元件有关的Procedure列表
	ArrayList<Procedure> lastContentList = new ArrayList<Procedure>();
	ArrayList<Procedure> curContentList = new ArrayList<Procedure>();
	
	public Reg(DLU dlu, String name) {
		super(dlu, name);
	}
	
	public void clear() {
		lastContentList = new ArrayList<Procedure>();
		curContentList = new ArrayList<Procedure>();		
	}
	
	public void addToLastContent(Procedure pd) {
		lastContentList.add(pd);
	}
	
	public void addToCurContent(Procedure pd) {
		curContentList.add(pd);
	}	
	
	public boolean hasLastContent() {
		return !lastContentList.isEmpty();
	}
	
	public boolean hasCurContent() {
		return !curContentList.isEmpty();
	}
		
	public Procedure getLastContent() {
		return lastContentList.get(0);
	}
	
	public Procedure getCurContent() {
		return curContentList.get(0);
	}
	
	public ArrayList<Procedure> getLastContentList() {
		return lastContentList;
	}
	
	public ArrayList<Procedure> getCurContentList() {
		return curContentList;
	}
	
	public boolean equals(Reg r) {
		return this.nameIs(r.getName());
	}
	
}
