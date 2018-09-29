package indi.monkey.webapp.web.init;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;

import indi.monkey.webapp.commons.annotation.ExecuteService;
import indi.monkey.webapp.service.BaseService;
import indi.monkey.webapp.service.context.ServiceContext;
import indi.monkey.webapp.web.context.ControllerContext;
import indi.monkey.webapp.web.controller.BaseController;

@Component
@Order(11) // after service init end
public class WebappControllerRunner implements ApplicationRunner {

	private static final String SERVICES_NAME = "services";

	private static final Logger logger = LoggerFactory.getLogger(WebappControllerRunner.class);

	@Resource
	ControllerContext controllerContext;

	@Resource
	ServiceContext serviceContext;

	public void run(ApplicationArguments args) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(JSON.toJSONString(args));
		}
		initBaseController();
	}

	/**
	 * init fileController
	 */
	protected void initFileController() {

	}

	/**
	 * 初始化Controller中的service
	 */
	protected void initBaseController() {
		Collection<BaseController> beans = controllerContext.getBeans(null);
		for (BaseController controller : beans) {
			Class<?> clazz = controller.getClass();
			ExecuteService controllerExecutors = clazz.getAnnotation(ExecuteService.class);
			Set<Object> serviceTypes = generateServiceTypes(controllerExecutors);
			Field declaredField = null;
			for (; clazz != null; clazz = clazz.getSuperclass()) {
				try {
					declaredField = clazz.getDeclaredField(SERVICES_NAME);
					break;
				} catch (Exception e) {
				}
			}

			if (declaredField != null) {
				// 添加field上的注解的service
				serviceTypes.addAll(generateServiceTypes(declaredField.getAnnotation(ExecuteService.class)));
				if (serviceTypes.size() == 0) {
					logger.info("no service in controller:{}", controller.getClass().getName());
					continue;
				}
				declaredField.setAccessible(true);
				try {
					BaseService[] services = serviceContext.getBeans(serviceTypes.toArray())
							.toArray(new BaseService[] {});
					declaredField.set(controller, services);
				} catch (IllegalAccessException e) {
					logger.error("assign error !!! for controller:{},fileName:{},paramValue:{}",
							controller.getClass().getName(), SERVICES_NAME);
				}
				declaredField.setAccessible(false);
				logger.info("controller init success, class is :{} , services size :{}",
						controller.getClass().getName(), serviceTypes.size());
			} else {
				logger.info("controller init error, class is :{}, ", controller.getClass().getName());
			}
		}
	}

	private Set<Object> generateServiceTypes(ExecuteService executors) {
		Set<Object> serviceTypes = Sets.newHashSet();
		if (executors == null) {
			return serviceTypes;
		}
		long[] ids = executors.execiteIds();
		Class<?>[] classes = executors.executeClasses();
		String[] names = executors.executeNames();
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				serviceTypes.add(id);
			}
		}
		if (names != null && names.length > 0) {
			for (String name : names) {
				serviceTypes.add(name);
			}
		}
		if (classes != null && classes.length > 0) {
			for (Class<?> c : classes) {
				serviceTypes.add(c);
			}
		}
		return serviceTypes;
	}
}
