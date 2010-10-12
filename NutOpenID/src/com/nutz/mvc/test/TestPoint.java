package com.nutz.mvc.test;

import org.nutz.dao.Dao;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

@IocBean
@InjectName
public class TestPoint {
	
	@Inject
	Dao dao;

	@At("/test")
	public Object test() {
		dao.exists("Nutz");
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
