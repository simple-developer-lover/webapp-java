package indi.monkey.webapp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;

import indi.monkey.webapp.commons.annotation.AppService;
import indi.monkey.webapp.commons.annotation.HandlerMethod;
import indi.monkey.webapp.commons.annotation.Reserved;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.dto.SocketResponse;
import indi.monkey.webapp.dao.taobao.TaobaoGoods_BraDao;
import indi.monkey.webapp.dao.taobao.TaobaoModelDao;
import indi.monkey.webapp.dao.taobao.TaobaoShopDao;
import indi.monkey.webapp.dao.tieba.TiebaUserDao;
import indi.monkey.webapp.pojo.hibernate.taobao.Model_pic;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoShop;
import indi.monkey.webapp.pojo.hibernate.tieba.TiebaUser;
import indi.monkey.webapp.proxy.SpiderProxy;

@AppService(id = 2, name = "spiderService")
public class SpiderServiveImpl extends BaseServiceImpl {

	@Reserved
	SpiderProxy spiderProxy;

	@Autowired
	TaobaoShopDao taobaoShopDao;

	@Autowired
	TaobaoGoods_BraDao taobaoGoods_braDao;

	@Autowired
	TaobaoModelDao taobaoModelDao;

	@Autowired
	TiebaUserDao tiebaUserDao;

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

	@HandlerMethod("taobaoGoods")
	public Response<?> taobaoGoods(Request request) {
		try {
			SocketResponse<List<TaobaoShop>> taobaoGoods = spiderProxy.taobaoGoods(request);
			List<TaobaoShop> data = taobaoGoods.getData();
			Set<TaobaoGoods_Bra> bras = Sets.newHashSet();
			data.forEach(shop -> {
				if (shop.getShopId() != null) {
					List<TaobaoGoods_Bra> bs = shop.getBras();
					taobaoShopDao.save(shop);
					bs.forEach(bra -> {
						bra.setShop(shop);
						bras.addAll(bs);
					});
				}
			});
			if (bras.size() > 0) {
				taobaoGoods_braDao.saveAll(bras);
			}
			return Response.of(200);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.builder().status(199).exception(e).build();
		}
	}

	/*
	 * public Response<?> findBrasByCup(Request request) { Set<TaobaoGoods_Bra> bras
	 * = taobaoGoods_braDao.findAllByCup(request.getParam("cup")); return
	 * Response.of(bras); }
	 */
}
