package indi.monkey.webapp.service.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import indi.monkey.webapp.dao.config.DataBaseConfigListener;

@Configuration
@Order(-9999)
public class ListenerConfig {

	@Bean
	public DataBaseConfigListener applicationStartListener() {
		return new DataBaseConfigListener();
	}
}
