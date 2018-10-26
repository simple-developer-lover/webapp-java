package indi.monkey.webapp.proxy.context;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import indi.monkey.webapp.commons.annotation.ReserveProxy;
import indi.monkey.webapp.commons.loader.WebClassLoader;
import indi.monkey.webapp.commons.pub.util.APPUtil;
import indi.monkey.webapp.commons.web.context.CommonsContext;

@Component("proxyContext")
public class ProxyContext extends CommonsContext<Object> {
	@PostConstruct
	public void init() {
		super.init();
		initResolve();
	}

	// 类加载器加载
	void initResolve() {
		File f = new File(APPUtil.getProjectPath(getClass()));
		Map<String, Object> proxys = WebClassLoader.createObjectsByLocation(f);
		for (Iterator<Entry<String, Object>> it = proxys.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> e = it.next();
			try {
				Object val = e.getValue();
				if (val.getClass().getAnnotation(ReserveProxy.class) != null) {
					this.beanMap.put(e.getKey(), val);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public Object getBean(Object beanType) {
		if (beanType instanceof Class) {
			Class<?> clazz = Class.class.cast(beanType);
			Iterator<Object> iterator = beanMap.values().iterator();
			while (iterator.hasNext()) {
				Object proxy = iterator.next();
				if (clazz.isAssignableFrom(proxy.getClass())) {
					return proxy;
				}
			}
		}
		return null;
	}
}
