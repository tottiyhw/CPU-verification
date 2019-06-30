package mipsCPUGen_util;

import java.util.Comparator;

import mipsCPUGen_DatapathGenerate.DataPathInfo;

public class myComparatorDatapath implements Comparator<DataPathInfo>{

	@Override
	public int compare(DataPathInfo o1, DataPathInfo o2) {
		// TODO Auto-generated method stub
		int type1=o1.getCtrlType();
		int type2=o2.getCtrlType();
		
		if(type1==CPUConstants.RegsCtrl&&type2==CPUConstants.RegsCtrl){
			return o1.getSource().compareTo(o2.getSource());
		}else if(type1==CPUConstants.MuxConnect&&type2==CPUConstants.MuxConnect){
			String mux1=o1.getMux();
			String mux2=o2.getMux();
			if(mux1.equals(mux2)){
				if(o1.getSource().equals(o2.getSource())){
					if(o1.getClk().equals(o2.getClk())){
						return o1.getLineNO()-o2.getLineNO();
//						return o1.getPathNO().compareTo(o2.getPathNO());
					}
					return o1.getClk().compareTo(o2.getClk());
				}
				return o1.getSource().compareTo(o2.getSource());
			}else{
				return mux1.compareTo(mux2);
			}
		}else{
			return  o1.getSource().compareTo(o2.getSource());
		}
	}
}
