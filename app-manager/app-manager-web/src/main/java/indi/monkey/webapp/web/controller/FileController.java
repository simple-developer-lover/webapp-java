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
import lombok.extern.slf4j.Slf4j;

/**
 * 用于文件上传下载的controller
 * 
 * @author F-Monkey
 *
 */
@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {

	FileService[] services;

	@Autowired
	ServiceJumper jumper;

	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public void uploadFile(String serviceType, String actionType, HttpServletRequest request,
			HttpServletResponse response) {
		jumper.jump(serviceType, actionType, request, response);
		log.info("upload end");
	}

	@RequestMapping(value = "/download", method = { RequestMethod.POST, RequestMethod.GET })
	public void downloadFile(String serviceType, String actionType, HttpServletRequest request,
			HttpServletResponse response) {
		jumper.jump(serviceType, actionType, request, response);
		log.info("download end");
	}
}
