package indi.monkey.webapp.proxy;

//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;


//@FeignClient("app-service")
@RequestMapping("/random-server")
public interface RandomProxy {

	@RequestMapping("/create")
	public Response<?> create(Request request);

}
