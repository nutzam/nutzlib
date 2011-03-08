package org.nutz.mvc.security;

import java.util.regex.Pattern;

public class AuthPair {

	private Pattern pattern;
	
	private String[] roles;

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
	
	
}
