package com.nutz.mvc.auth;

import java.util.Map;

public class AuthConfig {

	private Map<String, String> config;
	
	public AuthConfig(Map<String, String> config) {
		this.config = config;
	}
	
	public String get(String key){
		return config.get(key);
	}
}
