package mipsCPUGen_ComputeUnits;

import java.util.ArrayList;

public class Mux {
	private String name="";
	private int channelSize=0;
	private int dataSize=0;
	private ArrayList<MuxChannel> channels=new ArrayList<MuxChannel>();
	public Mux(String name){
		this.name=name;
	}
	public Mux(String name,int dataSize){
		this.name=name;
		this.dataSize=dataSize;
	}
	public int getChannelSize() {
		return channelSize;
	}
	public void setChannelSize(int channelSize) {
		this.channelSize = channelSize;
	}
	public int getDataSize() {
		return dataSize;
	}
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}
	public String getName() {
		return name;
	}
	public ArrayList<MuxChannel> getChannels() {
		return channels;
	}
	public void addChannel(MuxChannel channel){
		channels.add(channel);
		this.channelSize++;
	}
	public String getType(){
		return "Mux"+channelSize+"_"+dataSize+"bits";
	}

}
