package testPackage.managedexecutorservice;

import java.util.Map;
import java.util.Set;


public class ThreadInfo extends Thread {

	public static void printThreadStack() {
		Map<Thread, StackTraceElement[]> allThreads = Thread.
				getAllStackTraces();
		Set<Thread> keys = allThreads.keySet();
		System.out.println("print stack of thread");
		for (Thread s : keys) {
			System.out.println(s + ":\t" + allThreads.get(s));
		}
	}
	public static void findAllThreads() {
		System.out.println("begin to get thread info============");
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;

		// 遍历线程组树，获取根线程组
		while ( group != null ) {
			topGroup = group;
			group = group.getParent();
		}
		// 激活的线程数加倍
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		//获取根线程组的所有线程
		int actualSize = topGroup.enumerate(slackList);
		// copy into a list that is the exact size
		Thread[] list = new Thread[actualSize];
		System.arraycopy(slackList, 0, list, 0, actualSize);
		System.out.println("Thread list size == " + list.length);  
		for (Thread thread : list) {  
		    System.out.println(thread.getName());  
		}
		System.out.println("end get thread info============");
	} 
}