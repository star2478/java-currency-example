package testPackage.managedexecutorservice;

import java.util.*;
import java.util.ArrayList;
import java.util.concurrent.*;

class TestExecutorService {
	public static ExecutorService mes;
	
	public static void main(String[] args) {
		TestExecutorService obj = new TestExecutorService();
		obj.initGlobalObj();
		obj.testExecute();
		//obj.testSubmitRunnable();
		//obj.testSubmitCallable();
		//obj.testInvokeAll();
		//ThreadInfo.findAllThreads();
	}
	
	// 初始化全局ExecutorService对象mes
	public void initGlobalObj() {
		// 最多分配threadNum个线程来执行threadNum个任务
		int threadNum = 2;
		mes = Executors.newFixedThreadPool(threadNum);
		
		// 仅分配一个线程来执行一个任务
		//mes = Executors.newSingleThreadExecutor();
		
		// 线程数目动态分配
		//mes = Executors.newCachedThreadPool();
	}
	
	// 测试ExecutorService.execute()
	public void testExecute() {
		System.out.println("testing execute()");
		
		// 开始
		for (int i = 0; i < 5; i++) {
			mes.execute(new RunnableA());
		}
		mes.shutdown();
	}

	// 测试ExecutorService.submit(Runnable)
	public void testSubmitRunnable() {
		System.out.println("testing submitRunnable()");
		ArrayList<Future<?>> resultsArrayList = new ArrayList<Future<?>>();
		
		// 开始
		for (int i = 0; i < 5; i++) {
			resultsArrayList.add((Future<?>)mes.submit(new RunnableB()));
		}
		// 通过Future监控线程状态
		for(Future<?> fsFuture : resultsArrayList) {
			try {
				if(!fsFuture.isDone()) {
					System.out.println("current cycle thread not done");
				}
				// 子线程没有结束的话，主线程会在get()处被堵塞住
				fsFuture.get();
				
			} catch(InterruptedException e) {
				System.out.println("get InterruptedException");
				System.out.println(e);
			} catch(ExecutionException e) {
				System.out.println("get ExecutionException");
				System.out.println(e);				
			} finally {
				System.out.println("current cycle child thread done");
			}
		}
		mes.shutdown();
	}

	// 测试ExecutorService.submit(Callable)
	public void testSubmitCallable() {
		System.out.println("testing submitCallable()");
		ArrayList<Future<String>> resultsArrayList = new ArrayList<Future<String>>();
		
		// 开始
		for (int i = 0; i < 5; i++) {
			resultsArrayList.add(mes.submit(new CallableA()));
		}
		// 通过Future监控线程状态
		for(Future<String> fsFuture : resultsArrayList) {
			try {
				if(!fsFuture.isDone()) {
					System.out.println("current cycle thread not done");
				}
				// 子线程没有结束的话，主线程会在get()处被堵塞住
				System.out.println(fsFuture.get());
			} catch(InterruptedException e) {
				System.out.println("get InterruptedException");
				System.out.println(e);
			} catch(ExecutionException e) {
				System.out.println("get ExecutionException");
				System.out.println(e);				
			} finally {
				System.out.println("current cycle child thread done");
			}
		}
		mes.shutdown();
	}

	// 测试ExecutorService.invokeAll(Callable, timeout, unit)
	public void testInvokeAll() {
		System.out.println("testing invokeAll()");
        List<CallableA> taskList = new ArrayList<CallableA>(); 
		
		// 开始
		for (int i = 0; i < 5; i++) {
			taskList.add(new CallableA());
		}
		try {
			// invokeAll一直堵住，直到所有任务执行完或超时
			//List<Future<String>> resultsList = mes.invokeAll(taskList);
			List<Future<String>> resultsList = mes.invokeAll(taskList, 3, TimeUnit.SECONDS);
			
			for(Future<String> fsFuture : resultsList) {
				// 执行到此时
				if(!fsFuture.isDone()) {
					System.out.println("current cycle thread not done");
				}
				// 子线程没有结束的话，主线程会在get()处被堵塞住
				// 如果子线程已经超时，执行到get()将会抛出CancellationException
				System.out.println(fsFuture.get());
				System.out.println("current cycle child thread done");
			}
		} catch(InterruptedException e) {
			System.out.println("get InterruptedException");
			System.out.println(e);
		} catch(ExecutionException e) {
			System.out.println("get ExecutionException");
			System.out.println(e);
		} catch (CancellationException e) {
			System.out.println("get CancellationException");
			System.out.println(e);
		}finally {
			mes.shutdown();
		}
	}
 }