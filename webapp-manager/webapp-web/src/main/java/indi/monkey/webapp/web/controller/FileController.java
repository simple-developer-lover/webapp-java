package indi.monkey.webapp.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.monkey.webapp.service.FileService;
import indi.monkey.webapp.service.context.ServiceJumper;


/**
 * 用于文件上传下载的controller
 * 
 * @author F-Monkey
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	FileService[] services;
	
	@Autowired
	ServiceJumper jumper;

	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public void uploadFile( String serviceType,String actionType,
			HttpServletRequest request, HttpServletResponse response) {
		jumper.jump(serviceType, actionType, request, response);
		logger.info("upload end");
	}

	@RequestMapping(value = "/download", method = { RequestMethod.POST, RequestMethod.GET })
	public void downloadFile(String serviceType, String actionType,
			HttpServletRequest request, HttpServletResponse response) {
		jumper.jump(serviceType, actionType, request, response);
		logger.info("download end");
	}
}
