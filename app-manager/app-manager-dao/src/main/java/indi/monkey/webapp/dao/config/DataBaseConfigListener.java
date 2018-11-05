package indi.monkey.webapp.dao.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import indi.monkey.webapp.commons.config.PropertiesListenerConfig;
import indi.monkey.webapp.commons.pub.util.APPUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataBaseConfigListener implements ApplicationListener<ApplicationStartedEvent> {

	private static final String propertyFileName = APPUtil.getProjectPath(DataBaseConfigListener.class)
			+ APPUtil.defaultConfigName;

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		log.info("start load datasource properties....{}", propertyFileName);
		PropertiesListenerConfig.loadAllProperties(propertyFileName);
	}
}
