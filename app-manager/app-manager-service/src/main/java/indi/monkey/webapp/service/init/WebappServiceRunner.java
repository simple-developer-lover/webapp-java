package indi.monkey.webapp.service.init;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

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

	public static final String METHODS_NAME = "methods";

	@Resource
	ServiceContext serviceContext;

	@Resource
	ProxyContext proxyContext;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(JSON.toJSONString(args));
		}
		initBaseService();
//		initFileService();
	}

	protected void initBaseService() throws Exception {
		Collection<BaseService> beans = serviceContext.getBeans(null);
		if (beans.isEmpty()) {
			return;
		}
		for (BaseService service : beans) {
			assignMethods(service);
			resolveProxy(service);
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
		if (clazz.isInterface()) {
			return;
		}
		MethodAccessLoader loader = new MethodAccessLoader(clazz);
		while (!clazz.isInterface() && clazz != Object.class) {
			for (Field f : clazz.getDeclaredFields()) {
				if (f.getType().isAssignableFrom(MethodAccessLoader.class)) {
					try {
						f.setAccessible(true);
						f.set(service, loader);
						f.setAccessible(false);
						logger.info("method loader init success.....");
						break;
					} catch (Exception e) {
						logger.error("method loader init error", e);
						throw e;
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
	}

	protected void resolveProxy(Object service) {
		Field[] fields = service.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getAnnotation(Reserved.class) != null) {
				Object bean = proxyContext.getBean(f.getType());
				try {
					f.setAccessible(true);
					f.set(service, f.getType().cast(bean));
					f.setAccessible(false);
					logger.info("{} service proxy init success...for proxy {}", service.getClass().getName(),
							bean.getClass().getName());
					break;
				} catch (Exception e) {
					logger.error("{} service proxy init error", e);
				}
			}
		}
	}
}
