package testPackage.managedexecutorservice;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedExecutors;

import java.util.*;
import java.util.concurrent.*;
import javax.naming.*;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.swing.internal.plaf.basic.resources.basic;

import java.io.IOException;
import java.net.InetSocketAddress;


@WebServlet(urlPatterns = "/kodcu", name = "KodcuServlet")
public class TestHttpManagedExecutorService extends HttpServlet {
	 
	//@Resource(name = "concurrent/testExecutor")
	//private ManagedExecutorService objMes;
	//@Resource
	//javax.enterprise.concurrent.ManagedExecutorService mes;
	//ManagedExecutorService mes;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			System.out.println("start---------");
			//mes.submit(new RunnableA());
			/*InitialContext context;
			context = new InitialContext();
			Context envContext  = (Context)context.lookup("java:/comp/env");
			System.out.println("get conddtext---------");
			ManagedExecutorService mess = (ManagedExecutorService)envContext.lookup("concurrent/testExecutor");
			//ManagedExecutorService mes = (ManagedExecutorService)
			//		Thread b = (Thread)
					//context.lookup("java:concurrent/testExecutor");
			//		context.lookup("java:comp/env/concurrent/testExecutor");
			//mes.submit(new RunnableA());
			
			//context.lookup("concurrent/testExecutor");
			*/

			ThreadInfo.findAllThreads();
			/*while(true) {
				TimeUnit.SECONDS.sleep(5);
				System.out.println("---------------");
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("get NamingException");
			System.out.println(e);
			//e.printStackTrace();
			System.out.println("－－－－eeee3434eeee22－－－－－－－－－－－");
		}
	 
	}
}