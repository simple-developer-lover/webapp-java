package indi.monkey.webapp;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { MybatisAutoConfiguration.class })
@ComponentScan(basePackages = WebappStater.BASE_PACKAGES)
public class WebappStater {

	protected static final String BASE_PACKAGES = "indi.monkey.webapp";

	public static void main(String[] args) {
		SpringApplication.run(WebappStater.class, args);
	}
}
