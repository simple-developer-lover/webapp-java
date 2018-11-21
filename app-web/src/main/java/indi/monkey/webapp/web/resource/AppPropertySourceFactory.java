package indi.monkey.webapp.web.resource;

import java.io.IOException;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;

public class AppPropertySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
		return (name != null ? new ResourcePropertySource(name, resource) : new ResourcePropertySource("",resource));
	}

}
