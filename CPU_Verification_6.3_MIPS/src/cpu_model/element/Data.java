package cpu_model.element;

public class Data {

	private String name;
	
	public Data(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean nameIs(String name) {
		return this.name.equals(name);
	}
	
	public boolean equals(Data d) {
		return this.nameIs(d.getName());
	}
		
}
