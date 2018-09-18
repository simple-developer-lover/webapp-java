package indi.monkey.webapp.proxy;

//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.exception.ScriptException;

//@FeignClient("app-service")
@RequestMapping("/book-server")
public interface BookProxy{
	
	@RequestMapping("/search")
	Response<?> search(Request request) throws ScriptException;
}
