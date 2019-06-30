package mipsCPUGen_util;

public class CharReaderIterator {
	private String str = null;

	private int position = 0;

	public CharReaderIterator(String str) {
		this.str = str.trim();
	}

	public void next() {
		position++;
	}

	public char current() {
		if (position < str.length()) {
			return str.charAt(position);
		}
		return 0;
	}
	
	public char first() {
		return str.charAt(0);
	}
	
	public char last() {
		return str.charAt(str.length()-1);
	}

	public void skipWhiteSpace() {
		while (position < str.length()
				&& Character.isWhitespace(str.charAt(position))) {
			position++;
		}
	}
	
	public int length(){
		return str.length();
	}
	
	public boolean contains(String c){
		return str.contains(c);
	}
	
	public int getCPosition(String string){
		return str.indexOf(string);
	}
	
	public String getIncludeSubString(int begin,int end){
		return str.substring(begin, end);
	}

	public String getExcludeSubString(int begin,int end){
		return str.substring(begin+1, end);
	}
	
	public String toString() {
		return str;
	}
	
	public String[] getSplitString(){
		return str.substring(1, str.length()-1).split(",");
	}
}
