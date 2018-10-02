package indi.monkey.webapp.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import indi.monkey.webapp.commons.annotation.AppService;
import indi.monkey.webapp.commons.annotation.HandlerMethod;
import indi.monkey.webapp.commons.annotation.Reserved;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.dto.SocketResponse;
import indi.monkey.webapp.dao.tieba.TiebaUserDao;
import indi.monkey.webapp.pojo.hibernate.tieba.TiebaUser;
import indi.monkey.webapp.proxy.SpiderProxy;

@AppService(id = 2, name = "spiderService")
public class SpiderServiveImpl extends BaseServiceImpl {

	@Autowired
	TiebaUserDao tiebaUserDao;

	@Reserved
	SpiderProxy spiderProxy;
	
	

	@Override
	public Response<?> service(Request request) {
		return super.service(request);
	}



	@HandlerMethod("baiduTieba")
	public Response<?> baiduTieba(Request request) {
		try {
			SocketResponse<Set<TiebaUser>> resp = spiderProxy.baiduTieba(request);
			tiebaUserDao.saveAll(resp.getData());
			return Response.of(200);
		} catch (Exception e) {
			return Response.builder().status(199).exception(e).build();
		}
	}

}
