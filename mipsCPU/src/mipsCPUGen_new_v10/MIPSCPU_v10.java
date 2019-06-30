package mipsCPUGen_new_v10;

import java.io.IOException;
import mipsCPUGen_CodeGenerate.StructGenerate;
import mipsCPUGen_DatapathGenerate.DataPathOutput;
import mipsCPUGen_DatapathGenerate.DatapathProcess;
import mipsCPUGen_DatapathGenerate.generateEveryInstructionPath;

public class MIPSCPU_v10 {

	public static void main(String[] args) throws IOException {
//		DatapathGen datapathHandle=new DatapathGen("a.xlsx");
		generateEveryInstructionPath instructionSep=new generateEveryInstructionPath("基本通路.xlsx");
	
		DatapathProcess dp=new DatapathProcess(instructionSep.getpathInfoList());
		dp.doInsertMux();
		dp.doSort();
		dp.doSort_addMuxChannel();
		dp.doMerge();
		
		DataPathOutput output=new DataPathOutput(dp);
		output.outputSimpleDatapath("output\\");
		output.outputSortedDatapath("output\\");
//		output.outputDirectMuxSortedDatapath("output\\");
		int judge=output.outputMergedMap("output\\");
//		output.outputDirectMergedMap("output\\");
		if(judge==1){
			StructGenerate sg=new StructGenerate(output.getComputeComponetInfo(),dp,output);
			System.out.println("++++++++++++++++++++top file++++++++++++++++++++++");
			sg.printTop();
			System.out.println("++++++++++++++++++++cu file++++++++++++++++++++++");
			sg.printCU();
		/*	System.out.println("********************cuNoMux file**********************");
			sg.printCUNoMux();
			*/
			System.out.println("++++++++++++++++++++initial file++++++++++++++++++++++");
			sg.printInitial();
			sg.generateFile("output\\", "");
			System.out.println("done");
		}else{
			System.out.println("Error happend");
		}
	}

}
