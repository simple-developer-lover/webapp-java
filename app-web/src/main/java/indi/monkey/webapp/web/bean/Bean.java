package indi.monkey.webapp.web.bean;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import indi.monkey.webapp.WebApplication;
import indi.monkey.webapp.web.service.BaseService;

@Component
public class Bean {
	@Resource
	BaseService baseService;
	
	@Resource
	WebApplication applicaiton;
	
	public Bean() {
		System.out.println(this.getClass().getName() + ">>>>" + this.getClass().getClassLoader());
	}

	public BaseService getService() {
		return baseService;
	}
	
	public WebApplication getApplication() {
		return applicaiton;
	}
}
