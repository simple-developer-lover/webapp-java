package indi.monkey.webapp.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import indi.monkey.webapp.commons.annotation.AppService;
import indi.monkey.webapp.commons.annotation.HandlerMethod;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.loader.MethodAccessLoader;
import indi.monkey.webapp.service.BaseService;
import indi.monkey.webapp.service.context.ServiceJumper;

@AppService(id = 1, name = "baseService")
public class BaseServiceImpl implements BaseService {

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	// protected Map<String, Method> methods = Maps.newHashMap();
	protected MethodAccessLoader loader = null;

	@Resource
	ServiceJumper jumper;

	/**
	 * 示例
	 * 
	 * @param request
	 * @return
	 */
	@HandlerMethod("default")
	public Response<?> execute(Request request) {
		return Response.of(request);
	}

	@Override
	public boolean canService(Request request) {
		return loader.contains(request.getActionName());
	}

	@Override
	public Response<?> service(Request request) {
		String actionType = request.getActionName();
		logger.info("{} execute method:{} for args:{}", this.getClass().getName(), actionType,
				JSON.toJSONString(request));
		try {
			return loader.invoke(actionType, Response.class, request);
		} catch (Exception e) {
			Response<Object> response = Response.builder().exception(e).status(199).build();
			return response;
		}
	}

	@Override
	public Response<?> jumpService(Object service, String method, Request request) {
		return jumper.jump(service, method, request);
	}
}
