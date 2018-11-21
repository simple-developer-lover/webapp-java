package indi.monkey.webapp.service.init;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.annotation.Resource;

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
import lombok.extern.slf4j.Slf4j;

@Component
@Order(10)
@Slf4j
public class WebappServiceRunner implements ApplicationRunner {
	/**
	 * 初始化service中的methods
	 */
	@Resource
	ServiceContext serviceContext;

	@Resource
	ProxyContext proxyContext;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug(JSON.toJSONString(args));
		}
		initBaseService();
		// initFileService();
	}

	protected void initBaseService() throws Exception {
		Collection<BaseService> beans = serviceContext.getBeans(null);
		if (beans.isEmpty()) {
			return;
		}
		for (BaseService service : beans) {
			if (!resolveProxy(service)) {
				log.warn("service proxy init warn,this is no proxy in service:{}", service.getClass().getName());
			}
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
		if (clazz.isInterface()) {
			return;
		}
		MethodAccessLoader loader = null;
		try {
			log.info("try to initialize method loader");
			loader = new MethodAccessLoader(service);
		} catch (Exception e) {
			log.error(">>>>> class:{} method loader initialize error....", e);
		}
		if (loader == null) {
			return;
		}
		while (!clazz.isInterface() && clazz != Object.class) {
			for (Field f : clazz.getDeclaredFields()) {
				synchronized (f) {
					if (f.getType().isAssignableFrom(MethodAccessLoader.class)) {
						try {
							Object object = f.get(service);
							if (object == null) {
								f.setAccessible(true);
								f.set(service, loader);
								f.setAccessible(false);
							}
							log.info("MethodAccessLoader loader init success.....service:{}",
									service.getClass().getName());
							break;
						} catch (Exception e) {
							log.error("MethodAccessLoader loader init error", e);
							throw e;
						}
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
	}

	protected boolean resolveProxy(Object service) {
		Field[] fields = service.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getAnnotation(Reserved.class) != null) {
				Object bean = proxyContext.getBean(f.getType());
				if (bean == null) {
					continue;
				}
				try {
					f.setAccessible(true);
					f.set(service, bean);
					f.setAccessible(false);
					log.info("{} service proxy init success...for proxy {}", service.getClass().getName(),
							bean.getClass().getName());
					return true;
				} catch (Exception e) {
					log.error("{} service proxy init error", e);
				}
			}
		}
		return false;
	}
}
