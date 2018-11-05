package indi.monkey.webapp.service.impl;

import javax.annotation.Resource;

import indi.monkey.webapp.commons.annotation.AppService;
import indi.monkey.webapp.commons.annotation.HandlerMethod;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.loader.MethodAccessLoader;
import indi.monkey.webapp.service.BaseService;
import indi.monkey.webapp.service.context.ServiceJumper;
import lombok.extern.slf4j.Slf4j;

@AppService(id = 1, name = "baseService")
@Slf4j
public class BaseServiceImpl implements BaseService {

	protected MethodAccessLoader loader;

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
		if (loader == null) {
			log.error("class:{} method loader init error .... please check it...", this.getClass().getName());
			return false;
		}
		return loader.contains(request.getActionName());
	}

	@Override
	public Response<?> service(Request request) {
		String actionType = request.getActionName();
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
