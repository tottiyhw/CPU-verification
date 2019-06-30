package verification;

//计时器
//负责计时，提供开始计时、停止计时、获取运行时间等方法
public class Timer {

	private static long beginTime = 0;
	private static long endTime = 0;
	
	public static void start(){	
		beginTime = System.currentTimeMillis();
	}
	
	public static void stop(){
		endTime = System.currentTimeMillis();
	}
	
	public static long getRunTime(){
		return endTime-beginTime;
	}
}
