package proving_model;

public class ConditionValue {
	private String port;
	private int value;
	private String original;
	public ConditionValue(String port){
		this.port = port;
		this.original = "";
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public void setOriginal(String original){
		this.original = original;
	}
	
	public String getPort(){
		return port;
	}
	
	public int getValue(){
		return value;
	}
	
	public String getOriginal(){
		return original;
	}
	
	public boolean isSet(){
		if (value == 1){
			return true;
		}
		else{
			return false;
		}
	}
}
