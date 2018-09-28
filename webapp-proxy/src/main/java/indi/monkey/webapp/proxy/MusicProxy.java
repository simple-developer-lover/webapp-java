package indi.monkey.webapp.proxy;

import java.util.List;

//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;


//@FeignClient("app-service")
@RequestMapping("/random-server")
public interface MusicProxy{
	
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public Response<List<String>> search(Request req);
	
	@RequestMapping(method = RequestMethod.POST ,value ="/download")
	public Response<String> download(Request req);
}
