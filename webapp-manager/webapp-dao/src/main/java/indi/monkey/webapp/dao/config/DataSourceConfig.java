package indi.monkey.webapp.dao.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.alibaba.fastjson.JSON;
import com.zaxxer.hikari.util.DriverDataSource;

import indi.monkey.webapp.commons.pub.util.APPUtil;

@Configuration
public class DataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Bean(name = "hibernateDataSource")
	public DriverDataSource hibernateDataSource() throws IOException {
		String propertyFile = APPUtil.getProjectPath(DataSourceConfig.class) + APPUtil.defaultConfigName;
		logger.info("start create dataSource ...... for property file :\"{}\"", propertyFile);
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File(propertyFile)));
		logger.error(properties.getProperty("hibernate.jdbcUrl"));
		String jdbcUrl = properties.getProperty("hibernate.jdbcUrl", "jdbc:mysql://localhost:3306/webapp_spider");
		String driverClass = properties.getProperty("hibernate.driverClass", "com.mysql.jdbc.Driver");
		String username = properties.getProperty("hibernate.username", "root");
		String password = properties.getProperty("hibernate.password", "root");
		logger.info("datasource jdbcUrl:{}", jdbcUrl);
		DriverDataSource dataSource = new DriverDataSource(jdbcUrl, driverClass, properties, username, password);
		return dataSource;
	}

	/*
	 * @Bean(name = "mybatisDataSource") public DriverDataSource mybatisDataSource()
	 * throws IOException { String propertyFile =
	 * APPUtil.getProjectPath(DataSourceConfig.class) + APPUtil.defaultConfigName;
	 * Properties properties =
	 * PropertiesLoaderUtils.loadAllProperties(propertyFile); String jdbcUrl =
	 * properties.getProperty("mybatis.jdbcUrl",
	 * "jdbc:mysql://localhost:3306/webapp_spider"); String driverClass =
	 * properties.getProperty("mybatis.driverClass", "com.mysql.jdbc.Driver");
	 * String username = properties.getProperty("mybatis.username", "root"); String
	 * password = properties.getProperty("mybatis.password", "root");
	 * DriverDataSource dataSource = new DriverDataSource(jdbcUrl, driverClass,
	 * properties, username, password); return dataSource; }
	 */

}
