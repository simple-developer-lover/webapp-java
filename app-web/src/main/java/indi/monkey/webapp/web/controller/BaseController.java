package indi.monkey.webapp.web.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Controller;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.web.resource.AppPropertySourceFactory;
import indi.monkey.webapp.web.service.BaseService;

@Controller
// @PropertySources({ @PropertySource(factory = AppPropertySourceFactory.class,
// value = { "" }) })
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
