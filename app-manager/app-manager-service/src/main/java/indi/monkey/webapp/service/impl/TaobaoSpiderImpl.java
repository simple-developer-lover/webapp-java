package indi.monkey.webapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import indi.monkey.webapp.commons.pub.util.EchartUtils;
import indi.monkey.webapp.commons.pub.util.StringUtils;
import indi.monkey.webapp.dao.taobao.TaobaoGoods_BraDao;
import indi.monkey.webapp.dao.taobao.TaobaoModelDao;
import indi.monkey.webapp.dao.taobao.TaobaoShopDao;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoShop;
import indi.monkey.webapp.proxy.SpiderProxy;

@AppService(id = 11, name = "taobaoSpider")
public class TaobaoSpiderImpl extends BaseServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(TaobaoSpiderImpl.class);

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
//		List<TaobaoShop> shops = taobaoShopDao.findAll();
		Set<String> bra_types = Sets.newHashSet("A", "B", "C", "D", "E", "F", "G");
		List<Map<String, Object>> lists = Lists.newArrayList();
//		resultMap.put("shops", shops);
		for (String type : bra_types) {
			Map<String, Object> resultMap = new HashMap<>(2);
			resultMap.put("name", type);
			resultMap.put("value", processBraData(taobaoGoods_braDao.findByCup(type)).size());
			lists.add(resultMap);
		}
		return Response.of(EchartUtils.createOption("bras", "legend", lists));
	}

	private String[] processCup(String data) {
		Pattern p = Pattern.compile("/\\d{2}[A|B|C|D|E|F|G]");
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

	private static final Map<String, String> size_Map = new HashMap<>(6);
	static {
		size_Map.put("32", "70");
		size_Map.put("34", "75");
		size_Map.put("36", "80");
		size_Map.put("38", "85");
		size_Map.put("40", "90");
		size_Map.put("42", "95");
	}

	private String processSize(String size) {
		return size_Map.getOrDefault(size, size);
	}

	private Set<TaobaoGoods_Bra> processBraData(Set<TaobaoGoods_Bra> bras) {
		if (CollectionUtils.isEmpty(bras)) {
			return bras;
		}
		return bras.stream().map(s -> {
			String[] processCup = processCup(s.getCup());
			if (processCup != null) {
				s.setCup(processCup[1]);
				s.setSize(processSize(processCup[0]));
			}
			return s;
		}).collect(Collectors.toSet());
	}
}
