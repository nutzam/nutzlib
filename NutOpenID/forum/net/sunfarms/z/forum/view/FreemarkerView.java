package net.sunfarms.z.forum.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.mvc.View;

public class FreemarkerView implements View {
	
	private String value;

	public FreemarkerView(String value) {
		this.value = "/WEB-INF/"+value+".ftl";
	}
	
	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Throwable {
		req.getRequestDispatcher(value).forward(req, resp);
	}

}
