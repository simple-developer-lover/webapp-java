package indi.monkey.webapp.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import indi.monkey.webapp.commons.annotation.AppService;
import indi.monkey.webapp.service.FileService;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@AppService(id = 0, name = "default")
@Slf4j
public class FileServiceImpl implements FileService {

	@Override
	public boolean canService(String actionType, HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	@Override
	public void service(String actionType, HttpServletRequest request, HttpServletResponse response) {
		String picPath = request.getParameter("picPath");
		File f = new File(picPath);
		try {
			@Cleanup
			ServletOutputStream outputStream = response.getOutputStream();
			BufferedImage bi = null;
			int getImgCount = 0;
			while (bi == null) {
				getImgCount++;
				if (getImgCount > 3000 / 300) {
					break;
				}
				try {
					bi = ImageIO.read(f);
					if (bi != null) {
						break;
					}
				} catch (Exception e) {
					log.error("file read fail....getImgCount:{}", getImgCount);
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			if (bi != null) {
				ImageIO.write(bi, "png", outputStream);
			}
		} catch (IOException e) {
			log.error(">>>>> file read error...", e);
		}
	}
}
