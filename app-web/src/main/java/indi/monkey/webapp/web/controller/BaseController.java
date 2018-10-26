package indi.monkey.webapp.web.controller;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.web.service.BaseService;

public class BaseController {

	BaseService[] baseServices;

	public Response<?> service(Request req) {
		for (BaseService service : baseServices) {
			if (service.canService(req)) {
				return service.service(req);
			}
		}
		return Response.of(199);
	}
}
