package org.nutz.mvc.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.xml.XmlMap;

public class NutzSecurityFilter implements Filter {

	private ServletContext context;
	
	private String loginPage;
	private String authBy;
	private AuthProvider authProvider;
	
	private List<AuthPair> aps = new ArrayList<AuthPair>();

	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
		String configFileName = filterConfig.getInitParameter("config-file");
		if (Strings.isBlank(configFileName))
			configFileName = "conf/mvc-secuity.xml";
		InputStream is = Files.findFileAsStream(configFileName);
		if (is == null)
			throw new ServletException("Config file not found : " +configFileName);
		XmlMap map = XmlMap.read(is);
		List<XmlMap> list = map.getList("url");
		for (XmlMap xmlMap : list) {
			AuthPair ap = new AuthPair();
			ap.setPattern(Pattern.compile(xmlMap.get("patten")));
			ap.setRoles(xmlMap.get("access").split(","));
			aps.add(ap);
		}
		loginPage = map.get("login-page");
		authBy = map.get("auth-by");
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		if(authProvider == null) {
			if (authBy.startsWith("ioc:"))
				authProvider = Mvcs.getIoc(context).get(AuthProvider.class, authBy.substring(4));
			else
				try {
					authProvider = (AuthProvider) Lang.loadClass(authBy).newInstance();
				} catch (Exception e) {
					Lang.wrapThrow(e);
				}
		}
		String reqURI = ((HttpServletRequest)req).getRequestURI();
		for (AuthPair ap : aps) {
			if(ap.getPattern().matcher(reqURI).find())
				if (authProvider.isOK((HttpServletRequest) req, ap.getRoles())) {
					filterChain.doFilter(req, resp);
					return;
				}
		}
		
		if (loginPage != null)
			((HttpServletResponse)resp).sendRedirect(loginPage);
		else
			((HttpServletResponse)resp).sendError(403);
	}
	
	public void destroy() {
		
	}
}
