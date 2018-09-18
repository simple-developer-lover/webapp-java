package indi.monkey.webapp.service.init;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import indi.monkey.webapp.commons.annotation.HandlerMethod;
import indi.monkey.webapp.commons.annotation.Reserved;
import indi.monkey.webapp.proxy.context.ProxyContext;
import indi.monkey.webapp.service.BaseService;
import indi.monkey.webapp.service.FileService;
import indi.monkey.webapp.service.context.FileServiceContext;
import indi.monkey.webapp.service.context.ServiceContext;

@Component
@Order(10)
public class WebappServiceRunner implements ApplicationRunner {
	/**
	 * 初始化service中的methods
	 */
	private static final Logger logger = LoggerFactory.getLogger(WebappServiceRunner.class);

	private static final String MAPPING_SEPARATOR = ".";
	public static final String METHODS_NAME = "methods";
	private static final String BASE_METHOD_CANSERVICE = "canService";
	private static final String BASE_METHOD_SERVICE = "service";

	@Resource
	ServiceContext serviceContext;

	@Resource
	ProxyContext proxyContext;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(JSON.toJSONString(args));
		}
		resolveProxy();
		initBaseService();
		initFileService();
	}

	protected void initBaseService() {
		Collection<BaseService> beans = serviceContext.getBeans(null);
		for (BaseService service : beans) {
			assignMethods(service);
		}
	}

	@Resource
	FileServiceContext fileServiceContext;

	protected void initFileService() {
		Collection<FileService> beans = fileServiceContext.getBeans(null);
		for (FileService service : beans) {
			assignMethods(service);
		}
	}

	/**
	 * 给service对象赋值
	 * 
	 * @param service
	 */
	private void assignMethods(Object service) {
		Class<?> clazz = service.getClass();
		String suffix = null;
		Predicate<Method> p = m -> m.getAnnotation(Override.class) != null
				|| m.getAnnotation(HandlerMethod.class) != null && !BASE_METHOD_CANSERVICE.equals(m.getName())
						&& !BASE_METHOD_SERVICE.equals(m.getName());
		Map<String, Method> methodMap = Maps.newHashMap();
		for (; clazz != null; clazz = clazz.getSuperclass()) {
			Method[] ms = clazz.getDeclaredMethods();
			HandlerMethod mapping = clazz.getAnnotation(HandlerMethod.class);
			if (mapping != null && !"".equals(mapping.value())) {
				suffix = mapping.value();
			}
			if (ms != null && ms.length > 0) {
				List<Method> list = Arrays.asList(ms).stream().filter(p).collect(Collectors.toList());
				for (Method m : list) {
					HandlerMethod annotation = m.getAnnotation(HandlerMethod.class);
					if (annotation != null) {
						String key = suffix == null ? annotation.value()
								: suffix + MAPPING_SEPARATOR + annotation.value();
						methodMap.put(key, m);
					}
				}
			}
		}

		Field declaredField = null;
		for (clazz = service.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
			try {
				declaredField = clazz.getDeclaredField(METHODS_NAME);
				break;
			} catch (Exception e) {
			}
		}
		if (declaredField != null) {
			declaredField.setAccessible(true);
			try {
				declaredField.set(service, methodMap);
			} catch (IllegalAccessException e) {
				logger.error("assign error !!! for service:{},fileName:{},paramValue:{}", service.getClass().getName(),
						METHODS_NAME, methodMap);
			}
			declaredField.setAccessible(false);
			logger.info("methods init success, class is :{} , methods size :{}", service.getClass().getName(),
					methodMap.size());
		} else {
			logger.info("methods init error, class is :{}", service.getClass().getName());
		}
	}

	protected void resolveProxy() {
		Collection<BaseService> services = serviceContext.getBeans(null);
		if (services.size() == 0) {
			return;
		}
		for (BaseService service : services) {
			Field[] fields = service.getClass().getDeclaredFields();
			for (Field f : fields) {
				if (f.getAnnotation(Reserved.class) != null) {
					Object bean = proxyContext.getBean(f.getType());
					f.setAccessible(true);
					try {
						f.set(service, f.getType().cast(bean));
					} catch (Exception e) {
					}
					f.setAccessible(false);
				}
			}
		}

	}
}
