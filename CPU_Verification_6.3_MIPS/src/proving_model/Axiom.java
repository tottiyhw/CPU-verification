package proving_model;

import java.util.ArrayList;

public class Axiom {
	private String name;
	private ArrayList<String> alist;
	public Axiom(String name){
		this.name = name;
		alist = new ArrayList<String>();
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void addItem(String item){
		alist.add(item);
	}
	
	public ArrayList<String> getList(){
		return alist;
	}
	
	public void setItem(int i, String str){
		alist.remove(i);
		alist.add(i, str);
	}
}
