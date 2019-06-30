package verification;

//��ʱ��
//�����ʱ���ṩ��ʼ��ʱ��ֹͣ��ʱ����ȡ����ʱ��ȷ���
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
