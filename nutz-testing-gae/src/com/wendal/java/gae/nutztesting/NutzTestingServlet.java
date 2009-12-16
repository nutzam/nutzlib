package com.wendal.java.gae.nutztesting;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.JUnit4TestAdapter;
import junit.textui.TestRunner;

@SuppressWarnings("serial")
public class NutzTestingServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain; charset=utf-8");
		String className = req.getParameter("class");
		if(className != null){
			try {
				Class<?> testClass = Class.forName(className);
				new TestRunner(new PrintStream(resp.getOutputStream()))
					.doRun(new JUnit4TestAdapter(testClass));
			} catch (ClassNotFoundException e) {
				resp.getWriter().println("ClassNotFound: "+className);
			}catch (Throwable e) {
				e.printStackTrace(resp.getWriter());
			}
		}else{
			resp.getWriter().println("Using : http://wendalx999.appspot.com/check?class= + className");
		}
	}
}
