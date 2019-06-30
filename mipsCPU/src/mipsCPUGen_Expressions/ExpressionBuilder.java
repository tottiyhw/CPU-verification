package mipsCPUGen_Expressions;

import java.util.HashMap;

import mipsCPUGen_ComputeUnits.ComputeComponetInfo;
import mipsCPUGen_DatapathGenerate.DataPathInfo;
import mipsCPUGen_util.CharReaderIterator;
import mipsCPUGen_util.CompilingException;

public class ExpressionBuilder {
	public ExpressionBuilder(){
		
	}
	
	public static IExpression create(String expr) throws CompilingException{
		expr.trim();
		CharReaderIterator iter = new CharReaderIterator(expr);
		IExpression result = create(iter);
		return result;
	}
	private static IExpression create(CharReaderIterator iter) throws CompilingException{
		if(iter.first()=='{'){	// expression like : {}
			if(iter.last()=='}'){
				CompositeExpression result=new CompositeExpression(iter);
				if(iter.getCPosition(",")!=-1){
					String itertemp[]=iter.getSplitString();
					for(int i=0;i<itertemp.length;i++){
						CharReaderIterator iiter=new CharReaderIterator(itertemp[i]);
						IExpression parameter=create(iiter);
						result.add(parameter);
					}
					return result;
				}else{
					IExpression parameter=create(iter.getExcludeSubString(0, iter.length()-1));
					result.add(parameter);
					return result;
				}
			}else{
				throw new CompilingException("扩展不匹配:" + iter);
			}
		}else if(iter.contains("||")||iter.contains("&")||iter.contains("&&")){
			LogicExpression result=new LogicExpression(iter);
			return result;
		}else if(iter.last()==']'){	// expression like : A[]
			int sizebegin=iter.getCPosition("[");
			if(sizebegin!=-1&&sizebegin!=0){
				BitSelectExpression result=new BitSelectExpression(iter);
				return result;
			}else{
				throw new CompilingException("扩展不匹配:" + iter);
			}
		}else if(iter.getCPosition("'")!=-1&&iter.current()!=0){	// expression like : 32'd0
			ConstantsExpression result=new ConstantsExpression(iter);
			return result;
		}else if(iter.toString().equals("·")||iter.toString().equals("++")||iter.toString().equals("--")
				||iter.toString().equals("<")||iter.toString().equals(">")){
			CtrlExpression result=new CtrlExpression(iter);
			return result;
		}else{	// expression like : A or A.In
			StringExpression result=new StringExpression(iter);
			return result;
		}
	}

	public static DataPathInfo parse(IExpression source,IExpression dest,
			ComputeComponetInfo unitInfo,HashMap<String, String> regs_mux) throws CompilingException{
		DataPathInfo info=new DataPathInfo();
		if(dest instanceof CtrlExpression){
			String sourceUnitName=source.getUnitName();
			String sourceNameString=source.getExpressionValue();
			String destNameString=dest.getExpressionValue();
			String sourcePortName=destNameString.replace("+", sourceNameString);
			if(sourcePortName.equals("CtrlCP0_CAUSEReg_CE"))
				System.out.println(sourcePortName);
			unitInfo.setPortAndUnit(sourceUnitName, sourcePortName, sourcePortName);
			info.setSource(source.getExpressionValue());
			info.setCtrlname(sourcePortName);
			info.setDestination(dest.toString());
			info.setCtrlType(0);
			info.setSize(1);
			info.setSourceExp(source);
			info.setDestExp(dest);
			return info;
		}else if(source.getSize()==dest.getSize()){
			if(dest instanceof StringExpression){
				String sourceexpression=source.getExpressionValue();
				String mux=regs_mux.get(dest.toString());
				if(mux==null){
					if((!dest.toString().equals("CU.Op"))&&(!dest.toString().equals("CU.IRFunc"))&&
							(!dest.toString().equals("CU.Op0"))&&(!dest.toString().equals("CU.IRFunc0"))&&
							(!dest.toString().equals("CU.Op1"))&&(!dest.toString().equals("CU.IRFunc1"))&&
							(!dest.toString().equals("CU.Op2"))&&(!dest.toString().equals("CU.IRFunc2"))){
						throw new CompilingException("目的端格式错误或目的端没有定义多路选择器：" + source.toString()+":"+
								dest.toString());
					}else{
						info.setSource(sourceexpression);
						info.setMux("");
						info.setCtrlname("");
						info.setCtrlType(3);
						info.setDestination(dest.getExpressionValue());
					}
				}else{
					info.setMux(mux);
					info.setCtrlname("Ctrl"+mux);
					info.setCtrlType(1);
					info.setSize(source.getSize());
					info.setSource(sourceexpression);
					info.setDestination(dest.getExpressionValue());
				}
				info.setSourceExp(source);
				info.setDestExp(dest);
			}
			return info;
		}else{
			throw new CompilingException("左端右端数据宽度不匹配:" + source.toString()+":"+
					dest.toString()+"\n"+":"+source.getSize()+":"+dest.getSize());
		}
	}
	
	

}
