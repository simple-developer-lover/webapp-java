package indi.monkey.webapp.dao.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.util.DriverDataSource;

import indi.monkey.webapp.commons.pub.util.APPUtil;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataSourceConfig {

	private static final Properties properties = new Properties();

	@PostConstruct
	void init() {
		String propertyFile = APPUtil.getProjectPath(this.getClass()) + APPUtil.defaultConfigName;
		log.info("start create dataSource ...... for property file :\"{}\"", propertyFile);
		try {
			properties.load(new FileInputStream(new File(propertyFile)));
		} catch (IOException e) {
			log.error("properties load fail ....for file path:{}", propertyFile);
			e.printStackTrace();
		}
	}

	@Bean(name = "hibernateDataSource")
	public DriverDataSource hibernateDataSource() throws IOException {
		String jdbcUrl = properties.getProperty("hibernate.jdbcUrl",
				"jdbc:mysql://172.17.0.1:3306/webapp_spider?useUnicode=true&characterEncoding=&useSSL=false");
		String driverClass = properties.getProperty("hibernate.driverClass", "com.mysql.jdbc.Driver");
		String username = properties.getProperty("hibernate.username", "root");
		String password = properties.getProperty("hibernate.password", "root");
		log.info("datasource jdbcUrl:{}", jdbcUrl);
		DriverDataSource dataSource = new DriverDataSource(jdbcUrl, driverClass, properties, username, password);
		return dataSource;
	}
}
