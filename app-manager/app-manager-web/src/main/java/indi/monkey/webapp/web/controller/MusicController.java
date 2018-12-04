package indi.monkey.webapp.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.monkey.webapp.commons.annotation.ExecuteService;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;

@Controller
@RequestMapping("/music")
@ExecuteService(executeNames = "music")
public class MusicController extends BaseController {

	@RequestMapping(value = "/video_url_to_musicData", method = RequestMethod.GET)
	public Response<?> video_url_to_musicData(String actionType, HttpServletRequest request) {
		Request req = Request.of(request,actionType);
		return service(req);
	}
}
