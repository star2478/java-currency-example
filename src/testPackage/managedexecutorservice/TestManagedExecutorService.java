package testPackage.managedexecutorservice;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.*;
import java.util.concurrent.*;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet(urlPatterns = "/kodcu", name = "KodcuServlet")
class TestManagedExecutorService {
	
	@Resource(name = "concurrent/LongRunningTasksExecutor")
	ManagedExecutorService mess;
	
	public static void main(String[] args) {
		TestManagedExecutorService objExecutorService = new TestManagedExecutorService();
		objExecutorService.getAccounts(1000);
	}
	/**
	  * Retrieve all accounts from several account databases in parallel.
	  * Resource Mappings:
	  *  type:      javax.enterprise.concurrent.ManagedExecutorService
	  *  jndi-name: concurrent/ThreadPool
	  */
	 public List<String> getAccounts(long accountId) {
	   try {
		   ManagedExecutorService service = (ManagedExecutorService) Executors.newFixedThreadPool(2);
		   if(mess == null) {
			   System.out.println("cao");
		   }
	       mess.submit(new RunnableA()); 
	       System.out.println("ds");
	       InitialContext ctx = new InitialContext();
		   System.out.println("mes before");
	       ManagedExecutorService mes = (ManagedExecutorService)
	    		   ctx.lookup("concurrent/testExecutor");
	           //ctx.lookup("java:comp/env/concurrent/ThreadPool");
		   System.out.println("mes after");
	
	       // Create a set of tasks to perform the account retrieval.
	       ArrayList<Callable<String>> retrieverTasks = new ArrayList<Callable<String>>();
	       retrieverTasks.add(new CallableA());
	       retrieverTasks.add(new CallableB());
	
	       // Submit the tasks to the thread pool and wait for them
	       // to complete (successfully or otherwise).
	       List<Future<String>> taskResults= mes.invokeAll(retrieverTasks);
	
	       // Retrieve the results from the resulting Future list.
	       ArrayList<String> results = new ArrayList<String>();
	       for(Future<String> taskResult : taskResults) {
	           try {
	               results.add(taskResult.get());
	           } catch (ExecutionException e) {
	               Throwable cause = e.getCause();
	               // Handle the AccountRetrieverError.
	           }
	       }
	       return results;
	
	   } catch (NamingException e) {
		   System.out.println("get NamingException");
		   System.out.println(e);
			return null;
	   } catch (InterruptedException e) {
	       // Throw exception for shutdown or other interrupt condition.
		   System.out.println("get InterruptedException");
			return null;
	   }
	 }
 }