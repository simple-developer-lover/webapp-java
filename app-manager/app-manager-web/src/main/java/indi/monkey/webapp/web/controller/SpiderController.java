package indi.monkey.webapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import indi.monkey.webapp.commons.annotation.ExecuteService;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.service.impl.SpiderServiveImpl;
import indi.monkey.webapp.service.impl.TaobaoSpiderImpl;

@RequestMapping("/spider")
@Controller
@ExecuteService(executeClasses = { SpiderServiveImpl.class, TaobaoSpiderImpl.class })
public class SpiderController extends BaseController {

	@RequestMapping(value = "/{serviceName}", method = { RequestMethod.POST })
	@ResponseBody
	public Response<?> spider(@PathVariable String serviceName, HttpServletRequest request) {
		Request req = Request.of(request,serviceName);
		return service(req);
	}

	@RequestMapping(value = "/{page}", method = { RequestMethod.GET })
	public String page(@PathVariable String page, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("service", "spider");
		page = "spider/" + page;
		return super.page(page, request, response);
	}

}
