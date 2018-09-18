package indi.monkey.webapp.proxy.local;

import java.util.List;

import indi.monkey.webapp.commons.annotation.ReserveProxy;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.proxy.MusicProxy;

@ReserveProxy
public class MusicProxyImpl implements MusicProxy{

	public Response<List<String>> search(Request req) {
		return null;
	}

	public Response<String> download(Request req) {
		return null;
	}

}
