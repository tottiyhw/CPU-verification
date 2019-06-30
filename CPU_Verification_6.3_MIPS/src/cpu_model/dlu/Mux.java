package cpu_model.dlu;

import java.util.ArrayList;

import cpu_model.element.CtrlPort;
import cpu_model.element.Data;
import cpu_model.element.DataPort;
import proving_model.Formula;
import proving_model.Justification;
import proving_model.PortDataFormula;
import proving_model.Procedure;
import proving_model.Proof;

public class Mux extends DLU {

	private int inPortNum;
	private ArrayList<DataPort> In;
	private ArrayList<CtrlPort> Ctrl;
	private DataPort Out;
		
	public Mux(int muxNum, int inPortNum) {
		
		this.name = "Mux" + muxNum;
		this.inPortNum = inPortNum;
		
		In = new ArrayList<DataPort>();
		for (int i = 1; i <= inPortNum; i++)
			In.add(addDataPort(this, name + "." + i));
		
		Ctrl = new ArrayList<CtrlPort>();
		for (int i = 1; i <= inPortNum; i++)
			Ctrl.add(addCtrlPort(this, "Ctrl" + name + "." + i));
		
		Out = addDataPort(this, name + ".Out");		
		
	}
	
	public void applyTheorems() {
		
		_Mux();
				
	}
	
	private void _Mux() {
		
		//Mux
		//Muxn.k=data, CtrlMuxn.0=0, ..., CtrlMuxn.k=1, ..., CtrlMuxn.m=0, 
		//|- Muxn.Out=data
				
		//out已有数据，退出
		if (Out.hasData())
			return;
		
		//遍历控制端口，把为1的总数存到num中，把为1的编号存到k中
		int num = 0;
		int k = 0;
		for (int i = 0; i < inPortNum; i++) {
			
			CtrlPort c = Ctrl.get(i);
			
			if (c.isActive()) {
				num++;
				k = i;
				//有信号的口没数据，退出
				if (!In.get(i).hasData())
					return;
			}
			else if (c.notActive()) {
				
			}
			//有既不为0也不为1的控制端口，退出
			else
				return;
			
			//有多个为1的控制端口，退出
			if (num > 1)
				return;
		}
		
		//一个为1的控制端口也没有，退出
		if (num == 0)
			return;
		
		ArrayList<Procedure> pds = new ArrayList<Procedure>();
		Procedure a = In.get(k).getPortData();
		pds.add(a);
		for (int i = 0; i < inPortNum; i++)
			pds.add(Ctrl.get(i).getCtrlSignal());
		
		Data data = a.getData();
				
		Formula f = new PortDataFormula(Out, data);
		Justification j = new Justification("Mux", pds);
		Procedure b = new Procedure(f, j);
		Proof.add(b);
		
	}
	
}
