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
		String className = req.getParameter("class");
		if(className != null){
			try {
				new TestRunner(new PrintStream(resp.getOutputStream()))
					.doRun(new JUnit4TestAdapter(Class.forName(className)));
			} catch (ClassNotFoundException e) {
				resp.getWriter().println("û���ҵ����������: "+className);
			}catch (Throwable e) {
				resp.getWriter().println("δ֪����: ���������: "+className);
				e.printStackTrace(resp.getWriter());
			}
		}else{
			resp.setContentType("text/plain; charset=utf-8");
			resp.getWriter().println("�밴Ҫ������������ȫ��: http://wendalx999.appspot.com/check?class= + ������");
		}
	}
}
