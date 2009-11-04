package org.nutz.ioc2.Impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc2.IIoc;

public class NutzServlet extends HttpServlet {

	IIoc ioc = null;
	
	WebApplicationContext context = null;
	
	@Override
	public void init() throws ServletException {
		context = new WebApplicationContext();
		
		ioc = new NutzIoc(null, context, null);
		
		//other work...
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		context.beginServe(request);
		
		try {
			//other work...
			//ioc.get("some id");
		} finally {
			context.afterServe();
		}
	}


	
}
