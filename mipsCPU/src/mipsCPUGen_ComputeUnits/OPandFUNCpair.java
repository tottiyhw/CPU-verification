package mipsCPUGen_ComputeUnits;

public class OPandFUNCpair {
	private String OP=null;
	private String FUNC=null;
	private int funcsize=0;
	private String opexp=null;
	private String funcexp=null;
	private String funcpart=null;

	public OPandFUNCpair(String OP,String FUNC,int funcsize, String funcpart){
		this.OP=OP;
		this.FUNC=FUNC;
		this.funcsize=funcsize;
		this.funcpart=funcpart;
		this.computeOPExpression();
		this.computeFuncExpression();
	}
	
	private void computeOPExpression(){
		this.opexp=getExpression("Op",hexToBinary(OP,6));
	}
/*	private void computeFuncExpression(){
		if(FUNC==null){
			this.funcexp=null;
		}else{
			this.funcexp=getExpression("IRFunc",hexToBinary(FUNC,6));
		}
	}*/
	private void computeFuncExpression(){
		if(FUNC==null){
//			System.out.println("此通路的func位为空！！！");
			this.funcexp=null;
		}else{
			this.funcexp=getExpression("IR"+funcpart,hexToBinary(FUNC,funcsize));
		}
	}

	public String getOP() {
		return OP;
	}

	public String getFUNC() {
		return FUNC;
	}

	public int getFuncsize() {
		return funcsize;
	}

	public String getOpexp() {
		return opexp;
	}

	public String getFuncexp() {
		return funcexp;
	}
	

	public String getFuncpart() {
		return funcpart;
	}

	public void setFuncpart(String funcpart) {
		this.funcpart = funcpart;
	}

	private String hexToBinary(String hex,int size){
		String binaryResult="";
		String result="";
		for(int i=0;i<hex.length();i++){
			char c=hex.charAt(i);
			switch(c){
			case '0':
				result="0000";
				break;
			case '1':
				result="0001";
				break;
			case '2':
				result="0010";
				break;
			case '3':
				result="0011";
				break;
			case '4':
				result="0100";
				break;
			case '5':
				result="0101";
				break;
			case '6':
				result="0110";
				break;
			case '7':
				result="0111";
				break;
			case '8':
				result="1000";
				break;
			case '9':
				result="1001";
				break;
			case 'a':
				result="1010";
				break;
			case 'b':
				result="1011";
				break;
			case 'c':
				result="1100";
				break;
			case 'd':
				result="1101";
				break;
			case 'e':
				result="1110";
				break;
			case 'f':
				result="1111";
				break;
			}
			binaryResult=binaryResult+result;
		}
		if(size==6)
			binaryResult=binaryResult.substring(2);
		else if(size==5)
			binaryResult=binaryResult.substring(3);
		else
			System.out.println("长度错误"+hex+":"+size);
		return binaryResult;
	}
	private String getExpression(String name,String value){
		int size=value.length();
		String result="";
		for(int i=0;i<size;i++){
			char c=value.charAt(i);
			String resulttemp="";
			if(c=='0'){
				resulttemp="~"+name+"["+(size-1-i)+"]";
			}else{
				resulttemp=name+"["+(size-1-i)+"]";
			}
			if(i==0){
				result=resulttemp;
			}else{
				result=result+"&"+resulttemp;
			}
		}
		return result;
	}
}
