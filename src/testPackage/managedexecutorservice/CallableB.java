package testPackage.managedexecutorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CallableB implements Callable<String> {
	private static int countB = 0;
	private final int id = countB++;
	public String status() {
		return "CallableB: id=" + id;
	}
	public String call() {
		int i = 0;
		while(true) {
			System.out.println(status());
			try {
				TimeUnit.SECONDS.sleep(2);			
			} catch (Exception e) {
				System.out.println("child thread get Exception when sleep");
				System.out.println(e);
			}
			i++;
			if(i > 2) {
				return status() + " finish";
			}
			//ThreadInfo.findAllThreads();
			//Thread.yield();
		}
  }
}