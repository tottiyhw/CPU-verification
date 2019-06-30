package cpu_model.dlu;

import cpu_model.cpu.CPU;
import cpu_model.element.*;
import proving_model.*;

public class Path {
			
	public static void applyTheorems(DataPort port1) {
		
		_Path(port1);
		
	}
		
	private static void _Path(DataPort port1) {
		
		//Path
		//P=data, P=>Q |- Q=data
		
		if (port1.dluNameIs("ConstUnit") || !port1.hasData() || !port1.hasPath())
			return;
					
		for (Procedure b : port1.getPathList()) {
			
			DataPort port2 = b.getPort2();
			
			if (port2.hasData())
				continue;
		
			Procedure a = port1.getPortData();
			
			Data data = port1.getData();
			
			Formula f = new PortDataFormula(port2, data);
			Justification j = new Justification("Path", a, b);
			Proof.add(f, j);
			
		}
		
	}
	
}
