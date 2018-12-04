package indi.monkey.webapp.web.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import indi.monkey.webapp.commons.annotation.ExecuteService;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.pub.util.Thread4ViableResult;
import indi.monkey.webapp.service.BaseService;
import indi.monkey.webapp.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

/**
 * 公共controller
 * 
 * @author F-Monkey
 *
 */
@Slf4j
@Controller
@ExecuteService(executeClasses = BaseServiceImpl.class)
public class BaseController {

	BaseService[] services;
	private static final int DEFAULT_EXECUTE_POOL_SIZE = 4;

	@Value("${username}")
	private String s;

	@RequestMapping(value = "/{page}", method = { RequestMethod.GET })
	public String page(@PathVariable String page, HttpServletRequest request, HttpServletResponse response) {
		log.info("go to page .....{}", page);
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		request.setAttribute("basePath", basePath);
		return page;
	}

	/**
	 * 示例： 如何执行
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/default-execute", method = { RequestMethod.GET })
	@ResponseBody
	public Response<?> execute(HttpServletRequest request) {
		return service(Request.of(request, "default"));
	}

	/**
	 * 这里的概念是一样的，都是判断是否canService，然后再处理service逻辑 每个业务自己的逻辑可以重写该方法，以适应当前的业务
	 * 
	 * @param request
	 * @return
	 */

	protected BaseService buildService(Request request) {
		if (services == null || services.length == 0) {
			log.info("no service in the controller:{}", this.getClass().getName());
			throw new RuntimeException("no service error!!!");
		}
		if (services.length == 1) {
			if (services[0].canService(request)) {
				return services[0];
			} else {
				return null;
			}
		} else {
			Predicate<BaseService> ps = s -> s != null;
			Set<Callable<BaseService>> ss = Arrays.stream(services).map(s -> new Callable<BaseService>() {
				@Override
				public BaseService call() throws Exception {
					if (s.canService(request)) {
						return s;
					}
					return null;
				}
			}).collect(Collectors.toSet());
			return Thread4ViableResult.execute(ss, Math.min(DEFAULT_EXECUTE_POOL_SIZE, services.length), ps);
		}
	}

	protected Response<?> service(Request request) {
		String requestData = JSON.toJSONString(request);
		long startTime = System.currentTimeMillis();
		log.info("request:{}", requestData);
		BaseService service = buildService(request);
		if (service != null) {
			Response<?> resp = service.service(request);
			if (resp.getException() != null) {
				log.error("execute error...for \n", resp.getException());
			} else {
				String respStr = JSON.toJSONString(resp);
				log.info("service execute success, response data:{} ...cast {}ms",
						respStr.substring(0, Math.min(100, respStr.length() - 1)),
						(System.currentTimeMillis() - startTime));
			}
			return resp;
		} else {
			log.info("can't execute, cause request is : " + requestData);
			throw new RuntimeException("can't execute, cause request is : " + requestData);
		}
	}

	/**
	 * 500统一异常处理
	 * 
	 * @param exception
	 *            exception
	 * @return
	 */
	@ExceptionHandler({ RuntimeException.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(RuntimeException exception) {
		log.info("自定义异常处理-RuntimeException");
		ModelAndView m = new ModelAndView();
		m.addObject("error", exception);
		m.setViewName("/error/500");
		return m;
	}

	/**
	 * 404统一异常处理
	 * 
	 * @param exception
	 *            exception
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(Exception exception) {
		log.info("自定义异常处理-Exception");
		ModelAndView m = new ModelAndView();
		m.addObject("error", "page not found!!!");
		m.setViewName("/error/404");
		return m;
	}
}
