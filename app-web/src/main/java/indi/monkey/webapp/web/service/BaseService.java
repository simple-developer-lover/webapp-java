package indi.monkey.webapp.web.service;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;

public interface BaseService {
	boolean canService(Request request);

	Response<?> service(Request request);

}
