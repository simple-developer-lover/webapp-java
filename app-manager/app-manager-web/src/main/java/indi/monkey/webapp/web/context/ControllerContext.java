package indi.monkey.webapp.web.context;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import indi.monkey.webapp.commons.web.context.CommonsContext;
import indi.monkey.webapp.web.controller.BaseController;


@Component(value = "controllerContext")
public class ControllerContext extends CommonsContext<BaseController> {

	private static final Logger logger = LoggerFactory.getLogger(ControllerContext.class);

	@PostConstruct
	public void init() {
		super.init();
		logger.info("controller init end");
	}
}
