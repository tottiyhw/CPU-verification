package mipsCPUGen_util;
import java.util.Comparator;


public class myComparator implements Comparator<String>{
	
	@Override
	public int compare(String arg0, String arg1) {
		// TODO Auto-generated method stub
		String prefix="Mux";
		if(arg0.startsWith(prefix)&&arg1.startsWith(prefix)){
			if(arg0.contains("_")&&arg1.contains("_")){

				String arg0_temp=arg0.substring(3);
				String arg1_temp=arg1.substring(3);
				String[] arg0_temp_split1=arg0_temp.split("_");
				String[] arg1_temp_split1=arg1_temp.split("_");
				int arg0part1=Integer.parseInt(arg0_temp_split1[0]);
				int arg1part1=Integer.parseInt(arg1_temp_split1[0]);
				if(arg0part1>arg1part1){
					return 1;
				}else if(arg0part1<arg1part1){
					return -1;
				}else{
					int arg0part2=Integer.parseInt(arg0_temp_split1[1]);
					int arg1part2=Integer.parseInt(arg1_temp_split1[1]);
					if(arg0part2<arg1part2){
						return -1;
					}else if(arg0part2>arg1part2){
						return 1;
					}else{
						return 0;
					}
				}
			
			}else{
				String arg0_temp=arg0.substring(3);
				String arg1_temp=arg1.substring(3);
				int arg0part1=Integer.parseInt(arg0_temp);
				int arg1part1=Integer.parseInt(arg1_temp);
				if(arg0part1>arg1part1){
					return 1;
				}else if(arg0part1<arg1part1){
					return -1;
				}else{
					return 0;
				}
			}
		}else if(arg0.isEmpty()||(arg0==null)){
			return -1;
		}else{
			int result=arg0.compareTo(arg1);
			if(result<0){
				return -1;
			}else
				return 1;
		}
	}

}
