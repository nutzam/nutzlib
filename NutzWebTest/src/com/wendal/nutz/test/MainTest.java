package com.wendal.nutz.test;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.JUnit4TestAdapter;
import junit.textui.TestRunner;

import org.nutz.TestAll;

/**
 * Servlet implementation class MainTest
 */
public class MainTest extends HttpServlet {
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			TestRunner runner = new TestRunner(new PrintStream(response.getOutputStream()));
			runner.doRun(new JUnit4TestAdapter(TestAll.class));
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
