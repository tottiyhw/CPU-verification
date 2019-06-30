package mipsCPUGen_util;

import java.util.Comparator;

public class myComparator1 implements Comparator<String> {

	@Override
	public int compare(String arg0, String arg1) {
		int loc0=arg0.indexOf("_");
		int loc1=arg1.indexOf("_");
		int arg0part0=Integer.parseInt(arg0.substring(0, loc0));
		int arg0part1 = Integer.parseInt(arg0.substring(loc0+1));
		int arg1part0=Integer.parseInt(arg1.substring(0, loc1));
		int arg1part1 = Integer.parseInt(arg1.substring(loc1+1));
		
		if (arg0part0>arg1part0) {
			return 1;
		} else if (arg0part0<arg1part0) {
			return -1;
		} else {
			if (arg0part1>arg1part1) {
				return 1;
			} else if (arg0part1<arg1part1) {
				return -1;
			} else
				return 0;
		}
	}

}
