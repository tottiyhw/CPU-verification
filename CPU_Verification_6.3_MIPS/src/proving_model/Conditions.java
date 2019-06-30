package proving_model;

import java.util.ArrayList;
import java.util.Stack;

public class Conditions {
	static ArrayList<ConditionValue> conds;
	static String[] certainVal = {"CU.IMemHit", "CU.DMemHit"};
	static int start = 0;
	static int readyNum = 0;
	public Conditions(){
		conds = new ArrayList<ConditionValue>();
		start = 0;
		readyNum = 0;
	}
	
	public static void clearConds(){
		conds = new ArrayList<ConditionValue>();
		start = 0;
		readyNum = 0;
	}
	
	public static void add(String str){
		int s = 0, i = 0;
		while (i < str.length()){
			if (!isOP(str.charAt(i))){
				s = i;
				i++;
				while (i < str.length() && !isOP(str.charAt(i))){
					i++;
				}
				String temp = str.substring(s, i);
				if (indexOf(temp) == -1){
					conds.add(new ConditionValue(temp));
				}
				i++;
			}
			else{
				i++;
			}
		}
	}
	
//	判断条件项是否正确
	public static boolean judge(String str){
		boolean result, cur, pre;
		int i, s = 0;
		char c;
		result = false;
		try{
			Stack<Character> opStack = new Stack<Character>();
			Stack<Boolean> valStack = new Stack<Boolean>();
			for (i = 0; i < str.length(); i++){
	//			System.out.println(i + str);
				if (!isOP(str.charAt(i))){
					s = i;
					i++;
					while (i < str.length() && !isOP(str.charAt(i))){
						i++;
					}
					String temp = str.substring(s, i);
	//				System.out.print(temp + ":");
					if (indexOf(temp) >= 0){
						cur = conds.get(indexOf(temp)).isSet();
	//					System.out.println(cur);
						if (opStack.isEmpty()){
							valStack.push(cur);
						}
						else{
							c = opStack.peek();
							if (c == '('){
								valStack.push(cur);
							}
							else if (c == '&'){
								pre = valStack.pop();
								valStack.push(pre && cur);
								opStack.pop();
							}
							else if (c == '|'){
								pre = valStack.pop();
								valStack.push(pre || cur);
								opStack.pop();
							}
							else if (c == '~'){
								valStack.push(!cur);
								opStack.pop();
							}
						}
	//					System.out.println("#" + valStack.peek());
					}
					i--;
				}
				else{
					if (str.charAt(i) != ' '){
						c = str.charAt(i);
						if (c == '(' || c == '&' || c == '|' || c == '~'){
							opStack.push(c);
						}
						else if (c == ')'){
							if (opStack.peek() == '('){
								opStack.pop();
							}
							else{
								while (!opStack.empty() && opStack.peek() != '('){
									char t = opStack.pop();
									if (t == '~'){
										valStack.push(!valStack.pop());
									}
									else{
										cur = valStack.pop();
										pre = valStack.pop();
										valStack.push(doOP(t, cur, pre));
									}
								}
								if (!opStack.empty() && opStack.peek() == '('){
									opStack.pop();
								}
							}
						}
					}
				}
			}
			while (!opStack.empty()){
				c = opStack.pop();
				if (c == '~'){
					cur = valStack.pop();
					valStack.push(!cur);
				}
				else{
	//				System.out.println(str + c);
					pre = valStack.pop();
					cur = valStack.pop();
					valStack.push(doOP(c, pre, cur));
				}
			}
			if (!valStack.isEmpty()){
				result = valStack.pop();
			}
	//		System.out.println(str + result);
			return result;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	static boolean doOP(char c, boolean pre, boolean cur){
		boolean result = false;
		if (c == '&'){
			result = pre && cur;
		}
		else if (c == '|'){
			result = pre || cur;
		}
		else{
			System.out.println("error:" + c);
		}
		return result;
	}
	
//	判断指称语义项是否正确
	public static boolean judgeOriginal(String str){
//		System.out.println(str);
		boolean result = false, cur, pre;
		int i, s = 0, left;
		char c;
		Stack<Character> opStack = new Stack<Character>();
		Stack<Boolean> valStack = new Stack<Boolean>();
		for (i = 0; i < str.length(); i++){
			if (!isOP(str.charAt(i))){ //c == '(' || c == ')' || c == '&' || c == '|' || c == '~'
				s = i;
				left = 0;
				i++;
				while (i < str.length()){
//					if (str.equals("DCacheHit(AddrSel(rA,a)+{16{d[0]},d})")){
//						System.out.println(i + "," + left);
//					}
					if (!isOP(str.charAt(i))){
						i++;
					}
					else if (left > 0){
						if (str.charAt(i) == ')'){
							left--;
						}
						else if(str.charAt(i) == '('){
							left++;
						}
						i++;
					}
					else if (str.charAt(i) == '(' && !isOP(str.charAt(i - 1))){
						i++;
						left++;
					}
					else{
						break;
					}
				}
				String temp = str.substring(s, i);
//				System.out.println(temp);
				if (indexOfOriginal(temp) >= 0){
					cur = conds.get(indexOfOriginal(temp)).isSet();
					if (opStack.isEmpty()){
						valStack.push(cur);
					}
					else{
						c = opStack.peek();
						if (c == '('){
							valStack.push(cur);
						}
						else if (c == '&'){
							pre = valStack.pop();
							valStack.push(pre && cur);
							opStack.pop();
						}
						else if (c == '|'){
							pre = valStack.pop();
							valStack.push(pre || cur);
							opStack.pop();
						}
						else if (c == '~'){
							valStack.push(!cur);
							opStack.pop();
						}
					}
				}
				i--;
			}
			else{
				c = str.charAt(i);
				if (c == '(' || c == '&' || c == '|' || c == '~'){
					opStack.push(c);
				}
				else if (c == ')'){
					if (opStack.peek() == '('){
						opStack.pop();
					}
					else{
						while (!opStack.empty() && opStack.peek() != '('){
							char t = opStack.pop();
							if (t == '~'){
								valStack.push(!valStack.pop());
							}
							else{
								cur = valStack.pop();
								pre = valStack.pop();
								valStack.push(doOP(t, cur, pre));
							}
						}
						if (!opStack.empty() && opStack.peek() == '('){
							opStack.pop();
						}
					}
				}
			}
		}
		while (!opStack.empty()){
			c = opStack.pop();
			if (c == '~'){
				cur = valStack.pop();
				valStack.push(!cur);
			}
			else{
//				System.out.println(str + c);
				pre = valStack.pop();
				cur = valStack.pop();
				valStack.push(doOP(c, pre, cur));
			}
		}
		result = valStack.pop();
		return result;
	}
	
	public static ArrayList<ConditionValue> getConds(){
		return conds;
	}
	
	public static int indexOf(String str){
		int i;
		for (i = 0; i < conds.size(); i++){
			if (conds.get(i).getPort().equals(str)){
				break;
			}
		}
		if (i >= conds.size()){
			i = -1;
		}
		return i;
	}
	
	public static int indexOfOriginal(String str){
		int i;
		for (i = 0; i < conds.size(); i++){
			if (conds.get(i).getOriginal().equals(str)){
				break;
			}
		}
		if (i >= conds.size()){
			i = -1;
		}
		return i;
	}
	
	static boolean isOP(char c){
		boolean result = false;
		if (c == '(' || c == ')' || c == '&' || c == '|' || c == '~'){
			result = true;
		}
		return result;
	}
	
	static void doGenerate(ArrayList<int[]> result, int pos, int[] temp){
		if (pos < conds.size()){
			temp[pos] = 1;
			doGenerate(result, pos + 1, temp);
			temp[pos] = 0;
			doGenerate(result, pos + 1, temp);
		}
		else{
			int[] t = temp.clone();
			result.add(t);
		}
	}
	
//	产生条件值的所有组合
	public static ArrayList<int[]> generateConds(){
		ArrayList<int[]> result = new ArrayList<int[]>();
		start = 0;
		int i, j;
		for (i = 0; i < certainVal.length; i++){
			if (indexOf(certainVal[i]) != -1){
				j = indexOf(certainVal[i]);
				ConditionValue t = conds.get(start);
				conds.set(start, conds.get(indexOf(certainVal[i])));
				conds.set(j, t);
				start++;
			}
		}
		int[] temp = new int[conds.size()];
		for (i = 0; i < start; i++){
			temp[i] = 1;
		}
		doGenerate(result, start, temp);
		return result;
	}
	
	public static void setAll(int[] val){
		int i;
		for (i = 0; i < val.length; i++){
			conds.get(i).setValue(val[i]);
		}
	}
	
	public static int getStart(){
		return start;
	}
	
	public static int getReadyNum(){
		return readyNum;
	}
	
	public static int incReadyNum(){
		readyNum++;
		return readyNum;
	}
}
