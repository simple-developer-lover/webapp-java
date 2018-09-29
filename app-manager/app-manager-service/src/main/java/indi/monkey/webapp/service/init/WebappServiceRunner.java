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
import indi.monkey.webapp.commons.loader.MethodAccessLoader;
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
//		initFileService();
	}

	protected void initBaseService() throws Exception {
		Collection<BaseService> beans = serviceContext.getBeans(null);
		for (BaseService service : beans) {
			assignMethods(service);
		}
	}

	@Resource
	FileServiceContext fileServiceContext;

	protected void initFileService() throws Exception {
		Collection<FileService> beans = fileServiceContext.getBeans(null);
		for (FileService service : beans) {
			assignMethods(service);
		}
	}

	/**
	 * 给service对象赋值
	 * 
	 * @param service
	 * @throws Exception
	 */
	private void assignMethods(Object service) throws Exception {
		Class<?> clazz = service.getClass();
		MethodAccessLoader loader = new MethodAccessLoader(clazz);
		for (Field f : clazz.getDeclaredFields()) {
			if (f.getType().isAssignableFrom(MethodAccessLoader.class)) {
				try {
					f.set(service, loader);
					logger.info("method loader init success.....");
				} catch (Exception e) {
					logger.error("method loader init error", e);
					throw e;
				}
				break;
			}
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
