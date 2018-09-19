package indi.monkey.webapp.service.impl;

import java.util.List;
import java.util.Set;

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
			SocketResponse<Set<TaobaoShop>> resp = spiderProxy.taobaoGoods(request);
			Set<TaobaoShop> shops = resp.getData();
			Set<Model_pic> models = Sets.newHashSet();
			Set<TaobaoGoods_Bra> bras = Sets.newHashSet();
			List<TaobaoShop> all_shops = taobaoShopDao.findAll();
			taobaoShopDao.saveAll(shops);
			shops.forEach(shop -> {
				shop.getModel_pics().forEach(model -> {
					model.setShopId(shop.getShopId());
					models.add(model);
				});
				shop.getTaobaogoods_bras().forEach(bra -> {
					bra.setShopId(shop.getShopId());
					bras.add(bra);
				});
			});
			shops.removeIf(shop -> {
				if (all_shops.contains(shop)) {
					return true;
				}
				return false;
			});

			if (bras.size() > 0) {
				List<TaobaoGoods_Bra> all_bras = taobaoGoods_braDao.findAll();
				bras.removeIf(bra -> {
					if (all_bras.contains(bra)) {
						return true;
					}
					return false;
				});
				taobaoGoods_braDao.saveAll(bras);
			}
			if (models.size() > 0) {
				List<Model_pic> all_pics = taobaoModelDao.findAll();
				models.removeIf(pic -> {
					if (all_pics.contains(pic)) {
						return true;
					}
					return false;
				});
				taobaoModelDao.saveAll(models);
			}
			return Response.of(200);
		} catch (Exception e) {
			return Response.builder().status(199).exception(e).build();
		}
	}

	public Response<?> findBrasByCup(Request request) {
		Set<TaobaoGoods_Bra> bras = taobaoGoods_braDao.findAllByCup(request.getParam("cup"));
		return Response.of(bras);
	}
}
