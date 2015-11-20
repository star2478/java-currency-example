package testPackage.managedexecutorservice;

import java.util.*;
import java.util.concurrent.*;

class TestScheduledExecutorService {
	public static ScheduledExecutorService smes;
	
	public static void main(String[] args) {
		TestScheduledExecutorService obj = new TestScheduledExecutorService();
		obj.initGlobalObj();
		obj.testScheduleAtFixedRate();
	}
	
	// 初始化全局ScheduledExecutorService对象smes
	public void initGlobalObj() {
		// 最多分配threadNum个线程来执行threadNum个任务
		int threadNum = 3;
		smes = Executors.newScheduledThreadPool(threadNum);
		
		// 仅分配一个线程来执行一个任务
		//smes = Executors.newSingleThreadScheduledExecutor();
	}

	// 测试ScheduledExecutorService.scheduleAtFixedRate(Runnable, initDelay, period, unit)
	public void testScheduleAtFixedRate() {
		System.out.println("testing scheduleAtFixedRate()");
		
		// initDelay秒后，每隔period秒执行一个RunnableA任务
		int initDelay = 1;
		int period = 5;
		final RunnableA taskSchedule = new RunnableA();
		final ScheduledFuture<?> resultHandle = smes.scheduleAtFixedRate(taskSchedule, initDelay, period, TimeUnit.SECONDS);
		
		// 新建一个任务task
		Runnable task = new Runnable() {
			public void run() {
				System.out.println("start to stop task");
				// 中止RunnableA定时任务线程
				// 但不会中止当前运行中的任务，只会中止那些正在等待运行的任务
				resultHandle.cancel(true);
				//设置停止位，中止当前正在运行的任务
				taskSchedule.setStop(1);
				//ThreadInfo.findAllThreads();
				// 没有shutdown的话，smes还会继续接收定时任务，即使这些任务不会再运行了，最终导致整个程序无法退出
				smes.shutdown();
			}
		};
		
		// timeBeforeRun秒后执行task任务
		int timeBeforeRun = 3;
		//Executors.newSingleThreadScheduledExecutor().schedule(task, timeBeforeRun, TimeUnit.SECONDS);
		smes.schedule(task, timeBeforeRun, TimeUnit.SECONDS);
		System.out.println("main thread finish!");
		//smes.shutdownNow();
	}
 }