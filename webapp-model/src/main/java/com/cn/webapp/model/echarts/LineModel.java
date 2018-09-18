package com.cn.webapp.model.echarts;

import java.util.HashMap;
import java.util.Map;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;

public class LineModel implements Model {

	@Override
	public Option getChart() {
		String[] citis = { "广州", "深圳", "珠海", "汕头", "韶关", "佛山" };
		int[] datas = { 6030, 7800, 5200, 3444, 2666, 5708 };
		String[] colors = { "rgb(2,111,230)", "rgb(186,73,46)", "rgb(78,154,97)", "rgb(2,111,230)", "rgb(186,73,46)",
				"rgb(78,154,97)" };
		String title = "地市数据";

		// 底层调用gson的类
		GsonOption option = new GsonOption();
		option.title(title);
		/*
		 * 工具栏(Tool.mark数据视图，Tool.mark辅助线，
		 * MagicType图切换，Tool.restore还原，Tool.saveAsImage保存为图片
		 */
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar),
				Tool.restore, Tool.saveAsImage);
		// 显示工具提示,设置提示格式
		option.tooltip().show(true).formatter("{a} <br/>{b} : {c}");
		// 图例
		option.legend(title);
		Bar bar = new Bar(title);
		CategoryAxis category = new CategoryAxis();// 轴分类
		// 轴数据
		category = category.data(citis);
		for (int i = 0; i < citis.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("value", datas[i]);
			map.put("itemStyle", new ItemStyle().normal(new Normal().color(colors[i])));
			bar.data(map);
		}
		option.xAxis(category);// x轴
		option.yAxis(new ValueAxis());// y轴
		option.series(bar);
		return option;
	}

}
