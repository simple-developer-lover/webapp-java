package indi.monkey.webapp.service.impl;

import java.util.Map;

import indi.monkey.webapp.commons.annotation.AppService;
import indi.monkey.webapp.commons.annotation.HandlerMethod;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;

@AppService(id = 4, name = "music")
public class MusicServiceImpl extends BaseServiceImpl {

	@HandlerMethod("video_url_to_musicData")
	public Response<?> video_url_to_musicData(Request request) {
		Map<String, String> params = request.getContext();
		String video_url = params.get("video_url");
		return null;
	}
}
