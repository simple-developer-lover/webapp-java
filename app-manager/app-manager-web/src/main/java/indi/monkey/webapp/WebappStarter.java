package indi.monkey.webapp;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { MybatisAutoConfiguration.class })
@ComponentScan(basePackages = WebappStarter.BASE_PACKAGES)
//@PropertySources({ @PropertySource(factory = ) })
public class WebappStarter {

	protected static final String BASE_PACKAGES = "indi.monkey.webapp";

	public static void main(String[] args) {

		SpringApplication.run(WebappStarter.class, args);
	}
}
