package indi.monkey.webapp.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import indi.monkey.webapp.commons.annotation.AppService;
import indi.monkey.webapp.commons.annotation.HandlerMethod;
import indi.monkey.webapp.commons.annotation.Reserved;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.dto.SocketResponse;
import indi.monkey.webapp.commons.pub.util.StringUtils;
import indi.monkey.webapp.dao.taobao.TaobaoGoods_BraDao;
import indi.monkey.webapp.dao.taobao.TaobaoModelDao;
import indi.monkey.webapp.dao.taobao.TaobaoShopDao;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoShop;
import indi.monkey.webapp.proxy.SpiderProxy;

@AppService(id = 11, name = "taobaoSpider")
public class TaobaoSpiderImpl extends BaseServiceImpl {

	@Reserved
	SpiderProxy spiderProxy;

	@Autowired
	TaobaoShopDao taobaoShopDao;

	@Autowired
	TaobaoGoods_BraDao taobaoGoods_braDao;

	@Autowired
	TaobaoModelDao taobaoModelDao;

	@HandlerMethod("taobaoGoods")
	public Response<?> taobaoGoods(Request request) {
		try {
			SocketResponse<List<TaobaoShop>> taobaoGoods = spiderProxy.taobaoGoods(request);
			List<TaobaoShop> data = taobaoGoods.getData();
			Set<TaobaoGoods_Bra> bras = Sets.newHashSet();
			Set<TaobaoShop> shops = Sets.newHashSet();
			data = data.stream().filter(shop -> {
				if (shop.getShopId() != null) {
					List<TaobaoGoods_Bra> list = shop.getBras();
					list.forEach(bra -> {
						String content = bra.getRateContent();
						content = content.substring(0, Math.min(1000, content.length()));
						content = StringUtils.filterEmoji(content);
						bra.setRateContent(content);
						bra.setShop_id(shop.getShopId());
						bra.setSellerId(shop.getSellerId());
					});
					shops.add(shop);
					bras.addAll(list);
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			if (shops.size() > 0) {
				taobaoShopDao.saveAll(shops);
			}
			if (bras.size() > 0) {
				taobaoGoods_braDao.saveAll(bras);
			}
			return Response.of(200);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.builder().status(199).exception(e).build();
		}
	}

	@HandlerMethod("getTaobaoData")
	public Response<?> getData(Request req) {
//		String queryTypes = req.getParam("queryTable");
		List<TaobaoShop> shops = taobaoShopDao.findAll();
		Set<String> bra_types = Sets.newHashSet("A", "B", "C", "D", "E", "F", "G");
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("shops", shops);
		for (String type : bra_types) {
			resultMap.put(type, processBraData(taobaoGoods_braDao.findByCup(type)));
		}
		return Response.of(resultMap);
	}

	private String[] processCup(String data) {
		Pattern p = Pattern.compile("\\\\\\d{2}[A|B|C|D|E|F|G]");
		String[] result = new String[2];
		Matcher matcher = p.matcher(data);
		if (matcher.matches()) {
			Matcher m = Pattern.compile("\\d{2}").matcher(data);
			result[1] = String.valueOf(data.charAt(data.length() - 1));
			if (m.find()) {
				result[0] = m.group();
			}
			return result;
		}
		return null;
	}

	private Set<TaobaoGoods_Bra> processBraData(Set<TaobaoGoods_Bra> bras) {
		if (CollectionUtils.isEmpty(bras)) {
			return bras;
		}
		return bras.stream().map(s -> {
			String[] processCup = processCup(s.getCup());
			if (processCup != null) {
				s.setCup(processCup[1]);
				s.setSize(processCup[0]);
			}
			return s;
		}).collect(Collectors.toSet());
	}
}
