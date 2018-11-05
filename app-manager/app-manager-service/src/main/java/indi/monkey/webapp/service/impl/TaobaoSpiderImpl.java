package indi.monkey.webapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import indi.monkey.webapp.commons.annotation.AppService;
import indi.monkey.webapp.commons.annotation.HandlerMethod;
import indi.monkey.webapp.commons.annotation.Reserved;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.dto.SocketResponse;
import indi.monkey.webapp.commons.dto.response.EchartsDto;
import indi.monkey.webapp.commons.dto.response.Series;
import indi.monkey.webapp.commons.pub.util.StringUtils;
import indi.monkey.webapp.dao.taobao.TaobaoGoods_BraDao;
import indi.monkey.webapp.dao.taobao.TaobaoModelDao;
import indi.monkey.webapp.dao.taobao.TaobaoShopDao;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoShop;
import indi.monkey.webapp.proxy.SpiderProxy;
import lombok.extern.slf4j.Slf4j;

@AppService(id = 11, name = "taobaoSpider")
@Slf4j
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
			log.error(">>>>", e);
			return Response.builder().status(199).exception(e).build();
		}
	}

	@HandlerMethod("getTaobaoData")
	public Response<?> getData(Request req) {
		// List<TaobaoShop> shops = taobaoShopDao.findAll();
		Set<String> bra_types = Sets.newHashSet("A", "B", "C", "D", "E", "F", "G");
		List<Map<String, Object>> lists = Lists.newArrayList();
		// resultMap.put("shops", shops);
		for (String type : bra_types) {
			Map<String, Object> resultMap = new HashMap<>(2);
			resultMap.put("name", type);
			resultMap.put("value", String.valueOf(processBraData(taobaoGoods_braDao.findByCup(type)).size()));
			lists.add(resultMap);
		}
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("barData", createBar("bras", "legend", lists));
		resultMap.put("lineData", buildLineData("bras", "legend", taobaoGoods_braDao.findAll()));
		return Response.of(resultMap);
	}

	static class CupSizeFilter {
		private static final Map<String, String> size_Map = new HashMap<>(6);
		static Pattern p1 = Pattern.compile("/\\d{2}[A|B|C|D|E|F|G]");
		static Pattern p2 = Pattern.compile("\\d{2}");
		static {
			size_Map.put("32", "70");
			size_Map.put("34", "75");
			size_Map.put("36", "80");
			size_Map.put("38", "85");
			size_Map.put("40", "90");
			size_Map.put("42", "95");
		}

		private static String[] processCup(String data) {
			String[] result = new String[2];
			Matcher matcher = p1.matcher(data);
			if (matcher.matches()) {
				Matcher m = p2.matcher(data);
				result[1] = String.valueOf(data.charAt(data.length() - 1));
				if (m.find()) {
					result[0] = m.group();
				}
				return result;
			}
			return null;
		}

		private static String processSize(String size) {
			return size_Map.getOrDefault(size, size);
		}
	}

	public static EchartsDto createBar(String title, String legend, List<Map<String, Object>> list) {
		EchartsDto dto = new EchartsDto();
		dto.setTitle(title);
		dto.setLegend(new String[] { legend });
		String[] xAxis = new String[list.size()];
		Series series = new Series();
		Integer[] yAxis = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++) {
			xAxis[i] = list.get(i).get("name").toString();
			yAxis[i] = Integer.valueOf(list.get(i).get("value").toString());
		}
		dto.setXAxis(xAxis);
		series.setData(yAxis);
		series.setType("bar");
		dto.setSeries(Lists.newArrayList(series));
		return dto;
	}

	private EchartsDto buildLineData(String title, String legend, List<TaobaoGoods_Bra> bras) {
		EchartsDto dto = new EchartsDto();
		dto.setTitle(title);
		dto.setLegend(new String[] { legend });
		List<Series> series_list = Lists.newArrayList();
		String[] sizes = { "70", "75", "80", "85", "90", "95" };
		String[] cups = { "A", "B", "C", "D", "E", "F", "G" };
		List<String> size_list = Lists.newArrayList(sizes);
		List<String> cup_list = Lists.newArrayList(cups);

		Table<String, String, Integer> table = HashBasedTable.create();
		for (String cup : cups) {
			for (String size : sizes) {
				table.put(cup, size, 0);
			}
		}
		for (TaobaoGoods_Bra bra : bras) {
			String cup = bra.getCup();
			String size = bra.getSize();
			if (cup_list.contains(cup) && size_list.contains(size)) {
				if (table.containsRow(cup)) {
					Map<String, Integer> row = table.row(cup);
					if (row.containsKey(size)) {
						table.put(cup, size, row.get(size) + 1);
					} else {
						table.put(cup, size, 1);
					}
				} else {
					table.put(cup, size, 1);
				}
			}
		}

		for (String cup : cups) {
			Series series = new Series();
			series.setName(cup);
			series.setType("line");
			Map<String, Integer> row = table.row(cup);
			Integer[] data = new Integer[sizes.length];
			int index = 0;
			for (String size : sizes) {
				data[index] = row.get(size);
				index++;
			}
			series.setData(data);
			series_list.add(series);
		}
		dto.setXAxis(sizes);
		dto.setSeries(series_list);
		return dto;
	}

	private Set<TaobaoGoods_Bra> processBraData(Set<TaobaoGoods_Bra> bras) {
		if (CollectionUtils.isEmpty(bras)) {
			return bras;
		}
		return bras.stream().map(s -> {
			String[] processCup = CupSizeFilter.processCup(s.getCup());
			if (processCup != null) {
				s.setCup(processCup[1]);
				s.setSize(CupSizeFilter.processSize(processCup[0]));
			}
			return s;
		}).collect(Collectors.toSet());
	}
}
