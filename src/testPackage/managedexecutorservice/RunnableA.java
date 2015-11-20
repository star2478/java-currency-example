package testPackage.managedexecutorservice;

import java.util.concurrent.TimeUnit;

public class RunnableA implements Runnable {
	private static int countA = 0;
	private final int id = countA++;
	private int stop = 0;
	public String status() {
		return "RunnableA: id=" + id;
	}
	public void run() {
		int i = 0;
		while(true) {
			System.out.println(status());
			try {
				TimeUnit.SECONDS.sleep(2);			
			} catch (Exception e) {
				//System.out.println("child thread get Exception when sleep");
				//System.out.println(e);
			}
			i++;
			if(i > 2) {
				System.out.println(status() + " finish");
				break;
			}
			if(1 == stop) {
				System.out.println("child thread stop");
				break;
			}
			//Thread.yield();
		}
	}
	public void setStop(int intStop) {
		stop = intStop;
	}
}