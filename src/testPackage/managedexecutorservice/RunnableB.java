package testPackage.managedexecutorservice;

import java.util.concurrent.TimeUnit;

public class RunnableB implements Runnable {
	private static int countB = 0;
	private final int id = countB++;
	public String status() {
		return "RunnableB: id=" + id;
	}
	public void run() {
		int i = 0;
		while(true) {
			System.out.println(status());
			try {
				TimeUnit.SECONDS.sleep(2);			
			} catch (Exception e) {
				// TODO: handle exception
			}
			i++;
			if(i > 2) {
				System.out.println(status() + " finish");
				break;
			}
			//Thread.yield();
		}
  }
}