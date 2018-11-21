package indi.monkey.webapp.commons.web.context;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import indi.monkey.webapp.commons.pub.util.APPUtil;

@ComponentScans(value = {})
public class CommonsContext<T> implements ApplicationContextAware {

	protected ApplicationContext applicationContext;

	protected Map<String, T> beanMap = Maps.newHashMap();

	public void init() {
		Class<T> type = APPUtil.getGenericType(this.getClass());
		beanMap = applicationContext.getBeansOfType(type, true, true);
		if (beanMap == null) {
			beanMap = Maps.newHashMap();
			return;
		}
		ComponentScans annotation = this.getClass().getAnnotation(ComponentScans.class);
		ComponentScan[] value = {};
		if (annotation != null && (value = annotation.value()) != null && value.length > 0) {
			Set<String> allowPackages = new HashSet<>(value.length);
			// Set<ComponentScan.Filter> filterPackages = new HashSet<>(value.length);
			for (ComponentScan scan : value) {
				Arrays.stream(scan.value()).forEach(s -> allowPackages.add(s));
				// Arrays.stream(scan.includeFilters()).forEach(s -> filterPackages.add(s));
			}
			if (allowPackages.isEmpty()) {
				return;
			}
			beanMap = beanMap.entrySet().stream().filter(e -> {
				for (String pack : allowPackages) {
					if (e.getValue() != null && e.getValue().getClass().getName().indexOf(pack) > -1) {
						return true;
					} else {
						continue;
					}
				}
				return false;
			}).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
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
