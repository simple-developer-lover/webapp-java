package indi.monkey.webapp.dao.config;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.collect.Maps;

import indi.monkey.webapp.commons.pub.util.APPUtil;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableJpaRepositories(basePackages = { "indi.monkey.webapp.dao" })
@EnableTransactionManagement
@Slf4j
public class JpaConfig {

	@Resource(name = "hibernateDataSource")
	private DataSource dataSource;

	private static final String propertyFileName = APPUtil.getProjectPath(DataBaseConfigListener.class)
			+ APPUtil.defaultConfigName;
	private Properties properties;

	@PostConstruct
	public void init() {
		log.info("start loader peroperties ..... filePath:\"{}\"", propertyFileName);
		try {
			properties = PropertiesLoaderUtils.loadAllProperties(propertyFileName);
			log.info("load success...");
		} catch (IOException e) {
			properties = new Properties();
			log.error("load error ...", e);
		}
	}

	/**
	 * @return
	 * @throws IOException
	 */

	private void put(String key, String defaultVal, Map<String, Object> jpaProperties) {
		jpaProperties.put(key, properties.getProperty(key, defaultVal));
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws IOException {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("indi.monkey.webapp.pojo");
		factory.setDataSource(dataSource);
		Map<String, Object> jpaProperties = Maps.newHashMap();
		if (properties == null) {
			this.init();
		}
		put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy", jpaProperties);
		put("hibernate.dialect", "indi.monkey.webapp.dao.config.JpaDialectConfig", jpaProperties);
		put("hibernate.jdbc.batch_size", "50", jpaProperties);
		put("hibernate.hbm2ddl.auto", "update", jpaProperties);
		put("hibernate.show_sql", "false", jpaProperties);
		put("hibernate.format_sql", "true", jpaProperties);
		log.info("start init {} .... properties :{}... datasource:{}", this.getClass().getName(), jpaProperties,
				dataSource);
		factory.setJpaPropertyMap(jpaProperties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
}
