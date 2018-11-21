package indi.monkey.webapp.web.service;

import org.springframework.stereotype.Service;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;

@Service
public class BaseServiceImple implements BaseService {

	public BaseServiceImple() {
		System.out.println(this.getClass().getName() + ">>>>" + this.getClass().getClassLoader());
	}

	@Override
	public boolean canService(Request request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Response<?> service(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

}
