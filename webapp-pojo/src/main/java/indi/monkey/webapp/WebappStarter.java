package indi.monkey.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappStarter {

	protected static final String BASE_PACKAGES = "indi.monkey.webapp";

	public static void main(String[] args) {
		SpringApplication.run(WebappStarter.class, args);
	}
}
