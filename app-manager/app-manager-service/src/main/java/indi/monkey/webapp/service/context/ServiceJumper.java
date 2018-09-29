package indi.monkey.webapp.service.context;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.service.BaseService;
import indi.monkey.webapp.service.FileService;

/**
 * 服务层跳转
 * 
 * @author F-Monkey
 *
 */
@Component
@Order(100)
public class ServiceJumper {

	private static final Logger logger = LoggerFactory.getLogger(ServiceJumper.class);

	@Resource
	ServiceContext serviceContext;

	@Resource
	FileServiceContext fileServiceContext;

	/**
	 * 服务层跳转。
	 * 
	 * @param service： id、name、serviceClass
	 * @param methodName： handlerMethod。value()
	 * @param request： data
	 * @return
	 */
	public Response<?> jump(Object service, String methodName, Request request) {
		long startTime = System.currentTimeMillis();
		BaseService bean = serviceContext.getBean(service);
		try {
			if (bean.canService(request)) {
				return bean.service(request);
			}
			logger.info("can't execute method:{}", methodName);
		} catch (Exception e) {
			logger.error("jump service error...", e);
		}
		return Response.of(199, System.currentTimeMillis() - startTime);
	}

	/**
	 * @param          serviceType：
	 *                 因为取不到class类型的值，所以这里就按照serviceName去获取service，值为String类型
	 * @param          actionType：被handlerMethod修饰的方法名
	 * @param request
	 * @param response
	 */
	public void jump(String serviceName, String actionType, HttpServletRequest request, HttpServletResponse response) {
		FileService bean = fileServiceContext.getBean(serviceName);
		bean.service(actionType, request, response);
	}
}
