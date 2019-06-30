package mipsCPUGen_util;

public class CompilingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CompilingException(String message){
		super(message);
	}
	public String toString(){
		return this.getMessage();
	}

}
