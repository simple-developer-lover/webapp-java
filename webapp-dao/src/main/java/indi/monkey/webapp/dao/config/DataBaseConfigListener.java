package indi.monkey.webapp.dao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import indi.monkey.webapp.commons.config.PropertiesListenerConfig;
import indi.monkey.webapp.commons.pub.util.APPUtil;

public class DataBaseConfigListener implements ApplicationListener<ApplicationStartedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(DataBaseConfigListener.class);
	private static final String propertyFileName = APPUtil.getProjectPath(DataBaseConfigListener.class)
			+ APPUtil.defaultConfigName;

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		logger.info("start load datasource properties....{}", propertyFileName);
		PropertiesListenerConfig.loadAllProperties(propertyFileName);
	}
}
