package indi.monkey.webapp.commons.context;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import indi.monkey.webapp.commons.pub.util.APPUtil;

public class CommonsContext<T> implements ApplicationContextAware {

	protected ApplicationContext applicationContext;

	protected Map<String, T> beanMap = Maps.newHashMap();

	public void init() {
		Class<T> type = APPUtil.getGenericType(this.getClass());
		beanMap = applicationContext.getBeansOfType(type, false, true);
		if (beanMap == null) {
			beanMap = Maps.newHashMap();
			return;
		}
	}

	public T getBean(Object beanType) {
		if (beanType instanceof String) {
			return beanMap.get(beanType);
		}
		return null;
	}

	public boolean containsBean(Object beanType) {
		if (beanType instanceof String) {
			return beanMap.containsKey(beanType);
		}
		return false;
	}

	public Collection<T> getBeans(Object[] beanTypes) {
		if (beanTypes == null || beanTypes.length == 0) {
			return beanMap.values();
		}
		Set<T> beans = Sets.newHashSet();
		for (Object beanType : beanTypes) {
			if (containsBean(beanType)) {
				beans.add(getBean(beanType));
			}
		}
		return beans;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
